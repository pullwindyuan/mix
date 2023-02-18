package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.hlpay.base.model.RefundOrder;

public interface INotifyRefundService {
  Map doAliRefundNotify(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  Map doWxRefundNotify(JSONObject paramJSONObject);
  
  Map doUnionRefundNotify(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  Map sendBizRefundNotify(RefundOrder paramRefundOrder);
  
  String handleAliRefundNotify(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  String handleWxRefundNotify(String paramString);
  
  String handleUnionRefundNotify(JSONObject paramJSONObject, HttpServletResponse paramHttpServletResponse) throws NoSuchMethodException;
}

