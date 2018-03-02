package com.oliver.mymovies.klasi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oliver on 2/23/2018.
 */

public class Rated {

    @SerializedName("value")
    public int value;

    public int id;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
