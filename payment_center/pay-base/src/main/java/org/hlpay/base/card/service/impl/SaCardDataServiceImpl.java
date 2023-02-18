 package org.hlpay.base.card.service.impl;

 import com.github.pagehelper.PageHelper;
 import java.util.List;
 import org.hlpay.base.card.service.SaCardDataService;
 import org.hlpay.base.mapper.SaCardDataMapper;
 import org.hlpay.base.model.SaCardData;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;



 @Service
 public class SaCardDataServiceImpl
   implements SaCardDataService
 {
   @Autowired
   private SaCardDataMapper saCardDataMapper;

   public int baseInsert(SaCardData saCardData) throws Exception {
     return this.saCardDataMapper.baseInsert(saCardData);
   }



   public int baseDelete(String s) throws Exception {
     return this.saCardDataMapper.baseDelete(s);
   }


   public int baseUpdate(SaCardData saCardData) throws Exception {
     return this.saCardDataMapper.baseUpdate(saCardData);
   }



   public List<SaCardData> baseLimitSelect(Integer start, Integer end, SaCardData saCardData) throws Exception {
     PageHelper.offsetPage(start.intValue(), end.intValue(), false);
     return this.saCardDataMapper.baseLimitSelect(saCardData);
   }


   public SaCardData baseInfo(SaCardData saCardData) throws Exception {
     return (SaCardData)this.saCardDataMapper.baseInfo(saCardData);
   }
 }





