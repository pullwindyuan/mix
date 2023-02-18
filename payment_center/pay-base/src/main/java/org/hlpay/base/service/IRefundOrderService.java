package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;

public interface IRefundOrderService {
  Map select(JSONObject paramJSONObject);
  
  RefundOrder selectByMchIdAndRefundOrderId(JSONObject paramJSONObject);
  
  RefundOrder selectByMchIdAndMchRefundNo(JSONObject paramJSONObject);
  
  Map updateStatus4Ing(JSONObject paramJSONObject);
  
  Map updateStatus4Success(JSONObject paramJSONObject);
  
  Map updateStatus4Complete(JSONObject paramJSONObject);
  
  int create(RefundOrder paramRefundOrder);
  
  void sendRefundNotify(String paramString1, String paramString2);
  
  Result query(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  Map sendRefundNotify(JSONObject paramJSONObject);
  
  CommonResult<Result> doAliRefundReq(String paramString1, RefundOrder paramRefundOrder, String paramString2) throws NoSuchMethodException;
  
  CommonResult<Result> getAliRefundReq(String paramString1, RefundOrder paramRefundOrder, String paramString2) throws NoSuchMethodException;
  
  CommonResult<Result> doWxRefundReq(String paramString1, RefundOrder paramRefundOrder, String paramString2);
  
  CommonResult<Result> getWxRefundReq(String paramString1, RefundOrder paramRefundOrder, String paramString2);
}

