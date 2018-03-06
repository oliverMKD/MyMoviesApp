package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerPeople;
import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;
import com.oliver.mymovies.onRow.OnRowCast;

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
    com.oliver.mymovies.klasi.People person = new com.oliver.mymovies.klasi.People();

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
                    final PeopleModel model;
                    model = response.body();
                    adapter = new RecyclerPeople(context, model, new OnRowCast() {
                        @Override
                        public void onRowClick(com.oliver.mymovies.klasi.People people, int position) {
                            Intent intent = new Intent(context,PeopleDetali.class);
                            Log.d("details",""+model.results.get(position).id);
                            intent.putExtra("details", model.results.get(position).id);
                            startActivity(intent);
                        }
                    });
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,1));
                    rv.setAdapter(adapter);
                }else  {
                    Toast.makeText(context, "connection error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PeopleModel> call, Throwable t) {
                Toast.makeText(People.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
