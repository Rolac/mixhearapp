package com.gccdev.mixhearapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CustomAdapter extends BaseAdapter{

    private Song[] listSong = new Song[0];
    private Context mActivity;
    View view;
    Cursor cursor;
    LayoutInflater inflater;

    public CustomAdapter(Context context, Cursor cursor){
        this.mActivity = context;
        this.cursor = cursor;
        inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return listSong[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        view = convertView;
        Holder holder;
        cursor.moveToPosition(position);
        final String title;
        // LayoutInflater inflater = LayoutInflater.from(mActivity);

        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);

        holder = new Holder();

        holder.tvTitle = (TextView) view.findViewById(R.id.textListItem);
        //  textView.setText(listSong[position].getName());
        holder.ivImage = (ImageView) view.findViewById(R.id.imageListItem);
        //   String url = listSong[position].getIMAGE_URL_MEDIUM();
        view.setTag(holder);
    }else{
            holder = (Holder)view.getTag();
        }
        title = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
        holder.tvTitle.setText(title);
        String url = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_MEDIUM));
        Glide
                .with(mActivity)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.ivImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivity,DetailActivity.class);
                        mIntent.putExtra("name",title);
                mActivity.startActivity(mIntent);

            }
        });

        return view;
    }
    class Holder {
        TextView tvTitle;
        ImageView ivImage;
    }

//    public void setData(Song[] list){
//        if(listSong != null){
//            this.listSong = list;
//        } else
//            Toast.makeText(mActivity, "Impossibile caricare i dati! ", Toast.LENGTH_SHORT).show();
//    }


}