 package org.hlpay.common.util;

 import java.io.UnsupportedEncodingException;
 import org.apache.commons.codec.binary.Base64;

 public class Base64Util
 {
   private static final String UTF_8 = "UTF-8";

   public static String decodeData(String inputData) {
     try {
       if (null == inputData) {
         return null;
       }
       return new String(Base64.decodeBase64(inputData.getBytes("UTF-8")), "UTF-8");
     } catch (UnsupportedEncodingException unsupportedEncodingException) {


       return null;
     }
   }



   public static String encodeData(String inputData) {
     try {
       if (null == inputData) {
         return null;
       }
       return new String(Base64.encodeBase64(inputData.getBytes("UTF-8")), "UTF-8");
     } catch (UnsupportedEncodingException unsupportedEncodingException) {


       return null;
     }
   }
   public static void main(String[] args) {
     System.out.println(encodeData("A"));
   }
 }

