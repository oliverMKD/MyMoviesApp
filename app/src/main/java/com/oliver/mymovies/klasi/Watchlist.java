package com.oliver.mymovies.klasi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oliver on 2/27/2018.
 */

public class Watchlist {

    @SerializedName("media_type")
    public String media_type;

    @SerializedName("media_id")
    public int media_id;
    @SerializedName("watchlist")
    public boolean watchlist;

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

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }
}
