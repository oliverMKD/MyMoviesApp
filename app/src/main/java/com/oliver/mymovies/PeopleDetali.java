package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.*;
import com.oliver.mymovies.klasi.People;
import com.oliver.mymovies.model.PeopleModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDetali extends AppCompatActivity {
    Context context;
    @BindView(R.id.tekst)
    TextView ime;
    @BindView(R.id.slika)
    ImageView Slika;
    @BindView(R.id.desc)
    TextView opis;
    @BindView(R.id.data)
    TextView datum;
    @BindView(R.id.bio)
    TextView biografija;
    int pozicija;
    public PeopleModel model = new PeopleModel();

    public com.oliver.mymovies.klasi.People person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detali);
        ButterKnife.bind(this);
        context=this;
        final Intent intent = getIntent();
        if (intent.hasExtra("details")) {
            pozicija = intent.getIntExtra("details", 0);
            Log.d("details",""+pozicija);
            PeopleDetails(pozicija);
        }



}
public void PeopleDetails(int person_id){
    RestApi api = new RestApi(context);
    Call<com.oliver.mymovies.klasi.People> call1 = api.getPerson(person_id);
    call1.enqueue(new Callback<com.oliver.mymovies.klasi.People>() {
        @Override
        public void onResponse(Call<com.oliver.mymovies.klasi.People> call, Response<com.oliver.mymovies.klasi.People> response) {
            People person = new People();
            PeopleModel model = new PeopleModel();
            person = response.body();
            ime.setText(person.getName());
            biografija.setText(person.biography);
            Picasso.with(context).load(person.getProfile_path()).fit().centerCrop().into(Slika);
            for (KnownFor known : person.getKnown_for()  ) {
                opis.setText(opis.getText().toString() + known.getTitle());
            }

            if (person.birthday != null){
                String bday = DateConvert(person.birthday);
                DateConvert(person.birthday);
                if (person.deathday == null){
                    datum.setText("Birthday: " + bday);
                }else {
                    String dday = DateConvert(person.deathday);
                    datum.setText("Birthday: " + bday + " Deathday: " + dday);
                }

            }


        }
        @Override
        public void onFailure(Call<com.oliver.mymovies.klasi.People> call, Throwable t) {

        }
    });
}
    public String DateConvert(String date){

        String str[] = date.split("-");
        String year = (str[0]);
        String month = (str[1]);
        String day = (str[2]);
        if (month.equals("01")){
            month = "January";
        }else if (month.equals("02")){
            month = "February";
        }else if (month.equals("03")){
            month = "March";
        }else if (month.equals("04")){
            month = "April";
        }else if (month.equals("05")){
            month = "May";
        }else if (month.equals("06")){
            month = "June";
        }else if (month.equals("07")){
            month = "July";
        }else if (month.equals("08")){
            month = "August";
        }else if (month.equals("09")){
            month = "September";
        }else if (month.equals("10")){
            month = "October";
        }else if (month.equals("11")){
            month = "November";
        }else if (month.equals("12")){
            month = "December";
        }

        return day + " " + month + " " + year;
    }
}
