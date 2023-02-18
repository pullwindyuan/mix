package org.hlpay.pay.service


import java.util.List;
import org.hlpay.base.model.SaCard;
import org.hlpay.common.entity.CommonResult;

public interface InnerSettleLocalService {
  CommonResult settleIncoming(String paramString1, String paramString2, String paramString3) throws Exception;
  
  CommonResult settleTrans(String paramString) throws Exception;
  
  CommonResult tryToFixSettleTrans(String paramString) throws Exception;
  
  CommonResult settleWithdraw(String paramString) throws Exception;
  
  CommonResult settleDoWithdraw(String paramString) throws Exception;
  
  CommonResult settleDoWithdrawBySettleCardNo(String paramString) throws Exception;
  
  CommonResult syncSettleWithdrawToPlatform(String paramString) throws Exception;
  
  void callBackCashSettleDone(List<SaCard> paramList) throws NoSuchMethodException;
}

