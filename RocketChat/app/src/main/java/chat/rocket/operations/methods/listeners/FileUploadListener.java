package chat.rocket.operations.methods.listeners;

import chat.rocket.operations.meteor.ResultListener;

/**
 * Created by julio on 05/12/15.
 */
public interface FileUploadListener extends ResultListener {
    void onProgress(float progress);
}
