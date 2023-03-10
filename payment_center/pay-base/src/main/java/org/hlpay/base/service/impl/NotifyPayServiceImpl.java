 package org.hlpay.base.service.impl;
 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.alipay.api.AlipayApiException;
 import com.alipay.api.internal.util.AlipaySignature;
 import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
 import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
 import com.github.binarywang.wxpay.config.WxPayConfig;
 import com.github.binarywang.wxpay.exception.WxPayException;
 import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
 import io.swagger.annotations.ApiOperation;
 import java.math.BigDecimal;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.channel.alipay.AlipayConfig;
 import org.hlpay.base.channel.wechat.WxPayUtil;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.base.model.MchNotifyExample;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.INotifyPayService;
 import org.hlpay.common.enumm.RetEnum;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.ResultUtil;
 import org.hlpay.common.util.StrUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class NotifyPayServiceImpl extends BaseNotifyService implements INotifyPayService {
   private static final MyLog _log = MyLog.getLog(NotifyPayServiceImpl.class);

   @Resource
   private Environment env;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Autowired
   private AlipayConfig alipayConfig;

   @Resource
   private MchNotifyMapper mchNotifyMapper;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Autowired
   private BaseNotify4MchPay baseNotify4MchPay;

   public Map doAliPayNotify(JSONObject jsonParam) throws Exception {
     PayOrder payOrder;
     String logPrefix = "?????????????????????????????????";
     _log.info("====== ??????????????????????????????????????? ======", new Object[0]);
     Map<String, Object> result = new HashMap<>();
     result.put("isSuccess", Boolean.valueOf(false));
     Map<String, Object> payContext = new HashMap<>();

     payContext.put("parameters", jsonParam);
     if (!verifyAliPayParams(payContext)) {
       return ResultUtil.createFailMap("????????????????????????", RetEnum.RET_BIZ_PAY_NOTIFY_VERIFY_FAIL.getMessage());
     }
     _log.info("{}???????????????????????????????????????", new Object[] { logPrefix });
     String trade_status = jsonParam.get("trade_status").toString();
     String trade_no = jsonParam.get("trade_no").toString();

     if (trade_status.equals("TRADE_SUCCESS") || trade_status
       .equals("TRADE_FINISHED")) {

       String gmt_payment = jsonParam.get("gmt_payment").toString();
       Date successTime = DateUtils.str2Date(gmt_payment);
       payOrder = (PayOrder)payContext.get("payOrder");

       byte payStatus = payOrder.getStatus().byteValue();
       if (payStatus != 2 && payStatus != 3) {

         _log.info("{}?????????????????????{}", new Object[] { logPrefix, gmt_payment });

         int updatePayOrderRows = this.baseService4PayOrderForCache.baseUpdateStatus4Success(payOrder.getPayOrderId(), StrUtil.toString(jsonParam.get("trade_no"), null), successTime);
         if (updatePayOrderRows != 1) {
           _log.error("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
           _log.info("{}???????????????????????????{}", new Object[] { logPrefix, "fail" });
           return ResultUtil.createFailMap("????????????????????????", "fail");
         }
         _log.info("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
         payOrder.setPaySuccTime(Long.valueOf(successTime.getTime()));
         payOrder.setStatus(Byte.valueOf((byte)2));
         payOrder.setChannelOrderNo(trade_no);
       }
     } else {

       _log.info("{}????????????trade_status={},??????????????????", new Object[] { logPrefix, trade_status });
       _log.info("{}???????????????????????????{}", new Object[] { logPrefix, "success" });
       return ResultUtil.createSuccessMap("success");
     }
     _log.info("###################??????payOrderId{}", new Object[] { payOrder.getPayOrderId() });

     this.baseNotify4MchPay.doNotify(payOrder, true);
     _log.info("====== ??????????????????????????????????????? ======", new Object[0]);
     return ResultUtil.createSuccessMap("success");
   }


   public Map doWxPayNotify(JSONObject jsonParam) {
     String logPrefix = "??????????????????????????????";
     _log.info("====== ???????????????????????????????????? ======", new Object[0]);
     Map<String, Object> resultMap = new HashMap<>();
     try {
       String xmlResult = jsonParam.get("xmlResult").toString();

       WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
       WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);
       Map<String, Object> payContext = new HashMap<>();
       payContext.put("parameters", result);

       if (!verifyWxPayParams(payContext)) {
         return ResultUtil.createFailMap("????????????????????????", WxPayNotifyResponse.fail((String)payContext.get("retMsg")));
       }
       PayOrder payOrder = (PayOrder)payContext.get("payOrder");
       WxPayConfig wxPayConfig = (WxPayConfig)payContext.get("wxPayConfig");
       wxPayServiceImpl.setConfig(wxPayConfig);

       wxPayServiceImpl.parseOrderNotifyResult(xmlResult);

       byte payStatus = payOrder.getStatus().byteValue();
       if (payStatus != 2 && payStatus != 3) {
         String timeEnd = result.getTimeEnd();
         Date end = DateUtils.str2Date(timeEnd, "yyyyMMddHHmmss");

         int updatePayOrderRows = this.baseService4PayOrderForCache.baseUpdateStatus4Success(payOrder.getPayOrderId(), result.getTransactionId(), end);
         if (updatePayOrderRows != 1) {
           _log.error("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
           return ResultUtil.createFailMap("???????????????????????????", WxPayNotifyResponse.fail("??????????????????"));
         }
         _log.error("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
         payOrder.setPaySuccTime(Long.valueOf(end.getTime()));
         payOrder.setStatus(Byte.valueOf((byte)2));
         payOrder.setChannelOrderNo(result.getTransactionId());
       }


       this.baseNotify4MchPay.doNotify(payOrder, true);
       _log.info("====== ???????????????????????????????????? ======", new Object[0]);
       return ResultUtil.createSuccessMap(WxPayNotifyResponse.success("OK"));
     } catch (WxPayException e) {

       _log.error((Throwable)e, "????????????????????????,????????????");
       _log.info("{}????????????result_code=FAIL", new Object[] { logPrefix });
       _log.info("err_code:", new Object[] { e.getErrCode() });
       _log.info("err_code_des:", new Object[] { e.getErrCodeDes() });
       return ResultUtil.createFailMap("????????????????????????:" + e.getErrCodeDes(), WxPayNotifyResponse.fail(e.getMessage()));
     } catch (Exception e) {
       _log.error(e, "????????????????????????,????????????");
       return ResultUtil.createFailMap("????????????????????????", WxPayNotifyResponse.fail(e.getMessage()));
     }
   }

   @ApiOperation("??????????????????")
   public Map doUnionPayNotify(JSONObject jsonParam) throws Exception {
     PayOrder payOrder;
     String logPrefix = "??????????????????????????????";
     _log.info("====== ???????????????????????????????????? ======", new Object[0]);
     Map<String, Object> resultMap = new HashMap<>();
     Map<String, Object> payContext = new HashMap<>();

     payContext.put("parameters", jsonParam);
     if (!verifyUnionPayParams(payContext)) {
       return ResultUtil.createFailMap("??????????????????????????????", RetEnum.RET_BIZ_PAY_NOTIFY_VERIFY_FAIL);
     }
     _log.info("{}??????????????????????????????????????????", new Object[] { logPrefix });
     String trade_status = jsonParam.get("respCode").toString();

     if (trade_status.equals("00")) {

       payOrder = (PayOrder)payContext.get("payOrder");

       byte payStatus = payOrder.getStatus().byteValue();
       if (payStatus != 2 && payStatus != 3) {


         int updatePayOrderRows = this.baseService4PayOrderForCache.baseUpdateStatus4Success(payOrder.getPayOrderId(), jsonParam.get("queryId").toString(), null);
         if (updatePayOrderRows != 1) {
           _log.error("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
           _log.info("{}????????????????????????{}", new Object[] { logPrefix, "fail" });
           return ResultUtil.createFailMap("??????????????????", "fail");
         }
         _log.info("{}????????????????????????,???payOrderId={},??????payStatus={}??????", new Object[] { logPrefix, payOrder.getPayOrderId(), Byte.valueOf((byte)2) });
         payOrder.setPaySuccTime(Long.valueOf((new Date()).getTime()));
         payOrder.setStatus(Byte.valueOf((byte)2));
       }
     } else {

       _log.info("{}????????????trade_status={},??????????????????", new Object[] { logPrefix, trade_status });
       _log.info("{}????????????????????????{}", new Object[] { logPrefix, "Success!" });
       return ResultUtil.createSuccessMap("Success!");
     }

     this.baseNotify4MchPay.doNotify(payOrder, true);
     _log.info("====== ???????????????????????????????????? ======", new Object[0]);
     return ResultUtil.createSuccessMap("Success!");
   }


   public Map sendBizPayNotify(JSONObject jsonParam) throws NoSuchMethodException {
     Map<String, Object> resultMap = new HashMap<>();
     String payOrderId = jsonParam.get("payOrderId").toString();
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);
     if (payOrder == null) {
       return ResultUtil.createFailMap("???????????????", RetEnum.RET_BIZ_DATA_NOT_EXISTS);
     }

     try {
       this.baseNotify4MchPay.doNotify(payOrder, false);
     } catch (Exception e) {
       return ResultUtil.createFailMap("???????????????", RetEnum.RET_UNKNOWN_ERROR);
     }
     return ResultUtil.createSuccessMap("SUCCESS");
   }






   public boolean verifyAliPayParams(Map<String, Object> payContext) throws NoSuchMethodException {
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





   public boolean verifyWxPayParams(Map<String, Object> payContext) throws NoSuchMethodException {
     WxPayOrderNotifyResult params = (WxPayOrderNotifyResult)payContext.get("parameters");


     if (!"SUCCESS".equalsIgnoreCase(params.getResultCode()) &&
       !"SUCCESS".equalsIgnoreCase(params.getReturnCode())) {
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





   public boolean verifyUnionPayParams(Map<String, Object> payContext) throws NoSuchMethodException {
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
     _log.error("????????????base64={}", new Object[] { base64 });
     String reqReserved = "null";
     reqReserved = Base64Util.decodeData(base64);
     _log.error("????????????-decode reqReserved={}", new Object[] { reqReserved });

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

   public String handleAliPayNotify(JSONObject params) throws Exception {
     Map<String, Object> result = doAliPayNotify(params);
     Boolean s = (Boolean)result.get("isSuccess");
     if (s.booleanValue()) {
       return "success";
     }
     return "fail";
   }

   public String handleWxPayNotify(String xmlResult) {
     JSONObject params = new JSONObject();
     params.put("xmlResult", xmlResult);

     Map<String, Object> result = doWxPayNotify(params);
     return (String)result.get("bizResult");
   }

   public String handleUnionPayNotify(JSONObject params, HttpServletResponse response) throws Exception {
     _log.info("???????????????????????????" + params.toString(), new Object[0]);
     _log.info("????????????????????????[??????].", new Object[0]);
     String outtradeno = (String)params.get("orderId");
     String reqReserved = (String)params.get("reqReserved");
     _log.info("????????????????????????{},{}", new Object[] { outtradeno, reqReserved });

     Map<String, Object> result = doUnionPayNotify(params);
     Boolean s = (Boolean)result.get("isSuccess");
     if (s.booleanValue()) {
       return "Success!";
     }
     return "fail";
   }

   public MchNotify baseSelectMchNotify(String orderId) {
     return this.mchNotifyMapper.selectByPrimaryKey(orderId);
   }

   public int baseInsertMchNotify(String orderId, String mchId, String mchOrderNo, String orderType, String notifyUrl) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     mchNotify.setMchId(mchId);
     mchNotify.setMchOrderNo(mchOrderNo);
     mchNotify.setOrderType(orderType);
     mchNotify.setNotifyUrl(notifyUrl);
     return this.mchNotifyMapper.insertSelectiveOnDuplicateKeyUpdate(mchNotify);
   }

   public int baseInsertSyncSettleMchNotify(String orderId, String mchId, String mchOrderNo, String orderType, String notifyUrl) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     mchNotify.setMchId(mchId);
     mchNotify.setMchOrderNo(mchOrderNo);
     mchNotify.setOrderType(orderType);
     mchNotify.setNotifyUrl(notifyUrl);
     return this.mchNotifyMapper.insertSyncSelectiveOnDuplicateKeyUpdate(mchNotify);
   }

   public int baseSyncUpdateMchNotifySuccess(String orderId, String result, byte notifyCount) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setStatus(Byte.valueOf((byte)2));
     mchNotify.setResult(result);
     mchNotify.setNotifyCount(Byte.valueOf(notifyCount));
     mchNotify.setLastNotifyTime(new Date());
     MchNotifyExample example = new MchNotifyExample();
     MchNotifyExample.Criteria criteria = example.createCriteria();
     criteria.andOrderIdEqualTo(orderId);
     List<Byte> values = new LinkedList();
     values.add(Byte.valueOf((byte)1));
     values.add(Byte.valueOf((byte)3));
     criteria.andStatusIn(values);
     return this.mchNotifyMapper.updateSyncByExampleSelective(mchNotify, example);
   }

   public int baseUpdateMchNotifySuccess(String orderId, String result, byte notifyCount) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setStatus(Byte.valueOf((byte)2));
     mchNotify.setResult(result);
     mchNotify.setNotifyCount(Byte.valueOf(notifyCount));
     mchNotify.setLastNotifyTime(new Date());
     MchNotifyExample example = new MchNotifyExample();
     MchNotifyExample.Criteria criteria = example.createCriteria();
     criteria.andOrderIdEqualTo(orderId);
     List<Byte> values = new LinkedList();
     values.add(Byte.valueOf((byte)1));
     values.add(Byte.valueOf((byte)3));
     criteria.andStatusIn(values);
     return this.mchNotifyMapper.updateByExampleSelective(mchNotify, example);
   }

   public int baseUpdateMchNotifyFail(String orderId, String result, byte notifyCount) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setStatus(Byte.valueOf((byte)3));
     mchNotify.setResult(result);
     mchNotify.setNotifyCount(Byte.valueOf(notifyCount));
     mchNotify.setLastNotifyTime(new Date());
     MchNotifyExample example = new MchNotifyExample();
     MchNotifyExample.Criteria criteria = example.createCriteria();
     criteria.andOrderIdEqualTo(orderId);
     List<Byte> values = new LinkedList();
     values.add(Byte.valueOf((byte)1));
     values.add(Byte.valueOf((byte)3));
     return this.mchNotifyMapper.updateByExampleSelective(mchNotify, example);
   }
 }





