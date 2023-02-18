 package org.hlpay.base.service;
 import com.alibaba.fastjson.JSONObject;
 import com.github.pagehelper.StringUtil;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;

 import org.hlpay.base.mapper.SaCardDao;
 import org.hlpay.base.mapper.SaCardMapper;
 import org.hlpay.base.mapper.SaDealRecordMapper;
 import org.hlpay.base.model.InnerSettleInfo;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SaCardData;
 import org.hlpay.base.model.SaCardExample;
 import org.hlpay.base.model.SaSettleCard;
 import org.hlpay.base.model.SaSettleCardSum;
 import org.hlpay.base.model.SettleParams;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.enumm.CardDataTypeEnum;
 import org.hlpay.common.enumm.CurrencyTypeEnum;
 import org.hlpay.common.enumm.MchTypeEnum;
 import org.hlpay.common.enumm.RunModeEnum;
 import org.hlpay.common.enumm.SettleConfigEnum;
 import org.hlpay.common.enumm.SettleStatusEnum;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisProUtil;
 import org.hlpay.common.util.UnionIdUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service
 public class CardService {
   private static final MyLog _log = MyLog.getLog(CardService.class);

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   @Autowired
   private RunModeService runModeService;

   @Autowired
   private RedisProUtil<String> redisProUtil;

   @Resource
   private SaDealRecordMapper saDealRecordMapper;

   @Autowired
   private MchInfoService mchInfoService;

   @Autowired
   private SaCardDao saCardDao;
   @Resource
   private SaCardMapper saCardMapper;

   public SaCard getSettleCard(MchInfo mchInfo, String currency, LocalDateTime refDate, int payMode, String channelMchId) throws Exception {
     SaCard settleCard;
     if (mchInfo == null) {
       return null;
     }
     _log.info("商户ID：" + mchInfo.getMchId(), new Object[0]);
     List<SaCard> settleCardsList = getSettleCards(mchInfo.getMchId(), currency.toUpperCase(), CardDataTypeEnum.SETTLE.name(), refDate, payMode, channelMchId);

     if (settleCardsList.size() == 0) {
       settleCard = addMchSettlementCard(mchInfo, CurrencyTypeEnum.valueOf(currency.toUpperCase()), refDate, payMode, channelMchId);
     } else {
       settleCard = settleCardsList.get(0);
     }
     return settleCard;
   }



   private final Object $lock = new Object[0]; public SaCard addMchSettlementCard(MchInfo mchInfo, CurrencyTypeEnum currencyTypeEnum, LocalDateTime refDate, int payMode, String channelMchId) throws Exception { synchronized (this.$lock) {





       return addMchCard(mchInfo, CardDataTypeEnum.SETTLE, currencyTypeEnum, refDate, payMode, channelMchId);
     }  }


   public SaCard addTrusteeshipCard(MchInfo mchInfo, CurrencyTypeEnum currencyTypeEnum) throws Exception {
     if (mchInfo == null) {
       return null;
     }
     if (mchInfo.getType().equals(MchTypeEnum.PLATFORM.name())) {
       return addMchCard(mchInfo, CardDataTypeEnum.TRUSTEESHIP, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), null);
     }
     return null;
   }


   public SaCard addRegulationCard(MchInfo mchInfo, CurrencyTypeEnum currencyTypeEnum) throws Exception {
     if (mchInfo == null) {
       return null;
     }
     if (mchInfo.getType().equals(MchTypeEnum.PLATFORM.name())) {
       return addMchCard(mchInfo, CardDataTypeEnum.REGULATION, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), null);
     }
     return null;
   }


   public SaCard addDumbCard(MchInfo mchInfo, CurrencyTypeEnum currencyTypeEnum) throws Exception {
     if (mchInfo == null) {
       return null;
     }
     if (mchInfo.getType().equals(MchTypeEnum.PLATFORM.name())) {
       return addMchCard(mchInfo, CardDataTypeEnum.DUMB, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), null);
     }
     return null;
   }

   public SaCard addMchCard(MchInfo mchInfo, CardDataTypeEnum cardDataTypeEnum, CurrencyTypeEnum currencyTypeEnum, LocalDateTime refDate, int payMode, String channelMchId) throws Exception {
     if (mchInfo == null) {
       return null;
     }



     String cardNumber = MchTypeEnum.valueOf(mchInfo.getType()).getCode() + cardDataTypeEnum.getCode() + currencyTypeEnum.getCode() + RandomStrUtils.getInstance().createRandomNumString(12);
     return addMchCard(cardNumber, mchInfo, cardDataTypeEnum, currencyTypeEnum, refDate, payMode, channelMchId);
   }

   public SaCard addMchCard(String cardNumber, MchInfo mchInfo, CardDataTypeEnum cardDataTypeEnum, CurrencyTypeEnum currencyTypeEnum, LocalDateTime refDate, int payMode, String channelMchId) throws Exception {
     if (mchInfo == null || cardDataTypeEnum == null || currencyTypeEnum == null) {
       return null;
     }
     SaCardExample saCardExample = new SaCardExample();
     SaCardExample.Criteria criteria = saCardExample.createCriteria();
     criteria.andUserIdEqualTo(mchInfo.getMchId());
     criteria.andConfigNumberEqualTo(channelMchId);
     criteria.andCardTypeEqualTo(cardDataTypeEnum.name());
     criteria.andCurrencyEqualTo(currencyTypeEnum.name());


     if (payMode == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {

       criteria.andFlagEqualTo(Integer.valueOf(payMode));
     } else {

       criteria.andFlagEqualTo(RunModeEnum.PUBLIC.getCode());
     }
     if (refDate != null) {
       criteria.andBetweenValidStartTimeAndValidEndTime(refDate);
     }
     List<SaCard> list = selectByExample(saCardExample);
     if (list.size() > 0) {
       return list.get(0);
     }

     String name = mchInfo.getName();
     SaCard saCard = new SaCard();

     SaCardData cardTypeData = new SaCardData();
     cardTypeData.setDataType(cardDataTypeEnum.name());
     cardTypeData.setDataCode(currencyTypeEnum.name());

     saCard.setCardType(cardDataTypeEnum.name());
     saCard.setCurrency(currencyTypeEnum.name());
     saCard.setCardName(name + cardDataTypeEnum.getDesc() + "卡");
     if ((cardDataTypeEnum.name().equals(CardDataTypeEnum.DUMB.name()) || cardDataTypeEnum
       .name().equals(CardDataTypeEnum.PAYMENT.name())) && mchInfo
       .getType().equals(MchTypeEnum.PLATFORM.name())) {
       saCard.setCardLimit(Long.valueOf(-1L));
     }

     if (cardDataTypeEnum == CardDataTypeEnum.SETTLE) {
       SettleParams settleParams;

       mchInfo.setSettleParams(this.mchInfoService.getSettleParamsIfNullUseParentParams(mchInfo));



       String settleParamsStr = (String)this.redisProUtil.get(mchInfo.getMchId() + ":SP");
       if (StringUtils.isNotEmpty(settleParamsStr)) {
         settleParams = (SettleParams)JSONObject.parseObject(settleParamsStr, SettleParams.class);
       } else {
         settleParams = this.mchInfoService.getSettleParams(mchInfo);

         long value = DateUtils.getBetweenValueInMilSeconds(new Date(), DateUtils.getDaysDurationFirstDate(1)).longValue();
         this.redisProUtil.set(mchInfo.getMchId() + ":SP",
             JSONObject.toJSONString(settleParams), value, TimeUnit.MILLISECONDS);
       }


       if (payMode == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {

         saCard.setFlag(Integer.valueOf(payMode));
       } else {

         saCard.setFlag(RunModeEnum.PUBLIC.getCode());
       }

       LocalDateTime[] settleValidTime = SettleConfigEnum.getValidSettleTime(settleParams.getSettleCycle(), refDate);
       _log.info("商户信息：" + JSONObject.toJSONString(mchInfo), new Object[0]);

       if (StringUtil.isEmpty(settleParams.getSettleCycle())) {
         return null;
       }


       if (settleValidTime == null) {
         return null;
       }
       saCard.setValidStartTime(settleValidTime[0]);
       saCard.setValidEndTime(settleValidTime[1]);

       JSONObject settleConfigJson = new JSONObject();
       settleConfigJson.put("settleParams", this.mchInfoService.getSettleParamsIfNullUseParentParams(mchInfo));
       settleConfigJson.put("settleType", this.mchInfoService.getSettleTypeIfNullUseParent(mchInfo));
       saCard.setConfigNumber(channelMchId);
     }

     saCard.setCardId(cardNumber);
     saCard.setCardNumber(cardNumber);
     saCard.setLoginAccount(mchInfo.getMchId());
     saCard.setUserId(mchInfo.getMchId());
     saCard.setUserName(name);
     saCard.setCardStatus("1");
     saCard.setRemainPart(Long.valueOf(0L));


     int result = baseInsert(saCard);
     if (result > 0) {
       return saCard;
     }

     return null;
   }
   public String getMchCardNum(MchInfo mchInfo, CardDataTypeEnum cardDataTypeEnum, CurrencyTypeEnum currencyTypeEnum, LocalDateTime refDate) throws Exception {
     SaCard existenceCard;
     if (mchInfo == null) {
       return null;
     }


     if (refDate == null) {
       LocalDateTime[] settleValidTime = null;
       existenceCard = getCard(mchInfo.getMchId(), cardDataTypeEnum.name(), currencyTypeEnum.name(), null, null);
     } else {

       LocalDateTime[] settleValidTime = SettleConfigEnum.getValidSettleTime(this.mchInfoService.getSettleTypeIfNullUseParent(mchInfo), refDate);
       if (settleValidTime == null) {
         existenceCard = getCard(mchInfo.getMchId(), cardDataTypeEnum.name(), currencyTypeEnum.name(), null, null);
       } else {

         existenceCard = getCard(mchInfo.getMchId(), cardDataTypeEnum.name(), currencyTypeEnum.name(), settleValidTime[0], settleValidTime[1]);
       }
     }

     if (existenceCard != null) {
       return existenceCard.getCardNumber();
     }



     SaCard saCard = new SaCard();

     if (cardDataTypeEnum.name().equals(CardDataTypeEnum.SETTLE.name())) {
       saCard.setCardId(UnionIdUtil.getUnionId(new String[] { MchTypeEnum.valueOf(mchInfo.getType()).getType(), cardDataTypeEnum.getType(), currencyTypeEnum.getType(), mchInfo.getMchId(), DateUtils.getDays() }));
     } else {
       saCard.setCardId(UnionIdUtil.getUnionId(new String[] { MchTypeEnum.valueOf(mchInfo.getType()).getType(), cardDataTypeEnum.getType(), currencyTypeEnum.getType(), mchInfo.getMchId() }));
     }

     return saCard.getCardNumber();
   }

   public SaCard addUserCard(String userId, String userName, CardDataTypeEnum cardDataTypeEnum, CurrencyTypeEnum currencyTypeEnum, int payMode) throws Exception {
     if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(userName)) {
       return null;
     }

     MchInfo tempMchInfo = new MchInfo();
     tempMchInfo.setMchId(userId);
     tempMchInfo.setType(MchTypeEnum.USER.name());
     tempMchInfo.setName(userName);

     return addMchCard(tempMchInfo, cardDataTypeEnum, currencyTypeEnum, null, payMode, null);
   }

   public SaCard getUserCard(String userId, String userName, String cardType, String currency, int payMode) throws Exception {
     SaCard userCard = getCard(userId, cardType, currency.toUpperCase(), null, null);
     if (userCard == null) {
       MchInfo tempMchInfo = new MchInfo();
       tempMchInfo.setMchId(userId);
       tempMchInfo.setType(MchTypeEnum.USER.name());
       tempMchInfo.setName(userName);
       userCard = addMchCard(tempMchInfo, CardDataTypeEnum.valueOf(cardType), CurrencyTypeEnum.valueOf(currency.toUpperCase()), null, payMode, null);
     }
     return userCard;
   }

   public SaCard getCardByCardNumber(String cardNumber) {
     SaCardExample saCardExample = new SaCardExample();
     SaCardExample.Criteria criteria = saCardExample.createCriteria();
     criteria.andCardNumberEqualTo(cardNumber);
     List<SaCard> list = selectByExample(saCardExample);
     if (list.size() > 0) {
       return list.get(0);
     }
     return null;
   }

   public SaCard copyToSettleCard(String cardNum) throws Exception {
     SaCard saCard = new SaCard();
     saCard.setCardNumber(cardNum);
     saCard = baseInfo(saCard);
     if (saCard == null) {
       return null;
     }
     saCard.setCardStatus(SettleStatusEnum.CONFIRMED.getType());
     int result = insertToSettleCard(saCard);
     if (result > 0) {
       return saCard;
     }
     return null;
   }


   public int settleDone(MchInfo mchInfo, SaCard card) throws Exception {
     return settleDone(mchInfo, card, SettleStatusEnum.COMPLETED);
   }

   @Transactional(rollbackFor = {Exception.class})
   public int settleDone(MchInfo mchInfo, SaCard card, SettleStatusEnum settleStatusEnum) throws Exception {
     SaCard saCard = new SaCard();
     saCard.setCardNumber(card.getCardNumber());
     saCard = baseInfo(saCard);
     if (saCard == null) {
       return 0;
     }

     deleteByPrimaryKey(saCard.getCardId());

     if (mchInfo.getParentMchId() != null) {
       MchInfo parent = this.mchInfoService.selectMchInfo(mchInfo.getParentMchId());

       if (parent != null && parent.getType().equals(MchTypeEnum.MCH.name())) {
         SaCard parentCard = getSettleCard(parent, card.getCurrency(), card.getValidStartTime(), saCard.getFlag().intValue(), card.getConfigNumber());
         if (parentCard != null)
         {

           if (parentCard.getFlag().intValue() == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {
             this.mq4MchNotify.send("queue.notify.mch.settle.independent.withdraw", JSONObject.toJSONString(parentCard));
           } else {
             copyToSettleCard(parentCard.getCardNumber());
             deleteByPrimaryKey(parentCard.getCardId());
             parentCard.setCardStatus(settleStatusEnum.getType());
             updateByPrimaryKeyFromSettleCard(parentCard);
           }
         }
       }
     }
     card.setCardStatus(settleStatusEnum.getType());
     int result = updateByPrimaryKeyFromSettleCard(card);
     if (result > 0) {
       return 1;
     }
     return 0;
   }


   public int settleDone(String cardNum, Long incoming, Long exp, Long award, SettleStatusEnum settleStatusEnum) throws Exception {
     SaCard saCard = new SaCard();
     saCard.setCardNumber(cardNum);
     saCard = baseInfo(saCard);
     if (saCard == null) {
       return 0;
     }

     deleteByPrimaryKey(saCard.getCardId());
     saCard.setCardStatus(saCard.getCardStatus());
     saCard.setExpSum(exp);
     saCard.setAwardSum(award);
     saCard.setRechargeSum(incoming);
     saCard.setCardStatus(settleStatusEnum.getType());
     int result = updateByPrimaryKeyFromSettleCard(saCard);
     if (result > 0) {
       return 1;
     }
     return 0;
   }


   public List<SaCard> getAllParentSettleCards(MchInfo mchInfo, String currency, LocalDateTime refDate, int payMode, String channelMchId) throws Exception {
     String[][] all = UnionIdUtil.getIdInfoFromUnionId(mchInfo.getMchId());

     List<SaCard> cardList = new ArrayList<>();
     for (int i = 0; i < all.length - 1; i++) {
       String mchId = all[i][0];
       if (mchId.startsWith(channelMchId)) {
         cardList.add(getSettleCard(this.mchInfoService.selectMchInfo(mchId), currency, refDate, payMode, channelMchId));
       }
     }
     return cardList;
   }

   public int insertSaCard(SaCard saCard) {
     return this.saCardMapper.insertSaCard(saCard);
   }

   public List<SaCard> infoSaCard(String userId) {
     return this.saCardMapper.infoSaCard(userId);
   }

   public List<SaCard> getCardsByUserIdAndCardType(String userId, String cardType) {
     return this.saCardMapper.getCardsByUserIdAndCardType(userId, cardType);
   }

   public List<SaCard> getAllChildesCards(String userId, String currency, String cardType, LocalDateTime settleStartTime, LocalDateTime settleEndTime) {
     SaCardExample example = new SaCardExample();
     SaCardExample.Criteria criteria = example.createCriteria();

     criteria.andUserIdLike(userId + "-");
     criteria.andCurrencyEqualTo(currency);
     criteria.andCardTypeEqualTo(cardType);
     criteria.andValidStartTimeEqualTo(settleStartTime);
     criteria.andValidEndTimeEqualTo(settleEndTime);
     return this.saCardDao.selectByExample(example);
   }

   public List<SaCard> getSettleCards(String userId, String currency, String cardType, LocalDateTime settleTime, int payMode, String channelMchId) {
     SaCardExample saCardExample = new SaCardExample();
     SaCardExample.Criteria criteria = saCardExample.createCriteria();
     criteria.andUserIdEqualTo(userId);
     criteria.andCurrencyEqualTo(currency);
     criteria.andCardTypeEqualTo(cardType);
     criteria.andValidStartTimeLessThanOrEqualTo(settleTime);
     criteria.andConfigNumberEqualTo(channelMchId);
     criteria.andValidEndTimeGreaterThan(settleTime);
     criteria.andFlagEqualTo(Integer.valueOf(payMode));
     return this.saCardDao.selectByExample(saCardExample);
   }


   public List<SaCard> getCardsByCardType(String cardType) {
     return this.saCardMapper.getCardsByCardType(cardType);
   }

   public SaCard getCard(String userId, String cardType, String currency, LocalDateTime validStartTime, LocalDateTime validEndTime) {
     return this.saCardMapper.getCard(userId, cardType, currency, validStartTime, validEndTime);
   }

   public int updateSaCard(String cardNumber, String flag, String remainPart) {
     return this.saCardMapper.updateSaCard(cardNumber, flag, remainPart);
   }

   public int updateAll(SaCard saCard) {
     return this.saCardMapper.updateAll(saCard);
   }

   public List<SaCard> listSaCard() {
     return this.saCardMapper.listSaCard();
   }

   public int update(Map<String, Long> map) {
     return this.saCardMapper.update(map);
   }

   public int updateCard(SaCard saCard) {
     return this.saCardMapper.updateCard(saCard);
   }

   public List<SaCard> descSaCard() {
     return this.saCardMapper.descSaCard();
   }

   public SaCard getSaCardInfoByAccount(SaCard saCard) {
     return this.saCardMapper.getSaCardInfoByAccount(saCard);
   }

   public int addSaCard(List<SaCard> list) {
     return this.saCardMapper.addSaCard(list);
   }

   public int updateData(SaCard saCard) {
     return this.saCardMapper.updateData(saCard);
   }

   public int updateFreeze(SaCard saCard) {
     return this.saCardMapper.updateFreeze(saCard);
   }

   public int baseUpdateList(List<SaCard> list) {
     return this.saCardMapper.baseUpdateList(list);
   }

   public int baseUpdateCashSettleList(List<SaCard> list) {
     return this.saCardMapper.baseUpdateCashSettleList(list);
   }

   public int updateCardList(List<SaCard> list) {
     return this.saCardMapper.updateCardList(list);
   }


   public int doInnerSettle(InnerSettleInfo innerSettleInfo) {
     return this.saCardMapper.doInnerSettle(innerSettleInfo);
   }

   public int doInnerVirtualSettle(InnerSettleInfo innerSettleInfo) {
     return this.saCardMapper.doInnerSettle(innerSettleInfo);
   }

   public int doInnerVirtualSettleToReal(InnerSettleInfo innerSettleInfo) {
     return this.saCardMapper.doInnerVirtualSettleToReal(innerSettleInfo);
   }

   public int doInnerScheduleSettle(InnerSettleInfo innerSettleInfo) {
     return this.saCardMapper.doInnerScheduleSettle(innerSettleInfo);
   }

   public int baseInsert(SaCard card) throws Exception {
     return this.saCardMapper.baseInsert(card);
   }

   public int baseDelete(String s) throws Exception {
     return this.saCardMapper.baseDelete(s);
   }

   public int baseUpdate(SaCard card) throws Exception {
     return this.saCardMapper.baseUpdate(card);
   }

   public int baseUpdateOrderDetail(SaCard card) throws Exception {
     return this.saCardMapper.baseUpdateOrderDetail(card);
   }

   public List<SaCard> baseLimitSelect(SaCard card) throws Exception {
     return this.saCardMapper.baseLimitSelect(card);
   }

   public SaCard baseInfo(SaCard s) throws Exception {
     return (SaCard)this.saCardMapper.baseInfo(s);
   }

   public Integer count() throws Exception {
     return this.saCardMapper.count();
   }

   public int insertToSettleCard(SaCard record) {
     return this.saCardDao.insertToSettleCard(record);
   }

   public List<SaSettleCard> selectByExampleFromSettleCard(SaCardExample example) {
     return this.saCardDao.selectByExampleFromSettleCard(example);
   }

   public List<SaSettleCard> getMonthSettleSum(SaSettleCardSum saSettleCardSum) {
     return this.saCardDao.getMonthSettleSum(saSettleCardSum);
   }
   public List<SaSettleCard> getMonthSettleByUserSum(SaSettleCardSum saSettleCardSum) {
     return this.saCardDao.getMonthSettleByUserSum(saSettleCardSum);
   }
   public List<SaSettleCard> getPlatformMonthSettleSum(SaSettleCardSum saSettleCardSum) {
     return this.saCardDao.getPlatformMonthSettleSum(saSettleCardSum);
   }
   public List<SaSettleCard> getPlatformSettleSum(SaSettleCardSum saSettleCardSum) {
     return this.saCardDao.getPlatformSettleSum(saSettleCardSum);
   }
   public List<SaSettleCard> getMonthSettleSumByMchInfo(SaSettleCardSum saSettleCardSum) {
     return this.saCardDao.getMonthSettleSumByMchInfo(saSettleCardSum);
   }
   public int updateByPrimaryKeyFromSettleCardBySelective(SaCard record) {
     return this.saCardDao.updateByPrimaryKeyFromSettleCardBySelective(record);
   }
   public int updateByPrimaryKeyFromSettleCard(SaCard record) {
     return this.saCardDao.updateByPrimaryKeyFromSettleCard(record);
   }

   public long countByExampleFromSettleCard(SaCardExample example) {
     return this.saCardDao.countByExampleFromSettleCard(example);
   }

   public long countByExample(SaCardExample example) {
     return this.saCardDao.countByExample(example);
   }

   public int deleteByExample(SaCardExample example) {
     return this.saCardDao.deleteByExample(example);
   }

   public int deleteByPrimaryKey(String id) {
     return this.saCardDao.deleteByPrimaryKey(id);
   }

   public int insert(SaCard record) {
     return this.saCardDao.insert(record);
   }

   public int insertSelective(SaCard record) {
     return this.saCardDao.insertSelective(record);
   }


   public List<SaCard> selectByExampleWithSettleStatus(SaCardExample example) {
     return this.saCardDao.selectByExampleWithSettleStatus(example);
   }

   public List<SaCard> selectByExample(SaCardExample example) {
     return this.saCardDao.selectByExample(example);
   }

   public List<SaCard> selectSettleWithdraw() {
     return this.saCardDao.selectSettleWithdraw();
   }

   public SaCard selectByPrimaryKey(String id) {
     return (SaCard)this.saCardDao.selectByPrimaryKey(id);
   }

   public SaCard selectSettleByPrimaryKey(String id) {
     return (SaCard)this.saCardDao.selectSettleByPrimaryKey(id);
   }

   public int updateByExampleSelective(SaCard record, SaCardExample example) {
     return this.saCardDao.updateByExampleSelective(record, example);
   }

   public int updateByExample(SaCard record, SaCardExample example) {
     return this.saCardDao.updateByExample(record, example);
   }

   public int updateByPrimaryKeySelective(SaCard record) {
     return this.saCardDao.updateByPrimaryKeySelective(record);
   }

   public int updateByPrimaryKey(SaCard record) {
     return this.saCardDao.updateByPrimaryKey(record);
   }
 }

