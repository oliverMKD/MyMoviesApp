package com.oliver.mymovies.klasi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Oliver on 3/1/2018.
 */

public class Video implements Serializable {

    @SerializedName("site")
    public String site;

    @SerializedName("key")
    public String key;

    @SerializedName("id")
    public String id;
}
