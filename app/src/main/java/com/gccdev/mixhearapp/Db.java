package com.gccdev.mixhearapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

public class Db extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "mixhearapp.db";
	private static final int DB_VERSION = 9;

	public Db(Context context){
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db){

        db.execSQL(Statements.SONGS_CREATE_STATEMENT);
       Log.v("DB","DATABASE CREATO");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + Contract.Songs.TABLE_NAME);
		onCreate(db);
		}


	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public void onConfigure(SQLiteDatabase db){
		super.onConfigure(db);
		db.setForeignKeyConstraintsEnabled(true);
	}


	
}
