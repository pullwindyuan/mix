 package org.hlpay.common.util;

 import java.net.InetAddress;
 import java.net.NetworkInterface;
 import java.net.SocketException;
 import java.net.UnknownHostException;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;











 public class IPUtility
 {
   public static String getLocalhostIp() {
     String ip = "";
     try {
       ip = InetAddress.getLocalHost().getHostAddress();
     } catch (Exception e) {
       return null;
     }
     return ip;
   }

   public static List<String> getIpAddrs() throws Exception {
     List<String> IPs = new ArrayList<>();
     Enumeration<NetworkInterface> allNetInterfaces = null;
     allNetInterfaces = NetworkInterface.getNetworkInterfaces();
     InetAddress ip = null;
     while (allNetInterfaces.hasMoreElements()) {
       NetworkInterface netInterface = allNetInterfaces.nextElement();
       Enumeration<?> addresses = netInterface.getInetAddresses();
       while (addresses.hasMoreElements()) {
         ip = (InetAddress)addresses.nextElement();
         if (ip != null && ip instanceof java.net.Inet4Address && ip.getHostAddress().indexOf(".") != -1) {
           IPs.add(ip.getHostAddress());
         }
       }
     }
     return IPs;
   }





   public static String getLocalIP() {
     String ip = "";

     try {
       Enumeration<?> e1 = NetworkInterface.getNetworkInterfaces();
       while (e1.hasMoreElements()) {
         NetworkInterface ni = (NetworkInterface)e1.nextElement();
         Enumeration<?> e2 = ni.getInetAddresses();
         while (e2.hasMoreElements()) {
           InetAddress ia = (InetAddress)e2.nextElement();
           if (ia instanceof java.net.Inet6Address)
             continue;
           if (!ia.isLoopbackAddress()) {
             ip = ia.getHostAddress();
           }
         }

       }
     } catch (SocketException e) {
       e.printStackTrace();
       return "";
     }
     return ip;
   }

   public static void main(String[] args) throws Exception {
     System.out.println(getLocalIP());
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
         if ("127.0.0.1".equals(ipAddress)) {

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





