 package org.hlpay.base.service;

 import java.util.Arrays;
 import java.util.Date;
 import java.util.LinkedList;
 import java.util.List;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.mapper.TransOrderMapper;
 import org.hlpay.base.model.TransOrder;
 import org.hlpay.base.model.TransOrderExample;
 import org.springframework.stereotype.Service;
 import org.springframework.util.CollectionUtils;

 @Service
 public class BaseService4TransOrder
 {
   @Resource
   private TransOrderMapper transOrderMapper;

   public int baseCreateTransOrder(TransOrder transOrder) {
     return this.transOrderMapper.insertSelective(transOrder);
   }

   public TransOrder baseSelectTransOrder(String transOrderId) {
     return this.transOrderMapper.selectByPrimaryKey(transOrderId);
   }

   public TransOrder baseSelectByTransOrderId(String transOrderId) {
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     List<TransOrder> transOrderList = this.transOrderMapper.selectByExample(example);
     return CollectionUtils.isEmpty(transOrderList) ? null : transOrderList.get(0);
   }

   public TransOrder baseSelectByMchTransNo(String mchTransNo) {
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchTransNoEqualTo(mchTransNo);
     List<TransOrder> transOrderList = this.transOrderMapper.selectByExample(example);
     return CollectionUtils.isEmpty(transOrderList) ? null : transOrderList.get(0);
   }

   public TransOrder baseSelectByMchIdAndTransOrderId(String mchId, String transOrderId) {
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdEqualTo(mchId);
     criteria.andTransOrderIdEqualTo(transOrderId);
     List<TransOrder> transOrderList = this.transOrderMapper.selectByExample(example);
     return CollectionUtils.isEmpty(transOrderList) ? null : transOrderList.get(0);
   }

   public TransOrder baseSelectByMchIdAndMchTransNo(String mchId, String mchTransNo) {
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdEqualTo(mchId);
     criteria.andMchTransNoEqualTo(mchTransNo);
     List<TransOrder> transOrderList = this.transOrderMapper.selectByExample(example);
     return CollectionUtils.isEmpty(transOrderList) ? null : transOrderList.get(0);
   }

   public int baseUpdate4ExtraInfo(String transOrderId, String info) {
     TransOrder TransOrder = new TransOrder();
     TransOrder.setExtra(info);
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     return this.transOrderMapper.updateByExampleSelective(TransOrder, example);
   }

   public int baseUpdateStatus4Ing(String transOrderId, String channelOrderNo) {
     TransOrder transOrder = new TransOrder();
     transOrder.setStatus(Byte.valueOf((byte)1));
     if (channelOrderNo != null) transOrder.setChannelOrderNo(channelOrderNo);
     transOrder.setTransSuccTime(new Date());
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     List<Byte> list = new LinkedList<>();
     list.add(Byte.valueOf((byte)0));
     list.add(Byte.valueOf((byte)3));
     criteria.andStatusIn(list);
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public int baseUpdateStatus4Ing(String transOrderId) {
     TransOrder transOrder = new TransOrder();
     transOrder.setStatus(Byte.valueOf((byte)1));
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     List<Byte> list = new LinkedList<>();
     list.add(Byte.valueOf((byte)0));
     list.add(Byte.valueOf((byte)3));
     criteria.andStatusIn(list);
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public int baseSetStatus4Init(String transOrderId) {
     TransOrder transOrder = new TransOrder();
     transOrder.setStatus(Byte.valueOf((byte)0));
     transOrder.setTransSuccTime(new Date());
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     List<Byte> list = new LinkedList<>();
     list.add(Byte.valueOf((byte)0));
     list.add(Byte.valueOf((byte)1));
     list.add(Byte.valueOf((byte)3));
     criteria.andStatusIn(list);
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public int baseUpdate(TransOrder transOrder) {
     if (transOrder != null) {
       TransOrderExample example = new TransOrderExample();
       TransOrderExample.Criteria criteria = example.createCriteria();
       criteria.andTransOrderIdEqualTo(transOrder.getTransOrderId());
       return this.transOrderMapper.updateByExampleSelective(transOrder, example);
     }
     return 0;
   }
   public int baseUpdateStatus4Success(String transOrderId) {
     return baseUpdateStatus4Success(transOrderId, null);
   }

   public int baseUpdateStatus4Success(String transOrderId, String channelOrderNo) {
     TransOrder transOrder = new TransOrder();
     transOrder.setTransOrderId(transOrderId);
     transOrder.setStatus(Byte.valueOf((byte)2));
     transOrder.setResult(Byte.valueOf((byte)2));
     transOrder.setTransSuccTime(new Date());
     if (StringUtils.isNotBlank(channelOrderNo)) transOrder.setChannelOrderNo(channelOrderNo);
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)1));
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public int baseUpdateStatus4Complete(String transOrderId) {
     TransOrder transOrder = new TransOrder();
     transOrder.setTransOrderId(transOrderId);
     transOrder.setStatus(Byte.valueOf((byte)4));
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     List values = CollectionUtils.arrayToList(new Byte[] {
           Byte.valueOf((byte)2), Byte.valueOf((byte)3)
         });
     criteria.andStatusIn(values);
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public int baseUpdateStatus4Fail(String transOrderId, String channelErrCode, String channelErrMsg) {
     TransOrder transOrder = new TransOrder();
     transOrder.setStatus(Byte.valueOf((byte)3));
     transOrder.setResult(Byte.valueOf((byte)3));
     if (channelErrCode != null) transOrder.setChannelErrCode(channelErrCode);
     if (channelErrMsg != null) transOrder.setChannelErrMsg(channelErrMsg);
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     criteria.andTransOrderIdEqualTo(transOrderId);
     criteria.andStatusEqualTo(Byte.valueOf((byte)1));
     return this.transOrderMapper.updateByExampleSelective(transOrder, example);
   }

   public TransOrder selectTransOrder(String transOrderId) {
     return this.transOrderMapper.selectByPrimaryKey(transOrderId);
   }

   public List<TransOrder> getTransOrderList(int offset, int limit, TransOrder transOrder) {
     TransOrderExample example = new TransOrderExample();
     example.setOrderByClause("createTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     TransOrderExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, transOrder);
     return this.transOrderMapper.selectByExample(example);
   }

   public List<TransOrder> getTransOrderList(int offset, int limit, String ids) {
     if (StringUtils.isBlank(ids)) {
       return null;
     }
     TransOrderExample example = new TransOrderExample();
     example.setOrderByClause("createTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     TransOrderExample.Criteria criteria = example.createCriteria();
     String[] temp = ids.split("-");
     criteria.andTransOrderIdIn(Arrays.asList(temp));
     return this.transOrderMapper.selectByExample(example);
   }

   public List<TransOrder> getTransOrderListByChannels(int offset, int limit, String channels) {
     if (StringUtils.isBlank(channels)) {
       return null;
     }
     TransOrderExample example = new TransOrderExample();
     example.setOrderByClause("createTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     TransOrderExample.Criteria criteria = example.createCriteria();
     String[] temp = channels.split("-");
     criteria.andChannelIdIn(Arrays.asList(temp));
     return this.transOrderMapper.selectByExample(example);
   }

   public Integer count(TransOrder transOrder) {
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, transOrder);
     return Integer.valueOf(this.transOrderMapper.countByExample(example));
   }

   public Integer count(String ids) {
     if (StringUtils.isBlank(ids)) {
       return Integer.valueOf(0);
     }
     TransOrderExample example = new TransOrderExample();
     TransOrderExample.Criteria criteria = example.createCriteria();
     String[] temp = ids.split("-");
     criteria.andTransOrderIdIn(Arrays.asList(temp));
     return Integer.valueOf(this.transOrderMapper.countByExample(example));
   }

   void setCriteria(TransOrderExample.Criteria criteria, TransOrder transOrder) {
     if (transOrder != null) {
       if (StringUtils.isNotBlank(transOrder.getMchId())) criteria.andMchIdEqualTo(transOrder.getMchId());
       if (StringUtils.isNotBlank(transOrder.getTransOrderId())) criteria.andTransOrderIdEqualTo(transOrder.getTransOrderId());
       if (StringUtils.isNotBlank(transOrder.getMchTransNo())) criteria.andMchTransNoEqualTo(transOrder.getMchTransNo());
       if (StringUtils.isNotBlank(transOrder.getChannelOrderNo())) criteria.andChannelOrderNoEqualTo(transOrder.getChannelOrderNo());
       if (transOrder.getStatus() != null && transOrder.getStatus().byteValue() != -99) criteria.andStatusEqualTo(transOrder.getStatus());
       if (StringUtils.isNotBlank(transOrder.getChannelId())) criteria.andChannelIdEqualTo(transOrder.getChannelId());
       if (StringUtils.isNotBlank(transOrder.getExtra())) criteria.andExtraEqualTo(transOrder.getExtra());
       if (StringUtils.isNotBlank(transOrder.getCurrency())) criteria.andCurrencyEqualTo(transOrder.getExtra());
       if (StringUtils.isNotBlank(transOrder.getChannelUser())) criteria.andChannelUserEqualTo(transOrder.getChannelUser());
     }
   }
 }

