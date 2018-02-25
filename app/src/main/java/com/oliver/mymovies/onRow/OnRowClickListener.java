package com.oliver.mymovies.onRow;

import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.model.FilmModel;

/**
 * Created by Oliver on 2/6/2018.
 */

public interface OnRowClickListener {
    public void onRowClick(Film film, int position);
    public void onRatedClick(Film film, int id);

}
