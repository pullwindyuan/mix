package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import org.hlpay.base.vo.ExternalMchInfoVo;
import org.hlpay.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wljiam-user-organization-provider")
public interface AppOrganizeClient {
  @ApiOperation("支付中心专用接口-通过ID获取组织信息")
  @GetMapping({"/pd/organization/organize/getOrgMerchantParentInfo"})
  CommonResult<ExternalMchInfoVo> getOrgMerchantParentInfo(@RequestParam("id") Long paramLong);
}
