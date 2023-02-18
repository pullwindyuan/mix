package org.hlpay.base.payFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment-service")
public interface RefundOrderAPI {
  @RequestMapping({"/refund/queue"})
  String payOrder(@RequestParam String paramString);
  
  @RequestMapping({"/refund/create_order"})
  String payOrderWork(@RequestParam String paramString);
}
