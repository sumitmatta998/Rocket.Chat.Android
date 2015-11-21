package chat.rocket.models;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by julio on 18/11/15.
 */
public class UsernameId {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("username")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
