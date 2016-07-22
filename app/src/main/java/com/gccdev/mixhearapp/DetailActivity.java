package com.gccdev.mixhearapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        val = getIntent().getIntExtra("position",0);
        getSupportLoaderManager().initLoader(1,null,this);

        String testo;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri songUri = Contract.Songs.CONTENT_URI;
        String[] mProjection = {
                Contract.Songs.COLUMN_ID,
                Contract.Songs.COLUMN_AUTHOR,
                Contract.Songs.COLUMN_CREATED_TIME,
                Contract.Songs.COLUMN_LENGTH,
                Contract.Songs.COLUMN_PIC_LARGE,
                Contract.Songs.COLUMN_FAVORITE_COUNT,
                Contract.Songs.COLUMN_COMMENT_COUNT,
                Contract.Songs.COLUMN_LISTENER_COUNT,
                Contract.Songs.COLUMN_URL,
                Contract.Songs.COLUMN_TITLE };
        String v = String.valueOf(val);
        CursorLoader cursorLoader = new CursorLoader(
                this,
                songUri,
                mProjection,
                Contract.Songs.COLUMN_ID + "=?",
                new String[]{v},
                null);

        return cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();


        TextView textView = (TextView)findViewById(R.id.titolo);
        TextView lengthView = (TextView)findViewById(R.id.textLength);
        ImageView imageView = (ImageView)findViewById(R.id.imageSongLarge);
        TextView favoriteView = (TextView)findViewById(R.id.textFavorite);
        TextView commentView = (TextView)findViewById(R.id.textComment);
        TextView listenerView = (TextView)findViewById(R.id.textListener);
        TextView authorView = (TextView)findViewById(R.id.authorView) ;
        TextView createView = (TextView)findViewById(R.id.creationView) ;
        TextView urlView = (TextView)findViewById(R.id.urlView);

        String  urlImageLarge = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_LARGE));



        lengthView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_LENGTH)));
        textView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE)));
        favoriteView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_FAVORITE_COUNT))));
        commentView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_COMMENT_COUNT))));
        listenerView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_LISTENER_COUNT))));
        authorView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_AUTHOR)));
        createView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_CREATED_TIME)));
        urlView.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_URL)));

        Glide
                .with(this)
                .load(urlImageLarge)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(imageView);
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.abandon();
    }
//    class Holder {
//
//    }
}
