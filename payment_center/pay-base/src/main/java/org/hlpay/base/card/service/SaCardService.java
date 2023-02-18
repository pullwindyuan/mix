package org.hlpay.base.card.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.hlpay.base.card.common.PageBean;
import org.hlpay.base.model.FreezeInfo;
import org.hlpay.base.model.InnerSettleInfo;
import org.hlpay.base.model.SaCard;

public interface SaCardService {
  int insertSaCard(SaCard paramSaCard) throws Exception;
  
  List<SaCard> infoSaCard(String paramString) throws Exception;
  
  int updateSaCard(String paramString1, String paramString2, String paramString3) throws Exception;
  
  int updateAll(SaCard paramSaCard) throws Exception;
  
  PageBean listSaCard(int paramInt1, int paramInt2) throws Exception;
  
  List<SaCard> getCardsByUserIdAndCardType(String paramString1, String paramString2);
  
  List<SaCard> getSettleCards(String paramString1, String paramString2, Long paramLong);
  
  int update(Map<String, Long> paramMap) throws Exception;
  
  void updateCard(SaCard paramSaCard) throws Exception;
  
  int baseInsert(SaCard paramSaCard) throws Exception;
  
  int baseDelete(String paramString) throws Exception;
  
  int baseUpdate(SaCard paramSaCard) throws Exception;
  
  int baseUpdateList(List<SaCard> paramList) throws Exception;
  
  int doInnerSettle(InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerVirtualSettle(InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerVirtualSettleToReal(InnerSettleInfo paramInnerSettleInfo);
  
  int doInnerScheduleSettle(InnerSettleInfo paramInnerSettleInfo);
  
  int updateCardList(List<SaCard> paramList) throws Exception;
  
  List<SaCard> baseLimitSelect(Integer paramInteger1, Integer paramInteger2, SaCard paramSaCard) throws Exception;
  
  SaCard baseInfo(SaCard paramSaCard) throws Exception;
  
  List<SaCard> descSaCard() throws Exception;
  
  List<SaCard> get(String paramString) throws Exception;
  
  void set(String paramString, List<SaCard> paramList) throws Exception;
  
  SaCard getSaCardInfoByAccount(SaCard paramSaCard);
  
  int addSaCard(List<SaCard> paramList);
  
  int updateData(SaCard paramSaCard) throws Exception;
  
  String updateFreeze(SaCard paramSaCard, JSONObject paramJSONObject, String paramString) throws Exception;
  
  String updateFreeze(FreezeInfo paramFreezeInfo) throws Exception;
  
  boolean freeze(String paramString1, long paramLong, String paramString2, String paramString3, String paramString4, String paramString5);
  
  boolean freeze(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, String paramString5, String paramString6);
  
  boolean unfreeze(String paramString1, long paramLong, String paramString2, String paramString3, String paramString4, String paramString5);
}
