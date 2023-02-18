package org.hlpay.base.card.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.model.SaDealRecord;

public interface SaDealRecordService {
  int insertSaDealRecord(SaDealRecord paramSaDealRecord) throws Exception;
  
  int insertErrorSaDealRecord(SaDealRecord paramSaDealRecord) throws Exception;
  
  int deleteSaDealRecord(@Param("dealRecordId") String paramString) throws Exception;
  
  int updateSaDealRecord(SaDealRecord paramSaDealRecord) throws Exception;
  
  int updateSaDealRecord(Map<String, String> paramMap) throws Exception;
  
  PageBean selectSaDealRecord(Integer paramInteger1, Integer paramInteger2, SaDealRecord paramSaDealRecord) throws Exception;
  
  List<SaDealRecord> selectSaDealRecords(SaDealRecord paramSaDealRecord) throws Exception;
  
  List<SaDealRecord> info(@Param("dealRecordId") String paramString) throws Exception;
  
  List<Map<String, Integer>> totalDeal(SaDealRecord paramSaDealRecord) throws Exception;
  
  int baseInsert(SaDealRecord paramSaDealRecord) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaDealRecord paramSaDealRecord) throws Exception;
  
  int baseUpdateOrderDetail(SaDealRecord paramSaDealRecord) throws Exception;
  
  List<SaDealRecord> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaDealRecord paramSaDealRecord) throws Exception;
  
  SaDealRecord baseInfo(SaDealRecord paramSaDealRecord) throws Exception;
  
  String maxValue(String paramString) throws Exception;
  
  Integer count() throws Exception;
  
  int deleteM(SaDealRecord paramSaDealRecord) throws Exception;
}
