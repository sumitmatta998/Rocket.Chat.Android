package chat.rocket.app.ui.chat;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import chat.rocket.app.R;
import chat.rocket.app.db.DBContentProvider;
import chat.rocket.app.db.collections.RCSubscription;
import chat.rocket.app.db.collections.StreamNotifyRoom;
import chat.rocket.app.db.dao.CollectionDAO;
import chat.rocket.app.db.dao.MessageDAO;
import chat.rocket.app.ui.adapters.MessagesAdapter;
import chat.rocket.app.ui.base.BaseActivity;
import chat.rocket.app.utils.Util;
import chat.rocket.models.Message;
import chat.rocket.models.Messages;
import chat.rocket.operations.Subscription;
import chat.rocket.operations.meteor.SubscribeListener;
import chat.rocket.operations.methods.listeners.LoadHistoryListener;
import chat.rocket.operations.methods.listeners.ReadMessagesListener;
import chat.rocket.operations.methods.listeners.SendMessageListener;

/**
 * Created by julio on 29/11/15.
 */
public class ChatActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String RC_SUB = "sub";
    private static final int LOADER_ID = 3;
    private RCSubscription mRcSubscription;

    private LoadHistoryListener mLoadHistoryListener = new LoadHistoryListener() {
        @Override
        public void onResult(Messages result) {
            mRocketMethods.readMessages(mRcSubscription.getRid(), mReadMessagesListener);
            for (Message m : result.getMessages()) {
                MessageDAO msg = new MessageDAO(m);
                msg.insert();
            }
        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };

    private Subscription mRoomSubscription;
    private SubscribeListener mRoomListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };

    private Subscription mFullUserDataSubscription;
    private SubscribeListener mFullUserDataListener = new SubscribeListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };
    private ReadMessagesListener mReadMessagesListener = new ReadMessagesListener() {
        @Override
        public void onResult(Integer result) {

        }

        @Override
        public void onError(String error, String reason, String details) {

        }
    };
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
    private ListView mListView;
    private MessagesAdapter mAdapter;
    private SendMessageListener mSendMessageListener = new SendMessageListener() {
        @Override
        public void onResult(Message result) {
            Log.d("tste", result.toString());
            mSendEditText.getText().clear();
            mListView.smoothScrollToPosition(mAdapter.getCount() - 1);
        }

        @Override
        public void onError(String error, String reason, String details) {
            Log.d("tste", error);
        }
    };
    private EditText mSendEditText;
    private Handler mHandler = new Handler();
    private ContentObserver mObserver = new ContentObserver(mHandler) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            List<CollectionDAO> daos = CollectionDAO.query(StreamNotifyRoom.COLLECTION_NAME);
            if (daos != null && !daos.isEmpty()) {
                for (CollectionDAO dao : daos) {
                    StreamNotifyRoom notif = Util.GSON.fromJson(dao.getNewValuesJson(), StreamNotifyRoom.class);
                    notif.parseArgs();
                    if (mRcSubscription.getRid().equals(notif.getRid())) {
                        if (notif.isHappening()) {
                            getSupportActionBar().setSubtitle(notif.getUsername() + " is " + notif.getAction());
                        } else {
                            getSupportActionBar().setSubtitle("");
                        }
                        break;
                    }
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = DBContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(CollectionDAO.TABLE_NAME).
                appendQueryParameter(DBContentProvider.FILTER, StreamNotifyRoom.COLLECTION_NAME).build();
        getContentResolver().
                registerContentObserver(
                        uri,
                        true,
                        mObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRcSubscription = (RCSubscription) getIntent().getSerializableExtra(RC_SUB);

        mListView = (ListView) findViewById(R.id.listview);
        mSendEditText = (EditText) findViewById(R.id.submitEditText);

        mSendEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mRocketMethods.sendMessage(mRcSubscription.getRid(), mSendEditText.getText().toString(), mSendMessageListener);
                return true;
            }
            return false;
        });

        mAdapter = new MessagesAdapter(this);
        mListView.setAdapter(mAdapter);

        mRocketMethods.loadHistory(mRcSubscription.getRid(), null, mRcSubscription.getUnread(), mRcSubscription.getLs(), mLoadHistoryListener);

        //TODO: why that number 1 ??? is it the current number of users in the room?
        mFullUserDataSubscription = mRocketSubscriptions.fullUserData(null, 1, mFullUserDataListener);

        mRoomSubscription = mRocketSubscriptions.room(mRcSubscription.getName(), mRcSubscription.getType(), mRoomListener);

        mFilteredUsersSubscription = mRocketSubscriptions.filteredUsers(mFilteredUsersListener);

        mChannelAutocompleteSubscription = mRocketSubscriptions.channelAutocomplete(mChannelAutocompleteListener);

        mStreamMessagesSubscription = mRocketSubscriptions.streamMessages(mStreamMessagesListener);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("#" + mRcSubscription.getName());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRoomSubscription != null) {
            mRoomSubscription.unSubscribe();
        }

        if (mFullUserDataSubscription != null) {
            mFullUserDataSubscription.unSubscribe();
        }

        if (mFilteredUsersSubscription != null) {
            mFilteredUsersSubscription.unSubscribe();
        }

        if (mChannelAutocompleteSubscription != null) {
            mChannelAutocompleteSubscription.unSubscribe();
        }

        if (mStreamMessagesSubscription != null) {
            mStreamMessagesSubscription.unSubscribe();
        }
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
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
