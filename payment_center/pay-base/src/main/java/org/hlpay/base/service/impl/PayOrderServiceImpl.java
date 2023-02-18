 package org.hlpay.base.service.impl;
 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.function.BiConsumer;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.mapper.PayOrderMapper;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.PayChannelForRuntime;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.PayOrderExample;
 import org.hlpay.base.model.PayOrderForCreate;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.INotifyPayService;
 import org.hlpay.base.service.IPayChannel4WxService;
 import org.hlpay.base.service.IPayOrderService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.RunModeService;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.MchTypeEnum;
 import org.hlpay.common.enumm.PayChannelCodeEnum;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.StrUtil;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class PayOrderServiceImpl implements IPayOrderService {
   private static final MyLog _log = MyLog.getLog(PayOrderServiceImpl.class);

   @Autowired
   private INotifyPayService notifyPayService;

   @Resource
   private IPayChannel4WxService payChannel4WxService;

   @Resource
   private IPayChannel4AliService payChannel4AliService;

   @Resource
   private IPayChannel4UnionService payChannel4UnionService;

   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;

   @Resource
   private PayOrderMapper payOrderMapper;

   @Autowired
   private MchInfoService mchInfoService;

   @Autowired
   PayChannelForPayService payChannelForPayService;

   @Autowired
   private RunModeService runModeService;

   private static Map<String, LinkedBlockingQueue<PayOrderForCreate>> payOrderMap;


   public void initPayOrderForCreate() {
     payOrderMap = new HashMap<>(1501);

     List<MchInfo> branches = this.mchInfoService.selectBranchMchInfoList();

     for (MchInfo mchInfo : branches) {
       create(mchInfo);
     }
   }


   public void create(final MchInfo mchInfo) {
     if (!mchInfo.getType().equals(MchTypeEnum.MCH_BRANCH.name())) {
       return;
     }


     Map<String, PayChannelForRuntime> payChannelIdMap = this.payChannelForPayService.getPayChannelIdMapByMchId(mchInfo.getMchId());
     payChannelIdMap.forEach(new BiConsumer<String, PayChannelForRuntime>()
         {


           public void accept(String s, PayChannelForRuntime payChannel)
           {
             for (int i = 0; i < 2000; i++) {

               PayOrderForCreate payOrder = new PayOrderForCreate();

               payOrder.setMchId(mchInfo.getMchId());
               payOrder.setCurrency(payChannel.getCurrency());
               payOrder.setChannelAccount(payChannel.getChannelAccount());
               payOrder.setChannelMchId(payChannel.getChannelMchId());
               payOrder.setChannelId(payChannel.getChannelId());
               payOrder.setChannelCode(PayChannelCodeEnum.valueOf(payChannel.getChannelName()).getCode());
               payOrder.setPayChannel(payChannel);
               payOrder.setSubject("购买商品");
               payOrder.setExtra("{}");
               payOrder.setMchInfo(mchInfo);
               payOrder.setStatus(Byte.valueOf((byte)0));
               payOrder.setDevice(payChannel.getChannelId().split("_")[1]);
               JSONObject param2JSONObject = new JSONObject();

               param2JSONObject.put("mchType", mchInfo.getType());
               param2JSONObject.put("mchName", mchInfo.getName());
               param2JSONObject.put("storeName", mchInfo.getName());
               param2JSONObject.put("mchId", mchInfo.getMchId());
               param2JSONObject.put("channelId", payChannel.getChannelId());
               param2JSONObject.put("channelMchId", payChannel.getMchId());
               param2JSONObject.put("class", PayEnum.BIZ_CLASS_SALE_INCOME.getCode());






               payOrder.setParam2JSONObject(param2JSONObject);
               try {
                 LinkedBlockingQueue<PayOrderForCreate> payOrderQueue = (LinkedBlockingQueue<PayOrderForCreate>)PayOrderServiceImpl.payOrderMap.get(mchInfo.getMchId() + payChannel.getChannelId());
                 if (payOrderQueue == null) {
                   payOrderQueue = new LinkedBlockingQueue<>(2000);
                   PayOrderServiceImpl.payOrderMap.put(mchInfo.getMchId() + payChannel.getChannelId(), payOrderQueue);
                 }
                 payOrderQueue.put(payOrder);
               } catch (InterruptedException e) {
                 e.printStackTrace();
               }
             }
           }
         });
   }


   @RabbitListener(queues = {"queue.notify.mch.init.pay.order"})
   private void addPayOrderForCreate(Message message, Channel channel) throws NoSuchMethodException {
     if (payOrderMap == null) {
       initPayOrderForCreate();
       return;
     }
     String externalId = StrUtil.getJsonString(new String(message.getBody()));
     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(externalId, MchTypeEnum.MCH_BRANCH.name());
     create(mchInfo);
   }

   public PayOrderForCreate getPayOrderForCreate(String mchId, String channelId) {
     if (payOrderMap == null) {
       initPayOrderForCreate();
     }
     LinkedBlockingQueue<PayOrderForCreate> linkedBlockingQueue = payOrderMap.get(mchId + channelId);
     PayOrderForCreate payOrderForCreate = linkedBlockingQueue.poll();
     linkedBlockingQueue.offer(payOrderForCreate);
     return payOrderForCreate;
   }

   public Integer count(PayOrder payOrder) {
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, payOrder);
     return Integer.valueOf(this.payOrderMapper.countByExample(example));
   }

   void setCriteria(PayOrderExample.Criteria criteria, PayOrder payOrder) {
     if (payOrder != null) {
       if (StringUtils.isNotBlank(payOrder.getMchId())) criteria.andMchIdEqualTo(payOrder.getMchId());
       if (StringUtils.isNotBlank(payOrder.getPayOrderId())) criteria.andPayOrderIdEqualTo(payOrder.getPayOrderId());
       if (StringUtils.isNotBlank(payOrder.getMchOrderNo())) criteria.andMchOrderNoEqualTo(payOrder.getMchOrderNo());
       if (StringUtils.isNotBlank(payOrder.getChannelOrderNo())) criteria.andChannelOrderNoEqualTo(payOrder.getChannelOrderNo());
       if (payOrder.getStatus() != null && payOrder.getStatus().byteValue() != -99) criteria.andStatusEqualTo(payOrder.getStatus());

     }
   }

   public Result queryPayOrder(JSONObject queryParam) throws NoSuchMethodException {
     PayOrder order;
     String payOrderId = (String)queryParam.get("payOrderId");
     String queryFromChannel = (String)queryParam.get("queryFromChannel");
     String executeNotify = (String)queryParam.get("executeNotify");



     if (StringUtils.isNotBlank(payOrderId)) {
       order = selectPayOrderByMchIdAndPayOrderId(queryParam);
     } else {
       order = selectPayOrderByMchIdAndMchOrderNo(queryParam);
     }


     if (order != null && order.getChannelOrderNo().equals(order.getMchOrderNo())) {
       queryFromChannel = "false";
     }
     if (order == null) {
       return Result.createFailResult("订单不存在", null);
     }

     boolean isNotify = Boolean.parseBoolean(executeNotify);

     Byte payStatus = order.getStatus();


     if (Boolean.parseBoolean(queryFromChannel)) {
       Result queryResult;
       String channelId = order.getChannelId();

       PayOrder rspOrder = null;
       switch (channelId) {
         case "WX_APP":
         case "WX_JSAPI":
         case "WX_NATIVE":
         case "WX_MWEB":
           queryResult = this.payChannel4WxService.queryReq(order);
           _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
           rspOrder = (PayOrder)queryResult.getBizResult();
           break;
         case "ALIPAY_MOBILE":
         case "ALIPAY_PC":
         case "ALIPAY_WAP":
         case "ALIPAY_QR":
           queryResult = this.payChannel4AliService.queryReq(order);
           _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
           rspOrder = (PayOrder)queryResult.getBizResult();
           break;
         case "UNION_PC":
         case "UNION_WAP":
           queryResult = this.payChannel4UnionService.queryReq(order);
           rspOrder = (PayOrder)queryResult.getBizResult();
           break;
       }


       if (rspOrder != null &&
         2 == rspOrder.getStatus().byteValue())
       {
         order.setStatus(rspOrder.getStatus());
       }






       if (isNotify && payStatus.byteValue() != 2 && payStatus.byteValue() != 3) {
         JSONObject jsonParam = new JSONObject();
         jsonParam.put("payOrderId", order.getPayOrderId());
         if (order.getStatus().byteValue() == 2) {
           this.notifyPayService.sendBizPayNotify(jsonParam);
           _log.info("业务查单完成,并再次发送业务支付通知.发送结果:{}", new Object[] { Integer.valueOf(1) });
         }
       }
     }


     return Result.createSuccessResult(order);
   }

   public CommonResult<JSONObject> doWxPayReq(String tradeType, PayOrder payOrder, PayChannel payChannel, String resKey) {
     JSONObject result = this.payChannel4WxService.doWxPayReq(tradeType, payOrder, payChannel);
     if (!((Boolean)result.get("isSuccess")).booleanValue()) {
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("SUCCESS", "", "FAIL", "0111", "调用微信支付失败"));
     }
     return CommonResult.success((Serializable)result);
   }



   public CommonResult<JSONObject> doAliPayReq(String channelId, PayOrder payOrder, PayChannel payChannel, String resKey) {
     switch (channelId) {
       case "ALIPAY_MOBILE":
         return this.payChannel4AliService.doAliPayMobileReq(payOrder, payChannel);

       case "ALIPAY_PC":
         return this.payChannel4AliService.doAliPayPcReq(payOrder, payChannel);

       case "ALIPAY_WAP":
         return this.payChannel4AliService.doAliPayWapReq(payOrder, payChannel);

       case "ALIPAY_QR":
         return this.payChannel4AliService.doAliPayQrReq(payOrder, payChannel);
     }







     return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "不支持的支付渠道"));
   }





   public CommonResult<JSONObject> doUnionPayReq(String channelId, PayOrder payOrder, String resKey) {
     JSONObject result;
     Map<String, Object> paramMap = new HashMap<>();
     paramMap.put("payOrder", payOrder);


     String rsp = null;

     switch (channelId) {
       case "UNION_PC":
         result = this.payChannel4UnionService.doUnionPayReq(payOrder);
         break;
       case "UNION_WAP":
         result = this.payChannel4UnionService.doUnionPayReq(payOrder);
         break;
       default:
         result = null;
         break;
     }
     if (result == null) {
       return HLPayUtil.makeRetFailResult(HLPayUtil.makeRetMap("FAIL", "", "FAIL", "0111", "调用银联支付失败"));
     }






     return CommonResult.success((Serializable)result);
   }

   public Result queryPayOrderInPublicRunMode(PayOrder payOrder) throws Exception {
     Result queryResult = null;

     if (payOrder != null && payOrder.getChannelOrderNo().equals(payOrder.getMchOrderNo()) &&
       payOrder.getStatus().byteValue() >= 2) {
       return Result.createSuccessResult(payOrder);
     }

     String payOrderId = payOrder.getPayOrderId();
     String mchId = payOrder.getMchId();
     String channelId = payOrder.getChannelId();
     switch (channelId) {
       case "WX_APP":
       case "WX_JSAPI":
       case "WX_NATIVE":
       case "WX_MWEB":
         queryResult = this.payChannel4WxService.queryReq(payOrderId, mchId, channelId);
         _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
         break;
       case "ALIPAY_MOBILE":
       case "ALIPAY_PC":
       case "ALIPAY_WAP":
       case "ALIPAY_QR":
         queryResult = this.payChannel4AliService.queryReq(payOrderId, mchId, channelId);
         _log.info("查询返回结果:{}", new Object[] { JSONObject.toJSONString(queryResult) });
         break;
       case "UNION_PC":
       case "UNION_WAP":
         queryResult = this.payChannel4UnionService.queryReq(payOrderId, mchId, channelId);
         break;
     }


     return queryResult;
   }


   public PayOrder selectPayOrder(JSONObject jsonParam) throws NoSuchMethodException {
     String payOrderId = jsonParam.get("payOrderId").toString();
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);
     if (payOrder == null) {
       _log.warn("根据商户号和支付订单号查询支付订单失败, payOrderId={}", new Object[] { payOrderId });
     }
     return payOrder;
   }


   public PayOrder selectPayOrderByMchIdAndPayOrderId(JSONObject jsonParam) throws NoSuchMethodException {
     String mchId = jsonParam.get("mchId").toString();
     String payOrderId = jsonParam.get("payOrderId").toString();
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrderByMchIdAndPayOrderId(mchId, payOrderId);
     if (payOrder == null) {
       _log.warn("根据商户号和支付订单号查询支付订单失败, mchId={}. payOrderId={}", new Object[] { mchId, payOrderId });
     }
     return payOrder;
   }


   public PayOrder selectPayOrderByMchIdAndMchOrderNo(JSONObject jsonParam) throws NoSuchMethodException {
     String mchId = jsonParam.get("mchId").toString();
     String mchOrderNo = jsonParam.get("mchOrderNo").toString();
     _log.warn("根据商户号和商户订单号查询支付订, mchId={}. mchOrderNo={}", new Object[] { mchId, mchOrderNo });
     PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectByMchIdAndMchOrderNo(mchId, mchOrderNo);
     if (payOrder == null) {
       _log.warn("订单不存在, mchId={}. mchOrderNo={}", new Object[] { mchId, mchOrderNo });
     }
     return payOrder;
   }


   public int updateStatus4Ing(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     String channelOrderNo = jsonParam.get("channelOrderNo").toString();
     return this.baseService4PayOrderForCache.baseUpdateStatus4Ing(payOrderId, channelOrderNo);
   }


   public int updateStatus4Closed(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     return this.baseService4PayOrderForCache.baseUpdateStatus4Closed(payOrderId);
   }


   public int updateStatus4Settled(String payOrderId) {
     int result = this.baseService4PayOrderForCache.baseUpdateStatus4SettleComplete(payOrderId);
     return result;
   }


   public int updateChannelId(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     String channelId = jsonParam.get("channelId").toString();
     return this.baseService4PayOrderForCache.baseUpdateChannelId(payOrderId, channelId);
   }


   public int updateExtra(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     String channelId = jsonParam.get("channelId").toString();
     String extra = jsonParam.get("extra").toString();
     return this.baseService4PayOrderForCache.baseUpdateExtra(payOrderId, channelId, extra);
   }


   public int updateStatus4Success(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();

     return this.baseService4PayOrderForCache.baseUpdateStatus4Success(payOrderId, null, new Date(jsonParam.getLong("paySuccTime").longValue()));
   }


   public int updateStatus4Complete(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     return this.baseService4PayOrderForCache.baseUpdateStatus4Complete(payOrderId);
   }


   public int updateNotify(JSONObject jsonParam) {
     String payOrderId = jsonParam.get("payOrderId").toString();
     Byte count = Byte.valueOf(Byte.parseByte(jsonParam.get("count").toString()));
     return this.baseService4PayOrderForCache.baseUpdateNotify(payOrderId, count.byteValue());
   }

   public List<PayOrder> selectPayOrderByMchOrderNo(String mchOrderNo) {
     return this.baseService4PayOrderForCache.baseSelectPayOrderByMchOrderNo(mchOrderNo);
   }
 }





