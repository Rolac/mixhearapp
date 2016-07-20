package com.gccdev.mixhearapp;

/**
 * Created by rolac on 18/07/16.
 */
public class Statements {
    private static final String START_STATEMENTS = "create table if not exists ";

    public static final String SONGS_CREATE_STATEMENT = START_STATEMENTS + Contract.Songs.TABLE_NAME + " (" +
            Contract.Songs.COLUMN_ID + " text primary key, " +
            Contract.Songs.COLUMN_CREATED_TIME + " text, " +
            Contract.Songs.COLUMN_LENGTH + " text, " +
            Contract.Songs.COLUMN_SLUG + " text, " +
            Contract.Songs.COLUMN_URL + " text, " +
            Contract.Songs.COLUMN_PLAY_COUNT + " integer default 0, " +
            Contract.Songs.COLUMN_FAVORITE_COUNT + " integer default 0, " +
            Contract.Songs.COLUMN_LISTENER_COUNT + " integer default 0, " +
            Contract.Songs.COLUMN_COMMENT_COUNT+ " integer default 0, " +
            Contract.Songs.COLUMN_PIC_MEDIUM + " text, " +
            Contract.Songs.COLUMN_PIC_LARGE + " text);";
}
