package chat.rocket.app.db.collections;

/**
 * Created by julio on 27/11/15.
 */
public class StreamNotifyRoom extends Stream {
    public static final String COLLECTION_NAME = "stream-notify-room";
    private String rid;
    private String action;
    private String username;
    private boolean happening;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHappening() {
        return happening;
    }

    public void setHappening(boolean happening) {
        this.happening = happening;
    }

    public void parseArgs() {
        if (args != null) {
            if (args.size() > 0) {
                String aux = args.get(0).getAsString();
                String[] arr = aux.split("/");
                rid = arr[0];
                action = arr[1];
            }
            if (args.size() > 1) {
                username = args.get(1).getAsString();
            }
            if (args.size() > 2) {
                happening = Boolean.valueOf(args.get(2).getAsString());
            }
        }
    }
}
