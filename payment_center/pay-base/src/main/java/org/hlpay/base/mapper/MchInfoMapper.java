package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.ExternalMchInfo;
import org.hlpay.base.model.MchInfo;
import org.hlpay.base.model.MchInfoExample;

public interface MchInfoMapper {
  int countByExample(MchInfoExample paramMchInfoExample);
  
  int deleteByExample(MchInfoExample paramMchInfoExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(MchInfo paramMchInfo);
  
  int listInsertExternalMchInfo(@Param("list") List<ExternalMchInfo> paramList);
  
  int insertSelective(MchInfo paramMchInfo);
  
  int insertSelectiveForTest(MchInfo paramMchInfo);
  
  int insertSelectiveForAudit(MchInfo paramMchInfo);
  
  List<MchInfo> selectByExample(MchInfoExample paramMchInfoExample);
  
  List<MchInfo> selectByExampleFromAudit(MchInfoExample paramMchInfoExample);
  
  MchInfo selectByPrimaryKey(String paramString);
  
  MchInfo selectByPrimaryKeyForAudit(String paramString);
  
  int updateByExampleSelective(@Param("record") MchInfo paramMchInfo, @Param("example") MchInfoExample paramMchInfoExample);
  
  int updateByExample(@Param("record") MchInfo paramMchInfo, @Param("example") MchInfoExample paramMchInfoExample);
  
  int updateByPrimaryKeySelective(MchInfo paramMchInfo);
  
  int updateByPrimaryKeySelectiveForAudit(MchInfo paramMchInfo);
  
  int updateByPrimaryKey(MchInfo paramMchInfo);
}

