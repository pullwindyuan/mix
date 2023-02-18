package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyBatisBaseDao<Model, PK extends java.io.Serializable, E> {
  long countByExample(E paramE);
  
  int deleteByExample(E paramE);
  
  int deleteByPrimaryKey(PK paramPK);
  
  int insert(Model paramModel);
  
  int insertSelective(Model paramModel);
  
  List<Model> selectByExampleWithSettleStatus(E paramE);
  
  List<Model> selectByExample(E paramE);
  
  List<Model> selectSettleWithdraw();
  
  Model selectByPrimaryKey(PK paramPK);
  
  Model selectSettleByPrimaryKey(PK paramPK);
  
  int updateByExampleSelective(@Param("record") Model paramModel, @Param("example") E paramE);
  
  int updateByExample(@Param("record") Model paramModel, @Param("example") E paramE);
  
  int updateByPrimaryKeySelective(Model paramModel);
  
  int updateByPrimaryKey(Model paramModel);
}

