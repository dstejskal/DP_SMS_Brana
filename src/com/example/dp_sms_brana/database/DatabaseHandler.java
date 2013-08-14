package com.example.dp_sms_brana.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.dp_sms_brana.entity.Message;
import com.example.dp_sms_brana.entity.UserSettings;


public class DatabaseHandler implements IDatabaseHandler {

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

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#deleteMessage(com.example.dp_sms_brana.entity.Message)
 */
public void deleteMessage(Message message){
	dBArchiveAdapter.open();
	dBArchiveAdapter.deleteMessage(message.get_id());
	dBArchiveAdapter.close();
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#deleteSettings(com.example.dp_sms_brana.entity.UserSettings)
 */
public void deleteSettings(UserSettings settings){
	dBSettingsAdapter.open();
	dBSettingsAdapter.deleteSettings(settings.getId());
	dBSettingsAdapter.close();
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#updateMessage(com.example.dp_sms_brana.entity.Message)
 */
public void updateMessage(Message message){
	dBArchiveAdapter.open();
	dBArchiveAdapter.insertMessage(message.get_id(), message.getSender(), message.getRecipient(), message.getDate());
	dBArchiveAdapter.close();	
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#updateSettings(com.example.dp_sms_brana.entity.UserSettings)
 */
public void updateSettings(UserSettings  settings){
	dBSettingsAdapter.open();
	dBSettingsAdapter.insertSettings(settings.getInterval(), settings.getApiData(), settings.getApiSend(), settings.getApiStatus());
	dBSettingsAdapter.close();	
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#saveMessage(com.example.dp_sms_brana.entity.Message)
 */
public long saveMessage(Message message){
	dBArchiveAdapter.open();
	long id=dBArchiveAdapter.insertMessage(message.getMid(), message.getSender(), message.getRecipient(), message.getDate());
	dBArchiveAdapter.close();
	return id;
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#saveSettings(com.example.dp_sms_brana.entity.UserSettings)
 */
public long saveSettings(UserSettings settings){
	dBSettingsAdapter.open();
	long id=dBSettingsAdapter.insertSettings(settings.getInterval(), settings.getApiData(), settings.getApiSend(), settings.getApiStatus());
	dBSettingsAdapter.close();
	return id;
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getMessage(long)
 */
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

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getSettings(long)
 */
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

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getMessagesCount()
 */
public int getMessagesCount(){
int count=-1;	
dBArchiveAdapter.open();
Cursor cursor=dBArchiveAdapter.getAllMessages();
count=cursor.getCount();
dBArchiveAdapter.close();
return count;
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getMessagesOfDay(java.lang.String)
 */
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

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#oldestMessage()
 */
public Message oldestMessage(){
	Message message=null;
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.oldestMessage();

	if (cursor != null && cursor.getCount() != 0)
	{	
    message = createMessageFromCursor(cursor);	
	}
	return message;	
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getCountMessagesOfDay(java.lang.String)
 */
public int getCountMessagesOfDay(String date){
	int count=0;
	dBArchiveAdapter.open();	
	Cursor cursor=dBArchiveAdapter.fetchMessageByDate(date);
	count=cursor.getCount();
	cursor.close();
	dBArchiveAdapter.close();
	return count;		
}

/* (non-Javadoc)
 * @see com.example.dp_sms_brana.database.IDatabaseHandler#getAllMessages()
 */
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