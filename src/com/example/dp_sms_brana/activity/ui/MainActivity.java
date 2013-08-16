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

		// obnoven� stavu po p�eklopen� displeje
		restoreMe(savedInstanceState);

		Toast.makeText(MainActivity.this, "Pro dal�� nab�dku stiskn�te menu ",
				Toast.LENGTH_LONG).show();
	}

	public void connectDatabase(View toogleDatabase) {
	
		if(running==true) {
		sender.stop();

	    //sender.stopTask();
	    //sender.cancel(true);
		Toast.makeText(MainActivity.this, "Odpojuji datab�zi",Toast.LENGTH_SHORT).show();
		running = false;
		}else{
			// AsynncTask lze spsutit pouze jednu, mus�m vytvo�it nov� instance
			sender = new SmsSender(getApplicationContext());
			sender.start();
			//sender.execute("");
			Toast.makeText(MainActivity.this, "P�ipojuji se k datab�zi",Toast.LENGTH_SHORT).show();
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
    //Hlavn� menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.queue:
			Toast.makeText(MainActivity.this, "Nahr�v�m data",
					Toast.LENGTH_SHORT).show();
			//Intent i = new Intent(this, SMSList.class);
			Intent i = new Intent(this, SMSListActivity.class);
			startActivity(i);
			return true;
		case R.id.send:
			/* Odesl�n� p�es GSM br�nu */
			/*
			 * Toast.makeText(MainActivity.this, "Odes�l�m SMS",
			 * Toast.LENGTH_SHORT).show(); Intent j = new
			 * Intent(this,SMS.class); startActivity(j);
			 */

			/* Odesl�n� zpr�vy do datab�ze */
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
			myMsg.setText("Autor: Bc. Drahoslav Stejskal,\n diplomov� pr�ce\n Univerzita Pardubice\n 2013\n\n"
					+ "webov� rozhran�: www.dsweb.g6.cz/diplomka");
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
		Toast.makeText(MainActivity.this, "P�epos�l�m nov� SMS",
				Toast.LENGTH_SHORT).show();
		
	}

	// ulo�en� stavu po p�eklopen� displeje
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean("connected", running);
	}

	// obnoven� stavu po p�eklopen� displeje
	private void restoreMe(Bundle state) {
		if (state != null) {
			running = state.getBoolean("connected");
		}

	}

}
