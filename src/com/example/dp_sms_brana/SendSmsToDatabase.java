package com.example.dp_sms_brana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSmsToDatabase extends Activity {
	Button btnSendSMS;
	EditText txtPhoneNo;
	EditText txtMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_send);
    
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
		txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
		txtMessage = (EditText) findViewById(R.id.txtMessage);

		btnSendSMS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String phoneNo = txtPhoneNo.getText().toString();
				String message = txtMessage.getText().toString();

				if (phoneNo.length() > 0 && message.length() > 0)

					updateSms("12345", message, phoneNo, "0000-00-00 00:00:00");
				else
					Toast.makeText(getBaseContext(),
							"Pros�m zadejte ��slo a text zpr�vy.",
							Toast.LENGTH_SHORT).show();
			}
		});
	}

	// nastav� SMS status na odesl�no v MySql DB
	public void updateSms(String sender, String text, String phone, String date) {
		final String author = sender;
		final String content = text;
		final String tel = phone;
		final String day = date;


		new Thread(new Runnable() {
			public void run() {
				// http://www.helloandroid.com/tutorials/connecting-mysql-database
				InputStream is = null;
				String result = "";
				boolean success=false;
				HttpResponse response2=null;

				// http post
				try {
					HttpClient httpclient = new DefaultHttpClient();

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs
							.add(new BasicNameValuePair("sender", author));
					nameValuePairs.add(new BasicNameValuePair("text", content));
					nameValuePairs.add(new BasicNameValuePair("phone", tel));

					if(Token.getToken()==null){
					//Token nen� platn�, zad�m heslo a za��d�m o nov�
					Token.setToken(JSONfunctions.getTokenFromJSON(SettingsActivity.getApiSend(getApplicationContext())));			
				    //Token.setToken(JSONfunctions.getTokenFromJSON("http://dsweb.g6.cz/diplomka/api/send-sms.php"));	
					}
				    nameValuePairs.add(new BasicNameValuePair("token",Token.getToken())); 						
					nameValuePairs.add(new BasicNameValuePair("nick", "admin"));
					//identifik�tor u�ivatele mobiln�ho telefonu - mus� se shodovat s nickem v hlavn� datab�zi

					String adress=SettingsActivity.getApiSend(SendSmsToDatabase.this.getApplicationContext());
					//String adress="http://dsweb.g6.cz/diplomka/api/send-sms.php";
					HttpPost httppost = new HttpPost(adress);
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					int responseCode=response.getStatusLine().getStatusCode();
					//pokud vypr�ela platnost tokenu, je vr�cen k�d 449
					
					if(responseCode==449){
				    //nastav�m token a znova spust�m metodu pro odesl�n� SMS
						
				    Token.setToken(JSONfunctions.getTokenFromJSON(SettingsActivity.getApiSend(getApplicationContext())));		
					//Token.setToken(JSONfunctions.getTokenFromJSON("http://dsweb.g6.cz/diplomka/api/send-sms.php"));	
				    //mus�m ukon�it vl�kno

					HttpPost httppost2 = new HttpPost(adress);
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					response2 = httpclient.execute(httppost);
					}
					if (response2 != null) {
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
						success=true;
					} 

				} catch (ClientProtocolException e) {
					Log.e("log_tag", "Error in http connection " + e.toString());

				} catch (IOException e) {
					Log.e("log_tag", "Error IO " + e.toString());

				}

				// p�evod response na string
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
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
				// ukon�en� aktivity

			}
		}).start();
		Toast.makeText(SendSmsToDatabase.this,"SMS byla odesl�na do datab�ze ", Toast.LENGTH_LONG).show();
		this.finish();
	

	}

}
