package chat.rocket.network;

/**
 * Created by julio on 16/11/15.
 */
public interface PersistenceHandler {
    String getString(String key);

    void putString(String key, String value);
}
