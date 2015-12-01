package chat.rocket.models;

import java.io.Serializable;

/**
 * Created by julio on 01/12/15.
 */
//TODO: Migrate to Parceable
public class NotifyRoom implements Serializable {
    protected String rid;
    protected String action;
    protected String username;
    protected boolean happening;

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

}
