package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.MchNotify;
import org.hlpay.base.model.MchNotifyExample;

public interface MchNotifyMapper {
  int countByExample(MchNotifyExample paramMchNotifyExample);
  
  int deleteByExample(MchNotifyExample paramMchNotifyExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(MchNotify paramMchNotify);
  
  int insertSelective(MchNotify paramMchNotify);
  
  List<MchNotify> selectByExample(MchNotifyExample paramMchNotifyExample);
  
  MchNotify selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") MchNotify paramMchNotify, @Param("example") MchNotifyExample paramMchNotifyExample);
  
  int updateSyncByExampleSelective(@Param("record") MchNotify paramMchNotify, @Param("example") MchNotifyExample paramMchNotifyExample);
  
  int updateByExample(@Param("record") MchNotify paramMchNotify, @Param("example") MchNotifyExample paramMchNotifyExample);
  
  int updateByPrimaryKeySelective(MchNotify paramMchNotify);
  
  int updateByPrimaryKey(MchNotify paramMchNotify);
  
  int insertSelectiveOnDuplicateKeyUpdate(MchNotify paramMchNotify);
  
  int insertSyncSelectiveOnDuplicateKeyUpdate(MchNotify paramMchNotify);
}

