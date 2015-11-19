package chat.rocket.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by julio on 18/11/15.
 */
public class User {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("username")
    private String username;
}
