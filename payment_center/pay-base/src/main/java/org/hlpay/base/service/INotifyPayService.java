package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface INotifyPayService {
  Map doAliPayNotify(JSONObject paramJSONObject) throws Exception;
  
  Map doWxPayNotify(JSONObject paramJSONObject);
  
  Map doUnionPayNotify(JSONObject paramJSONObject) throws Exception;
  
  Map sendBizPayNotify(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  String handleAliPayNotify(JSONObject paramJSONObject) throws Exception;
  
  String handleWxPayNotify(String paramString);
  
  String handleUnionPayNotify(JSONObject paramJSONObject, HttpServletResponse paramHttpServletResponse) throws Exception;
}
