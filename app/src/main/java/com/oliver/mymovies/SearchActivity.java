package com.oliver.mymovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    ArrayList<Film> results = new ArrayList<>();
    FilmModel model = new FilmModel();
    User user;
    RestApi api;
    public Film film = new Film();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

    }
}
