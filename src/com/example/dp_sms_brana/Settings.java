package com.example.dp_sms_brana;

import java.text.ParseException;

import com.example.dp_sms_brana.database.DatabaseHandler;

import content.UserSettings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	Context context; //p�id�no, nev�m jestli je pot�eba..
	EditText interval;
	EditText apiData;
	EditText apiSend;
	EditText apiStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
    public void saveSettings(View buttonSave) throws ParseException{
    	UserSettings settings=null;
    	interval=(EditText) findViewById(R.id.txtInterval);
    	apiData=(EditText) findViewById(R.id.txtAPIData);
    	apiSend=(EditText) findViewById(R.id.txtAPISend);
    	apiStatus=(EditText) findViewById(R.id.txtAPIStatus);
    	
    	settings=new UserSettings(/*Integer.parseInt(interval.toString())*/3, apiData.toString(), apiSend.toString(), apiStatus.toString(), context);
    	Toast.makeText(Settings.this, "Ukl�d�m nastaven�", Toast.LENGTH_SHORT).show(); 
    	 
    	if (settings!=null){
        DatabaseHandler databaseHandler=new DatabaseHandler(this.context);
        databaseHandler.saveSettings(settings);
        Toast.makeText(Settings.this, "Nastaven� ulo�eno", Toast.LENGTH_SHORT).show(); 
    	}else{
        Toast.makeText(Settings.this, "Nastala chyba p�i ukl�d�n�!", Toast.LENGTH_SHORT).show(); 	
    	}
        
    }

}
