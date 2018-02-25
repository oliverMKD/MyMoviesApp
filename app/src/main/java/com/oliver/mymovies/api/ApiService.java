package com.oliver.mymovies.api;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.Rated;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Oliver on 1/21/2018.
 */

public interface ApiService {

    @GET("movie/popular?" +ApiConstants.api_key)
    Call<FilmModel> getPopular();
    @GET("movie/top_rated?"+ApiConstants.api_key)
    Call<FilmModel> getTopRated();
    @GET("movie/now_playing?"+ApiConstants.api_key)
    Call<FilmModel> getNowPlaying();
    @GET("movie/upcoming?"+ApiConstants.api_key)
    Call<FilmModel> getUpcoming();
    @GET("person/popular?"+ApiConstants.api_key)
    Call<PeopleModel> getPersonPopular();
    @GET("movie/{movie_id}?" + ApiConstants.api_key)
    Call<Film> getMovie(@Path("movie_id") int link,@Query("append_to_response")String credits);
    @GET("movie/{movie_id}?" + ApiConstants.api_key)
    Call<Film> getCast(@Path("movie_id") int pozicija,@Query("append_to_response")String credits);
    @GET("authentication/token/new?"+ApiConstants.api_key)
    Call<Token> getToken(@Query("request_token") String request_token);
    @GET("authentication/token/validate_with_login?"+ApiConstants.api_key)
    Call<Token> getLOGIN(@Query("username") String user, @Query("password") String pass, @Query("request_token") String request_token);
    @GET("authentication/session/new?"+ApiConstants.api_key)
    Call<Token> getSESION(@Query("request_token") String request_token);

    @POST("movie/{movie_id}/rating?" + ApiConstants.api_key)
    Call<Film> postUserRating(@Path("movie_id") int movie_id, @Query("session_id") String session_id, @Header("json/application") String json, @Body Rated body);

    @GET("account/{account_id}/rated/movies?" + ApiConstants.api_key)
    Call<FilmModel> getRated(@Query("session_id") String session_id);



}
