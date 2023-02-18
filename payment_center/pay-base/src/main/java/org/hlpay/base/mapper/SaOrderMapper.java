package org.hlpay.base.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SaOrder;

public interface SaOrderMapper extends BaseMapper<SaOrder, String> {
  int insertSaOrder(SaOrder paramSaOrder);
  
  int deleteSaOrder(@Param("dealOrderId") String paramString);
  
  int updateSaOrder(SaOrder paramSaOrder);
  
  int update(@Param("maps") Map<String, String> paramMap);
  
  List<SaOrder> selectSaOrder(@Param("userId") String paramString);
  
  List<SaOrder> infoSaOrder(@Param("dealOrderId") String paramString);
  
  int deleteM(SaOrder paramSaOrder);
}

