package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.CheckSumPayOrder;
import org.hlpay.base.model.PayOrder;
import org.hlpay.base.model.PayOrderExample;

public interface PayOrderMapper {
  int countSettleFailedByExample(PayOrderExample paramPayOrderExample);
  
  int countSyncFailedByExample(PayOrderExample paramPayOrderExample);
  
  int countByExample(PayOrderExample paramPayOrderExample);
  
  int deleteByExample(PayOrderExample paramPayOrderExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int deleteSyncFailedByPrimaryKey(String paramString);
  
  int deleteSettleFailedByPrimaryKey(String paramString);
  
  int insert(PayOrder paramPayOrder);
  
  int insertSelective(PayOrder paramPayOrder);
  
  int insertSyncFailedSelective(PayOrder paramPayOrder);
  
  int insertSettleFailedSelective(PayOrder paramPayOrder);
  
  List<PayOrder> getSumByPayChannelCodeAndPaySuccessTime(CheckSumPayOrder paramCheckSumPayOrder);
  
  List<PayOrder> getByPayChannelCodeAndPaySuccessTime(CheckSumPayOrder paramCheckSumPayOrder);
  
  List<PayOrder> selectByExample(PayOrderExample paramPayOrderExample);
  
  List<PayOrder> selectSettleFailedByExample(PayOrderExample paramPayOrderExample);
  
  List<PayOrder> selectSyncFailedByExample(PayOrderExample paramPayOrderExample);
  
  List<PayOrder> selectByMchOrderNo(String paramString);
  
  PayOrder selectByPrimaryKey(String paramString);
  
  PayOrder selectSettkeFailedByPrimaryKey(String paramString);
  
  PayOrder selectSyncFailedByPrimaryKey(String paramString);
  
  int updateSettleFailedByExampleSelective(@Param("record") PayOrder paramPayOrder, @Param("example") PayOrderExample paramPayOrderExample);
  
  int updateSyncFailedByExampleSelective(@Param("record") PayOrder paramPayOrder, @Param("example") PayOrderExample paramPayOrderExample);
  
  int updateByExampleSelective(@Param("record") PayOrder paramPayOrder, @Param("example") PayOrderExample paramPayOrderExample);
  
  int updateByExample(@Param("record") PayOrder paramPayOrder, @Param("example") PayOrderExample paramPayOrderExample);
  
  int updateByPrimaryKeySelective(PayOrder paramPayOrder);
  
  int updateByPrimaryKey(PayOrder paramPayOrder);
}

