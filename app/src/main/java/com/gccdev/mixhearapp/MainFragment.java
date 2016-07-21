package com.gccdev.mixhearapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.List;

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
        String testo;
        String urlImageMedium;
        ListView listView;

        List<Song> result = new ArrayList<>();

        DataParser dataParser = new DataParser(context,rootView);
        dataParser.execute(NEW);

        adapter = new CustomAdapter(context);
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
                Contract.Songs.CONTENT_URI,
                new String[]{Contract.Songs.COLUMN_TITLE, Contract.Songs.COLUMN_PIC_MEDIUM},
                null,
                null,
                null
        );

        if( cursor != null ) {
            while (cursor.moveToNext()) {

                testo = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
                urlImageMedium = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_MEDIUM));

                Song p = new Song(testo, urlImageMedium);
                result.add(p);


            }
            cursor.close();

        }

        listView = (ListView)rootView.findViewById(R.id.listview);
        adapter = new CustomAdapter(context);
        listView.setAdapter(adapter);
        Song[] res = result.toArray(new Song[result.size()]);
        adapter.setData(res);





        return rootView;
    }

}
