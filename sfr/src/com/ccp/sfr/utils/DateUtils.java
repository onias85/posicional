package  com.ccp.sfr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ccp.sfr.commons.AssertionsUtils;

public class DateUtils {

	/**
	 * Formata uma data
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String getFormattedDate(String value, String pattern) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("pattern", pattern);
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date();
		Long milissegundos = new Long(value);
		date.setTime(milissegundos);
		String format = sdf.format(date);
		return format;
	}

	
	/**
	 * Pergunta se uma string representa uma data ou hora valida
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDateTime(String value, String pattern) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("pattern", pattern);
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		try {
			Date parse = sdf.parse(value);
			String format = sdf.format(parse);
//			System.out.println(format.equals(value));
			boolean b = format.length() == value.length();
			return b;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Retorna a data de hoje a meia noite
	 * @return
	 */
	public static Date todayMidnight() {
		Date truncateDate = truncateDate(new Date());
		return truncateDate;
	}

	
	/**
	 * Retorna a data com horas, minutos, segundos e milissegundos zerados
	 * @param dateObject
	 * @return
	 */
	public static Date truncateDate(Date dateObject) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("dateObject", dateObject);

		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(dateObject);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date time = cal.getTime();

		return time;
	}

}
