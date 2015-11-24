package chat.rocket.models;


import com.google.gson.annotations.SerializedName;

/**
 * Created by julio on 18/11/15.
 */
public class TimeStamp {
    @SerializedName("$date")
    private long date;

    public TimeStamp(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
