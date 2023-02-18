 package org.hlpay.common.util;

 import java.math.BigDecimal;
 import org.apache.commons.lang3.StringUtils;

 public class CommonUtil
 {
   public static BigDecimal divide(String arg1, String arg2) {
     if (StringUtils.isBlank(arg1)) {
       arg1 = "0.0";
     }
     if (StringUtils.isBlank(arg2)) {
       arg2 = "0.0";
     }
     BigDecimal big3 = new BigDecimal("0.00");
     if (Double.parseDouble(arg2) != 0.0D) {
       BigDecimal big1 = new BigDecimal(arg1);
       BigDecimal big2 = new BigDecimal(arg2);
       big3 = big1.divide(big2, 2, 6);
     }
     return big3;
   }

   public static BigDecimal mul(String arg1, String arg2) {
     if (StringUtils.isBlank(arg1)) {
       arg1 = "0.0";
     }
     if (StringUtils.isBlank(arg2)) {
       arg2 = "0.0";
     }
     BigDecimal big1 = new BigDecimal(arg1);
     BigDecimal big2 = new BigDecimal(arg2);
     BigDecimal big3 = big1.multiply(big2);
     return big3;
   }

   public static BigDecimal sub(String arg1, String arg2) {
     if (StringUtils.isBlank(arg1)) {
       arg1 = "0.0";
     }
     if (StringUtils.isBlank(arg2)) {
       arg2 = "0.0";
     }
     BigDecimal big1 = new BigDecimal(arg1);
     BigDecimal big2 = new BigDecimal(arg2);
     BigDecimal big3 = big1.subtract(big2);
     return big3;
   }

   public static BigDecimal add(String arg1, String arg2) {
     if (StringUtils.isBlank(arg1)) {
       arg1 = "0.0";
     }
     if (StringUtils.isBlank(arg2)) {
       arg2 = "0.0";
     }
     BigDecimal big1 = new BigDecimal(arg1);
     BigDecimal big2 = new BigDecimal(arg2);
     BigDecimal big3 = big1.add(big2);
     return big3;
   }

   public static String add2(String arg1, String arg2) {
     if (StringUtils.isBlank(arg1)) {
       arg1 = "0.0";
     }
     if (StringUtils.isBlank(arg2)) {
       arg2 = "0.0";
     }
     BigDecimal big1 = new BigDecimal(arg1);
     BigDecimal big2 = new BigDecimal(arg2);
     BigDecimal big3 = big1.add(big2);
     return big3.toString();
   }

   public static String setScare(BigDecimal arg, int scare) {
     BigDecimal bl = arg.setScale(scare, 4);
     return String.valueOf(bl.doubleValue());
   }

   public static double setDifScare(double arg) {
     BigDecimal bd = new BigDecimal(arg);
     BigDecimal bl = bd.setScale(2, 4);
     return Double.parseDouble(bl.toString());
   }

   public static String setDifScare(String arg) {
     BigDecimal bd = new BigDecimal(arg);
     BigDecimal bl = bd.setScale(2, 4);
     return bl.toString();
   }

   public static String setDifScare(String arg, int i) {
     BigDecimal bd = new BigDecimal(arg);
     BigDecimal bl = bd.setScale(i, 4);
     return bl.toString();
   }

   public static String setFenScare(BigDecimal arg) {
     BigDecimal bl = arg.setScale(3, 4);
     String scare = String.valueOf(mul(bl.toString(), "100").doubleValue());
     String fenScare = scare + "%";
     return fenScare;
   }

   public static String subZeroAndDot(String s) {
     if (s.indexOf(".") > 0) {
       s = s.replaceAll("0+?$", "");
       s = s.replaceAll("[.]$", "");
     }
     return s;
   }
 }
