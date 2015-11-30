package chat.rocket.app.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.util.LruCache;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chat.rocket.app.db.dao.MessageDAO;
import chat.rocket.models.Message;

/**
 * Created by julio on 29/11/15.
 */
public class MessagesAdapter extends CursorAdapter {
    private LruCache<String, Message> mCache = new LruCache<>(10);

    public MessagesAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Message msg = new MessageDAO(cursor);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setText(msg.getMsg());
        text1.append("\nby:");
        text1.append(msg.getUsernameId().getUsername());

    }
}
