package com.gccdev.mixhearapp;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View rootView;
    private static final String NEW = "new";
    private static final String HOT = "hot";
    private static final String POPULAR = "popular";

    private Uri songUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(android.R.id.content);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SUPPORTO NAVBAR ED ALTRO
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Provider provider = new Provider();
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

//            ContentValues values = new ContentValues();
//            values.put(Contract.Songs.COLUMN_ID,"TITOLO PROVA");
//            songUri = getContentResolver().insert(
//                       Contract.Songs.CONTENT_URI,
//                        values
//            );



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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DataParser dataParser = new DataParser(this,rootView);
        insertNote("ciao");
        if (id == R.id.nav_new) {
           dataParser.execute(NEW);
           // new MainFragment(NEW);
        } else if (id == R.id.nav_popular) {
          dataParser.execute(POPULAR);

        } else if (id == R.id.nav_hot) {

        } else if (id == R.id.nav_search) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void insertNote(String text) {

        ContentValues values = new ContentValues();

        //values.put(DBOpenHelper.NOTE_TEXT, text);
        values.put(Contract.Songs.COLUMN_ID, text);

        Uri noteUri = getContentResolver().insert(
                Contract.Songs.CONTENT_URI,
                values
        );
        Log.d("MAIN_ACTIVITI", noteUri.getPath());
    }
}
