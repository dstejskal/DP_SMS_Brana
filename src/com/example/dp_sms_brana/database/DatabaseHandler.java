package com.example.dp_sms_brana.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import content.Message;
import content.UserSettings;

public class DatabaseHandler {

private DBArchiveAdapter dBArchiveAdapter;
private DBSettingsAdapter dBSettingsAdapter;

private Context context;

public DatabaseHandler(Context context)
{
	this.context = context;
	dBArchiveAdapter = new DBArchiveAdapter(context);
	//nova tabulka kvuli nastaveni
	dBSettingsAdapter = new DBSettingsAdapter(context);
}

public void deleteMessage(Message message){
	dBArchiveAdapter.open();
	dBArchiveAdapter.deleteMessage(message.get_id());
	dBArchiveAdapter.close();
}

public void deleteSettings(UserSettings settings){
	dBSettingsAdapter.open();
	dBSettingsAdapter.deleteSettings(settings.getId());
	dBSettingsAdapter.close();
}

public void updateMessage(Message message){
	dBArchiveAdapter.open();
	dBArchiveAdapter.insertMessage(message.get_id(), message.getSender(), message.getRecipient(), message.getDate());
	dBArchiveAdapter.close();	
}

public void updateSettings(UserSettings  settings){
	dBSettingsAdapter.open();
	dBSettingsAdapter.insertSettings(settings.getInterval(), settings.getApiData(), settings.getApiSend(), settings.getApiStatus());
	dBSettingsAdapter.close();	
}

public long saveMessage(Message message){
	dBArchiveAdapter.open();
	long id=dBArchiveAdapter.insertMessage(message.getMid(), message.getSender(), message.getRecipient(), message.getDate());
	dBArchiveAdapter.close();
	return id;
}

public long saveSettings(UserSettings settings){
	dBSettingsAdapter.open();
	long id=dBSettingsAdapter.insertSettings(settings.getInterval(), settings.getApiData(), settings.getApiSend(), settings.getApiStatus());
	dBSettingsAdapter.close();
	return id;
}

public Message getMessage(long id)
{
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.fetchMessage(id);
	Message message=null;
	
	if (cursor != null && cursor.getCount() != 0)
	{
		cursor.moveToFirst();
		message = createMessageFromCursor(cursor);

		cursor.close();
	}
	
	dBArchiveAdapter.close();
	return message;
}

public UserSettings getSettings(long id)
{
	dBSettingsAdapter.open();	
	Cursor cursor=dBSettingsAdapter.fetchSettings(id);
	UserSettings settings=null;
	
	if (cursor != null && cursor.getCount() != 0)
	{
		cursor.moveToFirst();
		settings = createSettingsFromCursor(cursor);

		cursor.close();
	}
	
	dBArchiveAdapter.close();
	return settings;	
}

public int getMessagesCount(){
int count=-1;	
dBArchiveAdapter.open();
Cursor cursor=dBArchiveAdapter.getAllMessages();
count=cursor.getCount();
dBArchiveAdapter.close();
return count;
}

public ArrayList<Message> getMessagesOfDay(String date){
	
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.fetchMessageByDate(date);
	Message message=null;
	ArrayList<Message> messageList=null;
	if (cursor != null && cursor.getCount() != 0)
	{		
		cursor.moveToFirst();
		messageList=new ArrayList<Message>();
		
		while(!cursor.isAfterLast()){
			message = createMessageFromCursor(cursor);
			messageList.add(message);
			cursor.moveToNext();
		}


		cursor.close();
	}
	
	dBArchiveAdapter.close();
	return messageList;	
	
}

public int getCountMessagesOfDay(String date){
	int count=0;
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.fetchMessageByDate(date);
	count=cursor.getCount();
	cursor.close();
	dBArchiveAdapter.close();
	return count;		
}

public ArrayList<Message> getAllMessages(){
	
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.getAllMessages();
	Message message=null;
	ArrayList<Message> messageList=null;
	if (cursor != null && cursor.getCount() != 0)
	{		
		cursor.moveToFirst();
		messageList=new ArrayList<Message>();
		
		while(!cursor.isAfterLast()){
			message = createMessageFromCursor(cursor);
			messageList.add(message);
			cursor.moveToNext();
		}


		cursor.close();
	}
	
	dBArchiveAdapter.close();
	return messageList;	
	
}


private Message createMessageFromCursor(Cursor cursor)
{
	Message message = new Message(
			cursor.getLong(cursor.getColumnIndex(DBArchiveAdapter.COLUMN_ID)),
			cursor.getLong(cursor.getColumnIndex(DBArchiveAdapter.COLUMN_MID)),
			cursor.getString(cursor.getColumnIndex(DBArchiveAdapter.COLUMN_SENDER)),
			cursor.getString(cursor.getColumnIndex(DBArchiveAdapter.COLUMN_RECIPIENT)),
			cursor.getString(cursor.getColumnIndex(DBArchiveAdapter.COLUMN_DATE)), 
			context
			);
	return message;
}

private UserSettings createSettingsFromCursor(Cursor cursor)
{
	UserSettings settings = new UserSettings(
			cursor.getLong(cursor.getColumnIndex(DBSettingsAdapter.COLUMN_ID)),
			cursor.getInt(cursor.getColumnIndex(DBSettingsAdapter.COLUMN_INTERVAL)),
			cursor.getString(cursor.getColumnIndex(DBSettingsAdapter.COLUMN_API_DATA)),
			cursor.getString(cursor.getColumnIndex(DBSettingsAdapter.COLUMN_API_SEND)),
			cursor.getString(cursor.getColumnIndex(DBSettingsAdapter.COLUMN_API_STATUS)), 
			context
			);
	return settings;
}

}
