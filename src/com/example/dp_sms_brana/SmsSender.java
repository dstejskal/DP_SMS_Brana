package com.example.dp_sms_brana;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.dp_sms_brana.database.DatabaseHandler;

import content.Message;

public class SmsSender extends AsyncTask<String, Void, String> {
	boolean sending = false;
	boolean running = false;
	Context context;
	Message message;

	public SmsSender(Context context) {
		this.context = context;
	}

	public boolean isRunning() {
		return running;
	}

	public void stopStatus() {
		running = false;
	}
	
    public void stopTask() {
    	this.cancel(true); 
    }

	@Override
	protected String doInBackground(String... arguments) {
		running = true;
		while (true) {
			try {
				// pokud již neodesílám zprávy, zkontroluji, zda nejsou ve frontì nové
				if (sending == false) 
				{
					// odeslání nových sms stažených pøes JSON, kontrola nových sms každých x vteøin dle nastavení uživatele .
					sendNewSms(); 
				}
				// uspání vlákna na x vteøin
				int timeSinceLastUpdate = 1000 * SettingsActivity
						.getInterval(this.context); 
				Thread.sleep(timeSinceLastUpdate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// return null;
	}

	int MAX_SMS_MESSAGE_LENGTH = 160;

	public void sendSms(String phone, String message) {
		SmsManager manager = SmsManager.getDefault();

		int length = message.length();

		if (length > MAX_SMS_MESSAGE_LENGTH) {
			ArrayList<String> messagelist = manager.divideMessage(message);

			manager.sendMultipartTextMessage(phone, null, messagelist, null,
					null);
		} else {
			if (phone.length() > 0 && message.length() > 0) {
				// manager.sendTextMessage(phone, null, message, piSend,
				// piDelivered);
				manager.sendTextMessage(phone, null, message, null, null);
			}
		}

	}

	public void sendNewSms() throws ParseException {

		DatabaseHandler databaseHandler = new DatabaseHandler(this.context);

		// pokud je dostupné pøipojení k internetu, stáhnu nová data
		if (ConnectionInfo.isConnected(context)) { 

			ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
			
			JSONObject json = JSONfunctions.getJSONfromURL(SettingsActivity.getApiData(this.context));
			//JSONObject json = JSONfunctions.getJSONfromURL2("http://dsweb.g6.cz/diplomka/api/user-data.php");
			if (json == null) {
				// špatný formát dat!
				return;
			}
			try {

				JSONArray messages = json.getJSONArray("data");

				if (messages.length() > 0)
					sending = true;

				for (int i = 0; i < messages.length(); i++) {

					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject e = messages.getJSONObject(i);

					map.put("text", "text:" + e.getString("text"));
					map.put("phone", "phone: " + e.getString("phone"));

					// odstranìní diakritiky, problém s èeštinou na emulátorech,
					// nevím jak v reálné aplikaci funkce normalize az od API 9!
					String normalizedText = removeDiacritics(e
							.getString("text"));

					// odeslání zprávy
					String sendDate = e.getString("send_date");
					
					if (sendDate != "null" && sendDate != null && sendDate!="") {
						// SMS s naplánovaným èasem odesláním
						boolean send=CalendarFunctions.readyToSend(sendDate);
						if (CalendarFunctions.readyToSend(sendDate)) {
							sendSms(e.getString("phone"), normalizedText);
							updateSmsStatus(e.getInt("id"));
							
							message = new content.Message((long) e.getInt("id"),
									(long) e.getInt("id"), "sender",
									e.getString("phone"), CalendarFunctions.now(),
									e.getString("text"), this.context);
							databaseHandler.saveMessage(message); // vložení do SQLite
							mylist.add(map);
						}
					} else{
						sendSms(e.getString("phone"), normalizedText);
						updateSmsStatus(e.getInt("id"));	
						
						message = new content.Message((long) e.getInt("id"),
								(long) e.getInt("id"), "sender",
								e.getString("phone"), CalendarFunctions.now(),
								e.getString("text"), this.context);
						databaseHandler.saveMessage(message); // vložení do SQLite
						mylist.add(map);
					}
						
				}

				sending = false;

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
		}

	}
	// nastaví SMS status na odesláno v databázi
	public void updateSmsStatus(int id) { 		
		// http://www.helloandroid.com/tutorials/connecting-mysql-database
		InputStream is = null;
		String result = "";
		// the year data to send
		String ids = Integer.toString(id);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(Token.getToken()==null){
			Token.setToken(JSONfunctions.getTokenFromJSON(SettingsActivity.getApiStatus(this.context)));
		}
		nameValuePairs.add(new BasicNameValuePair("id", ids));
		nameValuePairs.add(new BasicNameValuePair("token", Token.getToken())); 
		nameValuePairs.add(new BasicNameValuePair("nick", "admin")); 
		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(SettingsActivity.getApiStatus(this.context));
			//HttpPost httppost = new HttpPost("http://dsweb.g6.cz/diplomka/api/update-sms-status.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			
			int responseCode=response.getStatusLine().getStatusCode();
			if (responseCode==449){
			Token.setToken(null);	
			//spustíme metodu znova, naèteme pøi tom nový token
			updateSmsStatus(id);
			}
			
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

	}

	public static String removeDiacritics(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll(
				"\\p{InCombiningDiacriticalMarks}+", "");
	}

}
