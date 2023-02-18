package org.hlpay.base.payFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment-service")
public interface QueryOrderAPI {
  @RequestMapping({"/pay/query_order"})
  String queryPayOrder(@RequestParam("params") String paramString);
  
  @RequestMapping({"/pay/query_refund_order"})
  String queryRefundOrder(@RequestParam("params") String paramString);
}

