package com.example.dp_sms_brana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;

public class JSONfunctions {

	private static HttpResponse getResponse(String url, ArrayList<NameValuePair> nvp){
			
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		// heslo pro ovìøení identity
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nvp));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			return response;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}

	public static JSONObject getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// pøidání hesla pro pøístup k API
		
		//pøi prvním spuštìní zažádám o token
		if (Token.getToken()==null) {
			Token.setToken(getTokenFromJSON(url));
		}
		nameValuePairs.add(new BasicNameValuePair("nick", "admin"));
		nameValuePairs.add(new BasicNameValuePair("token", Token.getToken()));

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// heslo pro ovìøení identity
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			//získání návratového kódu - 449 = nutná reautorizace (døíve 221)
			int responseCode=response.getStatusLine().getStatusCode();
			if (responseCode==449){
			Token.setToken(null);	
			//spustíme metodu znova, naèteme pøi tom token
			getJSONfromURL(url);
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

		try {

			jArray = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}
	public static String getTokenFromJSON(String url){
		String token="";
		String result="";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("password", "480b007d9299f2df257708931c29ae38"));
		nameValuePairs.add(new BasicNameValuePair("nick", "admin"));
		try {
			HttpResponse response = getResponse(url,nameValuePairs);   
			//naètu si tokenka z JSON
			try{
			HttpEntity entity = response.getEntity();
			InputStream  is = entity.getContent(); //inputStream
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();			    
			
			
			String line = null;
		    try {
			    while ((line = reader.readLine()) != null)
			    {
			        sb.append(line + "\n");
			    }	
			} catch (Exception e) {
				// TODO: handle exception
			}

		    result = sb.toString();
		    
		    //vytvoøení JSON objektu
		    try {
		    	JSONObject jObject = new JSONObject(result);
			    //získání tokenu
		        Token.setToken(jObject.getString("token"));
				//token = jObject.getString("token");
			    
			}catch(Exception e){}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(Exception e){}	
		return Token.getToken();
		//return token;
	}	

}

