package com.oliver.mymovies.model;

import com.google.gson.annotations.SerializedName;
import com.oliver.mymovies.klasi.Film;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Oliver on 2/8/2018.
 */

public class FilmModel implements Serializable {

    public ArrayList<Film> results;

}
