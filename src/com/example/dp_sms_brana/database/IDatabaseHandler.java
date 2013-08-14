package com.example.dp_sms_brana.database;

import java.util.ArrayList;

import com.example.dp_sms_brana.entity.Message;
import com.example.dp_sms_brana.entity.UserSettings;

public interface IDatabaseHandler {

	public void deleteMessage(Message message);

	public void deleteSettings(UserSettings settings);

	public void updateMessage(Message message);

	public void updateSettings(UserSettings settings);

	public long saveMessage(Message message);

	public long saveSettings(UserSettings settings);

	public Message getMessage(long id);

	public UserSettings getSettings(long id);

	public int getMessagesCount();

	public ArrayList<Message> getMessagesOfDay(String date);

	public Message oldestMessage();

	public int getCountMessagesOfDay(String date);

	public ArrayList<Message> getAllMessages();

}