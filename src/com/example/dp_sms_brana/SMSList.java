package com.example.dp_sms_brana;


import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SMSList extends Activity {
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_list);
 
        /** The parsing of the JSON data is done in a non-ui thread */
        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
 
        /** Start parsing JSON data */
        //listViewLoaderTask.execute(strJson);
        
        if(isConnected(getApplicationContext())){ //pokud je telefon pøipojen k internetu, stáhnu data
        listViewLoaderTask.execute();
        }else{       	
        Toast.makeText(SMSList.this, "Pro stažení dat je nutné pøipojení k INTERNETU.", Toast.LENGTH_SHORT).show(); 
        }
    }
    
	/**
	* Check the network state
	* @param context context of application
	* @return true if the phone is connected
	*/
	public static boolean isConnected(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
 
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{
 
        JSONObject jObject;
        /** Doing the parsing of xml data in a non-ui thread */
        @Override
        protected SimpleAdapter doInBackground(String... strJson) {
         
            try{
                //jObject = new JSONObject(strJson[0]);
                jObject = JSONfunctions.getJSONfromURL("http://dsweb.g6.cz/diplomka/data.php");

                
                DataJSONParser countryJsonParser = new DataJSONParser();
                countryJsonParser.parse(jObject);
            }catch(Exception e){
                Log.d("JSON Exception1",e.toString());
            }
 
            DataJSONParser countryJsonParser = new DataJSONParser();
 
            List<HashMap<String, String>> countries = null;
 
            try{
                /** Getting the parsed data as a List construct */
                countries = countryJsonParser.parse(jObject);
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
 
            /** Keys used in Hashmap */
            //String[] from = { "country","flag","details"};
            
            String[] from = { "text","phone"};
 
            /** Ids of views in listview_layout */
            //int[] to = { R.id.tv_country,R.id.iv_flag,R.id.tv_country_details};
            int[] to = { R.id.sms_text,R.id.sms_phone};
            /** Instantiating an adapter to store each items
            *  R.layout.listview_layout defines the layout of each item
            */
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.lv_layout, from, to);
 
            return adapter;
            
        }
 
        /** Invoked by the Android system on "doInBackground" is executed completely */
        /** This will be executed in ui thread */
        @Override
        protected void onPostExecute(SimpleAdapter adapter) {
             
            /** Getting a reference to listview of main.xml layout file */
            ListView listView = ( ListView ) findViewById(R.id.lv_countries);
 
            /** Setting the adapter containing the country list to listview */
            listView.setAdapter(adapter);
        }
    } 
}
