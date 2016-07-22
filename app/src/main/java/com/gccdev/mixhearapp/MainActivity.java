package com.gccdev.mixhearapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , MainFragment.Communication {

    View rootView;
    private static final String NEW = "new";
    private static final String HOT = "popular/hot";
    private static final String POPULAR = "popular";


    private FragmentManager fm;
    private MainFragment mainFragment;

    private boolean isTabletLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        rootView = findViewById(android.R.id.content);

        if (savedInstanceState == null && !isTabletLayout) {
            mainFragment = new MainFragment();
            fm.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .addToBackStack(MainFragment.TAG)
                    .commit();
        }
        else {
            fm.beginTransaction().replace(R.id.songList,mainFragment).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConnectivityManager conman = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNet = conman.getActiveNetworkInfo();



        if(!(activeNet != null && activeNet.isConnectedOrConnecting())){
            Snackbar.make(rootView, "Connessione Internet Assente", Snackbar.LENGTH_LONG)
                    .setAction("Action",null).show();
        }else {
            Snackbar.make(rootView, "Connessione Internet Presente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

      }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


        if(fm.getBackStackEntryCount()==0)
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DataParser dataParser = new DataParser(this,rootView);

        if (id == R.id.nav_new) {
           dataParser.execute(NEW);

        } else if (id == R.id.nav_popular) {
          dataParser.execute(POPULAR);

        } else if (id == R.id.nav_hot) {
            dataParser.execute(HOT);
        } else if (id == R.id.nav_search) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemChoosed(long id) {
//
//        if(roomDetailFragment==null)
//            roomDetailFragment = RoomDetailFragment.newInstance(id);
//
//        if(isTabletLayout)
//            fm.beginTransaction().replace(R.id.detailRoot, roomDetailFragment).commit();
//
//        else
//            fm.beginTransaction().replace(R.id.container, roomDetailFragment).addToBackStack(RoomDetailFragment.TAG).commit();
    }

}
