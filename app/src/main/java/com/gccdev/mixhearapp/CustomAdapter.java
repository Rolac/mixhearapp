package com.gccdev.mixhearapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class CustomAdapter extends BaseAdapter{

    private Song[] listSong = new Song[0];
    private Context mainActivity;
    View view;

    public CustomAdapter(Context mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    public int getCount() {
        return listSong.length;
    }

    @Override
    public Object getItem(int position) {
        return listSong[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        view = convertView;

        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        if(view == null)
            view = inflater.inflate(R.layout.list_item, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textListItem);
        textView.setText(listSong[position].getName());
        ImageView imageView = (ImageView)view.findViewById(R.id.imageListItem);
        String url = listSong[position].getUrl();
        Glide
                .with(mainActivity)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = listSong[position].getName();

                Intent mIntent = new Intent(mainActivity,DetailActivity.class);
                        mIntent.putExtra("name",name);
                mainActivity.startActivity(mIntent);

            }
        });

        return view;
    }

    public void setData(Song[] list){
        if(listSong != null){
            this.listSong = list;
        } else
            Toast.makeText(mainActivity, "Impossibile caricare i dati! ", Toast.LENGTH_SHORT).show();
    }

}