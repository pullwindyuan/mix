package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SaSettleConfig;

public interface SaSettleConfigMapper {
  List<SaSettleConfig> selectSaSettleConfig(@Param("userId") String paramString);
  
  int save(SaSettleConfig paramSaSettleConfig);
}
