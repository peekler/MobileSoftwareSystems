package hu.aut.daggerdemo;

import javax.inject.Singleton;

import dagger.Component;
import hu.aut.daggerdemo.network.NetworkModule;

@Singleton
@Component(modules = {NetworkModule.class})
public interface MainAppComponent {
    void inject(MainActivity mainActivity);
}
