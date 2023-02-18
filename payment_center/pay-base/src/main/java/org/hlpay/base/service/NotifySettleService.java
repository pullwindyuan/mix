 package org.hlpay.base.service;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.ObjectUtils;
 import org.hlpay.base.mapper.MchNotifyMapper;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.base.model.MchNotifyExample;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.util.HttpClient;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.PayDigestUtil;
 import org.springframework.amqp.AmqpException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class NotifySettleService
   extends BaseNotifyService {
   private static final MyLog _log = MyLog.getLog(NotifySettleService.class);

   @Resource
   private Environment env;

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   @Resource
   private MchInfoService mchInfoService;

   @Resource
   private MchNotifyMapper mchNotifyMapper;

   @Autowired
   private BaseService4PayOrder baseService4PayOrder;

   private static List<PayOrder> syncFailedOrders = new ArrayList<>();

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










   public String createInnerSettleNotify(String payOrderId, String mchOrderNo, String agencyId, String mchId, String mchName) throws NoSuchMethodException {
     MchInfo agency = this.mchInfoService.selectMchInfo(agencyId);
     if (agency == null) {
       return "do not found the mchInfo";
     }
     MchInfo platform = this.mchInfoService.getRootMchInfo(agency);
     String reqKey = platform.getReqKey();
     JSONObject paramMap = new JSONObject();
     paramMap.put("payOrderId", ObjectUtils.defaultIfNull(payOrderId, ""));
     paramMap.put("mchId", ObjectUtils.defaultIfNull(mchId, ""));
     paramMap.put("mchOrderNo", ObjectUtils.defaultIfNull(mchOrderNo, ""));
     paramMap.put("mchName", ObjectUtils.defaultIfNull(mchName, ""));

     String reqSign = PayDigestUtil.getSign((Map)paramMap, reqKey);
     paramMap.put("sign", reqSign);
     return paramMap.toJSONString();
   }

   public void sendSyncPayOrderForSettle(String payOrderId) throws NoSuchMethodException {
     PayOrder payOrder = this.baseService4PayOrder.baseSelectPayOrder(payOrderId);
     _log.info("同步订单信息：" + JSONObject.toJSONString(payOrder), new Object[0]);
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(payOrder.getMchId());
     payOrder.setMchName(mchInfo.getName());
     sendSyncPayOrderForSettle(payOrder);
   }

   public void sendSyncPayOrderForSettle(PayOrder payOrder) throws NoSuchMethodException {
     String securityUrl = this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl") + "/api/settle/syncPay";
     _log.info("同步记账URL：" + securityUrl, new Object[0]);
     String payOrderId = payOrder.getPayOrderId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(payOrder.getMchId());
     if (mchInfo == null) {
       return;
     }
     Map<String, Object> settleSync = (Map<String, Object>)JSONObject.toJSON(payOrder);
     MchInfo platform = this.mchInfoService.getRootMchInfo(mchInfo.getMchId());

     String reqSign = PayDigestUtil.getSign(settleSync, platform.getReqKey(), new String[] { "sign", "clientIp", "device", "subject", "body", "extra", "errCode", "errMsg", "param1", "param2", "notifyCount", "lastNotifyTime", "expireTime", "createTime", "updateTime" });














     settleSync.put("sign", reqSign);
     CommonResult commonResult = null;

     try {
       _log.info("订单记账同步请求url：", new Object[] { securityUrl });
       String syncResult = HttpClient.post(securityUrl, settleSync);
       _log.info("订单记账同步返回结果：" + syncResult, new Object[0]);
       try {
         commonResult = (CommonResult)JSON.parseObject(syncResult, CommonResult.class);
       } catch (Exception e) {
         e.printStackTrace();
         this.mq4MchNotify.send("queue.notify.mch.settle.send.sync", JSON.toJSONString(payOrder), 60000L);
         _log.info("网络异常：增加订单记账同步失败记录失败,orderId=" + payOrderId, new Object[0]);
       }
       PayOrder failedOrder = this.baseService4PayOrder.baseSelectSyncFailedPayOrder(payOrder.getPayOrderId());
       int result = 0;
       if (commonResult == null) {
         if (failedOrder == null) {
           result = this.baseService4PayOrder.baseCreateSyncFailedPayOrder(payOrder);
         }
         this.mq4MchNotify.send("queue.notify.mch.settle.send.sync", JSON.toJSONString(payOrder), 60000L);
         _log.info("订单记账同步失败:" + JSONObject.toJSONString(Integer.valueOf(result)), new Object[0]);
         return;
       }
       if (commonResult.getCode().intValue() == 200) {
         if (failedOrder != null) {
           this.baseService4PayOrder.deleteSyncFailedByPrimaryKey(payOrderId);
         }
         System.out.println("同步记账结果：" + syncResult);
       } else {
         if (failedOrder == null) {
           result = this.baseService4PayOrder.baseCreateSyncFailedPayOrder(payOrder);
         }
         syncFailedOrders = this.baseService4PayOrder.baseSelectSyncFailedPayOrder();

         if (syncFailedOrders.size() > 0) {
           this.mq4MchNotify.send("queue.notify.mch.settle.batch.send.sync", "batchSync");
         } else {
           syncFailedOrders = this.baseService4PayOrder.baseSelectSyncFailedPayOrder();
         }
         _log.info("订单记账同步失败:" + JSONObject.toJSONString(Integer.valueOf(result)), new Object[0]);
       }
     } catch (AmqpException e) {
       e.printStackTrace();
       this.mq4MchNotify.send("queue.notify.mch.settle.send.sync", JSON.toJSONString(payOrder), 180000L);
       _log.info("发送同步失败:" + payOrderId, new Object[0]);
     }
   }



   public void batchSyncFailedRecords() throws NoSuchMethodException {
     if (syncFailedOrders.size() > 0) {
       for (PayOrder p : syncFailedOrders) {
         sendSyncPayOrderForSettle(p);
       }
       syncFailedOrders = new ArrayList<>();
     }
   }
 }





