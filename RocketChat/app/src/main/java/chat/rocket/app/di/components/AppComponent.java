package chat.rocket.app.di.components;

import chat.rocket.app.RocketApp;
import chat.rocket.app.di.AppModule;
import chat.rocket.app.di.RocketModule;
import chat.rocket.app.di.scopes.AppScope;
import chat.rocket.rxrc.RxRocketMethods;
import dagger.Component;

/**
 * Created by julio on 16/12/15.
 */
@Component(modules = AppModule.class)
@AppScope
public interface AppComponent {

    RocketComponent plus(RocketModule module);
}
