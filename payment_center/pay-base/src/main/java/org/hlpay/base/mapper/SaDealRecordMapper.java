package org.hlpay.base.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SaDealRecord;

public interface SaDealRecordMapper extends BaseMapper<SaDealRecord, String> {
  int insertSaDealRecord(SaDealRecord paramSaDealRecord);
  
  int insertErrorSaDealRecord(SaDealRecord paramSaDealRecord);
  
  int delete(@Param("dealRecordId") String paramString);
  
  int update(SaDealRecord paramSaDealRecord);
  
  int updateSaDealRecord(@Param("maps") Map<String, String> paramMap);
  
  List<SaDealRecord> select(SaDealRecord paramSaDealRecord);
  
  List<SaDealRecord> info(@Param("dealRecordId") String paramString);
  
  int count(SaDealRecord paramSaDealRecord);
  
  int totalDeal(SaDealRecord paramSaDealRecord);
  
  String maxValue(@Param("dealCreateTime") String paramString);
  
  int deleteM(SaDealRecord paramSaDealRecord);
}
