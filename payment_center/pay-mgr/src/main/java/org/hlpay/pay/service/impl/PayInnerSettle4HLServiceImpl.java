/*      */ package org.hlpay.pay.service.impl

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import com.rabbitmq.client.Channel;
 import java.math.BigDecimal;
 import java.time.LocalDateTime;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.bo.FixSettleBo;
 import org.hlpay.base.bo.SettleResult;
 import org.hlpay.base.bo.TransOrderForSettleBo;
 import org.hlpay.base.model.CheckSumPayOrder;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SaCardExample;
 import org.hlpay.base.model.SaDealRecord;
 import org.hlpay.base.model.SaScoreFlow;
 import org.hlpay.base.model.SaSettleCard;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.CardDataTypeEnum;
 import org.hlpay.common.enumm.CurrencyTypeEnum;
 import org.hlpay.common.enumm.DealTypeEnum;
 import org.hlpay.common.enumm.PayEnum;
 import org.hlpay.common.enumm.ResultEnum;
 import org.hlpay.common.enumm.RetEnum;
 import org.hlpay.common.enumm.RunModeEnum;
 import org.hlpay.common.enumm.SettleStatusEnum;
 import org.hlpay.common.util.BeanUtil;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.UnionIdUtil;
 import org.hlpay.pay.service.IPayInnerSettle4HLService;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Primary;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 @Primary
 public class PayInnerSettle4HLServiceImpl implements IPayInnerSettle4HLService {
   private static final MyLog _log = MyLog.getLog(org.hlpay.pay.service.impl.PayInnerSettle4HLServiceImpl.class);

   @Autowired
   private SaCardServiceImpl saCardService;

   @Autowired
   private MchInfoService mchInfoService;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Autowired
   private BaseService4TransOrder baseService4TransOrder;

   @Autowired
   private DealCore dealCore;

   @Autowired
   private ITransOrderService transOrderService;

   @Autowired
   private CardService cardService;

   @Autowired
   private SaDealRecordServiceImpl saDealRecordService;

   @Autowired
   private SaScoreFlowServiceRealImpl saScoreFlowRealService;

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   @Autowired
   private PlatformService platformService;
   @Resource
   private PayOrderMapper payOrderMapper;
   @Autowired
   private BaseService4PayOrder baseService4PayOrder;
   @Resource
   private Environment env;
   @Autowired
   private RedisUtil redisUtil;
   @Autowired
   private RunModeService runModeService;

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult cancel(String orderId, String mchId) throws NoSuchMethodException {
     JSONObject paramMap = new JSONObject();
     paramMap.put("mchId", mchId);
     paramMap.put("payOrderId", orderId);
     paramMap.put("executeNotify", "false");
     paramMap.put("queryFromChannel", "true");

     PayOrder payOrder = this.baseService4PayOrder.baseSelectPayOrder(orderId);
     if (payOrder == null) {
       return CommonResult.error(RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage());
     }
     try {
       String channelMchId;
       int payMode;
       MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
       JSONObject param2 = JSONObject.parseObject(payOrder.getParam2());
       int currRunMode = this.runModeService.getRunModeCode();

       if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
         payMode = currRunMode;
         channelMchId = "1";
       } else {
         channelMchId = param2.getString("channelMchId");
         if (mchId.startsWith(channelMchId)) {

           payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
         } else {
           payMode = RunModeEnum.PUBLIC.getCode().intValue();
         }
       }
       SaCard card = this.cardService.getSettleCard(mchInfo, payOrder.getCurrency().toUpperCase(), DateUtils.getLocalDateTime(payOrder.getPaySuccTime()), payMode, channelMchId);
       TransOrder transOrder = new TransOrder();
       transOrder.setCardNum(card.getCardNumber());
       transOrder.setMchId(payOrder.getMchId());
       transOrder.setAmount(payOrder.getAmount());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(payOrder.getCurrency().toUpperCase()).name() + "_CANCEL");
       transOrder.setChannelMchId(payOrder.getMchId());
       transOrder.setChannelOrderNo(payOrder.getPayOrderId());
       transOrder.setChannelUser(payOrder.getMchId());
       transOrder.setCurrency(payOrder.getCurrency());


       param2.remove("detail");

       transOrder.setExtra(param2.toJSONString());
       transOrder.setMchTransNo(payOrder.getMchOrderNo());
       transOrder.setChannelOrderNo(payOrder.getChannelOrderNo());
       transOrder.setTransOrderId(payOrder.getPayOrderId());
       transOrder.setNotifyUrl(payOrder.getNotifyUrl());
       transOrder.setTransSuccTime(new Date(payOrder.getPaySuccTime().longValue()));

       CommonResult result = doInnerSettleCreateReq(transOrder);
       return result;
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error(RetEnum.RET_UNKNOWN_ERROR.getMessage());
     }
   }


   @Transactional(rollbackFor = {Exception.class})
   public CommonResult thirdPayToIncomingOfMch(String orderId, String mchId, String channelId) throws NoSuchMethodException {
     return thirdPayToIncomingOfMch(orderId, mchId, channelId, false);
   } @Transactional(rollbackFor = {Exception.class})
   public CommonResult thirdPayToIncomingOfMch(String orderId, String mchId, String channelId, boolean forceSettle) throws NoSuchMethodException {
     try {
       String channelMchId;
       int payMode;
       Date date;
       LocalDateTime localPaySuccTime;
       PayOrder payOrder = this.baseService4PayOrder.baseSelectPayOrder(orderId);
       if (payOrder == null) {
         return CommonResult.error("支付订单不存在：" + orderId);
       }
       MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
       JSONObject param2 = JSONObject.parseObject(payOrder.getParam2());

       int currRunMode = this.runModeService.getRunModeCode();

       if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
         payMode = currRunMode;
         channelMchId = "1";
       } else {
         channelMchId = param2.getString("channelMchId");
         if (mchId.startsWith(channelMchId)) {

           payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
         } else {
           payMode = RunModeEnum.PUBLIC.getCode().intValue();
         }
       }




       if (payOrder.getPaySuccTime() == null) {
         date = payOrder.getCreateTime();
         localPaySuccTime = DateUtils.getLocalDateTime(payOrder.getCreateTime());
       } else {
         date = new Date(payOrder.getPaySuccTime().longValue());
         localPaySuccTime = DateUtils.getLocalDateTime(date);
       }

       if (localPaySuccTime == null) {
         date = payOrder.getCreateTime();
         localPaySuccTime = DateUtils.getLocalDateTime(payOrder.getCreateTime());
       }
       SaCard card = this.cardService.getSettleCard(mchInfo, payOrder.getCurrency().toUpperCase(), localPaySuccTime, payMode, channelMchId);
       TransOrder transOrder = new TransOrder();
       transOrder.setCardNum(card.getCardNumber());
       transOrder.setMchId(mchId);
       transOrder.setAmount(payOrder.getAmount());

       transOrder.setSettlePayChannelId(payOrder.getChannelId());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(payOrder.getCurrency().toUpperCase()).name() + "_RECHARGE");
       transOrder.setChannelMchId(payOrder.getMchId());
       transOrder.setChannelOrderNo(payOrder.getPayOrderId());
       transOrder.setChannelUser(payOrder.getMchId());
       transOrder.setCurrency(payOrder.getCurrency());

       if (param2 != null) {
         param2.remove("detail");
         transOrder.setExtra(param2.toJSONString());
       }
       transOrder.setMchTransNo(payOrder.getMchOrderNo());
       transOrder.setChannelOrderNo(payOrder.getChannelOrderNo());
       transOrder.setTransOrderId(payOrder.getPayOrderId());
       transOrder.setNotifyUrl(payOrder.getNotifyUrl());
       try {
         transOrder.setTransSuccTime(date);
       } catch (Exception e) {
         e.printStackTrace();
         transOrder.setTransSuccTime(payOrder.getCreateTime());
       }
       if (forceSettle) {
         transOrder.setStatus(Byte.valueOf((byte)1));
       }

       CommonResult result = null;
       try {
         result = doInnerSettleCreateReq(transOrder, forceSettle);
         if (result.getCode().intValue() == ResultEnum.SETTLE_REPEAT.getCode().intValue()) {

           this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(transOrder.getTransOrderId());
         } else if (result.getCode().intValue() != ResultEnum.SUCCESS.getCode().intValue()) {
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }
       } catch (Exception e) {
         e.printStackTrace();
         PayOrder failedPayOrder = this.baseService4PayOrder.baseSelectSettleFailedPayOrder(orderId);
         if (failedPayOrder == null) {
           this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }
       }
       return result;
     } catch (Exception e) {
       e.printStackTrace();
       PayOrder payOrder = this.baseService4PayOrder.baseSelectPayOrder(orderId);
       PayOrder failedPayOrder = this.baseService4PayOrder.baseSelectSettleFailedPayOrder(orderId);
       if (payOrder != null) {
         if (failedPayOrder == null) {
           this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }

       } else if (failedPayOrder != null) {
         this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(orderId);
       }

       return CommonResult.error("内部记账失败");
     }
   }



   @Transactional(rollbackFor = {Exception.class})
   public CommonResult thirdPayToIncomingOfMchBySuccessPayOrder(PayOrder payOrder) {
     if (payOrder == null) {
       return CommonResult.success("订单为空，不能记账");
     }
     String mchId = payOrder.getMchId();
     String orderId = payOrder.getPayOrderId(); try {
       String channelMchId; int payMode;
       if (payOrder.getStatus().byteValue() < 2) {
         return CommonResult.success("该订单未交易成功，不能记账");
       }

       MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
       JSONObject param2 = JSONObject.parseObject(payOrder.getParam2());

       int currRunMode = this.runModeService.getRunModeCode();

       if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
         payMode = currRunMode;
         channelMchId = "1";
       } else {
         channelMchId = param2.getString("channelMchId");
         if (mchId.startsWith(channelMchId)) {

           payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
         } else {
           payMode = RunModeEnum.PUBLIC.getCode().intValue();
         }
       }
       SaCard card = this.cardService.getSettleCard(mchInfo, payOrder.getCurrency().toUpperCase(), DateUtils.getLocalDateTime(payOrder.getPaySuccTime()), payMode, channelMchId);
       TransOrder transOrder = new TransOrder();
       transOrder.setCardNum(card.getCardNumber());
       transOrder.setMchId(mchId);
       transOrder.setAmount(payOrder.getAmount());

       transOrder.setSettlePayChannelId(payOrder.getChannelId());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(payOrder.getCurrency().toUpperCase()).name() + "_RECHARGE");
       transOrder.setChannelMchId(payOrder.getMchId());
       transOrder.setChannelOrderNo(payOrder.getPayOrderId());
       transOrder.setChannelUser(payOrder.getMchId());
       transOrder.setCurrency(payOrder.getCurrency());
       param2.remove("detail");
       transOrder.setExtra(param2.toJSONString());
       transOrder.setMchTransNo(payOrder.getMchOrderNo());
       transOrder.setChannelOrderNo(payOrder.getChannelOrderNo());
       transOrder.setTransOrderId(payOrder.getPayOrderId());
       transOrder.setNotifyUrl(payOrder.getNotifyUrl());
       transOrder.setTransSuccTime(new Date(payOrder.getPaySuccTime().longValue()));
       transOrder.setStatus(Byte.valueOf((byte)1));

       CommonResult result = null;
       try {
         result = doInnerSettleCreateReq(transOrder);
         if (result.getCode().intValue() == ResultEnum.SETTLE_REPEAT.getCode().intValue()) {

           this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(transOrder.getTransOrderId());
         } else if (result.getCode().intValue() != ResultEnum.SUCCESS.getCode().intValue()) {
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }
       } catch (Exception e) {
         e.printStackTrace();
         PayOrder failedPayOrder = this.baseService4PayOrder.baseSelectSettleFailedPayOrder(orderId);
         if (failedPayOrder == null) {
           this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }
       }
       return result;
     } catch (Exception e) {
       e.printStackTrace();
       PayOrder failedPayOrder = this.baseService4PayOrder.baseSelectSettleFailedPayOrder(orderId);
       if (payOrder != null) {
         if (failedPayOrder == null) {
           this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
         }

       } else if (failedPayOrder != null) {
         this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(orderId);
       }

       return CommonResult.error("内部记账失败");
     }
   }







   private SaCard checkSettleValidate(String fromSettleCardNo) throws Exception {
     return checkSettleValidate(fromSettleCardNo, false);
   }







   private SaCard checkSettleValidate(String fromSettleCardNo, boolean fromSettleCard) throws Exception {
     SaCard card;
     if (fromSettleCard) {
       card = this.cardService.selectSettleByPrimaryKey(fromSettleCardNo);
     } else {
       card = this.cardService.selectByPrimaryKey(fromSettleCardNo);
     }
     if (card == null) {
       throw new Exception("待结算卡不存在");
     }
     if (!checkValidate(card)) {
       return null;
     }
     List<MchInfo> childes = this.mchInfoService.getMchInfoListByRootMchId(card.getUserId() + "-");
     if (childes.size() == 0)
     {
       return card;
     }
     boolean isSettleCompleted = true;
     JSONObject unsettleInfos = new JSONObject();
     List<SaCard> childCards = this.cardService.getAllChildesCards(card.getUserId(), card.getCurrency(), CardDataTypeEnum.SETTLE.name(), card.getValidStartTime(), card.getValidEndTime());
     JSONObject newJson = new JSONObject();

     if (childCards.size() > 0) {

       isSettleCompleted = false;

       String parentId = null;





       for (SaCard c : childCards) {
         unsettleInfos.put(c.getUserId(), c.getCardNumber());
       }
       for (SaCard c : childCards) {
         String[][] idsInfo = UnionIdUtil.getIdInfoFromUnionId(c.getUserId());
         for (int i = 0; i < idsInfo.length; i++) {

           JSONObject parentJson = newJson.getJSONObject(parentId);
           String currMchId = idsInfo[i][0];
           if (!idsInfo[i][1].equals(parentId)) {


             String currMchExternalId = idsInfo[i][1];
             String cardNo = unsettleInfos.getString(currMchId);
             if (cardNo != null) {

               MchInfo mchInfo = this.mchInfoService.selectMchInfo(currMchId);
               if (parentJson == null) {
                 parentJson = new JSONObject();
                 parentJson.put("card", cardNo);
                 parentJson.put("name", mchInfo.getName());
                 parentJson.put("type", mchInfo.getType());
                 parentJson.put("innerId", currMchId);
                 newJson.put(currMchExternalId, parentJson);
               } else {
                 JSONObject currJson = new JSONObject();
                 JSONObject tempJson = new JSONObject();
                 currJson.put(currMchExternalId, tempJson);
                 tempJson.put("card", cardNo);
                 tempJson.put("name", mchInfo.getName());
                 tempJson.put("type", mchInfo.getType());
                 tempJson.put("innerId", currMchId);
                 JSONArray childrenJSONArray = parentJson.getJSONArray("children");
                 if (childrenJSONArray == null) {
                   childrenJSONArray = new JSONArray();
                 }
                 childrenJSONArray.add(currJson);
                 parentJson.put("children", childrenJSONArray);
               }
               parentId = currMchExternalId;
             }
           }
         }
       }
     }  if (!isSettleCompleted) {
       newJson.put("code", ResultEnum.SETTLE_SUB_MCH_HAVE_SETTLED.getCode());
       newJson.put("desc", ResultEnum.SETTLE_SUB_MCH_HAVE_SETTLED.getDesc());
       throw new Exception(newJson.toJSONString());
     }
     return card;
   }

   private CommonResult checkSettlePayOrderV1(MchInfo fromMchInfo, SaCard fromSettleCard, TransOrder settleTransOrder) throws Exception {
     Long notBill = Long.valueOf(0L);
     Long payTotal = Long.valueOf(0L);
     Long total = Long.valueOf(0L);
     Long freeze = Long.valueOf(0L);
     Long transTotal = Long.valueOf(0L);
     if (!fromMchInfo.getType().equals(MchTypeEnum.MCH_BRANCH.name())) {

       List<MchInfo> childes = this.mchInfoService.getMchInfoListByParentMchId(fromMchInfo.getMchId());
       List<String> ids = new ArrayList<>();
       for (MchInfo m : childes) {
         ids.add(m.getMchId());
       }
       SaCardExample example = new SaCardExample();
       SaCardExample.Criteria criteria = example.createCriteria();
       int runMode = this.runModeService.getRunModeCode();
       if (runMode == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {
         criteria.andFlagEqualTo(Integer.valueOf(runMode));
       } else {
         criteria.andFlagEqualTo(RunModeEnum.PUBLIC.getCode());
       }
       criteria.andUserIdIn(ids);
       criteria.andValidStartTimeEqualTo(fromSettleCard.getValidStartTime());
       criteria.andValidEndTimeEqualTo(fromSettleCard.getValidEndTime());
       List<SaSettleCard> saCards = this.cardService.selectByExampleFromSettleCard(example);
       BigDecimal remain = new BigDecimal("0");
       for (SaSettleCard card : saCards) {
         remain = remain.add(new BigDecimal(card.getTia()));
       }
       total = Long.valueOf(remain.multiply(new BigDecimal("100")).longValue());
     } else {
       SaDealRecord saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("1");
       saDealRecord.setOneselfCardNumber(fromSettleCard.getCardNumber());

       List<SaDealRecord> records = this.saDealRecordService.selectSaDealRecords(saDealRecord);

       saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("0");
       saDealRecord.setOtherCardNumber(fromSettleCard.getCardNumber());

       records.addAll(this.saDealRecordService.selectSaDealRecords(saDealRecord));
       Map<String, SaDealRecord> dealRecordMap = new HashMap<>();
       for (SaDealRecord s : records) {
         dealRecordMap.put(s.getMerchantOrderNumber(), s);
       }
       JSONArray jsonArray = new JSONArray();



       boolean isBreak = false;

       List<PayOrder> payOrderList = this.baseService4PayOrder.baseSelectPayOrderByMchIdAndSuccessTime(fromSettleCard.getUserId(),
           DateUtils.getTimestamp(fromSettleCard.getValidStartTime()).longValue(),
           DateUtils.getTimestamp(fromSettleCard.getValidEndTime()).longValue());

       for (PayOrder p : payOrderList) {
         JSONObject dealComment = JSONObject.parseObject(p.getParam2());
         total = Long.valueOf(total.longValue() + p.getAmount().longValue());
         notBill = Long.valueOf(notBill.longValue() + dealComment.getLong("cutOff").longValue());
         freeze = Long.valueOf(freeze.longValue() + dealComment.getLong("payAmount").longValue() + notBill.longValue());
         jsonArray.add(dealComment);

         SaDealRecord saDealRecordItem = dealRecordMap.get(p.getMchOrderNo());
         dealRecordMap.remove(p.getMchOrderNo(), saDealRecordItem);
         if (p.getStatus().byteValue() == 2 || p
           .getStatus().byteValue() == 3 || p
           .getStatus().byteValue() == 2 || p
           .getStatus().byteValue() == 2) {
           payTotal = Long.valueOf(payTotal.longValue() + p.getAmount().longValue());
           if (saDealRecordItem == null) {
             _log.error(p.getPayOrderId() + "：漏记帐目，再检查流水表，如果流水正确，说明记账任然成功", new Object[0]);
             SaScoreFlow saScoreFlow = new SaScoreFlow();
             saScoreFlow.setMerchantOrderNumber(p.getMchOrderNo());
             List<SaScoreFlow> list = this.saScoreFlowRealService.selectList(saScoreFlow);
             if (list.size() == 0) {
               _log.warn("漏记订单：" + p.getPayOrderId(), new Object[0]);
               isBreak = true;






               PayOrder payOrder = (PayOrder)BeanUtil.copyProperties(p, PayOrder.class);
               payOrder.setStatus(Byte.valueOf((byte)-2));
               int r = this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
               this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
               if (r <= 0) {
                 _log.error(p.getPayOrderId() + "：支付订单补登记账失败，需要人工介入", new Object[0]);
                 throw new Exception(p.getPayOrderId() + "：支付订单补登记账失败，需要人工介入");
               }
             }  continue;
           }  if (p.getAmount().longValue() != saDealRecordItem.getScoreReturnAmount().longValue()) {
             _log.error(p.getPayOrderId() + "：实际支付金额和记账金额不一致，需要人工核查", new Object[0]);
             throw new Exception(p.getPayOrderId() + "：实际支付金额和记账金额不一致，需要人工核查");
           }  continue;
         }
         if (saDealRecordItem != null) {
           isBreak = true;


           PayOrder payOrder = (PayOrder)BeanUtil.copyProperties(p, PayOrder.class);
           payOrder.setStatus(Byte.valueOf((byte)-4));
           int r = this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
           if (r <= 0) {
             _log.error(p.getPayOrderId() + "支付订单销账记账失败，需要人工介入", new Object[0]);
             throw new Exception(p.getPayOrderId() + "支付订单销账记账失败，需要人工介入");
           }
         }
       }




       if (isBreak) {
         _log.info("不可结算，存在异常订单，请人工核对并修复", new Object[0]);
         return CommonResult.error("结算核对失败，存在异常订单");
       }

       if (dealRecordMap.size() > 0) {

         Set<Map.Entry<String, SaDealRecord>> entrySet = dealRecordMap.entrySet();
         Iterator<Map.Entry<String, SaDealRecord>> recordEntry = entrySet.iterator();

         while (recordEntry.hasNext()) {
           SaDealRecord record = (SaDealRecord)((Map.Entry)recordEntry.next()).getValue();
           JSONObject dealComment = JSONObject.parseObject(record.getDealComment());
           if (record.getProductType().equals("TRANS")) {
             transTotal = Long.valueOf(transTotal.longValue() + record.getScoreReturnAmount().longValue());
           }
           total = Long.valueOf(total.longValue() + record.getScoreReturnAmount().longValue());
           freeze = Long.valueOf(freeze.longValue() + dealComment.getLong("price").longValue());
           notBill = Long.valueOf(notBill.longValue() + dealComment.getLong("cutOff").longValue());
           jsonArray.add(dealComment);
         }
       }
     }

     if (settleTransOrder != null) {

       if (total.longValue() != settleTransOrder.getAmount().longValue()) {
         throw new Exception("确认记账卡总额和记账记录累计总额不一致，请人工核对");
       }

       SaCard card = this.cardService.copyToSettleCard(fromSettleCard.getCardNumber());
       if (card != null &&
         this.cardService.settleDone(fromMchInfo, fromSettleCard) > 0) {
         return CommonResult.success("确认成功");
       }
     } else {

       return CommonResult.success("核对成功");
     }
     return CommonResult.error("结算失败");
   }


   @Transactional(rollbackFor = {Exception.class})
   public CommonResult transSettleToMch(String fromSettleCardNo) throws Exception {
     SaCard fromCard = checkSettleValidate(fromSettleCardNo);
     if (fromCard == null) {
       return CommonResult.error("结算转账失败:结算卡不存在或者下级商户还有未确认账单");
     }






     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(fromCard.getUserId());
     String toMchId = fromMchInfo.getParentMchId();
     MchInfo toMchInfo = this.mchInfoService.getSettleParentMchInfo(fromMchInfo);
     if (toMchInfo == null) {
       return CommonResult.error("结算转账失败");
     }

     int runMode = fromCard.getFlag().intValue();
     SaCard toCard = this.cardService.getSettleCard(toMchInfo, fromCard.getCurrency().toUpperCase(), fromCard.getValidStartTime(), runMode, fromCard.getConfigNumber());
     if (toCard == null) {
       return CommonResult.error("结算转账失败");
     }

     if (fromCard.getRemainPart().longValue() == 0L) {
       return CommonResult.error("结算卡无可结算余额：无效交易金额");
     }
     try {
       TransOrder transOrder = new TransOrder();
       transOrder.setCardNum(fromSettleCardNo);
       transOrder.setMchId(fromMchInfo.getMchId());
       transOrder.setAmount(fromCard.getRemainPart());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(fromCard.getCurrency().toUpperCase()).name() + "_TRANS");
       transOrder.setChannelMchId(fromCard.getUserId());
       String orderId = UnionIdUtil.getUnionId(new String[] { fromMchInfo.getExternalId(), toMchInfo
             .getExternalId(),
             DateUtils.getCurrentTimeStrDefault() });
       transOrder.setChannelOrderNo(orderId);
       transOrder.setChannelUser(fromCard.getUserId());
       transOrder.setCurrency(fromCard.getCurrency());
       transOrder.setMchTransNo(orderId);
       transOrder.setUserName(fromMchInfo.getName());
       transOrder.setRemarkInfo(fromMchInfo.getName() + "对账完成");
       transOrder.setChannelOrderNo(orderId);
       transOrder.setTransOrderId(orderId);
       JSONObject userItem = new JSONObject();
       userItem.put("userId", toMchId);
       userItem.put("amount", fromCard.getRemainPart());
       userItem.put("comment", fromMchInfo.getName() + "确认账单并转账到" + toMchInfo.getName());
       userItem.put("cardNum", toCard.getCardNumber());
       userItem.put("phone", toMchId);
       JSONArray userList = new JSONArray();
       userList.add(userItem);
       transOrder.setParam2(userList.toJSONString());
       transOrder.setTransSuccTime(new Date(DateUtils.getTimestamp(toCard.getValidStartTime()).longValue()));
       transOrder.setNotifyUrl("");

       SaCard otherCard = this.cardService.getSettleCard(toMchInfo, fromCard.getCurrency(), toCard.getValidStartTime(), runMode, fromCard.getConfigNumber());
       transOrder.setOtherCardNum(otherCard.getCardNumber());
       transOrder.setOtherId(toMchId);
       transOrder.setOtherName(toMchInfo.getName());

       return checkSettlePayOrderV1(fromMchInfo, fromCard, transOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error(RetEnum.RET_UNKNOWN_ERROR.getMessage());
     }
   }
   public CommonResult tryToFixAllPayOrderSettleToMchByMchIdInPlatform(FixSettleBo fixSettleBo) throws Exception {
     LocalDateTime refSettleTime;
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(fixSettleBo.getMchId());
     if (mchInfo == null) {
       throw new Exception("商户不存在：" + fixSettleBo.getMchId());
     }
     CommonResult commonResult = new CommonResult();

     Integer offset = fixSettleBo.getOffsetDays();
     String currency = fixSettleBo.getCurrency();
     Long reference = fixSettleBo.getRefSettleTime();
     if (fixSettleBo.getOffsetDays() == null) {
       offset = Integer.valueOf(0);
     }
     if (StringUtils.isEmpty(currency)) {
       currency = "CNY";
     }

     if (reference == null) {
       refSettleTime = LocalDateTime.now();
     } else {
       refSettleTime = DateUtils.getLocalDateTime(reference);
     }
     for (int i = offset.intValue(); i >= 0; i--) {
       LocalDateTime localDateTime = DateUtils.getLocalDateTimeByOffsetDay(i, refSettleTime);
       SaCard saCard = this.cardService.getSettleCard(mchInfo, currency, localDateTime, RunModeEnum.PUBLIC.getCode().intValue(), "1");
       List<PayOrder> payOrderList = this.baseService4PayOrder.baseSelectPayOrderByMchIdLikeAndPaySuccessTimeRange(mchInfo.getMchId(),
           DateUtils.getTimestamp(saCard.getValidStartTime()),
           DateUtils.getTimestamp(saCard.getValidEndTime()));
       commonResult = tryToFixAllPayOrderSettleToMch(mchInfo, saCard, payOrderList);
     }
     return commonResult;
   }

   public CommonResult tryToFixAllPayOrderSettleToMch(MchInfo mchInfo, SaCard fromCard, List<PayOrder> payOrderList) throws Exception {
     MchInfo fromMchInfo = mchInfo;
     MchInfo toMchInfo = this.mchInfoService.getSettleParentMchInfo(fromMchInfo);
     if (toMchInfo == null) {
       return CommonResult.error("结算转账失败");
     }
     String toMchId = toMchInfo.getMchId();
     int runMode = fromCard.getFlag().intValue();
     SaCard toCard = this.cardService.getSettleCard(toMchInfo, fromCard.getCurrency().toUpperCase(), fromCard.getValidStartTime(), runMode, fromCard.getConfigNumber());
     if (toCard == null) {
       return CommonResult.error("结算转账失败");
     }

     try {
       TransOrder transOrder = new TransOrder();
       transOrder.setCardNum(fromCard.getCardNumber());
       transOrder.setMchId(fromMchInfo.getMchId());
       transOrder.setAmount(fromCard.getRemainPart());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(fromCard.getCurrency().toUpperCase()).name() + "_TRANS");
       transOrder.setChannelMchId(fromCard.getUserId());
       String orderId = UnionIdUtil.getUnionId(new String[] { fromMchInfo.getExternalId(), toMchInfo
             .getExternalId(),
             DateUtils.getCurrentTimeStrDefault() });
       transOrder.setChannelOrderNo(orderId);
       transOrder.setChannelUser(fromCard.getUserId());
       transOrder.setCurrency(fromCard.getCurrency());
       transOrder.setMchTransNo(orderId);
       transOrder.setUserName(fromMchInfo.getName());
       transOrder.setRemarkInfo(fromMchInfo.getName() + "对账完成");
       transOrder.setChannelOrderNo(orderId);
       transOrder.setTransOrderId(orderId);
       JSONObject userItem = new JSONObject();
       userItem.put("userId", toMchId);
       userItem.put("amount", fromCard.getRemainPart());
       userItem.put("comment", fromMchInfo.getName() + "确认账单并转账到" + toMchInfo.getName());
       userItem.put("cardNum", toCard.getCardNumber());
       userItem.put("phone", toMchId);
       JSONArray userList = new JSONArray();
       userList.add(userItem);
       transOrder.setParam2(userList.toJSONString());
       transOrder.setTransSuccTime(new Date(DateUtils.getTimestamp(toCard.getValidStartTime()).longValue()));
       transOrder.setNotifyUrl("");
       SaCard otherCard = this.cardService.getSettleCard(toMchInfo, fromCard.getCurrency(), toCard.getValidStartTime(), runMode, fromCard.getConfigNumber());
       transOrder.setOtherCardNum(otherCard.getCardNumber());
       transOrder.setOtherId(toMchId);
       transOrder.setOtherName(toMchInfo.getName());

       SaDealRecord saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("1");
       saDealRecord.setOneselfCardNumber(fromCard.getCardNumber());
       List<SaDealRecord> records = this.saDealRecordService.selectSaDealRecords(saDealRecord);
       Map<String, SaDealRecord> dealRecordMap = new HashMap<>();
       for (SaDealRecord s : records) {
         dealRecordMap.put(s.getMerchantOrderNumber(), s);
       }
       saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("0");
       saDealRecord.setOtherCardNumber(fromCard.getCardNumber());
       records.addAll(this.saDealRecordService.selectSaDealRecords(saDealRecord));



       for (PayOrder p : payOrderList) {
         SaDealRecord saDealRecordItem = dealRecordMap.get(p.getMchOrderNo());
         dealRecordMap.remove(p.getMchOrderNo(), saDealRecordItem);
         if (p.getStatus().byteValue() == 2 || p
           .getStatus().byteValue() == 3 || p
           .getStatus().byteValue() == 2 || p
           .getStatus().byteValue() == 2) {
           if (saDealRecordItem == null) {
             _log.error(p.getPayOrderId() + "：漏记帐目", new Object[0]);
             CommonResult commonResult = thirdPayToIncomingOfMch(p.getPayOrderId(), fromMchInfo.getMchId(), p.getChannelId(), true);
             if (commonResult.getCode().intValue() != ResultEnum.SUCCESS.getCode().intValue()) {
               PayOrder payOrder = (PayOrder)BeanUtil.copyProperties(p, PayOrder.class);
               payOrder.setStatus(Byte.valueOf((byte)2));
             }  continue;
           }  if (p.getAmount() != saDealRecordItem.getScoreReturnAmount()) {
             _log.error(p.getPayOrderId() + "：实际支付金额和记账金额不一致，需要人工核查", new Object[0]);
             throw new Exception(p.getPayOrderId() + "：实际支付金额和记账金额不一致，需要人工核查");
           }  continue;
         }
         if (saDealRecordItem != null) {


           PayOrder payOrder = (PayOrder)BeanUtil.copyProperties(p, PayOrder.class);
           payOrder.setStatus(Byte.valueOf((byte)4));
           int r = this.baseService4PayOrder.baseCreateSettleFailedPayOrder(payOrder);
           this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
           if (r <= 0) {
             _log.error(p.getPayOrderId() + "支付订单销账记账失败，需要人工介入", new Object[0]);
             throw new Exception(p.getPayOrderId() + "支付订单销账记账失败，需要人工介入");
           }
         }
       }


       return CommonResult.success("对账核对并修复成功");
     } catch (Exception e) {
       e.printStackTrace();
       throw new Exception("核对并修复失败");
     }
   }



   public CommonResult tryToFixAllPayOrderSettleToMch(String fromSettleCardNo) throws Exception {
     SaCard fromCard = new SaCard();
     fromCard.setCardNumber(fromSettleCardNo);
     fromCard = this.cardService.selectByPrimaryKey(fromSettleCardNo);
     if (fromCard == null) {
       return CommonResult.error("结算转账失败");
     }
     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(fromCard.getUserId());

     List<PayOrder> payOrderList = this.baseService4PayOrder.baseSelectPayOrderByMchIdAndSuccessTime(fromMchInfo.getMchId(),
         DateUtils.getTimestamp(fromCard.getValidStartTime()).longValue(),
         DateUtils.getTimestamp(fromCard.getValidEndTime()).longValue());
     return tryToFixAllPayOrderSettleToMch(fromMchInfo, fromCard, payOrderList);
   }


   public void tryToFixSettleFailedPayOrders() throws Exception {
     List<PayOrder> list = this.baseService4PayOrder.baseSelectSettleFailedPayOrder();
     while (true) {
       if (list.size() == 0) {
         return;
       }
       if (this.redisUtil.hasKey("TRY_TO_FIX").booleanValue()) {
         return;
       }
       this.redisUtil.set("TRY_TO_FIX", "1", 5L, TimeUnit.SECONDS);

       for (PayOrder p : list) {
         int payMode; String channelMchId; MchInfo mchInfo = this.mchInfoService.selectMchInfo(p.getMchId());
         JSONObject param2 = JSONObject.parseObject(p.getParam2());
         int currRunMode = this.runModeService.getRunModeCode();


         if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
           payMode = currRunMode;
           channelMchId = "1";
         } else {
           channelMchId = param2.getString("channelMchId");
           if (p.getMchId().startsWith(channelMchId)) {

             payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
           } else {
             payMode = RunModeEnum.PUBLIC.getCode().intValue();
           }
         }
         CommonResult commonResult = tryToFixPayOrderSettleToMch(p.getPayOrderId(), this.cardService.getSettleCard(mchInfo, p.getCurrency().toUpperCase(),
               DateUtils.getLocalDateTime(p.getPaySuccTime()), payMode, channelMchId));
         if (commonResult.getCode().intValue() == 200) {
           this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(p.getPayOrderId()); continue;
         }  if (commonResult.getCode().intValue() == ResultEnum.SETTLE_REPEAT.getCode().intValue())
         {
           this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(p.getPayOrderId());
         }
       }
       list = this.baseService4PayOrder.baseSelectSettleFailedPayOrder();
       if (list.size() <= 0) {
         this.redisUtil.delete("TRY_TO_FIX");
         return;
       }
     }
   }
   public CommonResult tryToFixPayOrderSettleToMch(String payOrderId, String settleCardId) throws Exception {
     SaCard fromCard = new SaCard();
     fromCard.setCardNumber(settleCardId);
     fromCard = this.cardService.selectByPrimaryKey(settleCardId);
     return tryToFixPayOrderSettleToMch(payOrderId, fromCard);
   }

   public CommonResult tryToFixPayOrderSettleToMch(String payOrderId, SaCard fromCard) throws Exception {
     PayOrder payOrder = this.baseService4PayOrder.baseSelectPayOrder(payOrderId);
     PayOrder failed = this.baseService4PayOrder.baseSelectSettleFailedPayOrder(payOrderId);
     if (failed.getAmount().longValue() != payOrder.getAmount().longValue()) {
       throw new Exception("订单金额有误差，请人工处理");
     }





     if (fromCard == null) {
       return CommonResult.error("结算转账失败，结算卡不存在");
     }

     if (failed.getStatus().byteValue() == 4) {
       CommonResult commonResult1 = cancel(payOrderId, payOrder.getMchId());
       if (commonResult1.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(payOrderId);
       }
       return commonResult1;
     }

     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(fromCard.getUserId());
     MchInfo toMchInfo = this.mchInfoService.getSettleParentMchInfo(fromMchInfo);
     if (toMchInfo == null) {
       return CommonResult.error("结算转账失败");
     }

     _log.info(payOrder.getPayOrderId() + "：漏记帐目", new Object[0]);
     CommonResult commonResult = thirdPayToIncomingOfMch(payOrder.getPayOrderId(), fromMchInfo.getMchId(), payOrder.getChannelId(), false);
     if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
       return CommonResult.error("修复成功");
     }
     return commonResult;
   }



   @RabbitListener(queues = {"queue.notify.mch.settle.independent.withdraw"})
   private void sendSettleRequestToPlatform(Message message, Channel channel) throws NoSuchMethodException {
     String jsonStr = StrUtil.getJsonString(new String(message.getBody()));
     _log.info("独立收款提现申请:msg={}", new Object[] { jsonStr });
     SaCard card = (SaCard)JSONObject.parseObject(jsonStr, SaCard.class);
     try {
       sendSettleRequestToPlatform(card);
     } catch (Exception e) {
       e.printStackTrace();
       this.mq4MchNotify.forceSend("queue.notify.mch.pay.order.create", jsonStr, 30000L);
     }
   }








   @Transactional(rollbackFor = {Exception.class})
   public CommonResult sendSettleRequestToPlatform(String settleCardNo) throws Exception {
     SaCard fromCard = checkSettleValidate(settleCardNo);
     if (fromCard == null) {
       return CommonResult.error("结算转账失败:结算卡不存在或者下级商户还有未确认账单");
     }

     return sendSettleRequestToPlatform(fromCard);
   }

   public CommonResult sendSettleRequestToPlatform(SaCard fromCard) throws Exception {
     if (fromCard == null) {
       return CommonResult.error("结算转账失败:结算卡不存在或者下级商户还有未确认账单");
     }

     if (fromCard.getRemainPart().longValue() == 0L) {
       return CommonResult.error("无效交易金额：结算卡余额为0，无实际交易或者下级商户还未确认账单");
     }
     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(fromCard.getUserId());








     String mchId = fromMchInfo.getMchId();
     try {
       TransOrder transOrder = new TransOrder();
       transOrder.setMchId(fromMchInfo.getMchId());
       SettleResult settleResult = checkSettleResult(fromMchInfo, fromCard);
       JSONObject comments = settleResult.getComments();

       transOrder.setAmount(fromCard.getRemainPart());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(fromCard.getCurrency().toUpperCase()).name() + "_WITHDRAW");
       transOrder.setChannelMchId(fromCard.getUserId());

       String orderId = mchId + "-" + fromCard.getCardId();

       transOrder.setChannelOrderNo(orderId);
       transOrder.setChannelUser(fromCard.getUserId());
       transOrder.setCurrency(fromCard.getCurrency());
       transOrder.setMchTransNo(orderId);
       transOrder.setUserName(fromMchInfo.getName());
       transOrder.setRemarkInfo(fromMchInfo.getName() + "结算申请");
       transOrder.setChannelOrderNo(orderId);
       transOrder.setTransOrderId(orderId);
       transOrder.setParam1(settleResult.getPoundage().toString());
       transOrder.setParam2(JSON.toJSONString(fromCard));
       transOrder.setTransSuccTime(new Date());
       transOrder.setCardNum(fromCard.getCardNumber());
       transOrder.setNotifyUrl("");

       SaDealRecord saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("1");
       saDealRecord.setOneselfCardNumber(fromCard.getCardNumber());
       JSONArray jsonArray = new JSONArray();


       Long freeze = fromCard.getFreezePart();
       Long notBill = fromCard.getNotBill();

       comments.put("mchType", fromMchInfo.getType());
       comments.put("mchName", fromMchInfo.getName());

       comments.put("amount", Long.valueOf(freeze.longValue() - notBill.longValue()));
       comments.put("price", freeze);
       comments.put("cutOff", notBill);
       comments.put("class", PayEnum.BIZ_CLASS_TRANS_EXP.getCode());
       comments.put("detail", jsonArray);


       transOrder.setExtra(comments.toJSONString());
       _log.info("请求结算金额：" + transOrder.getAmount().longValue(), new Object[0]);
       _log.info("交易记录统计总额：" + settleResult.getCheckSum(), new Object[0]);
       if (settleResult.getCheckSum().longValue() != transOrder.getAmount().longValue()) {
         throw new Exception("统计渠道总交易额与结算交易总额不相等");
       }


       List<PayOrder> syncFailedList = this.baseService4PayOrder.baseSelectSyncFailedPayOrder();
       if (syncFailedList.size() > 0) {

         Object object = new Object(this, syncFailedList);












         object.run();
         return CommonResult.error("有未同步订单记录，正在补偿同步中，请稍后");
       }
       TransOrderForSettleBo transOrderForSettleBo = (TransOrderForSettleBo)BeanUtil.copyProperties(transOrder, TransOrderForSettleBo.class);
       transOrderForSettleBo.setSettleCardNo(fromCard.getCardNumber());
       transOrderForSettleBo.setSettleStartTime(fromCard.getValidStartTime());
       transOrderForSettleBo.setSettleEndTime(fromCard.getValidEndTime());

       JSONObject settleWithdrawSyncRequest = (JSONObject)JSONObject.toJSON(transOrderForSettleBo);
       MchInfo platform = this.mchInfoService.getRootMchInfo(mchId);
       String reqSign = PayDigestUtil.getSign((Map)settleWithdrawSyncRequest, platform.getReqKey(), new String[] { "sign", "createTime", "transSuccTime", "updateTime", "expireTime" });





       _log.info("签名信息：" + reqSign, new Object[0]);
       settleWithdrawSyncRequest.put("sign", reqSign);

       try {
         if (this.baseService4TransOrder.baseSelectByTransOrderId(transOrder.getTransOrderId()) == null) {
           this.baseService4TransOrder.baseCreateTransOrder(transOrder);
         } else {
           this.baseService4TransOrder.baseUpdate(transOrder);
         }
         _log.info("结算提现申请卡号：" + transOrderForSettleBo.getSettleCardNo(), new Object[0]);
         if (StringUtils.isEmpty(platform.getSecurityUrl())) {
           platform.setSecurityUrl(this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl"));
         }

         if (fromCard.getFlag().intValue() == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {

           settleWithdrawIndependentMode(fromCard, transOrder, settleResult);
           return CommonResult.success("独立收款的记账单不需要向公域申请结算，已经本地处理完成");
         }

         String syncResult = HttpClient.post(platform.getSecurityUrl() + "/mch_settle/syncSettleWithdrawToPlatform", (Map)settleWithdrawSyncRequest, Integer.valueOf(15000));
         CommonResult commonResult = (CommonResult)JSON.parseObject(syncResult, CommonResult.class);
         if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
           return handleSettleCompleted(fromCard.getCardNumber(), transOrderForSettleBo
               .getAmount(),
               Long.valueOf(transOrderForSettleBo.getAmount().longValue() - settleResult.getPoundage().longValue()), settleResult
               .getPoundage());
         }
         throw new Exception("结算提取申请失败");

       }
       catch (Exception e) {
         e.printStackTrace();
         throw new Exception("结算提取申请失败");
       }
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error(RetEnum.RET_UNKNOWN_ERROR.getMessage());
     }
   }

   private CommonResult handleSettleCompleted(String settleCardNo, Long incoming, Long exp, Long award) throws Exception {
     SaCard card = this.cardService.copyToSettleCard(settleCardNo);
     if (card != null) {
       if (this.cardService.settleDone(settleCardNo, incoming, exp, award, SettleStatusEnum.CONFIRMED) == 0)
       {



         throw new Exception("结算申请失败：结算卡转移处理失败");
       }

       return CommonResult.success("结算申请成功");
     }
     throw new Exception("结算申请失败：结算卡转移处理失败");
   }

   private SettleResult checkSettleResult(MchInfo fromMchInfo, SaCard fromCard) throws NoSuchMethodException {
     SettleResult settleResult = new SettleResult();
     settleResult.setPoundage(Long.valueOf(0L));
     settleResult.setComments(new JSONObject());
     CheckSumPayOrder checkSumPayOrder = new CheckSumPayOrder();
     checkSumPayOrder.setPaySuccTimeStart(fromCard.getValidStartTime());
     checkSumPayOrder.setPaySuccTimeEnd(fromCard.getValidEndTime());
     checkSumPayOrder.setMchId(fromMchInfo.getMchId() + "%");
     List<PayOrder> checkSumList = this.payOrderMapper.getSumByPayChannelCodeAndPaySuccessTime(checkSumPayOrder);



     for (PayOrder payOrder : checkSumList) {
       settleResult.setCheckSum(Long.valueOf(settleResult.getCheckSum().longValue() + payOrder.getAmount().longValue()));



       Double poundageRate = fromCard.getPoundageRateByPayChannel(PayChannelCodeEnum.getTypeByCode(payOrder.getChannelCode()));
       Long channelPoundage = Long.valueOf(Math.max(1L, Math.round(payOrder.getAmount().longValue() * poundageRate.doubleValue() / 1000.0D)));
       settleResult.setPoundage(Long.valueOf(settleResult.getPoundage().longValue() + channelPoundage.longValue()));
       JSONObject settleDetail = new JSONObject();
       settleDetail.put("amount", payOrder.getAmount());
       settleDetail.put("poundageRate", BigDecimal.valueOf(poundageRate.doubleValue()).divide(BigDecimal.valueOf(1000L)).toString());
       settleDetail.put("poundage", channelPoundage);
       settleResult.getComments().put(payOrder.getChannelCode().toString(), settleDetail.toJSONString());
     }

     settleResult.setPoundage(Long.valueOf(Math.max(1L, settleResult.getPoundage().longValue())));
     return settleResult;
   }







   @Transactional(rollbackFor = {Exception.class})
   public CommonResult withdrawInPlatform(String settleCardNo, String privateSettleTransOrderNo) throws Exception {
     _log.info("平台处理结算申请，平台对应记账卡号：" + settleCardNo, new Object[0]);
     SaCard fromCard = new SaCard();
     fromCard.setCardNumber(settleCardNo);
     fromCard = this.saCardService.baseInfo(fromCard);
     if (fromCard == null) {
       return CommonResult.error("结算申请失败");
     }

     if (fromCard.getRemainPart().longValue() == 0L) {
       return CommonResult.error("结算卡无可结算余额：无效交易金额");
     }

     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(fromCard.getUserId());
     String mchId = fromMchInfo.getMchId();
     try {
       TransOrder transOrder = new TransOrder();
       transOrder.setMchId(fromMchInfo.getMchId());

       SettleResult settleResult = checkSettleResult(fromMchInfo, fromCard);
       JSONObject comments = settleResult.getComments();



       transOrder.setAmount(fromCard.getRemainPart());
       transOrder.setChannelId("HL_" + CurrencyTypeEnum.valueOf(fromCard.getCurrency().toUpperCase()).name() + "_WITHDRAW");
       transOrder.setChannelMchId(fromCard.getUserId());

       String orderId = mchId + "-" + fromCard.getCardId();


       transOrder.setChannelOrderNo(orderId);
       transOrder.setChannelUser(fromCard.getUserId());
       transOrder.setCurrency(fromCard.getCurrency());
       transOrder.setMchTransNo(orderId);
       transOrder.setUserName(fromMchInfo.getName());
       transOrder.setRemarkInfo(fromMchInfo.getName() + "结算申请");
       transOrder.setChannelOrderNo(orderId);
       transOrder.setTransOrderId(orderId);
       transOrder.setParam1(settleResult.getPoundage().toString());
       JSONObject param2 = (JSONObject)JSONObject.toJSON(fromCard);

       param2.put("privateSettleNo", privateSettleTransOrderNo);
       transOrder.setParam2(param2.toJSONString());
       transOrder.setTransSuccTime(new Date());
       transOrder.setCardNum(settleCardNo);
       transOrder.setNotifyUrl("");

       SaDealRecord saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("1");
       saDealRecord.setOneselfCardNumber(settleCardNo);
       List<SaDealRecord> records = this.saDealRecordService.selectSaDealRecords(saDealRecord);
       _log.info("平台处理结算申请：记账记录：" + JSONObject.toJSONString(records), new Object[0]);

       JSONArray jsonArray = new JSONArray();


       Long freeze = Long.valueOf(0L);
       Long notBill = Long.valueOf(0L);
       for (SaDealRecord r : records) {
         JSONObject dealComment = JSONObject.parseObject(r.getDealComment());
         if (dealComment == null) {
           dealComment = new JSONObject();
         }
         dealComment.put("settleNo", r.getKernelCardNum());
         dealComment.put("class", PayEnum.BIZ_CLASS_SALE_INCOME.getCode());
         dealComment.put("status", Byte.valueOf((byte)2));

         Long price = dealComment.getLong("price");
         freeze = Long.valueOf(freeze.longValue() + ((price == null) ? 0L : price.longValue()));
         Long cutOff = dealComment.getLong("cutOff");
         notBill = Long.valueOf(notBill.longValue() + ((cutOff == null) ? 0L : cutOff.longValue()));
         jsonArray.add(dealComment);
       }

       saDealRecord = new SaDealRecord();
       saDealRecord.setDealType("0");
       saDealRecord.setOtherCardNumber(settleCardNo);
       records = this.saDealRecordService.selectSaDealRecords(saDealRecord);



       for (SaDealRecord r : records) {
         JSONObject dealComment = new JSONObject();
         JSONObject temp = JSONObject.parseObject(r.getDealComment());
         dealComment.put("mchType", temp.getString("mchType"));
         dealComment.put("settleNo", r.getKernelCardNum());
         dealComment.put("class", PayEnum.BIZ_CLASS_TRANS_EXP.getCode());
         dealComment.put("mchName", r.getUserName());
         dealComment.put("amount", r.getScoreReturnAmount());
         dealComment.put("price", temp.getLong("price"));
         dealComment.put("cutOff", temp.getLong("cutOff"));
         dealComment.put("dateTime", r.getDealCreateTime());
         dealComment.put("status", Byte.valueOf((byte)2));

         Long price = dealComment.getLong("price");
         freeze = Long.valueOf(freeze.longValue() + ((price == null) ? 0L : price.longValue()));
         Long cutOff = dealComment.getLong("cutOff");
         notBill = Long.valueOf(notBill.longValue() + ((cutOff == null) ? 0L : cutOff.longValue()));
         jsonArray.add(dealComment);
       }
       comments.put("mchType", fromMchInfo.getType());
       comments.put("mchName", fromMchInfo.getName());

       comments.put("amount", Long.valueOf(freeze.longValue() - notBill.longValue()));
       comments.put("price", freeze);
       comments.put("cutOff", notBill);
       comments.put("class", PayEnum.BIZ_CLASS_TRANS_EXP.getCode());
       comments.put("detail", jsonArray);

       transOrder.setExtra(comments.toJSONString());
       if (settleResult.getCheckSum().longValue() != transOrder.getAmount().longValue()) {
         throw new Exception("统计渠道总交易额与结算交易总额不相等");
       }

       this.baseService4TransOrder.baseCreateTransOrder(transOrder);
       CommonResult commonResult = handleSettleCompleted(settleCardNo, transOrder
           .getAmount(),
           Long.valueOf(transOrder.getAmount().longValue() - settleResult.getPoundage().longValue()), settleResult
           .getPoundage());



       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         doSettleBySettleCardNoInPlatform(settleCardNo);
       }
       return CommonResult.success("结算申请成功");
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error(RetEnum.RET_UNKNOWN_ERROR.getMessage());
     }
   }







   @Transactional(rollbackFor = {Exception.class})
   public CommonResult doSettleInPlatform(String mchId, String settleCardNo) throws NoSuchMethodException {
     TransOrder transOrder = this.baseService4TransOrder.baseSelectTransOrder(mchId + "-" + settleCardNo);
     if (transOrder == null) {
       return CommonResult.error("订单不存在");
     }
     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(transOrder.getMchId());
     transOrder.setRemarkInfo(fromMchInfo.getName() + "执行结算提现记账");
     JSONObject params2 = JSONObject.parseObject(transOrder.getParam2());
     transOrder.setCardNum(params2.getString("cardNumber"));


     String privateSettleNo = params2.getString("privateSettleNo");
     SaCard card = this.cardService.selectSettleByPrimaryKey(settleCardNo);
     card.setCardStatus(SettleStatusEnum.COMPLETED.getType());

     this.cardService.updateByPrimaryKeyFromSettleCard(card);
     this.baseService4TransOrder.baseUpdateStatus4Success(transOrder.getTransOrderId());
     CommonResult commonResult = new CommonResult();
     commonResult.setCode(ResultEnum.SUCCESS.getCode());
     commonResult.setExtra(privateSettleNo);
     return commonResult;
   }








   @Transactional(rollbackFor = {Exception.class})
   public CommonResult doSettle(String settleOrderNo) throws Exception {
     TransOrder transOrder = this.baseService4TransOrder.baseSelectTransOrder(settleOrderNo);
     if (transOrder == null) {
       return CommonResult.error("订单不存在");
     }
     MchInfo fromMchInfo = this.mchInfoService.selectMchInfo(transOrder.getMchId());
     transOrder.setRemarkInfo(fromMchInfo.getName() + "执行结算提现记账");
     JSONObject params2 = JSONObject.parseObject(transOrder.getParam2());
     transOrder.setCardNum(params2.getString("cardNumber"));

     SaCard card = this.cardService.selectSettleByPrimaryKey(transOrder.getCardNum());
     card.setCardStatus(SettleStatusEnum.COMPLETED.getType());
     this.cardService.updateByPrimaryKeyFromSettleCard(card);
     this.baseService4TransOrder.baseUpdateStatus4Success(transOrder.getTransOrderId());
     return CommonResult.success("结算处理完成");
   }








   @Transactional(rollbackFor = {Exception.class})
   public CommonResult doSettleBySettleCardNoInPlatform(String settleCardNo) throws Exception {
     SaCard settleCard = checkSettleValidate(settleCardNo, true);
     if (settleCard == null) {
       return CommonResult.error("结算转账失败:结算卡不存在或者下级商户还有未确认账单");
     }

     CommonResult result = doSettleInPlatform(settleCard.getUserId(), settleCardNo);
     if (result.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
       callBackSettleDone(settleCard.getUserId(), result.getExtra());
     }
     return result;
   }

   private void callBackSettleDone(String mchId, String privateSettleTransOrderNo) throws Exception {
     JSONObject param = new JSONObject();
     param.put("settleTransOrderNo", privateSettleTransOrderNo);
     param.put("mchId", mchId);

     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     try {
       String syncResult = HttpClient.post(mchInfo.getSecurityUrl() + "/mch_settle/doSettleWithdraw", (Map)param);
       CommonResult commonResult = (CommonResult)JSON.parseObject(syncResult, CommonResult.class);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         _log.info("结算处理完成的回调成功", new Object[0]);
       } else {
         _log.info("结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
         this.mq4MchNotify.send("queue.notify.mch.settle.completed.callback", param.toJSONString(), 180000L);
       }
     } catch (Exception e) {
       e.printStackTrace();
       _log.info("结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
       this.mq4MchNotify.send("queue.notify.mch.settle.completed.callback", param.toJSONString(), 180000L);
     }
   }





   public void callBackCashSettleDone(List<SaCard> list) throws NoSuchMethodException {
     for (int i = 0; i < list.size(); i++) {
       SaCard item = list.get(i);
       MchInfo mchInfo = this.mchInfoService.selectMchInfo(((SaCard)list.get(i)).getUserId());
       try {
         TransOrder transOrder = this.baseService4TransOrder.baseSelectTransOrder(item.getUserId() + "-" + item.getCardNumber());
         JSONObject param2 = JSONObject.parseObject(transOrder.getParam2());
         String settleCardNumber = UnionIdUtil.getIdInfoFromUnionId(param2.getString("privateSettleNo"))[1][1];
         String syncResult = HLPayUtil.call4Post(mchInfo.getSecurityUrl() + "/mch_settle/settleCashSettleCompletedCallback?settleCardNumber=" + settleCardNumber);
         _log.info("现金结算回调结果：" + syncResult, new Object[0]);
         CommonResult commonResult = (CommonResult)JSON.parseObject(syncResult, CommonResult.class);
         if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
           _log.info("现金结算处理完成的回调成功", new Object[0]);
         } else {
           _log.info("现金结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
           this.mq4MchNotify.send("queue.notify.mch.cash.settle.completed.callback", JSONArray.toJSONString(list.get(i)), 300000L);
         }
       } catch (Exception e) {
         e.printStackTrace();
         _log.info("现金结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
         this.mq4MchNotify.send("queue.notify.mch.cash.settle.completed.callback", JSONArray.toJSONString(list.get(i)), 300000L);
       }
     }
   }



   public void callBackCashSettleDone(SaCard card) throws NoSuchMethodException {
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(card.getUserId());
     try {
       TransOrder transOrder = this.baseService4TransOrder.baseSelectTransOrder(card.getUserId() + "-" + card.getCardNumber());
       JSONObject param2 = JSONObject.parseObject(transOrder.getParam2());
       String settleCardNumber = UnionIdUtil.getIdInfoFromUnionId(param2.getString("privateSettleNo"))[1][1];
       _log.info("settleCardNumber = " + settleCardNumber, new Object[0]);
       String syncResult = HLPayUtil.call4Post(mchInfo.getSecurityUrl() + "/mch_settle/settleCashSettleCompletedCallback?settleCardNumber=" + settleCardNumber);
       CommonResult commonResult = (CommonResult)JSON.parseObject(syncResult, CommonResult.class);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         _log.info("现金结算处理完成的回调成功", new Object[0]);
       } else {
         _log.info("现金结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
         this.mq4MchNotify.send("queue.notify.mch.cash.settle.completed.callback", JSONArray.toJSONString(card), 600000L);
       }
     } catch (Exception e) {
       e.printStackTrace();
       _log.info("现金结算处理完成的回调失败，发送延时消息进行补偿", new Object[0]);
       this.mq4MchNotify.send("queue.notify.mch.cash.settle.completed.callback", JSONArray.toJSONString(card), 600000L);
     }
   }

   @RabbitListener(queues = {"queue.notify.mch.cash.settle.completed.callback"})
   private void cashCallBackMQ(Message message, Channel channel) throws Exception {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     int start = msg.indexOf("{");
     if (start < 0) {
       msg = "{" + msg;
     }
     int end = msg.lastIndexOf("}");
     if (end <= 0) {
       msg.concat("}");
     }
     String jsonStr = msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1);
     SaCard param = (SaCard)JSONObject.parseObject(jsonStr, SaCard.class);
     callBackCashSettleDone(param);
   }


   @RabbitListener(queues = {"queue.notify.mch.settle.completed.callback"})
   private void callBackMQ(Message message, Channel channel) throws Exception {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     int start = msg.indexOf("{");
     if (start < 0) {
       msg = "{" + msg;
     }
     int end = msg.lastIndexOf("}");
     if (end <= 0) {
       msg.concat("}");
     }
     String jsonStr = msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1);
     JSONObject param = JSONObject.parseObject(jsonStr);
     String privateSettleTransOrderNo = param.getString("settleTransOrderNo");
     String mchId = param.getString("mchId");
     callBackSettleDone(mchId, privateSettleTransOrderNo);
   }











   public CommonResult syncSettleWithdrawToPlatformV1(String transOrderForSettleRequest) throws Exception {
     JSONObject transContext = new JSONObject();
     TransOrderForSettleBo transOrderForSettleBo = (TransOrderForSettleBo)JSONObject.parseObject(transOrderForSettleRequest, TransOrderForSettleBo.class);
     validateParams(JSONObject.parseObject(transOrderForSettleRequest), transContext);
     TransOrder transOrder = (TransOrder)BeanUtil.copyProperties(transOrderForSettleBo, TransOrder.class);
     transOrder.setStatus(Byte.valueOf((byte)0));
     transOrder.setCardNum(transOrderForSettleBo.getSettleCardNo());
     _log.info("getSettleCardNo:" + transOrderForSettleBo.getSettleCardNo(), new Object[0]);
     transOrder.setTransSuccTime(DateUtils.getDate(transOrderForSettleBo.getSettleStartTime()));



     MchInfo mchInfo = this.mchInfoService.selectMchInfo(transOrder.getMchId());

     Long checkSum = transOrder.getAmount();


     int runMode = RunModeEnum.PUBLIC.getCode().intValue();
     SaCard localSettleCard = this.cardService.getSettleCard(mchInfo, transOrder.getCurrency(), transOrderForSettleBo.getSettleStartTime(), runMode, "1");
     if (localSettleCard == null) {
       throw new Exception("结算中心没有找到对应的结算卡");
     }
     Long sum = localSettleCard.getRemainPart();
     _log.info("请求结算:" + checkSum + "    本地记账:" + sum, new Object[0]);

     CheckSumPayOrder checkSumPayOrder = new CheckSumPayOrder();
     checkSumPayOrder.setPaySuccTimeStart(transOrderForSettleBo.getSettleStartTime());
     checkSumPayOrder.setPaySuccTimeEnd(transOrderForSettleBo.getSettleEndTime());
     checkSumPayOrder.setMchId(transOrderForSettleBo.getMchId() + "%");
     List<PayOrder> checkSumList = this.payOrderMapper.getSumByPayChannelCodeAndPaySuccessTime(checkSumPayOrder);
     List<PayOrder> checkList = this.payOrderMapper.getByPayChannelCodeAndPaySuccessTime(checkSumPayOrder);

     checkSum = Long.valueOf(0L);
     _log.info("checkSumList:" + JSONObject.toJSONString(checkSumList), new Object[0]);
     Map<String, Integer> payOrderMap = new HashMap<>(16384);
     localSettleCard = this.cardService.getSettleCard(mchInfo, transOrder.getCurrency(), transOrderForSettleBo.getSettleStartTime(), runMode, "1");
     _log.info("checkSumList:" + JSONObject.toJSONString(checkSumList), new Object[0]);
     _log.info("checkList:" + JSONObject.toJSONString(checkList), new Object[0]);
     for (PayOrder payOrder : checkSumList) {
       checkSum = Long.valueOf(checkSum.longValue() + payOrder.getAmount().longValue());
     }


     for (PayOrder payOrder : checkList) {
       payOrderMap.put(payOrder.getPayOrderId(), Integer.valueOf(1));

       PayOrder settleFailed = this.payOrderMapper.selectSettkeFailedByPrimaryKey(payOrder.getPayOrderId());
       if (settleFailed != null) {
         _log.info("记账失败记录订单号：" + settleFailed.getPayOrderId(), new Object[0]);
         String channelId = payOrder.getChannelId();
         thirdPayToIncomingOfMch(payOrder.getPayOrderId(), mchInfo.getMchId(), channelId);
       }
     }
     _log.info("checkSum:" + checkSum + "    sum:" + sum, new Object[0]);
     if (checkSum.longValue() != sum.longValue() &&
       checkSum.longValue() != transOrder.getAmount().longValue()) {

       List<PayOrder> list = this.baseService4PayOrder.baseSelectSettleFailedPayOrder();
       for (PayOrder p : list)
       {
         thirdPayToIncomingOfMch(p.getPayOrderId(), mchInfo.getMchId(), p.getChannelId());
       }
     }

     localSettleCard = this.cardService.getSettleCard(mchInfo, transOrder.getCurrency(), transOrderForSettleBo.getSettleStartTime(), runMode, "1");

     if (checkSum.longValue() == sum.longValue()) {
       return withdrawInPlatform(localSettleCard.getCardNumber(), transOrderForSettleBo.getTransOrderId());
     }











     throw new Exception("结算同步失败,无实际交易订单");
   }


   public CommonResult settleWithdrawIndependentMode(SaCard withDrawCard, TransOrder transOrder, SettleResult settleResult) throws Exception {
     return handleSettleCompleted(withDrawCard.getCardNumber(), withDrawCard
         .getRemainPart(),
         Long.valueOf(withDrawCard.getRemainPart().longValue() - settleResult.getPoundage().longValue()), settleResult
         .getPoundage());
   }









   private Object validateParams(JSONObject params, JSONObject transContext) throws Exception {
     String mchId = params.getString("mchId");
     String mchTransNo = params.getString("mchTransNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     _log.info("转账金额（单位分）:{}", new Object[] { amount });
     String currency = params.getString("currency");
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

     commonCheckParams(params);


     if (StringUtils.isBlank(sign)) {
       String errorMessage = "request params[sign] error.";
       throw new Exception(errorMessage);
     }


     MchInfo mchInfo = this.mchInfoService.getRootMchInfo(mchId);
     if (mchInfo == null) {
       String errorMessage = "Can't found mchInfo[mchId=" + mchId + "] record in db.";
       throw new Exception(errorMessage);
     }
     if (mchInfo.getState().byteValue() != 1) {
       String errorMessage = "mchInfo not available [mchId=" + mchId + "] record in db.";
       throw new Exception(errorMessage);
     }

     String reqKey = mchInfo.getReqKey();
     if (StringUtils.isBlank(reqKey)) {
       String errorMessage = "reqKey is null[mchId=" + mchId + "] record in db.";
       return errorMessage;
     }
     transContext.put("resKey", mchInfo.getResKey());
     transContext.put("reqKey", mchInfo.getReqKey());


     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       String errorMessage = "Can't found payChannel[channelId=" + channelId + ",mchId=" + mchId + "] record in db.";
       throw new Exception(errorMessage);
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       String errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       throw new Exception(errorMessage);
     }
     transContext.put("channelName", payChannel.getString("channelName"));


     boolean verifyFlag = HLPayUtil.verifyPaySign((Map)params, reqKey, new String[] { "createTime", "transSuccTime", "updateTime", "expireTime" });




     if (!verifyFlag) {
       String errorMessage = "Verify XX trans sign failed.";
       throw new Exception(errorMessage);
     }

     JSONObject transOrder = new JSONObject();
     transOrder.put("transOrderId", UnionIdUtil.getUnionId(new String[] { mchId, mchTransNo }));
     transOrder.put("mchId", mchId);
     transOrder.put("mchTransNo", mchTransNo);
     transOrder.put("channelId", channelId);
     transOrder.put("amount", Long.valueOf(Long.parseLong(amount)));
     transOrder.put("currency", currency);
     transOrder.put("clientIp", clientIp);
     transOrder.put("device", device);
     transOrder.put("channelUser", channelUser);
     transOrder.put("userName", userName);
     transOrder.put("remarkInfo", remarkInfo);
     transOrder.put("extra", extra);
     transOrder.put("channelMchId", payChannel.getString("channelMchId"));
     transOrder.put("param1", param1);
     transOrder.put("param2", param2);
     transOrder.put("notifyUrl", notifyUrl);
     return transOrder;
   }



   String commonCheckParams(JSONObject params) throws Exception {
     String mchId = params.getString("mchId");
     String mchTransNo = params.getString("mchTransNo");
     String channelId = params.getString("channelId");
     String amount = params.getString("amount");
     String currency = params.getString("currency");

     if (StringUtils.isBlank(mchId)) {
       String errorMessage = "request params[mchId] error.";
       throw new Exception(errorMessage);
     }
     if (StringUtils.isBlank(mchTransNo)) {
       String errorMessage = "request params[mchTransNo] error.";
       throw new Exception(errorMessage);
     }
     if (StringUtils.isBlank(channelId)) {
       String errorMessage = "request params[channelId] error.";
       throw new Exception(errorMessage);
     }
     if (StringUtils.isBlank(amount)) {
       String errorMessage = "request params[amount] error.";
       throw new Exception(errorMessage);
     }
     if (!NumberUtils.isDigits(amount)) {
       String errorMessage = "request params[amount] error.";
       throw new Exception(errorMessage);
     }

     if (StringUtils.isBlank(currency)) {
       String errorMessage = "request params[currency] error.";
       throw new Exception(errorMessage);
     }


     if (StringUtils.isBlank(currency)) {
       String errorMessage = "request params[currency] error.";
       throw new Exception(errorMessage);
     }
     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     if (payChannel == null) {
       String errorMessage = "error: can not find channel. [channelId=" + channelId + ",mchId=" + mchId + "]";
       throw new Exception(errorMessage);
     }
     if (payChannel.getByte("state").byteValue() != 1) {
       String errorMessage = "channel not available [channelId=" + channelId + ",mchId=" + mchId + "]";
       throw new Exception(errorMessage);
     }
     if (!currency.toLowerCase().equals(payChannel.getJSONObject("param").get("currency"))) {
       String errorMessage = "request params[currency] error.";
       throw new Exception(errorMessage);
     }
     return null;
   }







   public CommonResult doInnerSettleCreateReq(TransOrder transOrder) throws Exception {
     return doInnerSettleCreateReq(transOrder, false);
   }






   public CommonResult doInnerSettleCreateReq(TransOrder transOrder, boolean isForce) throws Exception {
     TransOrder temp = this.transOrderService.selectByMchIdAndMchTransNo(transOrder.getMchId(), transOrder.getMchTransNo());
     if (temp == null) {
       this.transOrderService.create(transOrder);
     } else if (temp.getStatus().byteValue() == 2 && !isForce) {
       SaScoreFlow saScoreFlow = new SaScoreFlow();
       saScoreFlow.setMerchantOrderNumber(transOrder.getMchTransNo());
       List<SaScoreFlow> list = this.saScoreFlowRealService.selectList(saScoreFlow);
       if (list.size() > 0) {
         _log.warn("不可重复记账", new Object[0]);

         this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(transOrder.getTransOrderId());
         return CommonResult.error(ResultEnum.SETTLE_REPEAT.getCode().intValue(), "不可重复记账");
       }
     } else if (temp.getAmount().intValue() != transOrder.getAmount().intValue()) {
       throw new Exception("金额不一致，无法再次发起交易");
     }



     String transOrderId = transOrder.getTransOrderId();


     String channelId = transOrder.getChannelId();
     String amount = transOrder.getAmount().toString();
     _log.info("转账金额（单位分）:{}", new Object[] { amount });
     Map<String, Object> map = new HashMap<>();


     if (channelId.indexOf("RECHARGE") > 0) {
       map.put("isSuccess", Boolean.valueOf(true));
       map.put("channelOrderNo", transOrderId);
       return doInnerSettleTransReq(transOrder);
     }  if (channelId.indexOf("WITHDRAW") > 0) {
       String poundageStr = transOrder.getParam1();

       long poundage = Long.parseLong(poundageStr);
       _log.warn("冻结卡号：" + transOrder.getCardNum(), new Object[0]);
       boolean preFreezeRes = this.saCardService.freeze(transOrder.getChannelUser(), transOrder.getCardNum(), transOrder.getAmount().longValue(), CardDataTypeEnum.SETTLE.name(), transOrder.getCurrency(), "提现冻结", transOrder.getMchTransNo());
       if (preFreezeRes) {
         JSONObject obj = new JSONObject();
         obj.put("result", Boolean.valueOf(true));
         return CommonResult.success((Serializable)obj);
       }
       this.baseService4TransOrder.baseUpdateStatus4Fail(transOrderId, "preFreeze fail", "用户余额不足，提现冻结失败");
       throw new Exception("账户余额不足，提现冻结失败");
     }
     if (channelId.indexOf("TRANS") > 0)
       return doInnerSettleTransReq(transOrder);
     if (channelId.indexOf("CANCEL") > 0) {
       return doInnerSettleTransReq(transOrder);
     }

     throw new Exception("不支持的支付渠道类型[channelId=" + channelId + "]");
   }





   public CommonResult doInnerPlatformSettleToAgencyCreateReq(TransOrder transOrder) throws Exception {
     String channelId = transOrder.getChannelId();
     String amount = transOrder.getAmount().toString();
     _log.info("channelId :{}", new Object[] { channelId });
     _log.info("转账金额（单位分）:{}", new Object[] { amount });
     return doInnerSettleTransReq(transOrder);
   }







   public CommonResult doInnerSettleTransReq(TransOrder transOrder) throws Exception {
     String logPrefix = "【内部记账转账】";
     String transOrderId = transOrder.getTransOrderId();
     String mchId = transOrder.getMchId();
     String channelId = transOrder.getChannelId();
     Map<String, Object> map = new HashMap<>();
     map.put("transOrderId", transOrderId);
     map.put("isSuccess", Boolean.valueOf(false));

     _log.info("mchId:" + mchId + "     channelId:" + channelId, new Object[0]);
     this.baseService4TransOrder.baseUpdateStatus4Ing(transOrderId, null);
     JSONObject order = createInnerSettleTransOrder(transOrder);


     CommonResult result = this.dealCore.allDoing(order);
     _log.info("{}返回结果：{}", new Object[] { logPrefix, JSONObject.toJSONString(result) });

     if (result.getCode().intValue() == 200) {
       this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(transOrderId);
       int r = this.baseService4TransOrder.baseUpdateStatus4Success(transOrderId);
       if (r > 0) {
         return result;
       }
       return CommonResult.success(Integer.valueOf(1));
     }

     this.baseService4TransOrder.baseUpdateStatus4Fail(transOrderId, result.getCode().toString(), result.getMsg());
     return CommonResult.error("操作失败");
   }


   private JSONObject createInnerSettleTransOrder(TransOrder transOrder) throws Exception {
     int payMode;
     String channelMchId, mchId = transOrder.getMchId();
     String channelId = transOrder.getChannelId();
     String mchOrderId = transOrder.getMchTransNo();
     Long amountLong = transOrder.getAmount();
     String amount = Long.toString(transOrder.getAmount().longValue());
     String currency = transOrder.getCurrency().toUpperCase();
     JSONObject payChannel = this.payChannelForPayService.getByMchIdAndChannelId(mchId, channelId);
     JSONObject channelParam = payChannel.getJSONObject("param");
     String manageAccount = channelParam.getString("manageAccount");
     String dumbAccount = channelParam.getString("dumbAccount");

     String userId = transOrder.getChannelUser();

     String transOrderId = transOrder.getTransOrderId();
     JSONObject order = new JSONObject();
     JSONObject startOrder1 = new JSONObject();
     JSONObject startOrder2 = new JSONObject();
     JSONObject startOrder3 = new JSONObject();

     JSONObject endOrder1 = new JSONObject();
     JSONObject endOrder2 = new JSONObject();
     JSONObject endOrder3 = new JSONObject();
     JSONArray startList = new JSONArray();
     JSONArray endList1 = new JSONArray();
     JSONArray endList2 = new JSONArray();
     JSONArray endList3 = new JSONArray();
     JSONObject extra = JSONObject.parseObject(transOrder.getExtra());

     String price = extra.getString("price");
     String cutOff = extra.getString("cutOff");








     order.put("paySuccTime", transOrder.getTransSuccTime());
     order.put("innerSettle", "1");
     order.put("otherCardNum", transOrder.getOtherCardNum());
     order.put("otherId", transOrder.getOtherId());
     order.put("otherName", transOrder.getOtherName());
     order.put("dealComment", transOrder.getExtra());
     SaCard saCard = new SaCard();
     int currRunMode = this.runModeService.getRunModeCode();


     if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
       payMode = currRunMode;
       channelMchId = "1";
     } else {
       JSONObject param2 = JSONObject.parseObject(transOrder.getExtra());
       channelMchId = param2.getString("channelMchId");
       if (mchId.startsWith(channelMchId)) {

         payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
       } else {
         payMode = RunModeEnum.PUBLIC.getCode().intValue();
       }
     }

     if (channelId.indexOf("RECHARGE") > 0) {
       SaCard settleCard = this.cardService.selectByPrimaryKey(transOrder.getCardNum());
       MchInfo mchInfo = this.mchInfoService.selectMchInfo(transOrder.getMchId());
       List<SaCard> parentCards = this.cardService.getAllParentSettleCards(mchInfo, transOrder.getCurrency(), settleCard.getValidStartTime(), payMode, channelMchId);

       if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {

         MchInfo platform = this.mchInfoService.selectMchInfo("1");
         parentCards.add(this.cardService.getSettleCard(platform, transOrder.getCurrency(), settleCard.getValidStartTime(), currRunMode, "1"));
       }
       order.put("dealDetail", currency + PayEnum.BIZ_CLASS_RECHARGE.getMessage() + "：" + amount);
       order.put("productCode", PayEnum.BIZ_CLASS_RECHARGE.getCode());
       order.put("productName", "CZ");
       order.put("comment", PayEnum.BIZ_CLASS_RECHARGE.getMessage() + currency);

       order.put("freezeTime", null);

       order.put("userId", userId);
       order.put("phone", userId);
       order.put("refundNumber", null);
       order.put("mchOrderNo", mchOrderId);
       order.put("amount", amount);
       order.put("cardType", CardDataTypeEnum.SETTLE.name());
       order.put("currency", currency);
       order.put("dealType", "1");
       order.put("cardNum", transOrder.getCardNum());
       order.put("price", price);
       order.put("cutOff", cutOff);


       JSONArray otherUsers = new JSONArray();
       JSONObject otherUser = new JSONObject();
       otherUser.put("userId", dumbAccount);
       otherUser.put("cardType", CardDataTypeEnum.DUMB.name());

       saCard.setUserId(dumbAccount);
       saCard.setCardType(CardDataTypeEnum.DUMB.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       otherUser.put("cardNum", saCard.getCardNumber());
       otherUsers.add(otherUser);

       order.put("otherUserId", otherUsers.toJSONString());


       endOrder1.put("userId", userId);
       endOrder1.put("phone", userId);
       endOrder1.put("productCode", PayEnum.BIZ_CLASS_SETTLE_INCOME.getCode());
       endOrder1.put("productName", "SR");
       endOrder1.put("comment", PayEnum.BIZ_CLASS_SETTLE_INCOME.getMessage());
       endOrder1.put("mchOrderNo", mchOrderId);
       endOrder1.put("amount", amount);
       endOrder1.put("cardType", CardDataTypeEnum.SETTLE.name());
       endOrder1.put("currency", currency);
       endOrder1.put("dealType", "1");
       endOrder1.put("cardNum", transOrder.getCardNum());
       endList1.add(endOrder1);

       int size = parentCards.size();

       for (int n = 0; n < size; n++) {
         SaCard parentCard = parentCards.get(n);
         if (parentCard.getUserId().startsWith(channelMchId)) {



           JSONObject temp = new JSONObject();
           temp.put("userId", parentCard.getUserId());
           temp.put("phone", parentCard.getUserId());
           if (mchInfo.getMchId().equals(parentCard.getUserId())) {
             temp.put("productCode", PayEnum.BIZ_CLASS_SALE_INCOME.getCode());
             temp.put("productName", "SI");
             temp.put("comment", PayEnum.BIZ_CLASS_SALE_INCOME.getMessage());
           } else {
             temp.put("productCode", PayEnum.BIZ_CLASS_SETTLE_RECORD.getCode());
             temp.put("productName", "SR");
             temp.put("comment", PayEnum.BIZ_CLASS_SETTLE_RECORD.getMessage());
           }
           temp.put("mchOrderNo", mchOrderId);
           temp.put("amount", amount);
           temp.put("cardType", CardDataTypeEnum.SETTLE.name());
           temp.put("currency", currency);
           temp.put("dealType", "1");
           temp.put("cardNum", parentCard.getCardNumber());
           endList1.add(temp);
         }
       }

       startOrder1.put("userId", dumbAccount);
       startOrder1.put("phone", dumbAccount);
       startOrder1.put("productCode", PayEnum.BIZ_CLASS_RECHARGE_DUMB_EXP.getCode());
       startOrder1.put("productName", "CZ");
       startOrder1.put("comment", PayEnum.BIZ_CLASS_RECHARGE_DUMB_EXP.getMessage());
       startOrder1.put("mchOrderNo", mchOrderId);
       startOrder1.put("amount", Long.toString(amountLong.longValue()));
       startOrder1.put("cardType", CardDataTypeEnum.DUMB.name());
       startOrder1.put("currency", currency);
       startOrder1.put("dealType", "0");
       startOrder1.put("cardNum", saCard.getCardNumber());



















       startOrder1.put("end", endList1);
       startList.add(startOrder1);
       order.put("orderList", startList);
       return order;
     }  if (channelId.indexOf("CANCEL") > 0) {
       order.put("dealDetail", currency + PayEnum.BIZ_CLASS_CANCEL.getMessage() + "：" + amount);
       order.put("productCode", PayEnum.BIZ_CLASS_CANCEL.getCode());
       order.put("productName", "CZ");
       order.put("comment", PayEnum.BIZ_CLASS_CANCEL.getMessage() + currency);

       order.put("freezeTime", null);

       order.put("userId", userId);
       order.put("phone", userId);
       order.put("refundNumber", null);
       order.put("mchOrderNo", mchOrderId);
       order.put("amount", amount);
       order.put("cardType", CardDataTypeEnum.SETTLE.name());
       order.put("currency", currency);
       order.put("dealType", DealTypeEnum.EXP.getCode());
       order.put("cardNum", transOrder.getCardNum());

       JSONArray otherUsers = new JSONArray();
       JSONObject otherUser = new JSONObject();
       otherUser.put("userId", dumbAccount);
       otherUser.put("cardType", CardDataTypeEnum.DUMB.name());

       saCard.setUserId(dumbAccount);
       saCard.setCardType(CardDataTypeEnum.DUMB.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       otherUser.put("cardNum", saCard.getCardNumber());
       otherUsers.add(otherUser);

       order.put("otherUserId", otherUsers.toJSONString());

       startOrder1.put("userId", dumbAccount);
       startOrder1.put("phone", dumbAccount);
       startOrder1.put("productCode", PayEnum.BIZ_CLASS_CANCEL_DUMB_INCOME.getCode());
       startOrder1.put("productName", "XZ");
       startOrder1.put("comment", PayEnum.BIZ_CLASS_CANCEL_DUMB_INCOME.getMessage());
       startOrder1.put("mchOrderNo", mchOrderId);
       startOrder1.put("amount", Long.toString(amountLong.longValue() * 2L));
       startOrder1.put("cardType", CardDataTypeEnum.DUMB.name());
       startOrder1.put("currency", currency);
       startOrder1.put("dealType", DealTypeEnum.INCOME.getCode());
       startOrder1.put("cardNum", saCard.getCardNumber());

       endOrder1.put("userId", userId);
       endOrder1.put("phone", userId);
       endOrder1.put("productCode", PayEnum.BIZ_CLASS_CANCEL_EXP.getCode());
       endOrder1.put("productName", "XZ");
       endOrder1.put("comment", PayEnum.BIZ_CLASS_CANCEL_EXP.getMessage());
       endOrder1.put("mchOrderNo", mchOrderId);
       endOrder1.put("amount", amount);
       endOrder1.put("cardType", CardDataTypeEnum.SETTLE.name());
       endOrder1.put("currency", currency);
       endOrder1.put("dealType", DealTypeEnum.EXP.getCode());
       endOrder1.put("cardNum", transOrder.getCardNum());

       endList1.add(endOrder1);
       endOrder2.put("userId", manageAccount);
       endOrder2.put("phone", manageAccount);
       endOrder2.put("productCode", PayEnum.BIZ_CLASS_CANCEL_EXP.getCode());
       endOrder2.put("productName", "XZ");
       endOrder2.put("comment", PayEnum.BIZ_CLASS_CANCEL_EXP.getMessage());
       endOrder2.put("mchOrderNo", mchOrderId);
       endOrder2.put("amount", amount);
       endOrder2.put("cardType", CardDataTypeEnum.TRUSTEESHIP.name());
       endOrder2.put("currency", currency);
       endOrder2.put("dealType", DealTypeEnum.EXP.getCode());
       saCard = new SaCard();
       saCard.setUserId(manageAccount);
       saCard.setCardType(CardDataTypeEnum.TRUSTEESHIP.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       endOrder2.put("cardNum", saCard.getCardNumber());

       endList1.add(endOrder2);
       startOrder1.put("end", endList1);
       startList.add(startOrder1);
       order.put("orderList", startList);
       return order;
     }  if (channelId.indexOf("WITHDRAW") > 0) {
       String poundageStr = transOrder.getParam1();

       long poundage = Long.parseLong(poundageStr);

       _log.info("冻结代管账户金额：" + transOrder.getAmount(), new Object[0]);
       boolean preFreezeRes = this.saCardService.freeze(manageAccount, transOrder.getAmount().longValue(), CardDataTypeEnum.TRUSTEESHIP.name(), transOrder.getCurrency(), "代管账户提现冻结", transOrder.getMchTransNo());
       if (!preFreezeRes) {
         this.baseService4TransOrder.baseUpdateStatus4Fail(transOrderId, "preFreeze fail", "用户余额不足，提现冻结失败");
         throw new Exception("代管账户冻结失败");
       }
       order.put("dealDetail", currency + PayEnum.BIZ_CLASS_WITHDRAW.getMessage() + "：" + amount);
       order.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW.getCode());
       order.put("productName", "TB");
       order.put("comment", PayEnum.BIZ_CLASS_WITHDRAW.getMessage() + currency);
       order.put("freezeTime", null);
       order.put("channelId", channelId);

       order.put("userId", userId);
       order.put("phone", userId);
       order.put("mchOrderNo", mchOrderId);
       order.put("amount", amount);
       order.put("cardType", CardDataTypeEnum.SETTLE.name());
       order.put("currency", currency);
       order.put("dealType", "0");
       order.put("cardNum", transOrder.getCardNum());

       JSONArray otherUsers = new JSONArray();
       JSONObject otherUser = new JSONObject();
       otherUser.put("userId", dumbAccount);
       otherUser.put("cardType", CardDataTypeEnum.DUMB.name());
       saCard.setUserId(dumbAccount);
       saCard.setCardType(CardDataTypeEnum.DUMB.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       otherUser.put("cardNum", saCard.getCardNumber());
       otherUsers.add(otherUser);

       order.put("otherUserId", otherUsers.toJSONString());

       startOrder1.put("userId", userId);
       startOrder1.put("phone", userId);
       startOrder1.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_EXP.getCode());
       startOrder1.put("productName", "WITHDRAW");
       startOrder1.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_EXP.getMessage());
       startOrder1.put("mchOrderNo", mchOrderId);
       startOrder1.put("amount", Long.valueOf(amountLong.longValue() - poundage));
       startOrder1.put("currency", currency);
       startOrder1.put("cardType", CardDataTypeEnum.SETTLE.name());
       startOrder1.put("dealType", "0");
       startOrder1.put("cardNum", transOrder.getCardNum());


       endOrder1.put("userId", dumbAccount);
       endOrder1.put("phone", dumbAccount);
       endOrder1.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_DUMB_INCOME.getCode());
       endOrder1.put("productName", "TB");
       endOrder1.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_DUMB_INCOME.getMessage());
       endOrder1.put("mchOrderNo", mchOrderId);
       endOrder1.put("amount", Long.toString(amountLong.longValue() - poundage));
       endOrder1.put("cardType", CardDataTypeEnum.DUMB.name());
       endOrder1.put("currency", currency);
       endOrder1.put("dealType", "1");
       endOrder1.put("cardNum", saCard.getCardNumber());
       endList1.add(endOrder1);
       startOrder1.put("end", endList1);
       startList.add(startOrder1);


       endOrder3.put("userId", dumbAccount);
       endOrder3.put("phone", dumbAccount);
       endOrder3.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_DUMB_INCOME.getCode());
       endOrder3.put("productName", "WITHDRAW");
       endOrder3.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_DUMB_INCOME.getMessage());
       endOrder3.put("mchOrderNo", mchOrderId);
       endOrder3.put("amount", Long.toString(amountLong.longValue() - poundage));
       endOrder3.put("cardType", CardDataTypeEnum.DUMB.name());
       endOrder3.put("currency", currency);
       endOrder3.put("dealType", "1");
       endOrder3.put("cardNum", saCard.getCardNumber());

       startOrder2.put("userId", manageAccount);
       startOrder2.put("phone", manageAccount);
       startOrder2.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_TRUST_EXP.getCode());
       startOrder2.put("productName", "WITHDRAW");
       startOrder2.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_TRUST_EXP.getMessage());
       startOrder2.put("mchOrderNo", mchOrderId);
       startOrder2.put("amount", Long.toString(amountLong.longValue() - poundage));
       startOrder2.put("cardType", CardDataTypeEnum.TRUSTEESHIP.name());
       startOrder2.put("currency", currency);
       startOrder2.put("dealType", "0");
       saCard = new SaCard();
       saCard.setUserId(manageAccount);
       saCard.setCardType(CardDataTypeEnum.TRUSTEESHIP.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       startOrder2.put("cardNum", saCard.getCardNumber());

       endList2.add(endOrder3);
       startOrder2.put("end", endList2);
       startList.add(startOrder2);

       startOrder3.put("userId", userId);
       startOrder3.put("phone", userId);
       startOrder3.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_POUNDAGE_EXP.getCode());
       startOrder3.put("productName", "WITHDRAW");
       startOrder3.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_POUNDAGE_EXP.getMessage());
       startOrder3.put("mchOrderNo", mchOrderId);
       startOrder3.put("amount", poundageStr);
       startOrder3.put("cardType", CardDataTypeEnum.SETTLE.name());
       startOrder3.put("currency", currency);
       startOrder3.put("dealType", "0");
       startOrder3.put("cardNum", transOrder.getCardNum());

       endOrder2.put("userId", payChannel.getString("channelMchId"));
       endOrder2.put("phone", payChannel.getString("channelMchId"));
       endOrder2.put("productCode", PayEnum.BIZ_CLASS_WITHDRAW_POUNDAGE_INCOME.getCode());
       endOrder2.put("productName", "WITHDRAW");
       endOrder2.put("comment", PayEnum.BIZ_CLASS_WITHDRAW_POUNDAGE_INCOME.getMessage());
       endOrder2.put("mchOrderNo", mchOrderId);
       endOrder2.put("amount", poundageStr);
       endOrder2.put("cardType", CardDataTypeEnum.PAYMENT.name());
       endOrder2.put("currency", currency);
       endOrder2.put("dealType", "1");
       endOrder2.put("cardNum", transOrder.getCardNum());
       saCard = new SaCard();
       saCard.setUserId(payChannel.getString("channelMchId"));
       saCard.setCardType(CardDataTypeEnum.PAYMENT.name());
       saCard.setCurrency(currency);
       saCard = this.saCardService.baseInfo(saCard);
       endOrder2.put("cardNum", saCard.getCardNumber());

       endList3.add(endOrder2);
       startOrder3.put("end", endList3);
       startList.add(startOrder3);
       order.put("orderList", startList);

       order.put("preFreeze", Boolean.valueOf(true));
       return order;
     }  if (channelId.indexOf("TRANS") > 0) {
       order.put("dealDetail", currency + PayEnum.BIZ_CLASS_TRANS.getMessage() + "：" + amount);
       order.put("productCode", PayEnum.BIZ_CLASS_TRANS.getCode());
       order.put("productName", "TRANS");
       order.put("comment", PayEnum.BIZ_CLASS_TRANS.getMessage() + currency);
       order.put("freezeTime", null);
       order.put("channelId", channelId);

       order.put("userId", userId);
       order.put("phone", userId);
       order.put("mchOrderNo", mchOrderId);
       order.put("amount", amount);
       order.put("cardType", CardDataTypeEnum.SETTLE.name());
       order.put("currency", currency);
       order.put("dealType", "0");
       order.put("cardNum", transOrder.getCardNum());

       startOrder1.put("userId", userId);
       startOrder1.put("phone", userId);
       startOrder1.put("productCode", PayEnum.BIZ_CLASS_TRANS_EXP.getCode());
       startOrder1.put("productName", "TRANS");
       startOrder1.put("comment", PayEnum.BIZ_CLASS_TRANS_EXP.getMessage());
       startOrder1.put("mchOrderNo", mchOrderId);
       startOrder1.put("amount", amount);
       startOrder1.put("cardType", CardDataTypeEnum.SETTLE.name());
       startOrder1.put("currency", currency);
       startOrder1.put("dealType", "0");
       startOrder1.put("cardNum", transOrder.getCardNum());
       JSONArray otherUsers = new JSONArray();
       JSONArray userList = JSONArray.parseArray(transOrder.getParam2());

       for (int i = 0; i < userList.size(); i++) {
         JSONObject userItem = (JSONObject)userList.get(i);
         JSONObject endOrder = new JSONObject();
         String id = userItem.getString("userId");
         String cardNum = userItem.getString("cardNum");
         JSONObject otherUser = new JSONObject();
         otherUser.put("cardNum", cardNum);
         otherUser.put("userId", id);
         otherUser.put("cardType", CardDataTypeEnum.SETTLE.name());
         otherUsers.add(otherUser);

         endOrder.put("cardNum", cardNum);
         endOrder.put("userId", id);
         endOrder.put("phone", id);
         endOrder.put("productCode", PayEnum.BIZ_CLASS_TRANS_INCOME.getCode());
         endOrder.put("productName", "TRANS");
         endOrder.put("comment", PayEnum.BIZ_CLASS_TRANS_INCOME.getMessage());
         endOrder.put("mchOrderNo", mchOrderId);
         endOrder.put("amount", userItem.getString("amount"));
         endOrder.put("cardType", CardDataTypeEnum.SETTLE.name());
         endOrder.put("currency", currency);
         endOrder.put("dealType", "1");
         endList1.add(endOrder);
       }
       order.put("otherUserId", otherUsers.toJSONString());
       startOrder1.put("end", endList1);





       startList.add(startOrder1);
       order.put("orderList", startList);
       return order;
     }
     return null;
   }


   public CommonResult syncSettle(String params) throws Exception {
     PayOrder payOrder = (PayOrder)JSON.parseObject(params, PayOrder.class);
     return syncSettle(payOrder);
   }

   public CommonResult syncSettle(PayOrder payOrder) throws Exception {
     PayOrder existOrder = this.baseService4PayOrder.baseSelectPayOrder(payOrder.getPayOrderId());
     if (existOrder != null) {

       CommonResult commonResult = CommonResult.success("同步订单成功");
       commonResult.setData((Serializable)existOrder);
       return commonResult;
     }
     String mchId = payOrder.getMchId();
     String payOrderId = payOrder.getPayOrderId();
     String channelId = payOrder.getChannelId();
     String[][] ids = UnionIdUtil.getIdInfoFromUnionId(mchId);

     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     if (mchInfo == null) {
       System.out.println("平台代理信息不存在:" + mchId + ":尝试进行补偿同步信息");
       this.platformService.createAgencyInfo(mchId);
     }




     try {
       if (channelId.contains("WX") || channelId
         .contains("ALIPAY")) {
         PayOrder temp = this.baseService4PayOrder.baseSelectPayOrder(payOrderId);
         if (temp != null) {
           temp.setStatus(payOrder.getStatus());
           this.baseService4PayOrder.baseUpdateStatus(payOrderId, payOrder.getStatus().byteValue(), payOrder.getChannelOrderNo(), DateUtils.getDate(payOrder.getPaySuccTime()));
         } else {
           int r = this.baseService4PayOrder.baseCreatePayOrder(payOrder);
           if (r == 0) {
             throw new Exception("补偿创建订单失败");
           }
         }
       }

       return thirdPayToIncomingOfMch(payOrderId, UnionIdUtil.getIdInfoFromUnionId(mchId)[0][0], channelId);
     } catch (Exception e) {
       e.printStackTrace();
       throw new Exception("同步记录订单失败");
     }
   }





   private boolean checkValidate(SaCard saCard) throws Exception {
     int dateStatus = DateUtils.between(LocalDateTime.now(), saCard.getValidStartTime(), saCard.getValidEndTime());
     if (dateStatus == 0)
       throw new Exception("当前时间早于记账有效期");
     if (dateStatus == 1) {
       throw new Exception("当前正在记账中");
     }
     _log.info("记账结束，可以确认账单", new Object[0]);
     return true;
   }
 }





