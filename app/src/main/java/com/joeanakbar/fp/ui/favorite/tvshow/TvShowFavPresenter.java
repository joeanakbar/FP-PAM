package com.joeanakbar.fp.ui.favorite.tvshow;

import android.content.Context;

import com.joeanakbar.fp.db.AppDatabase;

public class TvShowFavPresenter implements TvShowFavView.Presenter {

    private final TvShowFavView.View view;

    TvShowFavPresenter(TvShowFavView.View view) {
        this.view = view;
    }

    @Override
    public void getDataListMovie(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        if (database.movieFavDAO().selectFavoriteTv() != null) {
            view.showDataList(database.movieFavDAO().selectFavoriteTv());
        }
        view.hideRefresh();
    }
}

