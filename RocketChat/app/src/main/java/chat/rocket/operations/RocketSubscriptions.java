package chat.rocket.operations;

import chat.rocket.app.enumerations.ChannelType;
import chat.rocket.operations.meteor.Meteor;
import chat.rocket.operations.meteor.MeteorSingleton;
import chat.rocket.operations.meteor.SubscribeListener;

/**
 * Created by julio on 19/11/15.
 */
public class RocketSubscriptions {
    private final Meteor mMeteor;

    public RocketSubscriptions() {
        mMeteor = MeteorSingleton.getInstance();
    }

    public RocketSubscriptions(Meteor meteor) {
        mMeteor = meteor;
    }

    public Subscription userData(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("userData", null, subscribeListener), mMeteor);
    }

    public Subscription loginServiceConfiguration(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("meteor.loginServiceConfiguration", null, subscribeListener), mMeteor);
    }

    public Subscription settings(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("settings", null, subscribeListener), mMeteor);
    }

    public Subscription streamNotifyAll(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("stream-notify-all", null, subscribeListener), mMeteor);
    }

    public Subscription streamNotifyRoom(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("stream-notify-room", null, subscribeListener), mMeteor);
    }

    public Subscription streamNotifyUser(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("stream-notify-user", null, subscribeListener), mMeteor);
    }

    public Subscription roles(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("_roles", null, subscribeListener), mMeteor);
    }

    public Subscription streamMessages(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("stream-messages", null, subscribeListener), mMeteor);
    }

    public Subscription permissions(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("permissions", null, subscribeListener), mMeteor);
    }

    public Subscription subscription(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("subscription", null, subscribeListener), mMeteor);
    }

    public Subscription activeUsers(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("activeUsers", null, subscribeListener), mMeteor);
    }

    public Subscription adminSettings(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("admin-settings", null, subscribeListener), mMeteor);
    }

    public Subscription room(String roomName, ChannelType type, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("room", new Object[]{type.type() + roomName}, subscribeListener), mMeteor);
    }

    public Subscription roomFiles(String rid, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("roomFiles", new Object[]{rid}, subscribeListener), mMeteor);
    }

    public Subscription fullUserData(String filter, int limit, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("fullUserData", new Object[]{filter, limit}, subscribeListener), mMeteor);
    }

    public Subscription filteredUsers(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("filteredUsers", new Object[]{}, subscribeListener), mMeteor);
    }

    public Subscription channelAutocomplete(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("channelAutocomplete", null, subscribeListener), mMeteor);
    }

    public Subscription starredMessages(String rid, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("starredMessages", new Object[]{rid}, subscribeListener), mMeteor);
    }

    public Subscription pinnedMessages(String rid, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("pinnedMessages", new Object[]{rid}, subscribeListener), mMeteor);
    }

    public Subscription meteorAutoupdateClientVersions(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("meteor_autoupdate_clientVersions", null, subscribeListener), mMeteor);
    }
}
