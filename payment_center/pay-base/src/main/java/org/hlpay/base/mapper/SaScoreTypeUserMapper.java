package org.hlpay.base.mapper;

import java.util.List;
import org.hlpay.base.model.SaScoreTypeUser;

public interface SaScoreTypeUserMapper {
  int save(SaScoreTypeUser paramSaScoreTypeUser);
  
  List<SaScoreTypeUser> info(SaScoreTypeUser paramSaScoreTypeUser);
  
  int update(SaScoreTypeUser paramSaScoreTypeUser);
}

