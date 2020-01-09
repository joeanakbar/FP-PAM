package com.joeanakbar.fp.ui.movie;

import com.joeanakbar.fp.model.MovieItem;

import java.util.ArrayList;

public interface MovieView {
    void showLoad();

    void finishLoad();

    void showList(ArrayList<MovieItem> listMovie);

    void noData();
}
