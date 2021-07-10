package com.revature.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

	public static String toMoneyString(double money) {
		return "$" + String.format("%.2f", money);
	}
	
	public static double round(double value) {
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
