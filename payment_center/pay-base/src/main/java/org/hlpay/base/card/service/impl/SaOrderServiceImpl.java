 package org.hlpay.base.card.service.impl;

 import com.github.pagehelper.PageHelper;
 import java.util.List;
 import java.util.Map;
 import org.hlpay.base.card.common.PageBean;
 import org.hlpay.base.card.service.CardBaseService;
 import org.hlpay.base.card.service.SaOrderService;
 import org.hlpay.base.mapper.SaOrderMapper;
 import org.hlpay.base.model.SaOrder;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service
 public class SaOrderServiceImpl
   extends CardBaseService implements SaOrderService {
   @Autowired
   private SaOrderMapper saOrderMapper;

   public int insertSaOrder(SaOrder dealOrder) throws Exception {
     int result = addSaOrder(dealOrder);
     return result;
   }


   public int deleteSaOrder(String dealOrderId) throws Exception {
     int result = super.deleteSaOrder(dealOrderId);
     return result;
   }


   public int updateSaOrder(SaOrder dealOrder) throws Exception {
     return super.updateSaOrder(dealOrder);
   }


   public PageBean selectSaOrder(int start, int end, String userId) throws Exception {
     return super.selectSaOrder(start, end, userId);
   }


   public List<SaOrder> info(String dealOrderId) throws Exception {
     return infoSaOrder(dealOrderId);
   }


   public int update(Map<String, String> map) throws Exception {
     return updateOrder(map);
   }







   public int baseInsert(SaOrder dealOrder) throws Exception {
     return this.saOrderMapper.baseInsert(dealOrder);
   }


   public int baseDelete(String s) throws Exception {
     return this.saOrderMapper.baseDelete(s);
   }


   public int baseUpdate(SaOrder dealOrder) throws Exception {
     return this.saOrderMapper.baseUpdate(dealOrder);
   }


   public List<SaOrder> baseLimitSelect(Integer start, Integer end, SaOrder dealOrder) throws Exception {
     PageHelper.offsetPage(start.intValue(), end.intValue(), false);
     return this.saOrderMapper.baseLimitSelect(dealOrder);
   }


   public SaOrder baseInfo(SaOrder dealOrder) throws Exception {
     return (SaOrder)this.saOrderMapper.baseInfo(dealOrder);
   }


   public int deleteM(SaOrder saOrder) throws Exception {
     return this.saOrderMapper.deleteM(saOrder);
   }
 }





