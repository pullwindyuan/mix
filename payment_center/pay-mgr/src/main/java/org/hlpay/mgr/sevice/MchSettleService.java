 package org.hlpay.mgr.sevice;
 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.time.ZoneId;
 import java.util.*;
 import java.util.function.BiConsumer;
 import java.util.function.Consumer;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.bo.*;
 import org.hlpay.base.mapper.PayOrderMapper;
 import org.hlpay.base.mapper.RestDateDao;
 import org.hlpay.base.mapper.SaDealRecordMapper;
 import org.hlpay.base.model.*;
 import org.hlpay.base.payFeign.WorkflowAudit;
 import org.hlpay.base.plugin.PageModel;
 import org.hlpay.base.security.SecurityAccessManager;
 import org.hlpay.base.service.*;
 import org.hlpay.base.service.impl.TransOrderServiceImpl;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.base.vo.SettleCardExportVo;
 import org.hlpay.base.vo.SettleCardVo;
 import org.hlpay.base.vo.SettleDetailExportVo;
 import org.hlpay.base.vo.SettlePayOrderExportVo;
 import org.hlpay.base.vo.SettlePayOrderVo;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.*;
 import org.hlpay.common.util.*;
 import org.hlpay.pay.service.impl.InnerSettleServiceImpl;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

 @Service
 public class MchSettleService {
   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.sevice.MchSettleService.class);

   @Resource
   private Environment env;

   @Autowired
   private WorkflowAudit workflowAudit;

   @Autowired
   private MchInfoService mchInfoService;

   @Resource
   private PayOrderMapper payOrderMapper;

   @Resource
   private InnerSettleServiceImpl innerSettleService;

   @Autowired
   private SecurityAccessManager securityAccessManager;

   @Autowired
   private CardService cardService;

   @Resource
   private SaDealRecordMapper saDealRecordMapper;

   @Autowired
   private TransOrderServiceImpl transOrderService;

   @Autowired
   private RestDateDao restDateDao;

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   @Autowired
   private BaseService4PayOrder baseService4PayOrder;

   @Autowired
   private RunModeService runModeService;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   public CommonResult<JSONArray> getSum(CheckSumPayOrderBo checkSumPayOrderBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }

     CheckSumPayOrder checkSumPayOrder = (CheckSumPayOrder)BeanUtil.copyProperties(checkSumPayOrderBo, CheckSumPayOrder.class);
     List<PayOrder> payOrderList = this.payOrderMapper.getSumByPayChannelCodeAndPaySuccessTime(checkSumPayOrder);
     return CommonResult.success((JSONArray)JSON.toJSON(payOrderList));
   }

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult settleCashSettleCompleted(CashSettleBo cashSettleBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }

     List<String> idList = new ArrayList<>();
     for (CashSettleItem item : cashSettleBo.getCashSettleList()) {
       idList.add(item.getCardNumber());
     }

     SaCardExample example = new SaCardExample();
     SaCardExample.Criteria criteria = example.createCriteria();
     criteria.andCardIdIn(idList);
     List<SaSettleCard> list = this.cardService.selectByExampleFromSettleCard(example);
     List<SaCard> cardList = BeanUtil.copyProperties(list, SaCard.class);
     int r = this.cardService.baseUpdateCashSettleList(cardList);
     if (r > 0) {


       List<SaCard> saCards = new ArrayList<>();
       for (SaCard card : cardList) {
         if (card.getFlag().intValue() == RunModeEnum.PUBLIC.getCode().intValue()) {
           saCards.add(card);
         }
       }
       this.innerSettleService.callBackCashSettleDone(saCards);
       return CommonResult.success("确认成功");
     }
     return CommonResult.error("确认失败");
   }

   public CommonResult settleCashSettleCompletedCallback(String settleCardNumber, HttpServletRequest request) {
     SaCard saCard = this.cardService.selectSettleByPrimaryKey(settleCardNumber);
     if (saCard != null && (saCard.getCardStatus().equals(SettleStatusEnum.COMPLETED.getType()) || saCard
       .getCardStatus().equals(SettleStatusEnum.CONFIRMED.getType()))) {
       saCard.setCardStatus(SettleStatusEnum.CASH_COMPLETED.getType());
       int r = this.cardService.updateByPrimaryKeyFromSettleCard(saCard);
       if (r > 0) {
         return CommonResult.success("确认成功");
       }
     }
     return CommonResult.error("确认失败");
   }

   public CommonResult<List<SettleCardVo>> getSettleWithdrawTodoList(GetSettleTodoListBo getSettleTodoListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     int runMode = this.runModeService.getRunModeCode();
     PageModel pageModel = new PageModel();

     RestDateExample example = new RestDateExample();
     RestDateExample.Criteria restDateCriteria = example.createCriteria();
     restDateCriteria.andEndDateGreaterThanOrEqualTo(LocalDate.now());
     restDateCriteria.andStartDateGreaterThanOrEqualTo(LocalDate.now());
     restDateCriteria.andStateEqualTo(Integer.valueOf(1));
     long count = this.restDateDao.countByExample(example);
     if (count > 0L) {
       pageModel.setList(new ArrayList());
       pageModel.setCount(Integer.valueOf(0));
       pageModel.setMsg("ok");
       pageModel.setRel(Boolean.valueOf(true));
       CommonResult<List<SettleCardVo>> commonResult1 = CommonResult.success(pageModel.getList());
       commonResult1.setExtra(MchTypeEnum.PLATFORM.name());
       return commonResult1;
     }




     SaCardExample saCardExample = new SaCardExample();
     saCardExample.setOrderByClause("VALID_END_TIME ASC");
     SaCardExample.Criteria criteria = saCardExample.createCriteria();

     criteria.andRemainPartGreaterThan(Long.valueOf(0L));
     criteria.andCardTypeEqualTo(CardDataTypeEnum.SETTLE.name());
     if (runMode == RunModeEnum.PUBLIC.getCode().intValue()) {
       criteria.andLoginAccountNotEqualTo("1");
     }
     else {

       MchInfo agencyInfo = this.mchInfoService.getAgencyInfo();
       GetSettleListBo getSettleListBo = new GetSettleListBo();
       getSettleListBo.setStatus(SettleStatusEnum.COMPLETED.getCode().intValue());
       getSettleListBo.setPageSize(Integer.valueOf(0));
       getSettleList(getSettleListBo, agencyInfo, true, agencyInfo


           .getMchId());

       List<MchInfo> children = this.mchInfoService.getMchInfoListByParentMchId(agencyInfo.getMchId());
       List<String> childrenId = new ArrayList<>();
       for (MchInfo mchInfo : children) {
         childrenId.add(mchInfo.getMchId());
       }
       criteria.andUserIdIn(childrenId);
     }
     if (getSettleTodoListBo.getStartDate() != null) {
       criteria.andValidStartTimeGreaterThan(DateUtils.getLocalDateTime(getSettleTodoListBo.getStartDate()));
       criteria.andValidEndTimeLessThan(DateUtils.getLocalDateTime(getSettleTodoListBo.getEndDate()));
     }

     criteria.andCardStatusEqualTo(SettleStatusEnum.COMPLETED.getType());
     List<SaSettleCard> saCardList = this.cardService.selectByExampleFromSettleCard(saCardExample);
     List<SettleCardVo> result = BeanUtil.copyProperties(saCardList, SettleCardVo.class);
     Map<String, List<SettleCardVo>> settleCardVoMap = new HashMap<>();
     result.forEach((Consumer<? super SettleCardVo>)new Object(this, settleCardVoMap));

     Iterator<String> iterator = settleCardVoMap.keySet().iterator();
     List<String> mchIdList = new ArrayList<>();
     while (iterator.hasNext()) {
       mchIdList.add(iterator.next());
     }
     List<MchInfo> mchInfoList = this.mchInfoService.getMchInfoListByMchIdList(mchIdList);

     TreeMap<String, ArrayList<SettleCardVo>> settleCardVoTreeMap = new TreeMap<>((Comparator<? super String>)new Object(this));

     for (MchInfo mchInfo : mchInfoList) {
       String settleType = this.mchInfoService.getSettleTypeIfNullUseParent(mchInfo);
       JSONObject settleTypeJSON = JSONObject.parseObject(settleType);
       Integer tn = settleTypeJSON.getInteger("tn");
       _log.info("结算tn:" + tn, new Object[0]);
       LocalDateTime settleEndTime = LocalDateTime.now().minusDays((tn.intValue() - 1));
       List<SettleCardVo> settleCardVoList = settleCardVoMap.get(mchInfo.getMchId());
       for (SettleCardVo settleCardVo : settleCardVoList) {

         if (settleCardVo.getValidEndTime().compareTo(settleEndTime) < 0) {
           settleCardVo.setCashSettleDate(settleCardVo.getValidEndTime().plusDays((tn.intValue() + 1)));
           String cashSettleTimeString = DateUtils.getTimestamp(settleCardVo.getCashSettleDate()).toString();
           ArrayList<SettleCardVo> arrayList = settleCardVoTreeMap.get(cashSettleTimeString);
           if (arrayList == null) {
             arrayList = new ArrayList<>();
             settleCardVoTreeMap.put(cashSettleTimeString, arrayList);
           }
           arrayList.add(settleCardVo);
         }
       }
     }

     ArrayList<SettleCardVo> settleCardVoOutput = new ArrayList<>();
     ArrayList<SettleCardVo> finalSettleCardVoOutput = settleCardVoOutput;
     settleCardVoTreeMap.forEach((BiConsumer<? super String, ? super ArrayList<SettleCardVo>>)new Object(this, finalSettleCardVoOutput));

     CommonResult<List<SettleCardVo>> commonResult = CommonResult.success(pageModel.getList());
     commonResult.setExtra(MchTypeEnum.PLATFORM.name());
     commonResult.setData(finalSettleCardVoOutput);
     return commonResult;
   }

   public CommonResult<JSONArray> getMonthSettleListByMchInfo(GetMonthSettleListByMchInfoBo getMonthSettleListByMchInfoBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(getMonthSettleListByMchInfoBo.getMchId(), getMonthSettleListByMchInfoBo.getType());
     if (mchInfo == null) {
       return CommonResult.error("商户不存在");
     }
     SaSettleCardSum saCard = new SaSettleCardSum();

     if (StringUtils.isEmpty(getMonthSettleListByMchInfoBo.getCurrency())) {
       getMonthSettleListByMchInfoBo.setCurrency(CurrencyTypeEnum.CNY.name());
     }
     saCard.setCurrency(getMonthSettleListByMchInfoBo.getCurrency());
     if (getMonthSettleListByMchInfoBo.getStartDate() != null) {
       saCard.setValidStartTime(DateUtils.getLocalDateTime(getMonthSettleListByMchInfoBo.getStartDate()));
     }
     if (getMonthSettleListByMchInfoBo.getEndDate() != null) {
       saCard.setValidEndTime(DateUtils.getMonthLastDate(DateUtils.getLocalDateTime(getMonthSettleListByMchInfoBo.getEndDate())));
     }
     saCard.setMchId(mchInfo.getMchId());
     List<SaSettleCard> monthGroup = this.cardService.getMonthSettleSumByMchInfo(saCard);

     CommonResult<JSONArray> result = new CommonResult();
     result.setCode(ResultEnum.SUCCESS.getCode());
     result.setData(JSON.parseArray(JSON.toJSONString(monthGroup)));
     return result;
   }

   public CommonResult<JSONObject> getMonthSettleList(GetMonthSettleListBo getMonthSettleListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     SaSettleCardSum saCard = new SaSettleCardSum();
     saCard.setUserName(getMonthSettleListBo.getName());
     Integer status = getMonthSettleListBo.getStatus();
     if (status != null &&
       !status.equals("99")) {
       saCard.setCardStatus(status.toString());
     }

     saCard.setCurrency(getMonthSettleListBo.getCurrency());
     saCard.setValidStartTime(DateUtils.getLocalDateTime(getMonthSettleListBo.getStartDate()));
     saCard.setValidEndTime(DateUtils.getMonthLastDate(DateUtils.getLocalDateTime(getMonthSettleListBo.getEndDate())));


     List<SaSettleCard> monthGroupByMch = this.cardService.getMonthSettleSum(saCard);

     List<SaSettleCard> monthGroupByMchSum = this.cardService.getMonthSettleByUserSum(saCard);

     List<SaSettleCard> monthPlatformSum = this.cardService.getPlatformMonthSettleSum(saCard);

     List<SaSettleCard> platformSum = this.cardService.getPlatformSettleSum(saCard);
     Map<String, List<SaSettleCard>> settleCardVoMap = new HashMap<>();

     monthGroupByMch.forEach((Consumer<? super SaSettleCard>)new Object(this, settleCardVoMap));

     monthGroupByMchSum.forEach((Consumer<? super SaSettleCard>)new Object(this, settleCardVoMap));

     settleCardVoMap.put("平台按月总计", monthPlatformSum);


     monthPlatformSum.addAll(monthPlatformSum.size(), platformSum);

     Iterator<Map.Entry<String, List<SaSettleCard>>> iterator = settleCardVoMap.entrySet().iterator();
     List<String> mchIdList = new ArrayList<>();
     List<SettleCardVo> list = new ArrayList<>();
     int index = 0;
     while (iterator.hasNext()) {
       list.addAll(index, BeanUtil.copyProperties((List)((Map.Entry)iterator.next()).getValue(), SettleCardVo.class));
       index = list.size();
     }

     CommonResult<JSONObject> result = new CommonResult();
     result.setCode(ResultEnum.SUCCESS.getCode());

     result.setData((JSONObject) JSONObject.toJSON(settleCardVoMap));
     return result;
   }

   public CommonResult<PageModel<SettleCardVo>> getSettleList(GetSettleListBo getSettleListBo) throws Exception {
     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(getSettleListBo.getMchId(), getSettleListBo.getType());
     if (mchInfo == null) {
       List<MchInfo> mchInfoList = this.mchInfoService.tryToAddAllExternalMchInfo(getSettleListBo.getMchId());
       if (mchInfoList == null) {
         return CommonResult.error("商户不存在且补偿失败");
       }
     }
     return getSettleList(getSettleListBo, mchInfo);
   }

   public CommonResult<PageModel<SettleCardVo>> getSettleList(GetSettleListBo getSettleListBo, MchInfo mchInfo) throws Exception {
     return getSettleList(getSettleListBo, mchInfo, false, null);
   }



   public CommonResult<PageModel<SettleCardVo>> getSettleList(GetSettleListBo getSettleListBo, MchInfo mchInfo, boolean fromChildes, String channelMchId) throws Exception {
     int count;
     this.mq4MchNotify.send("queue.notify.mch.settle.fix", "tryToFix");
     int runMode = this.runModeService.getRunModeCode();
     if (runMode == RunModeEnum.PRIVATE.getCode().intValue())
     {
       if (getSettleListBo.getMchId().equals("1")) {
         getSettleListBo.setMchId(this.mchInfoService.getAgencyInfo().getMchId());
         mchInfo = this.mchInfoService.selectMchInfo(getSettleListBo.getMchId());
       }
     }
     PageModel pageModel = new PageModel();
     int status = getSettleListBo.getStatus();
     Integer pageIndex = getSettleListBo.getPageIndex();
     Integer pageSize = getSettleListBo.getPageSize();
     List<SaSettleCard> saCardList = new ArrayList<>();
     List<SettleCardVo> result = new ArrayList<>();

     if (mchInfo == null) {
       return CommonResult.error("商户不存在");
     }
     getSettleListBo.setMchId(mchInfo.getMchId());



     SaCardExample saCardExample = new SaCardExample();
     if (pageSize.intValue() > 0) {
       saCardExample.setOffset(Long.valueOf(((pageIndex.intValue() - 1) * pageSize.intValue())));
       saCardExample.setLimit(pageSize);
     }
     SaCardExample.Criteria criteria = saCardExample.createCriteria();
     saCardExample.setOrderByClause("VALID_END_TIME DESC");
     if (StringUtils.isNotEmpty(getSettleListBo.getName())) {
       criteria.andUserNameLike(getSettleListBo.getName());
     }
     if (StringUtils.isNotEmpty(channelMchId)) {
       criteria.andConfigNumberEqualTo(channelMchId);
     }
     criteria.andRemainPartGreaterThan(Long.valueOf(0L));
     criteria.andCardTypeEqualTo(CardDataTypeEnum.SETTLE.name());
     List<String> allMchIds = new ArrayList<>();
     Map<String, MchInfo> mchInfoMap = new HashMap<>();
     if (fromChildes && !mchInfo.getType().equals(MchTypeEnum.MCH_BRANCH.name())) {

       List<MchInfo> childes = this.mchInfoService.getMchInfoListByParentMchId(mchInfo.getMchId());
       for (MchInfo m : childes) {
         allMchIds.add(m.getMchId());
         mchInfoMap.put(m.getMchId(), m);
       }
       if (getSettleListBo.getStartDate() != null) {
         criteria.andValidStartTimeGreaterThanOrEqualTo(DateUtils.getLocalDateTime(getSettleListBo.getStartDate()));
         criteria.andValidEndTimeLessThanOrEqualTo(DateUtils.getLocalDateTime(getSettleListBo.getEndDate()));
       }
     } else {
       allMchIds.add(mchInfo.getMchId());
       mchInfoMap.put(mchInfo.getMchId(), mchInfo);
       if (getSettleListBo.getStartDate() != null) {
         criteria.andValidEndTimeGreaterThanOrEqualTo(DateUtils.getLocalDateTime(getSettleListBo.getStartDate()));
         criteria.andValidEndTimeLessThanOrEqualTo(DateUtils.getLocalDateTime(getSettleListBo.getEndDate()));
       }
     }

     criteria.andUserIdIn(allMchIds);

     int currSize = pageIndex.intValue() * pageSize.intValue();
     int start = currSize - pageSize.intValue();
     if (status == SettleStatusEnum.ING.getCode().intValue()) {
       result.addAll(getVirtualSettleList(getSettleListBo, fromChildes, SettleStatusEnum.ING.getType(), channelMchId));
       count = result.size();

       if (count > 0) {

         int size = pageSize.intValue();
         if (count < currSize) {
           size = count - start;
         }
         result = result.subList(start, size);
       }
     } else if (status == SettleStatusEnum.SETTLED.getCode().intValue()) {
       result.addAll(getVirtualSettleList(getSettleListBo, fromChildes, SettleStatusEnum.SETTLED.getType(), channelMchId));
       count = result.size();

       if (count > 0) {

         int size = pageSize.intValue();
         if (count < currSize) {
           size = count - start;
         }
         result = result.subList(start, size);
       }
     } else if (status == SettleStatusEnum.CONFIRMED.getCode().intValue() || status == SettleStatusEnum.COMPLETED
       .getCode().intValue() || status == SettleStatusEnum.CASH_COMPLETED
       .getCode().intValue()) {
       criteria.andCardStatusEqualTo(String.valueOf(status));
       count = (int)this.cardService.countByExampleFromSettleCard(saCardExample);
       saCardList = this.cardService.selectByExampleFromSettleCard(saCardExample);
       result = BeanUtil.copyProperties(saCardList, SettleCardVo.class);

       for (int i = 0; i < result.size(); i++) {
         SettleCardVo temp = result.get(i);
         temp.setMchType(((MchInfo)mchInfoMap.get(temp.getUserId())).getType());
       }
     } else {
       result.addAll(getVirtualSettleList(getSettleListBo, fromChildes, SettleStatusEnum.ALL.getType(), channelMchId));
       count = result.size();
       int virtualCount = count;
       int settledCount = (int)this.cardService.countByExampleFromSettleCard(saCardExample);
       count += settledCount;

       if (pageSize.intValue() == 0) {

         if (settledCount > 0) {
           List<SaSettleCard> temp = this.cardService.selectByExampleFromSettleCard(saCardExample);
           saCardList.addAll(temp);
         }

       } else if (start >= virtualCount) {

         result.clear();
         saCardExample.setOffset(Long.valueOf((start - virtualCount)));
         saCardExample.setLimit(pageSize);
         List<SaSettleCard> temp = this.cardService.selectByExampleFromSettleCard(saCardExample);
         saCardList.addAll(temp);
       }
       else if (virtualCount - start >= pageSize.intValue()) {


         int size = pageSize.intValue();
         if (count < currSize) {
           size = count - start;
         }
         result = result.subList(start, size);
       } else {

         saCardExample.setOffset(Long.valueOf(0L));
         saCardExample.setLimit(Integer.valueOf(pageSize.intValue() - virtualCount - start));
         List<SaSettleCard> temp = this.cardService.selectByExampleFromSettleCard(saCardExample);

         saCardList.addAll(temp);
       }


       if (saCardList.size() > 0) {
         result.addAll(BeanUtil.copyProperties(saCardList, SettleCardVo.class));

         for (int i = 0; i < result.size(); i++) {
           SettleCardVo item = result.get(i);
           if (item.getMchType() == null) {
             item.setMchType(((MchInfo)mchInfoMap.get(item.getUserId())).getType());
           }
         }
       }
     }

     pageModel.setList(result);
     pageModel.setCount(Integer.valueOf(count));
     pageModel.setMsg("ok");
     pageModel.setRel(Boolean.valueOf(true));
     CommonResult<PageModel<SettleCardVo>> commonResult = CommonResult.success(pageModel);
     commonResult.setExtra(mchInfo.getType());
     return commonResult;
   }

   public CommonResult<PageModel<SettleCardVo>> getSettleDetailListV1(GetSettleDetailListBo getSettleDetailListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     SaCard card = this.cardService.selectByPrimaryKey(getSettleDetailListBo.getCardId());
     if (card == null) {
       card = this.cardService.selectSettleByPrimaryKey(getSettleDetailListBo.getCardId());
     }

     MchInfo mchInfo = this.mchInfoService.selectMchInfo(card.getUserId());
     GetSettleListBo getSettleListBo = (GetSettleListBo)BeanUtil.copyProperties(getSettleDetailListBo, GetSettleListBo.class);



     if ((mchInfo.getMchId().equals("1") && mchInfo.getType().equals(MchTypeEnum.PLATFORM.name())) || mchInfo
       .getType().equals(MchTypeEnum.MCH.name())) {
       getSettleListBo = (GetSettleListBo)BeanUtil.copyProperties(getSettleDetailListBo, GetSettleListBo.class);
       getSettleListBo.setMchId(mchInfo.getMchId());
       getSettleListBo.setStartDate(DateUtils.getTimestamp(card.getValidStartTime()));
       getSettleListBo.setEndDate(DateUtils.getTimestamp(card.getValidEndTime()));
       getSettleListBo.setType(MchTypeEnum.PLATFORM.name());

       return getSettleList(getSettleListBo, mchInfo, true, card.getConfigNumber());
     }


     getSettleListBo.setMchId(mchInfo.getMchId());
     getSettleListBo.setStartDate(DateUtils.getTimestamp(card.getValidStartTime()));
     getSettleListBo.setEndDate(DateUtils.getTimestamp(card.getValidEndTime()));
     getSettleListBo.setType(mchInfo.getType());

     CommonResult<PageModel<SettleCardVo>> result = getSettleList(getSettleListBo, mchInfo, true, card.getConfigNumber());




     return result;
   }

   public CommonResult<PageModel<List<SettlePayOrderVo>>> getSettlePayOrderList(GetSettlePayOrderDetailListBo getSettlePayOrderDetailListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     PageModel pageModel = new PageModel();


     String confirmStatus = "0";
     SaCard card = this.cardService.selectByPrimaryKey(getSettlePayOrderDetailListBo.getCardId());

     if (card == null) {
       _log.info("结算卡号：" + getSettlePayOrderDetailListBo.getCardId(), new Object[0]);
       card = this.cardService.selectSettleByPrimaryKey(getSettlePayOrderDetailListBo.getCardId());
       int dateStatus = DateUtils.between(LocalDateTime.now(), card.getValidStartTime(), card.getValidEndTime());
       if (dateStatus != 0)
       {
         if (dateStatus != 1)
         {
           if (dateStatus == 2) {
             confirmStatus = card.getCardStatus();
           }
         }
       }
       confirmStatus = card.getCardStatus();
     } else {
       int dateStatus = DateUtils.between(LocalDateTime.now(), card.getValidStartTime(), card.getValidEndTime());
       if (dateStatus == 0) {
         throw new Exception("当前时间早于记账有效期");
       }
       if (dateStatus == 1) {
         confirmStatus = SettleStatusEnum.ING.getType();
       } else if (dateStatus == 2) {
         confirmStatus = SettleStatusEnum.SETTLED.getType();
       }
     }

     PayOrderExample example = new PayOrderExample();
     if (getSettlePayOrderDetailListBo.getType().intValue() == 1) {
       example.setOffset(null);
       example.setLimit(null);
     } else {
       example.setOffset(Integer.valueOf((getSettlePayOrderDetailListBo.getPageIndex().intValue() - 1) * getSettlePayOrderDetailListBo.getPageSize().intValue()));
       example.setLimit(getSettlePayOrderDetailListBo.getPageSize());
     }
     example.setOrderByClause("PaySuccTime DESC");
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdEqualTo(card.getUserId());
     criteria.andPaySuccTimeBetween(DateUtils.getTimestamp(card.getValidStartTime()),
         DateUtils.getTimestamp(card.getValidEndTime()));
     criteria.andStatusBetween(Byte.valueOf((byte)2), Byte.valueOf((byte)3));
     List<PayOrder> settleFailedList = this.payOrderMapper.selectSettleFailedByExample(example);

     List<String> ids = new ArrayList<>();
     if (settleFailedList.size() > 0) {
       for (PayOrder p : settleFailedList) {
         try {
           CommonResult commonResult1 = this.innerSettleService.settleIncoming(p.getPayOrderId(), p.getMchId(), p.getChannelId());
           if (commonResult1.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
             this.baseService4PayOrder.deleteSettleFailedByPrimaryKey(p.getPayOrderId()); continue;
           }
           ids.add(p.getPayOrderId());
         }
         catch (Exception e) {
           e.printStackTrace();
           ids.add(p.getPayOrderId());
         }
       }
     }
     if (ids.size() > 0) {
       criteria.andPayOrderIdNotIn(ids);
     }
     int count = this.payOrderMapper.countByExample(example);
     List<PayOrder> payOrderList = this.payOrderMapper.selectByExample(example);
     List<SettlePayOrderVo> settlePayOrderVoList = BeanUtil.copyProperties(payOrderList, SettlePayOrderVo.class);
     Long total = Long.valueOf(0L);
     for (SettlePayOrderVo p : settlePayOrderVoList) {
       total = Long.valueOf(total.longValue() + p.getAmount().longValue());
     }
     pageModel.setList(settlePayOrderVoList);
     pageModel.setCount(Integer.valueOf(count));
     pageModel.setMsg("ok");
     pageModel.setRel(Boolean.valueOf(true));
     CommonResult<PageModel<List<SettlePayOrderVo>>> commonResult = CommonResult.success(pageModel);
     commonResult.setExtra(total.toString());
     return commonResult;
   }


   public Object exportSettlePayOrderList(GetSettlePayOrderDetailListBo getSettlePayOrderDetailListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     getSettlePayOrderDetailListBo.setType(Integer.valueOf(1));
     CommonResult<PageModel<List<SettlePayOrderVo>>> commonResult = getSettlePayOrderList(getSettlePayOrderDetailListBo, request);
     List<SettlePayOrderVo> result = ((PageModel)commonResult.getData()).getList();
     List<SettlePayOrderExportVo> settlePayOrderExportVos = new ArrayList<>();
     result.stream().forEach(e -> {
           SettlePayOrderExportVo settlePayOrderExportVo = new SettlePayOrderExportVo();
           settlePayOrderExportVo.setCfa(e.getCfa() + CurrencyTypeEnum.valueOf(e.getCurrency().toUpperCase()).getDesc());
           settlePayOrderExportVo.setTia(e.getTia() + CurrencyTypeEnum.valueOf(e.getCurrency().toUpperCase()).getDesc());
           settlePayOrderExportVo.setVia(e.getVia() + CurrencyTypeEnum.valueOf(e.getCurrency().toUpperCase()).getDesc());
           settlePayOrderExportVo.setUserNo(e.getUserNo());
           settlePayOrderExportVo.setUpdateTimeStr(DateUtil.date2Str(e.getUpdateTime(), "yyyy-MM-dd"));
           settlePayOrderExportVo.setMchOrderNo(e.getMchOrderNo());
           settlePayOrderExportVos.add(settlePayOrderExportVo);
         });
     ExcelUtil<SettlePayOrderExportVo> util = new ExcelUtil(SettlePayOrderExportVo.class);
     return util.exportExcel(settlePayOrderExportVos, "结算订单详情", this.env);
   }

   public Object exportSettleList(GetSettleListBo getSettleListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     getSettleListBo.setPageSize(Integer.valueOf(0));
     List<SettleCardVo> result = ((PageModel)getSettleList(getSettleListBo).getData()).getList();
     List<SettleCardExportVo> settleCardExportVos = new ArrayList<>();
     result.stream().forEach(e -> {
           SettleCardExportVo settleCardExportVo = new SettleCardExportVo();
           settleCardExportVo.setCfa(e.getCfa());
           settleCardExportVo.setPda(e.getPda());
           settleCardExportVo.setTia(e.getTia());
           settleCardExportVo.setUserName(e.getUserName());
           settleCardExportVo.setVia(e.getVia());
           settleCardExportVo.setWda(e.getWda());
           settleCardExportVo.setValidStartTimeStr(DateUtil.date2Str(Date.from(e.getValidStartTime().atZone(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"));
           settleCardExportVos.add(settleCardExportVo);
         });
     ExcelUtil<SettleCardExportVo> util = new ExcelUtil(SettleCardExportVo.class);
     return util.exportExcel(settleCardExportVos, "结算订单详情", this.env);
   }

   public Object exportSettleDetailList(GetSettleDetailListBo getSettleDetailListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     getSettleDetailListBo.setPageSize(Integer.valueOf(0));
     List<SettleCardVo> result = ((PageModel)getSettleDetailListV1(getSettleDetailListBo, request).getData()).getList();
     List<SettleDetailExportVo> settleDetailExportVos = new ArrayList<>();
     result.stream().forEach(e -> {
           SettleDetailExportVo settleDetailExportVo = new SettleDetailExportVo();
           settleDetailExportVo.setCfa(e.getCfa());
           settleDetailExportVo.setPda(e.getPda());
           settleDetailExportVo.setTia(e.getTia());
           settleDetailExportVo.setUserName(e.getUserName());
           settleDetailExportVo.setVia(e.getVia());
           settleDetailExportVo.setWda(e.getWda());
           settleDetailExportVo.setValidStartTimeStr(DateUtil.date2Str(Date.from(e.getValidStartTime().atZone(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"));
           settleDetailExportVos.add(settleDetailExportVo);
         });
     ExcelUtil<SettleDetailExportVo> util = new ExcelUtil(SettleDetailExportVo.class);
     return util.exportExcel(settleDetailExportVos, "结算订单详情", this.env);
   }

   public Object exportCashTodoList(GetSettleTodoListBo getSettleTodoListBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     getSettleTodoListBo.setPageSize(Integer.valueOf(0));
     List<SettleCardVo> result = (List<SettleCardVo>)getSettleWithdrawTodoList(getSettleTodoListBo, request).getData();
     List<SettleCardExportVo> settleCardExportVos = new ArrayList<>();
     result.stream().forEach(e -> {
           SettleCardExportVo settleCardExportVo = new SettleCardExportVo();
           settleCardExportVo.setCfa(e.getCfa());
           settleCardExportVo.setPda(e.getPda());
           settleCardExportVo.setTia(e.getTia());
           settleCardExportVo.setUserName(e.getUserName());
           settleCardExportVo.setVia(e.getVia());
           settleCardExportVo.setWda(e.getWda());
           settleCardExportVo.setValidStartTimeStr(DateUtil.date2Str(Date.from(e.getValidStartTime().atZone(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"));
           settleCardExportVos.add(settleCardExportVo);
         });
     ExcelUtil<SettleCardExportVo> util = new ExcelUtil(SettleCardExportVo.class);
     return util.exportExcel(settleCardExportVos, "结算订单详情", this.env);
   }








   private List<SettleCardVo> getVirtualSettleList(GetSettleListBo getSettleListBo, boolean fromChildes, String status, String channelMchId) throws Exception {
     List<SettleCardVo> result = new ArrayList<>();
     String mchId = getSettleListBo.getMchId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     if (mchInfo == null) {
       return result;
     }
     return getVirtualSettleBySettleTime(getSettleListBo, fromChildes, status, channelMchId);
   }









   private List<SettleCardVo> getVirtualSettleBySettleTime(GetSettleListBo getSettleListBo, boolean fromChildes, String status, String channelMchId) throws Exception {
     List<SaCard> result = new ArrayList<>();
     String mchId = getSettleListBo.getMchId();
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     if (mchInfo == null) {
       return null;
     }

     int runMode = this.runModeService.getRunModeCode();
     List<MchInfo> childes = this.mchInfoService.getMchInfoListByParentMchId(mchId);

     List<String> allMchIds = new ArrayList<>();
     Map<String, String> mchTypeMap = new HashMap<>();
     SaCardExample saCardExample = new SaCardExample();
     SaCardExample.Criteria criteria = saCardExample.createCriteria();
     saCardExample.setOrderByClause("VALID_END_TIME DESC");





     if (fromChildes) {
       if (childes.size() == 0) {
         return new ArrayList<>();
       }
       for (MchInfo m : childes) {
         allMchIds.add(m.getMchId());
         mchTypeMap.put(m.getMchId(), m.getType());
       }
       if (getSettleListBo.getStartDate() != null) {
         LocalDateTime settleStartTime = DateUtils.getLocalDateTime(getSettleListBo.getStartDate());
         criteria.andValidStartTimeGreaterThanOrEqualTo(settleStartTime);
       }
     } else {
       allMchIds.add(mchId);
       mchTypeMap.put(mchInfo.getMchId(), mchInfo.getType());
       if (getSettleListBo.getStartDate() != null) {
         LocalDateTime settleStartTime = DateUtils.getLocalDateTime(getSettleListBo.getStartDate());
         criteria.andValidEndTimeGreaterThanOrEqualTo(settleStartTime);
       }
     }
     if (getSettleListBo.getEndDate() != null) {
       LocalDateTime settleEndTime = DateUtils.getLocalDateTime(getSettleListBo.getEndDate());
       criteria.andValidEndTimeLessThanOrEqualTo(settleEndTime);
     }
     CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.CNY;
     if (StringUtils.isNotBlank(getSettleListBo.getCurrency())) {
       currencyTypeEnum = CurrencyTypeEnum.valueOf(getSettleListBo.getCurrency().toUpperCase());
     }
     if (StringUtils.isNotEmpty(channelMchId)) {
       criteria.andConfigNumberEqualTo(channelMchId);
     }
     criteria.andRemainPartGreaterThan(Long.valueOf(0L));
     criteria.andCurrencyEqualTo(currencyTypeEnum.name());
     criteria.andCardTypeEqualTo(CardDataTypeEnum.SETTLE.name());
     criteria.andUserIdIn(allMchIds);
     List<SaCard> saCards = this.cardService.selectByExample(saCardExample);


     for (int i = 0; i < saCards.size(); i++) {
       List<MchInfo> temp; SaCard card = saCards.get(i);
       saCardExample = new SaCardExample();
       criteria = saCardExample.createCriteria();

       if (mchInfo.getType().equals(MchTypeEnum.PLATFORM.name())) {

         temp = childes;
       } else {
         temp = this.mchInfoService.getBranchMchInfoListByParentExternalId(card.getUserId());
       }
       allMchIds.clear();
       for (MchInfo m : temp) {
         allMchIds.add(m.getMchId());
         mchTypeMap.put(m.getMchId(), m.getType());
       }
       if (fromChildes) {
         if (temp.size() == 0)
         {
           allMchIds.clear();
         }
       }
       else if (mchInfo.getType().equals(MchTypeEnum.MCH_BRANCH.name())) {

         allMchIds.clear();
       }

       if (allMchIds.size() == 0) {

         if (LocalDateTime.now().compareTo(card.getValidEndTime()) >= 0) {
           card.setCardStatus(SettleStatusEnum.SETTLED.getType());
         }
       } else {

         if (StringUtils.isNotEmpty(channelMchId)) {
           criteria.andConfigNumberEqualTo(channelMchId);
         }
         criteria.andUserIdIn(allMchIds);
         criteria.andRemainPartGreaterThan(Long.valueOf(0L));
         criteria.andCurrencyEqualTo(currencyTypeEnum.name());
         criteria.andCardTypeEqualTo(CardDataTypeEnum.SETTLE.name());



         criteria.andValidStartTimeGreaterThanOrEqualTo(card.getValidStartTime());
         criteria.andValidEndTimeLessThanOrEqualTo(card.getValidEndTime());

         long count = this.cardService.countByExample(saCardExample);
         if (count == 0L) {
           String platformMchType = MchTypeEnum.PLATFORM.name();




           if (mchInfo.getType().equals(platformMchType)) {

             List<SaSettleCard> settleCards = this.cardService.selectByExampleFromSettleCard(saCardExample);

             int tempStatus = SettleStatusEnum.CASH_COMPLETED.getCode().intValue();
             for (SaSettleCard settleCard : settleCards) {
               int subStatus = Integer.decode(settleCard.getCardStatus()).intValue();
               if (subStatus < tempStatus) {
                 tempStatus = subStatus;
               }
             }
             if (tempStatus == SettleStatusEnum.CASH_COMPLETED.getCode().intValue()) {

               if (this.cardService.copyToSettleCard(card.getCardNumber()) != null);

               this.cardService.settleDone(mchInfo, card, SettleStatusEnum.CASH_COMPLETED);
               card.setCardStatus(SettleStatusEnum.DELETE.getType());
             }
           } else {

             card.setCardStatus(SettleStatusEnum.SETTLED.getType());
           }
         }
       }

       if ((status.equals(card.getCardStatus()) || status.equals(SettleStatusEnum.ALL.getType())) &&
         !card.getCardStatus().equals(SettleStatusEnum.DELETE.getType())) {
         card.setMchType(mchTypeMap.get(card.getUserId()));
         result.add(card);
       }
     }
     return BeanUtil.copyProperties(result, SettleCardVo.class);
   }

   public CommonResult confirmSettle(String fromSettleCardNo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.settleTrans(fromSettleCardNo);
   }

   public CommonResult tryToFixSettle(String fromSettleCardNo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.tryToFixSettleTrans(fromSettleCardNo);
   }

   public CommonResult tryToFixSettleByMchIdInPlatform(FixSettleBo fixSettleBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.tryToFixSettleTransByMchIdInPlatform(fixSettleBo);
   }

   public CommonResult settleWithdraw(String settleCardNo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.settleWithdraw(settleCardNo);
   }

   public CommonResult doSettleWithdraw(String settleTransOrderNo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.settleDoWithdraw(settleTransOrderNo);
   }

   public CommonResult syncSettleWithdrawToPlatform(String params, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     return this.innerSettleService.syncSettleWithdrawToPlatform(params);
   }

   public CommonResult platformConfirmSettle(ConfirmAgencySettleBo confirmAgencySettleBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }

     WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
     workFlowAuditAddBo.setSyncAuditDataType("ADD");
     workFlowAuditAddBo.setAuthor(confirmAgencySettleBo.getOperatorId());
     workFlowAuditAddBo.setAuthorName(confirmAgencySettleBo.getOperator());
     workFlowAuditAddBo.setBizId(confirmAgencySettleBo.getSettleCardNo());
     SaCard card = this.cardService.selectSettleByPrimaryKey(confirmAgencySettleBo.getSettleCardNo());
     TransOrder transOrder = this.transOrderService.selectTransOrder(card.getUserId() + "-" + confirmAgencySettleBo.getSettleCardNo());
     transOrder.setExtra(null);
     workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(transOrder));
     workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
     workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong("1")));
     workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
     workFlowAuditAddBo.setServletPath("/mch_settle/auditPlatformConfirmSettle");
     workFlowAuditAddBo.setSyncAuditDataType("ADD");

     CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
     if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
       return CommonResult.success("确认结算单成功");
     }
     return CommonResult.success("工作流同步失败");
   }


   public CommonResult auditPlatformConfirmSettle(SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
       return CommonResult.error("非授权IP");
     }
     int audit = (syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0;
     if (audit == 1) {
       return this.innerSettleService.settleDoWithdrawBySettleCardNo(syncAuditStatusBo.getBizId());
     }
     return CommonResult.success("操作成功");
   }
 }

