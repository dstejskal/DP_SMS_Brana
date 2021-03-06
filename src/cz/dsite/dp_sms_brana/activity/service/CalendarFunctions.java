package cz.dsite.dp_sms_brana.activity.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFunctions {

	//public static final String DATE_FORMAT_NOW = "dd.MM.yyyy";

	public static String reformatDate(String date) throws ParseException {
		String result;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
		Date d = format1.parse(date);
		result = format2.format(date);
		return result;
	}

	public static String now() {
		String date;
		// Calendar cal = Calendar.getInstance();
		final String DATE_FORMAT_NOW = "dd.MM.yyyy";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		date = sdf.format(d);
		return date;
	}

	public static boolean readyToSend(String sendTime) {
		if (sendTime == "" || sendTime == null){
			return false;}
		String now;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		now = sdf.format(d);
		// pokud nastal �as odesl�n�, vr�t�m pravdivou odpov��
		int vysledek=sendTime.compareTo(now);
		if (sendTime.compareTo(now) <= 0){
			return true;}
		return false;
	}

	public static List<String> getDaysFromBeginOfMonth(String end)
			throws ParseException {

		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date lastDate = sdf.parse(end);

		calendar.setTime(lastDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1); // z�sk�n� prvn�ho dne v m�s�ci

		while (calendar.getTime().before(lastDate)) {
			Date result = calendar.getTime();
			dates.add(sdf.format(result));
			calendar.add(Calendar.DATE, 1);
		}

		Date result = calendar.getTime(); // p�id�n� posledn�ho dne
		dates.add(sdf.format(result));

		return dates;

	}

	public static List<String> getDaysFromToday(int countOfDays)
			throws ParseException {

		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date lastDate = sdf.parse(CalendarFunctions.now()); // dne�n� datum
		calendar.setTime(lastDate);

		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- countOfDays); // z�sk�n� dne p�ed t�dnem

		while (calendar.getTime().before(lastDate)) {
			Date result = calendar.getTime();
			dates.add(sdf.format(result));
			calendar.add(Calendar.DATE, 1);
		}

		Date result = calendar.getTime(); // p�id�n� posledn�ho dne
		dates.add(sdf.format(result));

		return dates;

	}

	// dod�lat - vyps�n� v�ech dn� od ur�it�ho dne - pro v�pis v�ech SMS
	public static List<String> getDaysFromDate(String day)
			throws ParseException {

		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date lastDate = sdf.parse(day);
		Date now = sdf.parse(CalendarFunctions.now());
		calendar.setTime(lastDate); // nastav�m datum na nejstar�� dostupn�

		while (calendar.getTime().before(now)) {
			Date result = calendar.getTime();
			dates.add(sdf.format(result));
			calendar.add(Calendar.DATE, 1);
		}

		Date result = calendar.getTime(); // p�id�n� posledn�ho dne
		dates.add(sdf.format(result));

		return dates;

	}

	public static List<String> getDaysFromInterval(String begin, String end)
			throws ParseException {

		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date start = sdf.parse(begin);
		Date lastDate = sdf.parse(end);

		calendar.setTime(start);
		while (calendar.getTime().before(lastDate)) {
			Date result = calendar.getTime();
			dates.add(sdf.format(result));
			calendar.add(Calendar.DATE, 1);
		}

		return dates;

	}
}
