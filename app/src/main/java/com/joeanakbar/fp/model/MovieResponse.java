package com.joeanakbar.fp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {
    @SerializedName("results")
    private ArrayList<MovieItem> result;

    public ArrayList<MovieItem> getResult() {
        return result;
    }
}
