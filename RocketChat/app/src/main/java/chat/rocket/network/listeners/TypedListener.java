package chat.rocket.network.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by julio on 19/11/15.
 */
abstract class TypedListener<T> extends LogListener {
    private static ObjectMapper mapper = new ObjectMapper();

    public abstract void onSuccess(T result);

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        if (result == null) {
            result = new JSONObject().toString();
        }
        Type[] listTypeArgs = ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments();

        try {
            if (listTypeArgs[0] instanceof Class) {
                onSuccess(mapper.readValue(result, (Class<T>) listTypeArgs[0]));
            } else {
                TypeReference<T> type = new TypeReference<T>() {
                };
                onSuccess((T) mapper.readValue(result, type));
            }

        } catch (Exception e) {
            onError("failed to convert " + result + " into " + listTypeArgs[0].getClass().getName(), e.getLocalizedMessage(), e.toString());
        }
    }
}
