 package org.hlpay.common.util;

 import java.sql.Timestamp;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import org.apache.commons.lang3.StringUtils;

 public class DateUtil
 {
   public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
   public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddhhmmssSSS";
   public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

   public static String getCurrentDate() {
     String formatPattern_Short = "yyyyMMddhhmmss";
     SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
     return format.format(new Date());
   }

   public static String getSeqString() {
     SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
     return fm.format(new Date());
   }

   public static Timestamp getCurrentTimestamp() {
     return new Timestamp(System.currentTimeMillis());
   }

   public static String getCurrentTimeStr(String format) {
     format = StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format;
     Date now = new Date();
     return date2Str(now, format);
   }

   public static String date2Str(Date date) {
     return date2Str(date, "yyyy-MM-dd HH:mm:ss");
   }

   public static String date2Str(Date date, String format) {
     if (format == null || format.equals("")) {
       format = "yyyy-MM-dd HH:mm:ss";
     }
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     if (date != null) {
       return sdf.format(date);
     }
     return "";
   }

   public static String getRevTime() {
     Date date = new Date();
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);
     cal.add(5, 1);
     String dateString = (new SimpleDateFormat("yyyyMMddhhmmss")).format(cal.getTime());
     System.out.println(dateString);
     return dateString;
   }

   public static int compareDate(String date1, String date2, String format) {
     DateFormat df = new SimpleDateFormat(format);
     try {
       Date dt1 = df.parse(date1);
       Date dt2 = df.parse(date2);
       if (dt1.getTime() > dt2.getTime())
         return 1;
       if (dt1.getTime() < dt2.getTime()) {
         return -1;
       }
       return 0;
     }
     catch (Exception exception) {
       exception.printStackTrace();

       return 0;
     }
   }

   public static Date minusDateByMinute(Date date, int minute) {
     Date newDate = new Date(date.getTime() - (minute * 60 * 1000));
     return newDate;
   }
 }

