package org.hlpay.base.payFeign;

import org.hlpay.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("payment-service")
public interface InnerSettleAPI {
  @RequestMapping(value = {"/settle/incoming"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult settleIncoming(@RequestParam("thirdOrderId") String paramString1, @RequestParam("mchId") String paramString2, @RequestParam("channelId") String paramString3) throws Exception;
  
  @RequestMapping(value = {"/settle/settleAgencyIncoming"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult platformSettleAgencyIncoming(@RequestParam("thirdOrderId") String paramString) throws Exception;
  
  @RequestMapping(value = {"/settle/trans"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult settleTrans(@RequestParam("fromSettleCardNo") String paramString) throws Exception;
  
  @RequestMapping(value = {"/settle/tryToFixSettleTrans"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult tryToFixSettleTrans(@RequestParam("fromSettleCardNo") String paramString) throws Exception;
  
  @RequestMapping(value = {"/settle/withdraw"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult settleWithdraw(@RequestParam("settleCardNo") String paramString) throws Exception;
  
  @RequestMapping(value = {"/settle/doWithdraw"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult settleDoWithdraw(@RequestParam("mchId") String paramString1, @RequestParam("settleCardNo") String paramString2) throws Exception;
  
  @RequestMapping(value = {"/settle/syncSettleWithdrawToPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult syncSettleWithdrawToPlatform(@RequestBody String paramString) throws Exception;
}

