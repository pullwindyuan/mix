package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import org.hlpay.base.vo.ExternalMchInfoVo;
import org.hlpay.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "yzn-mallplus-admin")
public interface GetExternalStoreInfo {
  @ApiOperation("支付中心专用接口-通过ID获取门店信息")
  @GetMapping({"/yzn/mallplus/admin/MallShopAdminController/getMerchantParentByStoreId"})
  CommonResult<ExternalMchInfoVo> getMerchantParentByStoreId(@RequestParam("id") Long paramLong);
}

