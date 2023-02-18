package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.exception.WxPayException;
import java.io.FileNotFoundException;
import org.hlpay.base.model.PayChannel;
import org.hlpay.base.model.PayOrder;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.base.model.TransOrder;
import org.hlpay.common.entity.Result;

public interface IPayChannel4WxService {
  JSONObject doWxPayReq(String paramString, PayOrder paramPayOrder, PayChannel paramPayChannel);
  
  Result queryReq(PayOrder paramPayOrder);
  
  Result queryReq(String paramString1, String paramString2, String paramString3) throws FileNotFoundException, WxPayException, NoSuchMethodException;
  
  Result doWxTransReq(TransOrder paramTransOrder);
  
  Result getWxTransReq(TransOrder paramTransOrder);
  
  Result doWxRefundReq(RefundOrder paramRefundOrder);
  
  Result getWxRefundReq(RefundOrder paramRefundOrder);
}

