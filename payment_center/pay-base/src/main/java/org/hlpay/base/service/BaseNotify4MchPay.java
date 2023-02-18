 package org.hlpay.base.service;

 import com.alibaba.fastjson.JSONObject;
 import java.io.UnsupportedEncodingException;
 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.ObjectUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 @Component
 public class BaseNotify4MchPay
   extends BaseNotifyService
 {
   private static final MyLog _log = MyLog.getLog(BaseNotify4MchPay.class);



   @Autowired
   private Mq4MchNotify mq4MchNotify;


   @Resource
   private MchInfoService mchInfoService;



   public String createNotifyUrl(PayOrder payOrder, String backType) throws NoSuchMethodException {
     String mchId = payOrder.getMchId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     String reqKey = mchInfo.getReqKey();
     Map<String, Object> paramMap = new HashMap<>();
     paramMap.put("payOrderId", ObjectUtils.defaultIfNull(payOrder.getPayOrderId(), ""));
     paramMap.put("mchId", ObjectUtils.defaultIfNull(payOrder.getMchId(), ""));
     paramMap.put("mchOrderNo", ObjectUtils.defaultIfNull(payOrder.getMchOrderNo(), ""));
     paramMap.put("channelId", ObjectUtils.defaultIfNull(payOrder.getChannelId(), ""));
     paramMap.put("channelAccount", ObjectUtils.defaultIfNull(payOrder.getChannelAccount(), ""));
     paramMap.put("amount", ObjectUtils.defaultIfNull(payOrder.getAmount(), ""));
     paramMap.put("currency", ObjectUtils.defaultIfNull(payOrder.getCurrency(), ""));
     paramMap.put("status", ObjectUtils.defaultIfNull(payOrder.getStatus(), ""));
     paramMap.put("clientIp", ObjectUtils.defaultIfNull(payOrder.getClientIp(), ""));
     paramMap.put("device", ObjectUtils.defaultIfNull(payOrder.getDevice(), ""));
     paramMap.put("subject", ObjectUtils.defaultIfNull(payOrder.getSubject(), ""));
     paramMap.put("channelOrderNo", ObjectUtils.defaultIfNull(payOrder.getChannelOrderNo(), ""));
     paramMap.put("param1", ObjectUtils.defaultIfNull(payOrder.getParam1(), ""));
     paramMap.put("param2", ObjectUtils.defaultIfNull(payOrder.getParam2(), ""));
     paramMap.put("paySuccTime", ObjectUtils.defaultIfNull(payOrder.getPaySuccTime(), ""));
     paramMap.put("backType", ObjectUtils.defaultIfNull(backType, ""));

     String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
     paramMap.put("sign", reqSign);

     try {
       paramMap.put("device", URLEncoder.encode((String)ObjectUtils.defaultIfNull(payOrder.getDevice(), ""), "UTF-8"));
       paramMap.put("subject", URLEncoder.encode((String)ObjectUtils.defaultIfNull(payOrder.getSubject(), ""), "UTF-8"));
       paramMap.put("param1", URLEncoder.encode((String)ObjectUtils.defaultIfNull(payOrder.getParam1(), ""), "UTF-8"));
       paramMap.put("param2", URLEncoder.encode((String)ObjectUtils.defaultIfNull(payOrder.getParam2(), ""), "UTF-8"));
     } catch (UnsupportedEncodingException e) {
       _log.error("URL Encode exception.", new Object[] { e });
       return null;
     }
     String param = HLPayUtil.genUrlParams(paramMap);
     StringBuffer sb = new StringBuffer();
     String notifyUrl = payOrder.getNotifyUrl();
     notifyUrl.replace("http://", "/");
     sb.append(notifyUrl).append("?").append(param);
     return sb.toString();
   }




   public void doNotify(PayOrder payOrder, boolean isFirst) throws Exception {
     _log.info(">>>>>> PAY开始回调通知业务系统 <<<<<<", new Object[0]);

     Byte payStatus = payOrder.getStatus();
     _log.info(">>>>>> PAY开始回调通知业务系统 <<<<<<" + payOrder.getParam1(), new Object[0]);

     if (payStatus.byteValue() >= 2) {
       JSONObject object = createNotifyInfo(payOrder, isFirst);
       try {
         this.mq4MchNotify.send("queue.notify.mch.pay", object.toJSONString());

         String payOrderStr = JSONObject.toJSONString(payOrder);
         this.mq4MchNotify.send("queue.notify.mch.settle", payOrderStr);
       } catch (Exception e) {
         _log.error(e, "payOrderId=%s,sendMessage error.", new Object[] { ObjectUtils.defaultIfNull(payOrder.getPayOrderId(), "") });
       }
       _log.info(">>>>>> PAY回调通知业务系统完成 <<<<<<", new Object[0]);
     } else {
       _log.info(">>>>>> PAY回调通知业务系统:未支付成功，不允许发送异步通知 <<<<<<", new Object[0]);
     }
   }

   public JSONObject createNotifyInfo(PayOrder payOrder, boolean isFirst) throws NoSuchMethodException {
     String url = createNotifyUrl(payOrder, "2");
     if (isFirst) {
       int result = baseInsertMchNotify(payOrder.getPayOrderId(), payOrder.getMchId(), payOrder.getMchOrderNo(), "1", url);
       _log.info("增加商户通知记录,orderId={},result:{}", new Object[] { payOrder.getPayOrderId(), Integer.valueOf(result) });
     }
     int count = 0;
     JSONObject object = new JSONObject();
     object.put("method", "GET");
     if (!isFirst) {
       MchNotify mchNotify = baseSelectMchNotify(payOrder.getPayOrderId());
       if (mchNotify != null) {

         _log.info("获取商户通知记录,orderId={},result:{}", new Object[] { payOrder.getPayOrderId(), mchNotify.getNotifyUrl() });

         url = mchNotify.getNotifyUrl();
       } else {
         int result = baseInsertMchNotify(payOrder.getPayOrderId(), payOrder.getMchId(), payOrder.getMchOrderNo(), "1", url);
         _log.info("没有通知记录，增加商户通知记录,orderId={},result:{}", new Object[] { payOrder.getPayOrderId(), Integer.valueOf(result) });
       }
     }
     object.put("url", url);
     object.put("statues", payOrder.getStatus());
     object.put("channelId", payOrder.getChannelId());
     object.put("payOrderId", payOrder.getPayOrderId());
     object.put("orderId", payOrder.getPayOrderId());
     object.put("count", Integer.valueOf(count));
     object.put("amount", payOrder.getAmount());
     object.put("mchId", payOrder.getMchId());
     object.put("Param1", payOrder.getParam1());
     object.put("Param2", payOrder.getParam2());
     object.put("createTime", payOrder.getCreateTime());
     object.put("paySuccTime", payOrder.getPaySuccTime());
     return object;
   }
 }

