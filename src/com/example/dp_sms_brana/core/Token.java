package com.example.dp_sms_brana.core;
//Tøída token udržuje stavu tokenu - sdílená promìnná pro celou aplikaci
public class Token {
private static String token=null;

public static String getToken() {
	return token;
}

public static void setToken(String tok) {
	token = tok;
}

}
