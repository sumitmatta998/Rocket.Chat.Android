package chat.rocket.app.ui.base;

import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import chat.rocket.app.RocketApp;
import chat.rocket.rc.RocketMethods;
import chat.rocket.rc.RocketSubscriptions;
import chat.rocket.rxrc.RxRocketMethods;
import chat.rocket.rxrc.RxRocketSubscriptions;
import meteor.operations.MeteorSingleton;
import rxmeteor.operations.RxMeteor;

/**
 * Created by julio on 20/11/15.
 */
public class BaseActivity extends RxAppCompatActivity {
    protected RocketMethods mRocketMethods;
    protected RocketSubscriptions mRocketSubscriptions;
    protected RxRocketSubscriptions mRxRocketSubscriptions;
    protected RxMeteor mRxMeteor;
    protected RxRocketMethods mRxRocketMethods;

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
        RocketApp app = ((RocketApp) getApplication());
        MeteorSingleton meteor = app.getMeteor();
        mRxMeteor = app.getRxMeteor();
        mRocketMethods = new RocketMethods(meteor);
        mRxRocketMethods = new RxRocketMethods(mRocketMethods);
        mRocketSubscriptions = new RocketSubscriptions(meteor);
        mRxRocketSubscriptions = new RxRocketSubscriptions(mRocketSubscriptions);
    }

    protected void startMeteorConnection() {
        if (!mRxMeteor.isConnected() && !mRxMeteor.isConnecting()) {
            RocketApp app = ((RocketApp) getApplication());
            app.connect();
        }
    }

    protected void endMeteorConnection() {
        mRxMeteor.disconnect();
    }
}
