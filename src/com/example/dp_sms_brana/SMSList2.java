package com.example.dp_sms_brana;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SMSList2 extends Activity {
	JSONObject jObject;
	ListView listView = null;
	SimpleAdapter adapter = null;
	Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				try {
					// adresu naèteme pomocí sdílených hodnot nastaveeníaplikace
					String adresa = SettingsActivity
							.getApiData(getApplicationContext());
					jObject = JSONfunctions.getJSONfromURL2("http://dsweb.g6.cz/diplomka/api/user-data.php");
					
					DataJSONParser messagesJsonParser = new DataJSONParser();
					if (jObject == null) {
						// return adapter;
					} else
						messagesJsonParser.parse(jObject);
				} catch (Exception e) {
					Log.d("JSON Exception1", e.toString());
				}

				DataJSONParser dataJsonParser = new DataJSONParser();
				List<HashMap<String, Object>> message = null;

				try {
					/** Vytvoøení zprávy z JSON objektu */
					message = dataJsonParser.parse(jObject);
					/** Použité klíèe pro Hashmap */
					String[] from = { "text", "phone" };

					int[] to = { R.id.sms_text, R.id.sms_phone };

					//adaptér pro zprávy èekající na odeslání
					
					adapter = new SimpleAdapter(getBaseContext(), message,
							R.layout.lv_layout, from, to);
					setListView((ListView) findViewById(R.id.lv_countries));

					// vykreslení a výpisy je nutné provádìt v hlavním vláknì
					runOnUiThread(new Runnable() {
						public void run() {
							if (listView == null) {
								Toast.makeText(
										SMSList2.this,
										"Adresa pro stažení dat je nedostupná!.",
										Toast.LENGTH_SHORT).show();
							} else
							/**
							 * Setting the adapter containing the country list
							 * to listview
							 */
							{
								if (adapter.isEmpty()) {
									Toast.makeText(SMSList2.this,
											"Žádné SMS k odeslání",
											Toast.LENGTH_SHORT).show();
								} else {
									listView.removeAllViewsInLayout();
									listView.setAdapter(adapter);
								}

							}
						}
					});

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	});

	private void setListView(ListView lv) {
		listView = lv;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_list);

		// pokud je telefon pøipojen k internetu, stáhnu data
		if (ConnectionInfo.isConnected(getApplicationContext())) { 
			try {
				thread.start();
			} catch (Exception e) {
			}
		} else {
			Toast.makeText(SMSList2.this,
					"Pro stažení dat je nutné pøipojení k INTERNETU.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
