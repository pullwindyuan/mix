package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SaScoreFlow;

public interface SaScoreFlowRealMapper extends BaseMapper<SaScoreFlow, String> {
  List<SaScoreFlow> getSaScoreFlows(SaScoreFlow paramSaScoreFlow);
  
  void updateScore(SaScoreFlow paramSaScoreFlow);
  
  Long yesterTotal(SaScoreFlow paramSaScoreFlow);
  
  Long partnerYesterTotal(SaScoreFlow paramSaScoreFlow);
  
  Long partnerCount(SaScoreFlow paramSaScoreFlow);
  
  List<SaScoreFlow> partnerEveryday(SaScoreFlow paramSaScoreFlow);
  
  Integer partnerFlowCount(SaScoreFlow paramSaScoreFlow);
  
  String maxValue(@Param("createTime") String paramString);
  
  List<SaScoreFlow> limitList(SaScoreFlow paramSaScoreFlow);
  
  List<SaScoreFlow> countExchange(List<String> paramList);
  
  List<SaScoreFlow> listExchange(SaScoreFlow paramSaScoreFlow);
  
  int listInsert(@Param("list") List<SaScoreFlow> paramList);
}
