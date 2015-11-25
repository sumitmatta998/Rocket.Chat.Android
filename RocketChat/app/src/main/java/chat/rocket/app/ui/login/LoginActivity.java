package chat.rocket.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import chat.rocket.app.R;
import chat.rocket.app.db.dao.CollectionDAO;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.MainActivity;
import chat.rocket.app.ui.login.password.ForgotPasswordActivity;
import chat.rocket.app.ui.registration.RegistrationActivity;
import chat.rocket.models.Token;
import chat.rocket.operations.RocketSubscriptions;
import chat.rocket.operations.meteor.SubscribeListener;
import chat.rocket.operations.methods.listeners.LogListener;
import chat.rocket.operations.methods.listeners.LoginListener;

/**
 * Created by julio on 20/11/15.
 */
public class LoginActivity extends BaseActivity {
    private static final List<String> PERMISSIONS = Arrays.asList("email", "user_birthday");
    private static final List<String> FIELDS = Arrays.asList("id", "email", "name", "birthday", "first_name", "last_name", "link", "gender", "locale", "age_range");
    private static final int REGISTRATION_REQUEST_CODE = 432;
    private LoginButton mFacebookButton;
    private CallbackManager mCallbackManager;
    private LoginListener mLoginListener = new LoginListener() {
        @Override
        public void onResult(Token result) {
            RocketSubscriptions subs = new RocketSubscriptions();
            subs.userData(new SubscribeListener() {
                @Override
                public void onSuccess() {
                    CollectionDAO dao = CollectionDAO.query("users", result.getId());
                    JsonParser parser = new JsonParser();
                    if (!parser.parse(dao.getNewValuesJson()).getAsJsonObject().has("username")) {
                        getUsernameSuggestion();
                    } else {
                        startMainActivity();
                    }
                }

                @Override
                public void onError(String error, String reason, String details) {

                }
            });
        }

        @Override
        public void onError(String error, String reason, String details) {
            Toast.makeText(LoginActivity.this, error + ", " + reason + ", " + details, Toast.LENGTH_LONG).show();
        }
    };

    private void getUsernameSuggestion() {
        mRocketMethods.getUsernameSuggestion(new LogListener("getusernamesug") {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                mRocketMethods.setUsername(result.replace("\"", ""), new LogListener("saveuserprofile") {
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        startMainActivity();
                    }
                });
            }
        });
    }

    private AccessTokenTracker mAccessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken != null) {
                Log.d("teste", currentAccessToken.toString());
                mRocketMethods.loginWithFacebook(currentAccessToken.getToken(), currentAccessToken.getExpires().getTime() - new Date().getTime(), mLoginListener);
            }
        }
    };
    ;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            // App code
        }

        @Override
        public void onCancel() {
            // App code
        }

        @Override
        public void onError(FacebookException exception) {
            // App code
        }
    };
    private TwitterLoginButton mTwitterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookButton = (LoginButton) findViewById(R.id.FacebookButton);
        mFacebookButton.setReadPermissions(PERMISSIONS);
        mFacebookButton.registerCallback(mCallbackManager, mFacebookCallback);

        EditText loginEditText = (EditText) findViewById(R.id.LoginEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.PasswordEditText);

        findViewById(R.id.LoginButton).setOnClickListener(v -> {
            executeLogin(loginEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
        });
        findViewById(R.id.ForgotPasswordTextview).setOnClickListener(v1 -> {
            openForgotPassword();
        });
        findViewById(R.id.RegistrationTextView).setOnClickListener(v -> {
            openRegistrationForResult();
        });


        mTwitterButton = (TwitterLoginButton) findViewById(R.id.TwitterButton);
        mTwitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = result.data.getAuthToken();
                mRocketMethods.loginWithOAuth(authToken.token, authToken.secret, mLoginListener);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    private void openForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openRegistrationForResult() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
    }

    private void executeLogin(String login, String password) {

        if (Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            mRocketMethods.loginWithEmail(login, password, mLoginListener);
        } else {
            mRocketMethods.loginWithUsername(login, password, mLoginListener);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (REGISTRATION_REQUEST_CODE == requestCode && resultCode == RESULT_OK && data != null) {
            String login = data.getStringExtra(RegistrationActivity.EMAIL);
            String password = data.getStringExtra(RegistrationActivity.PASSWORD);
            executeLogin(login, password);
        }
        mTwitterButton.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
    }
}
