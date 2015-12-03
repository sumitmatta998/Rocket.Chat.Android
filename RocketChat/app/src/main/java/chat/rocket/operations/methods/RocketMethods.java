package chat.rocket.operations.methods;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import chat.rocket.models.Message;
import chat.rocket.models.TimeStamp;
import chat.rocket.operations.meteor.Meteor;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.meteor.ResultListener;
import chat.rocket.operations.methods.listeners.AddUserToRoomListener;
import chat.rocket.operations.methods.listeners.ArchiveRoomListener;
import chat.rocket.operations.methods.listeners.CanAccessRoomListener;
import chat.rocket.operations.methods.listeners.ChannelsListListener;
import chat.rocket.operations.methods.listeners.CreateChannelListener;
import chat.rocket.operations.methods.listeners.CreateDirectMessageListener;
import chat.rocket.operations.methods.listeners.CreatePrivateGroupListener;
import chat.rocket.operations.methods.listeners.DeleteMessageListener;
import chat.rocket.operations.methods.listeners.EraseRoomListener;
import chat.rocket.operations.methods.listeners.GetTotalChannelsListener;
import chat.rocket.operations.methods.listeners.HideRoomListener;
import chat.rocket.operations.methods.listeners.JoinRoomListener;
import chat.rocket.operations.methods.listeners.LeaveRoomListener;
import chat.rocket.operations.methods.listeners.LoadHistoryListener;
import chat.rocket.operations.methods.listeners.LoginListener;
import chat.rocket.operations.methods.listeners.OpenRoomListener;
import chat.rocket.operations.methods.listeners.ReadMessagesListener;
import chat.rocket.operations.methods.listeners.ResetAvatarListener;
import chat.rocket.operations.methods.listeners.SaveRoomNameListener;
import chat.rocket.operations.methods.listeners.SendConfirmationEmailListener;
import chat.rocket.operations.methods.listeners.SendForgotPasswordEmailListener;
import chat.rocket.operations.methods.listeners.SendMessageListener;
import chat.rocket.operations.methods.listeners.UpdateMessageListener;

/**
 * Created by julio on 19/11/15.
 */
public class RocketMethods {
    private Meteor mMeteor;

    public RocketMethods() {
        mMeteor = MeteorSingleton.getInstance();
    }

    public RocketMethods(Meteor meteor) {
        mMeteor = meteor;
    }

    public void addUserToRoom(String rid, String username, AddUserToRoomListener listener) {
        Map<String, String> data = new HashMap<>();
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
        Map<String, String> data = new HashMap<>();
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
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("pass", pass);
        mMeteor.call("registerUser", new Object[]{data}, listener);
    }

