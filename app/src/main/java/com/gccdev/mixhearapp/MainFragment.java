package com.gccdev.mixhearapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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


    private Context context;
    private View rootView;
    private static final String NEW = "new";
    private ListView listView;
//    private Cursor mCursor;
    private CustomAdapter adapter;


    private CustomCursorAdapter cursorAdapter;
  //  private Uri songUri;
    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
      //  getActivity().getSupportLoaderManager().initLoader(1,null,this);


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
        getActivity().getSupportLoaderManager().initLoader(1,null,this);
//        String testo;
//        String urlImageMedium;

        listView = (ListView)rootView.findViewById(R.id.listview);
       // List<Song> result = new ArrayList<>();

        DataParser dataParser = new DataParser(context,rootView);
        dataParser.execute(NEW);

       // adapter = new CustomAdapter(context);
 //       ContentResolver resolver = context.getContentResolver();


//
//        String[] mProjection = {
//                Contract.Songs.COLUMN_ID,
//                Contract.Songs.COLUMN_TITLE,
//                Contract.Songs.COLUMN_PIC_MEDIUM};
//
//        mCursor = resolver.query(
//                Contract.Songs.CONTENT_URI,
//                mProjection,
//                null,
//                null,
//                null
//        );
 //       mCursor.close();
 //       if( cursor != null ) {
 //           cursor.moveToFirst();
 //           cursorAdapter = new CustomCursorAdapter(context,mCursor,0);
//            while (cursor.moveToNext()) {

//                testo = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
//                urlImageMedium = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_MEDIUM));
//
//                Song p = new Song(testo, urlImageMedium);
//                result.add(p);
 //                   cursorAdapter.bindView(rootView,context,cursor);
//               cursorAdapter = new CustomCursorAdapter(context,cursor);

 //           }
 //           cursorAdapter = new CustomCursorAdapter(context,cursor);

//        listView.setAdapter(cursorAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent(context,DetailActivity.class);
//               // mIntent.putExtra("name",name);
//                context.startActivity(mIntent);
//            }
//        });


 //       }


//        adapter = new CustomAdapter(context);

//        Song[] res = result.toArray(new
//
// Song[result.size()]);
//        adapter.setData(res);





        return rootView;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri songUri = Contract.Songs.CONTENT_URI;
        String[] mProjection = {
                Contract.Songs.COLUMN_ID,
                Contract.Songs.COLUMN_TITLE,
                Contract.Songs.COLUMN_PIC_MEDIUM};

        CursorLoader cursorLoader = new CursorLoader(
                context,
                songUri,
                mProjection,
                null,
                null,
                null);

        return cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    cursor.moveToFirst();
       // cursorAdapter = new CustomCursorAdapter(context,mCursor,0);
        adapter = new CustomAdapter(context,cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
