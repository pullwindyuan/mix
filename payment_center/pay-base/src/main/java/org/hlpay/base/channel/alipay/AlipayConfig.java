 package org.hlpay.base.channel.alipay;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.alipay.api.AlipayClient;
 import com.alipay.api.DefaultAlipayClient;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;
 import org.springframework.util.Assert;














 @Component
 @ConfigurationProperties(prefix = "config.ali")
 public class AlipayConfig
 {
   private String app_id;
   private String rsa_private_key;
   private String notify_url;
   private String return_url;
   private String url = "https://openapi.alipay.com/gateway.do";


   public static String CHARSET = "UTF-8";

   public static String FORMAT = "json";

   public String alipay_public_key;

   public static String SIGNTYPE = "RSA2";

   public AlipayClient client;
   private Short isSandbox = Short.valueOf((short)0);
   private static Map<Integer, AlipayConfig> payConfigMap = new HashMap<>();





   public AlipayConfig init(String configParam) {
     Integer key = Integer.valueOf(configParam.hashCode());
     AlipayConfig alipayConfig = payConfigMap.get(key);
     if (alipayConfig == null) {
       Assert.notNull(configParam, "init alipay config error");
       alipayConfig = new AlipayConfig();
       JSONObject paramObj = JSON.parseObject(configParam);
       alipayConfig.setApp_id(paramObj.getString("appId"));
       alipayConfig.setRsa_private_key(paramObj.getString("private_key"));
       alipayConfig.setAlipay_public_key(paramObj.getString("alipay_public_key"));
       alipayConfig.setNotify_url(this.notify_url);
       alipayConfig.setIsSandbox(Short.valueOf(paramObj.getShortValue("isSandbox")));
       if (alipayConfig.getIsSandbox().shortValue() == 1) {
         alipayConfig.setUrl("https://openapi.alipaydev.com/gateway.do");
       } else {
         alipayConfig.setUrl("https://openapi.alipay.com/gateway.do");
       }
       DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getApp_id(), alipayConfig.getRsa_private_key(), FORMAT, CHARSET, alipayConfig.getAlipay_public_key(), SIGNTYPE);
       alipayConfig.client = (AlipayClient)defaultAlipayClient;
       payConfigMap.put(key, alipayConfig);
     }
     return alipayConfig;
   }

   public String getApp_id() {
     return this.app_id;
   }

   public void setApp_id(String app_id) {
     this.app_id = app_id;
   }

   public String getRsa_private_key() {
     return this.rsa_private_key;
   }

   public void setRsa_private_key(String rsa_private_key) {
     this.rsa_private_key = rsa_private_key;
   }

   public String getNotify_url() {
     return this.notify_url;
   }

   public void setNotify_url(String notify_url) {
     this.notify_url = notify_url;
   }

   public String getReturn_url() {
     return this.return_url;
   }

   public void setReturn_url(String return_url) {
     this.return_url = return_url;
   }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) {
     this.url = url;
   }

   public Short getIsSandbox() {
     return this.isSandbox;
   }

   public void setIsSandbox(Short isSandbox) {
     this.isSandbox = isSandbox;
   }

   public String getAlipay_public_key() {
     return this.alipay_public_key;
   }

   public void setAlipay_public_key(String alipay_public_key) {
     this.alipay_public_key = alipay_public_key;
   }
 }





