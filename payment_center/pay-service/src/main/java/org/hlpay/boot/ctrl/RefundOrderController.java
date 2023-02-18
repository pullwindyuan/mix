 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSONObject;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.hlpay.base.service.ITransOrderService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.boot.mq.MqRefundWork;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;












 @RestController
 public class RefundOrderController
   extends MqRefundWork
 {
   private final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.RefundOrderController.class);


   @Autowired
   private MchInfoService mchInfoService;


   @Autowired
   private PayChannelForPayService payChannelForPayService;


   @Resource
   private MqRefundWork mqRefundWork;


   @Autowired
   private ITransOrderService transOrderService;



   @RequestMapping({"/refund/queue"})
   public CommonResult<Result> payOrder(@RequestParam String params) {
     this._log.info("###### 开始接收商户统一退款请求 ######", new Object[0]);
     String logPrefix = "【商户统一退款】";
     try {
       JSONObject po = JSONObject.parseObject(params);
       JSONObject refundContext = new JSONObject();
       JSONObject refundOrder = null;

       Object object = this.mqRefundWork.preValidateParams(po, refundContext);
       if (object instanceof String) {
         this._log.info("{}参数校验不通过:{}", new Object[] { logPrefix, object });
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", object.toString(), null, null));
       }
       if (object instanceof JSONObject) {
         refundOrder = (JSONObject)object;
       }
       if (refundOrder == null) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "创建退款订单失败", null, null));
       }
       po.put("channelId", refundOrder.getString("channelId"));
       this._log.info("{}异步退款参数：{}", new Object[] { logPrefix, po.toJSONString() });
       CommonResult<Result> flag = addRefund(po);
       if (flag == null) {
         return HLPayUtil.makeRetData(HLPayUtil.makeRetMap("FAIL", "您的退款申请已经提交,七十二小时到账,请注意查收", null, null), refundContext.getString("resKey"));
       }
       return flag;
     }
     catch (Exception e) {
       this._log.error(e, "");
       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", e.getMessage(), null, null));
     }
   }











   @RequestMapping({"/refund/create_order"})
   public CommonResult<Result> payOrderWork(@RequestParam String params) {
     return this.mqRefundWork.payOrderWork(params);
   }









   public Object validateTransRefundParams(JSONObject params, JSONObject refundContext, boolean defaultFreeze) throws NoSuchMethodException {
     String mchId = params.getString("mchId");

     String mchTransNo = params.getString("mchTransNo");
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
     queryParam.put("mchTransNo", mchTransNo);

     Map queryResult = this.transOrderService.selectByMchIdAndMchTransNo(queryParam);


     Map<String, Object> bizResult = (Map<String, Object>)queryResult.get("bizResult");


     JSONObject transOrder = (JSONObject)bizResult;
     if (transOrder == null) {
       String errorMessage = "payOrder is not exist.";
       return errorMessage;
     }
     String channelId = transOrder.getString("channelId");

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



     String channelPayOrderNo = transOrder.getString("transOrderId");
     Long payAmount = transOrder.getLong("amount");
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



     refundOrder.put("refundOrderId", UnionIdUtil.getUnionId(new String[] { mchId, mchRefundNo }));
     refundOrder.put("payOrderId", transOrder.getString("transOrderId"));
     refundOrder.put("channelPayOrderNo", channelPayOrderNo);
     refundOrder.put("mchId", mchId);
     refundOrder.put("mchOrderNo", mchTransNo);
     refundOrder.put("channelAccount", payChannel.getString("channelAccount"));
     refundOrder.put("mchRefundNo", mchRefundNo);
     refundOrder.put("channelId", channelId);
     refundOrder.put("refundAmount", Long.valueOf(Long.parseLong(amount)));
     refundOrder.put("payAmount", payAmount);
     refundOrder.put("currency", transOrder.getString("currency"));
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





