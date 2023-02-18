 package org.hlpay.base.channel.wechat;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.github.binarywang.wxpay.config.WxPayConfig;
 import java.io.FileNotFoundException;
 import java.util.HashMap;
 import java.util.Map;






 public class WxPayUtil
 {
   private static Map<Integer, WxPayConfig> wxPayConfigMap = new HashMap<>(751);








   public static WxPayConfig getWxPayConfig(String configParam, String tradeType, String certRootPath, String notifyUrl) throws FileNotFoundException {
     String srcString = configParam + tradeType + notifyUrl;
     Integer key = Integer.valueOf(srcString.hashCode());
     WxPayConfig wxPayConfig = wxPayConfigMap.get(key);
     if (wxPayConfig == null) {
       wxPayConfig = new WxPayConfig();
       JSONObject paramObj = JSON.parseObject(configParam);
       wxPayConfig.setMchId(paramObj.getString("mchId"));
       wxPayConfig.setAppId(paramObj.getString("appId"));
       wxPayConfig.setKeyPath(paramObj.getString("certLocalPath"));

       wxPayConfig.setMchKey(paramObj.getString("key"));
       wxPayConfig.setNotifyUrl(notifyUrl);
       wxPayConfig.setTradeType(tradeType);
       wxPayConfigMap.put(key, wxPayConfig);
     }
     return wxPayConfig;
   }






   public static WxPayConfig getWxPayConfig(String configParam) {
     WxPayConfig wxPayConfig = new WxPayConfig();
     JSONObject paramObj = JSON.parseObject(configParam);
     wxPayConfig.setMchId(paramObj.getString("mchId"));
     wxPayConfig.setAppId(paramObj.getString("appId"));
     wxPayConfig.setMchKey(paramObj.getString("key"));
     return wxPayConfig;
   }
 }





