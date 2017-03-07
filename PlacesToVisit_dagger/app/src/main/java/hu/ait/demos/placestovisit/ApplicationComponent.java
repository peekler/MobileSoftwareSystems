package hu.ait.demos.placestovisit;

import dagger.Component;
import hu.ait.demos.placestovisit.adapter.AdapterModule;
import hu.ait.demos.placestovisit.adapter.PlacesAdapter;

// ez egy injector
@Component(modules = AdapterModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(AnotherList anotherList);

    void inject(PlacesAdapter adapter);

    // ha MainActivity-ből származik le a Second akkor annak kell külön
    //void inject(SecondActivity secondActivity);
}
