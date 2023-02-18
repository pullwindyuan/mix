package org.hlpay.base.card.service;

import java.util.List;
import java.util.Map;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.model.SaOrder;

public interface SaOrderService {
  int insertSaOrder(SaOrder paramSaOrder) throws Exception;
  
  int deleteSaOrder(String paramString) throws Exception;
  
  int updateSaOrder(SaOrder paramSaOrder) throws Exception;
  
  int update(Map<String, String> paramMap) throws Exception;
  
  PageBean selectSaOrder(int paramInt1, int paramInt2, String paramString) throws Exception;
  
  List<SaOrder> info(String paramString) throws Exception;
  
  int baseInsert(SaOrder paramSaOrder) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaOrder paramSaOrder) throws Exception;
  
  List<SaOrder> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaOrder paramSaOrder) throws Exception;
  
  SaOrder baseInfo(SaOrder paramSaOrder) throws Exception;
  
  int deleteM(SaOrder paramSaOrder) throws Exception;
}

