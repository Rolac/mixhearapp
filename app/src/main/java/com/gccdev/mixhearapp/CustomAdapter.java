package com.gccdev.mixhearapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context mActivity;

    Cursor cursor;
    LayoutInflater inflater;

    private OnItemClickListener listener;

    public CustomAdapter( Cursor cursor){

        this.cursor = cursor;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            default: return new ViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final int id = cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_ID));
        final String text = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
        final String url = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_PIC_MEDIUM));

        buildHolder((ViewHolder) holder, id, text, url);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(cursor == null)
            return 0;
       return cursor.getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private void buildHolder(ViewHolder holder, final int id, String name, String image){


        Glide
              .with(holder.itemView.getContext())
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
               .into(holder.imageView);

        holder.nameView.setText(name);

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onItemClick(id);
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        ImageView imageView;
        LinearLayout rootLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
            nameView = (TextView)itemView.findViewById(R.id.textListItem);
            imageView = (ImageView)itemView.findViewById(R.id.imageListItem);
        }
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
        void onItemClick(int id);
    }



}