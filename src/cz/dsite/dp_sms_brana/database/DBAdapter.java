package cz.dsite.dp_sms_brana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/*Abstraktn� t��da DBAdapter je b�zovou pro v�echny t��dy tabulkov�ch adapt�r�.
 T��da umo��uje na��t�n� datab�ze, otev�en� datab�ze a tak� jej� uzav�en�.*/

public abstract class DBAdapter {
	
	protected Database database;
	protected SQLiteDatabase db;
	
	public DBAdapter(Context context)
	{
		database = new Database(context);
	}


	public void open()
	{
		db = database.getWritableDatabase();
	}


	public void close()
	{
		database.close();
	}
	
}
