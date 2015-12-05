package chat.rocket.operations.methods.listeners;

import android.util.Log;

import chat.rocket.operations.meteor.SubscribeListener;

public abstract class LogSubscribeListener implements SubscribeListener {

    private final String tag;

    public LogSubscribeListener() {
        tag = getClass().getSuperclass().getSimpleName();
    }

    public LogSubscribeListener(String tag) {
        this.tag = tag;
    }

    @Override
    public void onSuccess() {
        Log.d(tag, "onSuccess");
    }

    @Override
    public void onError(String error, String reason, String details) {
        Log.d(tag, "error: " + error + ", reason: " + reason + ", details: " + details);
    }
}