    public void removeUserFromRoom(String rid, String username, ResultListener listener) {
        Map<String, String> data = new HashMap<>();
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

        Map<String, String> data = new HashMap<>();

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
        Map<String, String> data = new HashMap<>();

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
        Map<String, String> data = new HashMap<>();

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
        Map<String, String> data = new HashMap<>();

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

    public void loginWithFacebook(String accessToken, long expiresIn, final ResultListener listener) {
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
        Map<String, Object> map = new HashMap<>();
        final Map<String, Object> tokenData = new HashMap<String, Object>();
        tokenData.put("gcm", token);
        map.put("token", tokenData);
        map.put("appName", "main");
        map.put("userId", mMeteor.getUserId());

        mMeteor.call("raix:push-update", new Object[]{map}, listener);
    }

    public void UserPresence(String status, String presence, ResultListener listener) {
        mMeteor.call("UserPresence:" + status, new Object[]{presence}, listener);
    }

    public void uploadFile(String userId, String rid, String[] strings, String type, String extension, long size) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Audio Record");
        data.put("size", size);
        data.put("type", type);
        data.put("rid", rid);
        data.put("complete", false);
        data.put("uploading", true);
        data.put("store", "rocketchat_uploads");
        data.put("extension", extension);
        data.put("userId", userId);

        mMeteor.insert("rocketchat_uploads", new Random(System.currentTimeMillis()).nextInt() + "", data, new ResultListener() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error, String reason, String details) {

            }
        });
    }

    public void ufsWrite() {
        //mMeteor.call("ufsWrite", );
    }


    //TODO: understand this message
    //["{\"msg\":\"method\",\"method\":\"registerUser\",\"params\":[{\"name\":\"Júlio Cesar Bueno Cotta\",\"emailOrUsername\":\"\",\"email\":\"juliocbcotta+6@gmail.com\",\"pass\":\"google\",\"confirm-pass\":\"google\"}],\"id\":\"14\"}"]
    //["{\"msg\":\"method\",\"method\":\"joinDefaultChannels\",\"params\":[],\"id\":\"4\"}"]
    //["{\"msg\":\"method\",\"method\":\"UserPresence:setDefaultStatus\",\"params\":[\"online\"],\"id\":\"96\"}"]
    //["{\"msg\":\"method\",\"method\":\"UserPresence:away\",\"params\":[],\"id\":\"1\"}"]
    //["{\"msg\":\"method\",\"method\":\"login\",\"params\":[{\"user\":{\"username\":\"julio\"},\"password\":{\"digest\":\"bbdefa2950f49882f295b1285d4fa9dec45fc4144bfb07ee6acc68762d12c2e3\",\"algorithm\":\"sha-256\"}}],\"id\":\"3\"}"]
    //["{\"msg\":\"method\",\"method\":\"/rocketchat_uploads/insert\",\"params\":[{\"name\":\"snapshot1.png\",\"size\":148131,\"type\":\"image/png\",\"rid\":\"GENERAL\",\"complete\":false,\"uploading\":true,\"store\":\"rocketchat_uploads\",\"extension\":\"png\",\"userId\":\"dcCYAPydG7jxSbFC5\"}],\"id\":\"8\",\"randomSeed\":\"da0c2dea1aa5a1977b26\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"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\"},\"AYor7yNhLaRdBH2Pe\",\"rocketchat_uploads\"],\"id\":\"9\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"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\"},\"AYor7yNhLaRdBH2Pe\",\"rocketchat_uploads\"],\"id\":\"10\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsComplete\",\"params\":[\"AYor7yNhLaRdBH2Pe\",\"rocketchat_uploads\"],\"id\":\"28\"}"]
    //["{\"msg\":\"method\",\"method\":\"sendMessage\",\"params\":[{\"_id\":\"xLu3w26XfDqz39xYb\",\"rid\":\"GENERAL\",\"msg\":\"File Uploaded: *snapshot1.png*\\nhttp://localhost:3000/ufs/rocketchat_uploads/AYor7yNhLaRdBH2Pe.png\",\"file\":{\"_id\":\"AYor7yNhLaRdBH2Pe\"}}],\"id\":\"29\"}"]

    //["{\"msg\":\"method\",\"method\":\"/rocketchat_uploads/insert\",\"params\":[{\"name\":\"Audio record\",\"size\":491564,\"type\":\"audio/wav\",\"rid\":\"GENERAL\",\"complete\":false,\"uploading\":true,\"store\":\"rocketchat_uploads\",\"extension\":\"\",\"userId\":\"dcCYAPydG7jxSbFC5\"}],\"id\":\"32\",\"randomSeed\":\"b2964a940974ec3cb4e2\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"UklGRiSABwBXQVZFZm10IBAAAAABAAIARKwAABCxAgAEABAAZGF0YQCABwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAQD8//z/CgAKAO3/7f8gACAA0v/S/z0APQC3/7f/TwBPALv/u/8Y+Bj4kvaS9jj3OPdg9mD2jvaO9hP2E/YM9gz2sfWx9Zn1mfVZ9Vn1IvUi9QL1AvXA9MD0lvSW9HL0cvQ09DT0FPQU9Ovz6/Oy87LzmvOa827zbvM/8z/zKPMo8/ny+fLU8tTyuvK68onyifJv8m/yUPJQ8iPyI/IN8g3y6fHp8cLxwvGv8a/xh/GH8WnxafFW8VbxLfEt8RjxGPEA8QDx2fDZ8MnwyfCt8K3wjPCM8IDwgPBg8GDwR/BH8DrwOvAY8BjwBvAG8Pbv9u/W79bvzO/M77jvuO+c75zvlu+W733vfe9p72nvZO9k70nvSe8+7z7vNe817xvvG+8W7xbvCe8J7/Pu8+707vTu4+7j7tTu1O7W7tbuwu7C7r3uve697r3uqu6q7q3ure6p7qnumu6a7qLuou6Z7pnuke6R7prumu6O7o7ukO6Q7pjumO6N7o3ul+6X7pzunO6V7pXupu6m7qbupu6m7qbuuO647rTutO6+7r7u0O7Q7s3uze7f7t/u7e7t7u7u7u4H7wfvEe8R7xrvGu837zfvPe8970/vT+9r72vvcO9w74vvi++j76PvrO+s78/vz+/j7+Pv9O/07xvwG/Aq8CrwRvBG8GzwbPB78HvwoPCg8MLwwvDV8NXwAvEC8R7xHvE48TjxaPFo8X7xfvGj8aPx0/HT8evx6/Eb8hvyRvJG8mDyYPKW8pbyvPK88t/y3/IZ8xnzOPM482TzZPOc85zzu/O78/Pz8/Mm9Cb0R/RH9IX0hfSy9LL03PTc9B/1H/VG9Ub1evV69bn1ufXd9d31HPYc9lb2VvZ89nz2wvbC9vL28vYg9yD3afdp95P3k/fN9833EvgS+Dr4Ovh9+H34u/i7+OX45fgw+TD5Zfll+Zf5l/nj+eP5DvoO+kr6SvqS+pL6u/q7+gL7AvtB+0H7avtq+7f7t/vt++37H/wf/G38bfyY/Jj80vzS/Bv9G/1C/UL9if2J/cn9yf3x/fH9PP48/m7+bv6d/p3+6v7q/hP/E/9M/0z/k/+T/7X/tf/4//j/MwAzAFcAVwCgAKAAzgDOAPcA9wA/AT8BYgFiAZYBlgHYAdgB9AH0ATICMgJoAmgChAKEAscCxwLvAu8CEQMRA1QDVANwA3ADnQOdA9kD2QPsA+wDIgQiBFEEUQRlBGUEogSiBMIEwgTbBNsEFgUWBSkFKQVNBU0FggWCBYwFjAW5BbkF3wXfBekF6QUdBh0GNAY0BkQGRAZ4BngGgQaBBpsGmwbHBscGyAbIBuwG7AYKBwoHCgcKBzQHNAdCB0IHSAdIB3IHcgdzB3MHgweDB6cHpwefB58HuQe5B9AH0AfGB8YH6AfoB+8H7wfqB+oHDAgMCAQIBAgKCAoIJggmCBYIFggoCCgIOAg4CCYIJghACEAIQAhACDMIMwhNCE0IPgg+CDwIPAhSCFIIOwg7CEUIRQhPCE8INQg1CEcIRwhBCEEILgguCEIIQggvCC8IJggmCDcINwgaCBoIHQgdCCAIIAj/B/8HDAgMCAYIBgj0B/QHDQgNCAAIAAj4B/gHDAgMCPIH8gf3B/cHAggCCOcH5wf3B/cH9Af0B90H3QfxB/EH3wffB9EH0QfiB+IHxwfHB8cHxwfPB88HsAewB7oHugezB7MHmAeYB6gHqAeVB5UHhAeEB5IHkgdzB3MHbQdtB3MHcwdRB1EHWAdYB1AHUAcxBzEHPAc8ByYHJgcRBxEHHQcdB/4G/gb1BvUG+Qb5BtYG1gbYBtgG0AbQBq4Grga2BrYGoQahBokGiQaTBpMGcgZyBmUGZQZoBmgGQwZDBkMGQwY6BjoGFgYWBhwGHAYHBgcG7AXsBfUF9QXVBdUFxgXGBckFyQWkBaQFoQWhBZkFmQV0BXQFeAV4BWQFZAVHBUcFTwVPBS8FLwUdBR0FIAUgBfsE+wT3BPcE7wTvBMoEygTOBM4EugS6BJ0EnQSkBKQEhgSGBHMEcwR2BHYEUQRRBEsESwRGBEYEIQQhBCMEIwQQBBAE8gPyA/kD+QPdA90DyQPJA80DzQOpA6kDoQOhA50DnQN6A3oDfQN9A20DbQNOA04DVANUAzkDOQMlAyUDKgMqAwgDCAMAAwAD/QL9AtoC2gLcAtwCzwLPArECsQK4ArgCoAKgAosCiwKSApICcgJyAmoCagJqAmoCSAJIAkoCSgI+Aj4CIAIgAigCKAISAhIC/QH9AQUCBQLoAegB3wHfAeIB4gHCAcIBxAHEAbwBvAGfAZ8BpwGnAZQBlAGAAYABigGKAXABcAFoAWgBbAFsAU0BTQFNAU0BSgFKASsBKwE8ATwBIwEjAR0BHQEXARcBEQERAfUA9QAXABcA8f/x/wsACwD4//j/BgAGAP3//f8CAAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/////wIAAgD8//z/BgAGAPn/+f8JAAkA9v/2/wkACQDtAO0A+AD4AN8A3wDxAPEAyADIANwA3ADJAMkAuQC5AMUAxQCvAK8AqACoALEAsQCXAJcAmwCbAJoAmgCAAIAAiwCLAIAAgABvAG8AfQB9AGUAZQBfAF8AawBrAFAAUABbAFsAZABkAE0ATQBhAGEAWwBbAEoASgBhAGEAUQBRAE0ATQBhAGEASgBKAFIAUgBcAFwARABEAFcAVwBWAFYARQBFAF4AXgBRAFEATABMAGMAYwBNAE0AVABUAGAAYABIAEgAXABcAF0AXQBMAEwAZQBlAFkAWQBTAFMAagBqAFYAVgBdAF0AbABsAFUAVQBnAGcAaABoAFcAVwBwAHAAZwBnAGIAYgB6AHoAZgBmAGwAbAB8AHwAZwBnAHoAegB/AH8AbQBtAIcAhwB/AH8AeAB4AJEAkQB/AH8AhACEAJYAlgCAAIAAkgCSAJgAmACHAIcAoQChAJsAmwCTAJMArQCtAJ0AnQCiAKIAtgC2AKEAoQCzALMAuwC7AKgAqADCAMIAvgC+ALYAtgDRANEAwQDBAMUAxQDaANoAxgDGANgA2ADiAOIAzwDPAOkA6QDnAOcA3wDfAPwA/ADuAO4A8QDxAAcBBwH0APQABQEFAREBEQH/AP8AGAEYARcBFwENAQ0BKgEqAR4BHgEhASEBOQE5ASUBJQE1ATUBQwFDATEBMQFMAUwBTgFOAUMBQwFgAWABVAFUAVUBVQFvAW8BXQFdAW0BbQF8AXwBaQFpAYIBggGEAYQBeAF4AZcBlwGOAY4BjwGPAakBqQGWAZYBpAGkAbUBtQGjAaMBvAG8Ab8BvwGzAbMB0AHQAccBxwHHAccB4wHjAdEB0QHdAd0B7wHvAdsB2wHzAfMB+AH4AeoB6gEHAgcC/gH+AfwB/AEYAhgCBgIGAhECEQIkAiQCEQIRAigCKAItAi0CHQIdAjoCOgIyAjICLwIvAksCSwI6AjoCQwJDAlUCVQJAAkACVgJWAlwCXAJMAkwCaAJoAmECYQJcAlwCeAJ4AmcCZwJuAm4CgQKBAmsCawJ/An8ChgKGAnUCdQKQApACiAKIAoACgAKaApoCiAKIAo4CjgKiAqICiwKLApwCnAKiAqICjgKOAqcCpwKhAqEClwKXArECsQKeAp4CoAKgArMCswKbApsCqgKqArECsQKbApsCswKzAq0CrQKhAqECuwK7AqkCqQKpAqkCvAK8AqQCpAKxArECuAK4AqACoAK1ArUCrwKvAqACoAK6AroCqAKoAqUCpQK4ArgCnwKfAqoCqgKzArMCmwKbArACsAKqAqoCmQKZArECsQKgAqACnAKcArACsAKXApcCoQKhAqsCqwKSApICpwKnAqICogKQApACqAKoApgCmAKTApMCpwKnAo4CjgKWApYCoAKgAogCiAKcApwCmgKaAoYChgKeAp4CjgKOAogCiAKfAp8CiAKIApECkQI=\"},\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"33\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsWrite\",\"params\":[{\"$binary\":\"ngKeAoYChgKaApoCmgKaAocChwKhAqEClAKUAo4CjgKnAqcCkwKTAp0CnQKuAq4CmQKZAq8CrwKyArICnwKfAroCugKvAq8CqAKoAsICwgKtAq0CtAK0AsUCxQKtAq0CwQLBAsUCxQKyArICywLLAsICwgK6AroC1ALUAsACwALFAsUC1QLVAr0CvQLOAs4C0wLTAr8CvwLYAtgCzwLPAsUCxQLeAt4CygLKAs0CzQLeAt4CxQLFAtUC1QLaAtoCxALEAtwC3ALTAtMCxwLHAuAC4ALMAswCzQLNAt4C3gLFAsUC0gLSAtcC1wK/Ar8C1QLVAs4CzgLAAsAC2ALYAsQCxALBAsEC0gLSArcCtwLCAsICyALIAq4CrgLCAsICugK6AqkCqQK/Ar8CqQKpAqQCpAK1ArUCmgKaAqICogKnAqcCigKKApoCmgKRApECfQJ9ApECkQJ8AnwCcgJyAoECgQJiAmICZgJmAmoCagJMAkwCWQJZAlACUAI4AjgCSQJJAjECMQIkAiQCMQIxAhQCFAITAhMCFQIVAvMB8wH7AfsB9wH3Ad0B3QHqAeoB0QHRAcEBwQHUAdQBuwG7AbwBvAGkAaQBegF6AZUBlQF+AX4BOAE4ASkBKQESARIB1wDXAK4ArgB1AHUAWwBbAEgASAABAAEA5P/k/8X/xf+g/6D/fP98/2f/Z/9G/0b/EP8Q/+v+6/7d/t3+xf7F/nb+dv5e/l7+Sv5K/gX+Bf7U/dT9tv22/bX9tf2d/Z39Yv1i/Uf9R/06/Tr9GP0Y/eD84PzO/M784fzh/Lf8t/yM/Iz8f/x//GX8Zfwv/C/8E/wT/Bf8F/zh++H7vPu8+677rvub+5v7ofuh+4X7hftV+1X7UftR+0b7Rvsp+yn7HPsc+xn7Gfsb+xv79/r3+tn62frP+s/6t/q3+pv6m/qT+pP6c/pz+mX6Zfp1+nX6Nvo2+iv6K/oh+iH6A/oD+gH6AfrN+c35r/mv+cD5wPmc+Zz5e/l7+ZT5lPmI+Yj5iPmI+Wr5avlH+Uf5UPlQ+Tv5O/lM+Uz5QvlC+SH5IflC+UL5R/lH+RH5Efnu+O74FvkW+fb49vj0+PT42fjZ+IT4hPjA+MD4m/ib+Ib4hvie+J74aPho+JD4kPid+J34TfhN+Cv4K/hB+EH4APgA+AL4AvjT99P3f/d/94P3g/cf9x/3Hfcd9/72/vZ09nT2QfZB9kD2QPbc9dz1p/Wn9Xb1dvUg9SD1OfU59b70vvRz9HP0i/SL9Eb0RvTW89bzvPO88+/z7/Nq82rzLPMs83XzdfM58znzAvMC8+/y7/La8tryrPKs8oPyg/JS8lLy6vHq8RjyGPL28fbxmvGa8dvx2/Hq8erxxPHE8azxrPHf8d/xwfHB8Z7xnvG18bXxqfGp8dbx1vHB8cHxyPHI8b7xvvGX8Zfx2/Hb8W7xbvF48XjxmfGZ8VbxVvHA8cDxgPGA8Wvxa/Gq8arxpfGl8YbxhvGd8Z3xpfGl8YbxhvHA8cDxmvGa8cjxyPHU8dTxtfG18Q/yD/IT8hPyFvIW8jryOvJm8mbyw/LD8gnzCfMG8wbzSfNJ85DzkPPK88rzFPQU9DX0NfRM9Ez0OvQ69IH0gfSe9J70jPSM9Nj02PQP9Q/1WPVY9X/1f/Vv9W/1ufW59SX2JfZZ9ln2aPZo9sn2yfYL9wv3CvcK94r3ivfb99v31vfW9zf4N/if+J/4w/jD+Cj5KPlK+Ur5bflt+d753vkn+if6hPqE+rv6u/oy+zL7f/t/+9D70PsY/Bj88vvy+3z8fPzv/O/87Pzs/Oz87Px5/Xn95/3n/ab9pv0V/hX+HP4c/vf99/1U/lT+R/5H/ov+i/75/vn+/P78/iv/K/9L/0v/Sv9K/5j/mP+Y/5j/pv+m/97/3v/I/8j/3//f/ywALABEAEQAiQCJAH0AfQAsACwANgA2AAEAAQAuAC4AZABkAFQAVABoAGgAeQB5AEgASADm/+b/9//3/ysAKwBFAEUANwA3ACYAJgAZABkADAAMAA0ADQB3AHcAUgBSAOz/7P9dAF0AVABUAGkAaQBjAGMANwA3ALsAuwDPAM8AvQC9ACIBIgEMAQwB/wD/ADwBPAFJAUkBUQFRAWgBaAG4AbgBoAGgAYEBgQFcAVwB9QD1AEMBQwHBAcEBfgF+ATABMAFaAVoBMQExAdEA0QB6AHoASwBLAPv/+//I/8j/0v/S/3P/c/9s/2z/g/+D/6n/qf9+/37/N/83/0H/Qf8p/yn/Qf9B/xD/EP/s/uz+F/8X/zj/OP9R/1H/OP84/zf/N/9G/0b/P/8//zr/Ov8v/y//Q/9D/0n/Sf9e/17/gP+A/1D/UP88/zz/Lf8t/y7/Lv+D/4P/Zv9m/1z/XP/b/9v//v/+/zgAOABvAG8AfQB9AIQAhACmAKYA8wDzAOsA6wBQAVABkAGQAYcBhwGUAZQBwQHBAQQCBAK1AbUBCgIKAloCWgIcAhwCHwIfAtcB1wGdAZ0BhwGHATYBNgFeAV4BawFrAR4BHgF3AXcBngGeAY4BjgHnAecBTAJMAkQCRAKLAosCogKiAmMCYwISAxIDPgM+A2YDZgPZA9kDCAQIBAkECQQRBBEEowSjBIgEiATpBOkEUAVQBQYFBgVHBUcFYgViBWQFZAU2BTYFYAVgBXkFeQUwBTAFGQUZBRMFEwVkBWQFZgVmBX8FfwWHBYcFwwXDBfQF9AW5BbkFIQYhBioGKgZGBkYGmQaZBs4GzgbYBtgGIAcgB3UHdQfwBvAGFgcWB1MHUwdtB20HqgeqB4oHigeOB44Hvge+B/UH9QexB7EHrQetB5UHlQdzB3MH3gfeB8MHwwduB24HiQeJB9oH2gebB5sHtAe0B+oH6gceCB4IWQhZCEgISAiTCJMIJggmCAMIAwghCCEI1wfXB/cH9wdLCEsIYghiCPEH8Qf1B/UHBggGCP4H/gffB98HzgfOB5MHkwcSBxIHEwcTB64GrgaBBoEGUQZRBgcGBwbFBcUFfwV/BU0FTQXUBNQEpwSnBIcEhwQ0BDQEtQO1A3oDegNcA1wDYwNjA4gDiANzA3MDXwNfA/gC+AK5ArkCtAK0AmsCawJwAnACegJ6AjsCOwIsAiwCFAIUAkQCRAJJAkkCKAIoAl8CXwIUAhQCAwIDAh0CHQLwAfABEwITAv4B/gG3AbcBrgGuAaYBpgHjAeMBHwIfAgwCDAJDAkMCRAJEAkYCRgJfAl8CdwJ3ArUCtQKJAokC0ALQAuQC5AK6AroC+gL6AvkC+QIAAwAD+gL6AvMC8wLXAtcC9gL2AusC6wK8ArwCoQKhAocChwKsAqwCOAI4AgwCDAIGAgYCvAG8AXYBdgFjAWMBfwF/AVoBWgFMAUwBTAFMARoBGgH+AP4AQAFAAfwA/ADeAN4ABAEEAfQA9AAbARsBHwEfAUUBRQEzATMBJQElAV8BXwF9AX0ByAHIAZ4BngE9AT0BRAFEAUIBQgEKAQoBKAEoAW4BbgFgAWABxAHEARUCFQL8AfwBUAJQAmcCZwJmAmYCdgJ2AnsCewKhAqECQQJBAlMCUwKhAqECsgKyArwCvAJeAl4C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\"},\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"34\"}"]
    //["{\"msg\":\"method\",\"method\":\"ufsComplete\",\"params\":[\"dbX2JBSwXEnhF2uqm\",\"rocketchat_uploads\"],\"id\":\"94\"}"]
    //["{\"msg\":\"method\",\"method\":\"sendMessage\",\"params\":[{\"_id\":\"KZ7X2BmRAeRwaeou9\",\"rid\":\"GENERAL\",\"msg\":\"File Uploaded: *Audio record*\\nhttp://localhost:3000/ufs/rocketchat_uploads/dbX2JBSwXEnhF2uqm\",\"file\":{\"_id\":\"dbX2JBSwXEnhF2uqm\"}}],\"id\":\"95\"}"]

}
