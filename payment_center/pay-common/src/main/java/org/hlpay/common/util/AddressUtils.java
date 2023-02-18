 package org.hlpay.common.util;

 import java.io.BufferedReader;
 import java.io.DataOutputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.UnsupportedEncodingException;
 import java.net.HttpURLConnection;
 import java.net.InetAddress;
 import java.net.URL;
 import java.net.UnknownHostException;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;

 public class AddressUtils
 {
   public static String getAddresses(String ip) throws UnsupportedEncodingException {
     String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
     String returnStr = getResult(urlStr, ip);
     if (returnStr != null) {

       String[] temp = returnStr.split(",");
       if (temp.length < 3) {
         return "0";
       }
       String region = temp[5].split(":")[1].replaceAll("\"", "");
       region = decodeUnicode(region);

       String country = "";
       String area = "";

       String city = "";
       String county = "";
       String isp = "";
       for (int i = 0; i < temp.length; i++) {
         switch (i) {
           case 1:
             country = temp[i].split(":")[2].replaceAll("\"", "");
             country = decodeUnicode(country);
             break;
           case 3:
             area = temp[i].split(":")[1].replaceAll("\"", "");
             area = decodeUnicode(area);
             break;
           case 5:
             region = temp[i].split(":")[1].replaceAll("\"", "");
             region = decodeUnicode(region);
             break;
           case 7:
             city = temp[i].split(":")[1].replaceAll("\"", "");
             city = decodeUnicode(city);
             break;
           case 9:
             county = temp[i].split(":")[1].replaceAll("\"", "");
             county = decodeUnicode(county);
             break;
           case 11:
             isp = temp[i].split(":")[1].replaceAll("\"", "");
             isp = decodeUnicode(isp);
             break;
         }
       }
       String address = region + city;
       if (StringUtils.isBlank(address)) {
         address = "地球村";
       }
       return address;
     }
     return null;
   }

   private static String getResult(String urlStr, String ip) {
     URL url = null;
     HttpURLConnection connection = null;
     try {
       url = new URL(urlStr);
       connection = (HttpURLConnection)url.openConnection();
       connection.setConnectTimeout(5000);
       connection.setReadTimeout(5000);
       connection.setDoOutput(true);
       connection.setDoInput(true);
       connection.setRequestMethod("POST");
       connection.setUseCaches(false);
       connection.connect();
       DataOutputStream out = new DataOutputStream(connection.getOutputStream());
       out.writeBytes("ip=" + ip);
       out.flush();
       out.close();
       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

       StringBuffer buffer = new StringBuffer();
       String line = "";
       while ((line = reader.readLine()) != null) {
         buffer.append(line);
       }
       reader.close();
       return buffer.toString();
     } catch (IOException e) {
       e.printStackTrace();
     } finally {
       if (connection != null) {
         connection.disconnect();
       }
     }
     return null;
   }

   public static String decodeUnicode(String theString) {
     int len = theString.length();
     StringBuffer outBuffer = new StringBuffer(len);
     for (int x = 0; x < len; ) {
       char aChar = theString.charAt(x++);
       if (aChar == '\\') {
         aChar = theString.charAt(x++);
         if (aChar == 'u') {
           int value = 0;
           for (int i = 0; i < 4; i++) {
             aChar = theString.charAt(x++);
             switch (aChar) {
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
                 value = (value << 4) + aChar - 48;
                 break;
               case 'a':
               case 'b':
               case 'c':
               case 'd':
               case 'e':
               case 'f':
                 value = (value << 4) + 10 + aChar - 97;
                 break;
               case 'A':
               case 'B':
               case 'C':
               case 'D':
               case 'E':
               case 'F':
                 value = (value << 4) + 10 + aChar - 65;
                 break;
               default:
                 throw new IllegalArgumentException("Malformed      encoding.");
             }
           }
           outBuffer.append((char)value); continue;
         }
         if (aChar == 't') {
           aChar = '\t';
         } else if (aChar == 'r') {
           aChar = '\r';
         } else if (aChar == 'n') {
           aChar = '\n';
         } else if (aChar == 'f') {
           aChar = '\f';
         }
         outBuffer.append(aChar);
         continue;
       }
       outBuffer.append(aChar);
     }

     return outBuffer.toString();
   }

   public static String getIpAddr(HttpServletRequest request) {
     String ipAddress = null;
     try {
       ipAddress = request.getHeader("x-forwarded-for");
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("Proxy-Client-IP");
       }
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getHeader("WL-Proxy-Client-IP");
       }
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
         ipAddress = request.getRemoteAddr();
         if (ipAddress.equals("127.0.0.1")) {

           InetAddress inet = null;
           try {
             inet = InetAddress.getLocalHost();
           } catch (UnknownHostException e) {
             e.printStackTrace();
           }
           ipAddress = inet.getHostAddress();
         }
       }

       if (ipAddress != null && ipAddress.length() > 15)
       {
         if (ipAddress.indexOf(",") > 0) {
           ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
         }
       }
     } catch (Exception e) {
       ipAddress = "";
     }


     return ipAddress;
   }
 }

