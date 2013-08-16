package com.example.dp_sms_brana.activity.ui;


import java.util.Observable;
import java.util.Observer;

import com.example.dp_sms_brana.R;

import com.example.dp_sms_brana.activity.service.SendSmsToDatabase;
import com.example.dp_sms_brana.core.ISmsSender;
import com.example.dp_sms_brana.core.SmsSender;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Observer {

	ISmsSender sender;
	//SmsSender sender;
	boolean running = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// obnovení stavu po pøeklopení displeje
		restoreMe(savedInstanceState);

		Toast.makeText(MainActivity.this, "Pro další nabídku stisknìte menu ",
				Toast.LENGTH_LONG).show();
	}

	public void connectDatabase(View toogleDatabase) {
	
		if(running==true) {
		sender.stop();

	    //sender.stopTask();
	    //sender.cancel(true);
		Toast.makeText(MainActivity.this, "Odpojuji databázi",Toast.LENGTH_SHORT).show();
		running = false;
		}else{
			// AsynncTask lze spsutit pouze jednu, musím vytvoøit nové instance
			sender = new SmsSender(getApplicationContext());
			sender.start();
			//sender.execute("");
			Toast.makeText(MainActivity.this, "Pøipojuji se k databázi",Toast.LENGTH_SHORT).show();
			running = true;	
		}
		//sender = new SmsSender(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return true;
	}
    //Hlavní menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.queue:
			Toast.makeText(MainActivity.this, "Nahrávám data",
					Toast.LENGTH_SHORT).show();
			//Intent i = new Intent(this, SMSList.class);
			Intent i = new Intent(this, SMSListActivity.class);
			startActivity(i);
			return true;
		case R.id.send:
			/* Odeslání pøes GSM bránu */
			/*
			 * Toast.makeText(MainActivity.this, "Odesílám SMS",
			 * Toast.LENGTH_SHORT).show(); Intent j = new
			 * Intent(this,SMS.class); startActivity(j);
			 */

			/* Odeslání zprávy do databáze */
			Intent smstodb = new Intent(this, SendSmsToDatabase.class);
			startActivity(smstodb);
			return true;
		case R.id.manage:
			
			Intent settingsActivity = new Intent(this, SettingsActivity.class);
			startActivity(settingsActivity);
			return true;
		case R.id.info:

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			TextView myMsg = new TextView(this);
			myMsg.setText("Autor: Bc. Drahoslav Stejskal,\n diplomová práce\n Univerzita Pardubice\n 2013\n\n"
					+ "webové rozhraní: www.dsweb.g6.cz/diplomka");
			myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
			alertDialog.setView(myMsg);
			alertDialog.show();
			return true;
		case R.id.stats:
			Intent graph = new Intent(this, StatisticsActivity.class);
			graph.putExtra("type", "line");
			startActivity(graph);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void update(Observable observable, Object data) {

		//sender.execute("");
		sender.start();
		Toast.makeText(MainActivity.this, "Pøeposílám nové SMS",
				Toast.LENGTH_SHORT).show();
		
	}

	// uložení stavu po pøeklopení displeje
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean("connected", running);
	}

	// obnovení stavu po pøeklopení displeje
	private void restoreMe(Bundle state) {
		if (state != null) {
			running = state.getBoolean("connected");
		}

	}

}
