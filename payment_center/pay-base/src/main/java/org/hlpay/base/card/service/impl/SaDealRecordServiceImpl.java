 package org.hlpay.base.card.service.impl;

 import com.github.pagehelper.PageHelper;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.hlpay.base.card.common.PageBean;
 import org.hlpay.base.card.service.CardBaseService;
 import org.hlpay.base.card.service.SaDealRecordService;
 import org.hlpay.base.mapper.SaDealRecordMapper;
 import org.hlpay.base.model.SaDealRecord;
 import org.springframework.stereotype.Service;

 @Service
 public class SaDealRecordServiceImpl
   extends CardBaseService
   implements SaDealRecordService {
   @Resource
   private SaDealRecordMapper saDealRecordMapper;

   public int insertSaDealRecord(SaDealRecord saDealRecord) throws Exception {
     int result = addSaDealRecord(saDealRecord);
     return result;
   }


   public int insertErrorSaDealRecord(SaDealRecord saDealRecord) throws Exception {
     return addErrorSaDealRecord(saDealRecord);
   }


   public int deleteSaDealRecord(String dealRecordId) throws Exception {
     int result = super.deleteSaDealRecord(dealRecordId);
     return result;
   }


   public int updateSaDealRecord(SaDealRecord saDealRecord) throws Exception {
     int result = super.updateSaDealRecord(saDealRecord);
     return result;
   }


   public PageBean selectSaDealRecord(Integer start, Integer end, SaDealRecord saDealRecord) throws Exception {
     return super.selectSaDealRecord(start, end, saDealRecord);
   }

   public List<SaDealRecord> info(String dealRecordId) throws Exception {
     return infoSaDealRecord(dealRecordId);
   }


   public List<Map<String, Integer>> totalDeal(SaDealRecord saDealRecord) throws Exception {
     return super.totalDeal(saDealRecord);
   }


   public int updateSaDealRecord(Map<String, String> map) throws Exception {
     int result = updateDeal(map);
     return result;
   }







   public int baseInsert(SaDealRecord saDealRecord) throws Exception {
     return this.saDealRecordMapper.baseInsert(saDealRecord);
   }


   public int baseDelete(String s) throws Exception {
     return this.saDealRecordMapper.baseDelete(s);
   }


   public int baseUpdate(SaDealRecord saDealRecord) throws Exception {
     return this.saDealRecordMapper.baseUpdate(saDealRecord);
   }


   public int baseUpdateOrderDetail(SaDealRecord saDealRecord) throws Exception {
     return this.saDealRecordMapper.baseUpdateOrderDetail(saDealRecord);
   }



   public List<SaDealRecord> baseLimitSelect(Integer start, Integer end, SaDealRecord saDealRecord) throws Exception {
     PageHelper.offsetPage(start.intValue(), end.intValue(), false);
     return this.saDealRecordMapper.baseLimitSelect(saDealRecord);
   }


   public SaDealRecord baseInfo(SaDealRecord saDealRecord) throws Exception {
     return (SaDealRecord)this.saDealRecordMapper.baseInfo(saDealRecord);
   }


   public String maxValue(String time) throws Exception {
     return this.saDealRecordMapper.maxValue(time);
   }


   public Integer count() throws Exception {
     return this.saDealRecordMapper.count();
   }


   public int deleteM(SaDealRecord saDealRecord) throws Exception {
     return this.saDealRecordMapper.deleteM(saDealRecord);
   }
 }





