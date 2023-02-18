 package org.hlpay.base.service.impl;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
 import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
 import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
 import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
 import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
 import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
 import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
 import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
 import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
 import com.github.binarywang.wxpay.config.WxPayConfig;
 import com.github.binarywang.wxpay.exception.WxPayException;
 import com.github.binarywang.wxpay.service.WxPayService;
 import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
 import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
 import com.github.binarywang.wxpay.util.SignUtils;
 import java.io.FileNotFoundException;
 import java.net.URLEncoder;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.LinkedBlockingQueue;
 import javax.annotation.Resource;
 import org.hlpay.base.channel.wechat.WxPayProperties;
 import org.hlpay.base.channel.wechat.WxPayUtil;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.RefundOrder;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.BaseService4RefundOrder;
 import org.hlpay.base.service.IPayChannel4WxService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class PayChannel4WxServiceImpl
   implements IPayChannel4WxService
 {
   private final MyLog _log = MyLog.getLog(PayChannel4WxServiceImpl.class);

   @Resource
   private WxPayProperties wxPayProperties;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Autowired
   private BaseService4RefundOrder baseService4RefundOrder;

   private static LinkedBlockingQueue<WxPayUnifiedOrderRequest> requestQueue = new LinkedBlockingQueue<>(1000);

   private static LinkedBlockingQueue<WxPayService> wxPayServiceQueue = new LinkedBlockingQueue<>(1000);

   public JSONObject doWxPayReq(String tradeType, PayOrder payOrder, PayChannel payChannel) {
     String logPrefix = "【微信支付统一下单】";
     this._log.info("开始下单", new Object[0]);
     JSONObject map = new JSONObject();
     try {
       String payOrderId = payOrder.getPayOrderId();

       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannel.getParam(), tradeType, this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());




       String redirectUrl = payOrder.getRedirectUrl();



       this._log.info("请求微信支付服务器", new Object[0]); try {
         JSONObject jSONObject; Map<String, String> payInfo; String timestamp, nonceStr, appId; Map<String, String> configMap; String partnerId, packageValue;
         WxPayUnifiedOrderResult wxPayUnifiedOrderResult = getWxPayUnifiedOrderRequest(payOrder, payChannel, wxPayConfig);

         this._log.info("{} >>> 下单成功,redirectUrl:{}", new Object[] { logPrefix, redirectUrl });

         map.put("isSuccess", Boolean.valueOf(true));
         map.put("payOrderId", payOrderId);
         map.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());


         switch (tradeType) {
           case "NATIVE":
             map.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL());
             break;

           case "APP":
             jSONObject = new JSONObject();
             timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
             nonceStr = String.valueOf(System.currentTimeMillis());

             appId = wxPayConfig.getAppId();
             configMap = new HashMap<>();

             partnerId = wxPayConfig.getMchId();
             configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
             configMap.put("partnerid", partnerId);
             packageValue = "Sign=WXPay";
             configMap.put("package", packageValue);
             configMap.put("timestamp", timestamp);
             configMap.put("noncestr", nonceStr);
             configMap.put("appid", appId);

             jSONObject.put("sign", SignUtils.createSign(configMap, wxPayConfig.getMchKey()));
             jSONObject.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
             jSONObject.put("partnerid", partnerId);
             jSONObject.put("appid", appId);
             jSONObject.put("package", packageValue);
             jSONObject.put("timestamp", timestamp);
             jSONObject.put("noncestr", nonceStr);
             map.put("payParams", jSONObject);
             break;

           case "JSAPI":
             payInfo = new HashMap<>();
             timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
             nonceStr = String.valueOf(System.currentTimeMillis());
             payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());

             payInfo.put("timeStamp", timestamp);
             payInfo.put("nonceStr", nonceStr);
             payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
             payInfo.put("signType", "MD5");
             payInfo.put("paySign", SignUtils.createSign(payInfo, wxPayConfig.getMchKey()));
             map.put("payParams", payInfo);
             break;

           case "MWEB":
             map.put("payUrl", wxPayUnifiedOrderResult.getMwebUrl() + "&redirect_url=" + URLEncoder.encode(redirectUrl, "UTF-8"));
             break;
         }

         map.put("retCode", "SUCCESS");
         return map;
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "下单失败");

         this._log.info("{}下单返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });
         map.put("isSuccess", Boolean.valueOf(false));
         map.put("retCode", e.getErrCode());
         map.put("errCodeDes", e.getErrCodeDes());
         return map;
       }
     } catch (Exception e) {
       this._log.error(e, "微信支付统一下单异常");
       map.put("isSuccess", Boolean.valueOf(false));
       map.put("retCode", "FAIL");
       map.put("errCodeDes", "微信支付统一下单异常");
       return map;
     }
   }


   public Result queryReq(String payOrderId, String mchId, String channelId) throws FileNotFoundException, WxPayException, NoSuchMethodException {
     String logPrefix = "【微信交易订单查询】";
     Map<String, Object> map = new HashMap<>();
     try {
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       wxPayServiceImpl.setConfig(wxPayConfig);


       try {
         WxPayOrderQueryResult result = wxPayServiceImpl.queryOrder(null, payOrderId);
         this._log.info("{} >>> 成功", new Object[] { logPrefix });
         JSONObject payResult = new JSONObject();
         try {
           PayOrder payOrder = new PayOrder();
           payOrder.setAmount(Long.valueOf(result.getCashFee().intValue()));
           payOrder.setChannelOrderNo(result.getTransactionId());
           payOrder.setPayOrderId(payOrderId);
           payOrder.setMchId(mchId);
           payOrder.setChannelId(channelId);
           if ("SUCCESS".equals(result.getTradeState())) {
             payOrder.setStatus(Byte.valueOf((byte)2));
             payOrder.setPaySuccTime(Long.valueOf(DateUtils.getDate(result.getTimeEnd(), "yyyyMMddHHmmss").getTime()));
             return Result.createSuccessMap(payOrder);
           }
           payOrder.setStatus(Byte.valueOf((byte)-1));
           return Result.createSuccessMap(payOrder);
         }
         catch (Exception e) {
           e.printStackTrace();
           throw e;
         }
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "失败");

         this._log.info("{}返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });
         throw e;
       }
     } catch (Exception e) {
       this._log.error(e, "微信订单查询异常");
       throw e;
     }
   }


   public Result queryReq(PayOrder payOrder) {
     String logPrefix = "【微信交易订单查询】";
     Map<String, Object> map = new HashMap<>();
     try {
       String mchId = payOrder.getMchId();
       String channelId = payOrder.getChannelId();
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       wxPayServiceImpl.setConfig(wxPayConfig);
       String payOrderId = payOrder.getPayOrderId();


       try {
         WxPayOrderQueryResult result = wxPayServiceImpl.queryOrder(null, payOrderId);
         this._log.info("{} >>> 成功", new Object[] { logPrefix });
         if ("SUCCESS".equals(result.getTradeState())) {
           payOrder.setStatus(Byte.valueOf((byte)2));
           String timeEnd = result.getTimeEnd();
           Date end = DateUtils.str2Date(timeEnd, "yyyyMMddHHmmss");
           payOrder.setPaySuccTime(Long.valueOf(end.getTime()));
         } else {
           payOrder.setStatus(Byte.valueOf((byte)1));
         }

         return Result.createSuccessMap(payOrder);
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "失败");

         this._log.info("{}返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });
         return Result.createFailMap(e.getErrCodeDes(), e.getErrCode());
       }
     } catch (Exception e) {
       this._log.error(e, "微信订单查询异常");
       return Result.createFailMap("微信交易订单查询失败", null);
     }
   }


   public Result doWxTransReq(TransOrder transOrder) {
     String logPrefix = "【微信企业付款】";
     Map<String, Object> map = new HashMap<>();
     try {
       String mchId = transOrder.getMchId();
       String channelId = transOrder.getChannelId();
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       EntPayServiceImpl entPayServiceImpl = new EntPayServiceImpl((WxPayService)wxPayServiceImpl);
       wxPayServiceImpl.setConfig(wxPayConfig);
       EntPayRequest wxEntPayRequest = buildWxEntPayRequest(transOrder, wxPayConfig);
       String transOrderId = transOrder.getTransOrderId();


       try {
         EntPayResult result = entPayServiceImpl.entPay(wxEntPayRequest);
         this._log.info("{} >>> 转账成功", new Object[] { logPrefix });
         return Result.createSuccessResult(transOrderId);
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "转账失败");

         this._log.info("{}转账返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });
         return Result.createFailResult(e.getErrCodeDes(), e.getErrCode());
       }

     } catch (Exception e) {
       this._log.error(e, "微信转账异常");
       return Result.createFailResult("微信转账异常", "FAIL");
     }
   }


   public Result getWxTransReq(TransOrder transOrder) {
     String logPrefix = "【微信企业付款查询】";
     Map<String, Object> map = new HashMap<>();
     try {
       String mchId = transOrder.getMchId();
       String channelId = transOrder.getChannelId();
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       EntPayServiceImpl entPayServiceImpl = new EntPayServiceImpl((WxPayService)wxPayServiceImpl);
       wxPayServiceImpl.setConfig(wxPayConfig);
       String transOrderId = transOrder.getTransOrderId();


       try {
         EntPayQueryResult result = entPayServiceImpl.queryEntPay(transOrderId);
         this._log.info("{} >>> 成功", new Object[] { JSONObject.toJSONString(result) });
         return Result.createSuccessResult(transOrderId);
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "失败");

         return Result.createFailResult(e.getErrCodeDes(), e.getErrCode());
       }
     } catch (Exception e) {
       this._log.error(e, "微信企业付款查询异常");
       map.put("isSuccess", Boolean.valueOf(false));
       map.put("errCode", "FAIL");
       map.put("errCodeDes", "微信企业付款查询异常");
       return Result.createFailResult("微信企业付款查询异常", "FAIL");
     }
   }


   public Result doWxRefundReq(RefundOrder refundOrder) {
     String logPrefix = "【微信退款】";
     try {
       String mchId = refundOrder.getMchId();
       String channelId = refundOrder.getChannelId();
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);

       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), null);
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       wxPayServiceImpl.setConfig(wxPayConfig);
       WxPayRefundRequest wxPayRefundRequest = buildWxPayRefundRequest(refundOrder, wxPayConfig);
       String refundOrderId = refundOrder.getRefundOrderId();
       String mchOrderNo = refundOrder.getMchOrderNo();

       try {
         WxPayRefundResult result = wxPayServiceImpl.refund(wxPayRefundRequest);
         this._log.info("{} >>> 下单成功", new Object[] { logPrefix });

         this.baseService4RefundOrder.baseUpdateStatus4Ing(refundOrderId, result.getRefundId());



         this.baseService4RefundOrder.baseUpdateStatus4Success(refundOrderId, new Date());
         return Result.createSuccessResult(refundOrderId);
       } catch (WxPayException e) {
         this._log.error((Throwable)e, "下单失败");

         this._log.info("{}下单返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });

         this.baseService4RefundOrder.baseUpdateStatus4Fail(refundOrderId, e.getErrCode(), e.getErrCodeDes());
         return Result.createFailMap(e.getErrCodeDes(), e.getErrCode());
       }
     } catch (Exception e) {
       this._log.error(e, "微信退款异常");
       return Result.createFailResult("微信退款异常", Integer.valueOf(-1));
     }
   }


   public Result getWxRefundReq(RefundOrder refundOrder) {
     String logPrefix = "【微信退款查询】";
     Map<String, Object> map = new HashMap<>();
     try {
       String mchId = refundOrder.getMchId();
       String channelId = refundOrder.getChannelId();
       PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
       WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam(), "", this.wxPayProperties.getCertRootPath(), this.wxPayProperties.getNotifyUrl());
       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       wxPayServiceImpl.setConfig(wxPayConfig);
       String refundOrderId = refundOrder.getRefundOrderId();


       try {
         WxPayRefundQueryResult result = wxPayServiceImpl.refundQuery(refundOrder.getChannelPayOrderNo(), refundOrder.getPayOrderId(), refundOrder.getRefundOrderId(), refundOrder.getChannelOrderNo());
         this._log.info("{} >>> 成功", new Object[] { logPrefix });



         return Result.createSuccessMap(result.getResultCode());

       }
       catch (WxPayException e) {
         this._log.error((Throwable)e, "失败");

         this._log.info("{}返回失败", new Object[] { logPrefix });
         this._log.info("err_code:{}", new Object[] { e.getErrCode() });
         this._log.info("err_code_des:{}", new Object[] { e.getErrCodeDes() });
         return Result.createFailMap("微信退款查询失败", null);
       }
     } catch (Exception e) {
       this._log.error(e, "微信退款查询异常");
       return Result.createFailMap("微信退款查询失败", null);
     }
   }

   WxPayUnifiedOrderResult getWxPayUnifiedOrderRequest(PayOrder payOrder, PayChannel payChannel, WxPayConfig wxPayConfig) throws WxPayException, FileNotFoundException {
     WxPayService wxPayService = wxPayServiceQueue.poll();
     if (wxPayService == null) {

       WxPayService temp = null;
       for (int i = 0; i < 1000; i++) {
         try {
           WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
           wxPayServiceImpl.setConfig(wxPayConfig);
           wxPayServiceQueue.put(wxPayServiceImpl);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       wxPayService = wxPayServiceQueue.poll();
     }
     wxPayServiceQueue.offer(wxPayService);
     WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(payOrder, payChannel, wxPayConfig);
     wxPayUnifiedOrderRequest.setProductId(payOrder.getSubject());
     return wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
   }






   WxPayUnifiedOrderRequest buildUnifiedOrderRequest(PayOrder payOrder, PayChannel payChannel, WxPayConfig wxPayConfig) {
     WxPayUnifiedOrderRequest request = requestQueue.poll();
     if (request == null) {


       for (int i = 0; i < 1000; i++) {
         try {
           WxPayUnifiedOrderRequest temp = new WxPayUnifiedOrderRequest();


           temp.setDetail(null);
           temp.setFeeType("CNY");
           temp.setTimeStart(null);
           temp.setGoodsTag(null);
           temp.setLimitPay(null);
           requestQueue.put(temp);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       request = requestQueue.poll();
     }

     request.setDeviceInfo(payOrder.getDevice());
     request.setBody(payOrder.getBody());
     request.setAttach(UnionIdUtil.getUnionId(new String[] { payOrder.getMchId(), payOrder.getMchOrderNo() }));
     request.setOutTradeNo(payOrder.getPayOrderId());
     request.setTotalFee(Integer.valueOf(payOrder.getAmount().intValue()));
     request.setSpbillCreateIp(payOrder.getClientIp());
     request.setNotifyUrl(wxPayConfig.getNotifyUrl());
     request.setTradeType(wxPayConfig.getTradeType());
     request.setOpenid(payChannel.getOpenId());
     request.setProductId(payChannel.getProductId());
     request.setProductId(payChannel.getSceneInfo());
     String expire = DateUtils.date2Str(DateUtils.getDate(payOrder.getExpireTime()), "yyyyMMddHHmmss");
     request.setTimeExpire(expire);
     requestQueue.offer(request);
     return request;
   }








   EntPayRequest buildWxEntPayRequest(TransOrder transOrder, WxPayConfig wxPayConfig) {
     EntPayRequest request = new EntPayRequest();
     request.setAmount(Integer.valueOf(transOrder.getAmount().intValue()));
     String checkName = "NO_CHECK";
     if (transOrder.getExtra() != null) checkName = JSON.parseObject(transOrder.getExtra()).getString("checkName");
     request.setCheckName(checkName);
     request.setDescription(transOrder.getRemarkInfo());
     request.setReUserName(transOrder.getUserName());
     request.setPartnerTradeNo(transOrder.getTransOrderId());
     request.setDeviceInfo(transOrder.getDevice());
     request.setSpbillCreateIp(transOrder.getClientIp());
     request.setOpenid(transOrder.getChannelUser());
     return request;
   }








   WxPayRefundRequest buildWxPayRefundRequest(RefundOrder refundOrder, WxPayConfig wxPayConfig) {
     WxPayRefundRequest request = new WxPayRefundRequest();
     request.setTransactionId(refundOrder.getChannelPayOrderNo());
     request.setOutTradeNo(refundOrder.getPayOrderId());
     request.setDeviceInfo(refundOrder.getDevice());


     request.setOutRefundNo(refundOrder.getMchOrderNo());
     request.setRefundDesc(refundOrder.getRemarkInfo());
     request.setRefundFee(Integer.valueOf(refundOrder.getRefundAmount().intValue()));
     request.setRefundFeeType("CNY");
     request.setTotalFee(Integer.valueOf(refundOrder.getPayAmount().intValue()));
     return request;
   }
 }





