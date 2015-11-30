package chat.rocket.app.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chat.rocket.app.db.collections.RCSubscription;
import chat.rocket.app.ui.base.CollectionCursorAdapter;

/**
 * Created by julio on 29/11/15.
 */
public class RCSubscriptionAdapter extends CollectionCursorAdapter<RCSubscription> {
    public RCSubscriptionAdapter(Context context) {
        super(context);
    }

    @Override
    public View newView(Context context, RCSubscription item, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, RCSubscription item) {
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setText("#" + item.getName());
        if (item.hasAlert()) {
            text1.setTypeface(text1.getTypeface(), Typeface.DEFAULT_BOLD.getStyle());
        } else {
            text1.setTypeface(text1.getTypeface(), Typeface.NORMAL);
        }
    }
}
