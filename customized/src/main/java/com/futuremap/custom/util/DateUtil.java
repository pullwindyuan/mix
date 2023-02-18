package com.futuremap.custom.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;




public class DateUtil {

	public static Date getDate(String DateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(DateStr);
		} catch (ParseException e) {
		}
		return date;
	}
	
	public static  String getDateString(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	public static  Timestamp getTimeStamp(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(dateStr);
		Timestamp t = new Timestamp(date.getTime());
		return t;
	}
	
	public static String getFormatterDateStrForTimestamp(Timestamp date, String formatter){
        String ret = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatter);
            ret = sf.format(date);
        }catch (Exception e){

        }

        return ret;
    }
	
	
	public static String getDayString(int i) {
		return getDateString("yyyy-MM-dd", getDayStart(i));
	}
	
	public static Boolean isSameDay(Timestamp a, Timestamp b) {
		if (a.toString().substring(0, 10).equals(b.toString().substring(0, 10))) {
			return true;
		} else {
			return false;
		}
	}
	
	
    /**获取当前时间所在年的周数
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        //c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    
    public static Date getBeforeHalfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -6);
        return c.getTime();
    }
    
    
    /**获取昨天起始时间
     * @param date
     * @return
     */
    public static Date getTodayStart() {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**获取昨天结束时间
     * @param date
     * @return
     */
    public static Date getTodayEnd() {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    
    /**获取某天起始时间
     * @param date
     * @return
     */
    public static Date getDayStart(int i) {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + i);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**获取某天结束时间
     * @param date
     * @return
     */
    public static Date getDayEnd(int i) {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + i);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    /**获取上一小时起始时间
     * @param date
     * @return
     */
    public static Date getForHourStart() {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        //c.add(Calendar.DATE, -1);
        c.add(Calendar.HOUR_OF_DAY, -1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**获取上一小时结束时间
     * @param date
     * @return
     */
    public static Date getForHourEnd() {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        //c.add(Calendar.DATE, -1);
        c.add(Calendar.HOUR_OF_DAY, -1);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    
    /**获取上一小时结束时间
     * @param date
     * @return
     */
    public static Date setDateDay(int day) {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        c.add(Calendar.DATE, day);
        return c.getTime();
    }
    
	/**
	 * 获取指定时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(int date, int hour, int minute, int second) {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
		c.add(Calendar.DATE, date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTime();
	}
    
    
    /**获取上周第一天
     * @param date
     * @return
     */
    public static Date getPastWeekFirstDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**获取上周最后一天
     * @param date
     * @return
     */
	public static Date getpastWeekLastDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
	//5 6 7 1 2 3 4 
	public static Date getPastWeekFirdayFirstDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
	
	public static Date getPastWeekFirdayEndDay(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
	
	public static List<String> getPastTenYear() {
		Calendar c = new GregorianCalendar();
		int nowYear = c.get(Calendar.YEAR) - 1;
		List<String> years =  new ArrayList<>(10);
		for(int i  = nowYear; i > nowYear - 10;i--) {
			years.add(String.valueOf(i));
		}
		return years;
	}
	
	public static Integer getYear() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.YEAR);
	}

	public static Integer getMonth() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MONTH) + 1;
	}
	
	public static Integer getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        return calendar.get(Calendar.MONTH)+1;
	}
	
	
	public static Integer getYear(Date date) {
		Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        return calendar.get(Calendar.YEAR);
	}
	
	public static Integer getYear(Timestamp date) {
		Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        return calendar.get(Calendar.YEAR);
	}
	
	
	
	
	/**获取上周第一天
     * @param date
     * @return
     */
    public  static String getPastWeekFirstDayStr(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return  sf.format(c.getTime());
    }
    
    public static Date getPastMonthFirst() {
    	//获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        return  calendar1.getTime();
    }
    
    
    
    
    public static Date getPastMonthEnd() {
    	Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        return calendar2.getTime();
    }
    
	public static Date getMonthFirst() {
		// 获取前一个月第一天
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		return calendar1.getTime();
	}
    
    
	public static Date getMonthEnd() {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, 1);
		calendar2.set(Calendar.DAY_OF_MONTH, 0);
		return calendar2.getTime();
	}
    
    public static Date getPastMonthEnd(Integer year, Integer month) {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.MONTH, c.get(Calendar.MONTH) -2);
    	//一个月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    public static Date getPastMonthStart(Integer year, Integer month) {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.MONTH, c.get(Calendar.MONTH) -2);
    	//一个月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
	public static Timestamp getStart(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		System.out.println(month - 1);
		// System.out.println(c.get(Calendar.MONTH));
		// c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		// System.out.println(c.getTime());
		// 一个月第一天
		// 一个月最后一天
		int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		System.out.println(c.getTime());
		return new Timestamp(c.getTimeInMillis());
	}
	
	public static Timestamp getEnd(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		//c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		// 一个月最后一天
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, lastDay);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
        System.out.println(c.getTime());
        return new Timestamp(c.getTimeInMillis());
	}
    
    
    /**获取上周第一天
     * @param date
     * @return
     */
    public  static String getPastWeekLastDayStr(Date date) {
    	Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        //c.add(Calendar.DATE, -7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return  sf.format(c.getTime());
    }

    /**
     * 获取昨天
     *
     * @param date
     * @return
     */
    public static String getYesterdayStr(Date date, String formatter) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, -1);
        SimpleDateFormat sf=new SimpleDateFormat(formatter);
        return  sf.format(c.getTime());
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔，
     *
     * @param date1
     * @param date2
     * @return 天数，不满一天返回1
     */
    public static int differentDays(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days == 0 ? 1 : days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔，(2019-07-29) - (2019-07-27)  + 1 = 3
     *
     * @param date1
     * @param date2
     * @return 天数+1
     */
    public static int getDiffDays(Timestamp end, Timestamp start) {
		return (int) ((Math.abs(end.getTime() - start.getTime()) / (1000 * 3600 * 24)) + 1);
	}
    
    public static  String getNextMonth(String date){
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM");
        Calendar c = new GregorianCalendar();
        try{
            c.setTime(sf.parse(date));
        }catch (ParseException e){

        }
        c.add(Calendar.MONTH, 1);
        return sf.format(c.getTime());
    }

    public static String getFormatterDateStr(String date, String formatter){
        String ret = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatter);
            ret = sf.format(sf.parse(date));
        }catch (ParseException e){

        }

        return ret;
    }

    public static String getFormatterDateStrForDate(Date date, String formatter){
        String ret = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatter);
            ret = sf.format(date);
        }catch (Exception e){

        }

        return ret;
    }

    public static Long getTime(String date){
        if(!date.contains(":")){
            date += " 23:59:59";
        }
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = new GregorianCalendar();
        Long time = 0L;
        try {
            c.setTime(sf.parse(date));
            time = c.getTimeInMillis()/1000;
        }catch (ParseException e){
        }

        return time;
    }
    
    /** 只保留前10位 年月日 yyyy-mm-dd
     * @param dateStr
     * @return
     */
    public String splitDateStrYMD(String dateStr) {
    	if(dateStr == null || "".equals(dateStr.trim())) {
    		return "";
    	}
    	return dateStr.substring(0, 10);
    }
    
    public Timestamp getTimeStamp(Long startDate) {
    	Timestamp startTime = null;
    	if(startDate != null) {
			 startTime = new Timestamp(startDate);
		}
    	return startTime;
    }
    
	/**
	 * 获取固定间隔时刻集合
	 * 
	 * @param start    开始时间
	 * @param end      结束时间
	 * @param interval 时间间隔(单位：分钟)
	 * @return
	 */
	public  List<String> getIntervalTimeList(Timestamp start, Timestamp end, int interval) {
		List<String> list = new ArrayList<>();
		while (start.getTime() <= end.getTime()) {
			list.add(splitDateStrYMD(start.toString()));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(start.getTime());
			calendar.add(Calendar.MINUTE, interval);
			if (calendar.getTime().getTime() > end.getTime()) {
				if (start.compareTo(end) < 0) {
					list.add(splitDateStrYMD(end.toString()));
				}
				start = new Timestamp(calendar.getTimeInMillis());
			} else {
				start = new Timestamp(calendar.getTimeInMillis());
			}

		}
		return list;
	}
	
	/**
     * 获取日期区间内的所有月份信息
     * @param minDate
     * @param maxDate
     * @return List<String>
     * @throws ParseException
     */
    public static List<String> getMonthBetween(Timestamp minDate, Timestamp maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }
    
    

    /**
     * 获取日期区间内的所有月份信息
     * @param minDate
     * @param maxDate
     * @return List<String>
     * @throws ParseException
     */
	public static Integer getMonthInteger(Timestamp date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		Calendar min = Calendar.getInstance();
		min.setTime(date);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		String str = sdf.format(min.getTime());
		return Integer.parseInt(str);
	}
	

}
