package com.example.dp_sms_brana.core;
//T��da token udr�uje stavu tokenu - sd�len� prom�nn� pro celou aplikaci
public class Token {
private static String token=null;

public static String getToken() {
	return token;
}

public static void setToken(String tok) {
	token = tok;
}

}
