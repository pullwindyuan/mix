 package org.hlpay.common.util;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Set;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.enumm.ResultEnum;









 public class HLPayUtil
 {
   private static final MyLog _log = MyLog.getLog(HLPayUtil.class);

   public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, String errCode, String errCodeDesc) {
     Map<String, Object> retMap = new HashMap<>();
     if (retCode != null) retMap.put("retCode", retCode);
     if (retMsg != null) retMap.put("retMsg", retMsg);
     if (resCode != null) retMap.put("resCode", resCode);
     if (errCode != null) retMap.put("errCode", errCode);
     if (errCodeDesc != null) retMap.put("errCodeDes", errCodeDesc);
     return retMap;
   }

   public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, PayEnum payEnum) {
     Map<String, Object> retMap = new HashMap<>();
     if (retCode != null) retMap.put("retCode", retCode);
     if (retMsg != null) retMap.put("retMsg", retMsg);
     if (resCode != null) retMap.put("resCode", resCode);
     if (payEnum != null) {
       retMap.put("errCode", payEnum.getCode());
       retMap.put("errCodeDes", payEnum.getMessage());
     }
     return retMap;
   }

   public static JSONObject makeRetJSONObject(String retCode, String retMsg, String resCode, PayEnum payEnum) {
     JSONObject retMap = new JSONObject();
     if (retCode != null) retMap.put("retCode", retCode);
     if (retMsg != null) retMap.put("retMsg", retMsg);
     if (resCode != null) retMap.put("resCode", resCode);
     if (payEnum != null) {
       retMap.put("errCode", payEnum.getCode());
       retMap.put("errCodeDes", payEnum.getMessage());
     }
     return retMap;
   }






   public static CommonResult<Result> makeRetData(Map retMap, String resKey) {
     if (retMap.get("retCode") != null) {
       return CommonResult.success(ResultUtil.createSuccessResult(retMap));
     }
     return CommonResult.error(ResultEnum.ERROR.getCode().intValue(), JSON.toJSONString(retMap));
   }



   public static CommonResult<Result> makeRetFail(Map retMap) {
     return CommonResult.error(ResultEnum.ERROR.getCode().intValue(), JSON.toJSONString(retMap));
   }


   public static CommonResult<JSONObject> makeRetFailResult(Map retMap) {
     return CommonResult.error(ResultEnum.ERROR.getCode().intValue(), JSON.toJSONString(retMap));
   }






   public static boolean verifyPaySign(Map<String, Object> params, String key) {
     String sign = (String)params.get("sign");
     _log.info("输入sign:" + sign, new Object[0]);
     params.remove("sign");
     String checkSign = PayDigestUtil.getSign(params, key);
     _log.info("输出sign:" + checkSign, new Object[0]);
     params.put("sign", sign);
     if (!checkSign.equalsIgnoreCase(sign)) {
       return false;
     }
     return true;
   }






   public static boolean verifyPaySign(Map<String, Object> params, String key, String... noSigns) {
     String sign = (String)params.get("sign");
     _log.info("输入sign:" + sign, new Object[0]);
     params.remove("sign");
     if (noSigns != null && noSigns.length > 0) {
       for (String noSign : noSigns) {
         params.remove(noSign);
       }
     }
     String checkSign = PayDigestUtil.getSign(params, key);
     _log.info("输出sign:" + checkSign, new Object[0]);
     params.put("sign", sign);
     if (!checkSign.equalsIgnoreCase(sign)) {
       return false;
     }
     return true;
   }

   public static String genUrlParams(Map<String, Object> paraMap) {
     if (paraMap == null || paraMap.isEmpty()) return "";
     StringBuffer urlParam = new StringBuffer();
     Set<String> keySet = paraMap.keySet();
     int i = 0;
     for (String key : keySet) {
       urlParam.append(key).append("=").append(paraMap.get(key));
       if (++i == keySet.size())
         break;  urlParam.append("&");
     }
     return urlParam.toString();
   }






   public static String call4Post(String url) {
     try {
       URL url1 = new URL(url);
       if ("https".equals(url1.getProtocol()))
         return HttpClient.callHttpsPost(url);
       if ("http".equals(url1.getProtocol())) {
         return HttpClient.callHttpPost(url);
       }
       return "";
     }
     catch (MalformedURLException e) {
       e.printStackTrace();

       return "";
     }
   }
 }





