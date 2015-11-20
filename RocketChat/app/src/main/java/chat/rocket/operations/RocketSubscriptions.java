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
}
