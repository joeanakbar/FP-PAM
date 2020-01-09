package com.joeanakbar.fp.api;

import com.joeanakbar.fp.BuildConfig;
import com.joeanakbar.fp.model.MovieResponse;
import com.joeanakbar.fp.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientService {
    @GET("movie/now_playing?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<MovieResponse> getMovie();

    @GET("tv/on_the_air?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<TvShowResponse> getTvShow();

    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=")
    Call<MovieResponse> getSearchMovie(@Query("query") String query);

    @GET("search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=")
    Call<TvShowResponse> getSearchTvShow(@Query("query") String query);

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    Call<MovieResponse> getTodaysMovieRelease(@Query("primary_release_date.gte") String releaseDateGte, @Query("primary_release_date.lte") String releaseDateIte);

    @GET("discover/tv?api_key=" + BuildConfig.API_KEY)
    Call<TvShowResponse> getTodaysTvShowRelease(@Query("first_air_date.gte") String firstAirDateGte, @Query("first_air_date.lte") String firstAirDateIte);
}
