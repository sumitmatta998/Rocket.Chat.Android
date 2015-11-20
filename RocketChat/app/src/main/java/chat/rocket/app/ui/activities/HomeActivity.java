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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import chat.rocket.app.R;
import chat.rocket.models.Channel;
import chat.rocket.models.Channels;
import chat.rocket.models.Message;
import chat.rocket.models.RoomId;
import chat.rocket.models.Token;
import chat.rocket.network.RocketMethods;
import chat.rocket.network.RocketSubscriptions;
import chat.rocket.network.Subscription;
import chat.rocket.network.listeners.AddUserToRoomListener;
import chat.rocket.network.listeners.ArchiveRoomListener;
import chat.rocket.network.listeners.CanAccessRoomListener;
import chat.rocket.network.listeners.ChannelsListListener;
import chat.rocket.network.listeners.CreateChannelListener;
import chat.rocket.network.listeners.EraseRoomListener;
import chat.rocket.network.listeners.JoinRoomListener;
import chat.rocket.network.listeners.LoginListener;
import chat.rocket.network.listeners.SaveRoomNameListener;
import chat.rocket.network.listeners.SendMessageListener;
import chat.rocket.network.listeners.UpdateMessageListener;
import chat.rocket.network.meteor.MeteorCallback;
import chat.rocket.network.meteor.MeteorSingleton;
import chat.rocket.network.meteor.ResultListener;
import chat.rocket.network.meteor.SubscribeListener;
import de.tavendo.autobahn.WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MeteorCallback {
    ObjectMapper mMapper = new ObjectMapper();
    private MeteorSingleton mMeteor;
    private TextView mTextView;
    private RocketMethods mRocketMethods;
    private RocketSubscriptions mRocketSubscriptions;
    private AddUserToRoomListener mAddUserToRoomListener = new AddUserToRoomListener() {
        @Override
        public void onSuccess(Boolean result) {
            log("added user to room?" + result);
        }
    };

    private JoinRoomListener mJoinRoomListener = new JoinRoomListener() {
        @Override
        public void onSuccess(Void result) {
            log(result);
        }
    };

    private CanAccessRoomListener mCanAccessRoomListener = new CanAccessRoomListener() {
        @Override
        public void onSuccess(Channel result) {
            log(result);
            if ("c".equals(result.getType())) {
                mRocketMethods.joinRoom(result.getId(), mJoinRoomListener);
            }
        }
    };

    private UpdateMessageListener mUpdateMessageListener = new UpdateMessageListener() {
        @Override
        public void onSuccess(Void result) {
            log(result);
        }
    };

    private SendMessageListener mSendMessageListener = new SendMessageListener() {
        @Override
        public void onSuccess(Message result) {
            log(result);
            mRocketMethods.updateMessage(result.getId(), result.getRid(), "Hello mother fucker", mUpdateMessageListener);
        }
    };

    private SaveRoomNameListener mSaveRoomNameListener = new SaveRoomNameListener() {
        @Override
        public void onSuccess(String result) {
            log(result);
        }
    };
    private CreateChannelListener mCreateChannelListener = new CreateChannelListener() {
        @Override
        public void onSuccess(RoomId result) {
            log(result);
            mRocketMethods.addUserToRoom(result.getRid(), "cotta.bueno.cesar.julio", mAddUserToRoomListener);
            mRocketMethods.canAccessRoom(result.getRid(), mMeteor.getUserId(), mCanAccessRoomListener);
            mRocketMethods.saveRoomName(result.getRid(), "sweet-alice-" + System.currentTimeMillis(), mSaveRoomNameListener);
        }
    };

    private ArchiveRoomListener mArchiveRoomListener = new ArchiveRoomListener() {
        @Override
        public void onSuccess(List<Integer> result) {
            if (result.size() > 0) {
                log("archived one room, I guess");
            }
        }
    };

    private EraseRoomListener mEraseRoomListener = new EraseRoomListener() {
        @Override
        public void onSuccess(Integer result) {
            log(result);
        }
    };
    private ChannelsListListener mChannelsListListener = new ChannelsListListener() {
        @Override
        public void onSuccess(Channels result) {
            log(result);
            for (Channel c : result.getChannels()) {
                if (c.getName().contains("alice")) {
                    if (!c.isArchived()) {
                        mRocketMethods.archiveRoom(c.getId(), mArchiveRoomListener);
                    }
                }
                if (c.isArchived() || c.getName().startsWith("jul")) {
                    mRocketMethods.eraseRoom(c.getId(), mEraseRoomListener);
                }
                if (c.getName().equals("general")) {
                    mRocketMethods.sendMessage(c.getId(), "hello world!", mSendMessageListener);
                }
            }
            mRocketMethods.createChannel("alice" + System.currentTimeMillis(), mCreateChannelListener);
        }
    };

    private ResultListener mLoginListener = new LoginListener() {
        @Override
        public void onSuccess(Token result) {
            log(result);
            mRocketMethods.channelsList(mChannelsListListener);
            Subscription subscription = mRocketSubscriptions.userData(new SubscribeListener() {
                @Override
                public void onSuccess() {
                    log("success sub");
                }

                @Override
                public void onError(String error, String reason, String details) {
                    log("error: " + error + ", reason: " + reason + ", details: " + details);
                }
            });
            subscription.unSubscribe();
        }
    };

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
        }
        mRocketMethods = new RocketMethods(mMeteor);
        mRocketSubscriptions = new RocketSubscriptions(mMeteor);
    }

    @Override
    public void onConnect(final boolean signedInAutomatically) {
        if (signedInAutomatically) {

        } else {
            mRocketMethods.login("julio.cesar.bueno.cotta", "google", mLoginListener);
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

    private void log(Object object) {
        String txt = null;
        if (object instanceof String) {
            txt = (String) object;
        } else {
            try {
                txt = mMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
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
