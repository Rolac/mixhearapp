package com.gccdev.mixhearapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rolac on 18/07/16.
 */
public class Provider extends ContentProvider {

    private Db db = null;
    private static final String TAG = "provider";
    private static final int CODE_SONGS = 100;
    private static final int CODE_SONG = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.Songs.TABLE_NAME,CODE_SONGS);

        matcher.addURI(authority, Contract.Songs.TABLE_NAME + "/#", CODE_SONG);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = new Db(getContext());
       return true;
    }

    @Nullable
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)){

            case CODE_SONGS:

                cursor = db.getReadableDatabase().query(
                        Contract.Songs.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, null
                );

                break;

            case CODE_SONG:

                cursor = db.getReadableDatabase().query(
                        Contract.Songs.TABLE_NAME,
                        projection,
                        Contract.Songs.COLUMN_ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, null
                );

                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        Log.d(TAG, "query: " + uri.toString() + ", " + cursor.getCount()  );

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String type;

        switch (sUriMatcher.match(uri)) {

            case CODE_SONGS:
                type = Contract.Songs.CONTENT_TYPE;
                break;

            case CODE_SONG:
                type = Contract.Songs.CONTENT_ITEM_TYPE;
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Log.d(TAG, "getType: " + uri.toString() + ", " + type);

        return type;

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        long id;


        switch (sUriMatcher.match(uri)){

            case CODE_SONGS:

                id = db.getWritableDatabase().insert(
                        Contract.Songs.TABLE_NAME,
                        null,
                        values
                );

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        returnUri = ContentUris.withAppendedId(uri, id);

        Log.d(TAG, "insert: " + uri.toString() + ", " + returnUri.toString());

        if(!returnUri.getLastPathSegment().equals("-1"))
            getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows;

        final SQLiteDatabase myDb = db.getWritableDatabase();

        switch (sUriMatcher.match(uri)){

            case CODE_SONGS:
                rows = myDb.delete(Contract.Songs.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(rows>0)
            getContext().getContentResolver().notifyChange(uri, null);

        Log.d(TAG, "delete: " + uri.toString() + ", " + rows);

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case CODE_SONG:
                rowsUpdated = db.getWritableDatabase().update(
                        Contract.Songs.TABLE_NAME,
                        values,
                        Contract.Songs.COLUMN_ID + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;
            case CODE_SONGS:
                rowsUpdated = db.getWritableDatabase().update(
                        Contract.Songs.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if(rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        String msg = "update: " + uri.toString() + ", " + rowsUpdated;
        Log.d(TAG, msg);

        return rowsUpdated;
    }
}
