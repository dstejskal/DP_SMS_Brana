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
 
        JSONArray jMessages = null;
        try {
            /** Retrieves all the elements in the 'data' array */
        jMessages = jObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getCountries with the array of json object
        * where each json object represent a country
        */
        return getMessages(jMessages);
    }
 
    private List<HashMap<String, String>> getMessages(JSONArray jMessages){
        int messagesCount = jMessages.length();
        List<HashMap<String, String>> messagesList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> message = null;
 
        /** Taking each country, parses and adds to list object */
        for(int i=0; i<messagesCount;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
                message = getMessages((JSONObject)jMessages.get(i));
                messagesList.add(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        return messagesList;
    }
 
    /** Parsing the Country JSON object */
    private HashMap<String, String> getMessages(JSONObject jMessage){
 
        HashMap<String, String> country = new HashMap<String, String>();

        String text = "";
        String phone="";

 
        try {

        	
            text = jMessage.getString("text");
            phone = jMessage.getString("phone");
            
            country.put("text", text);
            country.put("phone", phone);

 
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return country;
    }
}