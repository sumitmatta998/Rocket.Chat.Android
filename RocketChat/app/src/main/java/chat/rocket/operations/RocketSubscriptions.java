package chat.rocket.operations;

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

    public Subscription streaMmessages(SubscribeListener subscribeListener) {
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

    public Subscription room(String roomName, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("room", new Object[]{roomName}, subscribeListener), mMeteor);
    }

    public Subscription roomFiles(String rid, SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("roomFiles", new Object[]{rid}, subscribeListener), mMeteor);
    }

    public Subscription s(SubscribeListener subscribeListener) {
        return new Subscription(mMeteor.subscribe("", null, subscribeListener), mMeteor);
    }

    //TODO: understand this
    //["{\"msg\":\"sub\",\"id\":\"JCKKBAKRnr6BhC4qk\",\"name\":\"filteredUsers\",\"params\":[\"\"]}"]
    //["{\"msg\":\"sub\",\"id\":\"jcLZYJ7faH8TiMJDm\",\"name\":\"fullUserData\",\"params\":[null,1]}"]
    //["{\"msg\":\"sub\",\"id\":\"2hyq6Hat2XfPWc3Jr\",\"name\":\"channelAutocomplete\",\"params\":[\"\"]}"]
    //["{\"msg\":\"sub\",\"id\":\"mB242smievjf6hkeP\",\"name\":\"starredMessages\",\"params\":[\"ZxGahBDCn2cMb6YGG\"]}"]
    //["{\"msg\":\"sub\",\"id\":\"Yi79cbchwJL3o8PH3\",\"name\":\"pinnedMessages\",\"params\":[\"GENERAL\"]}"]


}
