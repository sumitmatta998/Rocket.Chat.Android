package chat.rocket.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.MainActivity;
import chat.rocket.app.ui.registration.RegistrationActivity;
import chat.rocket.models.Token;
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
    private AccessTokenTracker mAccessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken != null) {
                Log.d("teste", currentAccessToken.toString());
                requestMe();
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
        findViewById(R.id.RegistrationTextView).setOnClickListener(v -> {
            openRegistrationForResult();
        });
    }

    private void openRegistrationForResult() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
    }

    private void executeLogin(String login, String password) {
        LoginListener loginListener = new LoginListener() {
            @Override
            public void onResult(Token result) {
                startMainActivity();
            }

            @Override
            public void onError(String error, String reason, String details) {
                super.onError(error, reason, details);
                Toast.makeText(LoginActivity.this, error + ", " + reason + ", " + details, Toast.LENGTH_LONG).show();
            }
        };
        if (Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            mRocketMethods.loginWithEmail(login, password, loginListener);
        } else {
            mRocketMethods.loginWithUsername(login, password, loginListener);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void requestMe() {
        Bundle params = new Bundle();
        params.putString("fields", TextUtils.join(",", FIELDS));
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                params,
                HttpMethod.GET,
                response -> {
                    if (response != null && response.getError() == null) {
                        try {
                            Log.d("testeeee", response.toString());
                            String email = response.getJSONObject().get("email").toString();
                            String birthday = response.getJSONObject().get("birthday").toString();
                            Toast.makeText(LoginActivity.this, email + ", " + birthday, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
    }
}
