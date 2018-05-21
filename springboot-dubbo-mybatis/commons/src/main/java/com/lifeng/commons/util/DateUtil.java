package com.lifeng.commons.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.google.common.base.Preconditions.*;
import static java.lang.Math.abs;

/**
 * 日期时间工具类
 * 
 * @author lifeng
 */
public class DateUtil
{
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    
    private static final String[] CHINA_WEEKDAY = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

    public static interface TimeFormatter {
    	String YYYYMMDD = "yyyyMMdd";
    	String YYYY_MM_DD = "yyyy-MM-dd";
    	String YYYY_MM = "yyyy-MM";
    	String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    	String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    	String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    	String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    	String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    	String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
		String MM_DD = "MM-dd";
		String HH_MM_SS = "HH:mm:ss";
		String HH_MM_00 = "HH:mm:00";
		String HH_MM_MM_DD = "HH:mm MM-dd";
		String HH_MM = "HH:mm";
		String YYYY$M$D$HHMM = "yyyy年M月d日 HH:mm";
		String MM$DD = "MM月dd日";
		String M$D = "M月d日";
		String YYYY$M$D = "yyyy年M月d日";
    }

    /**
     * 线程本地变量，确保线程间的数据安全
     */
    public static final ThreadLocal<SimpleDateFormat> SHORT_DAY = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.HH_MM));

    public static final ThreadLocal<SimpleDateFormat> HH_MM_SS = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.HH_MM_SS));

    // 8位变量
    public static final ThreadLocal<SimpleDateFormat> YYYY_MM_DD = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.YYYY_MM_DD));

    public static final ThreadLocal<SimpleDateFormat> MMDDHH = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.HH_MM_MM_DD));

    /** 默认的时间格式化器，格式为yyyy-MM-dd HH:mm:ss */
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_DATETIME_FORMATER = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.YYYY_MM_DD_HH_MM_SS));

    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.YYYYMMDDHHMMSS));

    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMM = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.YYYY_MM_DD_HH_MM));

    public static final ThreadLocal<SimpleDateFormat> HHMM00 = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.HH_MM_00));
    

    public static final ThreadLocal<SimpleDateFormat> MD = ThreadLocal.withInitial(() -> new SimpleDateFormat(TimeFormatter.M$D));
    public static final ThreadLocal<SimpleDateFormat> YMD = new ThreadLocal<SimpleDateFormat>()
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat(TimeFormatter.YYYY$M$D);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> YMDHM = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(TimeFormatter.YYYY$M$D$HHMM));

    /**
     * 判断日期是否为同一天
     * 
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean isSameDay(Date dateA, Date dateB)
    {
        DateTime d1 = new DateTime(dateA);
        DateTime d2 = new DateTime(dateB);

        return Days.daysBetween(d1, d2).getDays() == 0;
    }

    /**
     * 日期加分钟,可以向前加，向后加
     * 
     * @param date
     *        日期
     * @param min
     *        分钟
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int min)
    {
        return new DateTime(date).plusMinutes(min).toDate();
    }

    /**
     * 获取当前时间的日期-时 数据，即将当前的分钟、秒置为0后的数值, *
     * 
     * @param hour
     *        要加减的小时数，如果为0表示当前小时
     * @return
     */
    public static Date getDayHourDate(int hour)
    {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.HOUR_OF_DAY, hour);
        return today.getTime();
    }

    /**
     * 获得某天0时0分0秒的秒数
     * 
     * @param day
     *        今天就是0，昨天就是-1，明天就是1，由此类推
     * @return
     */
    public static Date getSomeDayDate(int day)
    {
        // return (System.currentTimeMillis()/86400000*86400000L-(23- Calendar.ZONE_OFFSET)*3600000L +
        // day*24*3600000L)/1000;
        // 上面这种方法在服务器执行发现跟系统时间并不一致，早上8点获取到的是新的一天的数据，屏蔽掉。用系统提供的方法以免出错
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.DAY_OF_YEAR, day);
        return today.getTime();
    }

    /**
     * 返回日期代表的毫秒
     * 
     * @param date
     *        日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 取得当前日期（只有日期，没有时间，或者可以说是时间为0点0分0秒）
     * 
     * @return
     * @throws ParseException
     */
    public static Date getCurrentDate() throws ParseException
    {

        return clearTime(new Date());
    }

    /**
     * 取得当前时间（包括日期和时间）
     * 
     * @return 当前时间
     */
    public static Date getCurrentDateTime()
    {
        return longTimeToDateTime(System.currentTimeMillis());
    }

    /**
     * 用默认的日期格式，格式化一个Date对象
     * 
     * @param date
     *        待被格式化日期
     * @return “yyyy-MM-dd”格式的日期字符串
     */
    public static String formatDate(Date date)
    {
        return date == null ? "" : DateUtil.YYYY_MM_DD.get().format(date);
    }

    /**
     * 格式化成"HH:mm:ss"
     * 
     * @param date
     * @return
     */
    public static String formatTime(Date date)
    {
        return date == null ? "" : DateUtil.HH_MM_SS.get().format(date);
    }

    /**
     * 格式化成"HH:mm:ss"
     * 
     * @param hh_mm_ss
     * @return
     */
    public static Date parseHH_MM_SS(String hh_mm_ss)
    {
        try
        {
            return DateUtil.HH_MM_SS.get().parse(hh_mm_ss);
        }
        catch (ParseException e)
        {
            logger.error("", e);
        }
        return new Date();
    }

    /**
     * 根据传入的格式，将日期对象格式化为日期字符串
     * 
     * @param date
     *        待被格式化日期
     * @param format
     *        自定义日期格式器
     * @return 格式后的日期字符串
     */
    public static String formatDate(Date date, String format)
    {
    	if (date == null) {
			return "";
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
    }

    /**
     * 用默认的日期时间格式，格式化一个Date对象
     * 
     * @param date
     *        待被格式化日期
     * @return “yyyy-MM-dd HH:mm:ss”格式的日期时间字符串
     */
    public static String formatDateTime(Date date)
    {
        return date == null ? "" : DateUtil.DEFAULT_DATETIME_FORMATER.get().format(date);
    }

    public static String formatHH_MM_00(Date date)
    {
        return date == null ? "" : DateUtil.HHMM00.get().format(date);
    }

    /**
     * 获取指定天数后的日期
     * 
     * @param baseDate
     *        基准日期
     * @param day
     *        后推天数
     * @return 后推后的天数,-减
     */
    public static Date plusDays(Date baseDate, int day)
    {
        return new DateTime(baseDate).plusDays(day).toDate();
    }

    /**
     * 利用默认的格式（yyyy-MM-dd）将一个表示日期的字符串解析为日期对象
     * 
     * @param dateStr
     *        待格式化日期字符串
     * @return 格式化后日期对象
     * @throws RuntimeException
     */
    @Deprecated
    public static Date parseDate(String dateStr)
    {
        Date date = null;
        try
        {
            date = DateUtil.YYYY_MM_DD.get().parse(dateStr);
        }
        catch (ParseException e)
        {
            logger.error("", e);
        }

        return date;
    }

    /**
     * 利用默认的格式（yyyy-MM-dd HH:mm:ss）将一个表示时间的字符串解析为日期对象
     * 
     * @param timeStr
     *        时间字符串
     * @return 格式化后的日期对象
     * @throws ParseException
     */
    @Deprecated
    public static Date parseTime(String timeStr)
    {
        try
        {
            return DateUtil.DEFAULT_DATETIME_FORMATER.get().parse(timeStr);
        }
        catch (ParseException e)
        {
            logger.error("", e);
        }
        return new Date();
    }

    /**
     * 将一个字符串，按照特定格式，解析为日期对象
     * 
     * @param datetimeStr
     *        日期、时间、日期时间字符串
     * @param format
     *        自定义日期格式器
     * @return 格式化后的日期对象
     * @throws ParseException
     */
    @Deprecated
    public static Date parseDateTime(String datetimeStr, String format)
    {
        Date date = null;
        try
        {
            date = (new SimpleDateFormat(format)).parse(datetimeStr);
        }
        catch (ParseException e)
        {
            logger.error("", e);
        }

        return date;
    }

    /**
     * 得到当前年份
     * 
     * @return 当前年份2017
     */
    public static int getCurrentYear()
    {
        return new DateTime().getYear();
    }

    /**
     * 得到当前月份（1至12）
     * 
     * @return 当前月份（1至12）
     */
    public static int getCurrentMonth()
    {
        return new DateTime().getMonthOfYear();
    }

    /**
     * 获取yyyy-MM-dd格式的当前系统日期
     * 
     * @return 当前系统日期
     */
    public static String getCurrentDateAsString()
    {
        return YYYY_MM_DD.get().format(new Date());
    }

    /**
     * 获取当前为星期几,从星期日~星期六对应的值是1~7
     * 
     * @return 星期几
     * @date: 2013年12月31日下午3:35:08
     */
    public static int getDayOfWeek()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
     * 
     * @param date
     *        指定日期
     * @return 星期几
     * @date: 2013年12月31日下午3:45:35
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期几的中文名称
     * 
     * @return 星期几
     */
    public static String getChineseDayOfWeek()
    {
        Calendar cal = Calendar.getInstance();
        return DateUtil.getChineseDayOfWeek(cal.getTime());
    }

    /**
     * 获取星期几的中文名称
     * 
     * @param date
     *        指定日期
     * @return 星期几
     */
    public static String getChineseDayOfWeek(String date)
    {
        return DateUtil.getChineseDayOfWeek(DateUtil.parseDate(date));
    }

    /**
     * 获取星期几的中文名称
     * 
     * @param date
     *        指定日期
     * @return 星期几
     */
    public static String getChineseDayOfWeek(Date date)
    {
        int dateOfWeek = DateUtil.getDayOfWeek(date);
        if (dateOfWeek == Calendar.MONDAY)
        {
            return "星期一";
        }
        else if (dateOfWeek == Calendar.TUESDAY)
        {
            return "星期二";
        }
        else if (dateOfWeek == Calendar.WEDNESDAY)
        {
            return "星期三";
        }
        else if (dateOfWeek == Calendar.THURSDAY)
        {
            return "星期四";
        }
        else if (dateOfWeek == Calendar.FRIDAY)
        {
            return "星期五";
        }
        else if (dateOfWeek == Calendar.SATURDAY)
        {
            return "星期六";
        }
        else if (dateOfWeek == Calendar.SUNDAY)
        {
            return "星期日";
        }
        return null;
    }

    /**
     * 获取当天为几号
     * 
     * @return 几号
     * @date: 2013年12月31日下午3:50:11
     */
    public static int getDayOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期为几号
     * 
     * @param date
     *        指定的日期
     * @return 几号
     * @date: 2013年12月31日下午3:50:40
     */
    public static int getDayOfMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期所在月份的最后一天是几号
     * 
     * @param date
     *        指定日期
     * @return 指定日期所在月份的最后一天是几号
     * @date: 2013年12月31日下午3:51:07
     */
    public static int getMaxDayOfMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期所在月份的第一天
     * 
     * @param date
     *        指定日期
     * @return 指定日期所在月份的第一天
     * @date: 2013年12月31日下午4:16:56
     */
    public static String getFirstDayOfMonth(String date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return YYYY_MM_DD.get().format(cal.getTime());
    }

    public static final Date plusHours(Date date, int hours)
    {
        DateTime dt = new DateTime(date);
        dt = dt.plusHours(hours);
        return dt.toDate();
    }

    public static final Date plusMins(Date date, int minutes)
    {
        DateTime dt = new DateTime(date);
        dt = dt.plusMinutes(minutes);
        return dt.toDate();
    }

    public static final Date plusMonths(Date date, int months)
    {
        DateTime dt = new DateTime(date);
        dt = dt.plusMonths(months);
        return dt.toDate();
    }
    
	/**
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date plusYears(Date date, int year) {
		return new DateTime(date).plusYears(year).toDate();
	}

    /**
     * 获取当天为一年中第几天
     * 
     * @return 一年中第几天 2.11号返回42
     */
    public static int getDayOfYear()
    {
        return new DateTime().getDayOfYear();
    }

    /**
     * 获取指定日期为一年中第几天
     * 
     * @param date
     *        指定日期
     * @return 一年中第几天
     * @date: 2013年12月31日下午4:04:21
     */
    public static int getDayOfYear(Date date)
    {
        return new DateTime(date).getDayOfYear();
    }

    /**
     * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
     * 
     * @param date
     *        指定日期
     * @return 星期几
     * @date: 2013年12月31日下午3:45:35
     */
    public static int getDayOfWeek(String date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期为几号
     * 
     * @param date
     *        指定的日期
     * @return 几号
     * @date: 2013年12月31日下午3:50:40
     */
    public static int getDayOfMonth(String date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期为一年中第几天
     * 
     * @param date
     *        指定日期
     * @return 一年中第几天
     * @date: 2013年12月31日下午4:04:21
     */
    public static int getDayOfYear(String date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
     * 
     * @param time
     *        距离GTM时刻的毫秒数
     * @return 获取到的北京时区日期时间字符串
     */
    public static String longTimeToDateTimeString(Long time)
    {
        String d = DEFAULT_DATETIME_FORMATER.get().format(time);
        return d;
    }

    /**
     * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
     * 
     * @param time
     *        距离GTM时刻的毫秒数
     * @return 获取到的北京时区日期时间对象
     */
    public static Date longTimeToDateTime(long time)
    {
        String d = DEFAULT_DATETIME_FORMATER.get().format(time);
        return DateUtil.parseTime(d);
    }

    // timeStamp,linux时间戳
    public static long getNow()
    {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * 清除日期对象的时间部分, 只保留日期(年月日)信息
     * 
     * @param date
     * @return
     */
    public static Date clearTime(Date date)
    {
        return new DateTime(date).withMillisOfDay(0).toDate();
    }

    /**
     * 用来判断时间d是否在start和end之间, 如果start为空, 则表示没有起始时间限制, 同样end为空时即没有结束时间限制.
     * 
     * @param start
     *        开始时间
     * @param end
     *        结束时间
     */
    public static boolean between(Date start, Date end, Date d)
    {
        checkNotNull(d);
        checkState(!(start == null && end == null), "start and end arguments must appear at least one ");
        // 如果没有开始时间或结束时间
        if (start == null)
        {
            return d.compareTo(end) <= 0;
        }
        else if (end == null)
        {
            return d.compareTo(start) >= 0;
        }
        else
        {
            return d.compareTo(start) >= 0 && d.compareTo(end) <= 0;
        }
    }

    /**
     * 判断时间是否为全天时间, 即只有date信息, 没有time信息, 比如 2014-10-10
     * 
     * @return 如果该时间为全天时间, 没有time信息, 则返回true
     */
    public static boolean isAllDay(Date time)
    {
        checkNotNull(time);
        return new DateTime(time).getSecondOfDay() == 0;
    }

    /**
     * 判断两个时间是否相等. 这里相等的含义是: 它们的相差时间段误差在毫秒内. 比如 2011-1-1 12:23:34.234 和 2011-1-1 12:23:34.962 相等
     * 
     * @param t1
     * @param t2
     * @return 如果两个时间仅只是毫秒级内不同或者完全相同, 返回true
     */
    public static boolean isSameTime(Date t1, Date t2)
    {
        return abs(t1.getTime() - t2.getTime()) < 1000;
    }
    
    public static long getPeriodOfHours(Date t1, Date t2)
    {
        Duration d = getDuration(t1, t2);
        return d.getStandardHours();
    }

    public static long getPeriodOfDays(Date t1, Date t2)
    {
        Duration d = getDuration(t1, t2);
        return d.getStandardDays();
    }

    public static long getPeriodOfMinutes(Date t1, Date t2)
    {
        Duration d = getDuration(t1, t2);
        return d.getStandardMinutes();
    }

    private static Duration getDuration(Date t1, Date t2)
    {
        checkArgument(t1 != null);
        checkArgument(t2 != null);
        DateTime tt1 = new DateTime(t1.getTime());
        DateTime tt2 = new DateTime(t2.getTime());
        return new Duration(tt1, tt2);
    }

    /**
     * 获取当前月份的第一天
     */
    public static Date getCurrentMonthFirstDay()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前月份的最后一天
     * 
     * @return
     */
    public static Date getCurrentMonthLastDay()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定月份的第一天
     * 
     * @param month
     * @return
     */
    public static Date getFirstDayByMonth(Date month)
    {
        Calendar cale = Calendar.getInstance();
        cale.setTime(month);
        cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return cale.getTime();
    }

    /**
     * 获取指定月份的最后一天
     * 
     * @param month
     * @return
     */
    public static Date getLastDayByMonth(Date month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取指定日期的开始时间<br/>
     * 例如: 2015-03-23 00:00:00
     * 
     * @param day
     * @return
     */
    public static Date getStartTimeOfDay(Date day)
    {
        return new DateTime(day).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取指定日期的结束时间<br/>
     * 例如： 2015-03-23 23:59:59
     * 
     * @param day
     * @return
     */
    public static Date getLastTimeOfDay(Date day)
    {
        return new DateTime(day).plusDays(1).withTimeAtStartOfDay().plusMinutes(-1).toDate();
    }

    /**
     * 取秒级时间
     */
    public static String getTimestamp()
    {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 判断是否同一个星期
     */
    public static boolean isSameWeek(Date dateA, Date dateB)
    {
        DateTime d1 = new DateTime(dateA);
        DateTime d2 = new DateTime(dateB);

        return d1.getWeekyear() == d2.getWeekyear() && d1.getWeekOfWeekyear() == d2.getWeekOfWeekyear();
    }

    /**
     * 判断是否同一个月
     */
    public static boolean isSameMonth(Date dateA, Date dateB)
    {
        DateTime d1 = new DateTime(dateA);
        DateTime d2 = new DateTime(dateB);

        return d1.getYear() == d2.getYear() && d1.getMonthOfYear() == d2.getMonthOfYear();
    }
    
    /**
	 * 获取两个日期之间相差的天数
	 * @param start
	 * @param end
	 * @return
	 * 
	 */
	public static long getDaysBetween(Date start, Date end) {
		return Days.daysBetween(new DateTime(start), new DateTime(end))
				.getDays();
	}
	
	/**
	 * 获取两个日期之间相差的天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDaysBetween(String start, String end) {
		DateTime dtStart = new DateTime(parseToDate(start));
		DateTime dtEnd = new DateTime(parseToDate(end));
		return Days.daysBetween(dtStart, dtEnd).getDays();
	}
	
	/**
	 * 获取两个日期之间的相差年份
	 * @param start
	 * @param end
	 * @return
	 * 
	 */
	public static long getYearsBetween(Date start, Date end) {
		return Years.yearsBetween(new DateTime(start), new DateTime(end))
				.getYears();
	}
	
	/**
	 * 指定格式的字符串转转成日期
	 * 
	 * @param dateStr
	 * @return
	 * 
	 */
	public static Date parseToDate(String dateStr) {
		Date date = null;
		int dateLen = dateStr.length();
		if (dateLen == TimeFormatter.YYYY_MM.length()) {
			date = parseToDate(dateStr, TimeFormatter.YYYY_MM);
		} else if (dateLen == TimeFormatter.YYYY_MM_DD.length()) {
			date = parseToDate(dateStr, TimeFormatter.YYYY_MM_DD);
		} else if (dateLen == TimeFormatter.YYYY_MM_DD_HH_MM_SS.length()) {
			date = parseToDate(dateStr, TimeFormatter.YYYY_MM_DD_HH_MM_SS);
		} else if (dateLen == TimeFormatter.YYYYMMDDHHMMSS.length()) {
			date = parseToDate(dateStr, TimeFormatter.YYYYMMDDHHMMSS);
		} else {
			throw new RuntimeException("暂不支持此类型的格式转换...");
		}
		return date;
	}
	
	/**
	 * 指定格式的字符串转转成时间， 日期采用当前日
	 * 
	 * @param dateStr
	 * @return
	 * 
	 */
	public static Date parseToTime(String timeStr) {
		DateTime dateTime = null;
		int dateLen = timeStr.length();
		if (dateLen == TimeFormatter.HH_MM.length()) {
			DateTimeFormatter fotmatter = DateTimeFormat.forPattern(TimeFormatter.HH_MM);
			dateTime = fotmatter.parseDateTime(timeStr);
		} else if (dateLen == TimeFormatter.HH_MM_SS.length()) {
			DateTimeFormatter fotmatter = DateTimeFormat.forPattern(TimeFormatter.HH_MM_SS);
			dateTime = fotmatter.parseDateTime(timeStr);
		} else {
			throw new RuntimeException("暂不支持此类型的格式转换...");
		}
		Calendar oCld = Calendar.getInstance();
		oCld.set(Calendar.HOUR_OF_DAY, dateTime.getHourOfDay());
		oCld.set(Calendar.MINUTE, dateTime.getMinuteOfHour());
		oCld.set(Calendar.SECOND, dateTime.getSecondOfMinute());
		oCld.set(Calendar.MILLISECOND, 0);
		return oCld.getTime();
	}
	
	/**
	 * 指定格式的字符串转转成时间， 日期采用date
	 * 
	 * @param dateStr
	 * @return
	 * 
	 */
	public static Date parseToTime(Date date, String timeStr) {
		DateTime dateTime = null;
		int dateLen = timeStr.length();
		if (dateLen == TimeFormatter.HH_MM.length()) {
			DateTimeFormatter fotmatter = DateTimeFormat.forPattern(TimeFormatter.HH_MM);
			dateTime = fotmatter.parseDateTime(timeStr);
		} else if (dateLen == TimeFormatter.HH_MM_SS.length()) {
			DateTimeFormatter fotmatter = DateTimeFormat.forPattern(TimeFormatter.HH_MM_SS);
			dateTime = fotmatter.parseDateTime(timeStr);
		} else {
			throw new RuntimeException("暂不支持此类型的格式转换...");
		}
		Calendar oCld = Calendar.getInstance();
		oCld.setTime(date);
		oCld.set(Calendar.HOUR_OF_DAY, dateTime.getHourOfDay());
		oCld.set(Calendar.MINUTE, dateTime.getMinuteOfHour());
		oCld.set(Calendar.SECOND, dateTime.getSecondOfMinute());
		oCld.set(Calendar.MILLISECOND, 0);
		return oCld.getTime();
	}
	
	/**
	 * 指定格式的字符串转转成日期
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseToDate(String dateStr, String pattern) {
		DateTimeFormatter fotmatter = DateTimeFormat.forPattern(pattern);
		return fotmatter.parseDateTime(dateStr).toDate();
	}
	
	/**
	 * 判断字符串是否是有效日期
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String format) {
		boolean isDate = true;
		try {
			Format f = new SimpleDateFormat(format);
			Date d = (Date) f.parseObject(dateStr);
			isDate = f.format(d).equals(dateStr);
		} catch (ParseException e) {
			isDate = false;
		}
		return isDate;
	}
	
	/**
	 * 比较当前时间与传入时间
	 * 
	 * @param str
	 *            传入时分秒(HH:MM:ss)
	 * @return true - 当前时间大于传入时间
	 */
	public static boolean compareTime(String str) {
		Date parseToTime = parseToTime(str);
		Calendar calender1 = Calendar.getInstance();
		calender1.getTimeInMillis();
		if (calender1.getTimeInMillis() >= parseToTime.getTime()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 比较日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare(String date1, String date2) {
		Date d1 = parseToDate(date1);
		Date d2 = parseToDate(date2);
		return d1.compareTo(d2);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getChinaWeekDay(Date date) {
		return CHINA_WEEKDAY[getDayOfWeek(date) - 1];
	}

	/**
	 * @return
	 */
	public static String getChinaWeekDay() {
		return CHINA_WEEKDAY[getDayOfWeek() - 1];
	}
	
    public static void main(String[] args)
    {
        System.out.println(getDayOfYear());
        System.out.println(isSameWeek(DateUtil.parseDate("2017-01-01"), DateUtil.parseDate("2017-12-31")));
    }

}
