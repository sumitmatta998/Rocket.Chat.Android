package chat.rocket.app.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import chat.rocket.app.BuildConfig;
import chat.rocket.app.R;
import chat.rocket.app.db.collections.StreamNotifyRoom;
import chat.rocket.app.db.dao.MessageDAO;
import chat.rocket.app.ui.adapters.MessagesAdapter;
import chat.rocket.app.ui.base.AudioRecordActivity;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.ui.home.menu.FileListFragment;
import chat.rocket.app.ui.home.menu.MembersListFragment;
import chat.rocket.app.ui.home.menu.PinnedMessagesFragment;
import chat.rocket.app.ui.home.menu.RoomSettingsFragment;
import chat.rocket.app.ui.home.menu.SearchFragment;
import chat.rocket.app.ui.home.menu.StaredMessagesFragment;
import chat.rocket.app.ui.widgets.FabMenuLayout;
import chat.rocket.models.NotifyRoom;
import chat.rocket.models.RCSubscription;
import chat.rocket.rc.models.Message;
import io.fabric.sdk.android.services.network.HttpRequest;
import meteor.operations.MeteorException;
import meteor.operations.Protocol;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julio on 29/11/15.
 */
public class ChatActivity extends BaseActivity implements FabMenuLayout.MenuClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String RC_SUB = "sub";
    private static final int LOADER_ID = 3;
    private static final int RECORD_AUDIO_REQUEST_CODE = 123;
    private RCSubscription mRcSubscription;
    private FabMenuLayout mFabMenu;

    private ListView mListView;
    private MessagesAdapter mAdapter;

    private EditText mSendEditText;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotifyRoom notif = (NotifyRoom) intent.getSerializableExtra(StreamNotifyRoom.COLLECTION_NAME);
            if (mRcSubscription.getRid().equals(notif.getRid())) {
                if (notif.isHappening()) {
                    getSupportActionBar().setSubtitle(notif.getUsername() + " is " + notif.getAction());
                } else {
                    getSupportActionBar().setSubtitle("");
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(StreamNotifyRoom.COLLECTION_NAME + mRcSubscription.getRid());
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRcSubscription = (RCSubscription) getIntent().getSerializableExtra(RC_SUB);
        mFabMenu = (FabMenuLayout) findViewById(R.id.FabMenu);
        mListView = (ListView) findViewById(R.id.listview);
        mSendEditText = (EditText) findViewById(R.id.submitEditText);
        mFabMenu.setTopView(mListView);
        mFabMenu.setContentView(mListView);
        mFabMenu.setMenuClickListener(this);
        mSendEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mRxRocketMethods.sendMessage(mRcSubscription.getRid(), mSendEditText.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(message -> {
                            mSendEditText.getText().clear();
                            mListView.smoothScrollToPosition(mAdapter.getCount() - 1);
                        });
                return true;
            }
            return false;
        });

        mAdapter = new MessagesAdapter(this);
        mListView.setAdapter(mAdapter);
        int unread = mRcSubscription.getUnread();
        if (unread == 0) {
            unread = 25;
        }
        mRxRocketMethods.loadHistory(mRcSubscription.getRid(), null, unread, mRcSubscription.getLs())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages -> {
                    mRxRocketMethods.readMessages(mRcSubscription.getRid())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                    for (Message m : messages.getMessages()) {
                        MessageDAO msg = new MessageDAO(m);
                        msg.insert();
                    }
                });

        mRxRocketSubscriptions.room(mRcSubscription.getName(), mRcSubscription.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mRcSubscription.getFormattedName());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(AudioRecordActivity.FILE_PATH);
            File file = new File(filePath);
            if (file.exists()) {
                String str = decodeFile(file);
                if (str != null) {
                    String[] parts = str.split("(?<=\\G.{4})");
                    processUpload(file.getName(), file.length(), parts);
                }
            }
        }
    }

    private void processUpload(String name, long size, String[] parts) {
        mRxRocketMethods.uploadFile("https://" + BuildConfig.WS_HOST, mRxMeteor.getUserId(),
                mRcSubscription.getRid(), name, parts, "audio/3gp", "3gp", size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Float>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Protocol.Error err = ((MeteorException) e).getError();
                        String error = err.getError();
                        String reason = err.getReason();
                        String details = err.getDetails();
                        Log.d("upload - onError", error + ", " + reason + ", " + details);
                    }

                    @Override
                    public void onNext(Float progress) {
                        Log.d("upload - onProgress", progress + "%");
                    }
                });

    }

    private String decodeFile(File file) {
        String fileStr = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf))) {
                baos.write(buf, 0, n);
            }
            fileStr = HttpRequest.Base64.encodeBytes(baos.toByteArray());

        } catch (Exception e) {
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return fileStr;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return MessageDAO.getLoader(mRcSubscription.getRid());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (!mFabMenu.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onMenuItemClick(int id) {
        switch (id) {
            case R.id.SettingsButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new RoomSettingsFragment()).commit();
                break;
            case R.id.SearchButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new SearchFragment()).commit();
                break;
            case R.id.MembersButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new MembersListFragment()).commit();
                break;
            case R.id.FilesButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new FileListFragment()).commit();
                break;
            case R.id.StaredButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new StaredMessagesFragment()).commit();
                break;
            case R.id.PinnedButton:
                getSupportFragmentManager().beginTransaction().replace(R.id.MenuContentLayout, new PinnedMessagesFragment()).commit();
                break;
            case R.id.MicButton:
                //TODO: migrate code to fragment and insert below the submitEditText..
                Intent intent = new Intent(ChatActivity.this, AudioRecordActivity.class);
                startActivityForResult(intent, RECORD_AUDIO_REQUEST_CODE);
                break;

        }
    }

}
