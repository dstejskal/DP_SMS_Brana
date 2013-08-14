package com.example.dp_sms_brana.entity;

public class Result {
	private long _id;
	private long  mid;
	private String sender;
	private String text;
	private String recipient;
	private String date;
	
	
	public Result(long _id, long mid, String sender, String text,
			String recipient, String date) {
		super();
		this._id = _id;
		this.mid = mid;
		this.sender = sender;
		this.text = text;
		this.recipient = recipient;
		this.date = date;
	}
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
}
