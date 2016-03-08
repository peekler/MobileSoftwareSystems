package hu.bme.aut.android.spotifydemo;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.android.spotifydemo.interactor.InteractorModule;
import hu.bme.aut.android.spotifydemo.network.NetworkModule;

@Singleton
@Component(modules = {NetworkModule.class, AndroidTestModule.class, InteractorModule.class})
public interface AndroidTestComponent extends SpotifyDemoApplicationComponent {
}
