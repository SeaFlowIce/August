package com.galgame.util;

import java.util.Calendar;

public class DateUtils {
	public static String getFormatDate(int offset) {
		Calendar dateToGetUrl = Calendar.getInstance();
		dateToGetUrl.add(Calendar.DAY_OF_YEAR, offset);
		String date = ZHConstants.Dates.simpleDateFormat.format(dateToGetUrl.getTime());
		return date;
	}
}

