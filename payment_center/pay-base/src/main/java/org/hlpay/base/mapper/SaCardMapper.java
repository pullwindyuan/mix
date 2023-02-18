package org.hlpay.base.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.InnerSettleInfo;
import org.hlpay.base.model.SaCard;

public interface SaCardMapper extends BaseMapper<SaCard, String> {
  int insertSaCard(SaCard paramSaCard);
  
  List<SaCard> infoSaCard(@Param("userId") String paramString);
  
  List<SaCard> getCardsByUserIdAndCardType(@Param("userId") String paramString1, @Param("cardType") String paramString2);
  
  List<SaCard> getSettleCards(@Param("userId") String paramString1, @Param("currency") String paramString2, @Param("cardType") String paramString3, @Param("settleTime") LocalDateTime paramLocalDateTime);
  
  List<SaCard> getCardsByCardType(@Param("cardType") String paramString);
  
  SaCard getCard(@Param("userId") String paramString1, @Param("cardType") String paramString2, @Param("currency") String paramString3, @Param("validStartTime") LocalDateTime paramLocalDateTime1, @Param("validEndTime") LocalDateTime paramLocalDateTime2);
  
  int updateSaCard(@Param("cardNumber") String paramString1, @Param("flag") String paramString2, @Param("remainPart") String paramString3);
  
  int updateAll(SaCard paramSaCard);
  
  List<SaCard> listSaCard();
  
  int update(@Param("maps") Map<String, Long> paramMap);
  
  int updateCard(SaCard paramSaCard);
  
  List<SaCard> descSaCard();
  
  SaCard getSaCardInfoByAccount(SaCard paramSaCard);
  
  int addSaCard(List<SaCard> paramList);
  
  int updateData(SaCard paramSaCard);
  
  int updateFreeze(SaCard paramSaCard);
  
  int baseUpdateList(@Param("list") List<SaCard> paramList);
  
  int baseUpdateCashSettleList(@Param("list") List<SaCard> paramList);
  
  int updateCardList(@Param("list") List<SaCard> paramList);
  
  int doInnerSettle(@Param("innerSettleInfo") InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerVirtualSettleToReal(@Param("innerSettleInfo") InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerVirtualSettle(@Param("innerSettleInfo") InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerScheduleSettle(@Param("innerSettleInfo") InnerSettleInfo paramInnerSettleInfo);
}
