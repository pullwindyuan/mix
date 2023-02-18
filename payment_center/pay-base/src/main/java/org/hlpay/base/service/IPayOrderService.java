package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.hlpay.base.model.PayChannel;
import org.hlpay.base.model.PayOrder;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;

public interface IPayOrderService {
  PayOrder selectPayOrder(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  PayOrder selectPayOrderByMchIdAndPayOrderId(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  PayOrder selectPayOrderByMchIdAndMchOrderNo(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  int updateStatus4Ing(JSONObject paramJSONObject);
  
  int updateStatus4Closed(JSONObject paramJSONObject);
  
  int updateStatus4Settled(String paramString);
  
  int updateChannelId(JSONObject paramJSONObject);
  
  int updateStatus4Success(JSONObject paramJSONObject);
  
  int updateStatus4Complete(JSONObject paramJSONObject);
  
  int updateExtra(JSONObject paramJSONObject);
  
  int updateNotify(JSONObject paramJSONObject);
  
  List<PayOrder> selectPayOrderByMchOrderNo(String paramString);
  
  Result queryPayOrder(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  CommonResult<JSONObject> doWxPayReq(String paramString1, PayOrder paramPayOrder, PayChannel paramPayChannel, String paramString2);
  
  CommonResult<JSONObject> doAliPayReq(String paramString1, PayOrder paramPayOrder, PayChannel paramPayChannel, String paramString2);
  
  CommonResult<JSONObject> doUnionPayReq(String paramString1, PayOrder paramPayOrder, String paramString2);
}
