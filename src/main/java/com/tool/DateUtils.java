package com.tool;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 日期工具类
 * 
 * @Title: DateUtils.java
 * @Package mall.common.utils
 * @Description: 日期工具类
 * @author tianzy
 * @date 2017年7月14日下午3:45:39
 */
public class DateUtils {
	public static final String JSESSION_COOKIE = "JSESSIONID";
	public static final String JSESSION_URL = "jsessionid";

	/**
	 * 获得当前时间。由于freemarker的日期必须有具体类型，所以使用timestamp。
	 * 
	 * @return
	 */
	public static java.sql.Timestamp now() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 格式化日期。yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return format.format(date);
	}

	/**
	 * 格式化日期。yyyy-MM-dd hh-mm-ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dataFormatWhole(Date date) {
		return formatw.format(date);
	}

	public static String formatDate(Date date, int style) {
		if (date == null) {
			return "";
		}
		switch (style) {
		case 4:
			return formats.format(date);
		case 3:
			return formatm.format(date);
		case 2:
			return format.format(date);
		default:
			return formatw.format(date);
		}
	}

	public static Date getNowTimeDate() {
		return DateUtils.getNowTime(DateUtils.getTime(5), "yyyy-MM-dd HH:mm:ss");
	}

	public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat formatw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat formatm = new SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat formats = new SimpleDateFormat("MM-dd");
	public static FilenameFilter DIR_FILE_FILTER = new FilenameFilter() {

		public boolean accept(File dir, String name) {
			if (dir.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}
	};

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String strFormat) {

		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowTime(String strDate, String strFormat) {
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}

	public static String getTime(int type) {
		String t = null;
		SimpleDateFormat format = null;
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case 0:
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		case 1:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 2:
			format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			break;
		case 3:
			format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			break;
		case 4:
			format = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 5:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 6:
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		case 7:
			format = new SimpleDateFormat("yyyy-MM");
			break;
		case 8:
			format = new SimpleDateFormat("HH:mm:ss");
			break;
		case 9:
			format = new SimpleDateFormat("yyyy");
			break;
		case 10:
			format = new SimpleDateFormat("yyyyMMddHHmm");
			break;
		default:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		t = format.format(cal.getTime());
		return t;
	}

	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	public static long compareDate(Date iDate, String format) {
		long distime = 0;
		if (iDate != null) {
			try {
				distime = DateUtils.getLongTime() - DateFormat.getDateTimeInstance().parse(DateUtils.formatDateTime(iDate, format)).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			distime = distime / 1000 / 60 / 60 / 24;
		}
		return distime;
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34

	public static boolean isDateBefore(Date date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(DateUtils.formatDateTime(date2, "yyyy-MM-dd HH:mm:ss")));
		} catch (ParseException e) {
			System.out.print("[isDateBefore] " + e.getMessage());
			return false;
		}
	}

	// 判断时间date1是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[isDateBefore] " + e.getMessage());
			return false;
		}
	}

	public static Date strToDate(String dateStr) {
		Date dateTemp = null;
		try {
			dateStr = dateStr.substring(0, 10);
			StringTokenizer token = new StringTokenizer(dateStr, "-");
			int year = Integer.parseInt(token.nextToken());
			int month = Integer.parseInt(token.nextToken()) - 1;
			int day = Integer.parseInt(token.nextToken());
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			dateTemp = cal.getTime();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return dateTemp;
	}

	public static String formatDateTime(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}

	/**
	 * 计算两个日期的天数差值
	 */
	public static long getTwoDateDays(Date beginDate, Date endDate) {
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);
		return betweenDays;
	}

	/**
	 * 返回当前日期加、减N天后的日期(如果需要结果是一个Date型的，需要转回)
	 * 
	 * @param outday
	 *            当前日期相差的天数(可以为负数)
	 * @return 返回当前日期加、减N天后的日期
	 */
	public static String returnNDay(int outday) {
		// 定义返回值
		String newdatetime = "";
		// 格式化日期
		SimpleDateFormat fmtdt = new SimpleDateFormat("yyyy-MM-dd");
		// 得到当前日期
		Calendar c = Calendar.getInstance();
		// 在当前日期的基础上加outday天(outday可以为负数)
		c.add(Calendar.DATE, outday);
		// 得到增加outday天后的日期
		Date tempnewdatetime = c.getTime();
		// 格式化增加outday天后的日期，并且转换为string
		newdatetime = fmtdt.format(tempnewdatetime).toString();
		return newdatetime;
	}

	/**
	 * 根据日期返回 日期前或日期后的日期
	 * 
	 * @param date
	 * @param in
	 * @return
	 */
	public static Date getLastOrBeforeDate(Date date, int in) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, in);
		return cal.getTime();
	}

	/**
	 * 获得当前日期，例如2009-09-04
	 * 
	 * @return
	 */
	public static final Date getNowDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期类型转换指定格式字符串
	 * 
	 * @param errorResp
	 */
	public static String getFormatDateString(Date date, String format) {

		String dateStr = "";

		if (date == null || format == null) {
			return dateStr;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			dateStr = sdf.format(date);
		} catch (Exception e) {
			log.debug("getFormatDateString() - format date[" + date + "] formatStr[" + format + "] failed!!!", e);
			log.info("getFormatDateString() - format date[" + date + "] formatStr[" + format + "] failed!!!");
		}
		return dateStr;
	}

	/**
	 * 将指定格式字符串转换为日期类型
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getDateFromFormatString(String str, String format) {

		Date date = null;

		if (str == null || format == null) {
			return date;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			log.debug("getDateFromFormatString() - get date from str[" + str + "] as format[" + format + "] failed!!!", e);
			log.info("getDateFromFormatString() - get date from str[" + str + "] as format[" + format + "] failed!!!");
		}
		return date;
	}

	/**
	 * 调试信息对象
	 */
	private static Logger log = Logger.getLogger(DateUtils.class);

	/**
	 * US locale - all HTTP dates are in english
	 */
	public final static Locale LOCALE_US = Locale.US;

	/**
	 * GMT timezone - all HTTP dates are on GMT
	 */
	public final static TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");

	/**
	 * format for RFC 1123 date string -- "Sun, 06 Nov 1994 08:49:37 GMT"
	 */
	public final static String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

	private static final ThreadLocal<DateFormat> dfRfc1123 = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);
			rfc1123Format.setTimeZone(GMT_ZONE);
			return rfc1123Format;
		}
	};

	public static String rfc1123Fromat(Date date) {
		return dfRfc1123.get().format(date);
	}
}