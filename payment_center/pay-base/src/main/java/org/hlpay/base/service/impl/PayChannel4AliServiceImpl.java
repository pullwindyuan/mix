 package org.hlpay.base.service.impl;
 import com.alibaba.fastjson.JSONObject;
 import com.alipay.api.AlipayApiException;
 import com.alipay.api.AlipayObject;
 import com.alipay.api.AlipayRequest;
 import com.alipay.api.DefaultAlipayClient;
 import com.alipay.api.domain.*;
 import com.alipay.api.request.*;
 import com.alipay.api.response.*;

 import java.math.BigDecimal;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.LinkedBlockingQueue;
 import org.hlpay.base.channel.alipay.AlipayConfig;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.RefundOrder;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.BaseService4RefundOrder;
 import org.hlpay.base.service.IPayChannel4AliService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.util.*;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class PayChannel4AliServiceImpl implements IPayChannel4AliService {
   private static final MyLog _log = MyLog.getLog(PayChannel4AliServiceImpl.class);

   @Autowired
   private RedisUtil redisUtil;

   @Autowired
   private AlipayConfig alipayConfig;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Autowired
   private BaseService4RefundOrder baseService4RefundOrder;
   private static LinkedBlockingQueue<AlipayTradeWapPayRequest> wapRequestQueue = new LinkedBlockingQueue<>(1000);

   private static LinkedBlockingQueue<AlipayTradePagePayRequest> pageRequestQueue = new LinkedBlockingQueue<>(1000);

   private static LinkedBlockingQueue<AlipayTradeAppPayRequest> appRequestQueue = new LinkedBlockingQueue<>(1000);

   private static LinkedBlockingQueue<AlipayTradePrecreateRequest> preCreateRequestQueue = new LinkedBlockingQueue<>(1000);



   private AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(PayOrder payOrder, AlipayConfig alipayConfig, String payOrderId) {
     AlipayTradeWapPayRequest alipay_request = wapRequestQueue.poll();
     if (alipay_request == null) {


       for (int i = 0; i < 1000; i++) {
         AlipayTradeWapPayRequest tempRequest = new AlipayTradeWapPayRequest();

         tempRequest.setNotifyUrl(alipayConfig.getNotify_url());
         AlipayTradeWapPayModel tempModel = new AlipayTradeWapPayModel();
         tempModel.setProductCode("QUICK_WAP_PAY");
         tempRequest.setBizModel((AlipayObject)tempModel);
         try {
           wapRequestQueue.put(tempRequest);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       alipay_request = wapRequestQueue.poll();
     }

     AlipayTradeWapPayModel model = (AlipayTradeWapPayModel)alipay_request.getBizModel();
     model.setMerchantOrderNo(payOrderId);
     model.setOutTradeNo(payOrderId);
     model.setSubject(payOrder.getSubject());
     model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
     model.setBody(payOrder.getBody());
     String expire = DateUtils.date2Str(DateUtils.getDate(payOrder.getExpireTime()));
     model.setTimeExpire(expire);




     String defaultRedirectUrl = alipayConfig.getReturn_url();
     String redirectUrl = payOrder.getRedirectUrl();
     if (redirectUrl != null &&
       redirectUrl.indexOf("http") >= 0) {
       defaultRedirectUrl = redirectUrl;
     }

     alipay_request.setReturnUrl(defaultRedirectUrl);
     wapRequestQueue.offer(alipay_request);

     return alipay_request;
   }

   public CommonResult<JSONObject> doAliPayWapReq(PayOrder payOrder, PayChannel payChannel) {
     String payUrl, payOrderId = payOrder.getPayOrderId();
     this.alipayConfig = this.alipayConfig.init(payChannel.getParam());
     AlipayTradeWapPayRequest alipay_request = getAlipayTradeWapPayRequest(payOrder, this.alipayConfig, payOrderId);

     try {
       payUrl = ((AlipayTradeWapPayResponse)this.alipayConfig.client.pageExecute((AlipayRequest)alipay_request, "get")).getBody();
     } catch (AlipayApiException e) {
       e.printStackTrace();
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用支付宝支付失败"));
     }
     this.baseService4PayOrderForCache.baseUpdateStatus4Ing(payOrderId, null);
     _log.info("###### 商户统一下单处理完成 ######", new Object[0]);
     JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
     map.put("payOrderId", payOrderId);
     map.put("payUrl", payUrl);
     return CommonResult.success(map);
   }




   private AlipayTradePagePayRequest getAlipayTradePagePayRequest(PayOrder payOrder, AlipayConfig alipayConfig, String payOrderId) {
     AlipayTradePagePayRequest alipay_request = pageRequestQueue.poll();
     if (alipay_request == null) {


       for (int i = 0; i < 1000; i++) {
         AlipayTradePagePayRequest tempRequest = new AlipayTradePagePayRequest();

         tempRequest.setNotifyUrl(alipayConfig.getNotify_url());
         AlipayTradePagePayModel tempModel = new AlipayTradePagePayModel();
         tempModel.setProductCode("FAST_INSTANT_TRADE_PAY");
         tempModel.setQrPayMode("2");
         tempModel.setQrcodeWidth(Long.valueOf(Long.parseLong("200")));
         tempRequest.setBizModel((AlipayObject)tempModel);
         try {
           pageRequestQueue.put(tempRequest);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       alipay_request = pageRequestQueue.poll();
     }

     AlipayTradePagePayModel model = (AlipayTradePagePayModel)alipay_request.getBizModel();
     model.setMerchantOrderNo(payOrderId);
     model.setOutTradeNo(payOrderId);
     model.setSubject(payOrder.getSubject());
     model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
     model.setBody(payOrder.getBody());
     String expire = DateUtils.date2Str(DateUtils.getDate(payOrder.getExpireTime()));
     model.setTimeExpire(expire);




     String defaultRedirectUrl = alipayConfig.getReturn_url();
     String redirectUrl = payOrder.getRedirectUrl();
     if (redirectUrl != null &&
       redirectUrl.indexOf("http") >= 0) {
       defaultRedirectUrl = redirectUrl;
     }

     alipay_request.setReturnUrl(defaultRedirectUrl);
     pageRequestQueue.offer(alipay_request);

     return alipay_request;
   }

   public CommonResult<JSONObject> doAliPayPcReq(PayOrder payOrder, PayChannel payChannel) {
     String logPrefix = "【支付宝PC支付下单】";
     String payOrderId = payOrder.getPayOrderId();
     this.alipayConfig = this.alipayConfig.init(payChannel.getParam());
     AlipayTradePagePayRequest alipay_request = getAlipayTradePagePayRequest(payOrder, this.alipayConfig, payOrderId);

     String payUrl = null;
     try {
       payUrl = ((AlipayTradePagePayResponse)this.alipayConfig.client.pageExecute((AlipayRequest)alipay_request)).getBody();
     } catch (AlipayApiException e) {
       e.printStackTrace();
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用支付宝支付失败"));
     }

     _log.info("###### 商户统一下单处理完成 ######", new Object[0]);
     JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
     map.put("payOrderId", payOrderId);
     map.put("payUrl", payUrl);
     return CommonResult.success(map);
   }




   private AlipayTradeAppPayRequest getAlipayTradeAppPayRequest(PayOrder payOrder, AlipayConfig alipayConfig, String payOrderId) {
     AlipayTradeAppPayRequest alipay_request = appRequestQueue.poll();
     if (alipay_request == null) {


       for (int i = 0; i < 1000; i++) {
         AlipayTradeAppPayRequest tempRequest = new AlipayTradeAppPayRequest();

         tempRequest.setNotifyUrl(alipayConfig.getNotify_url());
         AlipayTradeAppPayModel tempModel = new AlipayTradeAppPayModel();
         tempModel.setProductCode("QUICK_MSECURITY_PAY");
         tempRequest.setBizModel((AlipayObject)tempModel);
         try {
           appRequestQueue.put(tempRequest);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       alipay_request = appRequestQueue.poll();
     }

     AlipayTradeAppPayModel model = (AlipayTradeAppPayModel)alipay_request.getBizModel();
     model.setMerchantOrderNo(payOrderId);
     model.setOutTradeNo(payOrderId);
     model.setSubject(payOrder.getSubject());
     model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
     model.setBody(payOrder.getBody());
     String expire = DateUtils.date2Str(DateUtils.getDate(payOrder.getExpireTime()));
     model.setTimeExpire(expire);




     String defaultRedirectUrl = alipayConfig.getReturn_url();
     String redirectUrl = payOrder.getRedirectUrl();
     if (redirectUrl != null &&
       redirectUrl.indexOf("http") >= 0) {
       defaultRedirectUrl = redirectUrl;
     }

     alipay_request.setReturnUrl(defaultRedirectUrl);
     appRequestQueue.offer(alipay_request);

     return alipay_request;
   }

   public CommonResult<JSONObject> doAliPayMobileReq(PayOrder payOrder, PayChannel payChannel) {
     String payParams, payOrderId = payOrder.getPayOrderId();
     this.alipayConfig = this.alipayConfig.init(payChannel.getParam());
     AlipayTradeAppPayRequest alipay_request = getAlipayTradeAppPayRequest(payOrder, this.alipayConfig, payOrderId);

     try {
       payParams = ((AlipayTradeAppPayResponse)this.alipayConfig.client.sdkExecute((AlipayRequest)alipay_request)).getBody();
     } catch (AlipayApiException e) {
       e.printStackTrace();
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用支付宝支付失败"));
     }

     _log.info("###### 商户统一下单处理完成 ######", new Object[0]);
     JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
     map.put("payOrderId", payOrderId);
     map.put("payParams", payParams);
     return CommonResult.success(map);
   }



   private AlipayTradePrecreateRequest getAlipayTradePrecreateRequest(PayOrder payOrder, AlipayConfig alipayConfig, String payOrderId) {
     AlipayTradePrecreateRequest alipay_request = preCreateRequestQueue.poll();
     if (alipay_request == null) {


       for (int i = 0; i < 1000; i++) {
         AlipayTradePrecreateRequest tempRequest = new AlipayTradePrecreateRequest();

         tempRequest.setNotifyUrl(alipayConfig.getNotify_url());
         AlipayTradePrecreateModel tempModel = new AlipayTradePrecreateModel();
         tempRequest.setBizModel((AlipayObject)tempModel);
         try {
           preCreateRequestQueue.put(tempRequest);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
       alipay_request = preCreateRequestQueue.poll();
     }

     AlipayTradePrecreateModel model = (AlipayTradePrecreateModel)alipay_request.getBizModel();
     model.setMerchantOrderNo(payOrderId);
     model.setOutTradeNo(payOrderId);
     model.setSubject(payOrder.getSubject());
     model.setTotalAmount(AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));
     model.setBody(payOrder.getBody());
     String expire = String.valueOf((payOrder.getExpireTime().longValue() - System.currentTimeMillis()) / 60000L);
     model.setTimeoutExpress(expire + "m");




     String defaultRedirectUrl = alipayConfig.getReturn_url();
     String redirectUrl = payOrder.getRedirectUrl();
     if (redirectUrl != null &&
       redirectUrl.indexOf("http") >= 0) {
       defaultRedirectUrl = redirectUrl;
     }

     alipay_request.setReturnUrl(defaultRedirectUrl);
     preCreateRequestQueue.offer(alipay_request);

     return alipay_request;
   }

   public CommonResult<JSONObject> doAliPayQrReq(PayOrder payOrder, PayChannel payChannel) {
     String payUrl, logPrefix = "【支付宝当面付之扫码支付下单】";
     String payOrderId = payOrder.getPayOrderId();
     this.alipayConfig = this.alipayConfig.init(payChannel.getParam());
     AlipayTradePrecreateRequest alipay_request = getAlipayTradePrecreateRequest(payOrder, this.alipayConfig, payOrderId);

     try {
       payUrl = ((AlipayTradePrecreateResponse)this.alipayConfig.client.execute((AlipayRequest)alipay_request)).getBody();
     } catch (AlipayApiException e) {
       e.printStackTrace();
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用支付宝支付失败"));
     }

     _log.info("###### 商户统一下单处理完成 ######", new Object[0]);
     JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
     map.put("payOrderId", payOrderId);
     map.put("payUrl", payUrl);
     return CommonResult.success(map);
   }








   public Map doAliTransReq(TransOrder transOrder) throws NoSuchMethodException {
     String logPrefix = "【支付宝转账】";
     String transOrderId = transOrder.getTransOrderId();
     String mchId = transOrder.getMchId();
     String channelId = transOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
     AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
     model.setOutBizNo(transOrderId);
     model.setPayeeType("ALIPAY_LOGONID");
     model.setPayeeAccount(transOrder.getChannelUser());
     model.setAmount(AmountUtil.convertCent2Dollar(transOrder.getAmount().toString()));
     model.setPayerShowName("支付转账");
     model.setPayeeRealName(transOrder.getUserName());
     model.setRemark(transOrder.getRemarkInfo());
     request.setBizModel((AlipayObject)model);
     Map<String, Object> map = new HashMap<>();
     map.put("transOrderId", transOrderId);
     map.put("isSuccess", Boolean.valueOf(false));
     try {
       AlipayFundTransToaccountTransferResponse response = (AlipayFundTransToaccountTransferResponse)defaultAlipayClient.execute((AlipayRequest)request);
       if (response.isSuccess()) {
         map.put("isSuccess", Boolean.valueOf(true));
         map.put("channelOrderNo", response.getOrderId());
       } else {

         _log.info("{}返回失败", new Object[] { logPrefix });
         _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });
         map.put("channelErrCode", response.getSubCode());
         map.put("channelErrMsg", response.getSubMsg());
       }
     } catch (AlipayApiException e) {
       _log.error((Throwable)e, "");
     }
     return map;
   }


   public Result getAliTransReq(TransOrder transOrder) throws NoSuchMethodException {
     String logPrefix = "【支付宝转账查询】";
     String transOrderId = transOrder.getTransOrderId();
     String mchId = transOrder.getMchId();
     String channelId = transOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
     AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
     model.setOutBizNo(transOrderId);
     model.setOrderId(transOrder.getChannelOrderNo());
     request.setBizModel((AlipayObject)model);
     Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
     map.put("transOrderId", transOrderId);
     try {
       AlipayFundTransOrderQueryResponse response = (AlipayFundTransOrderQueryResponse)defaultAlipayClient.execute((AlipayRequest)request);
       if (response.isSuccess()) {
         return Result.createSuccessResult(response);
       }
       _log.info("{}返回失败", new Object[] { logPrefix });
       _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });
       return Result.createFailResult(response.getSubMsg(), response.getSubCode());
     }
     catch (AlipayApiException e) {
       return Result.createFailResult(e.getErrMsg(), e.getErrCode());
     }
   }


   public Map doAliRefundReq(RefundOrder refundOrder) throws NoSuchMethodException {
     String logPrefix = "【支付宝退款】";
     String refundOrderId = refundOrder.getRefundOrderId();
     String payOrderId = refundOrder.getMchOrderNo();
     String mchId = refundOrder.getMchId();

     String channelId = refundOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
     AlipayTradeRefundModel model = new AlipayTradeRefundModel();
     model.setOutTradeNo(refundOrder.getPayOrderId());
     model.setTradeNo(refundOrder.getChannelPayOrderNo());
     model.setOutRequestNo(payOrderId);
     model.setRefundAmount(AmountUtil.convertCent2Dollar(refundOrder.getRefundAmount().toString()));
     model.setRefundReason("正常退款");
     request.setBizModel((AlipayObject)model);
     Map<String, Object> map = new HashMap<>();
     map.put("refundOrderId", refundOrderId);
     map.put("isSuccess", Boolean.valueOf(false));
     try {
       AlipayTradeRefundResponse response = (AlipayTradeRefundResponse)defaultAlipayClient.execute((AlipayRequest)request);
       if (response.isSuccess()) {
         map.put("isSuccess", Boolean.valueOf(true));
         String tradeNo = response.getTradeNo();
         map.put("channelOrderNo", tradeNo);

         _log.info("支付宝退款响应：{}", new Object[] { JsonUtil.getJSONObjectFromObj(response).toJSONString() });


         _log.info("refundOrderId:{},TradeNo:{}", new Object[] { refundOrderId, tradeNo });

         this.baseService4RefundOrder.baseUpdateStatus4Ing(refundOrderId, tradeNo);


         this.baseService4RefundOrder.baseUpdateStatus4Success(refundOrderId, tradeNo, response.getGmtRefundPay());
       } else {
         _log.info("{}返回失败", new Object[] { logPrefix });
         _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });
         map.put("channelErrCode", response.getSubCode());
         map.put("channelErrMsg", response.getSubMsg());

         this.baseService4RefundOrder.baseUpdateStatus4Fail(refundOrderId, response.getSubCode(), response.getSubMsg());
       }
     } catch (AlipayApiException e) {
       _log.error((Throwable)e, "");
     }
     return map;
   }


   public Result getAliRefundReq(RefundOrder refundOrder) throws NoSuchMethodException {
     String logPrefix = "【支付宝退款查询】";
     String refundOrderId = refundOrder.getRefundOrderId();
     String mchId = refundOrder.getMchId();
     String channelId = refundOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
     AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
     model.setOutTradeNo(refundOrder.getPayOrderId());
     model.setTradeNo(refundOrder.getChannelPayOrderNo());
     model.setOutRequestNo(refundOrderId);
     request.setBizModel((AlipayObject)model);
     try {
       AlipayTradeFastpayRefundQueryResponse response = (AlipayTradeFastpayRefundQueryResponse)defaultAlipayClient.execute((AlipayRequest)request);
       if (response.isSuccess()) {
         return Result.createSuccessResult(response);
       }
       _log.info("{}返回失败", new Object[] { logPrefix });
       _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });
       return Result.createFailResult(response.getSubMsg(), response.getSubCode());
     }
     catch (AlipayApiException e) {
       return Result.createFailResult(e.getErrMsg(), e.getErrCode());
     }
   }


   public Result queryReq(String payOrderId, String mchId, String channelId) throws Exception {
     String logPrefix = "【支付宝订单交易状态查询】";
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
     AlipayTradeQueryModel model = new AlipayTradeQueryModel();
     model.setOutTradeNo(payOrderId);
     request.setBizModel((AlipayObject)model);
     Map<String, Object> map = new HashMap<>();
     map.put("payOrderId", payOrderId);
     try {
       AlipayTradeQueryResponse response = (AlipayTradeQueryResponse)defaultAlipayClient.execute((AlipayRequest)request);
       _log.warn("{}查询响应数据：{}", new Object[] { logPrefix, JSONObject.toJSONString(response) });
       if (response.isSuccess()) {

         try {
           PayOrder payOrder = new PayOrder();
           BigDecimal amount = (new BigDecimal(response.getTotalAmount())).multiply(BigDecimal.valueOf(100L));
           payOrder.setAmount(Long.valueOf(amount.longValue()));
           payOrder.setChannelOrderNo(response.getTradeNo());
           payOrder.setPayOrderId(payOrderId);
           payOrder.setMchId(mchId);
           payOrder.setChannelId(channelId);
           if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
             payOrder.setStatus(Byte.valueOf((byte)2));
             payOrder.setPaySuccTime(Long.valueOf(response.getSendPayDate().getTime()));
           } else {
             payOrder.setStatus(Byte.valueOf((byte)-1));
           }
           return Result.createSuccessMap(payOrder);
         } catch (Exception e) {
           e.printStackTrace();
           throw e;
         }
       }
       _log.info("{}返回失败", new Object[] { logPrefix });
       _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });


       return Result.createFailMap("支付宝订单交易订单查询失败", null);
     }
     catch (AlipayApiException e) {
       _log.error((Throwable)e, "");
       throw new Exception("支付宝订单交易订单查询异常");
     }
   }

   public Result queryReq(PayOrder payOrder) throws NoSuchMethodException {
     String logPrefix = "【支付宝订单交易状态查询】";
     String payOrderId = payOrder.getPayOrderId();
     String mchId = payOrder.getMchId();
     String channelId = payOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     this.alipayConfig.init(payChannelForRuntime.getParam());
     DefaultAlipayClient defaultAlipayClient = new DefaultAlipayClient(this.alipayConfig.getUrl(), this.alipayConfig.getApp_id(), this.alipayConfig.getRsa_private_key(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, this.alipayConfig.getAlipay_public_key(), AlipayConfig.SIGNTYPE);
     AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
     AlipayTradeQueryModel model = new AlipayTradeQueryModel();
     model.setOutTradeNo(payOrder.getPayOrderId());
     model.setTradeNo(payOrder.getChannelOrderNo());
     request.setBizModel((AlipayObject)model);
     Map<String, Object> map = new HashMap<>();
     map.put("payOrderId", payOrderId);
     try {
       AlipayTradeQueryResponse response = (AlipayTradeQueryResponse)defaultAlipayClient.execute((AlipayRequest)request);
       if (response.isSuccess()) {

         if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
           payOrder.setStatus(Byte.valueOf((byte)2));
           payOrder.setPaySuccTime(Long.valueOf(response.getSendPayDate().getTime()));
           this.baseService4PayOrderForCache.baseUpdateStatus4Success(payOrder.getPayOrderId(), payOrder.getChannelOrderNo(), response.getSendPayDate());
         } else {
           payOrder.setStatus(Byte.valueOf((byte)1));
         }

         _log.warn("{}查询响应数据：{}", new Object[] { logPrefix, JSONObject.toJSONString(response) });
         return Result.createSuccessMap(payOrder);
       }
       _log.info("{}返回失败", new Object[] { logPrefix });
       _log.info("sub_code:{},sub_msg:{}", new Object[] { response.getSubCode(), response.getSubMsg() });


       return Result.createFailMap("支付宝订单交易订单查询失败", null);
     }
     catch (AlipayApiException e) {
       _log.error((Throwable)e, "");

       return Result.createFailMap("支付宝订单交易订单查询失败", null);
     }
   }
 }

