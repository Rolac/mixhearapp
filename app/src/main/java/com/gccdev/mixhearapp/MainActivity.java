package com.gccdev.mixhearapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ListFragment.Communication {

    private static final String TAG ="MainActivity";
    View rootView;
    private static final String NEW = "new";
    private static final String HOT = "popular/hot";
    private static final String POPULAR = "popular";

    private SwipeRefreshLayout refreshLayout;

    private FragmentManager fm;
    String selected = NEW;
    int mCurrentId = 0;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt("ID", mCurrentId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        rootView = findViewById(android.R.id.content);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        ListFragment listFragment;
        if (savedInstanceState == null) {

       drawer.setDrawerListener(toggle);
        toggle.syncState();

                listFragment = new ListFragment(selected);
                fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, listFragment)
                        .addToBackStack(ListFragment.TAG)
                        .commit();

        }
        else {

            mCurrentId = savedInstanceState.getInt("ID",mCurrentId);
            listFragment = new ListFragment(selected);
            fm.beginTransaction()
                    .replace(R.id.songList, listFragment)
                    .commit();
        }





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            DataParser dataParser = new DataParser(this,rootView);
            dataParser.execute(selected);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(fm.getBackStackEntryCount()==0)
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DataParser dataParser = new DataParser(this,rootView);

        if (id == R.id.nav_new) {
           dataParser.execute(NEW);
            selected = NEW;

        } else if (id == R.id.nav_popular) {
          dataParser.execute(POPULAR);
            selected = POPULAR;

        } else if (id == R.id.nav_hot) {
            dataParser.execute(HOT);
            selected = HOT;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    @Override
    public void onItemChoosed(int idx) {

        DetailFragment detailFragment = DetailFragment.newInstance(idx);




        detailFragment.setAllowEnterTransitionOverlap(true);
        detailFragment.setAllowReturnTransitionOverlap(true);

            fm.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .addToBackStack(DetailFragment.TAG)
                    .commit();


    }



}
