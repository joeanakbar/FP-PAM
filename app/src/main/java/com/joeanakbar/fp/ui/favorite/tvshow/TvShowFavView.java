package com.joeanakbar.fp.ui.favorite.tvshow;

import android.content.Context;

import com.joeanakbar.fp.model.TvShowItem;

import java.util.List;

public interface TvShowFavView {

    interface View {
        void hideRefresh();

        void showDataList(List<TvShowItem> tvShowItems);
    }

    interface Presenter {
        void getDataListMovie(Context context);
    }
}
