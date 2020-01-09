package com.joeanakbar.fp.ui.favorite.movie;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joeanakbar.fp.R;
import com.joeanakbar.fp.adapter.MovieAdapter;
import com.joeanakbar.fp.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class MovieFavFragment extends Fragment implements MovieFavView.View {
    private RecyclerView recyclerView;
    private ArrayList<MovieItem> dataFav = new ArrayList<>();

    private final MovieFavPresenter presenter = new MovieFavPresenter(this);
    SwipeRefreshLayout swipeFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvFavorite);
        swipeFav = view.findViewById(R.id.swipeFavoriteMovie);
        presenter.getDataListMovie(getContext());
        swipeFav.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getDataListMovie(getContext());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieAdapter adapter = new MovieAdapter(getActivity(), dataFav);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void hideRefresh() {
        swipeFav.setRefreshing(false);
    }

    @Override
    public void showDataList(List<MovieItem> movieItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieAdapter(getContext(), movieItems));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getDataListMovie(getContext());
    }
}