package com.oliver.mymovies.adapteri;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliver.mymovies.R;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.KnownFor;
import com.oliver.mymovies.klasi.People;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.model.PeopleModel;
import com.oliver.mymovies.onRow.OnRowCast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oliver on 2/18/2018.
 */

public class RecyclerPeople extends RecyclerView.Adapter<RecyclerPeople.ViewHolder> {
    public Context mContext;
    OnRowCast onRowCast;

    public List<People> peopleList = new ArrayList<>();
    PeopleModel model;

    public RecyclerPeople (Context mContext, PeopleModel model,OnRowCast _onrowCast) {
        this.mContext = mContext;
        peopleList=model.results;
        this.onRowCast=_onrowCast;
    }




    @Override
    public RecyclerPeople.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.people_row,parent,false);
        RecyclerPeople.ViewHolder viewHolder = new RecyclerPeople.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerPeople.ViewHolder holder, final int position) {
        final People people = peopleList.get(position);
        holder.textIme.setText(peopleList.get(position).getName());
        String image_url = peopleList.get(position).getProfile_path();
        Picasso.with(mContext).load(image_url).fit().into(holder.slika);
        for (KnownFor known : people.getKnown_for()  ) {
            holder.info.setText(holder.info.getText().toString() + known.getTitle());
        }
        holder.slika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRowCast.onRowClick(people,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (peopleList==null){
            return 0;
        }
        return peopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slika)
        ImageView slika;
        @BindView(R.id.tekst)
        TextView textIme;
        @BindView(R.id.desc)
        TextView info;
//        @BindView(R.id.data)
//        TextView rodenden;
//        @BindView(R.id.bio)
//        TextView biografija;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
