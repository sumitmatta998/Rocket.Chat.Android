package chat.rocket.app.enumerations;

import com.google.gson.annotations.SerializedName;

public enum NotifyActionType {
    @SerializedName("typing")
    TYPING("typing"),

    @SerializedName("deleteMessage")
    DELETE_MESSAGE("deleteMessage");

    private String type;

    NotifyActionType(String type) {

        this.type = type;
    }

    public static NotifyActionType fromType(String type) {
        for (NotifyActionType notifyActionType : values()) {
            if (notifyActionType.type.equals(type)) {
                return notifyActionType;
            }
        }
        return null;
    }
}
