package org.hlpay.base.card.core;

import javax.annotation.Resource;
import org.hlpay.base.card.core.base.BaseCore;
import org.hlpay.base.card.ctrl.SaDealRecordController;
import org.hlpay.base.card.service.impl.SaScoreFlowServiceImpl;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.enumm.DealTypeEnum;
import org.hlpay.common.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component
public class OrderCore
  extends BaseCore
{
  private static final MyLog _log = MyLog.getLog(OrderCore.class);

  @Resource
  private SaDealRecordController saDealRecordController;

  @Autowired
  private SaScoreFlowServiceImpl saScoreFlowService;

  public CommonResult saveV1(SaDealRecord saDealRecord) throws Exception {
    if (saDealRecord.getDealType().equals(DealTypeEnum.REFUND.getType())) {
      return this.saScoreFlowService.synCardRefundNotBill(saDealRecord);
    }

    boolean preFreeze = saDealRecord.isPreFreeze();
    saDealRecord.setDealStatus(Character.valueOf('0'));
    CommonResult result = this.saDealRecordController.add(saDealRecord, preFreeze);
    if (result.getCode().intValue() != 200) {
      return result;
    }
    return this.saScoreFlowService.synCardForPay(saDealRecord, false);
  }
}

