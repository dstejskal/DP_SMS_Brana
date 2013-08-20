package cz.dsite.dp_sms_brana.database;

import java.util.ArrayList;

import cz.dsite.dp_sms_brana.entity.Message;

public interface IDatabaseHandler {

	public void deleteMessage(Message message);

	public void updateMessage(Message message);

	public long saveMessage(Message message);

	public Message getMessage(long id);

	public int getMessagesCount();

	public ArrayList<Message> getMessagesOfDay(String date);

	public Message oldestMessage();

	public int getCountMessagesOfDay(String date);

	public ArrayList<Message> getAllMessages();

}