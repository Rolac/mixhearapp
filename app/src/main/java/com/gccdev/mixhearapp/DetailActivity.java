package com.gccdev.mixhearapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.prova);
        TextView lengthView = (TextView)findViewById(R.id.textLength);
        ImageView imageView = (ImageView)findViewById(R.id.imageSongLarge);
        TextView favoriteView = (TextView)findViewById(R.id.textFavorite);
        TextView commentView = (TextView)findViewById(R.id.textComment);
        TextView listenerView = (TextView)findViewById(R.id.textListener);
        String testo;
        String length = null;
        String urlImageLarge = null;
        String val = getIntent().getStringExtra("name");
        String favorite = null;
        String listener = null;
        String comment = null ;
        if (getIntent().hasExtra("name")) {
             testo = getIntent().getStringExtra("name");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "name");
        }

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                  Contract.Songs.CONTENT_URI,
                new String[]{Contract.Songs.COLUMN_TITLE, Contract.Songs.COLUMN_LENGTH,
                        Contract.Songs.COLUMN_PIC_LARGE,Contract.Songs.COLUMN_FAVORITE_COUNT,
                        Contract.Songs.COLUMN_COMMENT_COUNT,Contract.Songs.COLUMN_LISTENER_COUNT},
                Contract.Songs.COLUMN_TITLE + " =? ",
                new String[]{val},
                null
                );
        if( cursor != null && cursor.moveToFirst() ){
            testo  = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
            length = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_LENGTH));
            urlImageLarge = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_LARGE));
            favorite = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_FAVORITE_COUNT)));
            listener = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_LISTENER_COUNT)));
            comment = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_COMMENT_COUNT)));
            cursor.close();
        }

        lengthView.setText(length);
        textView.setText(testo);
        favoriteView.setText(favorite);
        commentView.setText(comment);
        listenerView.setText(listener);

        Glide
                .with(this)
                .load(urlImageLarge)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(imageView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
