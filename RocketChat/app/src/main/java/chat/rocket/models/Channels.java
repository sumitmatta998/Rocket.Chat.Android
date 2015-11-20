package chat.rocket.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by julio on 18/11/15.
 */
public class Channels {
    @JsonProperty("channels")
    private List<Channel> channels;

    public List<Channel> getChannels() {
        return channels;
    }
}
