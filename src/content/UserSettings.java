package content;

import android.content.Context;

public class UserSettings {
private long id;
private int interval;
private String apiData;
private String apiSend;
private String apiStatus;

public UserSettings(long id, int interval, String apiData, String apiSend,
		String apiStatus, Context context) {
	super();
	this.id = id;
	this.interval = interval;
	this.apiData = apiData;
	this.apiSend = apiSend;
	this.apiStatus = apiStatus;
}

public UserSettings( int interval, String apiData, String apiSend,
		String apiStatus, Context context) {
	super();
	this.interval = interval;
	this.apiData = apiData;
	this.apiSend = apiSend;
	this.apiStatus = apiStatus;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public int getInterval() {
	return interval;
}

public void setInterval(int interval) {
	this.interval = interval;
}

public String getApiData() {
	return apiData;
}

public void setApiData(String apiData) {
	this.apiData = apiData;
}

public String getApiSend() {
	return apiSend;
}

public void setApiSend(String apiSend) {
	this.apiSend = apiSend;
}

public String getApiStatus() {
	return apiStatus;
}

public void setApiStatus(String apiStatus) {
	this.apiStatus = apiStatus;
}





}
