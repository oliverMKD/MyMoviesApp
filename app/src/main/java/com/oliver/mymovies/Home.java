package com.oliver.mymovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oliver.mymovies.adapteri.RecyclerViewAdapter;
import com.oliver.mymovies.adapteri.VPagerAdapter;
import com.oliver.mymovies.api.RestApi;
import com.oliver.mymovies.fragmenti.NowPlaying;
import com.oliver.mymovies.fragmenti.Popular;
import com.oliver.mymovies.fragmenti.TopRated;
import com.oliver.mymovies.fragmenti.Upcoming;
import com.oliver.mymovies.klasi.Film;
import com.oliver.mymovies.klasi.User;
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;
import com.squareup.picasso.Picasso;
//import com.oliver.mymovies.fragmenti.Popular;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public @BindView(R.id.vp)
    ViewPager vPage;
    @BindView(R.id.tablayout)TabLayout tabs;
    DrawerLayout drawer;
    VPagerAdapter adapter;
    RecyclerViewAdapter recyclerViewAdapter;
    Context context;
    FilmModel model;
    ArrayList<Film> results;
    @BindView(R.id.editSearch)
    EditText search;
    public Film film = new Film();
    User userActive = new User();
    Handler handler;
    @BindView(R.id.MyRV)
    RecyclerView rv;
    RestApi api;
    TextView username;
    TextView txtName;
    ImageView imgProfile;


    ProgressDialog pd;
    public static final String LOG_TAG=RecyclerViewAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        context = this;
        Home.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);
        txtName = (TextView) headerView.findViewById(R.id.Ime);
        username = (TextView) headerView.findViewById(R.id.textView);
        final String sessionID = SharedPrefferences.getSessionID(context);
        imgProfile = (ImageView) headerView.findViewById(R.id.profile_image);
        if(sessionID!=null && !sessionID.isEmpty())

        { api = new RestApi(context);
        Call<User>call = api.getAccountDetails(sessionID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                        userActive=response.body();
                        imgProfile.setMaxWidth(70);
                        imgProfile.setMaxHeight(70);
                    Picasso.with(Home.this).load("http://www.gravatar.com/avatar/"
                    +userActive.avatar.gravatar.hash).into(imgProfile);
                    txtName.setText(userActive.getName());
                    username.setText(userActive.username);
                    Toast.makeText(Home.this, "Uspesno Gravatar", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Home.this, "greska gravatar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        View view = navigationView.getHeaderView(0);
//        Menu menu = navigationView.getMenu();
//        String name2 = SharedPrefferences.getUser(this);
//        String token = Sha.getToken(this);
//        MenuItem logout_login = menu.findItem(R.id.login);
//        View headerView = navigationView.getHeaderView(0);
//        name3 = (TextView) headerView.findViewById(R.id.name);
//        if (!token.isEmpty()) {
//            name3.setText(name2);
//            logout_login.setTitle("Logout");
//            itemLog = "Logout";
//        } else {
//            name3.setText("Guest");
//            logout_login.setTitle("Login");
//            itemLog = "Login";
//        }

        final VPagerAdapter adapter = new VPagerAdapter(this.getSupportFragmentManager());
        adapter.dodadiFragment(new Popular(),"POPULAR");
        adapter.dodadiFragment(new TopRated(),"TOP RATED");
        adapter.dodadiFragment(new Upcoming(),"UPCOMING");
        adapter.dodadiFragment(new NowPlaying(),"NOW PLAYING");
        vPage.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("handler",s + "");
                        if (s.toString().isEmpty()){
                            rv.setVisibility(View.GONE);
                            vPage.setVisibility(View.VISIBLE);
                            tabs.setVisibility(View.VISIBLE);
                        }else{
                            String movie = search.getText().toString();
                            rv.setVisibility(View.VISIBLE);
                            vPage.setVisibility(View.GONE);
                            tabs.setVisibility(View.GONE);
                            MovieSearch(movie);}}},1000);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("EXIT").setMessage("Do you want to exit?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            // Handle the camera action
        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(this,Favourites.class);
            startActivity(intent);
//            drawer.closeDrawers();
        } else if (id == R.id.nav_rated) {
            Intent intent = new Intent(this,Rated.class);
            startActivity(intent);
//            drawer.closeDrawers();
        } else if (id == R.id.nav_watchlist) {
            Intent intent = new Intent(this,Watchlist.class);
            startActivity(intent);
//            drawer.closeDrawers();

        } else if (id == R.id.nav_people) {
            Intent intent = new Intent(this,People.class);
            startActivity(intent);
//            drawer.closeDrawers();

        } else if (id == R.id.nav_login) {
            SharedPreferences preferences = getSharedPreferences("MySharedPreffsFile",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity)context;
            }context=((ContextWrapper)context).getBaseContext();
        }return null;
    }

    public void MovieSearch(String link){
        RestApi api = new RestApi(context);
        Call<FilmModel> call = api.getSearchMovie(link);
        call.enqueue(new Callback<FilmModel>() {
            @Override
            public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {
                if (response.code() == 200) {
                    model = response.body();
                    recyclerViewAdapter = new RecyclerViewAdapter(context, new OnRowClickListener() {
                        @Override
                        public void onRowClick(Film film5, int position) {
                            Intent intent = new Intent(context, Detali.class);
                            intent.putExtra("details",film5.id);
                            startActivityForResult(intent,1111);
                        }

                        @Override
                        public void onRatedClick(Film film, int id) {

                        }

                        @Override
                        public void onWatchClick(Film film, int position) {

                        }
                    }) ;


                    recyclerViewAdapter.setItems(model.results);
                    rv.setHasFixedSize(true);
                    rv.setLayoutManager(new GridLayoutManager(context,2));
                    rv.setAdapter(recyclerViewAdapter);}}

            @Override
            public void onFailure(Call<FilmModel> call, Throwable t) {
            }});
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1111){
            String session_id = SharedPrefferences.getSessionID(context);
            pd.show();
            RestApi api = new RestApi(context);
            Call<FilmModel> call = api.getUserFavorites("account_id",session_id);
            call.enqueue(new Callback<FilmModel>() {
                @Override
                public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {
                    if (response.code() == 200) {
                        model = response.body();
                        recyclerViewAdapter = new RecyclerViewAdapter(context, new OnRowClickListener() {
                            @Override
                            public void onRowClick(Film film6, int position) {
                                Intent intent = new Intent(context,Detali.class);
                                intent.putExtra("details", film6.id);
                                startActivity(intent);
                            }

                            @Override
                            public void onRatedClick(Film film, int id) {

                            }

                            @Override
                            public void onWatchClick(Film film, int position) {
                            }
                            });
                        recyclerViewAdapter.setItems(model.results);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new GridLayoutManager(context,2));
                        rv.setAdapter(recyclerViewAdapter);
                        pd.dismiss();
                    }}
                @Override
                public void onFailure(Call<FilmModel> call, Throwable t) {
                }});}}




}
