package chat.rocket.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import chat.rocket.app.db.DBManager;
import chat.rocket.app.db.collections.LoginServiceConfiguration;
import chat.rocket.app.db.collections.StreamMessages;
import chat.rocket.app.db.dao.CollectionDAO;
import chat.rocket.app.utils.Util;
import chat.rocket.operations.RocketSubscriptions;
import chat.rocket.operations.meteor.Meteor;
import chat.rocket.operations.meteor.MeteorCallback;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.meteor.Persistence;
import chat.rocket.operations.meteor.SubscribeListener;
import io.fabric.sdk.android.Fabric;

import static chat.rocket.app.db.collections.LoginServiceConfiguration.LoginService.FACEBOOK;

/**
 * Created by julio on 16/11/15.
 */
public class RocketApp extends Application implements Persistence, MeteorCallback {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    public static final String ACTION_DISCONNECTED = BuildConfig.APPLICATION_ID + ".METEOR.DISCONNECTED";
    public static final String ACTION_CONNECTED = BuildConfig.APPLICATION_ID + ".METEOR.CONNECTED";
    public static final String LOGGED_KEY = "logged";

    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(BuildConfig.TWITTER_KEY)) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
            Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        } else {
            Fabric.with(this, new Crashlytics());
        }
        DBManager.getInstance().init(this);
        setupMeteor();
        FacebookSdk.sdkInitialize(getApplicationContext());

    }

    public static RocketApp get(Context context) {
        return (RocketApp) context.getApplicationContext();
    }

    private void setupMeteor() {
        // enable logging of internal events for the library
        MeteorSingleton meteor = MeteorSingleton.createInstance(this, BuildConfig.WS_URL, Meteor.SUPPORTED_DDP_VERSIONS[0]);
        meteor.setCallback(this);
    }

    @Override
    public String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(key, null);
    }

    @Override
    public void putString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(key, value).apply();
    }

    @Override
    public void onConnect(boolean signedInAutomatically) {

        RocketSubscriptions subs = new RocketSubscriptions();
        subs.loginServiceConfiguration(new SubscribeListener() {
            @Override
            public void onSuccess() {
                String appId = LoginServiceConfiguration.query(FACEBOOK);
                if (!TextUtils.isEmpty(appId)) {
                    FacebookSdk.setApplicationId(appId);
                }
                Intent intent = new Intent();
                intent.setAction(ACTION_CONNECTED);
                intent.putExtra(LOGGED_KEY, signedInAutomatically);
                LocalBroadcastManager.getInstance(RocketApp.this).sendBroadcast(intent);
            }

            @Override
            public void onError(String error, String reason, String details) {

            }
        });

        SubscribeListener listener = new SubscribeListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(String error, String reason, String details) {

            }
        };

        subs.settings(listener);

        subs.streamNotifyRoom(listener);

        subs.streamNotifyAll(listener);

        subs.streamNotifyUser(listener);

        subs.roles(listener);

        subs.permissions(listener);

        subs.subscription(listener);

        subs.userData(listener);

        subs.activeUsers(listener);

    }

    @Override
    public void onDisconnect(int code, String reason) {
        Intent intent = new Intent();
        intent.setAction(ACTION_DISCONNECTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String newValuesJson) {
        switch (collectionName) {
            case StreamMessages.COLLECTION_NAME:
                StreamMessages msg = Util.GSON.fromJson(newValuesJson, StreamMessages.class);
                msg.parseArgs();
                msg.insert();
                break;
            default:
                new CollectionDAO(collectionName, documentID, newValuesJson).insert();
        }

    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {

        CollectionDAO dao = CollectionDAO.query(collectionName, documentID);
        if (dao != null) {
            try {
                dao.plusUpdatedValues(updatedValuesJson).lessUpdatedValues(removedValuesJson).update();
            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
            }
        } else {
            new CollectionDAO(collectionName, documentID, updatedValuesJson).insert();
        }
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        new CollectionDAO(collectionName, documentID, null).remove();
    }

    @Override
    public void onException(Exception e) {
        Crashlytics.logException(e);
    }
}
