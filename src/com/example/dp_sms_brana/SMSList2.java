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
					// adresu na�teme pomoc� sd�len�ch hodnot nastaveen�aplikace
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
					/** Vytvo�en� zpr�vy z JSON objektu */
					message = dataJsonParser.parse(jObject);
					/** Pou�it� kl��e pro Hashmap */
					String[] from = { "text", "phone" };

					int[] to = { R.id.sms_text, R.id.sms_phone };

					//adapt�r pro zpr�vy �ekaj�c� na odesl�n�
					
					adapter = new SimpleAdapter(getBaseContext(), message,
							R.layout.lv_layout, from, to);
					setListView((ListView) findViewById(R.id.lv_countries));

					// vykreslen� a v�pisy je nutn� prov�d�t v hlavn�m vl�kn�
					runOnUiThread(new Runnable() {
						public void run() {
							if (listView == null) {
								Toast.makeText(
										SMSList2.this,
										"Adresa pro sta�en� dat je nedostupn�!.",
										Toast.LENGTH_SHORT).show();
							} else
							/**
							 * Setting the adapter containing the country list
							 * to listview
							 */
							{
								if (adapter.isEmpty()) {
									Toast.makeText(SMSList2.this,
											"��dn� SMS k odesl�n�",
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

		// pokud je telefon p�ipojen k internetu, st�hnu data
		if (ConnectionInfo.isConnected(getApplicationContext())) { 
			try {
				thread.start();
			} catch (Exception e) {
			}
		} else {
			Toast.makeText(SMSList2.this,
					"Pro sta�en� dat je nutn� p�ipojen� k INTERNETU.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
