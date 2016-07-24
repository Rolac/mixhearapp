package com.gccdev.mixhearapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int URL_LOADER = 1;
    public static final String TAG = "list";
    private Context context;
    private View rootView;
    private static final String NEW = "new";
    private String selected;
    private RecyclerView listView;
    private CustomAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private Communication listener;

    public ListFragment(String selected) {
        this.selected = selected;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Communication)context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(URL_LOADER);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        context=getActivity();


        listView = (RecyclerView)rootView.findViewById(R.id.songList);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);




        getActivity().getSupportLoaderManager().initLoader(URL_LOADER,null,this);
        return rootView;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataParser dataParser = new DataParser(context,rootView);
        dataParser.execute(NEW);
        Uri songUri = Contract.Songs.CONTENT_URI;
        String[] mProjection = {
                Contract.Songs.COLUMN_ID,
                Contract.Songs.COLUMN_TITLE,
                Contract.Songs.COLUMN_PIC_MEDIUM,
                Contract.Songs.COLUMN_FAVORITE_COUNT,
                Contract.Songs.COLUMN_LISTENER_COUNT};

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

        if(adapter==null){

            adapter = new CustomAdapter(cursor);

            adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int id) {

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
    public void onLoaderReset(Loader<Cursor> loader) {
    adapter.swapCursor(null);
    }

    public interface Communication{
        void onItemChoosed(int id);
    }



}
