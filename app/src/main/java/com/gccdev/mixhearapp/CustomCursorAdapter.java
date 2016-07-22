package com.gccdev.mixhearapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by rolac on 18/07/16.
 */
public class CustomCursorAdapter extends CursorAdapter{



    public CustomCursorAdapter(Context context, Cursor c,int flags) {
        super(context, c,flags);
        Log.v("CustoMmCursorAdapter","CustomAdapter");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       String title = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
       String imageMedium = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_MEDIUM));

        TextView textView = (TextView)view.findViewById(R.id.textListItem);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageListItem);
        textView.setText(title);
       // Log.v("CustoMmCursorAdapter",title);
        Glide
                .with(context)
                .load(imageMedium)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(imageView);
    }

}
