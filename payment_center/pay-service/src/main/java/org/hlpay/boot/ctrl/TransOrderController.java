 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.service.BaseService4TransOrder;
 import org.hlpay.base.service.ITransOrderService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.JsonUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.MySeq;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;








 @RestController
 public class TransOrderController
 {
   private final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.TransOrderController.class);



   @Autowired
   private ITransOrderService transOrderService;



   @Autowired
   private MchInfoService mchInfoService;


   @Autowired
   private PayChannelForPayService payChannelForPayService;


   @Autowired
   private BaseService4TransOrder baseService4TransOrder;



   @RequestMapping({"/trans/create_order"})
   public CommonResult<Result> transOrder(@RequestParam String params) {
     this._log.info("###### 开始接收商户统一转账请求 ######", new Object[0]);
     this._log.info("请求参数：{}", new Object[] { params });
     String logPrefix = "【商户统一转账】";
     try {
       JSONObject po = JSONObject.parseObject(params);
       JSONObject transContext = new JSONObject();
       TransOrder transOrder = null;

       Object object = validateParams(po, transContext);
       if (object instanceof String) {
         this._log.info("{}参数校验不通过:{}", new Object[] { logPrefix, object });
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", object.toString(), null, null));
       }
       if (object instanceof JSONObject) {
         transOrder = (TransOrder)JSONObject.toJavaObject((JSON)object, TransOrder.class);
       }
       if (transOrder == null) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "支付中心转账失败", null, null));
       }

       String transOrderId = transOrder.getTransOrderId();
       String channelId = transOrder.getChannelId();
       String mchId = transOrder.getMchId();
       TransOrder trans = this.baseService4TransOrder.baseSelectByMchIdAndTransOrderId(mchId, transOrderId);
       if (trans == null) {
         int result = this.transOrderService.create(transOrder);
         this._log.info("{}创建转账订单,结果:{}", new Object[] { logPrefix, Integer.valueOf(result) });
         if (result != 1) {
           return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "创建转账订单失败", null, null));
         }
       } else if (0 == trans.getStatus().byteValue() || 1 == trans
         .getStatus().byteValue() || 3 == trans
         .getStatus().byteValue()) {
         this.baseService4TransOrder.baseSetStatus4Init(transOrderId);
         transOrder = trans;
       } else {
         Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
         map.put("isSuccess", Boolean.valueOf(true));
         map.put("channelOrderNo", transOrderId);
         return HLPayUtil.makeRetData(map, transContext.getString("resKey"));
       }

       switch (channelId) {
         case "WX_APP":
           return this.transOrderService.doWxTransReq("APP", transOrder, transContext.getString("resKey"));
         case "WX_JSAPI":
           return this.transOrderService.doWxTransReq("JSAPI", transOrder, transContext.getString("resKey"));
         case "WX_NATIVE":
           return this.transOrderService.doWxTransReq("NATIVE", transOrder, transContext.getString("resKey"));
         case "WX_MWEB":
           return this.transOrderService.doWxTransReq("MWEB", transOrder, transContext.getString("resKey"));
         case "ALIPAY_MOBILE":
           return this.transOrderService.doAliTransReq("MOBILE", transOrder, transContext.getString("resKey"));
         case "ALIPAY_PC":
           return this.transOrderService.doAliTransReq("PC", transOrder, transContext.getString("resKey"));
         case "ALIPAY_WAP":
           return this.transOrderService.doAliTransReq("WAP", transOrder, transContext.getString("resKey"));
         case "ALIPAY_QR":
           return this.transOrderService.doAliTransReq("QR", transOrder, transContext.getString("resKey"));
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









   private Object validateParams(JSONObject params, JSONObject transContext) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String mchTransNo = params.getString("mchTransNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     this._log.info("转账金额（单位分）:{}", new Object[] { amount });
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






















     String errorMessage = commonCheckParams(params);
     if (errorMessage != null) {
       return errorMessage;
     }

     if (StringUtils.isBlank(notifyUrl)) {
       errorMessage = "request params[notifyUrl] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(channelUser)) {
       errorMessage = "request params[channelUser] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(remarkInfo)) {
       errorMessage = "request params[remarkInfo] error.";
       return errorMessage;
     }


     if (StringUtils.isBlank(sign)) {
       errorMessage = "request params[sign] error.";
       return errorMessage;
     }


     JSONObject mchInfo = this.mchInfoService.getByMchId(mchId);
     if (mchInfo == null) {
       errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (mchInfo.getByte("state").byteValue() != 1) {
       errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       return errorMessage;
     }

     String reqKey = mchInfo.getString("reqKey");
     if (StringUtils.isBlank(reqKey)) {
       errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     transContext.put("resKey", mchInfo.getString("resKey"));
     transContext.put("reqKey", mchInfo.getString("reqKey"));


     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       return errorMessage;
     }
     transContext.put("channelName", payChannel.getString("channelName"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       errorMessage = "Verify XX trans sign failed.";
       return errorMessage;
     }

     JSONObject transOrder = new JSONObject();
     transOrder.put("transOrderId", UnionIdUtil.getUnionId(new String[] { mchId, mchTransNo }));
     transOrder.put("mchId", mchId);
     transOrder.put("mchTransNo", mchTransNo);
     transOrder.put("channelId", channelId);
     transOrder.put("amount", Long.valueOf(Long.parseLong(amount)));
     transOrder.put("currency", currency);
     transOrder.put("clientIp", clientIp);
     transOrder.put("device", device);
     transOrder.put("channelUser", channelUser);
     transOrder.put("userName", userName);
     transOrder.put("remarkInfo", remarkInfo);
     transOrder.put("extra", extra);
     transOrder.put("channelMchId", payChannel.getString("channelMchId"));
     transOrder.put("param1", param1);
     transOrder.put("param2", param2);
     transOrder.put("notifyUrl", notifyUrl);
     return transOrder;
   }









   private Object validateTransParams(String action, JSONObject params, JSONObject transContext) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String channelId = params.getString("channelId");
     String mchTransNo = params.getString("mchTransNo");
     String amount = params.getString("amount");
     String currency = params.getString("currency");
     this._log.info("转账金额（单位{}）:{}", new Object[] { currency, amount });
     String clientIp = params.getString("clientIp");
     String device = params.getString("device");
     String sign = params.getString("sign");
     String channelUser = params.getString("channelUser");
     String userName = params.getString("userName");
     String remarkInfo = params.getString("remarkInfo");
     String param1 = params.getString("param1");






















     String errorMessage = commonCheckParams(params);
     if (errorMessage != null) {
       return errorMessage;
     }

     if (StringUtils.isBlank(channelUser)) {
       errorMessage = "request params[channelUser] error.";
       return errorMessage;
     }

     if (StringUtils.isBlank(remarkInfo)) {
       errorMessage = "request params[remarkInfo] error.";
       return errorMessage;
     }

     if (StringUtils.isBlank(param1)) {
       errorMessage = "request params[param1] error.";
       return errorMessage;
     }


     if (StringUtils.isBlank(sign)) {
       errorMessage = "request params[sign] error.";
       return errorMessage;
     }


     JSONObject mchInfo = this.mchInfoService.getByMchId(mchId);
     if (mchInfo == null) {
       errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (mchInfo.getByte("state").byteValue() != 1) {
       errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       return errorMessage;
     }

     String reqKey = mchInfo.getString("reqKey");
     if (StringUtils.isBlank(reqKey)) {
       errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     transContext.put("resKey", mchInfo.getString("resKey"));
     transContext.put("reqKey", mchInfo.getString("reqKey"));


     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       return errorMessage;
     }
     transContext.put("channelName", payChannel.getString("channelName"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey, new String[] { "channelId", "device", "notifyUrl", "param2" });
     if (!verifyFlag) {
       errorMessage = "Verify XX trans sign failed.";
       return errorMessage;
     }

     JSONObject transOrder = new JSONObject();

     if (channelId.contains("RECHARGE")) {
       String orderId = "RECHARGE" + MySeq.getTrans();
       transOrder.put("extra", "RECHARGE");
     } else if (channelId.contains("WITHDRAW")) {
       String orderId = "WITHDRAW" + MySeq.getTrans();
       transOrder.put("extra", "WITHDRAW");
     } else {
       String orderId = "EXCHANGE" + MySeq.getTrans();
       transOrder.put("extra", "EXCHANGE");
     }

     String str1 = UnionIdUtil.getUnionId(new String[] { mchId, mchTransNo });
     transOrder.put("transOrderId", str1);
     transOrder.put("mchId", mchId);
     transOrder.put("mchTransNo", mchTransNo);
     transOrder.put("channelId", channelId);
     transOrder.put("amount", Long.valueOf(Long.parseLong(amount)));
     transOrder.put("currency", currency);
     transOrder.put("clientIp", clientIp);
     transOrder.put("device", device);
     transOrder.put("channelUser", channelUser);
     transOrder.put("userName", userName);
     transOrder.put("remarkInfo", remarkInfo);
     transOrder.put("channelMchId", payChannel.getString("channelMchId"));
     transOrder.put("notifyUrl", "empty");
     transOrder.put("param1", param1);
     return transOrder;
   }








   private Object validateDoTransParams(JSONObject params, JSONObject transContext) throws NoSuchMethodException {
     JSONObject payOrder;
     String mchId = params.getString("mchId");
     String mchTransNo = params.getString("mchTransNo");
     String sign = params.getString("sign");


     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(mchTransNo)) {
       String errorMessage = "request params[mchTransNo] error.";
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
     transContext.put("resKey", mchInfo.getString("resKey"));
     transContext.put("reqKey", mchInfo.getString("reqKey"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX pay sign failed.";
       return errorMessage;
     }
     TransOrder order = this.baseService4TransOrder.baseSelectByMchIdAndMchTransNo(mchId, mchTransNo);


     if (order != null) {
       payOrder = JsonUtil.getJSONObjectFromObj(order);
     } else {
       return "transOrder is null[mchTransNo=" + mchTransNo + "] record in db.";
     }
     return payOrder;
   }


   String commonCheckParams(JSONObject params) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String mchTransNo = params.getString("mchTransNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     String currency = params.getString("currency");












     if (StringUtils.isBlank(mchId)) {
       return "request params[mchId] error.";
     }
     if (StringUtils.isBlank(mchTransNo)) {
       return "request params[mchTransNo] error.";
     }
     if (StringUtils.isBlank(channelId)) {
       return "request params[channelId] error.";
     }
     if (StringUtils.isBlank(amount)) {
       return "request params[amount] error.";
     }
     if (!NumberUtils.isDigits(amount)) {
       return "request params[amount] error.";
     }

     if (StringUtils.isBlank(currency)) {
       return "request params[currency] error.";
     }


     if (StringUtils.isBlank(currency)) {
       return "request params[currency] error.";
     }
     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       return "error: can not find channel. [channelId=" + channelId + ",mchId=" + mchId + "]";
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       return "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
     }
     if (!currency.equals(payChannel.getJSONObject("param").get("currency"))) {
       return "request params[currency] error.";
     }
     return null;
   }
 }





