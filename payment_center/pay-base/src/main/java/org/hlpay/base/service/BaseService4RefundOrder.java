package org.hlpay.base.service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hlpay.base.mapper.RefundOrderMapper;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.base.model.RefundOrderExample;
import org.hlpay.common.util.RedisProUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BaseService4RefundOrder
{
  @Resource
  private RefundOrderMapper refundOrderMapper;
  @Autowired
  RedisProUtil redisProUtil;

  public int baseCreateRefundOrder(RefundOrder refundOrder) {
    return this.refundOrderMapper.insertSelective(refundOrder);
  }

  public RefundOrder baseSelectRefundOrder(String refundOrderId) {
    return this.refundOrderMapper.selectByPrimaryKey(refundOrderId);
  }

  public RefundOrder baseSelectByMchIdAndRefundOrderId(String mchId, String refundOrderId) {
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andMchIdEqualTo(mchId);
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    List<RefundOrder> refundOrderList = this.refundOrderMapper.selectByExample(example);
    return CollectionUtils.isEmpty(refundOrderList) ? null : refundOrderList.get(0);
  }

  public RefundOrder baseSelectByMchIdAndMchRefundNo(String mchId, String mchRefundNo) {
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andMchIdEqualTo(mchId);
    criteria.andMchRefundNoEqualTo(mchRefundNo);
    List<RefundOrder> refundOrderList = this.refundOrderMapper.selectByExample(example);
    return CollectionUtils.isEmpty(refundOrderList) ? null : refundOrderList.get(0);
  }

  public int baseUpdate4ExtraInfo(String refundOrderId, String info) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setExtra(info);
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    return this.refundOrderMapper.updateByExampleSelective(refundOrder, example);
  }

  public int baseUpdateStatus4Ing(String refundOrderId, String channelOrderNo) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setStatus(Byte.valueOf((byte)1));
    if (channelOrderNo != null) refundOrder.setChannelOrderNo(channelOrderNo);
    refundOrder.setRefundSuccTime(new Date());
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    criteria.andStatusEqualTo(Byte.valueOf((byte)0));
    return this.refundOrderMapper.updateByExampleSelective(refundOrder, example);
  }

  public int baseUpdateStatus4Success(String refundOrderId, Date refundSuccTime) {
    return baseUpdateStatus4Success(refundOrderId, null, refundSuccTime);
  }

  public int baseUpdateStatus4Success(String refundOrderId, String channelOrderNo, Date refundSuccTime) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setRefundOrderId(refundOrderId);
    refundOrder.setStatus(Byte.valueOf((byte)2));
    refundOrder.setResult(Byte.valueOf((byte)2));
    refundOrder.setRefundSuccTime(refundSuccTime);
    if (StringUtils.isNotBlank(channelOrderNo)) refundOrder.setChannelOrderNo(channelOrderNo);
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    criteria.andStatusEqualTo(Byte.valueOf((byte)1));
    int r = this.refundOrderMapper.updateByExampleSelective(refundOrder, example);




    return r;
  }

  public int baseUpdateStatus4Complete(String refundOrderId) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setRefundOrderId(refundOrderId);
    refundOrder.setStatus(Byte.valueOf((byte)4));
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    List values = CollectionUtils.arrayToList(new Byte[] {
          Byte.valueOf((byte)2), Byte.valueOf((byte)3)
        });
    criteria.andStatusIn(values);
    return this.refundOrderMapper.updateByExampleSelective(refundOrder, example);
  }

  public int baseUpdateStatus4Fail(String refundOrderId, String channelErrCode, String channelErrMsg) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setStatus(Byte.valueOf((byte)3));
    refundOrder.setResult(Byte.valueOf((byte)3));
    if (channelErrCode != null) refundOrder.setChannelErrCode(channelErrCode);
    if (channelErrMsg != null) refundOrder.setChannelErrMsg(channelErrMsg);
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    criteria.andStatusEqualTo(Byte.valueOf((byte)1));
    return this.refundOrderMapper.updateByExampleSelective(refundOrder, example);
  }

  public int baseUpdateStatus4Cancel(String refundOrderId) {
    RefundOrder refundOrder = new RefundOrder();
    refundOrder.setStatus(Byte.valueOf((byte)5));
    refundOrder.setResult(Byte.valueOf((byte)2));
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    criteria.andRefundOrderIdEqualTo(refundOrderId);
    criteria.andStatusEqualTo(Byte.valueOf((byte)1));
    return this.refundOrderMapper.updateByExampleSelective(refundOrder, example);
  }

  public RefundOrder selectRefundOrder(String refundOrderId) {
    return this.refundOrderMapper.selectByPrimaryKey(refundOrderId);
  }

  public List<RefundOrder> getRefundOrderList(int offset, int limit, RefundOrder refundOrder) {
    RefundOrderExample example = new RefundOrderExample();
    example.setOrderByClause("createTime DESC");
    example.setOffset(Integer.valueOf(offset));
    example.setLimit(Integer.valueOf(limit));
    RefundOrderExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, refundOrder);
    return this.refundOrderMapper.selectByExample(example);
  }

  public Integer count(RefundOrder refundOrder) {
    RefundOrderExample example = new RefundOrderExample();
    RefundOrderExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, refundOrder);
    return Integer.valueOf(this.refundOrderMapper.countByExample(example));
  }

  void setCriteria(RefundOrderExample.Criteria criteria, RefundOrder refundOrder) {
    if (refundOrder != null) {
      if (StringUtils.isNotBlank(refundOrder.getMchId())) criteria.andMchIdEqualTo(refundOrder.getMchId());
      if (StringUtils.isNotBlank(refundOrder.getRefundOrderId())) criteria.andRefundOrderIdEqualTo(refundOrder.getRefundOrderId());
      if (StringUtils.isNotBlank(refundOrder.getRefundOrderId())) criteria.andMchRefundNoEqualTo(refundOrder.getMchRefundNo());
      if (StringUtils.isNotBlank(refundOrder.getChannelOrderNo())) criteria.andChannelOrderNoEqualTo(refundOrder.getChannelOrderNo());
      if (refundOrder.getStatus() != null && refundOrder.getStatus().byteValue() != -99) criteria.andStatusEqualTo(refundOrder.getStatus());
    }
  }
}
