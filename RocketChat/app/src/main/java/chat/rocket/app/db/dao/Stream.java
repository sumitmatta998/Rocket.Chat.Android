package chat.rocket.app.db.dao;

import java.util.List;

/**
 * Created by julio on 27/11/15.
 */
public abstract class Stream {
    protected List<String> args;
    protected String userId;
    protected String subscriptionId;

    public abstract void parseArgs();
}
