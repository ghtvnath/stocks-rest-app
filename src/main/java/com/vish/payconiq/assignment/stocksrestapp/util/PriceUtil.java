package com.vish.payconiq.assignment.stocksrestapp.util;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceUtil.class);
	
//	private static NumberFormat _numberFormat = NumberFormat.getCurrencyInstance();
	
	public static final String formatPrice (BigDecimal price) {
		LOGGER.debug("Formatting price {} to String", price );
		String strPrice = "";
		if (price != null) {
			strPrice =  price.toPlainString();
		}
		return strPrice;
	}
	
	public static BigDecimal formatToPrice (String strPrice) {
		BigDecimal price = null;
		try {
			price = new BigDecimal(strPrice); 
		} catch (NumberFormatException nfe) {
			LOGGER.warn("Error converting string {} to BigDecimal", strPrice, nfe);
		}
		return price;
	}

}
