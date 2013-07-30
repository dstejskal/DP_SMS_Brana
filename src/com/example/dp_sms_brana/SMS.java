package com.example.dp_sms_brana;


import java.util.ArrayList;

import android.app.Activity;
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
                    sms.sendTextMessage(phoneNo, "android", message, null, null);

                else
                    Toast.makeText(getBaseContext(), 
                        "Zadejte prosím èíslo pøíjemce a text zprávy.", 
                        Toast.LENGTH_SHORT).show();
            }
        });        
    }
    
    //Metoda pro odeslání SMS pøes GSM sí
    int MAX_SMS_MESSAGE_LENGTH=160;
    public void sendSms(String phone,String message)
    {
        SmsManager manager = SmsManager.getDefault();    
                int length = message.length();

                if(length > MAX_SMS_MESSAGE_LENGTH)
                {
                        ArrayList<String> messagelist = manager.divideMessage(message);
                        manager.sendMultipartTextMessage(phone, null, messagelist, null, null);
                }
                else
                	
                {
                	if (phone.length()>0 && message.length()>0){
                		manager.sendTextMessage(phone, null, message, null, null);
                	}
                }
       
    }
}