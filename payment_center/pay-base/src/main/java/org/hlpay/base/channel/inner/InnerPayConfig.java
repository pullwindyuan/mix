package org.hlpay.base.channel.inner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;













@Component
@ConfigurationProperties(prefix = "config.inner")
public class InnerPayConfig
{
  private String app_id;
  private String rsa_private_key;
  private String notify_url;
  private String return_url;
  private String url = "https://openapi.alipay.com/gateway.do";


  public static String CHARSET = "UTF-8";

  public static String FORMAT = "json";

  public String inner_public_key;

  public static String SIGNTYPE = "RSA2";


  private Short isSandbox = Short.valueOf((short)0);






  public InnerPayConfig init(String configParam) {
    Assert.notNull(configParam, "init inner config error");
    JSONObject paramObj = JSON.parseObject(configParam);
    setApp_id(paramObj.getString("appid"));
    setRsa_private_key(paramObj.getString("private_key"));
    setInnerpay_public_key(paramObj.getString("innerpay_public_key"));
    setIsSandbox(Short.valueOf(paramObj.getShortValue("isSandbox")));
    if (getIsSandbox().shortValue() == 1) setUrl("https://openapi.alipaydev.com/gateway.do");
    return this;
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
    return this.inner_public_key;
  }

  public void setInnerpay_public_key(String bidou_public_key) {
    this.inner_public_key = bidou_public_key;
  }
}





