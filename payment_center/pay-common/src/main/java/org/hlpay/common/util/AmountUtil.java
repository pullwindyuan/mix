 package org.hlpay.common.util;

 import java.text.DecimalFormat;
 import java.text.FieldPosition;


 public class AmountUtil
 {
   public static String convertDollar2Cent(String str) {
     DecimalFormat df = new DecimalFormat("0.00");
     StringBuffer sb = df.format(Double.parseDouble(str), new StringBuffer(), new FieldPosition(0));

     int idx = sb.toString().indexOf(".");
     sb.deleteCharAt(idx);
     while (sb.length() != 1 &&
       sb.charAt(0) == '0') {
       sb.deleteCharAt(0);
     }



     return sb.toString();
   }

   public static String convertCent2Dollar(String s) {
     long l;
     if ("".equals(s) || s == null) {
       return "";
     }

     if (s.length() != 0) {
       if (s.charAt(0) == '+') {
         s = s.substring(1);
       }
       l = Long.parseLong(s);
     } else {
       return "";
     }
     boolean negative = false;
     if (l < 0L) {
       negative = true;
       l = Math.abs(l);
     }
     s = Long.toString(l);
     if (s.length() == 1)
       return negative ? ("-0.0" + s) : ("0.0" + s);
     if (s.length() == 2) {
       return negative ? ("-0." + s) : ("0." + s);
     }
     return negative ? ("-" + s.substring(0, s.length() - 2) + "." + s
       .substring(s.length() - 2)) : (s.substring(0, s
         .length() - 2) + "." + s
       .substring(s.length() - 2));
   }

   public static String convertCent2DollarShort(String s) {
     String ss = convertCent2Dollar(s);
     ss = "" + Double.parseDouble(ss);
     if (ss.endsWith(".0"))
       return ss.substring(0, ss.length() - 2);
     if (ss.endsWith(".00")) {
       return ss.substring(0, ss.length() - 3);
     }
     return ss;
   }
 }

