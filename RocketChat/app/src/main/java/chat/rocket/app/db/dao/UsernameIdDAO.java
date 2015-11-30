package chat.rocket.app.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import chat.rocket.app.db.DBManager;
import chat.rocket.app.db.util.ContentValuables;
import chat.rocket.app.db.util.TableBuilder;
import chat.rocket.models.UsernameId;

import static chat.rocket.app.db.util.TableBuilder.CASCADE;
import static chat.rocket.app.db.util.TableBuilder.TEXT;

/**
 * Created by julio on 29/11/15.
 */
public class UsernameIdDAO extends UsernameId implements ContentValuables {
    public static final String TABLE_NAME = "username";

    public static final String COLUMN_MSG_ID = "msg_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_USERNAME_ID = "username_id";

    private String msgId;

    public UsernameIdDAO(Cursor cursor) {
        msgId = cursor.getString(cursor.getColumnIndex(COLUMN_MSG_ID));
        id = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME_ID));
        username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
    }

    public static String createTableString() throws Exception {
        return getCreateTableString(TABLE_NAME).toString();
    }

    protected static TableBuilder getCreateTableString(String tableName) throws Exception {
        TableBuilder tb = new TableBuilder(tableName);
        tb.setPrimaryKey(COLUMN_MSG_ID, TEXT, false);
        tb.addColumn(COLUMN_USERNAME, TEXT, true);
        tb.addColumn(COLUMN_USERNAME_ID, TEXT, true);
        //tb.addFK(COLUMN_MSG_ID, TEXT, MessageDAO.TABLE_NAME, MessageDAO.COLUMN_MSG_ID, CASCADE, CASCADE);
        return tb;
    }

    public UsernameIdDAO(String msgId, UsernameId usernameId) {
        super(usernameId.getId(), usernameId.getUsername());
        this.msgId = msgId;
    }

    public void insert() {
        DBManager.getInstance().insert(TABLE_NAME, this);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MSG_ID, msgId);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_USERNAME_ID, id);
        return values;
    }

    public static UsernameId get(String msgId) {
        Cursor cursor = DBManager.getInstance().query(TABLE_NAME, null, COLUMN_MSG_ID + "=?", new String[]{msgId});
        UsernameId usernameId = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    usernameId = new UsernameIdDAO(cursor);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return usernameId;
    }
}
