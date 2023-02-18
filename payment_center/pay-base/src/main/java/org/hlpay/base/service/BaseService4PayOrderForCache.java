 package org.hlpay.base.service;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
 import java.util.concurrent.TimeUnit;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.cache.CacheService;
 import org.hlpay.base.mapper.PayOrderMapper;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.enumm.SortTypeEnum;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 @Service
 public class BaseService4PayOrderForCache
   extends CacheService<PayOrder>
 {
   @Resource
   private PayOrderMapper payOrderMapper;
   @Autowired
   private Mq4MchNotify mq4MchNotify;

   public PayOrder baseSelectByMchIdAndMchOrderNo(String mchId, String mchOrderNo) throws NoSuchMethodException {
     PayOrder payOrder = new PayOrder();
     payOrder.setMchId(mchId);
     payOrder.setMchOrderNo(mchOrderNo);
     payOrder = (PayOrder)getSimpleDataByUnionKey(payOrder);
     return payOrder;
   }

   public int baseUpdateStatus4SettleComplete(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)8));
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdate(PayOrder payOrder) {
     if (payOrder != null) {
       try {
         updateSimpleCache(payOrder);
       } catch (Exception e) {
         e.printStackTrace();
         return 0;
       }
       return 1;
     }
     return 0;
   }


   public int baseCreatePayOrder(PayOrder payOrder) {
     try {
       putToSimpleCacheExpire(payOrder, 6L, TimeUnit.HOURS);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }

     return 1;
   }

   public PayOrder baseSelectPayOrder(String payOrderId) throws NoSuchMethodException {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     return (PayOrder)getSimpleDataByPrimaryKey(payOrder);
   }

   public List<PayOrder> baseSelectPayOrderByMchOrderNo(String mchOrderNo) {
     return this.payOrderMapper.selectByMchOrderNo(mchOrderNo);
   }

   public PayOrder baseSelectPayOrderByMchOrderNoFromCache(String mchOrderNo) throws NoSuchMethodException {
     PayOrder payOrder = new PayOrder();
     payOrder.setMchOrderNo(mchOrderNo);
     return (PayOrder)getSimpleDataByUnionKey(payOrder);
   }

   public PayOrder baseSelectPayOrderByMchIdAndPayOrderId(String mchId, String payOrderId) throws NoSuchMethodException {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setMchId(mchId);
     return (PayOrder)getSimpleDataByPrimaryKey(payOrder);
   }

   public int baseUpdateStatus4Ing(String payOrderId, String channelOrderNo) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)1));
     if (channelOrderNo != null) payOrder.setChannelOrderNo(channelOrderNo);
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdateStatus4Success(String payOrderId, String channelOrderNo, Date paySuccTime) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)2));
     payOrder.setPaySuccTime(Long.valueOf(paySuccTime.getTime()));
     if (StringUtils.isNotBlank(channelOrderNo)) payOrder.setChannelOrderNo(channelOrderNo);
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdateStatus4Complete(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)3));
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdateStatus4Closed(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)-3));
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdateChannelId(String payOrderId, String channelId) {
     System.out.println("更新channelId::" + channelId);
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setChannelId(channelId);
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }

   public int baseUpdateExtra(String payOrderId, String channelId, String extra) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setChannelId(channelId);
     payOrder.setExtra(extra);
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }


   public int baseUpdateNotify(String payOrderId, byte count) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setNotifyCount(Byte.valueOf(count));
     payOrder.setLastNotifyTime(Long.valueOf(System.currentTimeMillis()));
     payOrder.setPayOrderId(payOrderId);
     try {
       updateSimpleCache(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       return 0;
     }
     return 1;
   }


   public String getNameSpace() {
     return "PAY-ORDER";
   }


   public String getSimplePrimaryKey(PayOrder data) {
     return data.getPayOrderId();
   }


   public List<String> getPrimaryKeyList(PayOrder data) {
     List<String> list = new ArrayList<>();
     list.add(data.getPayOrderId());
     return list;
   }


   public String getSimpleUnionKey(PayOrder data) {
     return data.getMchOrderNo();
   }


   public List<TreeMap<String, String>> getUnionKeyList(PayOrder data) {
     List<String> list = new ArrayList<>();
     list.add(data.getPayOrderId());
     list.add(data.getStatus().toString());
     list.add(data.getMchId());
     list.add(data.getMchOrderNo());
     list.add(data.getChannelMchId());
     list.add(data.getChannelCode().toString());
     return null;
   }

   public Map<String, Double> getScoreMap(PayOrder data) {
     Double score;
     Map<String, Double> map = new HashMap<>();

     if (data.getPaySuccTime() != null) {
       score = Double.valueOf(data.getPaySuccTime().longValue());
     } else {
       score = Double.valueOf(data.getUpdateTime().getTime());
     }
     map.put("paySuccTime", score);
     return map;
   }


   public Map<String, String> getGroupMap(PayOrder data) {
     Map<String, String> map = new HashMap<>();


     map.put("channelCode", data.getChannelCode().toString());

     map.put("channelId", data.getChannelId());

     map.put("mchId", data.getMchId());

     map.put("channelMchId", data.getChannelMchId());

     if (data.getStatus().byteValue() == 2 || data
       .getStatus().byteValue() == 3 || data
       .getStatus().byteValue() == 4 || data
       .getStatus().byteValue() == 5) {
       map.put("status", data.getStatus().toString());
     }
     return null;
   }



   public Map<String, SortTypeEnum> getDefaultSortMap() {
     return null;
   }


   public Class<PayOrder> getType() {
     return PayOrder.class;
   }


   public String getQueryExpFromExample(PayOrder data) {
     return null;
   }
 }
