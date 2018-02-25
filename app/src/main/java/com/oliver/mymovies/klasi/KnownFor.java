package com.oliver.mymovies.klasi;

import java.io.Serializable;

/**
 * Created by Oliver on 2/18/2018.
 */

public class KnownFor implements Serializable {

    String title;

    public KnownFor(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
