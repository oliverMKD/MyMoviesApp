package com.oliver.mymovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerPeople;
import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class People extends AppCompatActivity {
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerPeople adapter;
    ArrayList<People> results = new ArrayList<>();
    PeopleModel model;
    RestApi api;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        context = this;
        ButterKnife.bind(this);
        api = new RestApi(this);

        Call<PeopleModel> call = api.getPersonPopular();
        call.enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(Call<PeopleModel> call, Response<PeopleModel> response) {
                if (response.isSuccessful()){
                    model = response.body();
                    adapter = new RecyclerPeople(context,model);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,1));
                    rv.setAdapter(adapter);
                }else  {
                    Toast.makeText(context, "greska", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PeopleModel> call, Throwable t) {
                Toast.makeText(People.this, "Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
