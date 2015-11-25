package chat.rocket.app.ui.base;

import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import chat.rocket.operations.meteor.MeteorCallback;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.methods.RocketMethods;

/**
 * Created by julio on 20/11/15.
 */
public class BaseActivity extends RxAppCompatActivity implements MeteorCallback {
    private MeteorSingleton mMeteor;
    protected RocketMethods mRocketMethods;

    @Override
    protected void onResume() {
        super.onResume();
        if (FacebookSdk.getApplicationId() != null) {
            // Logs 'install' and 'app activate' App Events.
            AppEventsLogger.activateApp(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FacebookSdk.getApplicationId() != null) {
            // Logs 'app deactivate' App Event.
            AppEventsLogger.deactivateApp(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeteor = MeteorSingleton.getInstance();
        mRocketMethods = new RocketMethods(mMeteor);
        if (savedInstanceState == null) {
            mMeteor.setCallback(this);
        }
    }

    protected void startMeteorConnection() {
        if (!mMeteor.isConnected() && !mMeteor.isConnecting()) {
            mMeteor.reconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mMeteor.unsetCallback(this);
        }
        if (mClosingApp) {
            mMeteor.disconnect();
        }
    }

    private boolean mClosingApp;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mClosingApp = true;
    }

    @Override
    public void onConnect(final boolean signedInAutomatically) {
    }

    @Override
    public void onDisconnect(int code, String reason) {
        log("Disconnected code:" + code + ", reason:" + reason);
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        log("Data added to <" + collectionName + "> in document <" + documentID + ">");
        log("    Added: " + fieldsJson);
    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        log("Data changed in <" + collectionName + "> in document <" + documentID + ">");
        log("    Updated: " + updatedValuesJson);
        log("    Removed: " + removedValuesJson);
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        log("Data removed from <" + collectionName + "> in document <" + documentID + ">");
    }

    @Override
    public void onException(Exception e) {
        log("Exception:" + e);
        if (e != null) {
            e.printStackTrace();
        }
    }

    protected void log(String str) {
        Log.d("teste", str);
    }

}
