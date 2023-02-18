 package org.hlpay.boot.service


 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.alipay.api.AlipayApiException;
 import com.alipay.api.internal.util.AlipaySignature;
 import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
 import io.swagger.annotations.ApiOperation;
 import java.math.BigDecimal;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.channel.alipay.AlipayConfig;
 import org.hlpay.base.channel.wechat.WxPayUtil;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.RefundOrder;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.BaseService4RefundOrder;
 import org.hlpay.base.service.INotifyRefundService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.boot.service.BaseNotify4MchRefund;
 import org.hlpay.common.enumm.RetEnum;
 import org.hlpay.common.util.Base64Util;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.ResultUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Service;


 @Service
 @Primary
 public class NotifyRefundServiceImpl
   extends BaseNotify4MchRefund
   implements INotifyRefundService
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.service.NotifyRefundServiceImpl.class);

   @Autowired
   private AlipayConfig alipayConfig;

   @Resource
   private BaseService4RefundOrder baseSelectRefundOrder;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Autowired
   private PayChannelForPayService payChannelForPayService;


   public Map doAliRefundNotify(JSONObject jsonParam) throws NoSuchMethodException {
     String logPrefix = "【处理支付宝支付回调】";
     _log.info("====== 开始处理支付宝支付回调通知 ======", new Object[0]);
     Map<String, Object> result = new HashMap<>();
     result.put("isSuccess", Boolean.valueOf(false));
     Map<String, Object> payContext = new HashMap<>();

     payContext.put("parameters", jsonParam);
     if (!verifyAliRefundParams(payContext)) {
       return ResultUtil.createFailMap("查询商户信息失败", RetEnum.RET_BIZ_PAY_NOTIFY_VERIFY_FAIL.getMessage());
     }
     _log.info("{}验证支付通知数据及签名通过", new Object[] { logPrefix });






     RefundOrder refundOrder = (RefundOrder)payContext.get("refundOrder");


















     _log.info("###################当前payOrderId{}", new Object[] { refundOrder.getRefundOrderId() });
     doNotify(refundOrder, true);
     _log.info("====== 完成处理支付宝支付回调通知 ======", new Object[0]);
     return ResultUtil.createSuccessMap("success");
   }


   public Map doWxRefundNotify(JSONObject jsonParam) {
     String logPrefix = "【处理微信支付回调】";
     _log.info("====== 开始处理微信支付回调通知 ======", new Object[0]);
     Map<String, Object> resultMap = new HashMap<>();











































     return null;
   }


   @ApiOperation("银联回调通知")
   public Map doUnionRefundNotify(JSONObject jsonParam) throws NoSuchMethodException {
     String logPrefix = "【处理银联支付回调】";
     _log.info("====== 开始处理银联支付回调通知 ======", new Object[0]);
     Map<String, Object> resultMap = new HashMap<>();
     Map<String, Object> payContext = new HashMap<>();

     payContext.put("parameters", jsonParam);
     if (!verifyUnionRefundParams(payContext)) {
       return ResultUtil.createFailMap("银联回调参数校验异常", RetEnum.RET_BIZ_PAY_NOTIFY_VERIFY_FAIL);
     }
     _log.info("{}验证银联通知知数据及签名通过", new Object[] { logPrefix });
     String trade_status = jsonParam.get("respCode").toString();



     RefundOrder refundOrder = (RefundOrder)payContext.get("refundOrder");


















     doNotify(refundOrder, true);
     _log.info("====== 完成处理银联支付回调通知 ======", new Object[0]);
     return ResultUtil.createSuccessMap("Success!");
   }


   public Map sendBizRefundNotify(RefundOrder jsonParam) {
     String refundOrderId = jsonParam.getRefundOrderId();
     RefundOrder refundOrder = this.baseSelectRefundOrder.baseSelectRefundOrder(refundOrderId);
     if (refundOrder == null) {
       return ResultUtil.createFailMap("退款订单不存在", RetEnum.RET_BIZ_DATA_NOT_EXISTS);
     }

     try {
       doNotify(refundOrder, false);
     } catch (Exception e) {
       return ResultUtil.createFailMap("退款订单不存在", RetEnum.RET_UNKNOWN_ERROR);
     }
     return ResultUtil.createSuccessMap("SUCCESS");
   }






   public boolean verifyAliRefundParams(Map<String, Object> payContext) throws NoSuchMethodException {
     Map<String, String> params = (Map<String, String>)payContext.get("parameters");
     String out_trade_no = params.get("out_trade_no");
     String total_amount = params.get("total_amount");
     if (StringUtils.isBlank(out_trade_no)) {
       _log.error("AliPay Notify parameter out_trade_no is empty. out_trade_no={}", new Object[] { out_trade_no });
       payContext.put("retMsg", "out_trade_no is empty");
       return false;
     }
     if (StringUtils.isBlank(total_amount)) {
       _log.error("AliPay Notify parameter total_amount is empty. total_fee={}", new Object[] { total_amount });
       payContext.put("retMsg", "total_amount is empty");
       return false;
     }


     String payOrderId = out_trade_no;
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);
     if (payOrder == null) {
       _log.error("Can't found payOrder form db. payOrderId={}, ", new Object[] { payOrderId });
       payContext.put("retMsg", "Can't found payOrder");
       return false;
     }

     String mchId = payOrder.getMchId();
     String channelId = payOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     if (payChannelForRuntime == null) {
       _log.error("Can't found payChannel form db. mchId={} channelId={}, ", new Object[] { payOrderId, mchId, channelId });
       payContext.put("retMsg", "Can't found payChannel");
       return false;
     }
     boolean verify_result = false;
     try {
       verify_result = AlipaySignature.rsaCheckV1(params, this.alipayConfig.init(payChannelForRuntime.getParam()).getAlipay_public_key(), AlipayConfig.CHARSET, "RSA2");
     } catch (AlipayApiException e) {
       _log.error((Throwable)e, "AlipaySignature.rsaCheckV1 error");
     }


     if (!verify_result) {
       String errorMessage = "rsaCheckV1 failed.";
       _log.error("AliPay Notify parameter {}", new Object[] { errorMessage });
       payContext.put("retMsg", errorMessage);
       return false;
     }


     long aliPayAmt = (new BigDecimal(total_amount)).movePointRight(2).longValue();
     long dbPayAmt = payOrder.getAmount().longValue();
     if (dbPayAmt != aliPayAmt) {
       _log.error("db payOrder record payPrice not equals total_amount. total_amount={},payOrderId={}", new Object[] { total_amount, payOrderId });
       payContext.put("retMsg", "");
       return false;
     }
     payContext.put("payOrder", payOrder);
     return true;
   }





   public boolean verifyWxRefundParams(Map<String, Object> payContext) throws NoSuchMethodException {
     WxPayOrderNotifyResult params = (WxPayOrderNotifyResult)payContext.get("parameters");


     if (!"SUCCESS".equalsIgnoreCase(params.getResultCode()) ||
       !"SUCCESS".equalsIgnoreCase(params.getResultCode())) {
       _log.error("returnCode={},resultCode={},errCode={},errCodeDes={}", new Object[] { params.getReturnCode(), params.getResultCode(), params.getErrCode(), params.getErrCodeDes() });
       payContext.put("retMsg", "notify data failed");
       return false;
     }

     Integer total_fee = params.getTotalFee();
     String out_trade_no = params.getOutTradeNo();


     String payOrderId = out_trade_no;
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);
     if (payOrder == null) {
       _log.error("Can't found payOrder form db. payOrderId={}, ", new Object[] { payOrderId });
       payContext.put("retMsg", "Can't found payOrder");
       return false;
     }


     String mchId = payOrder.getMchId();
     String channelId = payOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     if (payChannelForRuntime == null) {
       _log.error("Can't found payChannel form db. mchId={} channelId={}, ", new Object[] { payOrderId, mchId, channelId });
       payContext.put("retMsg", "Can't found payChannel");
       return false;
     }
     payContext.put("wxPayConfig", WxPayUtil.getWxPayConfig(payChannelForRuntime.getParam()));


     long wxPayAmt = (new BigDecimal(total_fee.intValue())).longValue();
     long dbPayAmt = payOrder.getAmount().longValue();
     if (dbPayAmt != wxPayAmt) {
       _log.error("db payOrder record payPrice not equals total_fee. total_fee={},payOrderId={}", new Object[] { total_fee, payOrderId });
       payContext.put("retMsg", "total_fee is not the same");
       return false;
     }

     payContext.put("payOrder", payOrder);
     return true;
   }





   public boolean verifyUnionRefundParams(Map<String, Object> payContext) throws NoSuchMethodException {
     Map<String, String> params = (Map<String, String>)payContext.get("parameters");
     String orderId = params.get("orderId");
     String settleAmt = params.get("settleAmt");
     if (StringUtils.isBlank(orderId)) {
       _log.error("unionPay Notify parameter orderId is empty. orderId={}", new Object[] { orderId });
       payContext.put("retMsg", "out_trade_no is empty");
       return false;
     }
     if (StringUtils.isBlank(settleAmt)) {
       _log.error("unionPay Notify parameter settleAmt is empty. total_fee={}", new Object[] { settleAmt });
       payContext.put("retMsg", "total_amount is empty");
       return false;
     }


     String base64 = params.get("reqReserved");
     _log.error("透传参数base64={}", new Object[] { base64 });
     String reqReserved = "null";
     reqReserved = Base64Util.decodeData(base64);
     _log.error("透传参数-decode reqReserved={}", new Object[] { reqReserved });

     JSONObject objPayOrder = JSON.parseObject(reqReserved);
     String payOrderId = objPayOrder.getString("payOrderId");
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);
     if (payOrder == null) {
       _log.error("Can't found payOrder form db. payOrderId={}, ", new Object[] { payOrderId });
       payContext.put("retMsg", "Can't found payOrder");
       return false;
     }

     String mchId = payOrder.getMchId();
     String channelId = payOrder.getChannelId();
     PayChannelForRuntime payChannelForRuntime = this.payChannelForPayService.getMchPayChannel(mchId, channelId);
     if (payChannelForRuntime == null) {
       _log.error("Can't found payChannel form db. mchId={} channelId={}, ", new Object[] { payOrderId, mchId, channelId });
       payContext.put("retMsg", "Can't found payChannel");
       return false;
     }


     long unionPayAmt = (new BigDecimal(settleAmt)).longValue();
     long dbPayAmt = payOrder.getAmount().longValue();
     if (dbPayAmt != unionPayAmt) {
       _log.error("db payOrder record payPrice not equals settleAmt. settleAmt={},payOrderId={}", new Object[] { settleAmt, payOrderId });
       payContext.put("retMsg", "settleAmt is not the same");
       return false;
     }
     payContext.put("payOrder", payOrder);
     return true;
   }

   public String handleAliRefundNotify(JSONObject params) throws NoSuchMethodException {
     Map<String, Object> result = doAliRefundNotify(params);
     Boolean s = (Boolean)result.get("isSuccess");
     if (s.booleanValue()) {
       return "success";
     }
     return "fail";
   }

   public String handleWxRefundNotify(String xmlResult) {
     JSONObject params = new JSONObject();
     params.put("xmlResult", xmlResult);

     Map<String, Object> result = doWxRefundNotify(params);
     return (String)result.get("bizResult");
   }

   public String handleUnionRefundNotify(JSONObject params, HttpServletResponse response) throws NoSuchMethodException {
     _log.info("银联返回参数转换：" + params.toString(), new Object[0]);
     _log.info("银联验证签名结果[成功].", new Object[0]);
     String outtradeno = (String)params.get("orderId");
     String reqReserved = (String)params.get("reqReserved");
     _log.info("处理相关业务逻辑{},{}", new Object[] { outtradeno, reqReserved });

     Map<String, Object> result = doUnionRefundNotify(params);
     Boolean s = (Boolean)result.get("isSuccess");
     if (s.booleanValue()) {
       return "Success!";
     }
     return "fail";
   }
 }





