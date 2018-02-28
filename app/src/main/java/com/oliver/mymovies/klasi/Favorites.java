package com.oliver.mymovies.klasi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oliver on 2/25/2018.
 */

public class Favorites {
    @SerializedName("media_type")
    public String media_type;

    @SerializedName("media_id")
    public int media_id;
    @SerializedName("favorite")
    public boolean favorite;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return true;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
