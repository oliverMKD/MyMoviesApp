package com.oliver.mymovies.adapteri;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliver.mymovies.R;
import com.oliver.mymovies.klasi.Cast;
import com.oliver.mymovies.klasi.Credits;
import com.oliver.mymovies.klasi.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oliver on 2/19/2018.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    public Context context;
   public ArrayList<Cast> castlist = new ArrayList<>();
    Film model;

    public CastAdapter (Context context, Credits credits){
        this.context=context;
        castlist = credits.cast;
    }


    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cast_row,parent,false);
        CastAdapter.ViewHolder viewHolder = new CastAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, final int position) {
        Cast cast1 = castlist.get(position);
        ArrayList<Cast> stars = new ArrayList<>();

//             cast1 = model.credits.cast.get(position);


            holder.imeime.setText("Stars: " + cast1.getName().toString());


        String image_url = cast1.getProfile_path();
        Picasso.with(context).load(image_url).fit().centerCrop().into(holder.slika);

    }

    @Override
    public int getItemCount() {
        if (castlist==null){
            return 0;
        }
        return castlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.slikaCast)
        ImageView slika;
        @BindView(R.id.imemajkati)
        TextView imeime;
        public ViewHolder(View itemView) {



            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
