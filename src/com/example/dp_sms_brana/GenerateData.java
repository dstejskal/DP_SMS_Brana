package com.example.dp_sms_brana;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.dp_sms_brana.database.DatabaseHandler;

import content.Message;

public class GenerateData extends Activity {
	Context context;
	Message message;
	
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	DatabaseHandler databaseHandler=new DatabaseHandler(getApplicationContext());
	databaseHandler.saveMessage(new content.Message(25, 25, "sender1", "775665889", "02.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(26, 26, "sender1", "775665889", "02.01.2013", "Text", this.context));
	
	databaseHandler.saveMessage(new content.Message(27, 27, "sender2", "775665889", "03.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(28, 28, "sender2", "775665889", "03.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(29, 29, "sender3", "775665889", "03.01.2013", "Text", this.context));
	
	databaseHandler.saveMessage(new content.Message(30, 30, "sender2", "775665889", "05.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(31, 31, "sender4", "775665889", "05.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(32, 32, "sender5", "775665889", "05.01.2013", "Text", this.context));
	
	databaseHandler.saveMessage(new content.Message(33, 33, "sender1", "775665889", "06.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(34, 34, "sender5", "775665889", "06.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(35, 35, "sender2", "775665889", "06.01.2013", "Text", this.context));
	
	databaseHandler.saveMessage(new content.Message(36, 36, "sender4", "775665889", "08.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(37, 37, "sender5", "775665889", "08.01.2013", "Text", this.context));
	databaseHandler.saveMessage(new content.Message(38, 38, "sender8", "775665889", "08.01.2013", "Text", this.context));
	   }
	   }
