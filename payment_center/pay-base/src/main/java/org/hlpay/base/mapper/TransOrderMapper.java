package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.TransOrder;
import org.hlpay.base.model.TransOrderExample;

public interface TransOrderMapper {
  int countByExample(TransOrderExample paramTransOrderExample);
  
  int deleteByExample(TransOrderExample paramTransOrderExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(TransOrder paramTransOrder);
  
  int insertSelective(TransOrder paramTransOrder);
  
  List<TransOrder> selectByExample(TransOrderExample paramTransOrderExample);
  
  TransOrder selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") TransOrder paramTransOrder, @Param("example") TransOrderExample paramTransOrderExample);
  
  int updateByExample(@Param("record") TransOrder paramTransOrder, @Param("example") TransOrderExample paramTransOrderExample);
  
  int updateByPrimaryKeySelective(TransOrder paramTransOrder);
  
  int updateByPrimaryKey(TransOrder paramTransOrder);
}

