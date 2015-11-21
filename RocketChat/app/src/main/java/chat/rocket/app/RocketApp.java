package chat.rocket.app;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import chat.rocket.operations.meteor.Meteor;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.meteor.Persistence;
import io.fabric.sdk.android.Fabric;

/**
 * Created by julio on 16/11/15.
 */
public class RocketApp extends Application implements Persistence {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        setupMeteor();
        FacebookSdk.sdkInitialize(getApplicationContext());

    }

    public static RocketApp get(Context context) {
        return (RocketApp) context.getApplicationContext();
    }

    private void setupMeteor() {
        // enable logging of internal events for the library
        MeteorSingleton.createInstance(this, BuildConfig.WS_URL, Meteor.SUPPORTED_DDP_VERSIONS[0]);
    }

    @Override
    public String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(key, null);
    }

    @Override
    public void putString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(key, value).apply();
    }
}
