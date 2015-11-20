package chat.rocket.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;

/**
 * Created by julio on 20/11/15.
 */
public class LoginActivity extends BaseActivity {
    private static final List<String> PERMISSIONS = Arrays.asList("email", "user_birthday");
    private static final List<String> FIELDS = Arrays.asList("id", "email", "name", "birthday", "first_name", "last_name", "link", "gender", "locale", "age_range");
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.FacebookButton);
        loginButton.setReadPermissions(PERMISSIONS);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("testeeee", object.toString());
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    Log.d("teste", currentAccessToken.toString());
                    Bundle params = new Bundle();
                    params.putString("fields", TextUtils.join(",", FIELDS));
                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/me",
                            params,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
                                    if (response != null && response.getError() == null) {
                                        try {
                                            Log.d("testeeee", response.toString());
                                            String email = response.getJSONObject().get("email").toString();
                                            String birthday = response.getJSONObject().get("birthday").toString();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                    ).executeAsync();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
