package com.oliver.mymovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.klasi.Token;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {
    @BindView(R.id.editUser)
    EditText user1;
    @BindView(R.id.editPass)
    EditText pass;
    @BindView(R.id.kopceLogIn)
    Button login;
    User user;
    RestApi api;
    Context context;
    Token tokenModel;
    String token;
    String token2;
    @BindView(R.id.textGostin)
    TextView gostin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.kopceLogIn)
    public void Klik (View v) {
        getToken();
    }



    public void getToken() {
        api = new RestApi(LogIn.this);
        {
            Call<Token> call = api.getToken("request_token");
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.code() == 200) {
                        tokenModel = response.body();
                        token = tokenModel.request_token;
                        getLogin();  // text polinja
                    } else if (response.code() == 401) {
                        Toast.makeText(LogIn.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LogIn.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void getLogin() {   // tuka se vnesuvaat stringovi za username pass
        final User data = new User(user1.getText().toString(), pass.getText().toString());
        api = new RestApi(LogIn.this);
                Call<Token> call = api.getLOGIN(  data.username, data.password, token);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {
                            tokenModel = response.body();
                            token2 = tokenModel.request_token;
                            SharedPrefferences.addUserID(token2,LogIn.this);
                            getSesion();
                        } else if (response.code() == 401) {
                            Toast.makeText(LogIn.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(LogIn.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

    }
    public void getSesion() {



        api = new RestApi(LogIn.this);
                Call<Token> call = api.getSESION(token2);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {
                            tokenModel=response.body();
                            String sesion=tokenModel.session_id;
                            SharedPrefferences.addSessionID(sesion,LogIn.this);
                            Intent intent = new Intent(LogIn.this, Home.class);
                            startActivity(intent);
                        } else if (response.code() == 401) {
                            Toast.makeText(LogIn.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(LogIn.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });


    }

    @OnClick(R.id.textGostin)
    public void KLik(View view){
        SharedPreferences prefferences = getSharedPreferences("MySharedPreffsFile",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}
