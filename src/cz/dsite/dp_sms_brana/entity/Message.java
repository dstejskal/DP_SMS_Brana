package cz.dsite.dp_sms_brana.entity;

import android.content.Context;

public class Message {

	private long _id;
	private long  mid;
	private String sender;
	private String text;
	private String recipient;
	private String date;

	public Message (long _id, long mid, String sender, String recipient,  String date, Context context){
	this._id=_id;
	this.mid=mid;
	this.sender=sender;
	this.recipient=recipient;
	this.date=date;
	}
	
	public Message (long _id, long mid, String sender, String recipient,  String date, String text,  Context context){
	this._id=_id;
	this.mid=mid;
	this.sender=sender;
	this.recipient=recipient;
	this.text=text;
	this.date=date;
	}
	
	public long get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
}
