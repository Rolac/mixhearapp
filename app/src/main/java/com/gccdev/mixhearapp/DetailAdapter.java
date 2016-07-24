package com.gccdev.mixhearapp;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by rolac on 23/07/16.
 */
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Cursor cursor;
    private int pos;

    public DetailAdapter(Cursor cursor , int p){
        this.cursor = cursor;
        this.pos = p;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            cursor.moveToPosition(pos);

            String title = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_TITLE));
            String urlImageLarge = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Songs.COLUMN_PIC_LARGE));
            String length = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_LENGTH));
            String favorite = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_FAVORITE_COUNT)));
            String comment = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_COMMENT_COUNT)));
            String listener = String.valueOf(cursor.getInt(cursor.getColumnIndex(Contract.Songs.COLUMN_LISTENER_COUNT)));
            String author = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_AUTHOR));
            String create = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_CREATED_TIME));
            String url = cursor.getString(cursor.getColumnIndex(Contract.Songs.COLUMN_URL));

            buildHolder((ViewHolder) holder, title, urlImageLarge, length, favorite, comment, listener, author, create, url);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
      /*  if(cursor == null)
            return 0;*/
        return cursor.getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView lengthView;
        ImageView imageView;
        TextView favoriteView;
        TextView commentView;
        TextView listenerView;
        TextView authorView;
        TextView createView;
        TextView urlView;
        RelativeLayout detailView;

        public ViewHolder(View itemView) {
            super(itemView);
            detailView = (RelativeLayout)itemView.findViewById(R.id.detailContent);

            textView = (TextView)itemView.findViewById(R.id.titolo);
            lengthView = (TextView)itemView.findViewById(R.id.textLength);
            imageView = (ImageView)itemView.findViewById(R.id.imageSongLarge);
            favoriteView = (TextView)itemView.findViewById(R.id.textFavorite);
            commentView = (TextView)itemView.findViewById(R.id.textComment);
            listenerView = (TextView)itemView.findViewById(R.id.textListener);
            authorView = (TextView)itemView.findViewById(R.id.authorView);
            createView = (TextView)itemView.findViewById(R.id.creationView);
            urlView = (TextView)itemView.findViewById(R.id.urlView);

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

    private void buildHolder(ViewHolder holder, String title, String image , String length , String favorite, String comment, String listener, String author, String create, String url ){


        Glide
                .with(holder.itemView.getContext())
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.imageView);

        holder.textView.setText(title);
        holder.lengthView.setText(length);
        holder.favoriteView.setText(favorite);
        holder.commentView.setText(comment);
        holder.listenerView.setText(listener);
        holder.authorView.setText(author);
        holder.createView.setText(create);
        holder.urlView.setText(url);



    }



}
