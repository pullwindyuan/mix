package org.hlpay.base.mapper;

import java.util.List;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.SaCardExample;
import org.hlpay.base.model.SaSettleCard;
import org.hlpay.base.model.SaSettleCardSum;
import org.springframework.stereotype.Repository;

@Repository
public interface SaCardDao extends MyBatisBaseDao<SaCard, String, SaCardExample> {
  int insertToSettleCard(SaCard paramSaCard);
  
  List<SaSettleCard> selectByExampleFromSettleCard(SaCardExample paramSaCardExample);
  
  int updateByPrimaryKeyFromSettleCardBySelective(SaCard paramSaCard);
  
  int updateByPrimaryKeyFromSettleCard(SaCard paramSaCard);
  
  List<SaSettleCard> getMonthSettleSum(SaSettleCardSum paramSaSettleCardSum);
  
  List<SaSettleCard> getMonthSettleByUserSum(SaSettleCardSum paramSaSettleCardSum);
  
  List<SaSettleCard> getPlatformMonthSettleSum(SaSettleCardSum paramSaSettleCardSum);
  
  List<SaSettleCard> getPlatformSettleSum(SaSettleCardSum paramSaSettleCardSum);
  
  List<SaSettleCard> getMonthSettleSumByMchInfo(SaSettleCardSum paramSaSettleCardSum);
  
  long countByExampleFromSettleCard(SaCardExample paramSaCardExample);
}

