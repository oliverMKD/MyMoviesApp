package com.oliver.mymovies.klasi;

import java.io.Serializable;

/**
 * Created by Oliver on 2/18/2018.
 */

public class Cast implements Serializable {

   public String name;
   public String profile_path;

    String baseImageUrl = "http://image.tmdb.org/t/p/w500";

    public String getProfile_path() {
        return "http://image.tmdb.org/t/p/w500" +  profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
