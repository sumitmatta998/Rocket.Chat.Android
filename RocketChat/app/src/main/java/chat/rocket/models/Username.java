package chat.rocket.models;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by julio on 18/11/15.
 */
public class Username {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("username")
    private String username;
}
