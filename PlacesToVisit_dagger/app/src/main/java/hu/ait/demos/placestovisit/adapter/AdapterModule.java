package hu.ait.demos.placestovisit.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.ait.demos.placestovisit.data.Place;

@Module
public class AdapterModule {

    private Context context;

    public AdapterModule(Context context) {
        this.context = context;
    }

    @Provides
    @Named("Database")
    public PlacesAdapter providePlacesAdapter(Context context) {
        List<Place> placeList = Place.listAll(Place.class);
        return new PlacesAdapter(context, placeList);
    }

    @Provides
    @Named("Empty")
    public PlacesAdapter provideEmptyPlacesAdapter(Context context) {
        List<Place> placeList = new ArrayList<>();
        return new PlacesAdapter(context, placeList);
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
