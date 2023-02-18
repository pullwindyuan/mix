package org.hlpay.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hlpay.base.model.IapReceipt;
import org.hlpay.base.model.IapReceiptExample;

public interface IapReceiptMapper {
  int countByExample(IapReceiptExample paramIapReceiptExample);
  
  int deleteByExample(IapReceiptExample paramIapReceiptExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(IapReceipt paramIapReceipt);
  
  int insertSelective(IapReceipt paramIapReceipt);
  
  List<IapReceipt> selectByExampleWithBLOBs(IapReceiptExample paramIapReceiptExample);
  
  List<IapReceipt> selectByExample(IapReceiptExample paramIapReceiptExample);
  
  IapReceipt selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") IapReceipt paramIapReceipt, @Param("example") IapReceiptExample paramIapReceiptExample);
  
  int updateByExampleWithBLOBs(@Param("record") IapReceipt paramIapReceipt, @Param("example") IapReceiptExample paramIapReceiptExample);
  
  int updateByExample(@Param("record") IapReceipt paramIapReceipt, @Param("example") IapReceiptExample paramIapReceiptExample);
  
  int updateByPrimaryKeySelective(IapReceipt paramIapReceipt);
  
  int updateByPrimaryKeyWithBLOBs(IapReceipt paramIapReceipt);
  
  int updateByPrimaryKey(IapReceipt paramIapReceipt);
}

