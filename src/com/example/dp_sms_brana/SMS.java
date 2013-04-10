package com.example.dp_sms_brana;


import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.app.PendingIntent;
//import android.content.Intent;


public class SMS extends Activity 
{
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
                SmsManager sms = SmsManager.getDefault();
                
                if (phoneNo.length()>0 && message.length()>0)                
                    //sendSMS(phoneNo, message);
                    sms.sendTextMessage(phoneNo, "android", message, null, null);
                //sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent)
                //Send a text based SMS.
                else
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
        });        
    }
    
    //metoda slouží pro automatické odesílání smsek
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
}