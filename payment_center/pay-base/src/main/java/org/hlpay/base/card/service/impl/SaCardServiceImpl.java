 package org.hlpay.base.card.service.impl;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.github.pagehelper.PageHelper;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.ibatis.annotations.Param;
 import org.hlpay.base.card.common.PageBean;
 import org.hlpay.base.card.service.CardBaseService;
 import org.hlpay.base.card.service.SaCardService;
 import org.hlpay.base.model.FreezeInfo;
 import org.hlpay.base.model.InnerSettleInfo;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SaDealRecord;
 import org.hlpay.base.plugin.PageModel;
 import org.hlpay.base.service.CardService;
 import org.hlpay.common.enumm.FreezeActionEnum;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.core.ValueOperations;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;


 @Service
 public class SaCardServiceImpl
   extends CardBaseService
   implements SaCardService
 {
   private static final MyLog _log = MyLog.getLog(SaCardServiceImpl.class); @Autowired
   private CardService cardService; public List<SaCard> infoSaCard(String userId) throws Exception {
     return super.infoSaCard(userId);
   } @Resource
   RedisTemplate<String, Object> redisTemplate; @Autowired
   private SaDealRecordServiceImpl saDealRecordService;
   public int updateSaCard(String cardNumber, String flag, String remainPart) throws Exception {
     return super.updateSaCard(cardNumber, flag, remainPart);
   }


   public int insertSaCard(SaCard saCard) throws Exception {
     return super.insertSaCard(saCard);
   }


   public PageBean listSaCard(int start, int end) throws Exception {
     return super.listSaCard(start, end);
   }



   public int updateAll(SaCard saCard) throws Exception {
     return super.updateAll(saCard);
   }


   public int update(Map<String, Long> map) throws Exception {
     return updateCardRemainPart(map);
   }












   public List<SaCard> getCardsByUserIdAndCardType(String userId, String cardType) {
     return this.cardService.getCardsByUserIdAndCardType(userId, cardType);
   }


   public List<SaCard> getSettleCards(String userId, String cardType, Long settleTime) {
     return this.cardService.getCardsByUserIdAndCardType(userId, cardType);
   }


   public void updateCard(SaCard saCard) throws Exception {
     this.cardService.updateCard(saCard);
   }


   public int baseInsert(SaCard saCard) throws Exception {
     return this.cardService.baseInsert(saCard);
   }


   public int baseDelete(String s) throws Exception {
     return this.cardService.baseDelete(s);
   }


   public int baseUpdate(SaCard saCard) throws Exception {
     return this.cardService.baseUpdate(saCard);
   }


   public int baseUpdateList(@Param("list") List<SaCard> list) throws Exception {
     return this.cardService.baseUpdateList(list);
   }


   public int doInnerSettle(InnerSettleInfo innerSettleInfo) {
     return this.cardService.doInnerSettle(innerSettleInfo);
   }


   public int doInnerVirtualSettle(InnerSettleInfo innerSettleInfo) {
     return this.cardService.doInnerVirtualSettle(innerSettleInfo);
   }


   public int doInnerVirtualSettleToReal(InnerSettleInfo innerSettleInfo) {
     return this.cardService.doInnerVirtualSettleToReal(innerSettleInfo);
   }


   public int doInnerScheduleSettle(InnerSettleInfo innerSettleInfo) {
     return this.cardService.doInnerScheduleSettle(innerSettleInfo);
   }



   public int updateCardList(@Param("list") List<SaCard> list) throws Exception {
     return this.cardService.updateCardList(list);
   }



   public List<SaCard> baseLimitSelect(Integer start, Integer end, SaCard saCard) throws Exception {
     PageHelper.offsetPage(start.intValue(), end.intValue(), false);
     return this.cardService.baseLimitSelect(saCard);
   }


   public SaCard baseInfo(SaCard saCard) throws Exception {
     return this.cardService.baseInfo(saCard);
   }


   public List<SaCard> descSaCard() throws Exception {
     return this.cardService.descSaCard();
   }


   public List<SaCard> get(String key) throws Exception {
     try {
       ValueOperations<String, Object> vo = this.redisTemplate.opsForValue();
       return (List<SaCard>)vo.get(key);
     } catch (Exception e) {
       e.printStackTrace();

       return null;
     }
   }

   public void set(String key, List<SaCard> value) throws Exception {
     ValueOperations<String, Object> vo = this.redisTemplate.opsForValue();
     if (this.redisTemplate.hasKey(key).booleanValue()) {
       this.redisTemplate.delete(key);
       vo.set(key, value);
     } else {
       vo.set(key, value);
     }
   }


   public SaCard getSaCardInfoByAccount(SaCard saCard) {
     return this.cardService.getSaCardInfoByAccount(saCard);
   }


   public int addSaCard(List<SaCard> list) {
     return this.cardService.addSaCard(list);
   }


   public int updateData(SaCard saCard) throws Exception {
     return this.cardService.updateData(saCard);
   }


   @Transactional(rollbackFor = {Exception.class})
   public String updateFreeze(SaCard saCard, JSONObject jo, String currency) throws Exception {
     PageModel pageModel = new PageModel();
     saCard = new SaCard();
     String cardType = jo.getString("cardType");
     String userId = jo.getString("userId");
     Integer flag = jo.getInteger("flag");
     Long freezePart = jo.getLong("freezePart");
     String mchOrderNo = jo.getString("mchOrderNo");

     String productName = jo.getString("productName");

     saCard.setCardType(cardType);
     saCard.setCurrency(currency);
     saCard.setUserId(userId);
     saCard = this.cardService.baseInfo(saCard);

     String cardNumber = saCard.getCardNumber();
     String tempFlag = "4";
     if (flag.equals("0")) {
       tempFlag = "3";
       Long remainPart = saCard.getRemainPart();
       if (remainPart.longValue() < freezePart.longValue()) {
         pageModel.setCode(Integer.valueOf(1));
         pageModel.setMsg("卡余额小于冻结余额!");
         return JSONObject.toJSONString(pageModel);
       }
     }
     if (flag.equals("1")) {
       tempFlag = "4";
       Long fp = saCard.getFreezePart();
       if (fp.longValue() < freezePart.longValue()) {
         pageModel.setCode(Integer.valueOf(1));
         pageModel.setMsg("卡冻结余额小于解冻余额!");
         return JSONObject.toJSONString(pageModel);
       }
     }
     SaDealRecord saDealRecord = new SaDealRecord();
     saDealRecord.setMerchantOrderNumber(mchOrderNo);
     saDealRecord.setRefundNumber("0");
     saDealRecord.setDealType(tempFlag);
     saDealRecord.setUserId(userId);
     saDealRecord.setUserAmount(freezePart);
     saDealRecord.setOneselfCardNumber(cardNumber);
     saDealRecord.setProductType(productName);
     this.saDealRecordService.baseInsert(saDealRecord);

     saCard = new SaCard();
     saCard.setUserId(userId);
     saCard.setCardType(cardType);
     saCard.setCurrency(currency);
     saCard.setFreezePart(freezePart);
     saCard.setFlag(flag);
     this.cardService.updateFreeze(saCard);

     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     return JSONObject.toJSONString(pageModel);
   }


   @Transactional(rollbackFor = {Exception.class})
   public String updateFreeze(FreezeInfo freezeInfo) throws Exception {
     PageModel pageModel = new PageModel();
     SaCard saCard = new SaCard();
     String cardType = freezeInfo.getCardType();
     String currency = freezeInfo.getCurrency();
     String userId = freezeInfo.getUserId();
     FreezeActionEnum action = freezeInfo.getAction();
     Long freezePart = freezeInfo.getFreezePart();
     String mchOrderNo = freezeInfo.getMchOrderNo();
     String productCode = freezeInfo.getProductCode();
     String productName = freezeInfo.getProductName();
     String productType = freezeInfo.getProductType();

     saCard.setCardType(cardType);
     saCard.setCurrency(currency);
     saCard.setUserId(userId);
     if (StringUtils.isNotBlank(freezeInfo.getCardNum())) {
       saCard.setCardNumber(freezeInfo.getCardNum());
     }
     saCard = this.cardService.baseInfo(saCard);

     String cardNumber = saCard.getCardNumber();
     _log.info("冻结卡号：" + cardNumber, new Object[0]);
     Integer tempFlag = Integer.valueOf(4);
     Integer flag = Integer.valueOf(0);
     if (action == FreezeActionEnum.FREEZE) {
       tempFlag = Integer.valueOf(3);
       flag = Integer.valueOf(0);
       Long remainPart = saCard.getRemainPart();
       if (remainPart.longValue() < freezePart.longValue()) {
         pageModel.setCode(Integer.valueOf(1));
         pageModel.setMsg("卡余额小于冻结余额!");
         return JSONObject.toJSONString(pageModel);
       }
     }
     if (action == FreezeActionEnum.UNFREEZE) {
       tempFlag = Integer.valueOf(4);
       flag = Integer.valueOf(1);
       Long fp = saCard.getFreezePart();
       if (fp.longValue() < freezePart.longValue()) {
         pageModel.setCode(Integer.valueOf(1));
         pageModel.setMsg("卡冻结余额小于解冻余额!");
         return JSONObject.toJSONString(pageModel);
       }
     }
     SaDealRecord saDealRecord = new SaDealRecord();
     saDealRecord.setMerchantOrderNumber(mchOrderNo);
     saDealRecord.setRefundNumber("0");
     saDealRecord.setDealType(tempFlag.toString());
     saDealRecord.setUserId(userId);
     saDealRecord.setUserAmount(freezePart);
     saDealRecord.setOneselfCardNumber(cardNumber);
     saDealRecord.setProductType(productName);
     this.saDealRecordService.baseInsert(saDealRecord);

     saCard = new SaCard();
     saCard.setUserId(userId);
     saCard.setCardType(cardType);
     saCard.setCurrency(currency);
     saCard.setFreezePart(freezePart);
     saCard.setFlag(flag);
     if (StringUtils.isNotBlank(cardNumber)) {
       saCard.setCardNumber(cardNumber);
     }
     this.cardService.updateFreeze(saCard);

     pageModel.setCode(Integer.valueOf(0));
     pageModel.setMsg("success");
     return JSONObject.toJSONString(pageModel);
   }


   public boolean freeze(String userId, long amount, String cardType, String currency, String productName, String mchOrderNo) {
     FreezeInfo freezeInfo = new FreezeInfo();
     freezeInfo.setUserId(userId);
     freezeInfo.setCardType(cardType);
     freezeInfo.setCurrency(currency);
     freezeInfo.setAction(FreezeActionEnum.FREEZE);
     freezeInfo.setProductName(productName);
     freezeInfo.setFreezePart(Long.valueOf(amount));
     freezeInfo.setMchOrderNo(mchOrderNo);

     try {
       String str = updateFreeze(freezeInfo);
       JSONObject resultObj = JSON.parseObject(str);

       String code = resultObj.getString("code");
       if ("0".equals(code)) {
         return true;
       }
     } catch (Exception e) {
       e.printStackTrace();
       return false;
     }
     return false;
   }


   public boolean freeze(String userId, String cardNum, long amount, String cardType, String currency, String productName, String mchOrderNo) {
     FreezeInfo freezeInfo = new FreezeInfo();
     freezeInfo.setUserId(userId);
     freezeInfo.setCardType(cardType);
     freezeInfo.setCurrency(currency);
     freezeInfo.setAction(FreezeActionEnum.FREEZE);
     freezeInfo.setProductName(productName);
     freezeInfo.setFreezePart(Long.valueOf(amount));
     freezeInfo.setMchOrderNo(mchOrderNo);
     freezeInfo.setCardNum(cardNum);

     try {
       String str = updateFreeze(freezeInfo);
       JSONObject resultObj = JSON.parseObject(str);

       String code = resultObj.getString("code");
       if ("0".equals(code)) {
         return true;
       }
     } catch (Exception e) {
       e.printStackTrace();
       return false;
     }
     return false;
   }


   public boolean unfreeze(String userId, long amount, String cardType, String currency, String productName, String mchOrderNo) {
     FreezeInfo freezeInfo = new FreezeInfo();
     freezeInfo.setUserId(userId);
     freezeInfo.setCardType(cardType);
     freezeInfo.setCurrency(currency);
     freezeInfo.setAction(FreezeActionEnum.FREEZE);
     freezeInfo.setProductName(productName);
     freezeInfo.setFreezePart(Long.valueOf(amount));
     freezeInfo.setMchOrderNo(mchOrderNo);
     try {
       String str = updateFreeze(freezeInfo);
       JSONObject resultObj = JSON.parseObject(str);

       String code = resultObj.getString("code");
       if ("0".equals(code)) {
         return true;
       }
     } catch (Exception e) {
       e.printStackTrace();
       return false;
     }
     return false;
   }
 }





