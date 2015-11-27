package chat.rocket.app.db.dao;

/**
 * Created by julio on 27/11/15.
 */
public class StreamNotifyRoom extends Stream {
    public static final String COLLECTION_NAME = "stream-notify-room";
    private String rid;
    private String action;
    private String username;
    private boolean happening;

    public void parseArgs() {
        if (args != null) {
            if (args.size() > 0) {
                String aux = args.get(0);
                String[] arr = aux.split("/");
                rid = arr[0];
                action = arr[1];
            }
            if (args.size() > 1) {
                username = args.get(1);
            }
            if (args.size() > 2) {
                happening = Boolean.valueOf(args.get(2));
            }
        }
    }

//{"msg":"added","collection":"stream-notify-room","id":"wC5AmbeYNpwniBxNr","fields":
// {"args":["GENERAL/typing","lol-3",true],"userId":"bNd8sHHAvuqrgj6SM","subscriptionId":"NucnY3eDxEbuFFQhs"}}
}
