 package org.hlpay.base.service;

 import java.util.List;
 import javax.annotation.Resource;
 import org.hlpay.base.mapper.SaScoreFlowRealMapper;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SaScoreFlow;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class CountExchanageService
 {
   @Resource
   private SaScoreFlowRealMapper saScoreFlowRealMapper;
   @Autowired
   private CardService cardService;

   public List<SaScoreFlow> listExchange(SaScoreFlow saScoreFlow) {
     return this.saScoreFlowRealMapper.listExchange(saScoreFlow);
   }

   @Transactional(rollbackFor = {Exception.class})
   public int baseUpdate(SaScoreFlow saScoreFlow, Long cardLimit) throws Exception {
     this.saScoreFlowRealMapper.baseUpdate(saScoreFlow);
     SaCard saCard = new SaCard();
     saCard.setUserId(saScoreFlow.getUserId());
     saCard.setCurrency("bd");
     saCard.setFlag(Integer.valueOf(1));
     saCard.setCardLimit(cardLimit);
     this.cardService.updateData(saCard);
     return 1;
   }
 }
