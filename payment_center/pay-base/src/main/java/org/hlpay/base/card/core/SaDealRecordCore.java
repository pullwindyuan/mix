package org.hlpay.base.card.core;

import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import org.hlpay.base.card.common.CommonUtils;
import org.hlpay.base.card.ctrl.SaDealRecordController;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.model.SaOrder;
import org.hlpay.base.plugin.PageModel;
import org.hlpay.common.util.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


















@Controller
@RequestMapping({"/api/saDealRecord"})
public class SaDealRecordCore
  extends CommonUtils
{
  private static final MyLog _log = MyLog.getLog(SaDealRecordCore.class);
  @Resource
  private SaDealRecordController saDealRecordController;

  @RequestMapping({"list"})
  @ResponseBody
  public String select(Integer start, Integer end, SaDealRecord saDealRecord) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saDealRecordController.select(start, end, saDealRecord);
      _log.info("交易记录查询成功", new Object[0]);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("交易记录查询失败", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
  }

  @RequestMapping({"excel"})
  @ResponseBody
  public String excel(SaDealRecord saDealRecord, Integer start, Integer end) {
    PageModel pageModel = new PageModel();
    String retStr = "";
    try {
      retStr = this.saDealRecordController.excel(saDealRecord, start, end);
      _log.info("交易记录导出成功", new Object[0]);
      return retStr;
    } catch (Exception e) {
      e.printStackTrace();
      _log.info("交易记录导出失败", new Object[0]);
      pageModel.setCode(Integer.valueOf(1));
      pageModel.setMsg("fail");
      return JSONObject.toJSONString(pageModel);
    }
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

