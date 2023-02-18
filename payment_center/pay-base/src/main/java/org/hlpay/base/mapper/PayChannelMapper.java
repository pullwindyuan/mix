package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.PayChannel;
import org.hlpay.base.model.PayChannelExample;

public interface PayChannelMapper {
  int countByExample(PayChannelExample paramPayChannelExample);
  
  int deleteByExample(PayChannelExample paramPayChannelExample);
  
  int deleteByPrimaryKey(Integer paramInteger);
  
  int insert(PayChannel paramPayChannel);
  
  int insertSelective(PayChannel paramPayChannel);
  
  List<PayChannel> selectByExample(PayChannelExample paramPayChannelExample);
  
  PayChannel selectByPrimaryKey(Integer paramInteger);
  
  PayChannel selectByPrimaryKeyForAudit(Integer paramInteger);
  
  int updateByExampleSelective(@Param("record") PayChannel paramPayChannel, @Param("example") PayChannelExample paramPayChannelExample);
  
  int updateByExample(@Param("record") PayChannel paramPayChannel, @Param("example") PayChannelExample paramPayChannelExample);
  
  int updateByPrimaryKeySelective(PayChannel paramPayChannel);
  
  int updateByPrimaryKeySelectiveForAudit(PayChannel paramPayChannel);
  
  int updateByPrimaryKey(PayChannel paramPayChannel);
  
  int listInsert(@Param("list") List<PayChannel> paramList);
  
  int listInsertForAudit(@Param("list") List<PayChannel> paramList);
  
  int listInsertForTest(@Param("list") List<PayChannel> paramList);
}

