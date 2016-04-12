package hu.bme.aut.android.spotifydemo.ui.artists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.android.spotifydemo.R;
import hu.bme.aut.android.spotifydemo.SpotifyDemoApplication;
import hu.bme.aut.android.spotifydemo.model.Item;
import hu.bme.aut.android.spotifydemo.ui.details.DetailsActivity;
import hu.bme.aut.android.spotifydemo.ui.main.MainActivity;

/**
 * A placeholder fragment containing a simple screen.
 */
public class ArtistsFragment extends Fragment implements ArtistsScreen {

    private EditText etArtist;
    private RecyclerView recyclerViewArtists;
    private SwipeRefreshLayout swipeRefreshLayoutArtists;
    private TextView tvEmpty;
    private List<Item> artistsList;
    private ArtistsAdapter artistsAdapter;
    private String artist = "queen";

    @Inject
    ArtistsPresenter artistsPresenter;

    public ArtistsFragment() {
        SpotifyDemoApplication.injector.inject(this);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        artist = getActivity().getIntent().getStringExtra(MainActivity.KEY_ARTIST);
        artistsPresenter.attachScreen(this);
    }

    @Override
    public void onDetach() {
        artistsPresenter.detachScreen();
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        etArtist = (EditText) view.findViewById(R.id.etArtist);
        etArtist.setText(artist);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        recyclerViewArtists = (RecyclerView) view.findViewById(R.id.recyclerViewArtists);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewArtists.setLayoutManager(llm);

        artistsList = new ArrayList<>();
        artistsAdapter = new ArtistsAdapter(getContext(), artistsList);
        recyclerViewArtists.setAdapter(artistsAdapter);

        recyclerViewArtists.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        artistsPresenter.handleDetails(
                                artistsAdapter.getItem(position).
                                        getExternalUrls().getSpotify());
                    }
                })
        );

        swipeRefreshLayoutArtists = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutArtists);

        swipeRefreshLayoutArtists.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                artist = etArtist.getText().toString();
                artistsPresenter.refreshArtists(artist);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        artistsPresenter.refreshArtists(artist);
    }

    public void showArtists(List<Item> artists) {
        if (swipeRefreshLayoutArtists != null) {
            swipeRefreshLayoutArtists.setRefreshing(false);
        }

        artistsList.clear();
        artistsList.addAll(artists);
        artistsAdapter.notifyDataSetChanged();

        if (artistsList.isEmpty()) {
            recyclerViewArtists.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerViewArtists.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNetworkError(String errorMsg) {
        if (swipeRefreshLayoutArtists != null) {
            swipeRefreshLayoutArtists.setRefreshing(false);
        }
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showArtistsDetails(String url) {
        startActivity(new Intent(getActivity(), DetailsActivity.class));
    }
}
