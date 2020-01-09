package com.joeanakbar.fp.ui.favorite.movie;

import android.content.Context;

import com.joeanakbar.fp.db.AppDatabase;

public class MovieFavPresenter implements MovieFavView.Presenter {

    private final MovieFavView.View view;

    MovieFavPresenter(MovieFavView.View view) {
        this.view = view;
    }

    @Override
    public void getDataListMovie(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        if (database.movieFavDAO().selectFavoriteMovie() != null) {
            view.showDataList(database.movieFavDAO().selectFavoriteMovie());
        }
        view.hideRefresh();
    }
}

