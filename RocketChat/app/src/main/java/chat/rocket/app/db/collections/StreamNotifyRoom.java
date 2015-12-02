package chat.rocket.app.db.collections;

import chat.rocket.app.enumerations.NotifyActionType;
import chat.rocket.models.NotifyRoom;

/**
 * Created by julio on 27/11/15.
 */
public class StreamNotifyRoom extends Stream {
    public static final String COLLECTION_NAME = "stream-notify-room";
    private NotifyRoom notifyRoom;

    public void parseArgs() {
        if (args != null) {
            notifyRoom = new NotifyRoom();
            if (args.size() > 0) {
                String aux = args.get(0).getAsString();
                String[] arr = aux.split("/");
                notifyRoom.setRid(arr[0]);
                notifyRoom.setAction(NotifyActionType.fromType(arr[1]));
            }
            if (args.size() > 1) {
                if (NotifyActionType.TYPING.equals(notifyRoom.getAction())) {
                    notifyRoom.setUsername(args.get(1).getAsString());
                } else if (NotifyActionType.DELETE_MESSAGE.equals(notifyRoom.getAction())) {
                    notifyRoom.setId(args.get(1).getAsJsonObject().get("_id").getAsString());
                }
            }
            if (args.size() > 2) {
                notifyRoom.setHappening(Boolean.valueOf(args.get(2).getAsString()));
            }
        }
    }

    public NotifyRoom getNotifyRoom() {
        return notifyRoom;
    }

}
