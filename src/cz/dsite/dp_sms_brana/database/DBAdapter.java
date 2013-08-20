package cz.dsite.dp_sms_brana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/*Abstraktní tøída DBAdapter je bázovou pro všechny tøídy tabulkovıch adaptérù.
 Tøída umoòuje naèítání databáze, otevøení databáze a také její uzavøení.*/

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
