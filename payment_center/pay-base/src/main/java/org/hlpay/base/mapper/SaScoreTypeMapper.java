package org.hlpay.base.mapper;

import java.util.List;
import org.hlpay.base.model.SaScoreType;

public interface SaScoreTypeMapper {
  int save(SaScoreType paramSaScoreType);
  
  List<SaScoreType> info(SaScoreType paramSaScoreType);
  
  List<SaScoreType> select(SaScoreType paramSaScoreType);
}

