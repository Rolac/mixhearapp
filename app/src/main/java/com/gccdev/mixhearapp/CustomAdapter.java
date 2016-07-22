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

    private OnItemClickListener listener;

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
        final int ID;

        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);

        holder = new Holder();

        holder.tvTitle = (TextView) view.findViewById(R.id.textListItem);
        holder.ivImage = (ImageView) view.findViewById(R.id.imageListItem);
        view.setTag(holder);
    }else{
            holder = (Holder)view.getTag();
        }

        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE)));
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
                        mIntent.putExtra("position",position);
                mActivity.startActivity(mIntent);

            }
        });

        return view;
    }
    class Holder {
        TextView tvTitle;
        ImageView ivImage;
    }

    public void swapCursor(Cursor cursor){

        if(cursor==null){
            this.cursor.close();
        }

        else if(cursor!=this.cursor) {
            this.cursor.close();
            this.cursor = cursor;
            notifyDataSetChanged();
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(long id);
    }

}