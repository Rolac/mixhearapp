package com.gccdev.mixhearapp;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainFragment extends Fragment {


    private Context context;
    private View rootView;
    private static final String NEW = "new";
    private ListView mListView;
    private CustomAdapter adapter;


    private CustomCursorAdapter cursorAdapter;
    private Uri songUri;
    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            DataParser dataParser = new DataParser(context,rootView);
            dataParser.execute(NEW);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        context=getActivity();
        DataParser dataParser = new DataParser(context,rootView);
       dataParser.execute(NEW);


        ContentValues values = new ContentValues();
        values.put(Contract.Songs.COLUMN_ID,"TITOLO_PROVA");
        songUri = context.getContentResolver().insert(
                       Contract.Songs.CONTENT_URI,
                        values
                );




        return rootView;
    }

}
