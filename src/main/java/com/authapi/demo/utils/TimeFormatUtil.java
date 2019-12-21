package com.authapi.demo.utils;

import java.text.SimpleDateFormat;
//import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeFormatUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_TIME_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_12HR = "yyyy-MM-dd hh:mm A";
	
	public static String dateToString (Date date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_FORMAT);
			return simDate.format(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}
	
	public static Date stringToDate (String date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_FORMAT);
			return simDate.parse(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}
	
	public static Date stringToDateNTime (String date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_TIME_FORMAT);
			return simDate.parse(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}
	
	public static String DateNTimeToString (Date date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_TIME_FORMAT);
			return simDate.format(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}
	
	public static Date stringToDateNTimeWSec (String date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_TIME_SEC_FORMAT);
			return simDate.parse(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}

	public static String DateNTimeWSecToString (Date date) {
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_TIME_SEC_FORMAT);
			return simDate.format(date);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return null;
	}

	public static Date dateWOTime (Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return date;
	}
	
	public static Date getEODTime (Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return date;
	}
	
	public static Date getWOSeconds (Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return date;
	}
	
	public static Date customDateTime (int year, int month, int date, int hour, int min, int second) {
		Calendar c = Calendar.getInstance();
		try {
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			c.set(Calendar.DATE, date);
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, min);
			c.set(Calendar.SECOND, second);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return c.getTime();
	}
	
	public static Date countDate(Date date, int type, int count) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(type, count);
			return c.getTime();
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
			
		}
		return date;
	}

	public static int dateDifference(Date dateA, Date dateB, TimeUnit timeUnit) {
		try {
			long diffInMillies = dateA.getTime() - dateB.getTime();
			return (int) timeUnit.convert(diffInMillies, timeUnit);
		} catch (Exception e) {//commonUtilMethods.exceptionLogger(request, e);
//		} catch (DateTimeException dte) {
				return 0;
		}
		
	}

	public static Date addDateAndTimeToDate(String dateString, String timeString) {
		String date = dateString + " " + timeString;
		try {
			SimpleDateFormat simDate = new SimpleDateFormat(DATE_TIME_FORMAT_12HR);
			return simDate.parse(date);
		} catch (Exception e) {// commonUtilMethods.exceptionLogger(request, e);

		}
		return null;
	}
}
