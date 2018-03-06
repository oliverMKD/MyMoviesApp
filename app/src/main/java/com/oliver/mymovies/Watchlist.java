package com.oliver.mymovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class Watchlist extends AppCompatActivity {

    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    public FilmModel model = new FilmModel();
    User user;
    RestApi api;
   public Film film = new Film();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        ButterKnife.bind(this);
        context = this;
        api = new RestApi(context);
        final String bbbb = SharedPrefferences.getSessionID(context);
        retrofit2.Call<FilmModel>call = api.getWatch(bbbb);
        call.enqueue(new Callback<FilmModel>() {
            @Override
            public void onResponse(retrofit2.Call<FilmModel> call, Response<FilmModel> response) {
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
                        public void onWatchClick(Film film, int position) {

                        }
                    });
                    adapter.setItems(model.results);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,2));
                    rv.setAdapter(adapter);
                    Toast.makeText(Watchlist.this, "successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Watchlist.this, "connection error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FilmModel> call, Throwable t) {

            }
        });
    }
}
