 package org.hlpay.base.channel.wechat;

 import org.hlpay.common.util.MyLog;
 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;







 @Component
 @ConfigurationProperties(prefix = "config.wx")
 public class WxPayProperties
 {
   private final MyLog _log = MyLog.getLog(WxPayProperties.class);

   private String certRootPath;

   private String notifyUrl;

   public String getCertRootPath() {
     this._log.info("微信证书路径：{}", new Object[] { this.certRootPath });

     return "";
   }

   public void setCertRootPath(String certRootPath) {
     this.certRootPath = certRootPath;
   }

   public String getNotifyUrl() {
     return this.notifyUrl;
   }

   public void setNotifyUrl(String notifyUrl) {
     this.notifyUrl = notifyUrl;
   }
 }





