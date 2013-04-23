package com.example.dp_sms_brana;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSmsToDatabase extends Activity {
	Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_send);        
 
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
 
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {                
                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                
                if (phoneNo.length()>0 && message.length()>0)                

                    updateSms("12345", message, phoneNo, "0000-00-00 00:00:00");
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
        });        
    }
    
	public void updateSms(String sender, String text, String phone, String date){ //nastaví SMS status na odesláno v MySql DB
		
		//http://www.helloandroid.com/tutorials/connecting-mysql-database
	    InputStream is = null;  
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("sender",sender));
		nameValuePairs.add(new BasicNameValuePair("text",text));
		nameValuePairs.add(new BasicNameValuePair("phone",phone));
		//nameValuePairs.add(new BasicNameValuePair("date",date));
		nameValuePairs.add(new BasicNameValuePair("password","secured")); //heslo pro zmìnu obsahu
		 
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://dsweb.g6.cz/diplomka/api/send-sms.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		//ukonèení aktivity
		this.finish();
		 		
	}

}
