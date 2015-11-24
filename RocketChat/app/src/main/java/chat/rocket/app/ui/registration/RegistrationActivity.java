package chat.rocket.app.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.operations.methods.listeners.RegisterUserListener;

/**
 * Created by julio on 20/11/15.
 */
public class RegistrationActivity extends BaseActivity {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfirmationEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mNameEditText = (EditText) findViewById(R.id.NameEditText);
        mEmailEditText = (EditText) findViewById(R.id.EmailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
        mPasswordConfirmationEditText = (EditText) findViewById(R.id.PasswordConfirmationEditText);

        findViewById(R.id.RegistrationButton).setOnClickListener(v -> {

            String name = mNameEditText.getText().toString().trim();
            String email = mEmailEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();
            String passwordConfirmation = mPasswordConfirmationEditText.getText().toString().trim();

            if (validadeFields(name, email, password, passwordConfirmation)) {
                executeRegistration(name, email, password);
            }
        });
    }

    private boolean validadeFields(String name, String email, String password, String passwordConfirmation) {
        //TODO: What would be valid values here?
        return name.length() > 3 && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() > 5 && password.equals(passwordConfirmation);
    }

    private void executeRegistration(String name, String email, String password) {
        mRocketMethods.registerUser(name, email, password, new RegisterUserListener() {
            @Override
            public void onError(String error, String reason, String details) {

            }

            @Override
            public void onResult(String result) {
                returnUserData(email, password);
            }
        });
    }

    private void returnUserData(String email, String password) {
        Intent data = new Intent();
        data.putExtra(EMAIL, email);
        data.putExtra(PASSWORD, password);
        setResult(RESULT_OK, data);
        finish();
    }
}