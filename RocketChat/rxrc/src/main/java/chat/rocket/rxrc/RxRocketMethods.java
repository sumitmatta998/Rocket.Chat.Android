package chat.rocket.rxrc;

import java.util.List;

import chat.rocket.rc.RocketMethods;
import chat.rocket.rc.listeners.AddUserToRoomListener;
import chat.rocket.rc.listeners.ArchiveRoomListener;
import chat.rocket.rc.listeners.LoginListener;
import chat.rocket.rc.models.Token;
import meteor.operations.MeteorException;
import meteor.operations.ResultListener;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by julio on 05/12/15.
 */
public class RxRocketMethods {
    private RocketMethods mMethods;

    public RxRocketMethods(RocketMethods methods) {
        mMethods = methods;
    }

    public Observable<Boolean> addUserToRoom(final String rid, final String username) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                mMethods.addUserToRoom(rid, username, new AddUserToRoomListener() {
                    @Override
                    public void onResult(Boolean result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<List<Integer>> archiveRoom(final String rid) {
        return Observable.create(new Observable.OnSubscribe<List<Integer>>() {
            @Override
            public void call(final Subscriber<? super List<Integer>> subscriber) {
                mMethods.archiveRoom(rid, new ArchiveRoomListener() {

                    @Override
                    public void onResult(List<Integer> result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Token> loginWithUsername(final String username, final String pass) {
        return Observable.create(new Observable.OnSubscribe<Token>() {
            @Override
            public void call(final Subscriber<? super Token> subscriber) {
                mMethods.loginWithUsername(username, pass, new LoginListener() {

                    @Override
                    public void onResult(Token result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);

                    }
                });
            }
        });
    }

    public Observable<Token> loginWithEmail(final String email, final String pass) {
        return Observable.create(new Observable.OnSubscribe<Token>() {
            @Override
            public void call(final Subscriber<? super Token> subscriber) {
                mMethods.loginWithEmail(email, pass, new LoginListener() {

                    @Override
                    public void onResult(Token result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Token> loginWithOAuth(final String credentialToken, final String credentialSecret) {
        return Observable.create(new Observable.OnSubscribe<Token>() {
            @Override
            public void call(final Subscriber<? super Token> subscriber) {
                mMethods.loginWithOAuth(credentialToken, credentialSecret, new LoginListener() {

                    @Override
                    public void onResult(Token result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Token> loginWithFacebook(final String accessToken, final long expiresIn) {
        return Observable.create(new Observable.OnSubscribe<Token>() {
            @Override
            public void call(final Subscriber<? super Token> subscriber) {
                mMethods.loginWithFacebook(accessToken, expiresIn, new LoginListener() {

                    @Override
                    public void onResult(Token result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(MeteorException e) {
                        super.onError(e);
                        subscriber.onError(e);
                    }
                });
            }
        });
    }
}
