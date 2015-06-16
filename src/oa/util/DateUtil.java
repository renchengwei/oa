package oa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat dateTimeFormat4 = new SimpleDateFormat("yyyyMMddHHmmss");

	private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM");

	private static SimpleDateFormat calDavDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * 将"yyyy-MM"型字符串转换为date对象。
	 * 
	 * @param dateString
	 *            :满足"yyyy-MM"的字符串
	 * @return
	 */
	public static Date getDate3(String dateString) {
		try {
			return dateFormat3.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断一个字符串是否为日期类型
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isDate(String dateString) {
		boolean rs = false;
		if (DateUtil.getDate(dateString) == null
				&& DateUtil.getDateString2(dateString) == null
				&& DateUtil.getDateTime(dateString) == null) {
			rs = false;
		} else {
			rs = true;
		}
		return rs;
	}

	/**
	 * 获取date对象的 "yyyy-MM"字符串形式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getDateString3(Date date) {
		return dateFormat3.format(date);

	}

	/**
	 * 将"yyyy-MM-dd"型字符串转换为date对象。
	 * 
	 * @param date
	 *            :满足"yyyy-MM-dd"的字符串
	 * @return
	 */
	public static Date getDate(String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取指定年月日的Date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		calendar.clear();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	/**
	 * 判断该date是星期几.
	 * 
	 * @param date
	 *            :指定date
	 * @return 返回1-7，1代表Sunday,7代表Saturday.
	 */
	public static int getWeekday(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断该”year年month月date日“是星期几.
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return 返回1-7，1代表Sunday,7代表Saturday.
	 */
	public static int getWeekday(int year, int month, int day) {
		calendar.clear();
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断该date是否是星期六
	 * 
	 * @param date
	 *            :指定date
	 */
	public static boolean isSaturday(Date date) {
		return getWeekday(date) == Calendar.SATURDAY;
	}

	/**
	 * 判断该date是否是星期日
	 * 
	 * @param date
	 *            :指定date
	 */
	public static boolean isSunday(Date date) {
		return getWeekday(date) == Calendar.SUNDAY;
	}

	/**
	 * 判断该date是否是周末
	 * 
	 * @param date
	 *            :指定date
	 */
	public static boolean isWeekend(Date date) {
		return isSaturday(date) || isSunday(date);
	}

	/**
	 * 将"yyyy-MM-dd hh:mm:ss"型字符串转换为date对象。
	 * 
	 * @param
	 * @return
	 */
	public static Date getDateTime(String dateTimeString) {
		try {
			return dateTimeFormat.parse(dateTimeString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将date对象转换为"yyyy-MM-dd hh:mm:ss"型字符串。
	 * 
	 * @param
	 * @return
	 */
	public static String getDateTime(Date time) {
		return dateTimeFormat.format(time);
	}

	/**
	 * 获取date对象的 "yyyy-MM-dd"字符串形式
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * yyyy-MM-dd hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString2(Date date) {
		return dateFormat2.format(date);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString4(Date date) {
		return dateTimeFormat4.format(date);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateString4(String date) {
		try {
			return dateTimeFormat4.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString5(Date date) {
		if (date == null)
			return "";
		return dateTimeFormat.format(date);
	}

	/**
	 * 将传入的含有年月日时分的数据转为java.util.Date
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date getDateString2(String dateString) {
		try {
			return dateFormat2.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取Date对象中 年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取Date对象中 月份(月份以现实为准,1代表一月)
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1; // JAVA中一月从0开始，为避免混淆，故+1
	}

	/**
	 * 获取Date对象中 天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回指定指定月份 天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysCount(int year, int month) {
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1); // Calendar中month是从0开始
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 但指定日期进行加减天数
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date addDays(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ offset);// 让日期加位移量

		return calendar.getTime();
	}

	/**
	 * 但指定日期进行加减年数
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date addYears(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + offset);// 让日期加位移量

		return calendar.getTime();
	}

	/**
	 * 指定日期进行加减小时
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date addHours(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				+ offset);

		return calendar.getTime();
	}

	/**
	 * 但指定日期进行减天数
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date reduceDays(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- offset);// 让日期加位移量

		return calendar.getTime();
	}

	/**
	 * 指定日期进行减小时
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date reduceHours(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				- offset);

		return calendar.getTime();
	}

	/**
	 * 指定日期进行减分钟
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date reduceMinute(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - offset);
		return calendar.getTime();
	}

	/**
	 * 指定日期进行减月份
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date reduceMonth(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - offset);
		return calendar.getTime();
	}

	/**
	 * 获取Date对象中 分钟 LK0142
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取Date对象中 小时 LK0142
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		calendar.clear();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取D/M/Y的形式日期值 LK0142
	 * 
	 * */
	public static String getDMYDate() {
		Date date = new Date();
		return calDavDateFormat.format(date);
	}

	/**
	 * 获取两个日期的间隔天数 LK0142
	 * */
	public static Long getDaysBetween(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取两个整数的间隔小时数
	 * */
	public static int getHoursBetween(int starth, int endh) {
		if (endh < starth) {
			return 24 + endh - starth;
		}
		return endh - starth;
	}

	/**
	 * 获取两个整数的间隔分钟数
	 * */
	public static int getMinutessBetween(int startm, int endm) {
		if (endm < startm) {
			return 60 + endm - startm;
		}
		return endm - startm;
	}

	public static void systemLog(String tag) {
		System.out.println("TAG=" + tag + " time=" + new Date().getTime());
	}

	public static void main(String[] arg) {
		Date date = DateUtil.addYears(new Date(), 1);
		System.out.println(DateUtil.getDateString(date));
	}
}
