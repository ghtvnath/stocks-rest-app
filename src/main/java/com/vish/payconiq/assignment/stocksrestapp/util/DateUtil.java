package com.vish.payconiq.assignment.stocksrestapp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
	
	private static final String DEFAULT_DATE_FORMAT = "hh:mm:ss - dd/MM/yyyy";
	private static final Locale DEFAULT_LOCATE = new Locale("nl", "NL");
	
	public static String formatDate(Timestamp ts, String strDateFormat, Locale locale) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat, locale);
		return dateFormat.format(ts.getTime());
	}
	
	public static String formatDate(Timestamp ts) {
		return formatDate(ts, DEFAULT_DATE_FORMAT, DEFAULT_LOCATE);
	}
	
	public static String formatDate(Timestamp ts, String strDateFormat) {
		return formatDate(ts, strDateFormat, DEFAULT_LOCATE);
	}
	
	public static String formatDate(Timestamp ts, Locale locale) {
		return formatDate(ts, DEFAULT_DATE_FORMAT, locale);
	}

}
