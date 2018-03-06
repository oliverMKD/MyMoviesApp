package com.oliver.mymovies.fragmenti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oliver.mymovies.Detali;
import com.oliver.mymovies.R;
import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.Rated;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oliver on 2/8/2018.
 */

public class NowPlaying extends Fragment {
    private Unbinder mUnbind;
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RecyclerViewAdapter adapter;
    ArrayList<Film> results = new ArrayList<>();
    FilmModel model = new FilmModel();
    User user;
    RestApi api;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeContainer;
    ProgressBar pd;
    public Film film = new Film();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nowplaying, null);
        mUnbind = ButterKnife.bind(this, view);
        api = new RestApi(getContext());
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
        public void onRefresh() {

            Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();
        }
    });


        Call<FilmModel> call = api.getNowPlaying();
        call.enqueue(new Callback<FilmModel>() {
            @Override
            public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {
                if (response.isSuccessful())  {

                    model = response.body();

                    adapter = new RecyclerViewAdapter(getActivity(), new OnRowClickListener() {
                        @Override
                        public void onRowClick(Film film, int position) {
                            Intent intent = new Intent(getActivity(),Detali.class);
                            intent.putExtra("details", film.id);
                            startActivity(intent);


                        }

                        @Override
                        public void onRatedClick(Film modelj, int id) {
                            api = new RestApi(getActivity());
            final Rated rated = new Rated();
            film = new Film();
            rated.value=5;
//            rated.id=film.id;
            String aaa = SharedPrefferences.getSessionID(getActivity());
            Call<Film> call = api.postUserRating(modelj.id,aaa,"json/application",rated);
            call.enqueue(new Callback<Film>() {
                @Override
                public void onResponse(Call<Film> call, Response<Film> response) {
                    if (response.isSuccessful()) {
                        final Film film1;
                        film1 = response.body();
                        Toast.makeText(getActivity(), "uspesno Rated", Toast.LENGTH_SHORT).show();
//                        SharedPrefferences.addRated("rated", getActivity());
                    }else if (response.code()==400){
                        Toast.makeText(getActivity(), "greska", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Film> call, Throwable t) {

                }
            });


                        }

                        @Override
                        public void onWatchClick(final Film model, int id) {
                            String aaa = SharedPrefferences.getSessionID(getActivity());
                            api = new RestApi(getActivity());
                            final com.oliver.mymovies.klasi.Watchlist watchlist = new com.oliver.mymovies.klasi.Watchlist();
                            watchlist.media_type = "movie";
                            watchlist.media_id =model.id;
                            watchlist.watchlist = true;
                            Call<Film>call1 = api.postWatch(film.id,aaa,"json/application",watchlist);
                            call1.enqueue(new Callback<Film>() {
                                @Override
                                public void onResponse(Call<Film> call, Response<Film> response) {
                                    if (response.isSuccessful()) {

                                        film = response.body();
                                        SharedPrefferences.addWatch("watch", getActivity());
                                        Toast.makeText(getActivity(), "uspesno watch", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Film> call, Throwable t) {

                                }
                            });
                        }
                    });
                    adapter.setItems(model.results);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

                    rv.setAdapter(adapter);
//                    pd.setVisibility(View.INVISIBLE);
                }
                else  {
                    Toast.makeText(getContext(), "greska", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmModel> call, Throwable t) {

            }



        });

        return view;
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Call<FilmModel> call = api.getNowPlaying();
                call.enqueue(new Callback<FilmModel>() {
                    @Override
                    public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {
                        if (response.isSuccessful()){
                            model = response.body();
                            swipeContainer.setRefreshing(false);

                            adapter = new RecyclerViewAdapter(getActivity(), new OnRowClickListener() {
                                @Override
                                public void onRowClick(Film film, int position) {
                                    Intent intent = new Intent(getActivity(),Detali.class);
                                    intent.putExtra("details", film.id);
                                    startActivity(intent);
                                }

                                @Override
                                public void onRatedClick(Film film, int id) {

                                }

                                @Override
                                public void onWatchClick(Film film, int position) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmModel> call, Throwable t) {

                    }
                });
                adapter.setItems(model.results);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
                rv.setAdapter(adapter);
                swipeContainer.setRefreshing(false);
                swipeContainer.setVisibility(View.INVISIBLE);
            }
        }, 5);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
    }


}
