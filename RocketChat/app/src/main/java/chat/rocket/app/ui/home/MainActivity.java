package chat.rocket.app.ui.home;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.rc.listeners.LogListener;
import meteor.operations.ResultListener;
import meteor.operations.Subscription;

public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;

    private ResultListener mFilteredUsersListener = new LogListener();
    private Subscription mFilteredUsersSubscription;

    private ResultListener mChannelAutocompleteListener = new LogListener();
    private Subscription mChannelAutocompleteSubscription;

    private ResultListener mStreamMessagesListener = new LogListener();
    private Subscription mStreamMessagesSubscription;

    private ResultListener mFullUserDataListener = new LogListener();
    private Subscription mFullUserDataSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home, new HomeFragment()).commit();
        }

        //TODO: why that number 1 ??? is it the current number of users in the room?
        mFullUserDataSubscription = mRocketSubscriptions.fullUserData(null, 1, mFullUserDataListener);
        mFilteredUsersSubscription = mRocketSubscriptions.filteredUsers(mFilteredUsersListener);
        mChannelAutocompleteSubscription = mRocketSubscriptions.channelAutocomplete(mChannelAutocompleteListener);
        mStreamMessagesSubscription = mRocketSubscriptions.streamMessages(mStreamMessagesListener);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFilteredUsersSubscription != null) {
            mFilteredUsersSubscription.unSubscribe();
        }

        if (mChannelAutocompleteSubscription != null) {
            mChannelAutocompleteSubscription.unSubscribe();
        }

        if (mStreamMessagesSubscription != null) {
            mStreamMessagesSubscription.unSubscribe();
        }

        if (mFullUserDataSubscription != null) {
            mFullUserDataSubscription.unSubscribe();
        }
        if (isFinishing()) {
            endMeteorConnection();
        }

    }
}
