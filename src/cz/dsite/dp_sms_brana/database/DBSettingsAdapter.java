package cz.dsite.dp_sms_brana.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBSettingsAdapter extends DBAdapter{

	public DBSettingsAdapter(Context context) {
		super(context);
	}
	
	public static final String TABLE_SETTINGS="settings";
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_INTERVAL="interval";
	public static final String COLUMN_API_DATA="apiData"; //id zprávy v serverové databázi
	public static final String COLUMN_API_SEND="apiSend";
	public static final String COLUMN_API_STATUS="apiStatus";
	
	public static final String DATABASE_CREATE_SETTINGS_TABLE="create table " + TABLE_SETTINGS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "+ COLUMN_INTERVAL+ " integer not null, " 
			+ COLUMN_API_DATA + " text not null," + COLUMN_API_SEND + " text not null, "
			+ COLUMN_API_STATUS + " text not null);";
	
	public long insertSettings(int interval,String apiData, String apiSend, String apiStatus)
	{
		ContentValues values = buildValues(interval, apiData, apiSend, apiStatus);
		return db.insert(TABLE_SETTINGS, null, values);
	}
	
	public Cursor getSettings()
	{
		return db.query(TABLE_SETTINGS, new String[] {COLUMN_INTERVAL, COLUMN_API_DATA, COLUMN_API_SEND, COLUMN_API_STATUS}, null, null, null, null,null);
	}
	
	public int deleteSettings(long id)
	{
		return db.delete(TABLE_SETTINGS, COLUMN_ID + "=" + id, null);
	}
	
	public Cursor fetchSettings(long id)
	{
		return db.query(TABLE_SETTINGS, null, COLUMN_ID + "=" + id, null, null, null, null);
	}
	
	public void updateSettings(long id, int interval,String apiData, String apiSend, String apiStatus)
	{
		ContentValues values = buildValues(interval, apiData, apiSend, apiStatus);
		db.update(TABLE_SETTINGS, values, COLUMN_ID + "=?" + id, null);
	}
	
	private ContentValues buildValues(int interval,String apiData, String apiSend, String apiStatus)
	{
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_INTERVAL, interval);
		values.put(COLUMN_API_DATA, apiData);
		values.put(COLUMN_API_SEND, apiSend);
		values.put(COLUMN_API_STATUS, apiStatus);
		return values;
	}
}
