package com.joeanakbar.fp.receiver;

import android.content.Context;

import com.joeanakbar.fp.api.ApiRetrofit;
import com.joeanakbar.fp.model.MovieResponse;
import com.joeanakbar.fp.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderPresenter implements ReminderView.Presenter {

    private ReminderView.View view;
    private Context context;
    private int notifId;

    public ReminderPresenter(ReminderView.View view, Context context, int notifId) {
        this.view = view;
        this.context = context;
        this.notifId = notifId;
    }

    @Override
    public void requestMovies(String date) {
        ApiRetrofit.getServiceRetrofit().getTodaysMovieRelease(date, date).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                view.setMovies(context, response.body().getResult(), notifId);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void requestTvShows(String date) {
        ApiRetrofit.getServiceRetrofit().getTodaysTvShowRelease(date, date).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                assert response.body() != null;
                view.setTvShow(context, response.body().getResultTvShow(), notifId);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }
}
