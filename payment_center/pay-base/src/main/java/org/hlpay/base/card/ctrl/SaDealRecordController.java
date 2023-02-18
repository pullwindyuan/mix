package org.hlpay.base.card.ctrl;

import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.service.impl.SaCardServiceImpl;
import org.hlpay.base.card.service.impl.SaDealRecordServiceImpl;
import org.hlpay.base.model.MchInfo;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.base.service.CardService;
import org.hlpay.base.service.MchInfoService;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;






@Component
public class SaDealRecordController
  extends CommonUtils
{
  private static final MyLog _log = MyLog.getLog(SaDealRecordController.class);

  @Autowired
  private SaDealRecordServiceImpl saDealRecordService;

  @Autowired
  private SaCardServiceImpl saCardService;

  @Autowired
  private CardService cardService;

  @Autowired
  private MchInfoService mchInfoService;

  public String save(SaDealRecord saDealRecord) throws Exception {
    PageModel pageModel = new PageModel();
    SaDealRecord s = new SaDealRecord();
    SaCard saCard = new SaCard();
    saCard.setUserId(saDealRecord.getUserId());
    saCard.setCardType(saDealRecord.getCardType());
    saCard.setCurrency(saDealRecord.getCurrency());
    saCard = this.saCardService.baseInfo(saCard);










    s = this.saDealRecordService.baseInfo(saDealRecord);
    if (s != null && s.getMerchantOrderNumber().equals(saDealRecord.getMerchantOrderNumber()) && s
      .getDealType() == saDealRecord.getDealType() && s.getProductType() == saDealRecord.getProductType()) {
      _log.info("商户订单号已经存在!", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("商户订单号已经存在!");
      return JSONObject.toJSONString(pageModel);
    }
    if (saDealRecord.getDealType().equals("0")) {

      Long remianPart = saCard.getRemainPart();


      if (remianPart.longValue() < saDealRecord.getScoreReturnAmount().longValue() && !SaCard.isOverdrawCard(saCard)) {
        _log.info("支出总额大于可用余额", new Object[0]);
        pageModel.setCode(Integer.valueOf(1));
        pageModel.setMsg("支出总额大于可用余额");
        return JSONObject.toJSONString(pageModel);
      }

    } else if (s != null && !saDealRecord.getDealType().equals("0")) {
      String userId, proxyUserId; _log.info("该商户订单已经存在，交易记录不变", new Object[0]);
      saDealRecord.setDealRecordNumber(s.getDealRecordNumber());
      String sfd = saDealRecord.getDealType();
      switch (sfd) {
        case "2":
          userId = s.getUserId();
          proxyUserId = s.getProxyNumber();
          if (saCard.getFreezePart().longValue() < saDealRecord.getUserAmount().longValue()) {
            _log.info(userId + "用户冻结积分:" + saCard.getFreezePart() + "小于退款积分:" + saDealRecord.getUserAmount(), new Object[0]);
            pageModel.setCode(Integer.valueOf(1));
            pageModel.setMsg("用户冻结积分小于退款积分");
            return JSONObject.toJSONString(pageModel);
          }
          if (proxyUserId != null && !proxyUserId.equals(" ")) {
            saCard.setUserId(proxyUserId);
            saCard = this.saCardService.baseInfo(saCard);
            if (saCard.getFreezePart().longValue() < saDealRecord.getPartnerAmount().longValue()) {
              _log.info(userId + "合伙人冻结积分:" + saCard.getFreezePart() + "小于退款积分:" + saDealRecord.getProxyAccount(), new Object[0]);
              pageModel.setCode(Integer.valueOf(1));
              pageModel.setMsg("合伙人冻结积分小于退款积分");
              return JSONObject.toJSONString(pageModel);
            }
          }
          break;
      }


    } else {
      SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddhhmmssSSS");

      saDealRecord.setDealRecordNumber(sdf.format(new Date()) + ((new Random()).nextInt(899) + 100));
    }















    if (StringUtils.isBlank(saDealRecord.getTripTime())) {
      SimpleDateFormat sdf2 = getSimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      saDealRecord.setDealEndTime(sdf2.format(new Date()));
    }
    int result = this.saDealRecordService.baseInsert(saDealRecord);
    if (result > 0) {
      _log.info("交易记录添加成功,保存记录数：{}", new Object[] { Integer.valueOf(result) });
      pageModel.setCode(Integer.valueOf(0));
      pageModel.setMsg("success");
      return JSONObject.toJSONString(pageModel);
    }
    return JSONObject.toJSONString(pageModel);
  }


  public CommonResult add(SaDealRecord saDealRecord, boolean preFreeze) throws Exception {
    SaCard saCard = new SaCard();
    _log.info("cardType:" + saDealRecord.getCardType(), new Object[0]);
    _log.info("kernelCardNum:" + saDealRecord.getKernelCardNum(), new Object[0]);
    MchInfo mchInfo = this.mchInfoService.selectMchInfo(saDealRecord.getUserId());
    _log.info("getCardNumber:" + saCard.getCardNumber(), new Object[0]);

    SaDealRecord s = this.saDealRecordService.baseInfo(saDealRecord);
    if (s != null) {

      _log.info("商户订单号已经存在!", new Object[0]);
      if (s.getDealStatus().charValue() == '\001') {
        throw new Exception("商户订单已经存在并成功处理");
      }
    }

    if (s != null && saDealRecord.getDealType() != "0") {
      if (saDealRecord.getRefundNumber().equals("0")) {
        SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddhhmmssSSS");
        saDealRecord.setDealRecordNumber(sdf.format(new Date()) + ((new Random()).nextInt(899) + 100));
      } else {
        saDealRecord.setDealRecordNumber(s.getDealRecordNumber());
      }
    } else {
      SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddhhmmssSSS");
      saDealRecord.setDealRecordNumber(sdf.format(new Date()) + ((new Random()).nextInt(899) + 100));
    }

    if (StringUtils.isBlank(saDealRecord.getTripTime())) {
      SimpleDateFormat sdf2 = getSimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      saDealRecord.setDealEndTime(sdf2.format(new Date()));
    }
    if (s == null) {
      int result = this.saDealRecordService.baseInsert(saDealRecord);
      if (result > 0) {
        _log.info("交易记录添加成功,保存记录数：{}", new Object[] { Integer.valueOf(result) });
        return CommonResult.success("交易记录添加成功");
      }
      throw new Exception("交易记录添加失败");
    }

    return CommonResult.success("交易记录添加成功");
  }

  public String delete(String dealRecordId) {
    PageModel pageModel = new PageModel();
    try {
      int result = this.saDealRecordService.deleteSaDealRecord(dealRecordId);
      if (result > 0) {
        _log.info("交易记录删除成功,删除记录数：{}", new Object[] { Integer.valueOf(result) });
        pageModel.setCode(Integer.valueOf(0));
        pageModel.setMsg("success");
        return JSONObject.toJSONString(pageModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("交易记录：{}", new Object[] { "删除失败" });
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
    }
    return JSONObject.toJSONString(pageModel);
  }

  public String select(Integer start, Integer end, SaDealRecord saDealRecord) throws Exception {
    PageModel pageModel = new PageModel();
    List<SaDealRecord> list = this.saDealRecordService.baseLimitSelect(start, end, saDealRecord);
    Integer count = this.saDealRecordService.count();
    pageModel.setCode(Integer.valueOf(0));
    pageModel.setMsg("success");
    pageModel.setList(list);
    pageModel.setCount(count);
    return JSONObject.toJSONString(pageModel);
  }

  public String excel(SaDealRecord saDealRecord, Integer start, Integer end) throws Exception {
    PageModel pageModel = new PageModel();
    List<SaDealRecord> list = this.saDealRecordService.baseLimitSelect(start, end, saDealRecord);
    if (list.size() > 0) {
      importExcel(list, "交易记录表");
      pageModel.setCode(Integer.valueOf(0));
      pageModel.setMsg("success");
      pageModel.setCount(Integer.valueOf(list.size()));
      return JSONObject.toJSONString(pageModel);
    }
    pageModel.setCode(Integer.valueOf(1));
    pageModel.setMsg("没有数据");
    pageModel.setCount(Integer.valueOf(list.size()));

    return JSONObject.toJSONString(pageModel);
  }
}

