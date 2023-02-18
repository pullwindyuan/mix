 package com.futuremap.base.util;

 import org.apache.commons.beanutils.Converter;

 import java.text.DateFormat;
 import java.text.DateFormatSymbols;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Locale;


 public class DateTimeConverter
   implements Converter
 {
   private static final String DATE = "yyyy-MM-dd";
   private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
   private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

   @Override
   public Object convert(Class type, Object value) {
     return toDate(type, value);
   }

   public static Object toDate(Class type, Object value) {
     if (value == null || "".equals(value)) {
         return null;
     }
     if (value instanceof String) {
       String dateValue = value.toString().trim();
       int length = dateValue.length();
       if (type.equals(Date.class)) {
         try {
           DateFormat formatter = null;
           if (length <= 10) {
             formatter = new SimpleDateFormat("yyyy-MM-dd", new DateFormatSymbols(Locale.CHINA));
             return formatter.parse(dateValue);
           }
           if (length <= 19) {
             formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new DateFormatSymbols(Locale.CHINA));
             return formatter.parse(dateValue);
           }
           if (length <= 23) {
             formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", new DateFormatSymbols(Locale.CHINA));
             return formatter.parse(dateValue);
           }
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
     }
     return value;
   }
 }





