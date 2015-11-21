package chat.rocket.app.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.MainActivity;
import chat.rocket.app.ui.login.LoginActivity;


/**
 * Created by julio on 20/11/15.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startMeteorConnection();
    }

    @Override
    public void onConnect(boolean signedInAutomatically) {
        super.onConnect(signedInAutomatically);
        Intent intent1 = new Intent(SplashActivity.this, signedInAutomatically ? MainActivity.class : LoginActivity.class);
        startActivity(intent1);
        finish();

    }

    @Override
    public void onException(Exception e) {
        super.onException(e);
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}
