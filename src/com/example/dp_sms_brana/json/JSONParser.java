package com.example.dp_sms_brana.json;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

public interface JSONParser {

	/** P�ij�m� JSONObject a vrac� list */
	public List<HashMap<String, Object>> parse(JSONObject jObject);

}