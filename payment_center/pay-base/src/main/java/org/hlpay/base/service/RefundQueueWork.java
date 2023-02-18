 package org.hlpay.base.service;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.hlpay.base.model.RefundOrder;
 import org.hlpay.base.service.impl.PayOrderServiceImpl;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.JsonUtil;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;

 public class RefundQueueWork
 {
   private final MyLog _log = MyLog.getLog(RefundQueueWork.class);


   @Autowired
   private MchInfoService mchInfoService;


   @Autowired
   private PayChannelForPayService payChannelForPayService;


   @Autowired
   private PayOrderServiceImpl payOrderService;


   @Autowired
   private IRefundOrderService refundOrderService;


   @Autowired
   private BaseService4RefundOrder baseService4RefundOrder;


   public CommonResult<Result> payOrderWork(String params) {
     this._log.info("###### 开始接收商户统一退款请求 ######", new Object[0]);
     String logPrefix = "【商户统一退款】";
     try {
       JSONObject po = JSONObject.parseObject(params);
       JSONObject refundContext = new JSONObject();
       RefundOrder refundOrder = null;

       Object object = validateParams(po, refundContext, false);
       if (object instanceof String) {
         this._log.info("{}参数校验不通过:{}", new Object[] { logPrefix, object });
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", object.toString(), null, null));
       }
       if (object instanceof JSONObject) {
         refundOrder = (RefundOrder)JSONObject.toJavaObject((JSON)object, RefundOrder.class);
       }
       if (refundOrder == null) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "创建退款订单失败", null, null));
       }
       int result = this.refundOrderService.create(refundOrder);
       this._log.info("{}创建退款订单,结果:{}", new Object[] { logPrefix, Integer.valueOf(result) });
       if (result != 1) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "创建退款订单失败", null, null));
       }






       String channelId = refundOrder.getChannelId();
       switch (channelId) {
         case "WX_APP":
           return this.refundOrderService.doWxRefundReq("APP", refundOrder, refundContext.getString("resKey"));
         case "WX_JSAPI":
           return this.refundOrderService.doWxRefundReq("JSAPI", refundOrder, refundContext.getString("resKey"));
         case "WX_NATIVE":
           return this.refundOrderService.doWxRefundReq("NATIVE", refundOrder, refundContext.getString("resKey"));
         case "WX_MWEB":
           return this.refundOrderService.doWxRefundReq("MWEB", refundOrder, refundContext.getString("resKey"));
         case "ALIPAY_MOBILE":
           return this.refundOrderService.doAliRefundReq("MOBILE", refundOrder, refundContext.getString("resKey"));
         case "ALIPAY_PC":
           return this.refundOrderService.doAliRefundReq("PC", refundOrder, refundContext.getString("resKey"));
         case "ALIPAY_WAP":
           return this.refundOrderService.doAliRefundReq("WAP", refundOrder, refundContext.getString("resKey"));
         case "ALIPAY_QR":
           return this.refundOrderService.doAliRefundReq("QR", refundOrder, refundContext.getString("resKey"));
       }




       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "不支持的支付渠道类型[channelId=" + channelId + "]", null, null));



     }
     catch (Exception e) {
       String msg = e.getMessage();
       this._log.error(e, msg);
       if (msg.contains("IDX_MchId_MchOrderNo")) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "订单重复", null, PayEnum.ERR_0119));
       }
       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", e.getMessage(), null, PayEnum.ERR_0010));
     }
   }








   public Object validateDoRefundParams(JSONObject params, JSONObject refundContext) throws NoSuchMethodException {
     JSONObject refundOrder;
     String mchId = params.getString("mchId");
     String mchRefundNo = params.getString("mchRefundNo");


     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }

     if (StringUtils.isBlank(mchRefundNo)) {
       String errorMessage = "request params[mchRefundNo] error.";
       return errorMessage;
     }


     JSONObject mchInfo = this.mchInfoService.getByMchId(mchId);
     if (mchInfo == null) {
       String errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (mchInfo.getByte("state").byteValue() != 1) {
       String errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       return errorMessage;
     }

     String reqKey = mchInfo.getString("reqKey");
     if (StringUtils.isBlank(reqKey)) {
       String errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     refundContext.put("resKey", mchInfo.getString("resKey"));
     refundContext.put("reqKey", mchInfo.getString("reqKey"));
     RefundOrder order = this.baseService4RefundOrder.baseSelectByMchIdAndMchRefundNo(mchId, mchRefundNo);
     JSONObject queryParam = new JSONObject();
     queryParam.put("mchId", mchId);
     queryParam.put("mchOrderNo", order.getMchOrderNo());
     queryParam.put("executeNotify", "false");
     queryParam.put("queryFromChannel", "true");

     Result queryResult = this.payOrderService.queryPayOrder(queryParam);

     Map<String, Object> bizResult = (Map<String, Object>)queryResult.getBizResult();


     JSONObject payOrder = (JSONObject)bizResult;
     if (payOrder == null) {
       String errorMessage = "payOrder is not exist.";
       return errorMessage;
     }


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX refund sign failed.";
       return errorMessage;
     }


     if (order != null) {
       refundOrder = JsonUtil.getJSONObjectFromObj(order);
     } else {
       return "refundOrder is null[mchRefundNo=" + mchRefundNo + "] record in db.";
     }
     return refundOrder;
   }

   public Object validateParams(JSONObject params, JSONObject refundContext, boolean defaultFreeze) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String payOrderId = params.getString("payOrderId");
     String mchOrderNo = params.getString("mchOrderNo");
     String mchRefundNo = params.getString("mchRefundNo");
     Boolean preFreeze = params.getBoolean("preFreeze");

     String amount = params.getString("amount");

     String clientIp = params.getString("clientIp");
     String device = params.getString("device");
     String extra = params.getString("extra");
     String param1 = params.getString("param1");
     String param2 = params.getString("param2");
     String notifyUrl = params.getString("notifyUrl");
     String sign = params.getString("sign");
     String channelUser = params.getString("channelUser");
     String userName = params.getString("userName");
     String remarkInfo = params.getString("remarkInfo");

     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(payOrderId) && StringUtils.isBlank(mchOrderNo)) {
       String errorMessage = "request params[payOrderId,mchOrderNo] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(mchRefundNo)) {
       String errorMessage = "request params[mchRefundNo] error.";
       return errorMessage;
     }




     if (!NumberUtils.isNumber(amount)) {
       String errorMessage = "request params[amount] error.";
       return errorMessage;
     }




     if (StringUtils.isBlank(notifyUrl)) {
       String errorMessage = "request params[notifyUrl] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(channelUser)) {
       String errorMessage = "request params[channelUser] error.";
       return errorMessage;
     }


     if (StringUtils.isBlank(sign)) {
       String errorMessage = "request params[sign] error.";
       return errorMessage;
     }


     JSONObject mchInfo = this.mchInfoService.getByMchId(mchId);
     if (mchInfo == null) {
       String errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (mchInfo.getByte("state").byteValue() != 1) {
       String errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       return errorMessage;
     }

     String reqKey = mchInfo.getString("reqKey");
     if (StringUtils.isBlank(reqKey)) {
       String errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     refundContext.put("resKey", mchInfo.getString("resKey"));
     refundContext.put("reqKey", mchInfo.getString("reqKey"));



     JSONObject queryParam = new JSONObject();
     queryParam.put("mchId", mchId);
     queryParam.put("mchOrderNo", mchOrderNo);
     queryParam.put("payOrderId", payOrderId);
     queryParam.put("executeNotify", "false");
     queryParam.put("queryFromChannel", "true");

     Result queryResult = this.payOrderService.queryPayOrder(queryParam);


     Map<String, Object> bizResult = (Map<String, Object>)queryResult.getBizResult();


     JSONObject payOrder = (JSONObject)bizResult;
     if (payOrder == null) {
       String errorMessage = "payOrder is not exist.";
       return errorMessage;
     }
     String channelId = payOrder.getString("channelId");

     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       String errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       String errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       return errorMessage;
     }
     refundContext.put("channelName", payChannel.getString("channelName"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX refund sign failed.";
       return errorMessage;
     }



     String channelPayOrderNo = payOrder.getString("channelOrderNo");
     Long payAmount = payOrder.getLong("amount");
     Long refundAmount = Long.valueOf(Long.parseLong(amount));
     if (refundAmount.longValue() > payAmount.longValue()) {
       String errorMessage = "amount not available [channelId=" + channelId + ",mchId=" + mchId + "]退款金额大于支付金额";
       return errorMessage;
     }

     JSONObject param1Obj = JSONObject.parseObject(param1);
     if (param1Obj != null) {
       if (preFreeze == null) {
         preFreeze = Boolean.valueOf(defaultFreeze);
       }
       param1Obj.put("preFreeze", Boolean.valueOf(preFreeze.booleanValue()));
     } else {
       param1Obj = new JSONObject();
       param1Obj.put("preFreeze", Boolean.valueOf(defaultFreeze));
     }
     param1 = param1Obj.toJSONString();

     JSONObject refundOrder = new JSONObject();


     refundOrder.put("refundOrderId", mchRefundNo);
     refundOrder.put("payOrderId", payOrder.getString("payOrderId"));
     refundOrder.put("channelPayOrderNo", channelPayOrderNo);
     refundOrder.put("mchId", mchId);
     refundOrder.put("mchOrderNo", mchOrderNo);
     refundOrder.put("channelAccount", payOrder.getString("channelAccount"));
     refundOrder.put("mchRefundNo", mchRefundNo);
     refundOrder.put("channelId", channelId);
     refundOrder.put("refundAmount", Long.valueOf(Long.parseLong(amount)));
     refundOrder.put("payAmount", payAmount);
     refundOrder.put("currency", payOrder.getString("currency"));
     refundOrder.put("clientIp", clientIp);
     refundOrder.put("device", device);
     refundOrder.put("channelUser", channelUser);
     refundOrder.put("userName", userName);
     refundOrder.put("remarkInfo", remarkInfo);
     refundOrder.put("extra", extra);
     refundOrder.put("channelMchId", payChannel.getString("channelMchId"));
     refundOrder.put("param1", param1);
     refundOrder.put("param2", param2);
     refundOrder.put("notifyUrl", notifyUrl);
     return refundOrder;
   }




   public Object preValidateParams(JSONObject params, JSONObject refundContext) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String payOrderId = params.getString("payOrderId");
     String mchOrderNo = params.getString("mchOrderNo");
     String mchRefundNo = params.getString("mchRefundNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     String currency = params.getString("currency");
     String clientIp = params.getString("clientIp");
     String device = params.getString("device");
     String extra = params.getString("extra");
     String param1 = params.getString("param1");
     String param2 = params.getString("param2");
     String notifyUrl = params.getString("notifyUrl");
     String sign = params.getString("sign");
     String channelUser = params.getString("channelUser");
     String userName = params.getString("userName");
     String remarkInfo = params.getString("remarkInfo");

     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(payOrderId) && StringUtils.isBlank(mchOrderNo)) {
       String errorMessage = "request params[payOrderId,mchOrderNo] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(mchRefundNo)) {
       String errorMessage = "request params[mchRefundNo] error.";
       return errorMessage;
     }




     if (!NumberUtils.isNumber(amount)) {
       String errorMessage = "request params[amount] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(currency)) {
       String errorMessage = "request params[currency] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(notifyUrl)) {
       String errorMessage = "request params[notifyUrl] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(channelUser)) {
       String errorMessage = "request params[channelUser] error.";
       return errorMessage;
     }


     if (StringUtils.isBlank(sign)) {
       String errorMessage = "request params[sign] error.";
       return errorMessage;
     }


     JSONObject mchInfo = this.mchInfoService.getByMchId(mchId);
     if (mchInfo == null) {
       String errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (mchInfo.getByte("state").byteValue() != 1) {
       String errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       return errorMessage;
     }

     String reqKey = mchInfo.getString("reqKey");
     if (StringUtils.isBlank(reqKey)) {
       String errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     refundContext.put("resKey", mchInfo.getString("resKey"));
     refundContext.put("reqKey", mchInfo.getString("reqKey"));



     JSONObject queryParam = new JSONObject();
     queryParam.put("mchId", mchId);
     queryParam.put("mchOrderNo", mchOrderNo);
     queryParam.put("payOrderId", payOrderId);
     queryParam.put("executeNotify", "false");
     queryParam.put("queryFromChannel", "true");

     Result queryResult = this.payOrderService.queryPayOrder(queryParam);


     Map<String, Object> bizResult = (Map<String, Object>)queryResult.getBizResult();


     JSONObject payOrder = (JSONObject)bizResult;
     if (payOrder == null) {
       String errorMessage = "payOrder is not exist.";
       return errorMessage;
     }


     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       String errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       String errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       return errorMessage;
     }
     refundContext.put("channelName", payChannel.getString("channelName"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX refund sign failed.";
       return errorMessage;
     }



     String channelPayOrderNo = payOrder.getString("channelOrderNo");
     Long payAmount = payOrder.getLong("amount");
     Long refundAmount = Long.valueOf(Long.parseLong(amount));
     if (refundAmount.longValue() > payAmount.longValue()) {
       String errorMessage = "amount not available [channelId=" + channelId + ",mchId=" + mchId + "]退款金额大于支付金额";
       return errorMessage;
     }

     JSONObject refundOrder = new JSONObject();


     refundOrder.put("refundOrderId", mchRefundNo);
     refundOrder.put("payOrderId", payOrderId);
     refundOrder.put("channelPayOrderNo", channelPayOrderNo);
     refundOrder.put("mchId", mchId);
     refundOrder.put("mchOrderNo", mchOrderNo);
     refundOrder.put("channelAccount", payOrder.getString("channelAccount"));
     refundOrder.put("mchRefundNo", mchRefundNo);
     refundOrder.put("channelId", channelId);
     refundOrder.put("refundAmount", Long.valueOf(Long.parseLong(amount)));
     refundOrder.put("payAmount", payAmount);
     refundOrder.put("currency", currency);
     refundOrder.put("clientIp", clientIp);
     refundOrder.put("device", device);
     refundOrder.put("channelUser", channelUser);
     refundOrder.put("userName", userName);
     refundOrder.put("remarkInfo", remarkInfo);
     refundOrder.put("extra", extra);
     refundOrder.put("channelMchId", payChannel.getString("channelMchId"));
     refundOrder.put("param1", param1);
     refundOrder.put("param2", param2);
     refundOrder.put("notifyUrl", notifyUrl);
     return refundOrder;
   }
 }

