package com.oliver.mymovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.RatedList;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rated extends AppCompatActivity {
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    public FilmModel model = new FilmModel();
    User user;
    RestApi api;
    Film film;
    Context context;
    RatedList ratedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated);
        ButterKnife.bind(this);
        context = this;
        api = new RestApi(this);
        final String aaa = SharedPrefferences.getSessionID(this);
        Call<FilmModel>call=api.getRated(aaa);
        call.enqueue(new Callback<FilmModel>() {
            @Override
            public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {
                if (response.isSuccessful()){
                    model = response.body();
                    adapter = new RecyclerViewAdapter(context, new OnRowClickListener() {
                        @Override
                        public void onRowClick(Film film, int position) {

                        }

                        @Override
                        public void onRatedClick(Film film, int id) {

                        }

                        @Override
                        public void onWatchClick(Film film, int id) {

                        }
                    });
                    adapter.setItems(model.results);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,2));
                    rv.setAdapter(adapter);
                } else {
                    Toast.makeText(Rated.this, "connection error.You are not logged in. Please, log in !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmModel> call, Throwable t) {
                Toast.makeText(Rated.this, "connection error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
