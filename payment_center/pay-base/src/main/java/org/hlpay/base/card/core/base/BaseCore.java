package org.hlpay.base.card.core.base;

import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.model.SaOrder;
import org.hlpay.base.model.SaScoreFlow;

public class BaseCore
{
  public SaScoreFlow getSaScoreFlowInstance(SaOrder saOrder, SaDealRecord saDealRecord, String scoreFlowNumber) throws Exception {
    SaScoreFlow saScoreFlow = new SaScoreFlow();
    saScoreFlow.setScoreFlowNumber(scoreFlowNumber);
    saScoreFlow.setDealRecordNumber(saOrder.getDealRecordNumber());
    saScoreFlow.setMerchantOrderNumber(saOrder.getMerchantOrderNumber());
    saScoreFlow.setTripTime(saDealRecord.getTripTime());
    saScoreFlow.setScoreFlowDirection(saOrder.getDealType() + "");
    saScoreFlow.setCreateTime(saDealRecord.getDealEndTime());
    saScoreFlow.setComment(saDealRecord.getDealComment());
    saScoreFlow.setCurrency(saDealRecord.getCurrency());
    if (saOrder.getProxyId() != null && !saOrder.getProxyId().equals(" ")) {
      saScoreFlow.setCurrentDealScore(saOrder.getPartnerAmount());
      saScoreFlow.setUserId(saOrder.getProxyId());
      saScoreFlow.setOneselfAccount(saDealRecord.getProxyAccount());
      saScoreFlow.setOneselfCardNumber(saDealRecord.getOtherCardNumber());
      if (saDealRecord.getPartnerFlag() != null && saDealRecord.getPartnerProductType() != null) {
        saScoreFlow.setFlowType(saDealRecord.getPartnerFlag());
        saScoreFlow.setProductType(saDealRecord.getPartnerProductType());
      } else {
        saScoreFlow.setFlowType(saDealRecord.getFlag());
        saScoreFlow.setProductType(saDealRecord.getProductType());
      }
    } else {
      saScoreFlow.setCurrentDealScore(saOrder.getUserAmount());
      saScoreFlow.setUserId(saOrder.getUserId());
      saScoreFlow.setProxyId(saDealRecord.getProxyNumber());
      saScoreFlow.setOneselfAccount(saDealRecord.getOneselfAccount());
      saScoreFlow.setOneselfCardNumber(saDealRecord.getOneselfCardNumber());
      saScoreFlow.setFlowType(saDealRecord.getFlag());
      saScoreFlow.setProductType(saDealRecord.getProductType());
    }








    saScoreFlow.setOthersAccount(saDealRecord.getOthersAccount());
    saScoreFlow.setOthersCardNumber(saDealRecord.getOthersCardNumber());
    saScoreFlow.setCreateTime(saDealRecord.getDealCreateTime());
    saScoreFlow.setComment(saDealRecord.getDealComment());
    return saScoreFlow;
  }
}





