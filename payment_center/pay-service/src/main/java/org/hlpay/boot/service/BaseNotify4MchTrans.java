 package org.hlpay.boot.service


 import com.alibaba.fastjson.JSONObject;
 import java.io.UnsupportedEncodingException;
 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.commons.lang3.ObjectUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.service.BaseNotifyService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.boot.mq.Mq4MchTransNotify;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;









 @Component
 public class BaseNotify4MchTrans
   extends BaseNotifyService
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.service.BaseNotify4MchTrans.class);



   @Autowired
   private Mq4MchTransNotify mq4MchTransNotify;


   @Autowired
   MchInfoService mchInfoService;



   public String createNotifyUrl(TransOrder transOrder, String backType) throws NoSuchMethodException {
     String mchId = transOrder.getMchId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     String reqKey = mchInfo.getReqKey();
     Map<String, Object> paramMap = new HashMap<>();
     paramMap.put("transOrderId", ObjectUtils.defaultIfNull(transOrder.getTransOrderId(), ""));
     paramMap.put("mchId", ObjectUtils.defaultIfNull(transOrder.getMchId(), ""));
     paramMap.put("mchOrderNo", ObjectUtils.defaultIfNull(transOrder.getMchTransNo(), ""));
     paramMap.put("channelId", ObjectUtils.defaultIfNull(transOrder.getChannelId(), ""));
     paramMap.put("amount", ObjectUtils.defaultIfNull(transOrder.getAmount(), ""));
     paramMap.put("currency", ObjectUtils.defaultIfNull(transOrder.getCurrency(), ""));
     paramMap.put("status", ObjectUtils.defaultIfNull(transOrder.getStatus(), ""));
     paramMap.put("result", ObjectUtils.defaultIfNull(transOrder.getResult(), ""));
     paramMap.put("clientIp", ObjectUtils.defaultIfNull(transOrder.getClientIp(), ""));
     paramMap.put("device", ObjectUtils.defaultIfNull(transOrder.getDevice(), ""));
     paramMap.put("channelOrderNo", ObjectUtils.defaultIfNull(transOrder.getChannelOrderNo(), ""));
     paramMap.put("param1", ObjectUtils.defaultIfNull(transOrder.getParam1(), ""));
     paramMap.put("param2", ObjectUtils.defaultIfNull(transOrder.getParam2(), ""));
     paramMap.put("transSuccTime", ObjectUtils.defaultIfNull(transOrder.getTransSuccTime(), ""));
     paramMap.put("backType", (backType == null) ? "" : backType);

     String reqSign = PayDigestUtil.getSign(paramMap, reqKey);
     paramMap.put("sign", reqSign);

     try {
       paramMap.put("device", URLEncoder.encode((String)ObjectUtils.defaultIfNull(transOrder.getDevice(), ""), "UTF-8"));
       paramMap.put("param1", URLEncoder.encode((String)ObjectUtils.defaultIfNull(transOrder.getParam1(), ""), "UTF-8"));
       paramMap.put("param2", URLEncoder.encode((String)ObjectUtils.defaultIfNull(transOrder.getParam2(), ""), "UTF-8"));
     } catch (UnsupportedEncodingException e) {
       _log.error("URL Encode exception.", new Object[] { e });
       return null;
     }
     String param = HLPayUtil.genUrlParams(paramMap);
     StringBuffer sb = new StringBuffer();
     sb.append(transOrder.getNotifyUrl()).append("?").append(param);
     return sb.toString();
   }




   public void doNotify(TransOrder transOrder, boolean isFirst) throws NoSuchMethodException {
     _log.info(">>>>>> TRANS开始回调通知业务系统 <<<<<<", new Object[0]);

     JSONObject object = createNotifyInfo(transOrder, isFirst);
     try {
       this.mq4MchTransNotify.send(object.toJSONString());
     } catch (Exception e) {
       _log.error(e, "transOrderId=%s,sendMessage error.", new Object[] { ObjectUtils.defaultIfNull(transOrder.getTransOrderId(), "") });
     }
     _log.info(">>>>>> TRANS回调通知业务系统完成 <<<<<<", new Object[0]);
   }

   public JSONObject createNotifyInfo(TransOrder transOrder, boolean isFirst) throws NoSuchMethodException {
     String url = createNotifyUrl(transOrder, "2");
     if (isFirst) {
       int result = baseInsertMchNotify(transOrder.getTransOrderId(), transOrder.getMchId(), transOrder.getMchTransNo(), "2", url);
       _log.info("增加商户通知记录,orderId={},result:{}", new Object[] { transOrder.getTransOrderId(), Integer.valueOf(result) });
     }
     int count = 0;
     if (!isFirst) {
       MchNotify mchNotify = baseSelectMchNotify(transOrder.getTransOrderId());
       if (mchNotify != null) count = mchNotify.getNotifyCount().byteValue();
     }
     JSONObject object = new JSONObject();
     object.put("statues", transOrder.getStatus());
     object.put("channelId", transOrder.getChannelId());
     object.put("orderId", transOrder.getTransOrderId());
     object.put("count", Integer.valueOf(count));
     object.put("amount", transOrder.getAmount());
     object.put("mchId", transOrder.getMchId());
     object.put("Param1", transOrder.getParam1());
     object.put("createTime", Long.valueOf(System.currentTimeMillis()));
     return object;
   }
 }





