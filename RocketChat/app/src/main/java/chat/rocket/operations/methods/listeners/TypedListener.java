package chat.rocket.operations.methods.listeners;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by julio on 19/11/15.
 */
abstract class TypedListener<T> extends LogListener {
    private static Gson mapper = new Gson();

    public abstract void onResult(T result);

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        if (result == null) {
            result = new JSONObject().toString();
        }
        Type[] listTypeArgs = ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments();

        try {
            if (listTypeArgs[0] instanceof Class) {
                onResult(mapper.fromJson(result, (Class<T>) listTypeArgs[0]));
            } else {
                Type listType = new TypeToken<T>() {
                }.getType();
                onResult((T) mapper.fromJson(result, listType));
            }

        } catch (Exception e) {
            onError("failed to convert " + result + " into " + listTypeArgs[0].getClass().getName(), e.getLocalizedMessage(), e.toString());
        }
    }
}
