package chat.rocket.network;

import chat.rocket.network.meteor.Meteor;
import chat.rocket.network.meteor.MeteorSingleton;
import chat.rocket.network.meteor.SubscribeListener;

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
}
