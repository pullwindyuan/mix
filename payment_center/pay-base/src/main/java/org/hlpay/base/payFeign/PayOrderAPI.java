package org.hlpay.base.payFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("payment-service")
public interface PayOrderAPI {
  @RequestMapping({"/pay/create_order"})
  String payOrder(@RequestParam("params") String paramString);
  
  @RequestMapping({"/pay/repay"})
  String queryAndPayOrder(@RequestParam("params") String paramString);
  
  @RequestMapping({"/pay/query_order"})
  @ResponseBody
  String queryPayOrder(@RequestParam String paramString);
}

