package hu.bme.aut.android.spotifydemo.test;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.Test;

import hu.bme.aut.android.spotifydemo.ui.artists.ArtistsActivity;
import hu.bme.aut.android.spotifydemo.ui.main.MainActivity;
import hu.bme.aut.android.spotifydemo.utils.ElapsedTimeIdlingResource;
import hu.bme.aut.android.spotifydemo.utils.EspressoTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static hu.bme.aut.android.spotifydemo.utils.AndroidTestUtils.setTestInjector;

public class ArtistDetails  extends EspressoTest<ArtistsActivity> {

    public static final String ARTIST = "AC/DC";

    public ArtistDetails() {
        super(ArtistsActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        setTestInjector();
        Intent intentWithArtist = new Intent();
        intentWithArtist.putExtra(MainActivity.KEY_ARTIST, ARTIST);
        activityRule.launchActivity(intentWithArtist);
    }

    @Test
    public void testItemClick() {
        ElapsedTimeIdlingResource.waitFor(2000, new ElapsedTimeIdlingResource.Listener() {
            @Override
            public void inIdle() {
                onView(withId(
                        hu.bme.aut.android.spotifydemo.R.id.recyclerViewArtists))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(
                                0, ViewActions.click()));

                onView(withText("http://www.google.hu")).check(matches(isDisplayed()));
            }
        });

    }
}
