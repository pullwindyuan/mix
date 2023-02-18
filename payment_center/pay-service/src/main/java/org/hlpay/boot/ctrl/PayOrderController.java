 package org.hlpay.boot.ctrl


 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.Map;
 import java.util.concurrent.TimeUnit;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.PayOrderForCreate;
 import org.hlpay.base.service.BaseNotify4MchPay;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.base.service.impl.PayOrderServiceImpl;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;

 @RestController
 public class PayOrderController
 {
   private final MyLog _log = MyLog.getLog(org.hlpay.boot.ctrl.PayOrderController.class);

   @Autowired
   private PayOrderServiceImpl payOrderService;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Autowired
   private RedisUtil redisUtil;

   @Autowired
   private BaseNotify4MchPay baseNotify4MchPay;

   private static String extraBaseStr = "{\n  \"productId\": \" \",\n  \"openId\": \" \",\n  \"sceneInfo\": {\n    \"h5_info\": {\n      \"type\": \"Wap\",\n      \"wap_url\": \" \",\n      \"wap_name\": \"物连家美\"\n    }\n  }\n}";










   private static JSONObject extraJSON = JSONObject.parseObject(extraBaseStr);









   @RequestMapping({"/pay/create_order"})
   public CommonResult<JSONObject> payOrder(@RequestParam String params) {
     this._log.info("#下单请求#", new Object[0]);

     try {
       return doPayOrder(JSONObject.parseObject(params), true, true, false);
     }
     catch (Exception e) {
       return CommonResult.error(e.getMessage());
     }
   }











   @RequestMapping({"/pay/fastPay"})
   public CommonResult<JSONObject> fastPayOrder(@RequestParam String params) {
     try {
       JSONObject po = JSONObject.parseObject(params);
       return doPayOrder(po, true, true, true);
     } catch (Exception e) {
       this._log.error(e, "");
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "支付中心系统异常", null, null));
     }
   }










   private CommonResult<JSONObject> doPayOrder(JSONObject orderObj, boolean creatOrder, boolean checkSign, boolean isFastPay) {
     String logPrefix = "【开始处理支付】";

     try {
       PayOrderForCreate payOrderForCreate1, currPayOrder = validateParams(orderObj, creatOrder, checkSign);

       PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(currPayOrder.getPayOrderId());
       String channelId = currPayOrder.getChannelId();
       if (payOrder == null) {

         payOrderForCreate1 = currPayOrder;
         this.baseService4PayOrderForCache.baseCreatePayOrder((PayOrder)payOrderForCreate1);
       } else {
         if (payOrderForCreate1.getStatus().byteValue() >= 2) {

           this._log.info("{}当前订单已经支付成功，不可操作", new Object[] { logPrefix });
           JSONObject map = HLPayUtil.makeRetJSONObject("SUCCESS", "", "SUCCESS", null);
           map.put("isSuccess", Boolean.valueOf(true));
           map.put("channelOrderNo", payOrderForCreate1.getMchOrderNo());
           map.put("channelErrCode", "payOrder statue not init");
           map.put("channelErrMsg", "当前订单已经支付成功");

           this.baseNotify4MchPay.doNotify((PayOrder)payOrderForCreate1, true);
           return CommonResult.success((Serializable)map);
         }


         JSONObject extraObj = JSONObject.parseObject(payOrderForCreate1.getExtra());
         if (extraObj.getJSONObject(channelId) != null) {
           return HLPayUtil.makeRetFailResult((Map)extraObj.get(channelId));
         }


         if (!channelId.equals(payOrderForCreate1.getChannelId())) {
           this._log.info("现channelId:{} + 原channelId:{}", new Object[] { channelId, payOrderForCreate1.getChannelId() });
           payOrderForCreate1.setChannelId(channelId);


           this.baseService4PayOrderForCache.baseUpdateChannelId(payOrderForCreate1.getPayOrderId(), channelId);
         }
       }
       CommonResult<JSONObject> commonResult = doPayOrder((PayOrder)payOrderForCreate1, (PayChannel)currPayOrder.getPayChannel(), currPayOrder.getMchInfo(), isFastPay);
       JSONObject data = (JSONObject)commonResult.getData();
       JSONObject extra = JSONObject.parseObject(payOrderForCreate1.getExtra());
       if (commonResult.getCode().intValue() == 200) {
         extra.put(channelId, data);
       } else {
         extra.put("error", data);
       }
       this.baseService4PayOrderForCache.baseUpdateExtra(payOrderForCreate1.getPayOrderId(), channelId, extra.toJSONString());
       return commonResult;
     } catch (Exception e) {
       String msg = e.getMessage();
       this._log.error(e, msg);
       if (msg.contains("IDX_MchId_MchOrderNo")) {
         return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "订单重复", null, PayEnum.ERR_0119));
       }
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", e.getMessage(), null, PayEnum.ERR_0010));
     }
   }

   private CommonResult<JSONObject> doPayOrder(PayOrder payOrder, PayChannel payChannel, MchInfo mchInfo, boolean isFastPay) throws Exception {
     String channelId = payOrder.getChannelId();
     switch (channelId) {
       case "WX_APP":
         return this.payOrderService.doWxPayReq("APP", payOrder, payChannel, mchInfo.getResKey());

       case "WX_JSAPI":
         return this.payOrderService.doWxPayReq("JSAPI", payOrder, payChannel, mchInfo.getResKey());

       case "WX_NATIVE":
         return this.payOrderService.doWxPayReq("NATIVE", payOrder, payChannel, mchInfo.getResKey());

       case "WX_MWEB":
         return this.payOrderService.doWxPayReq("MWEB", payOrder, payChannel, mchInfo.getResKey());

       case "ALIPAY_MOBILE":
       case "ALIPAY_PC":
       case "ALIPAY_WAP":
       case "ALIPAY_QR":
         return this.payOrderService.doAliPayReq(channelId, payOrder, payChannel, mchInfo.getResKey());

       case "UNION_PC":
       case "UNION_WAP":
         return this.payOrderService.doUnionPayReq(channelId, payOrder, mchInfo.getResKey());
     }

     return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "不支持的支付渠道类型[channelId=" + channelId + "]", null, PayEnum.ERR_0120));
   }


   public boolean checkCanBeAccessed(String key) throws Exception {
     String order = this.redisUtil.get(key);
     if (StringUtils.isBlank(order)) {
       this.redisUtil.set(key, "1", 10L, TimeUnit.SECONDS);
       return true;
     }
     throw new Exception("请勿重复提交");
   }


   public void resetCanBeAccessed(String key) {
     this.redisUtil.delete(key);
   }











   private PayOrderForCreate validateParams(JSONObject params, boolean creatOrder, boolean checkSign) throws Exception {
     String mchId = params.getString("mchId");
     String mchOrderNo = params.getString("mchOrderNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     String clientIp = params.getString("clientIp");
     String openIdParams = params.getString("openId");
     JSONObject param2 = params.getJSONObject("param2");
     String notifyUrl = params.getString("notifyUrl");
     String redirectUrl = params.getString("redirectUrl");
     String subject = params.getString("subject");
     String body = params.getString("body");













     if (redirectUrl == null) {
       redirectUrl = "";
     }


     PayChannelForRuntime payChannel = this.payChannelForPayService.selectPayChannel(mchId, channelId);


     if ("WX_MWEB".equalsIgnoreCase(channelId) || "WX_NATIVE"
       .equalsIgnoreCase(channelId)) {
       if (StringUtils.isBlank(clientIp)) {
         String errorMessage = "request params[clientIp] not found.";
         throw new Exception(errorMessage);
       }
       payChannel.setProductId(mchOrderNo);
     } else if ("WX_JSAPI".equalsIgnoreCase(channelId)) {
       URL url; if (StringUtils.isBlank(openIdParams)) {
         String errorMessage = "request params[openId] not found.";
         throw new Exception(errorMessage);
       }


       try {
         url = new URL(redirectUrl);
       } catch (MalformedURLException e) {
         e.printStackTrace();
         url = new URL("https://www.wljiam.com");
       }

       String host = "http://" + url.getHost();

       JSONObject sceneInfo = extraJSON.getJSONObject("sceneInfo");
       JSONObject H5Info = sceneInfo.getJSONObject("h5_info");
       H5Info.put("wap_url,", host);
       sceneInfo.put("h5_info", H5Info);
       payChannel.setOpenId(openIdParams);
       payChannel.setProductId(mchOrderNo);
       payChannel.setSceneInfo(sceneInfo.toJSONString());
     }

     PayOrderForCreate payOrder = this.payOrderService.getPayOrderForCreate(mchId, channelId);


     JSONObject formatParam2 = payOrder.getParam2JSONObject();
     formatParam2.put("userAccount", param2.getString("phone"));
     formatParam2.put("price", param2.getString("totalAmount"));
     formatParam2.put("payAmount", amount);
     formatParam2.put("mchOrderNo", mchOrderNo);
     formatParam2.put("cutOff", param2.getString("discountAmount"));
     Long expire = param2.getLong("normalOrderOvertime");
     if (expire != null) {
       payOrder.setExpireTime(expire);
     } else {

       payOrder.setExpireTime(Long.valueOf(System.currentTimeMillis() + 1800000L));
     }


     payOrder.setPayOrderId(payOrder.getPayChannel().getMchInfo().getExternalId() + "-" + mchOrderNo);
     payOrder.setMchOrderNo(mchOrderNo);
     payOrder.setAmount(Long.valueOf(Long.parseLong(amount)));
     payOrder.setClientIp(clientIp);
     payOrder.setSubject(subject);
     payOrder.setBody(body);
     payOrder.setParam2(formatParam2.toJSONString());
     payOrder.setNotifyUrl(notifyUrl);
     payOrder.setRedirectUrl(redirectUrl);
     payOrder.setOpenId(openIdParams);
     payOrder.setClientIp(clientIp);
     return payOrder;
   }




















   PayChannel commonCheckParams(String mchId, String mchOrderNo, String channelId, String amount, String currency) throws Exception {
     return (PayChannel)this.payChannelForPayService.selectPayChannel(mchId, channelId);
   }
 }





