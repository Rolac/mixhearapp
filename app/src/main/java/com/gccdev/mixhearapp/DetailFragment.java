package com.gccdev.mixhearapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    String val;

    private static final int URL_LOADER = 2;
    Bundle bundle = new Bundle();
    private View view,view2;
    public static final String TAG = "songDetail";
    private Context context;
    Cursor cursor;

    private RecyclerView recyclerView;
    private DetailAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(URL_LOADER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // view = inflater.inflate(R.layout.content_detail, container, false);
        view = inflater.inflate(R.layout.activity_detail, container, false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Ciao");


        context=getActivity();



      getActivity().getSupportLoaderManager().initLoader(URL_LOADER,null,this);
        return view;
    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String v = getArguments().get(Contract.Songs.COLUMN_ID).toString();
        Uri songUri = Contract.Songs.CONTENT_URI;
        String[] mProjection = {
                 Contract.Songs.COLUMN_ID,
                 Contract.Songs.COLUMN_TITLE,
                 Contract.Songs.COLUMN_AUTHOR,
                 Contract.Songs.COLUMN_CREATED_TIME,
                 Contract.Songs.COLUMN_LENGTH,
                 Contract.Songs.COLUMN_URL,
                 Contract.Songs.COLUMN_FAVORITE_COUNT,
                 Contract.Songs.COLUMN_COMMENT_COUNT,
                 Contract.Songs.COLUMN_LISTENER_COUNT,
                 Contract.Songs.COLUMN_PIC_LARGE
        };



        return new CursorLoader(
                        getActivity(),
                        songUri,
                        mProjection,
                Contract.Songs.COLUMN_ID + "= ?",
                        new String[]{v},
                        null);



    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();



        TextView textView = (TextView) view.findViewById(R.id.titolo);
        TextView lengthView = (TextView)view.findViewById(R.id.textLength);
       ImageView imageView = (ImageView) view.findViewById(R.id.imageSongLarge);
        TextView favoriteView = (TextView)view.findViewById(R.id.textFavorite);
        TextView commentView = (TextView)view.findViewById(R.id.textComment);
        TextView listenerView = (TextView)view.findViewById(R.id.textListener);
        TextView authorView = (TextView)view.findViewById(R.id.authorView) ;
        TextView createView = (TextView)view.findViewById(R.id.creationView) ;
        TextView urlView = (TextView)view.findViewById(R.id.urlView);


        String urlImageLarge = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Songs.COLUMN_PIC_LARGE));


       lengthView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_LENGTH)));
       textView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE)));
       favoriteView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_FAVORITE_COUNT))));
       commentView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_COMMENT_COUNT))));
       listenerView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_LISTENER_COUNT))));
       authorView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_AUTHOR)));
       createView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_CREATED_TIME)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_URL));

       urlView.setText(url);
            urlView.setMovementMethod(LinkMovementMethod.getInstance());

            Glide
                    .with(context)
                    .load(urlImageLarge)
                    .centerCrop()
                    .placeholder(R.drawable.ic_menu_gallery)
                    .into(imageView);
        }




    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public void onDestroy() {
         super.onDestroy();
    }


    public static DetailFragment newInstance(int id){

        Bundle args = new Bundle();
        args.putInt(Contract.Songs.COLUMN_ID, id);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        return detailFragment;

    }
}
