package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import org.hlpay.base.bo.ExternalMchInfoBo;
import org.hlpay.base.vo.MchInfoForAppVo;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("pay-mgr")
public interface MchInfoApi {
  @ApiOperation(value = "私有云专用：获取商户信息", notes = "获取商户信息（包括支付渠道信息）")
  @RequestMapping(value = {"/getMchInfo"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<MchInfoForAppVo> getMchInfo(@RequestBody String paramString) throws Exception;
  
  @ApiOperation(value = "私有云专用：新增外部商户", notes = "新增外部商户：会默认使用父级支付渠道")
  @RequestMapping(value = {"/externalAddMch"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<Result> externalAddMch(@RequestBody ExternalMchInfoBo paramExternalMchInfoBo) throws Exception;
}
