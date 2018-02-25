package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Cast;
import com.oliver.mymovies.klasi.Crew;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.model.FilmModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detali extends AppCompatActivity {
    @BindView(R.id.textGore)
    TextView textNaslov;
    @BindView(R.id.slika_detali)
    ImageView slika;
    @BindView(R.id.textReziser)
    TextView reziser;
    @BindView(R.id.textScenario)
    TextView scenario;
    @BindView(R.id.textSvezdi)
    TextView svezdi;
    @BindView(R.id.kopceEkipa)
    Button ekipa;
    @BindView(R.id.textOpis)
    TextView opis;
    RestApi api;
    Film model;
    int pozicija ;
    Context context;
    Crew crew;
    Cast cast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detali);
        ButterKnife.bind(this);
        context = this;
        final Intent intent = getIntent();
        if (intent.hasExtra("details")) {
            pozicija = intent.getIntExtra("details", 0);
            GetMovie(pozicija);
        }
    }
    public void GetMovie(int id) {
        RestApi api = new RestApi(this);
        Call<Film> call = api.getMovie(id, "credits");
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()) {
                    ArrayList<Crew> writers = new ArrayList<>();
                    ArrayList<Cast> stars = new ArrayList<>();
                    model = response.body();
                    if (model.overview != null) {
                        opis.setText(model.overview);
                    } else {
                        opis.setText("No overview available");
                    }
                    if (model.credits.crew.size() > 0) {
                        for (int i = 0; i < 1; i++) {
                            crew = model.credits.crew.get(i);
                            reziser.setText("Director: " + crew.getName());
                        }
                    }
                    for (Crew crew : model.credits.crew) {
                        if (crew.department.equals("Writing")) {
                            writers.add(crew);
                        } else {
                            textNaslov.setText("");
                        }
                    }
                    if (writers.size() == 1) {
                        crew = writers.get(0);
                        textNaslov.setText(crew.name);
                    } else if (writers.size() > 1) {
                        for (int i = 0; i < 2; i++) {
                            crew = writers.get(i);
                            if (writers.size() > 0) {
                                textNaslov.setText(textNaslov.getText().toString() + crew.name + ", ");
                            }
                        }
                    }
                    textNaslov.setText("Writers: " + textNaslov.getText().toString());
                    if (model.credits.cast.size() >= 3) {
                        for (int i = 0; i < 3; i++) {
                            cast = model.credits.cast.get(i);
                            if (cast != null) {
                                stars.add(cast);
                            }
                        }
                    } else if (model.credits.cast.size() == 2) {
                        for (int i = 0; i < 2; i++) {
                            cast = model.credits.cast.get(i);
                            if (cast != null) {
                                stars.add(cast);
                            }
                        }
                    } else {
                        if (cast != null) {
                            stars.add(cast);
                        }
                    }
                    if (stars.size() > 0) {
                        for (int i = 0; i < stars.size(); i++) {
                            cast = stars.get(i);
                            if (stars.size() > 0) {
                                svezdi.setText(svezdi.getText().toString() + cast.getName() + ", ");
                            }
                        }
                        svezdi.setText("Stars: " + svezdi.getText().toString());
                    } else {
                        svezdi.setText("Stars: ");
                    }

                    String image_url = model.getPosterPath();
                    Picasso.with(context).load(image_url).fit().centerCrop().into(slika);

                }
            }
            @Override
            public void onFailure(Call<Film> call, Throwable t) {
            }
        });
    }

    @OnClick(R.id.kopceEkipa)
    public void Klik(View view){
        Intent intent2 = new Intent(this,FullCast.class);
        intent2.putExtra("details",pozicija);
        startActivity(intent2);
    }
}