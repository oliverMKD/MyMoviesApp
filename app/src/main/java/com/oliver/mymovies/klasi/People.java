package com.oliver.mymovies.klasi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Oliver on 2/18/2018.
 */

public class People implements Serializable {

    public String profile_path;
   public int id;
    public String name;
    public String birthday;
    public String biography;
    public String deathday;

    public People() {
    }

    ArrayList<KnownFor> known_for = new ArrayList<>();

    public ArrayList<KnownFor> getKnown_for() {
        return known_for;
    }
    String baseImageUrl = "http://image.tmdb.org/t/p/w500";

    public String getProfile_path(){
        return "http://image.tmdb.org/t/p/w500" + profile_path;
    }

    public void setKnown_for(ArrayList<KnownFor> known_for) {
        this.known_for = known_for;
    }

    public People(String profile_path, int id, String name) {
        this.profile_path = profile_path;
        this.id = id;
        this.name = name;
    }



    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
