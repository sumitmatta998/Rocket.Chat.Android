package chat.rocket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by julio on 19/11/15.
 */
public class Message {
    @JsonProperty("_id")
    private String id;
    private String msg;
    private String rid;
    private TimeStamp ts;
    @JsonProperty("t")
    private String type;
    @JsonProperty("u")
    private UsernameId usernameId;
    private FileId file;
    private List<UrlParts> urls;
    private TimeStamp editedAt;
    private EditedBy editedBy;
    private double score;

    public String toString(){
        return "id:"+id+", msg:"+msg+"\nby:"+usernameId.getUsername();
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileId getFile() {
        return file;
    }

    public void setFile(FileId file) {
        this.file = file;
    }

    public TimeStamp getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(TimeStamp editedAt) {
        this.editedAt = editedAt;
    }

    public EditedBy getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(EditedBy editedBy) {
        this.editedBy = editedBy;
    }

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

    public UsernameId getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(UsernameId usernameId) {
        this.usernameId = usernameId;
    }

    public List<UrlParts> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlParts> urls) {
        this.urls = urls;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
