package chat.rocket.operations.methods.listeners;

import android.util.Log;

import chat.rocket.operations.meteor.ResultListener;

/**
 * Created by julio on 19/11/15.
 */
public class LogListener implements ResultListener {
    private String tag;

    public LogListener() {
        tag = getClass().getSuperclass().getSimpleName();
    }

    public LogListener(String tag) {
        this.tag = tag;
    }

    @Override
    public void onSuccess(String result) {
        if (result != null) {
            Log.d(tag, result);
        } else {
            Log.d(tag, "Empty result");
        }
    }

    @Override
    public void onError(String error, String reason, String details) {
        Log.d(tag, "error: " + error + ", reason: " + reason + ", details: " + details);
    }
}
