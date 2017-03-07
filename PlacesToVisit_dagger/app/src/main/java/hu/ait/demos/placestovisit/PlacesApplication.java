package hu.ait.demos.placestovisit;

import android.app.Application;

import com.orm.SugarApp;

import hu.ait.demos.placestovisit.adapter.AdapterModule;

public class PlacesApplication extends SugarApp {
    public static ApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerApplicationComponent.builder().
                        adapterModule(
                                new AdapterModule(this)
                        ).build();
    }
}
