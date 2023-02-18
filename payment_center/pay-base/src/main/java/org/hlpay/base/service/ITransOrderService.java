package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.hlpay.base.model.TransOrder;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;

public interface ITransOrderService {
  Map select(JSONObject paramJSONObject);
  
  Map selectByMchIdAndTransOrderId(JSONObject paramJSONObject);
  
  Map selectByMchIdAndMchTransNo(JSONObject paramJSONObject);
  
  TransOrder selectByMchIdAndTransOrderId(String paramString1, String paramString2);
  
  TransOrder selectByMchIdAndMchTransNo(String paramString1, String paramString2);
  
  Map updateStatus4Ing(JSONObject paramJSONObject);
  
  Map updateStatus4Success(JSONObject paramJSONObject);
  
  Map updateStatus4Complete(JSONObject paramJSONObject);
  
  int updateStatus4Ing(String paramString);
  
  int updateStatus4Success(String paramString);
  
  int updateStatus4Complete(String paramString);
  
  Map sendTransNotify(JSONObject paramJSONObject);
  
  int create(JSONObject paramJSONObject) throws NoSuchMethodException;
  
  int create(TransOrder paramTransOrder);
  
  void sendTransNotify(String paramString1, String paramString2);
  
  CommonResult<Result> doAliTransReq(String paramString1, TransOrder paramTransOrder, String paramString2) throws NoSuchMethodException;
  
  CommonResult<Result> getAliTransReq(String paramString1, TransOrder paramTransOrder, String paramString2) throws NoSuchMethodException;
  
  CommonResult<Result> doWxTransReq(String paramString1, TransOrder paramTransOrder, String paramString2);
  
  CommonResult<Result> getWxTransReq(String paramString1, TransOrder paramTransOrder, String paramString2);
}
