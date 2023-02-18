package org.hlpay.pay.service


import java.util.List;
import org.hlpay.base.model.PayOrder;
import org.hlpay.base.model.SaCard;
import org.hlpay.base.model.TransOrder;
import org.hlpay.common.entity.CommonResult;

public interface IPayInnerSettle4HLService {
  CommonResult thirdPayToIncomingOfMch(String paramString1, String paramString2, String paramString3) throws NoSuchMethodException;
  
  CommonResult thirdPayToIncomingOfMch(String paramString1, String paramString2, String paramString3, boolean paramBoolean) throws NoSuchMethodException;
  
  CommonResult thirdPayToIncomingOfMchBySuccessPayOrder(PayOrder paramPayOrder);
  
  CommonResult transSettleToMch(String paramString) throws Exception;
  
  CommonResult tryToFixAllPayOrderSettleToMch(String paramString) throws Exception;
  
  CommonResult tryToFixPayOrderSettleToMch(String paramString1, String paramString2) throws Exception;
  
  CommonResult sendSettleRequestToPlatform(String paramString) throws Exception;
  
  CommonResult doSettle(String paramString) throws Exception;
  
  CommonResult doSettleBySettleCardNoInPlatform(String paramString) throws Exception;
  
  CommonResult syncSettleWithdrawToPlatformV1(String paramString) throws Exception;
  
  CommonResult doInnerSettleCreateReq(TransOrder paramTransOrder) throws Exception;
  
  CommonResult doInnerSettleTransReq(TransOrder paramTransOrder) throws Exception;
  
  CommonResult syncSettle(String paramString) throws Exception;
  
  void callBackCashSettleDone(List<SaCard> paramList) throws NoSuchMethodException;
}


