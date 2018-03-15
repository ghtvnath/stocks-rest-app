package com.vish.payconiq.assignment.stocksrestapp.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceUtil.class);
	
	private static NumberFormat _numberFormat = NumberFormat.getCurrencyInstance();
	
	public static final String formatPrice (BigDecimal price){
		LOGGER.debug("Formatting price {} to String", price );
		return _numberFormat.format(price);
	}
	
	public static final String formatPrice (BigDecimal price, Locale locale){
		LOGGER.debug("Formatting price {} to String using Locale {}", price, locale );
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		return numberFormat.format(price);
	}
	
	public static BigDecimal formatToPrice (String strPrice){
		BigDecimal price = null;
		try {
			price = new BigDecimal(strPrice); 
		} catch (NumberFormatException nfe) {
			LOGGER.error("Error converting string {} to BigDecimal", strPrice, nfe);
		}
		return price;
	}

}
