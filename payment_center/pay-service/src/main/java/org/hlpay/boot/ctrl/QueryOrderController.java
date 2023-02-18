 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.IRefundOrderService;
 import org.hlpay.base.service.ITransOrderService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.impl.PayOrderServiceImpl;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.enumm.RetEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;













 @RestController
 public class QueryOrderController
 {
   private final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.QueryOrderController.class);


   @Autowired
   private PayOrderServiceImpl payOrderService;


   @Autowired
   private ITransOrderService transOrderService;


   @Autowired
   private MchInfoService mchInfoService;


   @Autowired
   private IRefundOrderService refundOrderService;

   @Autowired
   private Mq4MchNotify mq4MchNotify;


   @RequestMapping({"/pay/query_order"})
   public CommonResult<JSONObject> queryPayOrder(@RequestParam String params) {
     this._log.info("###### 开始接收商户查询支付订单请求 ######", new Object[0]);
     String logPrefix = "【商户支付订单查询】";
     try {
       JSONObject po = JSONObject.parseObject(params);
       JSONObject payContext = new JSONObject();

       String errorMessage = validateParams(po, payContext);
       if (!"success".equalsIgnoreCase(errorMessage)) {
         this._log.warn(errorMessage, new Object[0]);
         return CommonResult.error("查询失败");
       }
       this._log.debug("请求参数及签名校验通过", new Object[0]);
       po.put("queryFromChannel", "true");

       JSONObject jsonParam = new JSONObject();
       jsonParam.put("queryParam", po);
       Result queryResult = this.payOrderService.queryPayOrder(po);
       Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
       PayOrder temp = (PayOrder)queryResult.getBizResult();
       JSONObject payOrder = (JSONObject)JSONObject.toJSON(temp);

       if (payOrder == null) {
         return CommonResult.error("查询失败");
       }

       if (payOrder.getByte("status").byteValue() == 2) {
         this.payOrderService.updateStatus4Success(payOrder);
         payOrder.put("withOutQuery", Boolean.valueOf(true));
         String payOrderStr = payOrder.toJSONString();
         this.mq4MchNotify.send("queue.notify.mch.settle", payOrderStr);
       }

















       map.put("result", payOrder.toJSONString());
       this._log.info("{}查询支付订单,结果:{}", new Object[] { logPrefix, payOrder.toJSONString() });
       this._log.info("###### 商户查询订单处理完成 ######", new Object[0]);





       return CommonResult.success((Serializable)payOrder);
     } catch (Exception e) {
       this._log.error(e, "");
       return CommonResult.error("查询失败");
     }
   }










   @RequestMapping({"/pay/query_refund_order"})
   public CommonResult<Result> queryRefundOrder(@RequestParam String params) {
     this._log.info("###### 开始接收商户查询支付订单请求 ######", new Object[0]);
     String logPrefix = "【商户支付订单查询】";
     try {
       JSONObject po = JSONObject.parseObject(params);
       JSONObject payContext = new JSONObject();

       String errorMessage = validateRefundParams(po, payContext);
       if (!"success".equalsIgnoreCase(errorMessage)) {
         this._log.warn(errorMessage, new Object[0]);
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", errorMessage, null, PayEnum.ERR_0014));
       }
       this._log.debug("请求参数及签名校验通过", new Object[0]);
       po.put("queryFromChannel", "true");




       JSONObject jsonParam = new JSONObject();

       Result queryResult = this.refundOrderService.query(po);
       Map<String, Object> map = HLPayUtil.makeRetMap("SUCCESS", "", "SUCCESS", null);
       JSONObject refundOrder = (JSONObject)queryResult.getBizResult();

       if (refundOrder == null) {
         return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage(), null, PayEnum.ERR_0112));
       }
       map.put("result", refundOrder);
       this._log.info("{}查询支付订单,结果:{}", new Object[] { logPrefix, queryResult.toString() });


       this._log.info("###### 商户查询订单处理完成 ######", new Object[0]);
       return HLPayUtil.makeRetData(map, payContext.getString("resKey"));
     } catch (Exception e) {
       this._log.error(e, "");
       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "支付中心系统异常", null, PayEnum.ERR_0010));
     }
   }








   private String validateParams(JSONObject params, JSONObject payContext) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String mchOrderNo = params.getString("mchOrderNo");
     String payOrderId = params.getString("payOrderId");

     String sign = params.getString("sign");


     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(mchOrderNo) && StringUtils.isBlank(payOrderId)) {
       String errorMessage = "request params[mchOrderNo or payOrderId] error.";
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
     payContext.put("resKey", mchInfo.getString("resKey"));
     payContext.put("reqKey", mchInfo.getString("reqKey"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX pay sign failed.";
       return errorMessage;
     }

     return "success";
   }









   private String validateRefundParams(JSONObject params, JSONObject payContext) throws NoSuchMethodException {
     String mchId = params.getString("mchId");
     String mchRefundNo = params.getString("mchRefundNo");
     String refundOrderId = params.getString("refundOrderId");

     String sign = params.getString("sign");


     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       return errorMessage;
     }
     if (StringUtils.isBlank(mchRefundNo) && StringUtils.isBlank(refundOrderId)) {
       String errorMessage = "request params[mchOrderNo or payOrderId] error.";
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
     payContext.put("resKey", mchInfo.getString("resKey"));
     payContext.put("reqKey", mchInfo.getString("reqKey"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey);
     if (!verifyFlag) {
       String errorMessage = "Verify XX pay sign failed.";
       return errorMessage;
     }

     return "success";
   }
 }





