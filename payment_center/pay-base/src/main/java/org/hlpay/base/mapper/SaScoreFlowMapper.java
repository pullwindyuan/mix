package org.hlpay.base.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SaScoreFlow;

public interface SaScoreFlowMapper extends BaseMapper<SaScoreFlow, String> {
  int insertSaScoreFlow(SaScoreFlow paramSaScoreFlow);
  
  int deleteSaScoreFlow(@Param("scoreFlowId") String paramString);
  
  int updateSaScoreFlow(SaScoreFlow paramSaScoreFlow);
  
  int update(@Param("maps") Map<String, String> paramMap);
  
  List<SaScoreFlow> selectSaScoreFlow(SaScoreFlow paramSaScoreFlow);
  
  int count(SaScoreFlow paramSaScoreFlow);
  
  int totalScore(SaScoreFlow paramSaScoreFlow);
  
  List<SaScoreFlow> infoSaScoreFlow(@Param("scoreFlowId") String paramString);
  
  int noConfigScore(@Param("userId") String paramString);
  
  List<String> countUserId();
  
  long noConfigList(@Param("userId") String paramString);
  
  int updateRefund(SaScoreFlow paramSaScoreFlow);
  
  List<SaScoreFlow> getSaScoreFlows(SaScoreFlow paramSaScoreFlow);
  
  void updateScore(SaScoreFlow paramSaScoreFlow);
  
  Long yesterTotal(SaScoreFlow paramSaScoreFlow);
  
  Long partnerYesterTotal(SaScoreFlow paramSaScoreFlow);
  
  Long partnerCount(SaScoreFlow paramSaScoreFlow);
  
  List<SaScoreFlow> partnerEveryday(SaScoreFlow paramSaScoreFlow);
  
  Integer partnerFlowCount(SaScoreFlow paramSaScoreFlow);
  
  String maxValue(@Param("createTime") String paramString);
  
  Long noCount(SaScoreFlow paramSaScoreFlow);
  
  int listInsert(@Param("list") List<SaScoreFlow> paramList);
  
  int updateScoreList(@Param("list") List<SaScoreFlow> paramList);
  
  int updateScoreRefundList(@Param("list") List<SaScoreFlow> paramList);
}
