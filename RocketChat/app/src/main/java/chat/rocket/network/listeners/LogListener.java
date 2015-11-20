package chat.rocket.network.listeners;

import android.util.Log;

import chat.rocket.network.meteor.ResultListener;

/**
 * Created by julio on 19/11/15.
 */
public class LogListener implements ResultListener {
    private String tag;

    public LogListener() {
        tag = "LogListener";
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
