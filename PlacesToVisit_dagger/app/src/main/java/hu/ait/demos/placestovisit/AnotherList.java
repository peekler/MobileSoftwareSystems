package hu.ait.demos.placestovisit;

import javax.inject.Inject;
import javax.inject.Named;

import hu.ait.demos.placestovisit.adapter.PlacesAdapter;

import static hu.ait.demos.placestovisit.PlacesApplication.injector;

public class AnotherList {

    @Inject
    @Named("Empty")
    PlacesAdapter placesAdapter;

    public AnotherList() {
        injector.inject(this);
    }
}
