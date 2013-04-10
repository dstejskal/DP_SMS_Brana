package com.example.dp_sms_brana;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public class DataJSONParser {
 
    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){
 
        JSONArray jCountries = null;
        try {
            /** Retrieves all the elements in the 'data' array */
        jCountries = jObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getCountries with the array of json object
        * where each json object represent a country
        */
        return getCountries(jCountries);
    }
 
    private List<HashMap<String, String>> getCountries(JSONArray jCountries){
        int countryCount = jCountries.length();
        List<HashMap<String, String>> countryList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> country = null;
 
        /** Taking each country, parses and adds to list object */
        for(int i=0; i<countryCount;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
                country = getCountry((JSONObject)jCountries.get(i));
                countryList.add(country);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        return countryList;
    }
 
    /** Parsing the Country JSON object */
    private HashMap<String, String> getCountry(JSONObject jCountry){
 
        HashMap<String, String> country = new HashMap<String, String>();

        String text = "";
        String phone="";

 
        try {

        	
            text = jCountry.getString("text");
            phone = jCountry.getString("phone");
            
            country.put("text", text);
            country.put("phone", phone);

 
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return country;
    }
}