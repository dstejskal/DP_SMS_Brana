package com.example.dp_sms_brana.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBArchiveAdapter extends DBAdapter{
  
	public DBArchiveAdapter(Context context) {
		super(context);
	}
	
	public static final String TABLE_ARCHIVE="archive";
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_MID="mid"; //id zprávy v serverové databázi
	public static final String COLUMN_SENDER="sender";
	public static final String COLUMN_RECIPIENT="recipient";
	public static final String COLUMN_DATE="date";
	
	public static final String DATABASE_CREATE_ARCHIVE_TABLE="create table " + TABLE_ARCHIVE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_MID + " integer not null," + COLUMN_SENDER + " text not null, "
			+ COLUMN_RECIPIENT + " string not null,"+ COLUMN_DATE+ " text not null);";
	

	public long insertMessage(long mid , String sender,String recipient, String date)
	{
		ContentValues values = buildValues(mid, sender, recipient, date);
		return db.insert(TABLE_ARCHIVE, null, values);
	}
	
	public Cursor getAllMessages()
	{
		return db.query(TABLE_ARCHIVE, new String[] { COLUMN_ID, COLUMN_MID, COLUMN_SENDER, COLUMN_RECIPIENT,
				COLUMN_DATE}, null, null, null, null, null);
	}

	
	public int deleteMessage(long id)
	{
		return db.delete(TABLE_ARCHIVE, COLUMN_ID + "=" + id, null);
	}
	
	public Cursor fetchMessage(long id)
	{
		return db.query(TABLE_ARCHIVE, null, COLUMN_ID + "=" + id, null, null, null, null);
	}
	
	/*Vrátí všechny zprávy ze dne date*/
	public Cursor fetchMessageByDate(String date)
	{
		return db.query(TABLE_ARCHIVE, null, COLUMN_DATE + "='" + date+"'", null, null, null, null);
	}
	
	public void updateMessage(long id, int mid , String sender,String recipient, String date)
	{
		ContentValues values = buildValues(mid, sender, recipient, date);
		db.update(TABLE_ARCHIVE, values, COLUMN_ID + "=?" + id, null);
	}
	
	
	private ContentValues buildValues(long mid , String sender,String recipient, String date)
	{
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_MID, mid);
		values.put(COLUMN_SENDER, sender);
		values.put(COLUMN_RECIPIENT, recipient);
		values.put(COLUMN_DATE, date);
		return values;
	}

}
