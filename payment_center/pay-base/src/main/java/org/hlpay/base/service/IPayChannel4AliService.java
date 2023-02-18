package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.hlpay.base.model.PayChannel;
import org.hlpay.base.model.PayOrder;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.base.model.TransOrder;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;

public interface IPayChannel4AliService {
  CommonResult<JSONObject> doAliPayWapReq(PayOrder paramPayOrder, PayChannel paramPayChannel);
  
  CommonResult<JSONObject> doAliPayPcReq(PayOrder paramPayOrder, PayChannel paramPayChannel);
  
  CommonResult<JSONObject> doAliPayMobileReq(PayOrder paramPayOrder, PayChannel paramPayChannel);
  
  CommonResult<JSONObject> doAliPayQrReq(PayOrder paramPayOrder, PayChannel paramPayChannel);
  
  Map doAliTransReq(TransOrder paramTransOrder) throws NoSuchMethodException;
  
  Result getAliTransReq(TransOrder paramTransOrder) throws NoSuchMethodException;
  
  Map doAliRefundReq(RefundOrder paramRefundOrder) throws NoSuchMethodException;
  
  Result getAliRefundReq(RefundOrder paramRefundOrder) throws NoSuchMethodException;
  
  Result queryReq(PayOrder paramPayOrder) throws NoSuchMethodException;
  
  Result queryReq(String paramString1, String paramString2, String paramString3) throws Exception;
}

