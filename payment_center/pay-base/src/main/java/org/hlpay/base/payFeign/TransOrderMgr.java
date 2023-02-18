package org.hlpay.base.payFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment-service")
public interface TransOrderMgr {
  @RequestMapping({"/trans_order/"})
  String getList(@RequestParam("order") String paramString, @RequestParam("pageIndex") Integer paramInteger1, @RequestParam("pageSize") Integer paramInteger2);
}

