package com.oliver.mymovies.api;
import com.oliver.mymovies.Watchlist;
import com.oliver.mymovies.klasi.Favorites;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.Rated;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;
import com.oliver.mymovies.model.VideoModel;

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

//    RATED E DOBRO I POST I GET !!!!!
    @POST("movie/{movie_id}/rating?" + ApiConstants.api_key)
    Call<Film> postUserRating( @Path("movie_id") int movie_id,@Query("session_id") String session_id, @Header("json/application") String json, @Body Rated body);

    @GET("account/account_id/rated/movies?" + ApiConstants.api_key)
    Call<FilmModel> getRated(@Query("session_id") String session_id);
//    /////////////////////////////////////////////////////////////////////////

//        FAVORITES SE DOBRI TRITE
    @GET("account/account_id/favorite/movies?" + ApiConstants.api_key)
    Call<FilmModel> getFavorites (@Query ("session_id") String sessionId) ;

    @POST("account/account_id/favorite?" + ApiConstants.api_key)
    Call<Film> postFavorites(@Query("session_id") String session_id, @Header("json/application") String json, @Body Favorites body);

    ///////////////////////////////////////////////////////////////////////////////////////////


    @GET(" movie/{movie_id}/account_states?" + ApiConstants.api_key)
    Call<Film> getFavorit (@Path("movie_id") int id, @Query("session_id") String sessionId);

//////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////


    @POST("account/{account_id}/watchlist?" + ApiConstants.api_key)
    Call<Film> postWatch(@Path("account_id") int account_id,@Query("session_id") String session_id, @Header("json/application") String json, @Body com.oliver.mymovies.klasi.Watchlist body);

    @GET("account/account_id/watchlist/movies?" + ApiConstants.api_key)
    Call<FilmModel> getWatch (@Query ("session_id") String sessionId) ;


////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("movie/{movie_id}/videos?" + ApiConstants.api_key)
    Call<VideoModel> getVideo(@Path("movie_id") int pozicija);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("search/movie?" + ApiConstants.api_key)
    Call<FilmModel> getSearchMovie (@Query ("query") String query) ;



}

