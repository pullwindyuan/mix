 package org.hlpay.base.service;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.security.SecureRandom;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import java.util.List;
 import java.util.Map;
 import javax.net.ssl.HttpsURLConnection;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.common.util.HttpClient;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.cloud.client.discovery.DiscoveryClient;
 import org.springframework.stereotype.Service;
 import org.springframework.web.client.RestTemplate;

 @Service
 public class Rest
 {
   private static final MyLog _log = MyLog.getLog(Rest.class);

   @Autowired
   private RestTemplate restTemplate;

   @Autowired
   private DiscoveryClient discoveryClient;

   public String postByUrl(String url) {
     List<String> services = this.discoveryClient.getServices();
     String httpResult = null;
     String resultStr = null;
     for (String s : services) {
       if (url.contains(s)) {

         httpResult = (String)this.restTemplate.postForObject(url, null, String.class, new Object[0]);

         break;
       }
     }
     if (httpResult == null) {
       resultStr = HttpClient.post(url, "");
       if (StringUtils.isNotEmpty(resultStr)) {
         return resultStr;
       }
     } else {
       return httpResult;
     }
     return null;
   }


   public String postByUrl(String url, Map<String, ?> uriVariables) {
     List<String> services = this.discoveryClient.getServices();
     String httpResult = null;

     for (String s : services) {
       if (url.contains(s))
       {
         httpResult = (String)this.restTemplate.postForObject(url, null, String.class, uriVariables);
       }
     }

     if (httpResult == null) {
       String resultStr = HttpClient.post(url, "");
       if (StringUtils.isNotEmpty(resultStr)) {
         return resultStr;
       }
     } else {
       return httpResult;
     }
     return null;
   }

   public String httpPost(String url) {
     StringBuffer sb = new StringBuffer();
     try {
       URL console = new URL(url);
       if ("https".equals(console.getProtocol())) {
         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());

         HttpsURLConnection con = (HttpsURLConnection)console.openConnection();
         con.setSSLSocketFactory(sc.getSocketFactory());
         con.setRequestMethod("POST");
         con.setDoInput(true);
         con.setDoOutput(true);
         con.setUseCaches(false);
         con.setConnectTimeout(30000);
         con.setReadTimeout(60000);
         con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         InputStream inputStream = null;
         try {
           inputStream = con.getInputStream();
           if (inputStream != null) {
             BufferedReader in = new BufferedReader(new InputStreamReader(inputStream), 1048576);
             while (true) {
               String line = in.readLine();
               if (line == null) {
                 break;
               }
               sb.append(line);
             }
             in.close();
           }
         } catch (IOException e) {
           e.printStackTrace();
         }

       } else if ("http".equals(console.getProtocol())) {
         HttpURLConnection con = (HttpURLConnection)console.openConnection();
         con.setRequestMethod("POST");
         con.setDoInput(true);
         con.setDoOutput(true);
         con.setUseCaches(false);
         con.setConnectTimeout(30000);
         con.setReadTimeout(60000);
         con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         InputStream inputStream = null;
         try {
           inputStream = con.getInputStream();
           if (inputStream != null) {
             BufferedReader in = new BufferedReader(new InputStreamReader(inputStream), 1048576);
             while (true) {
               String line = in.readLine();
               if (line == null) {
                 break;
               }
               sb.append(line);
             }
             in.close();
           }
         } catch (IOException e) {
           e.printStackTrace();
         }
       } else {
         _log.error("not do protocol. protocol=%s", new Object[] { console.getProtocol() });
       }
     } catch (Exception e) {
       _log.error(e, "httpPost exception. url:%s", new Object[] { url });
     }
     return sb.toString();
   }

   private static class TrustAnyTrustManager
     implements X509TrustManager
   {
     private TrustAnyTrustManager() {}

     public X509Certificate[] getAcceptedIssuers() {
       return new X509Certificate[0];
     }

     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

     public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
   }
 }
