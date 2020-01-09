package com.joeanakbar.fp.ui.favorite.tvshow;

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
import com.joeanakbar.fp.adapter.TvShowAdapter;
import com.joeanakbar.fp.model.TvShowItem;

import java.util.ArrayList;
import java.util.List;

public class TvShowFavFragment extends Fragment implements TvShowFavView.View {
    private RecyclerView recyclerView;
    private List<TvShowItem> dataFav = new ArrayList<>();

    private final TvShowFavPresenter presenter = new TvShowFavPresenter(this);
    SwipeRefreshLayout swipeFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
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
        TvShowAdapter adapter = new TvShowAdapter(getActivity(), dataFav);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void hideRefresh() {
        swipeFav.setRefreshing(false);
    }

    @Override
    public void showDataList(List<TvShowItem> tvShowItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TvShowAdapter(getContext(), tvShowItems));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getDataListMovie(getContext());
    }
}
