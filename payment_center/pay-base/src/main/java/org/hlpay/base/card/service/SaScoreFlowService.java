package org.hlpay.base.card.service;

import java.util.List;
import java.util.Map;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.model.SaDealRecord;
import org.hlpay.base.model.SaScoreFlow;
import org.hlpay.common.entity.CommonResult;

public interface SaScoreFlowService {
  int insertSaScoreFlow(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  int deleteSaScoreFlow(String paramString) throws Exception;
  
  int updateSaScoreFlow(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  int update(Map<String, String> paramMap) throws Exception;
  
  PageBean selectSaScoreFlow(SaScoreFlow paramSaScoreFlow, Integer paramInteger1, Integer paramInteger2) throws Exception;
  
  List<SaScoreFlow> infoSaScoreFlow(String paramString) throws Exception;
  
  int noConfigScore(String paramString) throws Exception;
  
  List<Map<String, Integer>> totalScore(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<String> countUserId() throws Exception;
  
  long noConfigList(String paramString) throws Exception;
  
  int updateRefund(SaScoreFlow paramSaScoreFlow);
  
  int baseInsert(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  int listInsert(List<SaScoreFlow> paramList) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<SaScoreFlow> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaScoreFlow paramSaScoreFlow) throws Exception;
  
  SaScoreFlow baseInfo(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  CommonResult synCardRefundNotBill(SaDealRecord paramSaDealRecord) throws Exception;
  
  CommonResult synCardForPay(SaDealRecord paramSaDealRecord, boolean paramBoolean) throws Exception;
  
  CommonResult synCardForPayVirtualToReal(List<SaScoreFlow> paramList, SaDealRecord paramSaDealRecord) throws Exception;
  
  Long noCount(SaScoreFlow paramSaScoreFlow) throws Exception;
}
