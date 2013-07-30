package com.example.dp_sms_brana;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataJSONParser {

	/** Pøijímá JSONObject a vrací list */
	public List<HashMap<String, Object>> parse(JSONObject jObject) {

		JSONArray jMessages = null;
		try {
			/** Naètení elementù v JSON poli 'data' */
			jMessages = jObject.getJSONArray("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getMessages(jMessages);
	}

	private List<HashMap<String, Object>> getMessages(JSONArray jMessages) {

		int messagesCount = jMessages.length();
		List<HashMap<String, Object>> messagesList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> message = null;
		/* Naètení zpráv a vložení do listu*/
		for (int i = 0; i < messagesCount; i++) {
			try {
				message = getMessages((JSONObject) jMessages.get(i));
				messagesList.add(message);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return messagesList;
	}

	/* Parsování zprávy z JSON objektu*/

	private HashMap<String, Object> getMessages(JSONObject jMessage) {

		HashMap<String, Object> message = new HashMap<String, Object>();
		int id = -1;
		String text = "";
		String phone = "";
		String send_date = "";
		short sent = 0;

		try {

			id = jMessage.getInt("id");
			text = jMessage.getString("text");
			phone = jMessage.getString("phone");
			sent = (short) jMessage.getInt("sent");
			send_date = jMessage.getString("send_date");

			message.put("id", id);
			message.put("text", text);
			message.put("phone", phone);
			message.put("sent", sent);
			message.put("send_date", send_date);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
}