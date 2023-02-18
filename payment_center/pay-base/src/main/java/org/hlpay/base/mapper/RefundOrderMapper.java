package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.RefundOrder;
import org.hlpay.base.model.RefundOrderExample;

public interface RefundOrderMapper {
  int countByExample(RefundOrderExample paramRefundOrderExample);
  
  int deleteByExample(RefundOrderExample paramRefundOrderExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(RefundOrder paramRefundOrder);
  
  int insertSelective(RefundOrder paramRefundOrder);
  
  List<RefundOrder> selectByExample(RefundOrderExample paramRefundOrderExample);
  
  RefundOrder selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") RefundOrder paramRefundOrder, @Param("example") RefundOrderExample paramRefundOrderExample);
  
  int updateByExample(@Param("record") RefundOrder paramRefundOrder, @Param("example") RefundOrderExample paramRefundOrderExample);
  
  int updateByPrimaryKeySelective(RefundOrder paramRefundOrder);
  
  int updateByPrimaryKey(RefundOrder paramRefundOrder);
}
