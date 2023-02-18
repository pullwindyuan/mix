package org.hlpay.base.mapper;

import java.util.List;
import org.hlpay.base.model.SaCardData;

public interface SaCardDataMapper extends BaseMapper<SaCardData, String> {
  int baseInsert(SaCardData paramSaCardData);
  
  List<SaCardData> baseLimitSelect(SaCardData paramSaCardData);
}

