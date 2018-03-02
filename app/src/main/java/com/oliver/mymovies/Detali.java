package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.oliver.mymovies.klasi.*;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.VideoModel;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;
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
    @BindView(R.id.kopceSrce)
    ImageView favo;
    RestApi api;
    public Film model = new Film();
    public VideoModel videoModel = new VideoModel();
    public Video video = new Video();
    int pozicija;
    Context context;
    Crew crew;
    Cast cast;
    FilmModel filmModel;
    @BindView(R.id.kopceLink)
    Button link;
//    @BindView(R.id.kopceZvezda)
//    ImageView rating;
    @BindView(R.id.kopceSave)
    ImageView watch;
    com.oliver.mymovies.klasi.Rated rated = new com.oliver.mymovies.klasi.Rated();


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


//             @OnClick(R.id.kopceZvezda)
//                     public void klik5 (View v) {
//        onRatedClick();
//
//    }

    @OnClick(R.id.kopceSave)
    public void klik6 (View v) {

        onWatchClick();
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
                    EdenFavorit();
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
                            scenario.setText("");
                        }
                    }
                    if (writers.size() == 1) {
                        crew = writers.get(0);
                        scenario.setText(crew.name);
                    } else if (writers.size() > 1) {
                        for (int i = 0; i < 2; i++) {
                            crew = writers.get(i);
                            if (writers.size() > 0) {
                                scenario.setText(scenario.getText().toString() + crew.name + ", ");
                            }
                        }
                    }
                    scenario.setText("Writers: " + scenario.getText().toString());
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
                        textNaslov.setText("Title :"+model.getOriginalTitle());
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
    public void Klik(View view) {
        Intent intent2 = new Intent(this, FullCast.class);
        intent2.putExtra("details", pozicija);
        startActivity(intent2);
    }

    @OnClick(R.id.kopceSrce)
    public void klik(View view) {
        api = new RestApi(this);
        final Favorites favorites = new Favorites();
        favorites.media_type = "movie";
        favorites.media_id = model.id;
        favorites.favorite = true;
        final String aaa = SharedPrefferences.getSessionID(this);
        Call<Film> call1 = api.postFavorites(aaa, "json/application", favorites);
        call1.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()){
                    Film film = new Film();
                    film = response.body();
                    Picasso.with(context).load(R.drawable.favourites_full_mdpi).fit().centerCrop().into(favo);
                    favo.setClickable(false);
                    SharedPrefferences.addFavorites("favorites", context);
                    Toast.makeText(context, "added to favoriTI", Toast.LENGTH_SHORT).show();
                } else if (response.code()==401){
                    Toast.makeText(context, "Please Log In", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {

            }
        });
    }

    public void EdenFavorit() {
        RestApi api3 = new RestApi(this);
        final String aaa = SharedPrefferences.getSessionID(this);
        Call<Film> call2 = api3.getFavorit(pozicija, aaa);
        call2.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()){
                    model = response.body();

                    if (model.favorite==true){
                        Picasso.with(context).load(R.drawable.favourites_full_mdpi).fit().centerCrop().into(favo);
                        favo.setClickable(false);
                    } if (model.watchlist==true){
                        Picasso.with(context).load(R.drawable.watchlist_remove_mdpi).fit().centerCrop().into(watch);
                        watch.setClickable(false);
//                        rating.setVisibility(View.INVISIBLE);
//                        rating.setClickable(false);
                    }

//                    final int value = rated.value;
//                    if (value==model.id){
//                       rating.setVisibility(View.INVISIBLE);
//                    }
                }else if (response.code()==401){
                    Toast.makeText(context, "please log in !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Toast.makeText(context, "padna favorit", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @OnClick(R.id.kopceLink)
    public void Klik2(View view) {
        api = new RestApi(context);
        final Call<VideoModel> videoModelCall = api.getVideo(model.id);
        videoModelCall.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                if (response.isSuccessful()) {
                    videoModel = response.body();
                    video = videoModel.results.get(0);
                    if (video==null){
                        video=videoModel.results.get(1);
                    } else if (video==null){
                        video=videoModel.results.get(2);
                    }else if (response.code()==401){
                        Toast.makeText(context, "Please log in", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + video.key));
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {

            }
        });
    }

//    public void onRatedClick() {
//        api = new RestApi(context);
//        final com.oliver.mymovies.klasi.Rated rated = new com.oliver.mymovies.klasi.Rated();
//        rated.value = 5;
////        rated.id=model.id;
//        String aaa = SharedPrefferences.getSessionID(context);
//        Call<Film> call = api.postUserRating(model.id, aaa, "json/application", rated);
//        call.enqueue(new Callback<Film>() {
//            @Override
//            public void onResponse(Call<Film> call, Response<Film> response) {
//                if (response.isSuccessful()) {
//                    Film model1 = new Film();
//                    model1 = response.body();
//                    Toast.makeText(context, "added to Rated", Toast.LENGTH_SHORT).show();
//                    rating.setClickable(false);
//                    rating.setVisibility(View.INVISIBLE);
////                        SharedPrefferences.addRated("rated", getActivity());
//                } else if (response.code() == 401) {
//                    Toast.makeText(context, "please log in !", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Film> call, Throwable t) {
//                Toast.makeText(Detali.this, "neuspesno rated", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    public void onWatchClick() {
        String aaa = SharedPrefferences.getSessionID(context);
        api = new RestApi(context);
        final com.oliver.mymovies.klasi.Watchlist watchlist = new com.oliver.mymovies.klasi.Watchlist();
        watchlist.media_type = "movie";
        watchlist.media_id = model.id;
        watchlist.watchlist = true;
        Call<Film>call1 = api.postWatch(model.id,aaa,"json/application",watchlist);
        call1.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()){
                    Film model2 = new Film();
                    model2 = response.body();
                    Picasso.with(context).load(R.drawable.watchlist_remove_mdpi).fit().centerCrop().into(watch);
                    watch.setClickable(false);
                    SharedPrefferences.addWatch("watch", context);
                    Toast.makeText(context, "added to watchlist!", Toast.LENGTH_SHORT).show();
                } else if (response.code()==401){
                    Toast.makeText(context, "please log in !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {

            }
        });
    }
}