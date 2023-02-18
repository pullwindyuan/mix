package org.hlpay.pay.feign

-INF.classes.org.hlpay.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "card-package-api", configuration = {})
public interface ICardPackage {
  @PostMapping({"/makeDistProfitSplitByPay"})
  String makeDistProfitSplitByPay(@RequestParam("userId") String paramString1, @RequestParam("prodCode") String paramString2, @RequestParam("amount") String paramString3, @RequestParam("mchOrderNo") String paramString4, @RequestParam("unfreezeStringTime") String paramString5);
  
  @PostMapping({"/makeDistProfitSplitByRefund"})
  String makeDistProfitSplitByRefund(@RequestParam("userId") String paramString1, @RequestParam("prodCode") String paramString2, @RequestParam("amount") String paramString3, @RequestParam("mchOrderNo") String paramString4);
  
  @RequestMapping(value = {"/queryGoods"}, method = {RequestMethod.POST})
  Object queryGoods(@RequestParam("code") String paramString);
  
  @RequestMapping(value = {"/queryGoodsList"}, method = {RequestMethod.GET})
  Object queryGoodsList();
}

