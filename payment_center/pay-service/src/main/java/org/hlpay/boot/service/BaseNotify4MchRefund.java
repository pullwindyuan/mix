 package org.hlpay.boot.service


 import com.alibaba.fastjson.JSONObject;
 import java.io.UnsupportedEncodingException;
 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.ObjectUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.base.model.RefundOrder;
 import org.hlpay.base.service.BaseNotifyService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.boot.mq.Mq4MchRefundNotify;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;









 @Component
 public class BaseNotify4MchRefund
   extends BaseNotifyService
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.service.BaseNotify4MchRefund.class);


   @Autowired
   private Mq4MchRefundNotify mq4MchRefundNotify;


   @Resource
   private MchInfoService mchInfoService;



   public String createNotifyUrl(RefundOrder refundOrder, String backType) throws NoSuchMethodException {
     String mchId = refundOrder.getMchId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     String reqKey = mchInfo.getReqKey();
     Map<String, Object> paramMap = new HashMap<>();
     paramMap.put("refundOrderId", ObjectUtils.defaultIfNull(refundOrder.getRefundOrderId(), ""));
     paramMap.put("payOrderId", ObjectUtils.defaultIfNull(refundOrder.getPayOrderId(), ""));
     paramMap.put("channelAccount", ObjectUtils.defaultIfNull(refundOrder.getChannelAccount(), ""));
     paramMap.put("mchOrderNo", ObjectUtils.defaultIfNull(refundOrder.getMchOrderNo(), ""));
     paramMap.put("mchId", ObjectUtils.defaultIfNull(refundOrder.getMchId(), ""));
     paramMap.put("mchRefundNo", ObjectUtils.defaultIfNull(refundOrder.getMchRefundNo(), ""));
     paramMap.put("channelId", ObjectUtils.defaultIfNull(refundOrder.getChannelId(), ""));
     paramMap.put("refundAmount", ObjectUtils.defaultIfNull(refundOrder.getRefundAmount(), ""));
     paramMap.put("currency", ObjectUtils.defaultIfNull(refundOrder.getCurrency(), ""));
     paramMap.put("status", ObjectUtils.defaultIfNull(refundOrder.getStatus(), ""));
     paramMap.put("result", ObjectUtils.defaultIfNull(refundOrder.getResult(), ""));
     paramMap.put("clientIp", ObjectUtils.defaultIfNull(refundOrder.getClientIp(), ""));
     paramMap.put("device", ObjectUtils.defaultIfNull(refundOrder.getDevice(), ""));
     paramMap.put("channelOrderNo", ObjectUtils.defaultIfNull(refundOrder.getChannelOrderNo(), ""));
     paramMap.put("param1", ObjectUtils.defaultIfNull(refundOrder.getParam1(), ""));
     paramMap.put("param2", ObjectUtils.defaultIfNull(refundOrder.getParam2(), ""));
     paramMap.put("refundSuccTime", ObjectUtils.defaultIfNull(Long.valueOf(refundOrder.getRefundSuccTime().getTime()), ""));
     paramMap.put("backType", (backType == null) ? "" : backType);

     String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
     paramMap.put("sign", reqSign);

     try {
       paramMap.put("device", URLEncoder.encode((String)ObjectUtils.defaultIfNull(refundOrder.getDevice(), ""), "UTF-8"));
       paramMap.put("param1", URLEncoder.encode((String)ObjectUtils.defaultIfNull(refundOrder.getParam1(), ""), "UTF-8"));
       paramMap.put("param2", URLEncoder.encode((String)ObjectUtils.defaultIfNull(refundOrder.getParam2(), ""), "UTF-8"));
     } catch (UnsupportedEncodingException e) {
       _log.error("URL Encode exception.", new Object[] { e });
       return null;
     }
     String param = HLPayUtil.genUrlParams(paramMap);
     StringBuffer sb = new StringBuffer();
     sb.append(refundOrder.getNotifyUrl()).append("?").append(param);
     return sb.toString();
   }




   public void doNotify(RefundOrder refundOrder, boolean isFirst) throws NoSuchMethodException {
     _log.info(">>>>>> REFUND开始回调通知业务系统 <<<<<<", new Object[0]);

     Byte payStatus = refundOrder.getStatus();
     if (payStatus.byteValue() >= 2) {
       JSONObject object = createNotifyInfo(refundOrder, isFirst);
       try {
         this.mq4MchRefundNotify.send(object.toJSONString());
       } catch (Exception e) {
         _log.error(e, "refundOrderId=%s,sendMessage error.", new Object[] { ObjectUtils.defaultIfNull(refundOrder.getRefundOrderId(), "") });
       }
     } else {
       _log.info(">>>>>> REFUND回调通知业务系统:退款中，不允许发送异步通知 <<<<<<", new Object[0]);
     }
   }

   public JSONObject createNotifyInfo(RefundOrder refundOrder, boolean isFirst) throws NoSuchMethodException {
     MchNotify mchNotify = baseSelectMchNotify(refundOrder.getRefundOrderId());

     int count = 0;
     JSONObject object = new JSONObject();
     object.put("method", "GET");
     if (mchNotify == null) {
       String url = createNotifyUrl(refundOrder, "2");
       int result = baseInsertMchNotify(refundOrder.getRefundOrderId(), refundOrder.getMchId(), refundOrder.getMchRefundNo(), "3", url);
       _log.info("增加商户通知记录,refundOrderId={},result:{}", new Object[] { refundOrder.getRefundOrderId(), Integer.valueOf(result) });

       object.put("url", url);
     } else {
       count = mchNotify.getNotifyCount().byteValue();
       _log.info("获取商户通知记录,refundOrderId={},result:{}", new Object[] { refundOrder.getRefundOrderId(), mchNotify.getNotifyUrl() });
       object.put("url", mchNotify.getNotifyUrl());
     }
     object.put("statues", refundOrder.getStatus());
     object.put("channelId", refundOrder.getChannelId());
     object.put("orderId", refundOrder.getRefundOrderId());
     object.put("count", Integer.valueOf(count));
     object.put("amount", refundOrder.getRefundAmount());
     object.put("payOrderId", refundOrder.getPayOrderId());
     object.put("mchId", refundOrder.getMchId());
     object.put("Param1", refundOrder.getParam1());
     object.put("Param2", refundOrder.getParam2());
     object.put("createTime", Long.valueOf(System.currentTimeMillis()));
     return object;
   }
 }





