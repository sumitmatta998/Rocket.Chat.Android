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

import chat.rocket.app.db.DBManager;
import chat.rocket.app.db.collections.LoginServiceConfiguration;
import chat.rocket.app.db.collections.StreamMessages;
import chat.rocket.app.db.collections.StreamNotifyRoom;
import chat.rocket.app.db.dao.CollectionDAO;
import chat.rocket.app.db.dao.MessageDAO;
import chat.rocket.app.db.dao.RCSubscriptionDAO;
import chat.rocket.app.utils.Util;
import chat.rocket.models.NotifyRoom;
import chat.rocket.rc.RocketSubscriptions;
import chat.rocket.rc.enumerations.LoginService;
import chat.rocket.rc.enumerations.NotifyActionType;
import chat.rocket.rxrc.RxRocketSubscriptions;
import io.fabric.sdk.android.Fabric;
import meteor.operations.Meteor;
import meteor.operations.MeteorException;
import meteor.operations.MeteorSingleton;
import meteor.operations.Persistence;
import meteor.operations.Protocol;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxmeteor.operations.RxMeteor;
import timber.log.TLog;
import timber.log.Timber;


/**
 * Created by julio on 16/11/15.
 */
public class RocketApp extends Application implements Persistence {
    public static final String ACTION_DISCONNECTED = BuildConfig.APPLICATION_ID + ".METEOR.DISCONNECTED";
    public static final String ACTION_CONNECTED = BuildConfig.APPLICATION_ID + ".METEOR.CONNECTED";
    public static final String LOGGED_KEY = "logged";

    private Subscription mConnectionSubscription;
    private RxMeteor mRxMeteor;
    private MeteorSingleton mMeteor;


