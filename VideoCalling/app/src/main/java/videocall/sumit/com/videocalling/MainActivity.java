package videocall.sumit.com.videocalling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import chat.rocket.app.ui.login.LoginActivity;
import chat.rocket.app.ui.splash.SplashActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rocketChat).setOnClickListener(this);
        findViewById(R.id.rocketChatLoginActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("this", "onClick");
        switch (view.getId()) {
            case R.id.rocketChat:
                Intent i = new Intent(this, SplashActivity.class);
                startActivity(i);
                break;
            case R.id.rocketChatLoginActivity:
                Intent i1 = new Intent(this, LoginActivity.class);
                startActivity(i1);
                break;
        }
    }
}
