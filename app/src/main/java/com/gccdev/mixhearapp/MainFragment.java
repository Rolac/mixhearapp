package com.gccdev.mixhearapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "list";
    private Context context;
    private View rootView;
    private static final String NEW = "new";

    private ListView listView;
    private CustomAdapter adapter;

    private Communication listener;

    public MainFragment() {

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        listener = (Communication)context;
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        getActivity().getSupportLoaderManager().destroyLoader(1);
//    }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        context=getActivity();

        listView = (ListView)rootView.findViewById(R.id.listview);

        DataParser dataParser = new DataParser(context,rootView);
        dataParser.execute(NEW);

        getActivity().getSupportLoaderManager().initLoader(1,null,this);
        return rootView;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri songUri = Contract.Songs.CONTENT_URI;
        String[] mProjection = {
                Contract.Songs.COLUMN_ID,
                Contract.Songs.COLUMN_TITLE,
                Contract.Songs.COLUMN_PIC_MEDIUM};

        return new CursorLoader(
                context,
                songUri,
                mProjection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    cursor.moveToFirst();
        adapter = new CustomAdapter(context,cursor);
        listView.setAdapter(adapter);

        if(adapter==null){

            adapter = new CustomAdapter(context,cursor);

            adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(long id) {

                    if(listener!=null)
                        listener.onItemChoosed(id);

                }
            });
        }

        else adapter.swapCursor(cursor);

        if(listView.getAdapter()==null)
            listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    adapter.swapCursor(null);
    }

    public interface Communication{
        void onItemChoosed(long id);
    }
}
