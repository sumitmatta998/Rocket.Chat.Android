package chat.rocket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by julio on 21/11/15.
 */
public class FileId {
    @JsonProperty("_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
