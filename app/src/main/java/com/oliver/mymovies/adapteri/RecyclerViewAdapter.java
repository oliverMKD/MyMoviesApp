package com.oliver.mymovies.adapteri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.icu.util.ValueIterator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oliver.mymovies.Detali;
import com.oliver.mymovies.R;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.Rated;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oliver on 2/6/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public Context mContext;

    OnRowClickListener onRowClickListener;
    FilmModel modelfilms = new FilmModel();
    Film film111;
    RestApi api;



    public RecyclerViewAdapter (Context context,OnRowClickListener _onRowClick) {
        this.mContext = context;
        this.onRowClickListener = _onRowClick;
        }


    public void setItems(ArrayList<Film> movielist){

        this.modelfilms.results = movielist;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       Context context = parent.getContext();
       LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {

       final Film film2 = modelfilms.results.get(position);
        holder.naslov.setText("Title : " +modelfilms.results.get(position).getOriginalTitle());
        String vote = Double.toString(modelfilms.results.get(position).getAverageVote());
        holder.rejting.setText("Rating :" +vote);
        String image_url = modelfilms.results.get(position).getPosterPath();
        Picasso.with(mContext).load(image_url).fit().into(holder.film);

//        Glide.with(mContext).load(movieList.get(position).getPosterPath()).into(holder.film);


        holder.film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onRowClickListener.onRowClick(film2,position);
            }
        });

//        holder.zvezda.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRowClickListener.onRatedClick(film2,film2.id);
////
//            }
//        });
//
//        holder.kopcesave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onRowClickListener.onWatchClick(film2,film2.id);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return modelfilms.results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slikaFilm)
        ImageView film;
//        @BindView(R.id.kopceZvezda)
//        Button zvezda;
//        @BindView(R.id.kopceSave)
//        Button kopcesave;
        @BindView(R.id.textNaslov)
        TextView naslov;
        @BindView(R.id.textRejting)
        TextView rejting;
        @BindView(R.id.textSave)
        TextView textViewSave;
        RestApi api;
//        @BindView(R.id.kopceZvezdaStisnato)
//        Button zvezdaStisnata;
        public SharedPrefferences prefferences;
        public List<Film> movieList = new ArrayList<>();
        Rated rated;

        public ViewHolder(final View itemView) {


            super(itemView);
            ButterKnife.bind(this, itemView);



        }

    }
}
