package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.CastAdapter;
import com.oliver.mymovies.adapteri.RecyclerPeople;
import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Cast;
import com.oliver.mymovies.klasi.Credits;
import com.oliver.mymovies.klasi.Crew;
import com.oliver.mymovies.klasi.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullCast extends AppCompatActivity {

    Context context;
    @BindView(R.id.MyRV)
    RecyclerView rv;
    CastAdapter adapter;
    RestApi api;
    int pozicija;
    Film model;
    Credits credits;
    Cast cast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_cast);
        ButterKnife.bind(this);
        context = this;
        api = new RestApi(this);
        final Intent intent = getIntent();
        if (intent.hasExtra("details")) {
            pozicija = intent.getIntExtra("details", 0);
            GetCast();
        }
    }

    public void GetCast() {
        RestApi api = new RestApi(this);
        Call<Film> call = api.getCast(pozicija, "credits");
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    adapter = new CastAdapter(context,model.credits);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,1));
                    rv.setAdapter(adapter);
                }else  {
                    Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
            }
        });
    }
}
