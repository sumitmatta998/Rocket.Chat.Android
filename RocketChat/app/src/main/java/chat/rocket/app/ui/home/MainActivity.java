package chat.rocket.app.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.menu.FileListFragment;
import chat.rocket.app.ui.home.menu.MembersListFragment;
import chat.rocket.app.ui.home.menu.PinnedMessagesFragment;
import chat.rocket.app.ui.home.menu.RoomSettingsFragment;
import chat.rocket.app.ui.home.menu.SearchFragment;
import chat.rocket.app.ui.home.menu.StaredMessagesFragment;
import chat.rocket.app.ui.widgets.FabMenuLayout;
import chat.rocket.operations.Subscription;
import chat.rocket.operations.meteor.SubscribeListener;

public class MainActivity extends BaseActivity implements FabMenuLayout.MenuClickListener, HomeFragment.OnContentReady {
    private DrawerLayout mDrawerLayout;
    private FabMenuLayout mFabMenu;
    private View mContentView;
    private SubscribeListener mFilteredUsersListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };
    private Subscription mFilteredUsersSubscription;
    private SubscribeListener mChannelAutocompleteListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };
    private Subscription mChannelAutocompleteSubscription;
    private SubscribeListener mStreamMessagesListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };
    private Subscription mStreamMessagesSubscription;
    private Subscription mFullUserDataSubscription;
    private SubscribeListener mFullUserDataListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.content_home);
        mFabMenu = (FabMenuLayout) findViewById(R.id.FabMenu);
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
        } else if (!mFabMenu.onBackPressed()) {
            super.onBackPressed();
            endMeteorConnection();
        }
    }

    @Override
    public void onMenuItemClick(int id) {
        switch (id) {
            case R.id.SettingsButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new RoomSettingsFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.SearchButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new SearchFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.MembersButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new MembersListFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.FilesButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new FileListFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.StaredButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new StaredMessagesFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.PinnedButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new PinnedMessagesFragment()).commit();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    public void onTopViewReady(View topView) {
        mFabMenu.setTopView(topView);
        mFabMenu.setContentView(mContentView);
        mFabMenu.setMenuClickListener(this);
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

    }
}
