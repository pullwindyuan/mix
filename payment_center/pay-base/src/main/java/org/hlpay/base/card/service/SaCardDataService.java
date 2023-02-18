package org.hlpay.base.card.service;

import java.util.List;
import org.hlpay.base.model.SaCardData;

public interface SaCardDataService {
  int baseInsert(SaCardData paramSaCardData) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaCardData paramSaCardData) throws Exception;
  
  List<SaCardData> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaCardData paramSaCardData) throws Exception;
  
  SaCardData baseInfo(SaCardData paramSaCardData) throws Exception;
}
