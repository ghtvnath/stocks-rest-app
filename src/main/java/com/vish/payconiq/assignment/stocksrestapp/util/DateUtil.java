package com.vish.payconiq.assignment.stocksrestapp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	
	private static final String DEFAULT_DATE_FORMAT = "hh:mm:ss - dd/MM/yyyy";
	private static final Locale DEFAULT_LOCATE = new Locale("nl", "NL");
	private static final String EMPTY_STRING = "";
	
	public static String formatDate(Timestamp ts, String strDateFormat, Locale locale) {
		LOGGER.debug("Processing timestamp {} using dateFormat {} in Locale", ts, strDateFormat, locale);
		String date = EMPTY_STRING;
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat, locale);
		try {
			date = dateFormat.format(ts.getTime());
		} catch (IllegalArgumentException iae) {
			LOGGER.warn("Error in processing date - ", ts);
		}
		return date;
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
