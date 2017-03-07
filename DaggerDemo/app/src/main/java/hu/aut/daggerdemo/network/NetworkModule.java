package hu.aut.daggerdemo.network;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    private Context context;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public NetworkCall provideNetworkCall() {
        return new NetworkCall();
    }
}
