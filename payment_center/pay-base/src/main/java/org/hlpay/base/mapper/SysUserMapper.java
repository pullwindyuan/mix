package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SysUser;

public interface SysUserMapper {
  List<SysUser> infoSysUser(@Param("loginAccount") String paramString);
  
  SysUser info(@Param("userId") String paramString);
  
  int save(SysUser paramSysUser);
}

