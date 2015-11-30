package chat.rocket.app.ui.base;

import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import chat.rocket.operations.RocketSubscriptions;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.methods.RocketMethods;

/**
 * Created by julio on 20/11/15.
 */
public class BaseActivity extends RxAppCompatActivity {
    protected MeteorSingleton mMeteor;
    protected RocketMethods mRocketMethods;
    protected RocketSubscriptions mRocketSubscriptions;

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
        mRocketSubscriptions = new RocketSubscriptions();
    }

    protected void startMeteorConnection() {
        if (!mMeteor.isConnected() && !mMeteor.isConnecting()) {
            mMeteor.reconnect();
        }
    }

    protected void endMeteorConnection() {
        mMeteor.disconnect();
    }
}
