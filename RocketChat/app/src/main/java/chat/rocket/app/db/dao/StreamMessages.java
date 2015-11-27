package chat.rocket.app.db.dao;

import chat.rocket.app.utils.Util;
import chat.rocket.models.Message;

/**
 * Created by julio on 27/11/15.
 */
public class StreamMessages extends Stream {
    public static final String COLLECTION_NAME = "stream-messages";
    private String rid;
    private Message msg;

    @Override
    public void parseArgs() {
        if (args != null) {
            if (args.size() > 0) {
                rid = args.get(0);
            }

            if (args.size() > 1) {
                msg = Util.GSON.fromJson(args.get(1), Message.class);
            }
        }
    }

    //{"msg":"added","collection":"stream-messages","id":"d7TNgB68PdyEuzb58","fields":
    // {"args":["GENERAL",{"rid":"GENERAL","msg":"hi","ts":{"$date":1448592859773},
    // "u":{"_id":"bNd8sHHAvuqrgj6SM","username":"lol-3"},"_id":"pBjgkaJrbM38Jr56Y"}],"userId":null,"subscriptionId":null}}
}
