package org.hlpay.base.card.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.card.service.CardBaseService;
import org.hlpay.base.card.service.SaScoreFlowService;
import org.hlpay.base.mapper.SaScoreFlowMapper;
import org.hlpay.base.model.InnerSettleInfo;
import org.hlpay.base.model.MchInfo;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.model.SaScoreFlow;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.base.service.BaseService4PayOrder;
import org.hlpay.base.service.CardService;
import org.hlpay.base.service.MchInfoService;
import org.hlpay.base.service.mq.Mq4MchNotify;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.enumm.CardDataTypeEnum;
import org.hlpay.common.enumm.MchTypeEnum;
import org.hlpay.common.enumm.PayEnum;
import org.hlpay.common.enumm.RunModeEnum;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;












@Service
public class SaScoreFlowServiceImpl
  extends CardBaseService
  implements SaScoreFlowService
{
  @Autowired
  private CardService cardService;
  @Autowired
  private Mq4MchNotify mq4MchNotify;
  @Autowired
  private BaseService4PayOrder baseService4PayOrder;
  @Resource
  private SaScoreFlowMapper saScoreFlowMapper;
  @Autowired
  private SaCardServiceImpl saCardService;
  @Autowired
  private SaDealRecordServiceImpl saDealRecordService;
  @Autowired
  private MchInfoService mchInfoService;
  private static final MyLog _log = MyLog.getLog(SaScoreFlowServiceImpl.class);


  public int insertSaScoreFlow(SaScoreFlow saScoreFlow) throws Exception {
    int result = addSaScoreFlow(saScoreFlow);
    return result;
  }


  public int deleteSaScoreFlow(String scoreFlowId) throws Exception {
    return super.deleteSaScoreFlow(scoreFlowId);
  }


  public int updateSaScoreFlow(SaScoreFlow saScoreFlow) throws Exception {
    return super.updateSaScoreFlow(saScoreFlow);
  }


  public PageBean selectSaScoreFlow(SaScoreFlow saScoreFlow, Integer start, Integer end) throws Exception {
    return super.selectSaScoreFlow(saScoreFlow, start, end);
  }


  public List<SaScoreFlow> infoSaScoreFlow(String scoreFlowId) throws Exception {
    return super.infoSaScoreFlow(scoreFlowId);
  }


  public int noConfigScore(String userId) throws Exception {
    return super.noConfigScore(userId);
  }

  public List<Map<String, Integer>> totalScore(SaScoreFlow saScoreFlow) throws Exception {
    return super.totalScore(saScoreFlow);
  }


  public List<String> countUserId() throws Exception {
    return super.countUserId();
  }


  public long noConfigList(String userId) throws Exception {
    return super.noConfigList(userId);
  }


  public int update(Map<String, String> map) throws Exception {
    return updateScoreFlow(map);
  }


  public int updateRefund(SaScoreFlow saScoreFlow) {
    return super.updateRefund(saScoreFlow);
  }


  public int baseInsert(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowMapper.baseInsert(saScoreFlow);
  }


  public int listInsert(@Param("list") List<SaScoreFlow> list) throws Exception {
    return this.saScoreFlowMapper.listInsert(list);
  }


  public int baseDelete(String s) throws Exception {
    return this.saScoreFlowMapper.baseDelete(s);
  }


  public int baseUpdate(SaScoreFlow saScoreFlow) throws Exception {
    return this.saScoreFlowMapper.baseUpdate(saScoreFlow);
  }



  public List<SaScoreFlow> baseLimitSelect(Integer start, Integer end, SaScoreFlow saScoreFlow) throws Exception {
    PageHelper.offsetPage(start.intValue(), end.intValue(), false);
    return this.saScoreFlowMapper.baseLimitSelect(saScoreFlow);
  }


  public SaScoreFlow baseInfo(SaScoreFlow saScoreFlow) throws Exception {
    return (SaScoreFlow)this.saScoreFlowMapper.baseInfo(saScoreFlow);
  }




  public CommonResult synCardRefundNotBill(SaDealRecord saDealRecord) throws Exception {
    Map<String, String> map = new HashMap<>();
    PageModel pageModel = new PageModel();

    pageModel.setCode(Integer.valueOf(1));
    pageModel.setMsg("fail");
    String retStr = "";

    SaScoreFlow saScoreFlow = new SaScoreFlow();
    saScoreFlow.setScoreFlowNumber(saDealRecord.getDealRecordNumber());
    saScoreFlow.setDealRecordNumber(saDealRecord.getDealRecordNumber());
    saScoreFlow.setFlag(Integer.valueOf(0));
    JSONArray orderList = saDealRecord.getOrderList();


    List<SaCard> cardList = new ArrayList<>();
    if (saDealRecord.getDealType().equals("2")) {
      SaDealRecord saDealRecordNotBill = new SaDealRecord();
      saDealRecordNotBill.setMerchantOrderNumber(saDealRecord.getMerchantOrderNumber());
      saDealRecordNotBill.setDealType("0");
      saDealRecordNotBill = this.saDealRecordService.baseInfo(saDealRecordNotBill);
      if (saDealRecordNotBill != null &&
        StringUtils.isBlank(saDealRecordNotBill.getDealEndTime())) {
        String notBillOrderDetail = saDealRecordNotBill.getOrderDetail();
        JSONObject notBillOrderDetailObj = JSONObject.parseObject(notBillOrderDetail);
        JSONArray notBillOrderList = notBillOrderDetailObj.getJSONArray("orderList");
        SaScoreFlow saScoreFlowNotBill = new SaScoreFlow();
        saScoreFlowNotBill.setDealRecordNumber(saDealRecordNotBill.getDealRecordNumber());
        saScoreFlowNotBill.setStatus(Character.valueOf('0'));
        saScoreFlowNotBill.setScoreFlowDirection("1");
        List<SaScoreFlow> list = this.saScoreFlowMapper.selectSaScoreFlow(saScoreFlowNotBill);
        List<SaScoreFlow> usedlist = new ArrayList<>();


        boolean fullRefund = true;
        if (list != null) {


          for (int i = 0; i < orderList.size(); i++) {

            String userId = orderList.getJSONObject(i).getString("userId");
            String userDealType = orderList.getJSONObject(i).getString("dealType");
            JSONArray endArray = orderList.getJSONObject(i).getJSONArray("end");
            long userRefundAmount = orderList.getJSONObject(i).getLongValue("amount");
            for (int j = 0; j < endArray.size(); j++) {
              String otherUserId = endArray.getJSONObject(j).getString("userId");
              String otherDealType = endArray.getJSONObject(j).getString("dealType");
              long otherRefundAmount = endArray.getJSONObject(j).getLongValue("amount");

              for (int k = 0; k < list.size(); k++) {
                String refundUserId; long refundAmount; if (userDealType.equals("0")) {
                  refundUserId = userId;
                  refundAmount = userRefundAmount;
                } else {
                  refundUserId = otherUserId;
                  refundAmount = otherRefundAmount;
                }
                if (refundUserId.equals(((SaScoreFlow)list.get(k)).getUserId())) {
                  SaCard sc = new SaCard();
                  sc.setCardNumber(((SaScoreFlow)list.get(k)).getOneselfCardNumber());
                  sc.setNotBill(Long.valueOf(refundAmount));
                  sc.setFlag(Integer.valueOf(0));
                  cardList.add(sc);
                  long remainAmount = ((SaScoreFlow)list.get(k)).getCurrentDealScore().longValue();
                  if (remainAmount - refundAmount > 0L) {
                    ((SaScoreFlow)list.get(k)).setStatus(Character.valueOf('0'));
                    fullRefund = false;

                    BigDecimal totalAmount = new BigDecimal(notBillOrderDetailObj.getLongValue("amount"));
                    for (int m = 0; m < notBillOrderList.size(); m++) {

                      JSONObject notBillOrder = notBillOrderList.getJSONObject(m);
                      String userIdNotBill = notBillOrder.getString("userId");
                      String userDealTypeNotBill = notBillOrder.getString("dealType");
                      JSONArray endArrayNotBill = notBillOrder.getJSONArray("end");
                      long userRefundAmountNotBill = notBillOrder.getLongValue("amount");
                      BigDecimal userAmountNotBill = new BigDecimal(userRefundAmountNotBill);
                      for (int n = 0; n < endArrayNotBill.size(); n++) {
                        JSONObject notBillEnd = endArrayNotBill.getJSONObject(n);
                        String otherUserIdNotBill = notBillEnd.getString("userId");
                        String otherDealTypeNotBill = notBillEnd.getString("dealType");
                        long otherRefundAmountNotBill = notBillEnd.getLongValue("amount");

                        if (otherUserIdNotBill.equals(refundUserId)) {
                          userAmountNotBill = userAmountNotBill.subtract(new BigDecimal(refundAmount));
                          totalAmount = totalAmount.subtract(new BigDecimal(refundAmount));
                          BigDecimal endAmount = new BigDecimal(otherRefundAmountNotBill - refundAmount);
                          notBillEnd.put("amount", endAmount.stripTrailingZeros().toPlainString());
                        }
                      }
                      notBillOrder.put("amount", userAmountNotBill.stripTrailingZeros().toPlainString());
                    }
                    notBillOrderDetailObj.put("amount", totalAmount.stripTrailingZeros().toPlainString());
                    ((SaScoreFlow)list.get(k)).setCurrentDealScore(Long.valueOf(refundAmount));
                  } else {
                    ((SaScoreFlow)list.get(k)).setStatus(Character.valueOf('2'));
                  }
                  usedlist.add(list.get(k));
                }
              }
            }
          }
          saDealRecordNotBill.setOrderDetail(notBillOrderDetailObj.toJSONString());








          int result = this.saScoreFlowMapper.updateScoreRefundList(usedlist);
          if (result > 0) {
            this.saCardService.updateCardList(cardList);
            if (fullRefund) {
              this.saDealRecordService.baseUpdate(saDealRecordNotBill);
            } else {
              this.saDealRecordService.baseUpdateOrderDetail(saDealRecordNotBill);
            }
            return CommonResult.success("退款成功");
          }
        }
        pageModel.setMsg("not fount virtual bill");
        throw new Exception("无未到账记录");
      }
    }



    return synCardForPay(saDealRecord, false);
  }


  public CommonResult synCardForPayVirtualToReal(List<SaScoreFlow> scoreFlowList, SaDealRecord saDealRecord) throws Exception {
    return synCardForPay(scoreFlowList, saDealRecord, true);
  }


  public CommonResult synCardForPay(SaDealRecord saDealRecord, boolean isVirtualFlow2Real) throws Exception {
    return synCardForPay(null, saDealRecord, isVirtualFlow2Real);
  } private CommonResult synCardForPay(List<SaScoreFlow> saScoreFlowList, SaDealRecord saDealRecord, boolean isVirtualFlow2Real) throws Exception {
    int payMode;
    String channelMchId;
    _log.info("结算记录ID：" + saDealRecord.getDealRecordId(), new Object[0]);
    _log.info("结算记录NO：" + saDealRecord.getDealRecordNumber(), new Object[0]);
    PageModel pageModel = new PageModel();

    pageModel.setCode(Integer.valueOf(1));
    pageModel.setMsg("fail");

    SaScoreFlow saScoreFlow = new SaScoreFlow();
    saScoreFlow.setScoreFlowNumber(saDealRecord.getDealRecordNumber());
    saScoreFlow.setDealRecordNumber(saDealRecord.getDealRecordNumber());
    boolean instantPay = StringUtils.isBlank(saDealRecord.getTripTime());
    if (instantPay) {

      saScoreFlow.setFlag(Integer.valueOf(0));
    } else {

      saScoreFlow.setFlag(Integer.valueOf(1));
    }
    String tripTime = saDealRecord.getTripTime();
    Long retAmount = saDealRecord.getScoreReturnAmount();
    boolean preFreeze = saDealRecord.isPreFreeze();
    JSONArray orderList = saDealRecord.getOrderList();

    List<SaScoreFlow> flowList = new ArrayList<>();
    List<SaCard> cardList = new ArrayList<>();


























    JSONObject dealComment = JSONObject.parseObject(saDealRecord.getDealComment());
    int currRunMode = dealComment.getIntValue("runMode");


    if (currRunMode == RunModeEnum.PUBLIC.getCode().intValue()) {
      payMode = currRunMode;
      channelMchId = "1";
    } else {
      channelMchId = dealComment.getString("channelMchId");
      if (saDealRecord.getUserId().startsWith(channelMchId)) {

        if (this.mchInfoService.selectByPrimaryKey(channelMchId).getType().equals(MchTypeEnum.MCH.name())) {

          payMode = RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue();
        } else {

          payMode = RunModeEnum.PRIVATE.getCode().intValue();
        }
      } else {
        payMode = RunModeEnum.PUBLIC.getCode().intValue();
      }
    }
    for (int i = 0; i < orderList.size(); i++) {
      SaCard startCard;
      String userId = orderList.getJSONObject(i).getString("userId");
      String currency = orderList.getJSONObject(i).getString("currency");
      String cardType = orderList.getJSONObject(i).getString("cardType");
      String userName = orderList.getJSONObject(i).getString("name");
      if (cardType.equals(CardDataTypeEnum.SETTLE.name())) {
        if (StringUtils.isBlank(saDealRecord.getKernelCardNum())) {
          MchInfo mchInfo = this.mchInfoService.selectMchInfo(userId);
          startCard = this.cardService.getSettleCard(mchInfo, currency.toUpperCase(), saDealRecord.getExternalPaySuccessTime(), payMode, channelMchId);
        } else {
          startCard = this.cardService.getCardByCardNumber(saDealRecord.getKernelCardNum());
        }
      } else {
        startCard = this.cardService.getUserCard(userId, userName, cardType, currency, payMode);
      }
      String userCardNumber = startCard.getCardNumber();
      String userLoginAccount = orderList.getJSONObject(i).getString("phone");
      String userCurrency = orderList.getJSONObject(i).getString("currency");
      String userDealType = orderList.getJSONObject(i).getString("dealType");
      String userMchOrderNo = orderList.getJSONObject(i).getString("mchOrderNo");
      String userProductCode = orderList.getJSONObject(i).getString("productCode");
      String userProductName = orderList.getJSONObject(i).getString("productName");
      String userComment = orderList.getJSONObject(i).getString("comment");
      saScoreFlow.setTripTime(tripTime);
      saScoreFlow.setMerchantOrderNumber(userMchOrderNo);
      JSONArray endArray = orderList.getJSONObject(i).getJSONArray("end");

      for (int j = 0; j < endArray.size(); j++) {
        String otherProductCode = endArray.getJSONObject(j).getString("productCode");
        if (otherProductCode.equals(PayEnum.BIZ_CLASS_SETTLE_RECORD.getCode()) || otherProductCode
          .equals(PayEnum.BIZ_CLASS_RECHARGE_TRUST_INCOME.getCode())) {
          SaCard saCard; Long userRemainPart = startCard.getRemainPart();
          String otherUserId = endArray.getJSONObject(j).getString("userId");
          String otherUserName = endArray.getJSONObject(j).getString("name");
          Long otherAmount = endArray.getJSONObject(j).getLong("amount");
          String otherCurrency = endArray.getJSONObject(j).getString("currency");
          String otherDealType = endArray.getJSONObject(j).getString("dealType");
          String otherCardNumber = endArray.getJSONObject(j).getString("cardNum");
          String otherCardType = endArray.getJSONObject(j).getString("cardType");
          if (otherCardType.equals(CardDataTypeEnum.SETTLE.name())) {
            if (StringUtils.isBlank(otherCardNumber)) {
              MchInfo other = this.mchInfoService.selectMchInfo(otherUserId);
              saCard = this.cardService.getSettleCard(other, otherCurrency.toUpperCase(), saDealRecord.getExternalPaySuccessTime(), payMode, channelMchId);
            } else {
              saCard = this.cardService.getCardByCardNumber(otherCardNumber);
            }
          } else {

            saCard = this.cardService.getUserCard(otherUserId, otherUserName, otherCardType, currency, payMode);
          }
          Long otherRemainPart = saCard.getRemainPart();
          SaCard sc = new SaCard();
          if (userDealType.equals("0")) {

            if (instantPay) {
              if (preFreeze) {
                sc.setFreezePart(otherAmount);
              } else {
                if (retAmount.longValue() > userRemainPart.longValue() && !SaCard.isOverdrawCard(startCard)) {
                  _log.info("发起方余额小于支出总额，发起方ID：" + userId, new Object[0]);
                  _log.info("发起方总额：" + userRemainPart + " 支出总额：" + retAmount, new Object[0]);
                  throw new Exception();
                }
                sc.setRemainPart(otherAmount);
              }
              sc.setCardNumber(userCardNumber);
              sc.setFlag(Integer.valueOf(0));
              cardList.add(sc);
            }
          } else if (userDealType.equals("1")) {

            sc.setCardNumber(userCardNumber);
            if (isVirtualFlow2Real || !instantPay) {
              sc.setNotBill(otherAmount);
            } else {
              sc.setRemainPart(otherAmount);
            }
            sc.setFlag(Integer.valueOf(1));
            sc.setFreezePart(saDealRecord.getPrice());
            sc.setNotBill(saDealRecord.getCutOff());
            cardList.add(sc);
          } else {
            throw new Exception("不支持的内部交易类型");
          }
          sc = new SaCard();
          if (otherDealType.equals("1")) {
            sc.setCardNumber(otherCardNumber);
            if (isVirtualFlow2Real || !instantPay) {
              sc.setNotBill(otherAmount);
            } else {
              sc.setRemainPart(otherAmount);
            }
            sc.setFlag(Integer.valueOf(1));
            sc.setFreezePart(saDealRecord.getPrice());
            sc.setNotBill(saDealRecord.getCutOff());
            cardList.add(sc);
          } else if (otherDealType.equals("0") &&
            instantPay) {
            if (preFreeze) {
              sc.setFreezePart(otherAmount);
            } else {
              if (retAmount.longValue() > otherRemainPart.longValue() && !SaCard.isOverdrawCard(saCard)) {
                _log.info("用户余额小于支出总额，用户ID：" + otherUserId, new Object[0]);
                _log.info("用户总额：" + otherRemainPart + " 支出总额：" + retAmount, new Object[0]);
                throw new Exception();
              }
              sc.setRemainPart(otherAmount);
            }
            sc.setCardNumber(otherCardNumber);
            sc.setFlag(Integer.valueOf(0));
            cardList.add(sc);
          }
        } else {
          SaCard saCard;
          Long userRemainPart = startCard.getRemainPart();
          Long userFreezePart = startCard.getFreezePart();
          String uname = startCard.getUserName();
          String otherUserId = endArray.getJSONObject(j).getString("userId");
          String otherUserName = endArray.getJSONObject(j).getString("name");
          String otherLoginAccount = endArray.getJSONObject(j).getString("phone");
          Long otherAmount = endArray.getJSONObject(j).getLong("amount");
          String otherCurrency = endArray.getJSONObject(j).getString("currency");
          String otherDealType = endArray.getJSONObject(j).getString("dealType");
          String otherProductName = endArray.getJSONObject(j).getString("productName");
          String otherComment = endArray.getJSONObject(j).getString("comment");
          String otherCardNumber = endArray.getJSONObject(j).getString("cardNum");
          String otherCardType = endArray.getJSONObject(j).getString("cardType");
          if (otherCardType.equals(CardDataTypeEnum.SETTLE.name())) {
            if (StringUtils.isBlank(otherCardNumber)) {
              MchInfo other = this.mchInfoService.selectMchInfo(otherUserId);
              saCard = this.cardService.getSettleCard(other, otherCurrency.toUpperCase(), saDealRecord.getExternalPaySuccessTime(), payMode, channelMchId);
            } else {
              saCard = this.cardService.getCardByCardNumber(otherCardNumber);
            }
          } else {
            saCard = this.cardService.getUserCard(otherUserId, otherUserName, otherCardType, currency, payMode);
          }
          Long otherRemainPart = saCard.getRemainPart();
          Long otherFreezePart = saCard.getFreezePart();
          String ouName = saCard.getUserName();
          saScoreFlow.setFlowType(userProductCode);
          saScoreFlow.setProductType(userProductName);
          saScoreFlow.setComment(userComment);
          saScoreFlow.setCurrency(userCurrency);
          saScoreFlow.setUserId(userId);
          saScoreFlow.setScoreFlowDirection(userDealType);
          saScoreFlow.setOneselfAccount(userLoginAccount);
          saScoreFlow.setOneselfCardNumber(userCardNumber);
          saScoreFlow.setOthersAccount(otherLoginAccount);
          saScoreFlow.setOthersCardNumber(otherCardNumber);
          saScoreFlow.setCurrentDealScore(otherAmount);
          saScoreFlow.setUserName(uname);
          SaCard sc = new SaCard();
          if (userDealType.equals("0")) {

            saScoreFlow.setRemainScore(Long.valueOf(userRemainPart.longValue() - otherAmount.longValue() + userFreezePart.longValue()));
            saScoreFlow.setStatus(Character.valueOf('1'));
            SaScoreFlow flowItem = new SaScoreFlow(saScoreFlow);
            flowList.add(flowItem);
            if (instantPay) {
              if (preFreeze) {
                sc.setFreezePart(otherAmount);
              } else {
                if (retAmount.longValue() > userRemainPart.longValue() && !SaCard.isOverdrawCard(startCard)) {
                  _log.info("发起方余额小于支出总额，发起方ID：" + userId, new Object[0]);
                  _log.info("发起方总额：" + userRemainPart + " 支出总额：" + retAmount, new Object[0]);
                  throw new Exception();
                }
                sc.setRemainPart(otherAmount);
              }
              sc.setCardNumber(userCardNumber);
              sc.setFlag(Integer.valueOf(0));
              cardList.add(sc);
            }
          } else if (userDealType.equals("1")) {

            saScoreFlow.setRemainScore(Long.valueOf(userRemainPart.longValue() + otherAmount.longValue() + userFreezePart.longValue()));
            if (instantPay) {
              saScoreFlow.setStatus(Character.valueOf('1'));
            } else {
              saScoreFlow.setStatus(Character.valueOf('0'));
            }
            SaScoreFlow flowItem = new SaScoreFlow(saScoreFlow);
            flowList.add(flowItem);
            sc.setCardNumber(userCardNumber);
            if (isVirtualFlow2Real || !instantPay) {
              sc.setNotBill(otherAmount);
            } else {
              sc.setRemainPart(otherAmount);
            }
            sc.setFlag(Integer.valueOf(1));
            sc.setFreezePart(saDealRecord.getPrice());
            sc.setNotBill(saDealRecord.getCutOff());
            cardList.add(sc);
          } else {
            throw new Exception("不支持的内部交易类型");
          }
          saScoreFlow.setCurrency(otherCurrency);
          saScoreFlow.setUserId(otherUserId);
          saScoreFlow.setScoreFlowDirection(otherDealType);
          saScoreFlow.setOneselfAccount(otherLoginAccount);
          saScoreFlow.setOneselfCardNumber(otherCardNumber);
          saScoreFlow.setOthersAccount(userLoginAccount);
          saScoreFlow.setOthersCardNumber(userCardNumber);
          saScoreFlow.setFlowType(otherProductCode);
          saScoreFlow.setProductType(otherProductName);
          saScoreFlow.setComment(otherComment);
          saScoreFlow.setUserName(ouName);
          sc = new SaCard();
          if (otherDealType.equals("1")) {
            saScoreFlow.setRemainScore(Long.valueOf(otherRemainPart.longValue() + otherAmount.longValue() + otherFreezePart.longValue()));
            if (instantPay) {
              saScoreFlow.setStatus(Character.valueOf('1'));
            } else {
              saScoreFlow.setStatus(Character.valueOf('0'));
            }
            SaScoreFlow flowItem = new SaScoreFlow(saScoreFlow);
            flowList.add(flowItem);
            sc.setCardNumber(otherCardNumber);
            if (isVirtualFlow2Real || !instantPay) {
              sc.setNotBill(otherAmount);
            } else {
              sc.setRemainPart(otherAmount);
            }
            sc.setFlag(Integer.valueOf(1));
            sc.setFreezePart(saDealRecord.getPrice());
            sc.setNotBill(saDealRecord.getCutOff());
            cardList.add(sc);
          } else if (otherDealType.equals("0")) {
            saScoreFlow.setRemainScore(Long.valueOf(otherRemainPart.longValue() - otherAmount.longValue() + otherFreezePart.longValue()));
            saScoreFlow.setStatus(Character.valueOf('1'));
            SaScoreFlow flowItem = new SaScoreFlow(saScoreFlow);
            flowList.add(flowItem);
            if (instantPay) {
              if (preFreeze) {
                sc.setFreezePart(otherAmount);
              } else {
                if (retAmount.longValue() > otherRemainPart.longValue() && !SaCard.isOverdrawCard(saCard)) {
                  _log.info("用户余额小于支出总额，用户ID：" + otherUserId, new Object[0]);
                  _log.info("用户总额：" + otherRemainPart + " 支出总额：" + retAmount, new Object[0]);
                  throw new Exception();
                }
                sc.setRemainPart(otherAmount);
              }
              sc.setCardNumber(otherCardNumber);
              sc.setFlag(Integer.valueOf(0));
              cardList.add(sc);
            }
          }
        }
      }
    }
    InnerSettleInfo innerSettleInfo = InnerSettleInfo.create(saDealRecord, cardList, saScoreFlowList, flowList);
    if (instantPay) {
      int result; if (isVirtualFlow2Real) {
        result = this.saCardService.doInnerVirtualSettleToReal(innerSettleInfo);
      } else {
        result = this.saCardService.doInnerSettle(innerSettleInfo);
      }

      if (result > 0) {
        return CommonResult.success("交易成功");
      }
    }
    throw new Exception("交易失败");
  }


  public Long noCount(SaScoreFlow saScoreFlow) {
    return this.saScoreFlowMapper.noCount(saScoreFlow);
  }
}

