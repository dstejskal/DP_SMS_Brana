package com.example.dp_sms_brana.activity.ui;

import com.example.dp_sms_brana.R;
import com.example.dp_sms_brana.R.xml;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.webkit.URLUtil;

//T��da vyu��v� sharedPreferences - sd�len� nastaven� aplikace
//t��da implementuje OnSharedPreferenceChangeListener kv�li validaci vstupu.
public class SettingsActivity extends android.preference.PreferenceActivity
implements OnSharedPreferenceChangeListener{
	public static String interval="interval";
	public static String apiData="apiData";
	public static String apiSend="apiSend";
	public static String apiStatus="apiStatus";
	
	
    public static int getInterval(Context ctx) {
    	return Integer.valueOf(
    			PreferenceManager.getDefaultSharedPreferences(ctx).getString(interval,"5"));
	}


	public static String getApiData(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(apiData,"http://dsweb.g6.cz/diplomka/api/user-data.php");
	}


	public static String getApiSend(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(apiSend,"http://dsweb.g6.cz/diplomka/api/send-sms.php");
	}


	public static String getApiStatus(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(apiStatus,"http://dsweb.g6.cz/diplomka/api/update-sms-status.php");
	}


	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    
    @Override 
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        if ("interval".equals(key)) {
            String value = sharedPreferences.getString(key, "");
            try {
                int v = Integer.parseInt(value);
                // do whatever is needed
            } catch (RuntimeException e) {//pokud nen� zad�no ��slo, nastav�m interval na 30 s.
                String def ="30"; 
                EditTextPreference p = (EditTextPreference) findPreference(key);
                p.setText(def);
                //Zobraz�m hl�ku o chybn�m zad�n�
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Chybn� interval!");
                alertDialog.setMessage("Interval mus� b�t cel� ��slo, byl nastaven z�kladn� interval 30 sekund.");
                alertDialog.show();
            }
        }
        
        if ("apiData".equals(key)) {
            String value = sharedPreferences.getString(key, "");

            	if(URLUtil.isValidUrl(value)){           		
            	}else {
            		
                    String def ="http://dsweb.g6.cz/diplomka/api/data.php"; 
                    EditTextPreference p = (EditTextPreference) findPreference(key);
                    p.setText(def);
                    //Zobraz�m hl�ku o chybn�m zad�n�
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Chybn� adresa!");
                    alertDialog.setMessage("Zadejte adresu API ve spr�vnem tvaru, byla nastavena z�kladn� URL pro data.");
                    alertDialog.show();
            	}            
        }
        
        if ("apiSend".equals(key)) {
            String value = sharedPreferences.getString(key, "");

            	if(URLUtil.isValidUrl(value)){           		
            	}else {
            		
                    String def ="http://dsweb.g6.cz/diplomka/api/send-sms.php"; 
                    EditTextPreference p = (EditTextPreference) findPreference(key);
                    p.setText(def);
                    //Zobraz�m hl�ku o chybn�m zad�n�
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Chybn� adresa!");
                    alertDialog.setMessage("Zadejte adresu API ve spr�vnem tvaru, byla nastavena z�kladn� URL pro odes�l�n� SMS.");
                    alertDialog.show();
            	}            
        }
        
        if ("apiStatus".equals(key)) {
            String value = sharedPreferences.getString(key, "");

            	if(URLUtil.isValidUrl(value)){           		
            	}else {
            		
                    String def ="http://dsweb.g6.cz/diplomka/api/update-sms-status.php"; 
                    EditTextPreference p = (EditTextPreference) findPreference(key);
                    p.setText(def);
                    //Zobraz�m hl�ku o chybn�m zad�n�
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Chybn� adresa!");
                    alertDialog.setMessage("Zadejte adresu API ve spr�vnem tvaru, byla nastavena z�kladn� URL pro aktualizaci stavu SMS.");
                    alertDialog.show();
            	}            
        }
    }
}
