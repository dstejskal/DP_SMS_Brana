package com.example.dp_sms_brana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper{
	

	private static final String DATABASE_NAME = "archive.db";
	private static final int DATABASE_VERSION = 1;
	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DBArchiveAdapter.DATABASE_CREATE_ARCHIVE_TABLE);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.w(Database.class.getName(), "Aktualizuji databázi z verze " + oldVersion + " na " + newVersion
				+ ", stará data budou smazána");
	db.execSQL("DROP TABLE IF EXISTS " + DBArchiveAdapter.TABLE_ARCHIVE);
		
	onCreate(db);	
	}
	
}
