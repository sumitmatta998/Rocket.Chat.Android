package chat.rocket.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.concurrent.TimeUnit;

import chat.rocket.app.db.DBManager;
import chat.rocket.app.db.collections.LoginServiceConfiguration;
import chat.rocket.app.db.collections.StreamMessages;
import chat.rocket.app.db.collections.StreamNotifyRoom;
import chat.rocket.app.db.dao.CollectionDAO;
import chat.rocket.app.db.dao.MessageDAO;
import chat.rocket.app.db.dao.RCSubscriptionDAO;
import chat.rocket.app.enumerations.LoginService;
import chat.rocket.app.enumerations.NotifyActionType;
import chat.rocket.app.utils.Util;
import chat.rocket.models.NotifyRoom;
import chat.rocket.operations.RocketSubscriptions;
import chat.rocket.operations.meteor.Meteor;
import chat.rocket.operations.meteor.MeteorCallback;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.meteor.Persistence;
import chat.rocket.operations.meteor.SubscribeListener;
import io.fabric.sdk.android.Fabric;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
                /*TODO: The database insertion is async, so before we query it, we need to wait some time
                  need to change this approach to something more reliable, like a ContentObserver to the LoginServiceConfiguration collection*/
                Observable.defer(() -> Observable.just("")).delay(500, TimeUnit.MICROSECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
                    String appId = LoginServiceConfiguration.query(LoginService.FACEBOOK);
                    if (!TextUtils.isEmpty(appId)) {
                        FacebookSdk.setApplicationId(appId);
                    }
                    Intent intent = new Intent();
                    intent.setAction(ACTION_CONNECTED);
                    intent.putExtra(LOGGED_KEY, signedInAutomatically);
                    LocalBroadcastManager.getInstance(RocketApp.this).sendBroadcast(intent);
                });
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

        subs.streamMessages(listener);

        subs.meteorAutoupdateClientVersions(listener);

        subs.subscription(listener);

        subs.userData(listener);

        subs.activeUsers(listener);

        subs.adminSettings(listener);

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
            case RCSubscriptionDAO.COLLECTION_NAME:
                RCSubscriptionDAO sub = Util.GSON.fromJson(newValuesJson, RCSubscriptionDAO.class);
                sub.insert(documentID);
                break;
            case StreamNotifyRoom.COLLECTION_NAME:
                StreamNotifyRoom stream = Util.GSON.fromJson(newValuesJson, StreamNotifyRoom.class);
                stream.parseArgs();
                NotifyRoom notifyRoom = stream.getNotifyRoom();
                if (notifyRoom != null) {
                    if (NotifyActionType.TYPING.equals(notifyRoom.getAction())) {
                        executeRoomNotification(notifyRoom);
                    } else if (NotifyActionType.DELETE_MESSAGE.equals(notifyRoom.getAction())) {
                        MessageDAO.remove(notifyRoom.getId());
                    }
                } else {
                    Log.d("debug", newValuesJson);
                }
                break;
            default:
                new CollectionDAO(collectionName, documentID, newValuesJson).insert();
        }
    }

    private void executeRoomNotification(NotifyRoom notifyRoom) {
        String rid = notifyRoom.getRid();
        if (!TextUtils.isEmpty(rid)) {
            Intent intent = new Intent();
            intent.setAction(StreamNotifyRoom.COLLECTION_NAME + rid);
            intent.putExtra(StreamNotifyRoom.COLLECTION_NAME, notifyRoom);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        //TODO: update the data that changed in the right table
        // I think I will not be able to use GSON, probable manual parsing and updating only the needed fields

        switch (collectionName) {
            case RCSubscriptionDAO.COLLECTION_NAME:
                RCSubscriptionDAO rcSub = RCSubscriptionDAO.get(documentID);
                if (rcSub != null) {
                    rcSub.update(documentID, updatedValuesJson);
                }
                return;
            case StreamNotifyRoom.COLLECTION_NAME:
                break;
        }
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
