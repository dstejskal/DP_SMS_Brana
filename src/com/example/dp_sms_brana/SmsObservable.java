package com.example.dp_sms_brana;

import org.json.JSONObject;

import android.content.Context;

public class SmsObservable {
	Context context;
	 // JSONObject json = JSONfunctions.getJSONfromURL("http://dsweb.g6.cz/diplomka/data.php");	
	 JSONObject json = JSONfunctions.getJSONfromURL(SettingsActivity.getApiData(context.getApplicationContext()));
}
