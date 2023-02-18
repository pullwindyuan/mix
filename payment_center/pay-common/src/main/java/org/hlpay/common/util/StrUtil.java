 package org.hlpay.common.util;

 import org.apache.commons.lang.StringEscapeUtils;






 public class StrUtil
 {
   public static String toString(Object obj) {
     return (obj == null) ? "" : obj.toString();
   }

   public static String toString(Object obj, String nullStr) {
     return (obj == null) ? nullStr : obj.toString();
   }

   public static String getJsonString(String string) {
     String msg = StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeJava(string));
     msg = msg.replace("\"{", "{");
     msg = msg.replace("}\"", "}");
     msg = msg.replace("\"[", "[");
     msg = msg.replace("]\"", "]");
     return msg;
   }
 }





