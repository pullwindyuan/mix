package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.GoodsOrder;
import org.hlpay.base.model.GoodsOrderExample;

public interface GoodsOrderMapper {
  int countByExample(GoodsOrderExample paramGoodsOrderExample);
  
  int deleteByExample(GoodsOrderExample paramGoodsOrderExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(GoodsOrder paramGoodsOrder);
  
  int insertSelective(GoodsOrder paramGoodsOrder);
  
  List<GoodsOrder> selectByExample(GoodsOrderExample paramGoodsOrderExample);
  
  GoodsOrder selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") GoodsOrder paramGoodsOrder, @Param("example") GoodsOrderExample paramGoodsOrderExample);
  
  int updateByExample(@Param("record") GoodsOrder paramGoodsOrder, @Param("example") GoodsOrderExample paramGoodsOrderExample);
  
  int updateByPrimaryKeySelective(GoodsOrder paramGoodsOrder);
  
  int updateByPrimaryKey(GoodsOrder paramGoodsOrder);
}

