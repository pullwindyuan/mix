 package org.hlpay.base.card.core;

 import com.alibaba.fastjson.JSONObject;
 import java.time.LocalDateTime;
 import java.util.Date;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.card.core.base.BaseCore;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.SaDealRecord;
 import org.hlpay.base.model.SaOrder;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;




 @Component
 public class DealCore
   extends BaseCore
 {
   private static final MyLog _log = MyLog.getLog(DealCore.class);



   @Resource
   private OrderCore orderCore;


   @Autowired
   private MchInfoService mchInfoService;



   public CommonResult allDoing(JSONObject jsonObj) throws Exception {
     SaDealRecord saDealRecord = retSaDealRecord(jsonObj);
     return this.orderCore.saveV1(saDealRecord);
   }

   public SaDealRecord retSaDealRecord(JSONObject jsonObj) throws Exception {
     SaDealRecord saDealRecord = new SaDealRecord();
     if (jsonObj.get("price") != null) {
       saDealRecord.setPrice(jsonObj.getLong("price"));
     }
     if (jsonObj.get("cutOff") != null) {
       saDealRecord.setCutOff(jsonObj.getLong("cutOff"));
     }











     saDealRecord.setPreFreeze(jsonObj.getBooleanValue("preFreeze"));
     saDealRecord.setOrderList(jsonObj.getJSONArray("orderList"));
     saDealRecord.setKernelCardNum(jsonObj.getString("cardNum"));
     saDealRecord.setOrderDetail(jsonObj.toJSONString());
     saDealRecord.setDealDetail(jsonObj.getString("dealDetail"));
     saDealRecord.setFlag(jsonObj.getString("productCode"));
     saDealRecord.setProductType(jsonObj.getString("productName"));
     saDealRecord.setTripTime(jsonObj.getString("freezeTime"));
     saDealRecord.setChannelId(jsonObj.getString("channelId"));
     String userId = jsonObj.getString("userId");
     saDealRecord.setUserId(userId);
     saDealRecord.setLoginAccount(jsonObj.getString("phone"));
     saDealRecord.setUserPhone(jsonObj.getString("phone"));

     saDealRecord.setCardType(jsonObj.getString("cardType"));
     saDealRecord.setCurrency(jsonObj.getString("currency"));
     saDealRecord.setDealType(jsonObj.getString("dealType"));
     saDealRecord.setMerchantOrderNumber(jsonObj.getString("mchOrderNo"));
     saDealRecord.setDealStatus(Character.valueOf('0'));

     if ("1".equals(jsonObj.getString("innerSettle"))) {
       MchInfo mchInfo = this.mchInfoService.selectMchInfo(userId);
       saDealRecord.setUserName(mchInfo.getName());
       saDealRecord.setOtherCardNumber(jsonObj.getString("otherCardNum"));
       saDealRecord.setDealComment(jsonObj.getString("dealComment"));
     }
     Date date = jsonObj.getDate("paySuccTime");
     saDealRecord.setExternalPaySuccessTime(
         LocalDateTime.of(DateUtils.getYears(date),
           DateUtils.getMonth(date) + 1,
           DateUtils.getDay(date),
           DateUtils.getHours(date),
           DateUtils.getMinutes(date),
           DateUtils.getSecond(date)));
     if (!StringUtils.isBlank(jsonObj.getString("refundNumber"))) {
       saDealRecord.setRefundNumber(jsonObj.getString("refundNumber"));
     } else {
       saDealRecord.setRefundNumber("0");
     }
     saDealRecord.setScoreReturnAmount(Long.valueOf(jsonObj.getString("amount")));














     if (StringUtils.isNotBlank(jsonObj.getString("innerSettle"))) {
       saDealRecord.setOneselfCardNumber(saDealRecord.getKernelCardNum());
       saDealRecord.setOthersCardNumber(jsonObj.getString("otherUserId"));
     }
     return saDealRecord;
   }

   public SaOrder getSaOrderInstance(SaDealRecord saDealRecord) {
     SaOrder saOrder = new SaOrder();
     saOrder.setDealRecordNumber(saDealRecord.getDealRecordNumber());
     saOrder.setMerchantOrderNumber(saDealRecord.getMerchantOrderNumber());
     saOrder.setProxyId(saDealRecord.getProxyNumber());
     saOrder.setDealType(Character.valueOf(saDealRecord.getDealType().charAt(0)));
     saOrder.setProductType(saDealRecord.getProductType());
     saOrder.setCalculateTime(null);
     saOrder.setOrderAmount(saDealRecord.getOrderAmount());
     saOrder.setUserAmount(saDealRecord.getUserAmount());
     saOrder.setPartnerAmount(saDealRecord.getPartnerAmount());
     saOrder.setDealBringScoreType(saDealRecord.getDealBringScoreType());
     saOrder.setDealBringScoreRate(saDealRecord.getDealBringScoreRate());
     saOrder.setScoreReturnAmount(saDealRecord.getScoreReturnAmount());
     saOrder.setDealComment(saDealRecord.getDealComment());
     saOrder.setDealStatus(saDealRecord.getDealStatus());
     saOrder.setDealCreateTime(saDealRecord.getDealCreateTime());

     saOrder.setUserId(saDealRecord.getUserId());
     return saOrder;
   }
 }

