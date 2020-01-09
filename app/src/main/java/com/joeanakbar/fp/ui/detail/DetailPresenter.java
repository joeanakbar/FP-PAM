package com.joeanakbar.fp.ui.detail;

import android.content.Context;
import android.os.Bundle;

import com.joeanakbar.fp.db.AppDatabase;
import com.joeanakbar.fp.model.MovieItem;
import com.joeanakbar.fp.model.TvShowItem;
import com.joeanakbar.fp.utils.Helper;

import static com.joeanakbar.fp.utils.Helper.EXTRA_MOVIE;
import static com.joeanakbar.fp.utils.Helper.EXTRA_TV_SHOW;

public class DetailPresenter implements DetailView.Presenter {

    private final DetailView.View view;
    private AppDatabase database;

    DetailPresenter(DetailView.View view) {
        this.view = view;
    }

    @Override
    public void getDetailMovie(Bundle bundle) {
        if ("movie".equals(bundle.getSerializable(Helper.STATE))) {
            MovieItem movieItem = (MovieItem) bundle.getSerializable(EXTRA_MOVIE);
            view.showDetailMovie(movieItem);
        } else if ("tvShow".equals(bundle.getSerializable(Helper.STATE))) {
            TvShowItem tvShowItem = (TvShowItem) bundle.getSerializable(EXTRA_TV_SHOW);
            view.showDetailTvShow(tvShowItem);
        }
    }

    @Override
    public void addToFavMovie(Context context, MovieItem movieItem) {
        database = AppDatabase.getDatabase(context);
        database.movieFavDAO().insertMovie(movieItem);
    }

    @Override
    public void addToFavTv(Context context, TvShowItem tvShowItem) {
        database = AppDatabase.getDatabase(context);
        database.movieFavDAO().insertTvShow(tvShowItem);
    }

    @Override
    public void removeFavMovie(Context context, MovieItem movieItem) {
        database = AppDatabase.getDatabase(context);
        database.movieFavDAO().deleteMovie(movieItem);
    }

    @Override
    public void removeFavTv(Context context, TvShowItem tvShowItem) {
        database = AppDatabase.getDatabase(context);
        database.movieFavDAO().deleteTv(tvShowItem);
    }

    @Override
    public boolean checkFavMovie(Context context, MovieItem movieItem) {
        boolean bFavorite;
        database = AppDatabase.getDatabase(context);
        bFavorite = database.movieFavDAO().selectedMovie(movieItem.getId()) != null;
        return bFavorite;
    }

    @Override
    public boolean checkFavTvShow(Context context, TvShowItem tvShowItem) {
        boolean bFavorite;
        database = AppDatabase.getDatabase(context);
        bFavorite = database.movieFavDAO().selectedTv(tvShowItem.getId()) != null;
        return bFavorite;
    }
}
