package com.example.dp_sms_brana.core;

import org.json.JSONObject;

import com.example.dp_sms_brana.activity.ui.SettingsActivity;
import com.example.dp_sms_brana.json.JSONfunctions;

import android.content.Context;

public class SmsObservable {
	Context context;
	 JSONObject json = JSONfunctions.getJSONfromURL(SettingsActivity.getApiData(context.getApplicationContext()));
}
