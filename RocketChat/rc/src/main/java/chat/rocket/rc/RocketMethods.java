package chat.rocket.rc;

import java.util.HashMap;
import java.util.Map;

import chat.rocket.rc.listeners.AddUserToRoomListener;
import chat.rocket.rc.listeners.ArchiveRoomListener;
import chat.rocket.rc.listeners.CanAccessRoomListener;
import chat.rocket.rc.listeners.ChannelsListListener;
import chat.rocket.rc.listeners.CreateChannelListener;
import chat.rocket.rc.listeners.CreateDirectMessageListener;
import chat.rocket.rc.listeners.CreatePrivateGroupListener;
import chat.rocket.rc.listeners.DeleteMessageListener;
import chat.rocket.rc.listeners.EraseRoomListener;
import chat.rocket.rc.listeners.FileUploadListener;
import chat.rocket.rc.listeners.GetTotalChannelsListener;
import chat.rocket.rc.listeners.HideRoomListener;
import chat.rocket.rc.listeners.JoinRoomListener;
import chat.rocket.rc.listeners.LeaveRoomListener;
import chat.rocket.rc.listeners.LoadHistoryListener;
import chat.rocket.rc.listeners.LoginListener;
import chat.rocket.rc.listeners.OpenRoomListener;
import chat.rocket.rc.listeners.ReadMessagesListener;
import chat.rocket.rc.listeners.ResetAvatarListener;
import chat.rocket.rc.listeners.SaveRoomNameListener;
import chat.rocket.rc.listeners.SendConfirmationEmailListener;
import chat.rocket.rc.listeners.SendForgotPasswordEmailListener;
import chat.rocket.rc.listeners.SendMessageListener;
import chat.rocket.rc.listeners.UpdateMessageListener;
import chat.rocket.rc.models.Message;
import chat.rocket.rc.models.TimeStamp;
import meteor.operations.Meteor;
import meteor.operations.MeteorException;
import meteor.operations.ResultListener;

/**
 * Created by julio on 19/11/15.
 */
public class RocketMethods {
    private Meteor mMeteor;

    public RocketMethods(Meteor meteor) {
        mMeteor = meteor;
    }

