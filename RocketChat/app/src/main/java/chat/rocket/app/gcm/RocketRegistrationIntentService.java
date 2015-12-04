/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chat.rocket.app.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import chat.rocket.operations.meteor.ResultListener;
import chat.rocket.operations.methods.RocketMethods;
import chat.rocket.operations.methods.listeners.LogListener;

public class RocketRegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    private SharedPreferences mSharedPreferences;

    public RocketRegistrationIntentService() {
        super(TAG);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String senderId = mSharedPreferences.getString(PushKeys.SENDER_ID, null);
            String token = instanceID.getToken(senderId,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            sendRegistrationToServer(token);

            subscribeTopics(token);


            mSharedPreferences.edit().putString(PushKeys.TOKEN, token).apply();

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            mSharedPreferences.edit().putBoolean(PushKeys.SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    /**
     * Persist registration to third-party servers.
     * <p>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        RocketMethods methods = new RocketMethods();
        methods.setPushUser(new LogListener() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                ResultListener listener = new LogListener() {
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        mSharedPreferences.edit().putBoolean(PushKeys.SENT_TOKEN_TO_SERVER, true).apply();
                    }

                    @Override
                    public void onError(String error, String reason, String details) {
                        super.onError(error, reason, details);
                        mSharedPreferences.edit().putBoolean(PushKeys.SENT_TOKEN_TO_SERVER, false).apply();
                    }
                };
                //TODO:make it synchronous
                methods.updatePush(token, listener);
            }
        });
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

}