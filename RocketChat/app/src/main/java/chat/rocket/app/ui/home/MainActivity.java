package chat.rocket.app.ui.home;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chat.rocket.app.R;
import chat.rocket.app.db.collections.RCSubscription;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.menu.FileListFragment;
import chat.rocket.app.ui.home.menu.MembersListFragment;
import chat.rocket.app.ui.home.menu.PinnedMessagesFragment;
import chat.rocket.app.ui.home.menu.RoomSettingsFragment;
import chat.rocket.app.ui.home.menu.SearchFragment;
import chat.rocket.app.ui.home.menu.StaredMessagesFragment;
import chat.rocket.app.ui.widgets.FabMenuLayout;
import chat.rocket.models.Message;
import chat.rocket.models.Messages;
import chat.rocket.operations.RocketSubscriptions;
import chat.rocket.operations.meteor.SubscribeListener;
import chat.rocket.operations.methods.listeners.LoadHistoryListener;

public class MainActivity extends BaseActivity implements FabMenuLayout.MenuClickListener {
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<Message> mAdapter;
    private ListView mListview;
    private FabMenuLayout mFabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListview = (ListView) findViewById(R.id.ListView);

        mFabMenu = (FabMenuLayout) findViewById(R.id.FabMenu);
        mFabMenu.setTopView(toolbar);
        mFabMenu.setContentView(mListview);
        mFabMenu.setMenuClickListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        RocketSubscriptions subs = new RocketSubscriptions();

        subs.filteredUsers(new SubscribeListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String error, String reason, String details) {

            }
        });
        mAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<>());
        mListview.setAdapter(mAdapter);

        // TODO: The right thing to do here is do not try to enter any room,
        // it may be the case where the query will fail because of the network speed
        List<RCSubscription> rcs = RCSubscription.getRCSubscriptions();
        if (!rcs.isEmpty()) {
            RCSubscription rcSubscription = rcs.get(0);
            getSupportActionBar().setTitle("Chat Room: " + rcSubscription.getName());

            mRocketMethods.loadHistory(rcSubscription.getRid(), null, 50, rcSubscription.getLs(), new LoadHistoryListener() {
                @Override
                public void onResult(Messages result) {
                    mAdapter.addAll(result.getMessages());
                }

                @Override
                public void onError(String error, String reason, String details) {
                }
            });
        } else {
            //TODO: this is a new user, he does not belong to any room
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
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
}
