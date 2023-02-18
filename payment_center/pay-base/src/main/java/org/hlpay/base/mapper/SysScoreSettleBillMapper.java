package org.hlpay.base.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.SysScoreSettleBill;

public interface SysScoreSettleBillMapper {
  int insertSettleBill(SysScoreSettleBill paramSysScoreSettleBill);
  
  int insert(@Param("userIdList") List<String> paramList, @Param("sa") SysScoreSettleBill paramSysScoreSettleBill);
  
  int deleteSettleBill(@Param("billId") String paramString);
  
  int updateSettleBill(SysScoreSettleBill paramSysScoreSettleBill);
  
  List<SysScoreSettleBill> infoSettleBill(@Param("billId") String paramString);
  
  List<SysScoreSettleBill> selectSettleBill(SysScoreSettleBill paramSysScoreSettleBill);
  
  List<SysScoreSettleBill> limitSettleBill(@Param("userId") String paramString);
  
  int count(SysScoreSettleBill paramSysScoreSettleBill);
  
  int totalBill(SysScoreSettleBill paramSysScoreSettleBill);
  
  List<Map<String, String>> getBill(@Param("createTime") String paramString);
}

