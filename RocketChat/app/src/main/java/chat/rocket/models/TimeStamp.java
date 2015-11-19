package chat.rocket.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by julio on 18/11/15.
 */
public class TimeStamp {
    @JsonProperty("$date")
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
