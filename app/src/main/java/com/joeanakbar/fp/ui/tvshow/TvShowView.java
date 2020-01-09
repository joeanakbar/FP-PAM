package com.joeanakbar.fp.ui.tvshow;

import com.joeanakbar.fp.model.TvShowItem;

import java.util.ArrayList;

public interface TvShowView {
    void showLoad();

    void finishLoad();

    void showList(ArrayList<TvShowItem> listTvShow);

    void noData();
}
