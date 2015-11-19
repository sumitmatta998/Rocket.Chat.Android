package chat.rocket.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by julio on 18/11/15.
 */
public class Channel {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("ts")
    private TimeStamp ts;
    @JsonProperty("t")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("usernames")
    private List<String> usernames;
    private long msgs;
    @JsonProperty("u")
    private User user;
    @JsonProperty("default")
    private boolean defaultRoom;
    private TimeStamp lm;

    public TimeStamp getTs() {
        return ts;
    }

    public void setTs(TimeStamp ts) {
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public long getMsgs() {
        return msgs;
    }

    public void setMsgs(long msgs) {
        this.msgs = msgs;
    }

    public boolean isDefaultRoom() {
        return defaultRoom;
    }

    public void setDefaultRoom(boolean defaultRoom) {
        this.defaultRoom = defaultRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TimeStamp getLm() {
        return lm;
    }

    public void setLm(TimeStamp lm) {
        this.lm = lm;
    }
}
