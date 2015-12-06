package chat.rocket.rxrc;

import chat.rocket.rc.RocketSubscriptions;
import meteor.operations.MeteorException;
import meteor.operations.ResultListener;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by julio on 05/12/15.
 */
public class RxRocketSubscriptions {
    private RocketSubscriptions mRocketSubscriptions;

    public RxRocketSubscriptions(RocketSubscriptions rocketSubscriptions) {
        mRocketSubscriptions = rocketSubscriptions;
    }

    public Observable<Void> userData() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mRocketSubscriptions.userData(new ResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Void> loginServiceConfiguration() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mRocketSubscriptions.loginServiceConfiguration(new ResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }
}
