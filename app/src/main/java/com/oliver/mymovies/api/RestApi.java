package com.oliver.mymovies.api;

import android.content.Context;


import com.oliver.mymovies.BuildConfig;
import com.oliver.mymovies.Watchlist;
import com.oliver.mymovies.interceptor.LoggingInterceptor;
import com.oliver.mymovies.klasi.Favorites;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.Rated;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;
import com.oliver.mymovies.model.VideoModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

/**
 * Created by Oliver on 1/21/2018.
 */

public class RestApi {
    User user;
    Token token;
    public static final int request_max_time_in_secconds = 20;
    private Context activity;

    public RestApi(Context context) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor(activity, token))
                .readTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiService request() {
        return getRetrofitInstance().create(ApiService.class);
    }

    public Call<FilmModel> getPopular() {
        return request().getPopular();
    }

    public Call<FilmModel> getTopRated() {
        return request().getTopRated();
    }

    public Call<FilmModel> getUpcoming() {
        return request().getUpcoming();
    }

    public Call<FilmModel> getNowPlaying() {
        return request().getNowPlaying();
    }

    public Call<PeopleModel> getPersonPopular() {
        return request().getPersonPopular();
    }

    public Call<Film> getMovie(int id, String append_to_response) {
        return request().getMovie(id, append_to_response);
    }


    public Call<Film> getCast(int pozicija, String append_to_response) {
        return request().getMovie(pozicija, append_to_response);
    }

    public Call<Token> getToken(String request_token) {
        return request().getToken(request_token);
    }

    public Call<Token> getLOGIN(String username, String password, String request_token) {
        return request().getLOGIN(username, password, request_token);
    }

    public Call<Token> getSESION(String request_token) {
        return request().getSESION(request_token);
    }

    public Call<Film> postUserRating(int movie_id, String session_id, String json, Rated rated) {
        return request().postUserRating(movie_id, session_id, json, rated);
    }


    public Call<FilmModel> getRated(String session_id) {
        return request().getRated(session_id);
    }

    public Call<FilmModel> getFavorites(String sessionId) {

        return request().getFavorites(sessionId);
    }

    public Call<Film> postFavorites(String session_id, String json, Favorites body) {
        return request().postFavorites(session_id, json, body);
    }

    public Call<Film> getFavorit(int id, String sessionId) {
        return request().getFavorit(id, sessionId);
    }

    public Call<FilmModel> getWatch(String sessionId) {

        return request().getWatch(sessionId);
    }

    public Call<Film> postWatch(int account_id, String session_id, String json, com.oliver.mymovies.klasi.Watchlist body) {
        return request().postWatch(account_id, session_id, json, body);
    }

    public Call<VideoModel> getVideo(int id) {

        return request().getVideo(id);

    }
    public Call<FilmModel> getSearchMovie(String query) {

        return request().getSearchMovie(query);
    }
}
