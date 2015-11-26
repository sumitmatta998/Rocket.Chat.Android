package chat.rocket.app.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import chat.rocket.app.R;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.menu.FileListFragment;
import chat.rocket.app.ui.home.menu.MembersListFragment;
import chat.rocket.app.ui.home.menu.PinnedMessagesFragment;
import chat.rocket.app.ui.home.menu.RoomSettingsFragment;
import chat.rocket.app.ui.home.menu.SearchFragment;
import chat.rocket.app.ui.home.menu.StaredMessagesFragment;
import chat.rocket.models.Message;
import chat.rocket.models.Messages;
import chat.rocket.models.TimeStamp;
import chat.rocket.operations.methods.listeners.LoadHistoryListener;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<Message> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat Room: GENERAL");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.LeftNavView);
        navigationView.setNavigationItemSelectedListener(this);
        ListView listview = (ListView) findViewById(R.id.RoomMsgsListView);
        mAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<>());
        listview.setAdapter(mAdapter);
        mRocketMethods.loadHistory("GENERAL", null, 50, new TimeStamp(0), new LoadHistoryListener() {
            @Override
            public void onResult(Messages result) {
                mAdapter.addAll(result.getMessages());
            }

            @Override
            public void onError(String error, String reason, String details) {
            }
        });

        findViewById(R.id.SettingsButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new RoomSettingsFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });
        findViewById(R.id.SearchButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new SearchFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });

        findViewById(R.id.MembersButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new MembersListFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });

        findViewById(R.id.FilesButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new FileListFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });

        findViewById(R.id.StaredButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new StaredMessagesFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });

        findViewById(R.id.PinnedButton).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.RightNavView, new PinnedMessagesFragment()).commit();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            endMeteorConnection();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return false;
    }

}
