package com.example.dp_sms_brana;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dp_sms_brana.database.DatabaseHandler;

public class Statistics extends Activity{
	//Context context;
	   public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.statistics);
	
	DatabaseHandler databaseHandler =new DatabaseHandler(getApplicationContext());
	TextView countSms = (TextView) findViewById(R.id.countSms);
	countSms.setText("Poèet odeslaných SMS:  " + Integer.toString(databaseHandler.getMessagesCount()));
	   }
	}

