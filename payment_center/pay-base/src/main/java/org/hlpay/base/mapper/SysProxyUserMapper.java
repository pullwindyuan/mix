package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SysProxyUser;

public interface SysProxyUserMapper {
  List<SysProxyUser> infoSysProxyUser(@Param("proxyNumber") String paramString);
}

