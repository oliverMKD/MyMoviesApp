package com.oliver.mymovies.fragmenti;

import android.content.Intent;
import android.os.Bundle;
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

public class Upcoming extends Fragment {
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
    Film film;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nowplaying, null);
        mUnbind = ButterKnife.bind(this, view);
        api = new RestApi(getContext());
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();
//            }
//        });


        Call<FilmModel> call = api.getUpcoming();
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
                        public void onRatedClick(Film film, int id) {
                            api = new RestApi(getActivity());
                            final Rated rated = new Rated();
                            rated.value=5;
                            String aaa = SharedPrefferences.getSessionID(getActivity());
                            Call<Film> call = api.postUserRating(film.id,aaa,"json/application",rated);
                            call.enqueue(new Callback<Film>() {
                                @Override
                                public void onResponse(Call<Film> call, Response<Film> response) {
                                    if (response.isSuccessful()) {
                                       final Film film = response.body();
                                        SharedPrefferences.addRated("rated", getActivity());
                                    }else if (response.code()==400){
                                        Toast.makeText(getActivity(), "greska", Toast.LENGTH_SHORT).show();
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
                else if (response.code() == 400) {
                    Toast.makeText(getContext(), "greska", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilmModel> call, Throwable t) {

            }



        }); return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
    }

}
