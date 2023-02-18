package org.hlpay.base.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hlpay.base.mapper.MchNotifyMapper;
import org.hlpay.base.model.MchNotify;
import org.hlpay.base.model.MchNotifyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





@Component
public class MchNotifyService
{
  @Autowired
  private MchNotifyMapper mchNotifyMapper;

  public MchNotify selectMchNotify(String orderId) {
    return this.mchNotifyMapper.selectByPrimaryKey(orderId);
  }

  public List<MchNotify> getMchNotifyList(int offset, int limit, MchNotify mchNotify) {
    MchNotifyExample example = new MchNotifyExample();
    example.setOrderByClause("createTime DESC");
    example.setOffset(Integer.valueOf(offset));
    example.setLimit(Integer.valueOf(limit));
    MchNotifyExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, mchNotify);
    return this.mchNotifyMapper.selectByExample(example);
  }

  public Integer count(MchNotify mchNotify) {
    MchNotifyExample example = new MchNotifyExample();
    MchNotifyExample.Criteria criteria = example.createCriteria();
    setCriteria(criteria, mchNotify);
    return Integer.valueOf(this.mchNotifyMapper.countByExample(example));
  }

  void setCriteria(MchNotifyExample.Criteria criteria, MchNotify mchNotify) {
    if (mchNotify != null) {
      if (StringUtils.isNotBlank(mchNotify.getMchId())) criteria.andMchIdEqualTo(mchNotify.getMchId());
      if (StringUtils.isNotBlank(mchNotify.getOrderId())) criteria.andOrderIdEqualTo(mchNotify.getOrderId());
      if (StringUtils.isNotBlank(mchNotify.getOrderType())) criteria.andOrderTypeEqualTo(mchNotify.getOrderType());
      if (StringUtils.isNotBlank(mchNotify.getMchOrderNo())) criteria.andMchOrderNoEqualTo(mchNotify.getMchOrderNo());
      if (mchNotify.getStatus() != null && mchNotify.getStatus().byteValue() != -99) criteria.andStatusEqualTo(mchNotify.getStatus());
    }
  }
}
