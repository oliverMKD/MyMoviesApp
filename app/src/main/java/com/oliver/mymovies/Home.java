package com.oliver.mymovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.oliver.mymovies.model.FilmModel;
import com.oliver.mymovies.onRow.OnRowClickListener;
import com.oliver.mymovies.sharedPrefferences.SharedPrefferences;
//import com.oliver.mymovies.fragmenti.Popular;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    ProgressDialog pd;
    public static final String LOG_TAG=RecyclerViewAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        initViews();
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
//    private void initViews(){
//        pd = new ProgressDialog(this);
//        pd.setMessage("Fetching Movies");
//        pd.setCancelable(false);
//        pd.show();
//    }
    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity)context;
            }context=((ContextWrapper)context).getBaseContext();
        }return null;
    }

}
