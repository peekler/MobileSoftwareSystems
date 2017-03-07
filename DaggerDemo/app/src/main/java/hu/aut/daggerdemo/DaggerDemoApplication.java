package hu.aut.daggerdemo;

import android.app.Application;

import hu.aut.daggerdemo.network.NetworkModule;

/**
 * Created by Peter on 2017. 02. 23..
 */

public class DaggerDemoApplication extends Application {

    public static MainAppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerMainAppComponent.builder().networkModule(new NetworkModule(this)).build();
    }
}
