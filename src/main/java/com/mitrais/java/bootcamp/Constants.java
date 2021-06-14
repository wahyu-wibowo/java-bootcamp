package com.mitrais.java.bootcamp;

import java.math.BigDecimal;

public class Constants {

	public Constants() {
		// do not instantiate this class
	}

	public static final Integer ACC_LENGTH = 6;
	public static final Integer PIN_LENGTH = 6;
	public static final Integer MAX_QUERY_BY_ACC_LIMIT = 10;
	public static final BigDecimal MIN_TRF_AMOUNT = BigDecimal.ONE;
	public static final BigDecimal MAX_TRX_AMOUNT = new BigDecimal(1000);

	public static final String PARAM_ACCOUNT = "acc";
	public static final String PARAM_DATE = "date";
}