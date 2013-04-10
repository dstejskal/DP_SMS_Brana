package com.example.dp_sms_brana;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.telephony.SmsManager;


public class SmsSender extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		sendSms("5556", "automat");
		return null;
	}
	
	 int MAX_SMS_MESSAGE_LENGTH=160;
	    public void sendSms(String phone,String message)
	    {
	        SmsManager manager = SmsManager.getDefault();

	        //PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
	        //PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
	    
	                int length = message.length();

	                if(length > MAX_SMS_MESSAGE_LENGTH)
	                {
	                        ArrayList<String> messagelist = manager.divideMessage(message);

	                        manager.sendMultipartTextMessage(phone, null, messagelist, null, null);
	                }
	                else
	                	
	                {
	                	if (phone.length()>0 && message.length()>0){
	                        //manager.sendTextMessage(phone, null, message, piSend, piDelivered);
	                		manager.sendTextMessage(phone, null, message, null, null);
	                	}
	                }
	       
	    }
	
	public void onPostExecute() {

       
	}

}
