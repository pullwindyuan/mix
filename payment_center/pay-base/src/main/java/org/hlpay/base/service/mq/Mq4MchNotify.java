 package org.hlpay.base.service.mq;

 import com.alibaba.fastjson.JSONObject;
 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.security.SecureRandom;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import java.util.concurrent.TimeUnit;
 import javax.net.ssl.HttpsURLConnection;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import org.hlpay.base.mq.Mq4Base;
 import org.hlpay.base.service.BaseNotifyService;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisUtil;
 import org.springframework.amqp.AmqpException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 @Service
 public class Mq4MchNotify
   extends BaseNotifyService
 {
   private static final MyLog _log = MyLog.getLog(Mq4MchNotify.class);



   @Autowired
   private Mq4Base mq4Base;



   @Autowired
   private RedisUtil redisUtil;



   public void send(String queueName, String msg) throws AmqpException {
     String key = String.valueOf((msg + queueName).hashCode());
     if (this.redisUtil.hasKey(key).booleanValue()) {
       _log.warn("请勿重复发送消息:" + msg, new Object[0]);
       return;
     }
     this.redisUtil.set(key, "1", 30L, TimeUnit.SECONDS);
     this.mq4Base.send(queueName, msg);
     _log.info("设置消息发送标志：" + key, new Object[0]);
   }


   public void forceSend(String queueName, String msg) throws AmqpException {
     this.mq4Base.send(queueName, msg);
   }

   public void send(String queueName, String msg, long delay) throws AmqpException {
     _log.info("发送MQ延时消息:msg={},delay={}", new Object[] { msg, Long.valueOf(delay) });
     String key = String.valueOf((queueName + msg).hashCode());
     if (this.redisUtil.hasKey(key).booleanValue()) {
       _log.warn("请勿重复发送消息:" + msg, new Object[0]);
       return;
     }
     this.redisUtil.set(key, "1", 30L, TimeUnit.SECONDS);
     this.mq4Base.send(queueName, msg, delay);
     _log.info("设置消息发送标志：" + key, new Object[0]);
   }

   public void forceSend(String queueName, String msg, long delay) throws AmqpException {
     _log.info("发送MQ延时消息:msg={},delay={}", new Object[] { msg, Long.valueOf(delay) });
     this.mq4Base.send(queueName, msg, delay);
   }

   private static class TrustAnyTrustManager implements X509TrustManager {
     private TrustAnyTrustManager() {}

     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

     public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

     public X509Certificate[] getAcceptedIssuers() {
       return new X509Certificate[0];
     }
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
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1048576);
         while (true) {
           String line = in.readLine();
           if (line == null) {
             break;
           }
           sb.append(line);
         }
         in.close();
       } else if ("http".equals(console.getProtocol())) {
         HttpURLConnection con = (HttpURLConnection)console.openConnection();
         con.setRequestMethod("POST");
         con.setDoInput(true);
         con.setDoOutput(true);
         con.setUseCaches(false);
         con.setConnectTimeout(30000);
         con.setReadTimeout(60000);
         con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1048576);
         while (true) {
           String line = in.readLine();
           if (line == null) {
             break;
           }
           sb.append(line);
         }
         in.close();
       } else {
         _log.error("not do protocol. protocol=%s", new Object[] { console.getProtocol() });
       }
     } catch (Exception e) {
       _log.error(e, "httpPost exception. url:%s", new Object[] { url });
     }
     return sb.toString();
   }

   public JSONObject toJSONObject(String params) {
     JSONObject json = new JSONObject();
     String[] paramsArr = params.split("&");
     for (int i = 0; i < paramsArr.length; i++) {
       String[] secondArr = paramsArr[i].split("=");
       if (secondArr.length == 2) {
         json.put(secondArr[0], (secondArr[1] == null) ? "" : secondArr[1]);
       } else {
         json.put(secondArr[0], "");
       }
     }
     json.put("body", "");
     json.put("channelMchId", "");
     json.put("notifyUrl", "");
     json.put("notifyCount", "");
     return json;
   }
 }

