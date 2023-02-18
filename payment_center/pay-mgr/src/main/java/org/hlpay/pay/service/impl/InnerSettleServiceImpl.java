 package org.hlpay.pay.service.impl


 import com.alibaba.fastjson.JSONObject;
 import java.util.List;
 import org.hlpay.base.bo.FixSettleBo;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.ResultEnum;
 import org.hlpay.common.util.StrUtil;
 import org.hlpay.pay.service.InnerSettleLocalService;
 import org.hlpay.pay.service.impl.PayInnerSettle4HLServiceImpl;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;










 @Component
 public class InnerSettleServiceImpl
   implements InnerSettleLocalService
 {
   @Autowired
   private PayInnerSettle4HLServiceImpl payInnerSettle4HLService;

   public CommonResult settleIncoming(String thirdOrderId, String mchId, String channelId) throws NoSuchMethodException {
     return this.payInnerSettle4HLService.thirdPayToIncomingOfMch(thirdOrderId, mchId, channelId);
   }


   public CommonResult settleTrans(String fromSettleCardNo) {
     try {
       return this.payInnerSettle4HLService.transSettleToMch(fromSettleCardNo);
     } catch (Exception e) {
       e.printStackTrace();
       if (e.getMessage().equals(ResultEnum.SETTLE_ING.getCode().toString())) {
         return CommonResult.error(ResultEnum.SETTLE_ING.getCode().intValue(), ResultEnum.SETTLE_ING.getDesc());
       }
       try {
         String eMsg = StrUtil.getJsonString(e.getMessage());
         JSONObject jsonObject = JSONObject.parseObject(eMsg);
         int code = jsonObject.getIntValue("code");
         jsonObject.remove("code");
         return CommonResult.error(code, jsonObject.toJSONString());
       } catch (Exception ex) {
         return CommonResult.error(e.getMessage());
       }
     }
   }



   public CommonResult tryToFixSettleTrans(String fromSettleCardNo) {
     try {
       return this.payInnerSettle4HLService.tryToFixAllPayOrderSettleToMch(fromSettleCardNo);
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error("操作失败");
     }
   }

   public CommonResult tryToFixSettleTransByMchIdInPlatform(FixSettleBo fixSettleBo) {
     try {
       return this.payInnerSettle4HLService.tryToFixAllPayOrderSettleToMchByMchIdInPlatform(fixSettleBo);
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error("操作失败");
     }
   }


   public CommonResult settleWithdraw(String settleCardNo) {
     try {
       return this.payInnerSettle4HLService.sendSettleRequestToPlatform(settleCardNo);
     } catch (Exception e) {
       e.printStackTrace();
       try {
         String eMsg = StrUtil.getJsonString(e.getMessage());
         JSONObject jsonObject = JSONObject.parseObject(eMsg);
         int code = jsonObject.getIntValue("code");
         jsonObject.remove("code");
         return CommonResult.error(code, jsonObject.toJSONString());
       } catch (Exception ex) {
         return CommonResult.error(e.getMessage());
       }
     }
   }


   public CommonResult settleDoWithdraw(String settleOrderNo) {
     try {
       return this.payInnerSettle4HLService.doSettle(settleOrderNo);
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error("操作失败");
     }
   }


   public CommonResult settleDoWithdrawBySettleCardNo(String settleCardNo) {
     try {
       return this.payInnerSettle4HLService.doSettleBySettleCardNoInPlatform(settleCardNo);
     } catch (Exception e) {
       e.printStackTrace();
       return CommonResult.error("操作失败");
     }
   }


   public CommonResult syncSettleWithdrawToPlatform(String params) throws Exception {
     return this.payInnerSettle4HLService.syncSettleWithdrawToPlatformV1(params);
   }


   public void callBackCashSettleDone(List<SaCard> list) throws NoSuchMethodException {
     this.payInnerSettle4HLService.callBackCashSettleDone(list);
   }
 }

