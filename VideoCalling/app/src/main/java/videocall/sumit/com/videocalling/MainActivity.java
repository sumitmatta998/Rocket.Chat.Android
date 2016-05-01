package videocall.sumit.com.videocalling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rocketChat).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("this", "onClick");
        Intent i = new Intent(this,chat.rocket.app.ui.splash.SplashActivity.class);
        startActivity(i);
    }
}
