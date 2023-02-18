package org.hlpay.base.payFeign;

import org.hlpay.common.util.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card-package-award")
public interface CommonRewardByTrans {
  @PostMapping({"/api/common/reward"})
  ResponseObject userReward(@RequestParam("userId") String paramString1, @RequestParam("prodCode") String paramString2);
  
  @PostMapping({"/api/common/checkReward"})
  ResponseObject checkReward(@RequestParam("userId") String paramString1, @RequestParam("prodCode") String paramString2);
}

