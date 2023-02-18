package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.hlpay.base.bo.AccessBo;
import org.hlpay.base.bo.MchGetSettleBo;
import org.hlpay.base.bo.MchInfoForConfigBo;
import org.hlpay.base.bo.MchPayChannelALIPAYBo;
import org.hlpay.base.bo.MchPayChannelWXBo;
import org.hlpay.base.bo.MchSettleConfigBo;
import org.hlpay.base.bo.MchSettlePoundageRateConfigBo;
import org.hlpay.base.bo.MchSettleTnConfigBo;
import org.hlpay.base.bo.PayChannelALIPAYBo;
import org.hlpay.base.bo.PayChannelBo;
import org.hlpay.base.bo.PayChannelStateBo;
import org.hlpay.base.bo.PayChannelWXBo;
import org.hlpay.base.bo.PlatformAgencyBo;
import org.hlpay.base.bo.PlatformSettlePoundageRateConfigBo;
import org.hlpay.base.bo.PlatformSettleTnConfigBo;
import org.hlpay.base.bo.SyncAuditStatusBo;
import org.hlpay.base.vo.MchInfoVo;
import org.hlpay.base.vo.PayChannelALIPAYVo;
import org.hlpay.base.vo.PayChannelWXVo;
import org.hlpay.common.entity.CommonResult;
import org.hlpay.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("pay-mgr")
public interface MgrApi {
  @ApiOperation(value = "私有云专用：获取平台信息", notes = "获取平台信息（包括支付渠道信息）")
  @RequestMapping(value = {"/get"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<MchInfoVo> getPlatform(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：公域持有的安全获取平台商户信息", notes = "该接口在共有平台模式下运行时有效，用于私有平台请求同步支付信息。安全获取平台商户信息（包括支付渠道信息）,调用本接口不会直接返还信息，而是根据商户信息中配置的安全URL异步回调")
  @RequestMapping(value = {"/safeGet"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<Result> safeGetPlatform(@RequestBody String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：公域持有的安全获取平台商户信息", notes = "该接口在共有平台模式下运行时有效，用于私有平台请求同步支付信息。")
  @RequestMapping(value = {"/getPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<MchInfoForConfigBo> getPlatform(@RequestBody String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：新增平台", notes = "新增平台")
  @RequestMapping(value = {"/create"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult createPlatformInfo(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：公域持有的新增平台代理信息", notes = "新增平台代理信息：公有平台专用，用于添加入驻平台的私有化企业的对应的商户信息，私有化部署的支付中心会拉取该商户作为自己的平台支付信息")
  @RequestMapping(value = {"/createAgency"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<Result> createPlatformAgencyInfo(@RequestBody PlatformAgencyBo paramPlatformAgencyBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：私域持有的设置平台信息", notes = "该接口在私有平台模式下运行时有效，用于共有平台在接收到请求同步支付信息后将支付信息通过异步回调的方式来远程设置支付信息。设置平台信息")
  @RequestMapping(value = {"/config"}, method = {RequestMethod.POST})
  @ResponseBody
  @Transactional(rollbackFor = {Exception.class})
  CommonResult<Result> configPlatform(@RequestBody MchInfoForConfigBo paramMchInfoForConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：修改平台费率", notes = "修改平台费率：poundageRate为整数")
  @RequestMapping(value = {"/updatePoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updatePlatformSettlePoundageRate(@RequestBody PlatformSettlePoundageRateConfigBo paramPlatformSettlePoundageRateConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：审核平台费率", notes = "审核平台费率：audit:1为审核通过，0为不通过")
  @RequestMapping(value = {"/auditPoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditPlatformSettlePoundageRate(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：修改平台默认T+n", notes = "修改平台费率：n为整数")
  @RequestMapping(value = {"/updateTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updatePlatformSettleTn(@RequestBody PlatformSettleTnConfigBo paramPlatformSettleTnConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：审核平台Tn", notes = "审核平台费率：audit:1为审核通过，0为不通过")
  @RequestMapping(value = {"/auditTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditPlatformSettleTn(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台当前费率", notes = "获取平台当前费率")
  @RequestMapping(value = {"/getActivePoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getPlatformActiveSettlePoundageRate(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台待审核费率", notes = "获取平台待审核费率")
  @RequestMapping(value = {"/getAuditPoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getPlatformAuditSettlePoundageRate(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台当前T+n", notes = "获取平台当前T+n")
  @RequestMapping(value = {"/getActiveTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getPlatformActiveSettleTn(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台待审核T+n", notes = "获取平台待审核T+n")
  @RequestMapping(value = {"/getAuditTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getPlatformAuditSettleTn(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户费率", notes = "修改特定商户费率：poundageRate为整数")
  @RequestMapping(value = {"/updateMchPoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updateMchSettlePoundageRate(@RequestBody MchSettlePoundageRateConfigBo paramMchSettlePoundageRateConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户费率", notes = "审核特定商户费率：audit:1为审核通过，0为不通过")
  @RequestMapping(value = {"/auditMchPoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditMchSettlePoundageRate(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户T+n", notes = "修改特定商户T+n：n为整数")
  @RequestMapping(value = {"/updateMchTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updateMchSettleTn(@RequestBody MchSettleTnConfigBo paramMchSettleTnConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户T+n", notes = "审核特定商户T+n：audit:1为审核通过，0为不通过")
  @RequestMapping(value = {"/auditMchTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditMchSettleTn(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户当前费率", notes = "获取特定商户当前费率")
  @RequestMapping(value = {"/getMchActivePoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getMchActiveSettlePoundageRate(@RequestBody MchGetSettleBo paramMchGetSettleBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户待审核费率", notes = "获取特定商户待审核费率")
  @RequestMapping(value = {"/getMchAuditPoundageRate"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getMchAuditSettlePoundageRate(@RequestBody MchGetSettleBo paramMchGetSettleBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户当前T+n", notes = "获取特定商户当前T+n")
  @RequestMapping(value = {"/getMchActiveTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getMchActiveSettleTn(@RequestBody MchGetSettleBo paramMchGetSettleBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户待审核T+n", notes = "获取特定商户待审核T+n")
  @RequestMapping(value = {"/getMchAuditTn"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult getMchAuditSettleTn(@RequestBody MchGetSettleBo paramMchGetSettleBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台微信APP支付参数", notes = "获取的支付渠道信息直接绑定平台对应的mchId")
  @RequestMapping(value = {"/getWXAPPForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<PayChannelWXVo> getWXAPPForPlatform(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台支付宝APP支付参数", notes = "获取的支付渠道信息直接绑定平台对应的mchId")
  @RequestMapping(value = {"/getALIPAYAPPForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<PayChannelALIPAYVo> getALIPAYAPPForPlatform(@RequestBody AccessBo paramAccessBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台微信APP支付参数", notes = "保存的支付渠道信息直接绑定平台对应的mchId")
  @RequestMapping(value = {"/saveWXAPPForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult saveWXAPPForPlatform(@RequestBody PayChannelWXBo paramPayChannelWXBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台支付宝APP支付参数", notes = "保存的支付渠道信息直接绑定平台对应的mchId")
  @RequestMapping(value = {"/saveALIPAYAPPForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult saveALIPAYAPPForPlatform(@RequestBody PayChannelALIPAYBo paramPayChannelALIPAYBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：保存商户微信APP支付参数", notes = "保存的支付渠道信息直接绑定商户对应的mchId")
  @RequestMapping(value = {"/saveMchWXPayForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult saveMchWXPayForPlatform(@RequestBody MchPayChannelWXBo paramMchPayChannelWXBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：保存商户支付宝APP支付参数", notes = "保存的支付渠道信息直接绑定商户对应的mchId")
  @RequestMapping(value = {"/saveMchALIPAYAPPForPlatform"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult saveMchALIPAYAPPForPlatform(@RequestBody MchPayChannelALIPAYBo paramMchPayChannelALIPAYBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：变更支付渠道状态", notes = "变更支付渠道状态")
  @RequestMapping(value = {"/updatePayChannelState"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updatePayChannelState(@RequestBody PayChannelStateBo paramPayChannelStateBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：审核支付渠道审核信息", notes = "审核的支付渠道审核信息")
  @RequestMapping(value = {"/auditPlatformPayChannel"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditPlatformPayChannel(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用：创建平台内部支付渠道", notes = "创建平台内部支付渠道：默认只能配置给平台商户")
  @RequestMapping(value = {"/createInnerPayChannel"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult<Integer> createPlatformInnerChannel(@RequestBody PayChannelBo paramPayChannelBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户结算参数，包括费率、结算时间、是否启用配置", notes = "修改特定商户结算参数，包括费率、结算时间、是否启用配置")
  @RequestMapping(value = {"/updateMchSettleParams"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult updateMchSettleParams(@RequestBody MchSettleConfigBo paramMchSettleConfigBo, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户结算参数", notes = "审核特定商户结算参数：audit:1为审核通过，0为不通过")
  @RequestMapping(value = {"/auditMchSettleParams"}, method = {RequestMethod.POST})
  @ResponseBody
  CommonResult auditMchSettleParams(@RequestBody SyncAuditStatusBo paramSyncAuditStatusBo, HttpServletRequest paramHttpServletRequest) throws Exception;
}

