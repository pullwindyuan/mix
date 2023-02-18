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
 import org.hlpay.base.model.PayOrderExample;
 import org.hlpay.common.enumm.SortTypeEnum;
 import org.springframework.stereotype.Service;
 import org.springframework.util.CollectionUtils;

 @Service
 public class BaseService4PayOrder
   extends CacheService<PayOrder>
 {
   @Resource
   private PayOrderMapper payOrderMapper;

   public int baseCreateSyncFailedPayOrder(PayOrder payOrder) {
     int r = this.payOrderMapper.insertSyncFailedSelective(payOrder);

     if (r > 0) {
       putToSimpleCacheExpire(payOrder, 6L, TimeUnit.HOURS);
     }


     return r;
   }

   public int baseCreateSettleFailedPayOrder(PayOrder payOrder) {
     int r = this.payOrderMapper.insertSettleFailedSelective(payOrder);





     return r;
   }

   public List<PayOrder> baseSelectSyncFailedPayOrder() {
     PayOrderExample example = new PayOrderExample();
     return this.payOrderMapper.selectSyncFailedByExample(example);
   }

   public PayOrder baseSelectSyncFailedPayOrder(String payOrderId) throws NoSuchMethodException {
     PayOrder payOrder = (PayOrder)getSimpleDataByPrimaryKey(payOrderId);
     if (payOrder != null) {
       return payOrder;
     }
     return this.payOrderMapper.selectSyncFailedByPrimaryKey(payOrderId);
   }





   public PayOrder baseSelectByMchIdAndMchOrderNo(String mchId, String mchOrderNo) throws NoSuchMethodException {
     PayOrder payOrder = new PayOrder();
     payOrder.setMchId(mchId);
     payOrder.setMchOrderNo(mchOrderNo);
     payOrder = (PayOrder)getSimpleDataByUnionKey(payOrder);
     if (payOrder != null) {
       return payOrder;
     }
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdEqualTo(mchId);
     criteria.andMchOrderNoEqualTo(mchOrderNo);
     List<PayOrder> payOrderList = this.payOrderMapper.selectByExample(example);
     if (payOrderList.size() > 0) {
       putToSimpleCache(payOrderList.get(0));
       return payOrderList.get(0);
     }
     return null;
   }

   public int baseUpdateStatus4SuccessAndAward(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)6));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)2));
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdateStatus4SettleComplete(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)8));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);

     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdate4ExtraInfo(String payOrderId, String info) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setExtra(info);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdate(PayOrder payOrder) {
     if (payOrder != null) {
       PayOrderExample example = new PayOrderExample();
       PayOrderExample.Criteria criteria = example.createCriteria();
       criteria.andPayOrderIdEqualTo(payOrder.getPayOrderId());
       int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
       if (r > 0) {
         updateSimpleCache(payOrder);
       }

       return r;
     }
     return 0;
   }

   public int baseUpdateStatus4SettleInfo(String payOrderId, String settlInfo) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setParam2(settlInfo);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);

     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdateStatus4SettleCompleteAndAward(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)9));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);

     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdateStatus4CompleteAndAward(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)7));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)3));
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int baseUpdateStatus4Fail(String payOrderId, String channelErrCode, String channelErrMsg) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)-1));
     if (channelErrCode != null) payOrder.setErrCode(channelErrCode);
     if (channelErrMsg != null) payOrder.setErrMsg(channelErrMsg);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }

     return r;
   }

   public int deleteSyncFailedByPrimaryKey(String payOrderId) {
     int r = this.payOrderMapper.deleteSyncFailedByPrimaryKey(payOrderId);



     return r;
   }

   public int deleteSettleFailedByPrimaryKey(String payOrderId) {
     int r = this.payOrderMapper.deleteSettleFailedByPrimaryKey(payOrderId);



     return r;
   }

   public int baseCreatePayOrder(PayOrder payOrder) {
     int r = this.payOrderMapper.insertSelective(payOrder);








     return r;
   }





   public PayOrder baseSelectPayOrder(String payOrderId) throws NoSuchMethodException {
     PayOrder payOrder = (PayOrder)getSimpleDataByPrimaryKey(payOrderId);
     if (payOrder != null) {
       return payOrder;
     }
     payOrder = this.payOrderMapper.selectByPrimaryKey(payOrderId);
     if (payOrder != null) {
       putToSimpleCache(payOrder);
     }
     return payOrder;
   }

   public List<PayOrder> baseSelectPayOrderByMchOrderNo(String mchOrderNo) {
     return this.payOrderMapper.selectByMchOrderNo(mchOrderNo);
   }

   public PayOrder baseSelectPayOrderByMchIdAndPayOrderId(String mchId, String payOrderId) {
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdEqualTo(mchId);
     criteria.andPayOrderIdEqualTo(payOrderId);
     List<PayOrder> payOrderList = this.payOrderMapper.selectByExample(example);
     return CollectionUtils.isEmpty(payOrderList) ? null : payOrderList.get(0);
   }

   public List<PayOrder> baseSelectPayOrderByMchIdLikeAndPaySuccessTimeRange(String mchId, Long startTime, Long endTime) {
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdLike(mchId + "%");
     criteria.andPaySuccTimeBetween(startTime, endTime);
     return this.payOrderMapper.selectByExample(example);
   }

   public List<PayOrder> baseSelectSettleFailedPayOrder() {
     PayOrderExample example = new PayOrderExample();
     example.setLimit(Integer.valueOf(1000));
     example.setOffset(Integer.valueOf(1));


     return this.payOrderMapper.selectSettleFailedByExample(example);
   }

   public PayOrder baseSelectSettleFailedPayOrder(String payOrderId) {
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     List<PayOrder> payOrderList = this.payOrderMapper.selectSettleFailedByExample(example);
     return CollectionUtils.isEmpty(payOrderList) ? null : payOrderList.get(0);
   }

   public int baseUpdateStatus4Ing(String payOrderId, String channelOrderNo) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)1));
     if (channelOrderNo != null) payOrder.setChannelOrderNo(channelOrderNo);
     payOrder.setPaySuccTime(Long.valueOf(System.currentTimeMillis()));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)0));
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }
     return r;
   }

   public int baseUpdateStatus(String payOrderId, byte status, String channelOrderNo, Date paySuccTime) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf(status));
     payOrder.setPaySuccTime(Long.valueOf(paySuccTime.getTime()));
     if (StringUtils.isNotBlank(channelOrderNo)) payOrder.setChannelOrderNo(channelOrderNo);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int res = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (res > 0) {
       updateSimpleCache(payOrder);
     }
     return res;
   }

   public int baseUpdateStatus4Success(String payOrderId, String channelOrderNo, Date paySuccTime) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)2));
     payOrder.setPaySuccTime(Long.valueOf(paySuccTime.getTime()));
     if (StringUtils.isNotBlank(channelOrderNo)) payOrder.setChannelOrderNo(channelOrderNo);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);

     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }
     return r;
   }

   public int baseUpdateStatus4Complete(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)3));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)2));
     int r = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }
     return r;
   }

   public int baseUpdateStatus4Closed(String payOrderId) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setStatus(Byte.valueOf((byte)-3));
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int res = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (res > 0) {
       updateSimpleCache(payOrder);
     }
     return res;
   }

   public int baseUpdateChannelId(String payOrderId, String channelId) {
     System.out.println("更新channelId::" + channelId);
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setChannelId(channelId);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int res = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (res > 0) {
       updateSimpleCache(payOrder);
     }
     return res;
   }

   public int baseUpdateExtra(String payOrderId, String channelId, String extra) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setChannelId(channelId);
     payOrder.setExtra(extra);
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPayOrderIdEqualTo(payOrderId);
     int res = this.payOrderMapper.updateByExampleSelective(payOrder, example);
     if (res > 0) {
       updateSimpleCache(payOrder);
     }
     return res;
   }

   public List<PayOrder> baseSelectPayOrderByMchIdAndSuccessTime(String mchId, long startSuccessTime, long endSuccessTime) {
     PayOrderExample example = new PayOrderExample();
     PayOrderExample.Criteria criteria = example.createCriteria();
     criteria.andPaySuccTimeBetween(Long.valueOf(startSuccessTime), Long.valueOf(endSuccessTime));
     criteria.andStatusBetween(Byte.valueOf((byte)2), Byte.valueOf((byte)3));
     criteria.andMchIdEqualTo(mchId);
     return this.payOrderMapper.selectByExample(example);
   }


   public int baseUpdateNotify(String payOrderId, byte count) {
     PayOrder payOrder = new PayOrder();
     payOrder.setPayOrderId(payOrderId);
     payOrder.setNotifyCount(Byte.valueOf(count));
     payOrder.setLastNotifyTime(Long.valueOf(System.currentTimeMillis()));
     payOrder.setPayOrderId(payOrderId);
     int r = this.payOrderMapper.updateByPrimaryKeySelective(payOrder);
     if (r > 0) {
       updateSimpleCache(payOrder);
     }
     return r;
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
     return data.getMchId() + ":" + data.getMchOrderNo();
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
