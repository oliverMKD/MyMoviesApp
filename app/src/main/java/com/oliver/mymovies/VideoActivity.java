package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {
    @BindView(R.id.videoText)
    TextView textView;
    public int pozicija;
    Context context;
    RestApi api;
    public Film model = new Film();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        context = this;
//        final Intent intent = getIntent();
//        if (intent.hasExtra("video")) {
//            pozicija = intent.getIntExtra("video", 0);
//            GetMovie(pozicija);
//        }


    }
//    public void GetMovie(int id){
//        api = new RestApi(context);
//        Call<Film> call = api.getVideo(pozicija);
//        call.enqueue(new Callback<Film>() {
//            @Override
//            public void onResponse(Call<Film> call, Response<Film> response) {
//                model = response.body();
//                textView.setText(model.getOriginalTitle());
//            }
//
//            @Override
//            public void onFailure(Call<Film> call, Throwable t) {
//
//            }
//        });
//    }
}