    public void addUserToRoom(String rid, String username, AddUserToRoomListener listener) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("rid", rid);
        data.put("username", username);
        mMeteor.call("addUserToRoom", new Object[]{data}, listener);
    }

    public void archiveRoom(String rid, ArchiveRoomListener listener) {
        mMeteor.call("archiveRoom", new Object[]{rid}, listener);
    }

    public void canAccessRoom(String rid, String userId, CanAccessRoomListener listener) {
        mMeteor.call("canAccessRoom", new Object[]{rid, userId}, listener);
    }

    public void channelsList(ChannelsListListener listener) {
        mMeteor.call("channelsList", listener);
    }

    public void createChannel(String name, CreateChannelListener listener) {
        mMeteor.call("createChannel", new Object[]{name, new short[]{}}, listener);
    }

    public void createDirectMessage(String username, CreateDirectMessageListener listener) {
        mMeteor.call("createDirectMessage", new Object[]{username}, listener);
    }

    public void createPrivateGroup(String name, CreatePrivateGroupListener listener) {
        mMeteor.call("createPrivateGroup", new Object[]{name, new short[]{}}, listener);
    }

    public void deleteMessage(String id, DeleteMessageListener listener) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("_id", id);
        mMeteor.call("DELETE_MESSAGE", new Object[]{data}, listener);
    }

    public void deleteUser(String userId, ResultListener listener) {
        mMeteor.call("deleteUser", new Object[]{userId}, listener);
    }

    public void eraseRoom(String rid, EraseRoomListener listener) {
        mMeteor.call("eraseRoom", new Object[]{rid}, listener);
    }

    public void getAvatarSuggestion(ResultListener listener) {
        //TODO: How does it work???
    }

    public void getRoomIdByNameOrId(String rid, ResultListener listener) {
        mMeteor.call("getRoomIdByNameOrId", new Object[]{rid}, listener);
    }

    public void getTotalChannels(GetTotalChannelsListener listener) {
        mMeteor.call("getTotalChannels", listener);
    }

    public void getUsernameSuggestion(ResultListener listener) {
        mMeteor.call("getUsernameSuggestion", listener);
    }

    public void hideRoom(String rid, HideRoomListener listener) {
        mMeteor.call("hideRoom", new Object[]{rid}, listener);
    }

    public void joinRoom(String rid, JoinRoomListener listener) {
        mMeteor.call("joinRoom", new Object[]{rid}, listener);
    }

    public void leaveRoom(String rid, LeaveRoomListener listener) {
        mMeteor.call("leaveRoom", new Object[]{rid}, listener);
    }

    public void loadHistory(String rid, TimeStamp end, int limit, TimeStamp ls, LoadHistoryListener listener) {
        mMeteor.call("loadHistory", new Object[]{rid, end, limit, ls}, listener);
    }

    public void loadLocale(String locale, ResultListener listener) {
        //TODO: It loaded some js, is it a bug or by design? need to ask..
        mMeteor.call("loadLocale", new Object[]{locale}, listener);
    }

    public void loadMissedMessages(String rid, long start, ResultListener listener) {
        mMeteor.call("loadMissedMessages", new Object[]{rid, start}, listener);
    }

    public void loginWithUsername(String username, String pass, LoginListener listener) {
        mMeteor.loginWithUsername(username, pass, listener);
    }

    public void loginWithEmail(String email, String pass, LoginListener listener) {
        mMeteor.loginWithEmail(email, pass, listener);
    }

    public void logoutCleanUp(ResultListener listener) {
        //TODO: How does it work???
    }

    public void messageSearch(String rid, String text, ResultListener listener) {
        mMeteor.call("messageSearch", new Object[]{text, rid}, listener);
    }

    public void migrate(ResultListener listener) {
        //TODO: How does it work???
    }

    public void openRoom(String rid, OpenRoomListener listener) {
        mMeteor.call("openRoom", new Object[]{rid}, listener);
    }

    public void readMessages(String rid, ReadMessagesListener listener) {
        mMeteor.call("readMessages", new Object[]{rid}, listener);
    }

    public void registerUser(String name, String email, String pass, ResultListener listener) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("email", email);
        data.put("pass", pass);
        mMeteor.call("registerUser", new Object[]{data}, listener);
    }

    public void removeUserFromRoom(String rid, String username, ResultListener listener) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("rid", rid);
        data.put("username", username);
        mMeteor.call("removeUserFromRoom", new Object[]{data}, listener);
    }

    public void reportMessage(Message message, String description, ResultListener listener) {
        mMeteor.call("removeUserFromRoom", new Object[]{message, description}, listener);
    }

    public void resetAvatar(ResetAvatarListener listener) {
        mMeteor.call("resetAvatar", listener);
    }

    public void saveRoomName(String rid, String name, SaveRoomNameListener listener) {
        mMeteor.call("saveRoomName", new Object[]{rid, name}, listener);
    }

    public void saveUserPreferences(boolean disableNewRoomNotification,
                                    boolean disableNewMessageNotification,
                                    boolean useEmojis,
                                    boolean convertAsciiEmoji,
                                    boolean saveMobileBandwidth,
                                    boolean compactView,
                                    boolean unreadRoomsMode,
                                    boolean autoImageLoad,
                                    ResultListener listener) {

        Map<String, String> data = new HashMap<String, String>();

        data.put("disableNewRoomNotification", booleantoString(disableNewRoomNotification));
        data.put("disableNewMessageNotification", booleantoString(disableNewMessageNotification));
        data.put("useEmojis", booleantoString(useEmojis));
        data.put("convertAsciiEmoji", booleantoString(convertAsciiEmoji));
        data.put("saveMobileBandwidth", booleantoString(saveMobileBandwidth));
        data.put("compactView", booleantoString(compactView));
        data.put("unreadRoomsMode", booleantoString(unreadRoomsMode));
        data.put("autoImageLoad", booleantoString(autoImageLoad));

        mMeteor.call("saveUserPreferences", new Object[]{data}, listener);
    }

    private String booleantoString(boolean b) {
        return b ? "1" : "0";
    }

    public void setUsername(String username, ResultListener listener) {
        mMeteor.call("setUsername", new Object[]{username}, listener);
    }

    public void saveUserProfile(String language, String realname, String username, ResultListener listener) {
        Map<String, String> data = new HashMap<String, String>();

        data.put("language", language);
        data.put("realname", realname);
        data.put("username", username);

        mMeteor.call("saveUserProfile", new Object[]{data}, listener);
    }

    public void sendConfirmationEmail(String email, SendConfirmationEmailListener listener) {
        mMeteor.call("sendConfirmationEmail", new Object[]{email}, listener);
    }

    public void sendForgotPasswordEmail(String email, SendForgotPasswordEmailListener listener) {
        mMeteor.call("sendForgotPasswordEmail", new Object[]{email}, listener);
    }

    public void sendMessage(String rid, String msg, SendMessageListener listener) {
        Map<String, String> data = new HashMap<String, String>();

        data.put("rid", rid);
        data.put("msg", msg);

        mMeteor.call("sendMessage", new Object[]{data}, listener);
    }

    public void setAvatarFromService(ResultListener listener) {
        //TODO: How does it work???
    }

    public void setUserActiveStatus(String userId, String active, ResultListener listener) {
        //TODO: Check what are the allowed values to active parameter
        mMeteor.call("setUserActiveStatus", new Object[]{userId, active}, listener);
    }

    public void toogleFavorite(ResultListener listener) {
        //TODO: How does it work???
    }

    public void unarchiveRoom(String rid, ResultListener listener) {
        mMeteor.call("unarchiveRoom", new Object[]{rid}, listener);
    }

    public void updateMessage(String msgId, String rid, String msg, UpdateMessageListener listener) {
        Map<String, String> data = new HashMap<String, String>();

        data.put("_id", msgId);
        data.put("rid", rid);
        data.put("msg", msg);

        mMeteor.call("updateMessage", new Object[]{data}, listener);
    }

    public void updateUserUtcOffset(ResultListener listener) {
        //TODO: How does it work???
    }

    public void loginWithOAuth(String credentialToken, String credentialSecret, final ResultListener listener) {
        final Map<String, Object> userData = new HashMap<String, Object>();

        userData.put("credentialToken", credentialToken);
        userData.put("credentialSecret", credentialSecret);

        final Map<String, Object> authData = new HashMap<String, Object>();
        authData.put("oauth", userData);

        mMeteor.call("login", new Object[]{authData}, listener);

    }

    public void loginWithFacebook(String accessToken, long expiresIn, final LoginListener listener) {
        final Map<String, Object> userData = new HashMap<String, Object>();

        userData.put("accessToken", accessToken);
        userData.put("expiresIn", expiresIn);

        final Map<String, Object> authData = new HashMap<String, Object>();
        authData.put("authResponse", userData);
        authData.put("cordova", "true");

        mMeteor.call("login", new Object[]{authData}, listener);

    }

    public void setPushUser(ResultListener listener) {
        mMeteor.call("raix:push-setuser", new Object[]{mMeteor.getUserId()}, listener);
    }

    public void updatePush(String token, ResultListener listener) {
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("gcm", token);

        map.put("token", tokenMap);
        map.put("appName", "main");
        map.put("userId", mMeteor.getUserId());

        mMeteor.call("raix:push-update", new Object[]{map}, listener);
    }

    public void UserPresence(String status, String presence, ResultListener listener) {
        mMeteor.call("UserPresence:" + status, new Object[]{presence}, listener);
    }

    public void uploadFile(final String host, final String userId, final String rid,
                           final String name, final String[] strings,
                           final String type, final String extension,
                           final long size, final FileUploadListener listener) {
        final String store = "rocketchat_uploads";
        //TODO: What is this id?
        final String id = mMeteor.uniqueID().replace("-", "").substring(0, 17);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", name);
        data.put("size", size);
        data.put("type", type);
        data.put("rid", rid);
        data.put("_id", id);
        data.put("complete", false);
        data.put("uploading", true);
        data.put("store", store);
        data.put("extension", extension);
        data.put("userId", userId);
        final float length = strings.length;
        final ResultListener ufsCompletelistener = new ResultListener() {
            @Override
            public void onSuccess(String result) {

                String msg = "*File Uploaded*: " + name + "\n" + host + "/ufs/rocketchat_uploads/" + id;
                Map<String, Object> data = new HashMap<String, Object>();

                data.put("rid", rid);
                data.put("msg", msg);

                Map<String, String> file = new HashMap<String, String>();
                file.put("_id", id);
                data.put("file", file);

                mMeteor.call("sendMessage", new Object[]{data}, listener);

            }

            @Override
            public void onError(MeteorException e) {
                listener.onError(e);
            }
        };
        ResultListener ufsWriterListener = new ResultListener() {
            int index = 0;

            @Override
            public void onSuccess(String result) {
                if (index < length) {
                    listener.onProgress(index * 1.0f / length);
                    ufsWrite(strings[index], id, store, this);
                    index = index + 1;
                } else {
                    ufsComplete(id, store, ufsCompletelistener);
                }
            }

            @Override
            public void onError(MeteorException e) {
                listener.onError(e);
            }
        };

        mMeteor.insert(store, mMeteor.uniqueID().replace("-", "").substring(0, 17), data, ufsWriterListener);
    }

    public void ufsWrite(String binary, String id, String store, ResultListener listener) {
        Map<String, Object> binaryMap = new HashMap<String, Object>();
        binaryMap.put("$binary", binary);
        mMeteor.call("ufsWrite", new Object[]{binaryMap, id, store}, listener);
    }

    public void ufsComplete(String id, String store, ResultListener listener) {
        mMeteor.call("ufsComplete", new Object[]{id, store}, listener);
    }


    //TODO: understand this message
    //["{\"msg\":\"method\",\"method\":\"/rocketchat_uploads/insert\",\"params\":[{\"name\":\"Audio record\",\"size\":491564,\"type\":\"audio/wav\",\"rid\":\"GENERAL\",\"complete\":false,\"uploading\":true,\"store\":\"rocketchat_uploads\",\"extension\":\"\",\"userId\":\"dcCYAPydG7jxSbFC5\"}],\"id\":\"32\",\"randomSeed\":\"b2964a940974ec3cb4e2\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"UklGRiSABwBXQVZFZm10IBAAAAABAAIARKwAABCxAgAEABAAZGF0YQCABwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAQD8//z/CgAKAO3/7f8gACAA0v/S/z0APQC3/7f/TwBPALv/u/8Y+Bj4kvaS9jj3OPdg9mD2jvaO9hP2E/YM9gz2sfWx9Zn1mfVZ9Vn1IvUi9QL1AvXA9MD0lvSW9HL0cvQ09DT0FPQU9Ovz6/Oy87LzmvOa827zbvM/8z/zKPMo8/ny+fLU8tTyuvK68onyifJv8m/yUPJQ8iPyI/IN8g3y6fHp8cLxwvGv8a/xh/GH8WnxafFW8VbxLfEt8RjxGPEA8QDx2fDZ8MnwyfCt8K3wjPCM8IDwgPBg8GDwR/BH8DrwOvAY8BjwBvAG8Pbv9u/W79bvzO/M77jvuO+c75zvlu+W733vfe9p72nvZO9k70nvSe8+7z7vNe817xvvG+8W7xbvCe8J7/Pu8+707vTu4+7j7tTu1O7W7tbuwu7C7r3uve697r3uqu6q7q3ure6p7qnumu6a7qLuou6Z7pnuke6R7prumu6O7o7ukO6Q7pjumO6N7o3ul+6X7pzunO6V7pXupu6m7qbupu6m7qbuuO647rTutO6+7r7u0O7Q7s3uze7f7t/u7e7t7u7u7u4H7wfvEe8R7xrvGu837zfvPe8970/vT+9r72vvcO9w74vvi++j76PvrO+s78/vz+/j7+Pv9O/07xvwG/Aq8CrwRvBG8GzwbPB78HvwoPCg8MLwwvDV8NXwAvEC8R7xHvE48TjxaPFo8X7xfvGj8aPx0/HT8evx6/Eb8hvyRvJG8mDyYPKW8pbyvPK88t/y3/IZ8xnzOPM482TzZPOc85zzu/O78/Pz8/Mm9Cb0R/RH9IX0hfSy9LL03PTc9B/1H/VG9Ub1evV69bn1ufXd9d31HPYc9lb2VvZ89nz2wvbC9vL28vYg9yD3afdp95P3k/fN9833EvgS+Dr4Ovh9+H34u/i7+OX45fgw+TD5Zfll+Zf5l/nj+eP5DvoO+kr6SvqS+pL6u/q7+gL7AvtB+0H7avtq+7f7t/vt++37H/wf/G38bfyY/Jj80vzS/Bv9G/1C/UL9if2J/cn9yf3x/fH9PP48/m7+bv6d/p3+6v7q/hP/E/9M/0z/k/+T/7X/tf/4//j/MwAzAFcAVwCgAKAAzgDOAPcA9wA/AT8BYgFiAZYBlgHYAdgB9AH0ATICMgJoAmgChAKEAscCxwLvAu8CEQMRA1QDVANwA3ADnQOdA9kD2QPsA+wDIgQiBFEEUQRlBGUEogSiBMIEwgTbBNsEFgUWBSkFKQVNBU0FggWCBYwFjAW5BbkF3wXfBekF6QUdBh0GNAY0BkQGRAZ4BngGgQaBBpsGmwbHBscGyAbIBuwG7AYKBwoHCgcKBzQHNAdCB0IHSAdIB3IHcgdzB3MHgweDB6cHpwefB58HuQe5B9AH0AfGB8YH6AfoB+8H7wfqB+oHDAgMCAQIBAgKCAoIJggmCBYIFggoCCgIOAg4CCYIJghACEAIQAhACDMIMwhNCE0IPgg+CDwIPAhSCFIIOwg7CEUIRQhPCE8INQg1CEcIRwhBCEEILgguCEIIQggvCC8IJggmCDcINwgaCBoIHQgdCCAIIAj/B/8HDAgMCAYIBgj0B/QHDQgNCAAIAAj4B/gHDAgMCPIH8gf3B/cHAggCCOcH5wf3B/cH9Af0B90H3QfxB/EH3wffB9EH0QfiB+IHxwfHB8cHxwfPB88HsAewB7oHugezB7MHmAeYB6gHqAeVB5UHhAeEB5IHkgdzB3MHbQdtB3MHcwdRB1EHWAdYB1AHUAcxBzEHPAc8ByYHJgcRBxEHHQcdB/4G/gb1BvUG+Qb5BtYG1gbYBtgG0AbQBq4Grga2BrYGoQahBokGiQaTBpMGcgZyBmUGZQZoBmgGQwZDBkMGQwY6BjoGFgYWBhwGHAYHBgcG7AXsBfUF9QXVBdUFxgXGBckFyQWkBaQFoQWhBZkFmQV0BXQFeAV4BWQFZAVHBUcFTwVPBS8FLwUdBR0FIAUgBfsE+wT3BPcE7wTvBMoEygTOBM4EugS6BJ0EnQSkBKQEhgSGBHMEcwR2BHYEUQRRBEsESwRGBEYEIQQhBCMEIwQQBBAE8gPyA/kD+QPdA90DyQPJA80DzQOpA6kDoQOhA50DnQN6A3oDfQN9A20DbQNOA04DVANUAzkDOQMlAyUDKgMqAwgDCAMAAwAD/QL9AtoC2gLcAtwCzwLPArECsQK4ArgCoAKgAosCiwKSApICcgJyAmoCagJqAmoCSAJIAkoCSgI+Aj4CIAIgAigCKAISAhIC/QH9AQUCBQLoAegB3wHfAeIB4gHCAcIBxAHEAbwBvAGfAZ8BpwGnAZQBlAGAAYABigGKAXABcAFoAWgBbAFsAU0BTQFNAU0BSgFKASsBKwE8ATwBIwEjAR0BHQEXARcBEQERAfUA9QAXABcA8f/x/wsACwD4//j/BgAGAP3//f8CAAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/////wIAAgD8//z/BgAGAPn/+f8JAAkA9v/2/wkACQDtAO0A+AD4AN8A3wDxAPEAyADIANwA3ADJAMkAuQC5AMUAxQCvAK8AqACoALEAsQCXAJcAmwCbAJoAmgCAAIAAiwCLAIAAgABvAG8AfQB9AGUAZQBfAF8AawBrAFAAUABbAFsAZABkAE0ATQBhAGEAWwBbAEoASgBhAGEAUQBRAE0ATQBhAGEASgBKAFIAUgBcAFwARABEAFcAVwBWAFYARQBFAF4AXgBRAFEATABMAGMAYwBNAE0AVABUAGAAYABIAEgAXABcAF0AXQBMAEwAZQBlAFkAWQBTAFMAagBqAFYAVgBdAF0AbABsAFUAVQBnAGcAaABoAFcAVwBwAHAAZwBnAGIAYgB6AHoAZgBmAGwAbAB8AHwAZwBnAHoAegB/AH8AbQBtAIcAhwB/AH8AeAB4AJEAkQB/AH8AhACEAJYAlgCAAIAAkgCSAJgAmACHAIcAoQChAJsAmwCTAJMArQCtAJ0AnQCiAKIAtgC2AKEAoQCzALMAuwC7AKgAqADCAMIAvgC+ALYAtgDRANEAwQDBAMUAxQDaANoAxgDGANgA2ADiAOIAzwDPAOkA6QDnAOcA3wDfAPwA/ADuAO4A8QDxAAcBBwH0APQABQEFAREBEQH/AP8AGAEYARcBFwENAQ0BKgEqAR4BHgEhASEBOQE5ASUBJQE1ATUBQwFDATEBMQFMAUwBTgFOAUMBQwFgAWABVAFUAVUBVQFvAW8BXQFdAW0BbQF8AXwBaQFpAYIBggGEAYQBeAF4AZcBlwGOAY4BjwGPAakBqQGWAZYBpAGkAbUBtQGjAaMBvAG8Ab8BvwGzAbMB0AHQAccBxwHHAccB4wHjAdEB0QHdAd0B7wHvAdsB2wHzAfMB+AH4AeoB6gEHAgcC/gH+AfwB/AEYAhgCBgIGAhECEQIkAiQCEQIRAigCKAItAi0CHQIdAjoCOgIyAjICLwIvAksCSwI6AjoCQwJDAlUCVQJAAkACVgJWAlwCXAJMAkwCaAJoAmECYQJcAlwCeAJ4AmcCZwJuAm4CgQKBAmsCawJ/An8ChgKGAnUCdQKQApACiAKIAoACgAKaApoCiAKIAo4CjgKiAqICiwKLApwCnAKiAqICjgKOAqcCpwKhAqEClwKXArECsQKeAp4CoAKgArMCswKbApsCqgKqArECsQKbApsCswKzAq0CrQKhAqECuwK7AqkCqQKpAqkCvAK8AqQCpAKxArECuAK4AqACoAK1ArUCrwKvAqACoAK6AroCqAKoAqUCpQK4ArgCnwKfAqoCqgKzArMCmwKbArACsAKqAqoCmQKZArECsQKgAqACnAKcArACsAKXApcCoQKhAqsCqwKSApICpwKnAqICogKQApACqAKoApgCmAKTApMCpwKnAo4CjgKWApYCoAKgAogCiAKcApwCmgKaAoYChgKeAp4CjgKOAogCiAKfAp8CiAKIApECkQI=\"},\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"33\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"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/v/+/zgAOABvAG8AfQB9AIQAhACmAKYA8wDzAOsA6wBQAVABkAGQAYcBhwGUAZQBwQHBAQQCBAK1AbUBCgIKAloCWgIcAhwCHwIfAtcB1wGdAZ0BhwGHATYBNgFeAV4BawFrAR4BHgF3AXcBngGeAY4BjgHnAecBTAJMAkQCRAKLAosCogKiAmMCYwISAxIDPgM+A2YDZgPZA9kDCAQIBAkECQQRBBEEowSjBIgEiATpBOkEUAVQBQYFBgVHBUcFYgViBWQFZAU2BTYFYAVgBXkFeQUwBTAFGQUZBRMFEwVkBWQFZgVmBX8FfwWHBYcFwwXDBfQF9AW5BbkFIQYhBioGKgZGBkYGmQaZBs4GzgbYBtgGIAcgB3UHdQfwBvAGFgcWB1MHUwdtB20HqgeqB4oHigeOB44Hvge+B/UH9QexB7EHrQetB5UHlQdzB3MH3gfeB8MHwwduB24HiQeJB9oH2gebB5sHtAe0B+oH6gceCB4IWQhZCEgISAiTCJMIJggmCAMIAwghCCEI1wfXB/cH9wdLCEsIYghiCPEH8Qf1B/UHBggGCP4H/gffB98HzgfOB5MHkwcSBxIHEwcTB64GrgaBBoEGUQZRBgcGBwbFBcUFfwV/BU0FTQXUBNQEpwSnBIcEhwQ0BDQEtQO1A3oDegNcA1wDYwNjA4gDiANzA3MDXwNfA/gC+AK5ArkCtAK0AmsCawJwAnACegJ6AjsCOwIsAiwCFAIUAkQCRAJJAkkCKAIoAl8CXwIUAhQCAwIDAh0CHQLwAfABEwITAv4B/gG3AbcBrgGuAaYBpgHjAeMBHwIfAgwCDAJDAkMCRAJEAkYCRgJfAl8CdwJ3ArUCtQKJAokC0ALQAuQC5AK6AroC+gL6AvkC+QIAAwAD+gL6AvMC8wLXAtcC9gL2AusC6wK8ArwCoQKhAocChwKsAqwCOAI4AgwCDAIGAgYCvAG8AXYBdgFjAWMBfwF/AVoBWgFMAUwBTAFMARoBGgH+AP4AQAFAAfwA/ADeAN4ABAEEAfQA9AAbARsBHwEfAUUBRQEzATMBJQElAV8BXwF9AX0ByAHIAZ4BngE9AT0BRAFEAUIBQgEKAQoBKAEoAW4BbgFgAWABxAHEARUCFQL8AfwBUAJQAmcCZwJmAmYCdgJ2AnsCewKhAqECQQJBAlMCUwKhAqECsgKyArwCvAJeAl4C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\"},\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"34\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsComplete\",\"params\":[\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"94\"}"]
    //["{\"msg\":\"method\",\"method\":\"sendMessage\",\"params\":[{\"_id\":\"KZ7X2BmRAeRwaeou9\",\"rid\":\"GENERAL\",\"msg\":\"File Uploaded: *Audio record*\\nhttp://localhost:3000/ufs/rocketchat_uploads/dbX2JBSwXEnhF2uqm\",\"file\":{\"_id\":\"dbX2JBSwXEnhF2uqm\"}}],\"id\":\"95\"}"]


    //["{\"msg\":\"method\",\"method\":\"registerUser\",\"params\":[{\"name\":\"Júlio Cesar Bueno Cotta\",\"emailOrUsername\":\"\",\"email\":\"juliocbcotta+6@gmail.com\",\"pass\":\"google\",\"confirm-pass\":\"google\"}],\"id\":\"14\"}"]
    //["{\"msg\":\"method\",\"method\":\"joinDefaultChannels\",\"params\":[],\"id\":\"4\"}"]
    //["{\"msg\":\"method\",\"method\":\"UserPresence:setDefaultStatus\",\"params\":[\"online\"],\"id\":\"96\"}"]
    //["{\"msg\":\"method\",\"method\":\"UserPresence:away\",\"params\":[],\"id\":\"1\"}"]
    //["{\"msg\":\"method\",\"method\":\"login\",\"params\":[{\"user\":{\"username\":\"julio\"},\"password\":{\"digest\":\"bbdefa2950f49882f295b1285d4fa9dec45fc4144bfb07ee6acc68762d12c2e3\",\"algorithm\":\"sha-256\"}}],\"id\":\"3\"}"]
}
