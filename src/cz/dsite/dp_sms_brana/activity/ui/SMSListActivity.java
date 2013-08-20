package cz.dsite.dp_sms_brana.activity.ui;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import cz.dsite.dp_sms_brana.R;

import cz.dsite.dp_sms_brana.activity.service.ConnectionInfoActivity;
import cz.dsite.dp_sms_brana.json.DataJSONParser;
import cz.dsite.dp_sms_brana.json.JSONParser;
import cz.dsite.dp_sms_brana.json.JSONfunctions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SMSListActivity extends Activity {
	JSONObject jObject;
	ListView listView = null;
	SimpleAdapter adapter = null;
	Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				try {
					// adresu na�teme pomoc� sd�len�ch hodnot nastaveen�aplikace
					String adress = SettingsActivity.getApiData(getApplicationContext());
					jObject = JSONfunctions.getJSONfromURL(adress);
					
					JSONParser messagesJsonParser = new DataJSONParser();
					if (jObject == null) {
						// return adapter;
					} else
						messagesJsonParser.parse(jObject);
				} catch (Exception e) {
					Log.d("JSON Exception1", e.toString());
				}

				//JSONParser dataJsonParser = new DataJSONParser();
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
										SMSListActivity.this,
										"Adresa pro sta�en� dat je nedostupn�!.",
										Toast.LENGTH_SHORT).show();
							} else
							/**
							 * Setting the adapter containing the country list
							 * to listview
							 */
							{
								if (adapter.isEmpty()) {
									Toast.makeText(SMSListActivity.this,
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
		if (ConnectionInfoActivity.isConnected(getApplicationContext())) { 
			try {
				thread.start();
			} catch (Exception e) {
			}
		} else {
			Toast.makeText(SMSListActivity.this,
					"Pro sta�en� dat je nutn� p�ipojen� k INTERNETU.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
