package com.joeanakbar.fp.ui.favorite.movie;

import android.content.Context;

import com.joeanakbar.fp.model.MovieItem;

import java.util.List;

public interface MovieFavView {

    interface View {
        void hideRefresh();

        void showDataList(List<MovieItem> movieItems);
    }

    interface Presenter {
        void getDataListMovie(Context context);
    }
}
