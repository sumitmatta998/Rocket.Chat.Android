package chat.rocket.app;

import android.app.Application;
import android.preference.PreferenceManager;

import chat.rocket.network.PersistenceHandler;

/**
 * Created by julio on 16/11/15.
 */
public class RocketApp extends Application implements PersistenceHandler {
    @Override
    public void onCreate() {
        super.onCreate();
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
