package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import org.hlpay.base.bo.AuthGenAccessKeyBo;
import org.hlpay.base.vo.AuthPrivateKeyVo;
import org.hlpay.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("pay-mgr")
public interface SecurityMgrApi {
  @ApiOperation(value = "获取操作秘钥", notes = "获取操作秘钥接口：传入参数为JSON格式封装的params参数集合，集合内字段定义如下\n \t/**\n\t/**\n\t * api接口访问编号\n\t */\n\t@ApiModelProperty(value = \"api接口访问编号：由本系统审核通过后分配\", required = true)\n\tprivate String appid;\n\t/**\n\t * 被授权用户编号\n\t */\n\t@ApiModelProperty(value = \"被授权用户编号\", required = true)\n\tprivate Long userNo;\n\t/**\n\t * 所属组织ID\n\t */\n\t@ApiModelProperty(value = \"所属组织ID\", required = true)\n\tprivate Long orgId;\n\t/**\n\t * MD5签名\n\t */\n\t@ApiModelProperty(value = \"MD5签名：通过随appid一起分配的accesskey来对params参数进行签名，防止被篡改\", required = true)\n\tprivate String sign;\n\t/**\n\t * ip地址\n\t */\n\t@ApiModelProperty(value = \"IP：客户端IP地址, required = true)\n\tprivate String ip;")
  @RequestMapping(value = {"/security/genPlatformAccessKey"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<AuthPrivateKeyVo> genPlatformAccessKey(@RequestBody AuthGenAccessKeyBo paramAuthGenAccessKeyBo);
}
