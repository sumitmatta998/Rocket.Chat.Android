package chat.rocket.app.ui.home;

import android.os.Bundle;
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

public class MainActivity extends BaseActivity implements FabMenuLayout.MenuClickListener, HomeFragment.OnContentReady {
    private DrawerLayout mDrawerLayout;
    private FabMenuLayout mFabMenu;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        mContentView = findViewById(R.id.content_home);
        mFabMenu = (FabMenuLayout) findViewById(R.id.FabMenu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home, new HomeFragment()).commit();
        }
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
}
