package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.hlpay.base.model.PayOrder;
import org.hlpay.common.entity.Result;

public interface IPayChannel4UnionService {
  String unionPay(JSONObject paramJSONObject);
  
  JSONObject doUnionPayReq(PayOrder paramPayOrder);
  
  Result queryReq(PayOrder paramPayOrder);
  
  Result queryReq(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String validate(Map<String, String> paramMap, String paramString);
  
  void fileTransfer();
}

