 package org.hlpay.base.service;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
 import java.util.concurrent.TimeUnit;
 import org.hlpay.base.cache.CacheService;
 import org.hlpay.base.model.MchNotify;
 import org.hlpay.common.enumm.SortTypeEnum;
 import org.springframework.stereotype.Service;


 @Service
 public class BaseNotifyService
   extends CacheService<MchNotify>
 {
   public MchNotify baseSelectMchNotify(String orderId) throws NoSuchMethodException {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     return (MchNotify)getSimpleDataByPrimaryKey(mchNotify);
   }

   public int baseInsertMchNotify(String orderId, String mchId, String mchOrderNo, String orderType, String notifyUrl) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     mchNotify.setMchId(mchId);
     mchNotify.setMchOrderNo(mchOrderNo);
     mchNotify.setOrderType(orderType);
     mchNotify.setNotifyCount(Byte.valueOf((byte)0));
     mchNotify.setStatus(Byte.valueOf((byte)1));
     mchNotify.setNotifyUrl(notifyUrl);

     try {
       putToSimpleCacheExpire(mchNotify, 12L, TimeUnit.HOURS);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseDeleteMchNotify(String orderId) {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     try {
       deleteSimpleCache(mchNotify);
     } catch (Exception exception) {}


     return 1;
   }

   public int baseUpdateMchNotifySuccess(String orderId, String result, byte notifyCount) throws NoSuchMethodException {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     mchNotify = (MchNotify)getSimpleDataByPrimaryKey(mchNotify);
     if (mchNotify.getStatus().equals(Byte.valueOf((byte)1)) || mchNotify
       .getStatus().equals(Byte.valueOf((byte)3))) {
       mchNotify.setStatus(Byte.valueOf((byte)2));
       mchNotify.setResult(result);
       mchNotify.setNotifyCount(Byte.valueOf(notifyCount));

       if (mchNotify != null) {
         try {
           updateSimpleCache(mchNotify);
         } catch (Exception e) {
           e.printStackTrace();
           return 0;
         }
         return 1;
       }
     }
     return 0;
   }


   public int baseUpdateMchNotifyFail(String orderId, String result, byte notifyCount) throws NoSuchMethodException {
     MchNotify mchNotify = new MchNotify();
     mchNotify.setOrderId(orderId);
     mchNotify = (MchNotify)getSimpleDataByPrimaryKey(mchNotify);
     if (mchNotify.getStatus().equals(Byte.valueOf((byte)1)) || mchNotify
       .getStatus().equals(Byte.valueOf((byte)3))) {
       mchNotify.setStatus(Byte.valueOf((byte)3));
       mchNotify.setResult(result);
       mchNotify.setNotifyCount(Byte.valueOf(notifyCount));
       mchNotify.setLastNotifyTime(new Date());

       if (mchNotify != null) {
         try {
           updateSimpleCache(mchNotify);
         } catch (Exception e) {
           e.printStackTrace();
           return 0;
         }
         return 1;
       }
     }
     return 0;
   }


   public String getNameSpace() {
     return "MCH-NOTIFY";
   }


   public String getSimplePrimaryKey(MchNotify data) {
     return data.getOrderId();
   }


   public List<String> getPrimaryKeyList(MchNotify data) {
     List<String> list = new ArrayList<>();
     list.add(data.getOrderId());
     return list;
   }


   public String getSimpleUnionKey(MchNotify data) {
     return data.getMchId() + ":" + data.getMchOrderNo();
   }


   public List<TreeMap<String, String>> getUnionKeyList(MchNotify data) {
     List<String> list = new ArrayList<>();
     list.add(data.getOrderId());
     list.add(data.getStatus().toString());
     list.add(data.getMchId());
     list.add(data.getMchOrderNo());
     list.add(data.getOrderType());
     return null;
   }

   public Map<String, Double> getScoreMap(MchNotify data) {
     Double score;
     Map<String, Double> map = new HashMap<>();

     if (data.getLastNotifyTime() != null) {
       score = Double.valueOf(data.getLastNotifyTime().getTime());
     } else {
       score = Double.valueOf(data.getUpdateTime().getTime());
     }
     map.put("lastNotifyTime", score);
     return map;
   }


   public Map<String, String> getGroupMap(MchNotify data) {
     Map<String, String> map = new HashMap<>();

     map.put("mchId", data.getMchId());

     map.put("status", data.getStatus().toString());
     return null;
   }



   public Map<String, SortTypeEnum> getDefaultSortMap() {
     return null;
   }


   public Class<MchNotify> getType() {
     return MchNotify.class;
   }


   public String getQueryExpFromExample(MchNotify data) {
     return null;
   }
 }

