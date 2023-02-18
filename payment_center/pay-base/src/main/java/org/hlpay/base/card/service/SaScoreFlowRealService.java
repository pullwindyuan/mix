package org.hlpay.base.card.service;

import java.util.List;
import org.hlpay.base.model.SaScoreFlow;

public interface SaScoreFlowRealService {
  int baseInsert(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  int listInsert(List<SaScoreFlow> paramList) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<SaScoreFlow> selectList(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<SaScoreFlow> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaScoreFlow paramSaScoreFlow) throws Exception;
  
  SaScoreFlow baseInfo(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  Long yesterTotal(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  String maxValue(String paramString) throws Exception;
  
  Long partnerYesterTotal(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  Long partnerCount(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<SaScoreFlow> partnerEveryday(Integer paramInteger1, Integer paramInteger2, SaScoreFlow paramSaScoreFlow);
  
  Integer partnerFlowCount(SaScoreFlow paramSaScoreFlow) throws Exception;
  
  Integer count() throws Exception;
  
  List<SaScoreFlow> limitList(Integer paramInteger1, Integer paramInteger2, SaScoreFlow paramSaScoreFlow) throws Exception;
  
  List<SaScoreFlow> countExchange(List<String> paramList) throws Exception;
}
