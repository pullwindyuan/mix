 package com.futuremap.base.util;

 import org.apache.commons.lang3.StringUtils;

 import java.sql.Timestamp;
 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.time.ZoneOffset;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.SimpleTimeZone;

 public class DateUtils
 {
   private static final String DEFAULT_CONVERT_PATTERN = "yyyyMMddHHmmssSSS";
   private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

   private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

   private static final SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

   private static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   private static String[] parsePatterns = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

   static String[] dayOfWeek = new String[] { " ", "日", "一", "二", "三", "四", "五", "六" };

   public static void main(String[] args) {
     System.out.println(getCurrDayOfWeek());
     System.out.println(getAfterDayOfWeek("6"));
     Calendar calendar = Calendar.getInstance();
     Date start = calendar.getTime();
     calendar.add(5, 3);
     Date end = calendar.getTime();
     isInWeek(start, end);
     System.out.println(getDayOfWeek(new Date()));
     System.out.println(getDayOfWeek("2020-12-20"));
     System.out.println(getWeekOfYear(end));

     String format = "yyyy-MM-dd HH:mm:ss:SSS";
     System.out.println(getDateToString(getCurrDayFirstDate(), format));
     System.out.println(getDateToString(getCurrWeekFirstDate(), format));
     System.out.println(getDateToString(getCurrMonthFirstDate(), format));
     System.out.println(getDateToString(getCurrYearFirstDate(), format));

     System.out.println(getDateToString(getCurrDayLastDate(), format));
     System.out.println(getDateToString(getCurrWeekLastDate(), format));
     System.out.println(getDateToString(getCurrMonthLastDate(), format));
     System.out.println(getDateToString(getCurrYearLastDate(), format));
   }
   public static String getCurrentDateTime() {
     String formatPattern_Short = "yyyy-MM-dd HH:mm:ss";
     SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
     return format.format(new Date());
   }

   public static String getCurrentDate() {
     String formatPattern_Short = "yyyy-MM-dd";
     SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
     return format.format(new Date());
   }

   public static LocalDateTime getLocalDateTime(Date date) {
     if (date == null) {
       return null;
     }
     return LocalDateTime.of(getYears(date),
         getMonth(date) + 1,
         getDay(date),
         getHours(date),
         getMinutes(date),
         getSecond(date));
   }

   public static LocalDateTime getLocalDateTime(Long timeStamp) {
     if (timeStamp == null) {
       return null;
     }
     Date date = new Date(timeStamp.longValue());
     return LocalDateTime.of(getYears(date),
         getMonth(date) + 1,
         getDay(date),
         getHours(date),
         getMinutes(date),
         getSecond(date));
   }

   public static Date getDate(LocalDateTime localDateTime) {
     if (localDateTime == null) {
       return null;
     }
     Calendar calendar = Calendar.getInstance();
     calendar.set(localDateTime.getYear(), localDateTime
         .getMonthValue(), localDateTime
         .getDayOfMonth(), localDateTime
         .getHour(), localDateTime
         .getMinute(), localDateTime
         .getSecond());
     return calendar.getTime();
   }

   public static Date getDate(LocalDate localDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(localDate.getYear(), localDate
         .getMonthValue(), localDate
         .getDayOfMonth(), 0, 0, 0);



     return calendar.getTime();
   }

   public static Long getTimestamp(LocalDateTime localDateTime) {
     if (localDateTime == null) {
       return null;
     }
     return Long.valueOf(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
   }

   public static String getCurrentTimeStrDefault() {
     return getCurrentTimeStr("yyyyMMddHHmmssSSS");
   }

   public static String getTimeStrDefault(Date date) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
     return dateFormat.format(date);
   }

   public static String getCurrentTimeStr(String pattern) {
     SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
     return dateFormat.format(new Date());
   }

   public static String getTimeStr(Date date, String pattern) {
     SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
     return dateFormat.format(date);
   }

   public static boolean isValidDefaultFormat(String dateTimeStr) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
     try {
       dateFormat.parse(dateTimeStr);
       return true;
     } catch (Exception e) {

       return false;
     }
   }

   public static String getYear() {
     return sdfYear.format(new Date());
   }

   Timestamp timestamp = new Timestamp((new Date()).getTime());

   public static String getDay() {
     return sdfDay.format(new Date());
   }

   public static String getDays() {
     return sdfDays.format(new Date());
   }

   public static String getTime() {
     return sdfTime.format(new Date());
   }

   public static Date formatDate(String date) {
     DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
     try {
       return fmt.parse(date);
     } catch (ParseException e) {
       e.printStackTrace();
       return null;
     }
   }

   public static boolean isValidDate(String s) {
     DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
     try {
       fmt.parse(s);
       return true;
     } catch (Exception e) {

       return false;
     }
   }

   public static int getDiffYear(String startTime, String endTime) {
     DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
     try {
       int years = (int)((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / 86400000L / 365L);
       return years;
     } catch (Exception e) {

       return 0;
     }
   }

   public static long getDaySub(String beginDateStr, String endDateStr) {
     long day = 0L;
     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     Date beginDate = null;
     Date endDate = null;

     try {
       beginDate = format.parse(beginDateStr);
       endDate = format.parse(endDateStr);
     } catch (ParseException e) {
       e.printStackTrace();
     }
     day = (endDate.getTime() - beginDate.getTime()) / 86400000L;


     return day;
   }

   public static String getAfterDayDate(String days) {
     int daysInt = Integer.parseInt(days);

     Calendar calendar = Calendar.getInstance();
     calendar.add(5, daysInt);
     Date date = calendar.getTime();

     SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String dateStr = sdfd.format(date);

     return dateStr;
   }

   public static Long getAfterMinuteDate(int minutes) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(12, minutes);
     Date date = calendar.getTime();
     return Long.valueOf(date.getTime());
   }

   public static String getBeforeDayDate(String days) {
     int daysInt = Integer.parseInt(days);

     Calendar calendar = Calendar.getInstance();
     calendar.add(5, daysInt);
     Date date = calendar.getTime();

     String dateStr = sdfDays.format(date);

     return dateStr;
   }

   public static String getAfterDayOfWeek(String days) {
     int daysInt = Integer.parseInt(days);

     Calendar calendar = Calendar.getInstance();
     calendar.add(5, daysInt);
     Date date = calendar.getTime();

     SimpleDateFormat sdf = new SimpleDateFormat("E");
     String dateStr = sdf.format(date);

     return dateStr;
   }

   public static String getAfterDayOfWeek(int days) throws Exception {
     if (days < 0) {
       throw new Exception("参数错误");
     }

     Calendar calendar = Calendar.getInstance();
     calendar.add(5, days);
     Date date = calendar.getTime();

     SimpleDateFormat sdf = new SimpleDateFormat("E");
     String dateStr = sdf.format(date);

     return dateStr;
   }

   public static String getBeforeDayOfWeek(int days) throws Exception {
     if (days > 0) {
       throw new Exception("参数错误");
     }
     int daysInt = 0 - days;

     Calendar calendar = Calendar.getInstance();
     calendar.add(5, daysInt);
     Date date = calendar.getTime();

     SimpleDateFormat sdf = new SimpleDateFormat("E");
     String dateStr = sdf.format(date);

     return dateStr;
   }

   public static String getCurrDayOfWeek() {
     Calendar calendar = Calendar.getInstance();
     Date date = calendar.getTime();
     SimpleDateFormat sdf = new SimpleDateFormat("E");
     String dateStr = sdf.format(date);
     return dateStr;
   }

   public static boolean isInHour(Date start, Date end) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(start);
     int startIndex = calendar.get(10);
     int startYear = calendar.get(1);
     System.out.println("起始日期是第：" + startIndex + "天");
     calendar.setTime(end);
     int endYear = calendar.get(1);
     int endIndex = calendar.get(10);
     System.out.println("结束日期是第：" + endIndex + "天");

     if (startIndex - endIndex == 0 && startYear == endYear) {
       System.out.println("两个时间处于同一天");
       return true;
     }
     System.out.println("两个时间处于不同天");
     return false;
   }

   public static boolean isInDay(Date start, Date end) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(start);
     int startIndex = calendar.get(6);
     int startYear = calendar.get(1);
     System.out.println("起始日期是第：" + startIndex + "天");
     calendar.setTime(end);
     int endYear = calendar.get(1);
     int endIndex = calendar.get(6);
     System.out.println("结束日期是第：" + endIndex + "天");

     if (startIndex - endIndex == 0 && startYear == endYear) {
       System.out.println("两个时间处于同一天");
       return true;
     }
     System.out.println("两个时间处于不同天");
     return false;
   }

   public static boolean isInWeek(Date start, Date end) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(start);
     int startWeek = calendar.get(3);
     int startYear = calendar.get(1);
     System.out.println("起始日期是第：" + startWeek + "周");
     calendar.setTime(end);
     int endYear = calendar.get(1);
     int endWeek = calendar.get(3);
     System.out.println("结束日期是第：" + endWeek + "周");

     if (startWeek - endWeek == 0 && startYear == endYear) {
       System.out.println("两个时间处于同一周");
       return true;
     }
     System.out.println("两个时间处于不同周");
     return false;
   }

   public static boolean isInMonth(Date start, Date end) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(start);
     int startIndex = calendar.get(2);
     int startYear = calendar.get(1);
     System.out.println("起始日期是第：" + startIndex + "月");
     calendar.setTime(end);
     int endYear = calendar.get(1);
     int endIndex = calendar.get(2);
     System.out.println("结束日期是第：" + endIndex + "月");

     if (startIndex - endIndex == 0 && startYear == endYear) {
       System.out.println("两个时间处于同一月");
       return true;
     }
     System.out.println("两个时间处于不同月");
     return false;
   }

   public static boolean isInYear(Date start, Date end) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(start);
     int startYear = calendar.get(1);
     calendar.setTime(end);
     int endYear = calendar.get(1);

     if (startYear == endYear) {
       System.out.println("两个时间处于同一年");
       return true;
     }
     System.out.println("两个时间处于不同年");
     return false;
   }

   public static boolean isInWeek(Date endTime) {
     return isInWeek(new Date(), endTime);
   }

   public static Date getCurrDayFirstDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return formatDateTime(calendar.getTime());
   }

   public static Date getCurrWeekFirstDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 1 - calendar.get(7));
     return formatDateTime(calendar.getTime());
   }





   public static Date getCurrMonthFirstDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return formatDateTime(calendar.getTime());
   }





   public static Date getCurrYearFirstDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(2, 1);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return formatDateTime(calendar.getTime());
   }





   public static Date getCurrDayLastDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(5, 1);
     return formatDateTime(calendar.getTime());
   }






   public static Date getCurrWeekLastDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 8 - calendar.get(7));
     return formatDateTime(calendar.getTime());
   }





   public static Date getCurrMonthLastDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(2, 1);
     return formatDateTime(calendar.getTime());
   }





   public static Date getCurrYearLastDate() {
     Calendar calendar = Calendar.getInstance();
     calendar.set(2, 0);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(1, 1);
     return formatDateTime(calendar.getTime());
   }












   public static LocalDateTime getDayFirstDate(LocalDateTime refDate) {
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }












   public static Date getDayLastDate(LocalDate refDate) {
     return getDate(refDate);
   }












   public static LocalDate getLocalDayLastDate(LocalDate refDate) {
     refDate = refDate.plusDays(1L);
     return refDate;
   }













   public static LocalDateTime getWeekFirstDate(LocalDateTime refDate) {
     refDate = refDate.minusDays(refDate.getDayOfWeek().getValue());
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }













   public static LocalDateTime getMonthFirstDate(LocalDateTime refDate) {
     refDate = refDate.withDayOfMonth(1);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }














   public static LocalDateTime getYearFirstDate(LocalDateTime refDate) {
     refDate = refDate.withMonth(1);
     refDate = refDate.withDayOfMonth(1);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }













   public static LocalDateTime getLocalDayLastDate(LocalDateTime refDate) {
     refDate = refDate.plusDays(1L);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }













   public static Date getDayLastDate(LocalDateTime refDate) {
     refDate = refDate.plusDays(1L);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return getDate(refDate);
   }


   public static Date getDayLastDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     return formatDateTime(calendar.getTime());
   }











   public static LocalDateTime getWeekLastDate(LocalDateTime refDate) {
     refDate = refDate.plusDays((8 - refDate.getDayOfWeek().getValue()));
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }





   public static LocalDateTime getMonthLastDate(LocalDateTime refDate) {
     if (refDate == null) {
       return null;
     }









     refDate = refDate.plusMonths(1L);
     refDate = refDate.withDayOfMonth(1);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }





   public static LocalDateTime getYearLastDate(LocalDateTime refDate) {
     refDate = refDate.plusYears(1L);
     refDate = refDate.withMonth(1);
     refDate = refDate.withDayOfMonth(1);
     refDate = refDate.withHour(0);
     refDate = refDate.withMinute(0);
     refDate = refDate.withSecond(0);
     return refDate;
   }





   public static Date getDayFirstDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     return formatDateTime(calendar.getTime());
   }






   public static Date getWeekFirstDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 1 - calendar.get(7));
     return formatDateTime(calendar.getTime());
   }





   public static Date getMonthFirstDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     return formatDateTime(calendar.getTime());
   }





   public static Date getYearFirstDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(2, 1);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     return formatDateTime(calendar.getTime());
   }





   public static Date getLocalDayLastDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(5, 1);
     return formatDateTime(calendar.getTime());
   }






   public static Date getWeekLastDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 8 - calendar.get(7));
     return formatDateTime(calendar.getTime());
   }





   public static Date getMonthLastDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     calendar.add(2, 1);
     return formatDateTime(calendar.getTime());
   }





   public static Date getYearLastDate(Date refDate) {
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(refDate);
     calendar.set(2, 0);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.set(Calendar.SECOND, 0);
     calendar.set(Calendar.MILLISECOND, 0);
     calendar.add(1, 1);
     return formatDateTime(calendar.getTime());
   }





   public static Date getDaysDurationFirstDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(5, param);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return calendar.getTime();
   }





   public static Date getAfterDaysDuration(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(5, param);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return calendar.getTime();
   }





   public static Date getAfter(int field, int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(field, param);
     return calendar.getTime();
   }





   public static Date getAfterMinuteDuration(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(12, param);
     return calendar.getTime();
   }

   public static Long getBetweenValueInMilSeconds(Date start, Date end) {
     return Long.valueOf(end.getTime() - start.getTime());
   }




   public static Date getAfterDays(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(5, param);
     return calendar.getTime();
   }





   public static LocalDateTime getLocalDateTimeByOffsetDay(int param) {
     LocalDateTime localDateTime = LocalDateTime.now();
     localDateTime = localDateTime.minusDays(param);
     localDateTime = localDateTime.withHour(0);
     localDateTime = localDateTime.withMinute(0);
     localDateTime = localDateTime.withSecond(0);
     return localDateTime;
   }





   public static LocalDateTime getLocalDateTimeByOffsetDay(int param, LocalDateTime reference) {
     if (reference == null) {
       reference = LocalDateTime.now();
     }
     LocalDateTime localDateTime = reference;
     localDateTime = localDateTime.minusDays(param);
     localDateTime = localDateTime.withHour(0);
     localDateTime = localDateTime.withMinute(0);
     localDateTime = localDateTime.withSecond(0);
     return localDateTime;
   }






   public static Date getWeeksDurationFirstDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 1 - calendar.get(7));
     return calendar.getTime();
   }





   public static Date getMonthsDurationFirstDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return calendar.getTime();
   }





   public static Date getAfterMonthsDuration(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.add(2, param);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return calendar.getTime();
   }





   public static Date getYearsDurationFirstDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(2, 1);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     return calendar.getTime();
   }





   public static Date getDaysDurationLastDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(5, 1);
     return calendar.getTime();
   }






   public static Date getWeeksDurationLastDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(7, 8 - calendar.get(7));
     return calendar.getTime();
   }





   public static Date getMonthsDurationLastDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(2, 1);
     return calendar.getTime();
   }





   public static Date getYearsDurationLastDate(int param) {
     Calendar calendar = Calendar.getInstance();
     calendar.set(2, 0);
     calendar.set(5, 1);
     calendar.set(11, 0);
     calendar.set(12, 0);
     calendar.set(13, 0);
     calendar.set(14, 0);
     calendar.add(1, param);
     return calendar.getTime();
   }






   public static String date2Str(Date date) {
     return date2Str(date, "yyyy-MM-dd HH:mm:ss");
   }






   public static Date str2Date(String date) {
     if (StringUtils.isNotBlank(date)) {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try {
         return sdf.parse(date);
       } catch (ParseException e) {
         e.printStackTrace();

         return new Date();
       }
     }  return null;
   }







   public static Date str2Date(String date, String format) {
     if (StringUtils.isNotBlank(date)) {
       SimpleDateFormat sdf = new SimpleDateFormat(format);
       try {
         return sdf.parse(date);
       } catch (ParseException e) {
         e.printStackTrace();

         return new Date();
       }
     }  return null;
   }








   public static String date2Str(Date date, String format) {
     if (null == format) {
       format = "yyyy-MM-dd HH:mm:ss";
     }
     if (date != null) {
       SimpleDateFormat sdf = new SimpleDateFormat(format);
       return sdf.format(date);
     }
     return "";
   }






   public static String getTimes(String StrDate) {
     String resultTimes = "";

     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


     try {
       Date now = new Date();
       Date date = df.parse(StrDate);
       long times = now.getTime() - date.getTime();
       long day = times / 86400000L;
       long hour = times / 3600000L - day * 24L;
       long min = times / 60000L - day * 24L * 60L - hour * 60L;
       long sec = times / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;

       StringBuffer sb = new StringBuffer();

       if (hour > 0L) {
         sb.append(hour + "小时前");
       } else if (min > 0L) {
         sb.append(min + "分钟前");
       } else {
         sb.append(sec + "秒前");
       }

       resultTimes = sb.toString();
     } catch (ParseException e) {
       e.printStackTrace();
     }

     return resultTimes;
   }

   public static String getTimestamp() {
     return String.valueOf(System.currentTimeMillis() / 1000L);
   }

   public static String getDayOfWeekToString(String date) {
     return "星期" + dayOfWeek[getDayOfWeek(date)];
   }


   public static String getDayOfWeekToString(Date date) {
     return "星期" + dayOfWeek[getDayOfWeek(date)];
   }

   public static String getDayOfWeekToString(String head, String date) {
     return head + dayOfWeek[getDayOfWeek(date)];
   }

   public static String getDayOfWeekToString(String head, Date date) {
     return head + dayOfWeek[getDayOfWeek(date)];
   }

   public static int getDayOfWeek(String date) {
     return getDayOfWeek(getDate(date));
   }

   public static int getCurrDayIndexOfWeek() {
     return getDayOfWeek(new Date());
   }

   public static int getWeekOfYear(Date date) {
     return getByType(date, 3);
   }

   public static int getDayOfWeek(Date date) {
     return getByType(date, 7);
   }

   public static int getByType(Date date, int type) {
     if (date == null) {
       return -1;
     }
     SimpleDateFormat sDateFormat1 = new SimpleDateFormat("E");
     Calendar cale1 = sDateFormat1.getCalendar();
     cale1.setTime(date);
     int curr_day = cale1.get(type);
     return curr_day;
   }

   public static Date getDateWithoutTime(String dateString) {
     return getDate(dateString, "yyyy-MM-dd");
   }

   public static String getYearMonthDayString(String dateString) {
     Date date = getDate(dateString, "yyyy-MM-dd");
     return getDateToString(date, "yyyy-MM-dd");
   }

   public static String getYearMonthString(String dateString) {
     Date date = getDate(dateString, "yyyy-MM-dd");
     return getDateToString(date, "yyyy-MM");
   }

   public static String getYearString(String dateString) {
     Date date = getDate(dateString, "yyyy-MM-dd");
     return getDateToString(date, "yyyy");
   }

   public static Date getDate(String date) {
     return formatDateTime(date);
   }


   public static Date getDate(Long timeStamp) {
     return new Date(timeStamp.longValue());
   }

   public static Date formatDateTime(String date) {
     date = date.trim();
     if (StringUtils.isBlank(date)) {
       date = "1900-01-01 00:00:00";
     }
     return getDate(date, "yyyy-MM-dd HH:mm:ss");
   }

   public static Date formatDateTime(Date date) {
     return getDate(date, "yyyy-MM-dd HH:mm:ss");
   }

   public static Date getDate(String date, String format) {
     if (StringUtils.isBlank(date)) {
       return null;
     }
     SimpleDateFormat sDateFormat1 = new SimpleDateFormat(format);
     Calendar cale1 = sDateFormat1.getCalendar();
     try {
       return sDateFormat1.parse(date.replace("/", "-"));
     } catch (ParseException e) {
       e.printStackTrace();
       return null;
     }
   }

   public static Date getDate(Date date, String format) {
     if (date == null) {
       return null;
     }
     SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
     try {
       return sDateFormat.parse(sDateFormat.format(date));
     } catch (ParseException e) {
       e.printStackTrace();
       return null;
     }
   }

   private static Date getCurrDate() {
     return new Date();
   }

   public static String getCurrDateWithoutTimeToString() {
     return getDateToString(getCurrDate(), "yyyy-MM-dd");
   }

   public static String getCurrDateToString() {
     return getDateToString(getCurrDate(), "yyyy-MM-dd HH:mm");
   }

   public static String getDateOnlyTimeToString(Date date) {
     return getDateToString(date, "HH:mm");
   }

   public static String getDateOnlyTimeToString(String date) {
     return getDateToString(getDate(date), "HH:mm");
   }

   public static String getDateWithoutTimeToString(String date) {
     return getDateToString(getDate(date), "yyyy-MM-dd");
   }

   public static String getDateWithoutTimeToString(Date date) {
     return getDateToString(date, "yyyy-MM-dd");
   }

   public static String getDateToString(Date date) {
     return getDateToString(date, "yyyy-MM-dd HH:mm:ss.SSS");
   }

   public static String getDateToString(Date date, String format) {
     SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
     return sDateFormat.format(date);
   }

   public static String mergeDate(String date1, String date2, String between) {
     return mergeDate(getDate(date1), getDate(date2), between);
   }

   public static String mergeDate(Date date1, Date date2, String between) {
     String merge = null;
     Date start = null;
     Date end = null;
     int temp = compareDate(date1, date2);
     if (temp > 1) {
       start = date2;
       end = date1;
       temp = compareDateWithoutTime(date1, date2);
       if (temp == 0) {
         return merge = getDateToString(date2) + between + getDateOnlyTimeToString(date1);
       }
     } else if (temp == 1) {
       start = date2;
       end = date1;
     } else {
       start = date1;
       end = date2;
       temp = compareDateWithoutTime(date1, date2);
       if (temp == 0) {
         return merge = getDateToString(start) + between + getDateOnlyTimeToString(end);
       }
     }
     return merge = getDateToString(start) + between + getDateToString(end);
   }

   public static String mergeDateWithOutTime(String date1, String date2, String between) {
     return mergeDateWithOutTime(getDate(date1), getDate(date2), between);
   }

   public static String mergeDateWithOutTime(Date date1, Date date2, String between) {
     String merge = null;
     Date start = null;
     Date end = null;
     int temp = compareDateWithoutTime(date1, date2);
     if (temp > 1) {
       start = date2;
       end = date1;
     } else {
       start = date1;
       end = date2;
     }
     return merge = getDateWithoutTimeToString(start) + between + getDateWithoutTimeToString(end);
   }

   public static String mergeDateOnlyTime(String date1, String date2, String between) {
     return mergeDateOnlyTime(getDate(date1), getDate(date2), between);
   }

   public static String getHoursString(String date) {
     return String.format("%02d", new Object[] { Integer.valueOf(getCalendar(getDate(date), "yyyy-MM-dd HH:mm").get(11)) });
   }

   public static String getMinutesString(String date) {
     return String.format("%02d", new Object[] { Integer.valueOf(getCalendar(getDate(date), "yyyy-MM-dd HH:mm").get(12)) });
   }

   public static int getHours(String date) {
     return getCalendar(getDate(date), "yyyy-MM-dd HH:mm").get(11);
   }

   public static int getMinutes(String date) {
     return getCalendar(getDate(date), "yyyy-MM-dd HH:mm").get(12);
   }

   public static String getHoursString(Date date) {
     return String.format("%02d", new Object[] { Integer.valueOf(getCalendar(date, "yyyy-MM-dd HH:mm").get(11)) });
   }

   public static String getMinutesString(Date date) {
     return String.format("%02d", new Object[] { Integer.valueOf(getCalendar(date, "yyyy-MM-dd HH:mm").get(12)) });
   }

   public static int getHours(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(11);
   }

   public static int getDay(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(5);
   }

   public static int getYears(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(1);
   }

   public static int getSecond(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(13);
   }

   public static int getMinutes(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(12);
   }

   public static int getMonth(Date date) {
     return getCalendar(date, "yyyy-MM-dd HH:mm").get(2);
   }

   public static String checkHours(String hours) {
     return checkTime(hours, 24);
   }

   public static String checkMinutes(String minutes) {
     return checkTime(minutes, 60);
   }

   public static int checkHours(int hours) {
     return checkTime(hours, 24);
   }

   public static int checkMinutes(int minutes) {
     return checkTime(minutes, 60);
   }

   public static String checkHoursToString(int hours) {
     return String.format("%02d", new Object[] { Integer.valueOf(checkTime(hours, 24)) });
   }

   public static String checkMinutesToString(int minutes) {
     return String.format("%02d", new Object[] { Integer.valueOf(checkTime(minutes, 60)) });
   }

   public static int checkHoursToInteger(String hours) {
     return Integer.parseInt(checkTime(hours, 24));
   }

   public static int checkMinutesToInteger(String minutes) {
     return Integer.parseInt(checkTime(minutes, 60));
   }

   public static String checkTime(String minutes, int standard) {
     if (StringUtils.isBlank(minutes)) {
       return "00";
     }
     return String.format("%02d", new Object[] { Integer.valueOf(checkTime(Integer.parseInt(minutes), standard)) });
   }

   public static int checkTime(int minutes, int standard) {
     if (minutes >= standard || minutes < 0) {
       return minutes % standard;
     }
     return minutes;
   }







   public static String getDeltaTimeToString(Date date1, Date date2, String format) {
     long start, end;
     Calendar calendar = getCalendar(date1, "yyyy-MM-dd HH:mm");



     int result = compareDate(date1, date2);
     if (compareDate(date1, date2) > 0) {
       end = date1.getTime();
       start = date2.getTime();
     } else {
       start = date1.getTime();
       end = date2.getTime();
     }
     String s = String.format("%s天%s.%s小时", new Object[] { Integer.toString((int)(end - start) / 3600000 / 24), Integer.toString((int)(end - start) / 3600000), Integer.toString((int)(end - start) % 3600000 / 60000) });
     return s;
   }

   public static Calendar getCalendar(Date date, String format) {
     SimpleDateFormat sDateFormat1 = new SimpleDateFormat(format);
     Calendar cale1 = sDateFormat1.getCalendar();
     cale1.setTime(date);
     return cale1;
   }

   public static String mergeDateOnlyTime(Date date1, Date date2, String between) {
     String merge = null;
     Date start = null;
     Date end = null;
     int temp = compareDate(date1, date2);
     if (temp > 1) {
       start = date2;
       end = date1;
     } else {
       start = date1;
       end = date2;
     }
     return merge = getDateOnlyTimeToString(start) + between + getDateOnlyTimeToString(end);
   }


   public static int compareDateWithoutTime(String date1, String date2) {
     return compareDate(getDateWithoutTime(date1), getDateWithoutTime(date2));
   }

   public static int compareDateWithoutTime(Date date1, Date date2) {
     return compareDateWithoutTime(getDateWithoutTimeToString(date1), getDateWithoutTimeToString(date2));
   }

   public static int compareDate(String date1, String date2) {
     return compareDate(date1, date2, "yyyy-MM-dd HH:mm");
   }

   public static int compareDate(Date date1, Date date2) {
     return compareDate(date1, date2, "yyyy-MM-dd HH:mm");
   }








   public static int compareDate(String date1, String date2, String format) {
     return compareDate(getDate(date1), getDate(date2), format);
   }






   public static int compareDate(Date date1, Date date2, String format) {
     SimpleDateFormat sDateFormat1 = new SimpleDateFormat(format);
     Calendar cale1 = sDateFormat1.getCalendar();

     SimpleDateFormat sDateFormat2 = new SimpleDateFormat(format);
     Calendar cale2 = sDateFormat2.getCalendar();

     cale1.setTime(date1);
     cale2.setTime(date2);

     int i = cale1.compareTo(cale2);
     return i;
   }







   public static int daysBetween(Date date1, Date date2) {
     Calendar cal = Calendar.getInstance();
     cal.setTime(date1);
     long time1 = cal.getTimeInMillis();
     cal.setTime(date2);
     long time2 = cal.getTimeInMillis();
     long between_days = (time2 - time1) / 86400000L;

     return Integer.parseInt(String.valueOf(between_days));
   }








   public static int between(Date curr, Date start, Date end) throws Exception {
     Long currTime = Long.valueOf(curr.getTime());
     Long startTime = Long.valueOf(start.getTime());
     Long endTime = Long.valueOf(end.getTime());
     return between(currTime, startTime, endTime);
   }








   public static int between(LocalDateTime curr, LocalDateTime start, LocalDateTime end) throws Exception {
     Long currTime = Long.valueOf(curr.toInstant(ZoneOffset.of("+8")).toEpochMilli());
     Long startTime = Long.valueOf(start.toInstant(ZoneOffset.of("+8")).toEpochMilli());
     Long endTime = Long.valueOf(end.toInstant(ZoneOffset.of("+8")).toEpochMilli());
     return between(currTime, startTime, endTime);
   }








   public static int between(Long currTime, Long startTime, Long endTime) throws Exception {
     try {
       if (currTime.longValue() < startTime.longValue()) {
         return 0;
       }

       if (currTime.longValue() > endTime.longValue()) {
         return 2;
       }

       return 1;
     } catch (Exception e) {
       e.printStackTrace();
       throw new Exception("时间参数错误");
     }
   }








   public static String getDayByMonth(String datetime) {
     StringBuffer buffer = new StringBuffer();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
     Calendar calendar = new GregorianCalendar();
     try {
       Date date = sdf.parse(datetime);
       calendar.setTime(date);
       int day = calendar.getActualMaximum(5);
       if (day < 10) {
         buffer = buffer.append("0").append(String.valueOf(day));
       } else {
         buffer = buffer.append(String.valueOf(day));
       }
     } catch (ParseException e) {
       e.printStackTrace();
     }
     return buffer.toString();
   }





   public static Date parseDate(Object str) {
     if (str == null)
     {
       return null;
     }

     try {
       return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
     }
     catch (ParseException e) {

       return null;
     }
   }


   public static final String parseDateToStr(String format, Date date) {
     return (new SimpleDateFormat(format)).format(date);
   }

   /**
    * mongo 日期查询isodate
    * @param dateStr
    * @return
    */
   public static Date dateToISODate(String dateStr){
     //T代表后面跟着时间，Z代表UTC统一时间
     Date date = formatD(dateStr);
     SimpleDateFormat format =
             new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
     format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
     String isoDate = format.format(date);
     try {
       return format.parse(isoDate);
     } catch (ParseException e) {
       e.printStackTrace();
     }
     return null;
   }
   /** 时间格式(yyyy-MM-dd HH:mm:ss) */
   public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

   public static Date formatD(String dateStr){
     return formatD(dateStr,DATE_TIME_PATTERN);
   }

   public static Date formatD(String dateStr ,String format)  {
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
     Date ret = null ;
     try {
       ret = simpleDateFormat.parse(dateStr) ;
     } catch (ParseException e) {
       //
     }
     return ret;
   }


 }

