package com.vish.payconiq.assignment.stocksrestapp.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceUtil {
	
	private static NumberFormat _numberFormat = NumberFormat.getCurrencyInstance();
	
	public static final String formatPrice (BigDecimal price){
		return _numberFormat.format(price);
	}
	
	public static final String formatPrice (BigDecimal price, Locale locale){
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		return numberFormat.format(price);
	}

}
