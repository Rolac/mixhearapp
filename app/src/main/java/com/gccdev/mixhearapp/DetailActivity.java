package com.gccdev.mixhearapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
  //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.prova);
        String testo;

        if (getIntent().hasExtra("name")) {
             testo = getIntent().getStringExtra("name");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "name");
        }

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                  Contract.Songs.CONTENT_URI,
                null,
                null,
                null,
                null
                );
        if( cursor != null && cursor.moveToFirst() ){
            cursor.getColumnCount();
            testo = cursor.getString(cursor.getColumnIndex("_ID"));
            testo = testo;
            cursor.close();
        }


        textView.setText(testo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
