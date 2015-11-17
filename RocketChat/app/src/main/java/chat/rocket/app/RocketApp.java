package chat.rocket.app;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import chat.rocket.network.Meteor;
import chat.rocket.network.MeteorSingleton;
import chat.rocket.network.PersistenceHandler;

/**
 * Created by julio on 16/11/15.
 */
public class RocketApp extends Application implements PersistenceHandler {

    @Override
    public void onCreate() {
        super.onCreate();
        connect();
    }

    public static RocketApp get(Context context) {
        return (RocketApp) context.getApplicationContext();
    }

    private void connect() {
        // enable logging of internal events for the library
        Meteor.setLoggingEnabled(true);

        // create a new instance (protocol version in second parameter is optional)
        MeteorSingleton meteor = MeteorSingleton.createInstance(this, "ws://android-ddp-meteor.meteor.com/websocket");
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
