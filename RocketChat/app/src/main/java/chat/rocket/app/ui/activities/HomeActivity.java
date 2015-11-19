package chat.rocket.app.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import chat.rocket.app.R;
import chat.rocket.models.Channel;
import chat.rocket.models.Channels;
import chat.rocket.network.MeteorCallback;
import chat.rocket.network.MeteorSingleton;
import chat.rocket.network.ResultListener;
import chat.rocket.network.RocketAPI;
import de.tavendo.autobahn.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MeteorCallback {

    private MeteorSingleton mMeteor;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mMeteor = MeteorSingleton.getInstance();
        mMeteor.setCallback(this);
        if (!mMeteor.isConnected() && !mMeteor.isConnecting()) {
            mMeteor.reconnect();
        } else {
            if (mMeteor.isConnected()) {
                log("Is logged in: " + mMeteor.isLoggedIn());
                log("User ID: " + mMeteor.getUserId());
                mMeteor.call("channelsList", mChannelsListener);
            }
        }
    }


    private ResultListener mAddUserToRoomListener = new ResultListener() {
        @Override
        public void onSuccess(String result) {
            log("Result: " + result);
        }

        @Override
        public void onError(String error, String reason, String details) {
            log("Error: " + error + " / " + reason + " / " + details);
        }
    };
    private ResultListener mCreateChannelListener = new ResultListener() {
        @Override
        public void onSuccess(String result) {
            ObjectMapper mapper = new ObjectMapper();
            String id = "";
            try {
                id = mapper.readTree(result).get("rid").asText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new RocketAPI(mMeteor).addUserToRoom(id, "cotta.bueno.cesar.julio", mAddUserToRoomListener);
            log("Result: " + id);
        }

        @Override
        public void onError(String error, String reason, String details) {
            log("Error: " + error + " / " + reason + " / " + details);
        }
    };
    private ResultListener mChannelsListener = new ResultListener() {
        @Override
        public void onSuccess(String result) {
            log("Result: " + result);
            ObjectMapper mapper = new ObjectMapper();
            try {
                Channels channels = mapper.readValue(result, Channels.class);
                String newChannelName = "juliohannel123456";
                for (Channel c : channels.getChannels()) {
                    if (c.getName().equals(newChannelName)) {
                        return;
                    }
                }

                mMeteor.call("createChannel", new Object[]{newChannelName, new short[]{}}, mCreateChannelListener);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(String error, String reason, String details) {
            log("Error: " + error + " / " + reason + " / " + details);
        }
    };

    @Override
    public void onConnect(final boolean signedInAutomatically) {
        log("Connected");
        log("Is logged in: " + mMeteor.isLoggedIn());
        log("User ID: " + mMeteor.getUserId());

        if (signedInAutomatically) {
            log("Successfully logged in automatically");
        } else {
            // sign in to the server
            mMeteor.loginWithUsername("julio.cesar.bueno.cotta", "google", new ResultListener() {

                @Override
                public void onSuccess(String result) {
                    log("Successfully logged in: " + result);

                    log("Is logged in: " + mMeteor.isLoggedIn());
                    log("User ID: " + mMeteor.getUserId());
                    if (mMeteor.isConnected()) {
                        mMeteor.call("channelsList", mChannelsListener);

                    }
                }

                @Override
                public void onError(String error, String reason, String details) {
                    log("Could not log in: " + error + " / " + reason + " / " + details);
                }
            });
        }
    }

    @Override
    public void onDisconnect(WebSocketCloseNotification code, String reason) {
        log("Disconnected code:" + code + ", reason:" + reason);
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        log("Data added to <" + collectionName + "> in document <" + documentID + ">");
        log("    Added: " + fieldsJson);
    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        log("Data changed in <" + collectionName + "> in document <" + documentID + ">");
        log("    Updated: " + updatedValuesJson);
        log("    Removed: " + removedValuesJson);
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        log("Data removed from <" + collectionName + "> in document <" + documentID + ">");
    }

    @Override
    public void onException(Exception e) {
        log("Exception:" + e);
        if (e != null) {
            e.printStackTrace();
        }
    }

    private void log(String txt) {
        Log.d("HomeActivity", txt);
        mTextView.append(txt);
        mTextView.append("\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMeteor.unsetCallback(this);
        if (isFinishing()) {
            mMeteor.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
