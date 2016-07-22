package com.gccdev.mixhearapp;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by rolac on 18/07/16.
 */
public class Contract {
    public static final String CONTENT_AUTHORITY = "com.gccdev.mixhearapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class Songs {
        public static final String TABLE_NAME = "songs";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = TABLE_NAME + "_TITLE";
        public static final String COLUMN_LENGTH = TABLE_NAME + "_LENGTH";
        public static final String COLUMN_CREATED_TIME = TABLE_NAME + "createdTime";
         public static final String COLUMN_SLUG = TABLE_NAME + "slug";
        public static final String COLUMN_URL = TABLE_NAME + "url";
        public static final String COLUMN_PLAY_COUNT = TABLE_NAME + "playCount";
        public static final String COLUMN_FAVORITE_COUNT = TABLE_NAME + "favoriteCount";
        public static final String COLUMN_LISTENER_COUNT = TABLE_NAME + "listenerCount";
        public static final String COLUMN_COMMENT_COUNT = TABLE_NAME + "commentCount";
        public static final String COLUMN_PIC_MEDIUM = TABLE_NAME + "_PIC_MEDIUM";
       public static final String COLUMN_PIC_LARGE = TABLE_NAME + "_PIC_LARGE";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    }
}
