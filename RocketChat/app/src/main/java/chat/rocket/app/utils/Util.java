package chat.rocket.app.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by julio on 27/11/15.
 */
public class Util {
    public static Gson GSON = new Gson();

    //TODO: Do it in a async way
    public static JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
        Iterator<String> it = source.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = source.get(key);
            if (!target.has(key)) {
                // new value for "key":
                target.put(key, value);
            } else {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepMerge(valueJson, target.getJSONObject(key));
                } else {
                    target.put(key, value);
                }
            }
        }
        return target;
    }

    //TODO: Do it in a async way
    public static JSONObject deepRemove(JSONObject source, JSONObject target) throws JSONException {
        Iterator<String> it = source.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = source.get(key);
            if (target.has(key)) {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepRemove(valueJson, target.getJSONObject(key));
                } else {
                    target.remove(key);
                }
            }
        }
        return target;
    }
}
