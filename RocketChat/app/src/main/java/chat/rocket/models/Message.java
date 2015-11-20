package chat.rocket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by julio on 19/11/15.
 */
public class Message {
    @JsonProperty("_id")
    private String id;
    private String msg;
    private String rid;
    private TimeStamp ts;
    @JsonProperty("u")
    private Username username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public TimeStamp getTs() {
        return ts;
    }

    public void setTs(TimeStamp ts) {
        this.ts = ts;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }
}