    @Override
    public void onCreate() {
        super.onCreate();

        setupTimber();
        // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
        if (!TextUtils.isEmpty(BuildConfig.TWITTER_KEY)) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
            Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        } else {
            Fabric.with(this, new Crashlytics());
        }
        mMeteor = MeteorSingleton.createInstance(this, BuildConfig.WS_URL, Meteor.SUPPORTED_DDP_VERSIONS[0]);
        mRxMeteor = new RxMeteor(mMeteor);
        FacebookSdk.sdkInitialize(getApplicationContext());
        DBManager.getInstance().init(this);

    }

    private void setupTimber() {
        Timber.plant(new Timber.DebugTree(), new TLog() {
            @Override
            public void wtf(String tag, String message) {
                Log.wtf(tag, message);
            }

            @Override
            public void println(int priority, String tag, String message) {
                Log.println(priority, tag, message);
            }
        });
    }

    public static RocketApp get(Context context) {
        return (RocketApp) context.getApplicationContext();
    }

    public void connect() {
        mConnectionSubscription = mRxMeteor
                .setOnConnectObserver(signedInAutomatically -> onConnect(signedInAutomatically))
                .setOnDisconnectObserver(pair -> {
                    onDisconnect(pair.first, pair.second);
                    mConnectionSubscription.unsubscribe();
                })
                .create()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    switch (data.getType()) {
                        case ADD:
                            onDataAdded(data.getCollectionName(), data.getDocumentID(), data.getNewValuesJson());
                            break;
                        case CHANGE:
                            onDataChanged(data.getCollectionName(), data.getDocumentID(), data.getNewValuesJson(), data.getRemovedValuesJson());
                            break;
                        case REMOVE:
                            onDataRemoved(data.getCollectionName(), data.getDocumentID());
                            break;
                    }
                }, throwable -> {
                    onException(throwable);
                    mRxMeteor.disconnect();
                });

        mMeteor.reconnect();
    }

    public void onConnect(boolean signedInAutomatically) {

        RocketSubscriptions subs = new RocketSubscriptions(MeteorSingleton.getInstance());
        RxRocketSubscriptions rxSubs = new RxRocketSubscriptions(subs);

        //Note: using flatmap to try avoid backpressure
        rxSubs.loginServiceConfiguration()
                .flatMap(aVoid -> rxSubs.settings())
                .flatMap(aVoid0 -> rxSubs.streamNotifyRoom())
                .flatMap(aVoid1 -> rxSubs.streamNotifyAll())
                .flatMap(aVoid2 -> rxSubs.streamNotifyUser())
                .flatMap(aVoid3 -> rxSubs.roles())
                .flatMap(aVoid4 -> rxSubs.permissions())
                .flatMap(aVoid5 -> rxSubs.streamMessages())
                .flatMap(aVoid6 -> rxSubs.meteorAutoupdateClientVersions())
                .flatMap(aVoid7 -> rxSubs.subscription())
                .flatMap(aVoid8 -> rxSubs.userData())
                .flatMap(aVoid9 -> rxSubs.activeUsers())
                .flatMap(aVoid10 -> rxSubs.adminSettings())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Protocol.Error err = ((MeteorException) e).getError();
                        String error = err.getError();
                        String reason = err.getReason();
                        String details = err.getDetails();
                        Timber.d(error + ", " + reason + ", " + details);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        String appId = LoginServiceConfiguration.query(LoginService.FACEBOOK);
                        Timber.d("appId: " + appId);
                        if (!TextUtils.isEmpty(appId)) {
                            FacebookSdk.setApplicationId(appId);
                        }
                        Intent intent = new Intent();
                        intent.setAction(ACTION_CONNECTED);
                        intent.putExtra(LOGGED_KEY, signedInAutomatically);
                        LocalBroadcastManager.getInstance(RocketApp.this).sendBroadcast(intent);
                    }
                });


    }

    public void onDisconnect(int code, String reason) {
        Intent intent = new Intent();
        intent.setAction(ACTION_DISCONNECTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onDataAdded(String collectionName, String documentID, String newValuesJson) {
        switch (collectionName) {
            case StreamMessages.COLLECTION_NAME:
                Observable.create((OnSubscribe<StreamMessages>) subscriber -> {
                    StreamMessages msg = Util.GSON.fromJson(newValuesJson, StreamMessages.class);
                    msg.parseArgs();
                    subscriber.onNext(msg);
                    subscriber.onCompleted();
                })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(msg -> {
                            msg.insert();
                        });
                break;
            case RCSubscriptionDAO.COLLECTION_NAME:
                Observable.create((OnSubscribe<RCSubscriptionDAO>) subscriber -> {
                    RCSubscriptionDAO sub = Util.GSON.fromJson(newValuesJson, RCSubscriptionDAO.class);
                    subscriber.onNext(sub);
                    subscriber.onCompleted();
                })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(sub -> {
                            sub.insert(documentID);
                        });

                break;
            case StreamNotifyRoom.COLLECTION_NAME:
                Observable.create((OnSubscribe<StreamNotifyRoom>) subscriber -> {
                    StreamNotifyRoom stream = Util.GSON.fromJson(newValuesJson, StreamNotifyRoom.class);
                    stream.parseArgs();
                    subscriber.onNext(stream);
                    subscriber.onCompleted();
                })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(stream -> {
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
                        });
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

    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        //TODO: update the data that changed in the right table
        // I think I will not be able to use GSON, probable manual parsing and updating only the needed fields

        switch (collectionName) {
            case RCSubscriptionDAO.COLLECTION_NAME:
                Observable.create(new OnSubscribe<RCSubscriptionDAO>() {
                    @Override
                    public void call(Subscriber<? super RCSubscriptionDAO> subscriber) {
                        RCSubscriptionDAO rcSub = RCSubscriptionDAO.get(documentID);
                        subscriber.onNext(rcSub);
                        subscriber.onCompleted();
                    }
                }).subscribe(rcSub -> {
                    if (rcSub != null) {
                        rcSub.update(documentID, updatedValuesJson);
                    }
                });
                return;
            case StreamNotifyRoom.COLLECTION_NAME:
                break;
        }

        Observable.create(new OnSubscribe<CollectionDAO>() {
            @Override
            public void call(Subscriber<? super CollectionDAO> subscriber) {
                try {
                    final CollectionDAO dao = CollectionDAO.query(collectionName, documentID);
                    if (dao != null) {
                        dao.plusUpdatedValues(updatedValuesJson).lessUpdatedValues(removedValuesJson);
                    }
                    subscriber.onNext(dao);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dao -> {
                    if (dao != null) {
                        dao.update();
                    } else {
                        new CollectionDAO(collectionName, documentID, updatedValuesJson).insert();
                    }
                });

    }

    public void onDataRemoved(String collectionName, String documentID) {
        new CollectionDAO(collectionName, documentID, null).remove();
    }

    public void onException(Throwable e) {
        Crashlytics.logException(e);
    }

    @Override
    public String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(key, null);
    }

    @Override
    public void putString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(key, value).apply();
    }

    public MeteorSingleton getMeteor() {
        return mMeteor;
    }

    public RxMeteor getRxMeteor() {
        return mRxMeteor;
    }
}
