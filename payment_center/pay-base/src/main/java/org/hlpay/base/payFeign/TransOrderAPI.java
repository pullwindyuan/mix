package org.hlpay.base.payFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment-service")
public interface TransOrderAPI {
  @RequestMapping({"/trans/create_order"})
  String payOrder(@RequestParam("params") String paramString);
}
