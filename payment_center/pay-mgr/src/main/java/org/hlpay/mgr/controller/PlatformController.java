/*     */ package org.hlpay.mgr.controller;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.hlpay.base.bo.AccessBo;
/*     */ import org.hlpay.base.bo.MchGetSettleBo;
/*     */ import org.hlpay.base.bo.MchInfoForConfigBo;
/*     */ import org.hlpay.base.bo.MchPayChannelALIPAYBo;
/*     */ import org.hlpay.base.bo.MchPayChannelWXBo;
/*     */ import org.hlpay.base.bo.MchSettleAuditBo;
/*     */ import org.hlpay.base.bo.MchSettleConfigBo;
/*     */ import org.hlpay.base.bo.MchSettlePoundageRateConfigBo;
/*     */ import org.hlpay.base.bo.MchSettleTnConfigBo;
/*     */ import org.hlpay.base.bo.PayChannelALIPAYBo;
/*     */ import org.hlpay.base.bo.PayChannelAuditBo;
/*     */ import org.hlpay.base.bo.PayChannelBo;
/*     */ import org.hlpay.base.bo.PayChannelStateBo;
/*     */ import org.hlpay.base.bo.PayChannelWXBo;
/*     */ import org.hlpay.base.bo.PlatformAgencyBo;
/*     */ import org.hlpay.base.bo.PlatformSettleAuditBo;
/*     */ import org.hlpay.base.bo.PlatformSettlePoundageRateConfigBo;
/*     */ import org.hlpay.base.bo.PlatformSettleTnConfigBo;
/*     */ import org.hlpay.base.bo.SyncAuditStatusBo;
/*     */ import org.hlpay.base.model.MchInfo;
/*     */ import org.hlpay.base.model.PayChannel;
/*     */ import org.hlpay.base.model.PayOrder;
/*     */ import org.hlpay.base.payFeign.MgrApi;
import org.hlpay.base.payFeign.WorkflowAudit;
import org.hlpay.base.security.SecurityAccessManager;
import org.hlpay.base.service.MchInfoService;
import org.hlpay.base.service.PayChannelService;
import org.hlpay.base.service.RunModeService;
import org.hlpay.base.service.impl.PayChannel4AliServiceImpl;
import org.hlpay.base.service.impl.PayChannel4WxServiceImpl;
import org.hlpay.base.service.mq.Mq4MchNotify;
import org.hlpay.base.vo.MchInfoVo;
import org.hlpay.base.vo.PayChannelALIPAYVo;
/*     */ import org.hlpay.base.vo.PayChannelWXVo;
/*     */ import org.hlpay.common.entity.CommonResult;
/*     */ import org.hlpay.common.entity.Result;
/*     */ import org.hlpay.common.enumm.CurrencyTypeEnum;
/*     */ import org.hlpay.common.enumm.ResultEnum;
/*     */ import org.hlpay.common.enumm.RunModeEnum;
/*     */ import org.hlpay.common.util.*;
/*     */
/*     */
/*     */
/*     */
import org.hlpay.mgr.sevice.PlatformService;
import org.hlpay.pay.mq.MqCardNotify;
import org.springframework.amqp.AmqpException;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ 
/*     */ @Api(value = "支付中心平台设置", tags = {"支付中心平台设置"})
/*     */ @Controller
/*     */ @RequestMapping({"/platform/"})
/*     */ public class PlatformController implements MgrApi {
/*  53 */   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.controller.PlatformController.class);
/*     */   
/*     */   @Resource
/*     */   private Environment env;
/*     */   
/*     */   @Autowired
/*     */   private WorkflowAudit workflowAudit;
/*     */   
/*     */   @Autowired
/*     */   private MchInfoService mchInfoService;
/*     */   
/*     */   @Autowired
/*     */   private PlatformService platformService;
/*     */   
/*     */   @Autowired
/*     */   private SecurityAccessManager securityAccessManager;
/*     */   
/*     */   @Autowired
/*     */   private PayChannelService payChannelService;
/*     */   
/*     */   @Autowired
/*     */   private PayChannel4AliServiceImpl payChannel4AliService;
/*     */   
/*     */   @Autowired
/*     */   private PayChannel4WxServiceImpl payChannel4WxService;
/*     */   
/*     */   @Autowired
/*     */   private RedisProUtil redisProUtil;
/*     */   
/*     */   @Autowired
/*     */   private MqCardNotify mqCardNotify;
/*     */   
/*     */   @Autowired
/*     */ Mq4MchNotify mq4MchNotify;
/*     */   
/*     */   @Autowired
/*     */   private RunModeService runModeService;
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：获取平台信息", notes = "获取平台信息（包括支付渠道信息）")
/*     */   public CommonResult<MchInfoVo> getPlatform(HttpServletRequest request) throws Exception {
/*  94 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/*  95 */       return CommonResult.error("非授权IP");
/*     */     }
/*  97 */     return this.platformService.getPlatform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：公域持有的安全获取平台商户信息", notes = "该接口在共有平台模式下运行时有效，用于私有平台请求同步支付信息。安全获取平台商户信息（包括支付渠道信息）,调用本接口不会直接返还信息，而是根据商户信息中配置的安全URL异步回调")
/*     */   public CommonResult<Result> safeGetPlatform(@RequestBody String externalId, HttpServletRequest request) throws Exception {
/* 105 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 106 */       return CommonResult.error("非授权IP");
/*     */     }
/* 108 */     return this.platformService.safeGetPlatform(externalId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：公域持有的安全获取平台商户信息", notes = "该接口在共有平台模式下运行时有效，用于私有平台请求同步支付信息。")
/*     */   public CommonResult<MchInfoForConfigBo> getPlatform(@RequestBody String externalId, HttpServletRequest request) throws Exception {
/* 116 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 117 */       return CommonResult.error("非授权IP");
/*     */     }
/* 119 */     return CommonResult.success(this.platformService.getPlatform(externalId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：新增平台", notes = "新增平台")
/*     */   public CommonResult createPlatformInfo(HttpServletRequest request) throws Exception {
/* 127 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 128 */       return CommonResult.error("非授权IP");
/*     */     }
/* 130 */     return this.mchInfoService.createPlatformInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：公域持有的新增平台代理信息", notes = "新增平台代理信息：公有平台专用，用于添加入驻平台的私有化企业的对应的商户信息，私有化部署的支付中心会拉取该商户作为自己的平台支付信息")
/*     */   public CommonResult<Result> createPlatformAgencyInfo(@RequestBody PlatformAgencyBo platformAgencyBo, HttpServletRequest request) throws Exception {
/* 138 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 139 */       return CommonResult.error("非授权IP");
/*     */     }
/* 141 */     return this.mchInfoService.createPlatformAgencyInfo(platformAgencyBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：私域持有的设置平台信息", notes = "该接口在私有平台模式下运行时有效，用于共有平台在接收到请求同步支付信息后将支付信息通过异步回调的方式来远程设置支付信息。设置平台信息")
/*     */   @Transactional(rollbackFor = {Exception.class})
/*     */   public CommonResult<Result> configPlatform(@RequestBody MchInfoForConfigBo mchInfoForConfigBo, HttpServletRequest request) throws Exception {
/* 150 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 151 */       return CommonResult.error("非授权IP");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 156 */       if (mchInfoForConfigBo.getConfigType().equals("pc")) {
/*     */         
/* 158 */         this.mqCardNotify.broadcast("pc");
/* 159 */       } else if (mchInfoForConfigBo.getConfigType().equals("mch")) {
/*     */         
/* 161 */         mchInfoForConfigBo.setPayChannels(null);
/* 162 */         this.redisProUtil.hPutAll("platformConfig", BeanConvertUtils.bean2MapWithOutNull(mchInfoForConfigBo));
/* 163 */         this.mqCardNotify.broadcast("mch");
/*     */       } 
/* 165 */     } catch (AmqpException e) {
/* 166 */       e.printStackTrace();
/* 167 */       return CommonResult.error("内部错误");
/*     */     } 
/* 169 */     return CommonResult.success(Result.createSuccessResult("成功"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：修改平台费率", notes = "修改平台费率：poundageRate为整数")
/*     */   public CommonResult updatePlatformSettlePoundageRate(@RequestBody PlatformSettlePoundageRateConfigBo platformSettlePoundageRateConfigBo, HttpServletRequest request) throws Exception {
/* 183 */     this.securityAccessManager.checkAccessKey(platformSettlePoundageRateConfigBo.getAccessKey(), platformSettlePoundageRateConfigBo.getOperator(), platformSettlePoundageRateConfigBo.getOperatorOrgId(), request);
/* 184 */     this.mchInfoService.createPlatform();
/* 185 */     return this.platformService.updatePlatformSettlePoundageRate(platformSettlePoundageRateConfigBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：审核平台费率", notes = "审核平台费率：audit:1为审核通过，0为不通过")
/*     */   public CommonResult auditPlatformSettlePoundageRate(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 194 */     this.mchInfoService.createPlatform();
/* 195 */     PlatformSettleAuditBo platformSettleAuditBo = new PlatformSettleAuditBo();
/* 196 */     platformSettleAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 197 */     return this.platformService.auditPlatformSettlePoundageRate(platformSettleAuditBo.getAudit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：修改平台默认T+n", notes = "修改平台费率：n为整数")
/*     */   public CommonResult updatePlatformSettleTn(@RequestBody PlatformSettleTnConfigBo platformSettleTnConfigBo, HttpServletRequest request) throws Exception {
/* 205 */     this.securityAccessManager.checkAccessKey(platformSettleTnConfigBo.getAccessKey(), platformSettleTnConfigBo.getOperator(), platformSettleTnConfigBo.getOperatorOrgId(), request);
/* 206 */     this.mchInfoService.createPlatform();
/* 207 */     return this.platformService.updatePlatformSettleTn(platformSettleTnConfigBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：审核平台Tn", notes = "审核平台费率：audit:1为审核通过，0为不通过")
/*     */   public CommonResult auditPlatformSettleTn(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 216 */     this.mchInfoService.createPlatform();
/* 217 */     PlatformSettleAuditBo platformSettleAuditBo = new PlatformSettleAuditBo();
/* 218 */     platformSettleAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 219 */     return this.platformService.auditPlatformSettleTn(platformSettleAuditBo.getAudit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台当前费率", notes = "获取平台当前费率")
/*     */   public CommonResult getPlatformActiveSettlePoundageRate(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 227 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 228 */     this.mchInfoService.createPlatform();
/* 229 */     return this.platformService.getPlatformActiveSettlePoundageRate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台待审核费率", notes = "获取平台待审核费率")
/*     */   public CommonResult getPlatformAuditSettlePoundageRate(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 237 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 238 */     this.mchInfoService.createPlatform();
/* 239 */     return this.platformService.getPlatformAuditSettlePoundageRate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台当前T+n", notes = "获取平台当前T+n")
/*     */   public CommonResult getPlatformActiveSettleTn(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 247 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 248 */     this.mchInfoService.createPlatform();
/* 249 */     return this.platformService.getPlatformActiveSettleTn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台待审核T+n", notes = "获取平台待审核T+n")
/*     */   public CommonResult getPlatformAuditSettleTn(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 257 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 258 */     this.mchInfoService.createPlatform();
/* 259 */     return this.platformService.getPlatformAuditSettleTn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户结算参数，包括费率、结算时间、是否启用配置", notes = "修改特定商户结算参数，包括费率、结算时间、是否启用配置")
/*     */   public CommonResult updateMchSettleParams(@RequestBody MchSettleConfigBo mchSettleConfigBo, HttpServletRequest request) throws Exception {
/* 267 */     this.securityAccessManager.checkAccessKey(mchSettleConfigBo.getAccessKey(), mchSettleConfigBo.getOperator(), mchSettleConfigBo.getOperatorOrgId(), request);
/* 268 */     this.mchInfoService.createPlatform();
/* 269 */     return this.mchInfoService.updateMchSettle(mchSettleConfigBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户费率", notes = "修改特定商户费率：poundageRate为整数")
/*     */   public CommonResult updateMchSettlePoundageRate(@RequestBody MchSettlePoundageRateConfigBo mchSettlePoundageRateConfigBo, HttpServletRequest request) throws Exception {
/* 277 */     this.securityAccessManager.checkAccessKey(mchSettlePoundageRateConfigBo.getAccessKey(), mchSettlePoundageRateConfigBo.getOperator(), mchSettlePoundageRateConfigBo.getOperatorOrgId(), request);
/* 278 */     this.mchInfoService.createPlatform();
/* 279 */     return this.mchInfoService.updateMchSettlePoundageRate(mchSettlePoundageRateConfigBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户费率", notes = "审核特定商户费率：audit:1为审核通过，0为不通过")
/*     */   public CommonResult auditMchSettlePoundageRate(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 288 */     this.mchInfoService.createPlatform();
/* 289 */     MchSettleAuditBo mchSettleAuditBo = new MchSettleAuditBo();
/* 290 */     mchSettleAuditBo.setMchId(syncAuditStatusBo.getBizId());
/* 291 */     mchSettleAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 292 */     CommonResult commonResult = this.mchInfoService.auditMchSettle(mchSettleAuditBo.getMchId(), mchSettleAuditBo.getAudit());
/* 293 */     if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue())
/*     */     {
/* 295 */       this.mq4MchNotify.send("queue.notify.platform.config.update", "syncConfig-mch", 
/*     */           
/* 297 */           DateUtils.getBetweenValueInMilSeconds(DateUtils.getDaysDurationFirstDate(1), new Date()).longValue());
/*     */     }
/* 299 */     return commonResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户结算参数", notes = "审核特定商户结算参数：audit:1为审核通过，0为不通过")
/*     */   public CommonResult auditMchSettleParams(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 308 */     this.mchInfoService.createPlatform();
/* 309 */     MchSettleAuditBo mchSettleAuditBo = new MchSettleAuditBo();
/* 310 */     mchSettleAuditBo.setMchId(syncAuditStatusBo.getBizId());
/* 311 */     mchSettleAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 312 */     CommonResult commonResult = this.mchInfoService.auditMchSettle(mchSettleAuditBo.getMchId(), mchSettleAuditBo.getAudit());
/* 313 */     if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue())
/*     */     {
/* 315 */       this.mq4MchNotify.send("queue.notify.platform.config.update", "syncConfig-mch", 
/*     */           
/* 317 */           DateUtils.getBetweenValueInMilSeconds(DateUtils.getDaysDurationFirstDate(1), new Date()).longValue());
/*     */     }
/* 319 */     return commonResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：修改特定商户T+n", notes = "修改特定商户T+n：n为整数")
/*     */   public CommonResult updateMchSettleTn(@RequestBody MchSettleTnConfigBo mchSettleTnConfigBo, HttpServletRequest request) throws Exception {
/* 327 */     this.securityAccessManager.checkAccessKey(mchSettleTnConfigBo.getAccessKey(), mchSettleTnConfigBo.getOperator(), mchSettleTnConfigBo.getOperatorOrgId(), request);
/* 328 */     this.mchInfoService.createPlatform();
/* 329 */     return this.mchInfoService.updateMchSettleTn(mchSettleTnConfigBo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：审核特定商户T+n", notes = "审核特定商户T+n：audit:1为审核通过，0为不通过")
/*     */   public CommonResult auditMchSettleTn(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 338 */     this.mchInfoService.createPlatform();
/* 339 */     MchSettleAuditBo mchSettleAuditBo = new MchSettleAuditBo();
/* 340 */     mchSettleAuditBo.setMchId(syncAuditStatusBo.getBizId());
/* 341 */     mchSettleAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 342 */     CommonResult commonResult = this.mchInfoService.auditMchSettle(mchSettleAuditBo.getMchId(), mchSettleAuditBo.getAudit());
/* 343 */     if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue())
/*     */     {
/* 345 */       this.mq4MchNotify.send("queue.notify.platform.config.update", "syncConfig-mch", 
/*     */           
/* 347 */           DateUtils.getBetweenValueInMilSeconds(DateUtils.getDaysDurationFirstDate(1), new Date()).longValue());
/*     */     }
/* 349 */     return commonResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户当前费率", notes = "获取特定商户当前费率")
/*     */   public CommonResult getMchActiveSettlePoundageRate(@RequestBody MchGetSettleBo mchGetSettleBo, HttpServletRequest request) throws Exception {
/* 357 */     this.securityAccessManager.checkAccessKey(mchGetSettleBo.getAccessKey(), mchGetSettleBo.getOperator(), mchGetSettleBo.getOperatorOrgId(), request);
/* 358 */     this.mchInfoService.createPlatform();
/* 359 */     return this.mchInfoService.getMchActiveSettlePoundageRate(mchGetSettleBo.getMchId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户待审核费率", notes = "获取特定商户待审核费率")
/*     */   public CommonResult getMchAuditSettlePoundageRate(@RequestBody MchGetSettleBo mchGetSettleBo, HttpServletRequest request) throws Exception {
/* 367 */     this.securityAccessManager.checkAccessKey(mchGetSettleBo.getAccessKey(), mchGetSettleBo.getOperator(), mchGetSettleBo.getOperatorOrgId(), request);
/* 368 */     this.mchInfoService.createPlatform();
/* 369 */     return this.mchInfoService.getMchAuditSettlePoundageRate(mchGetSettleBo.getMchId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户当前T+n", notes = "获取特定商户当前T+n")
/*     */   public CommonResult getMchActiveSettleTn(@RequestBody MchGetSettleBo mchGetSettleBo, HttpServletRequest request) throws Exception {
/* 377 */     this.securityAccessManager.checkAccessKey(mchGetSettleBo.getAccessKey(), mchGetSettleBo.getOperator(), mchGetSettleBo.getOperatorOrgId(), request);
/* 378 */     this.mchInfoService.createPlatform();
/* 379 */     return this.mchInfoService.getMchActiveSettleTn(mchGetSettleBo.getMchId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取特定商户待审核T+n", notes = "获取特定商户待审核T+n")
/*     */   public CommonResult getMchAuditSettleTn(@RequestBody MchGetSettleBo mchGetSettleBo, HttpServletRequest request) throws Exception {
/* 387 */     this.securityAccessManager.checkAccessKey(mchGetSettleBo.getAccessKey(), mchGetSettleBo.getOperator(), mchGetSettleBo.getOperatorOrgId(), request);
/* 388 */     this.mchInfoService.createPlatform();
/* 389 */     return this.mchInfoService.getMchAuditSettleTn(mchGetSettleBo.getMchId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：获取平台微信APP支付参数", notes = "获取的支付渠道信息直接绑定平台对应的mchId")
/*     */   public CommonResult<PayChannelWXVo> getWXAPPForPlatform(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 397 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 398 */     this.mchInfoService.createPlatform();
/* 399 */     PayChannel payChannel = this.payChannelService.selectPayChannel("WX_APP", "1");
/* 400 */     PayChannelWXVo channelWXVo = new PayChannelWXVo();
/* 401 */     if (payChannel != null) {
/* 402 */       channelWXVo.setId(payChannel.getId());
/* 403 */       channelWXVo.setChannelMchId(payChannel.getChannelMchId());
/* 404 */       channelWXVo.setState(payChannel.getState());
/* 405 */       JSONObject param = JSONObject.parseObject(payChannel.getParam());
/* 406 */       channelWXVo.setIPWhiteList(param.getString("IPWhiteList"));
/* 407 */       channelWXVo.setAppId(param.getString("appId"));
/*     */     } 
/*     */     
/* 410 */     return CommonResult.success(channelWXVo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台支付宝APP支付参数", notes = "获取的支付渠道信息直接绑定平台对应的mchId")
/*     */   public CommonResult<PayChannelALIPAYVo> getALIPAYAPPForPlatform(@RequestBody AccessBo accessBo, HttpServletRequest request) throws Exception {
/* 418 */     this.securityAccessManager.checkAccessKey(accessBo.getAccessKey(), accessBo.getOperator(), accessBo.getOperatorOrgId(), request);
/* 419 */     this.mchInfoService.createPlatform();
/* 420 */     PayChannel payChannel = this.payChannelService.selectPayChannel("ALIPAY_MOBILE", "1");
/* 421 */     PayChannelALIPAYVo channelALIPAYVo = new PayChannelALIPAYVo();
/* 422 */     if (payChannel != null) {
/* 423 */       channelALIPAYVo.setId(payChannel.getId());
/* 424 */       channelALIPAYVo.setChannelMchId(payChannel.getChannelMchId());
/* 425 */       channelALIPAYVo.setState(payChannel.getState());
/* 426 */       JSONObject param = JSONObject.parseObject(payChannel.getParam());
/* 427 */       channelALIPAYVo.setIPWhiteList(param.getString("IPWhiteList"));
/*     */     } 
/* 429 */     return CommonResult.success(channelALIPAYVo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台微信APP支付参数", notes = "保存的支付渠道信息直接绑定平台")
/*     */   public CommonResult saveWXAPPForPlatform(@RequestBody PayChannelWXBo payChannelWXBo, HttpServletRequest request) throws Exception {
/* 437 */     this.securityAccessManager.checkAccessKey(payChannelWXBo.getAccessKey(), payChannelWXBo.getOperator(), payChannelWXBo.getOperatorOrgId(), request);
/* 438 */     int runMode = this.runModeService.getRunModeCode();
/* 439 */     if (runMode == RunModeEnum.PUBLIC.getCode().intValue()) {
/* 440 */       if (StringUtils.isEmpty(payChannelWXBo.getMchId())) {
/* 441 */         payChannelWXBo.setMchId("1");
/*     */       }
/* 443 */       return this.platformService.saveWXPayChannel(payChannelWXBo, request);
/*     */     } 
/* 445 */     return CommonResult.error("不支持的运行模式");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：保存平台支付宝APP支付参数", notes = "保存的支付渠道信息直接绑定平台")
/*     */   public CommonResult saveALIPAYAPPForPlatform(@RequestBody PayChannelALIPAYBo payChannelALIPAYBo, HttpServletRequest request) throws Exception {
/* 454 */     this.securityAccessManager.checkAccessKey(payChannelALIPAYBo.getAccessKey(), payChannelALIPAYBo.getOperator(), payChannelALIPAYBo.getOperatorOrgId(), request);
/* 455 */     int runMode = this.runModeService.getRunModeCode();
/* 456 */     if (runMode == RunModeEnum.PUBLIC.getCode().intValue()) {
/* 457 */       if (StringUtils.isEmpty(payChannelALIPAYBo.getMchId())) {
/* 458 */         payChannelALIPAYBo.setMchId("1");
/*     */       }
/* 460 */       return this.platformService.saveALIPayChannel(payChannelALIPAYBo, request);
/*     */     } 
/* 462 */     return CommonResult.error("不支持的运行模式");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：保存商户微信APP支付参数", notes = "保存的支付渠道信息直接绑定商户对应的mchId")
/*     */   public CommonResult saveMchWXPayForPlatform(@RequestBody MchPayChannelWXBo mchPayChannelWXBo, HttpServletRequest request) throws Exception {
/* 471 */     this.securityAccessManager.checkAccessKey(mchPayChannelWXBo.getAccessKey(), mchPayChannelWXBo.getOperator(), mchPayChannelWXBo.getOperatorOrgId(), request);
/* 472 */     int runMode = this.runModeService.getRunModeCode();
/* 473 */     if (runMode == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {
/* 474 */       PayChannelWXBo payChannelWXBo = (PayChannelWXBo)BeanUtil.copyProperties(mchPayChannelWXBo, PayChannelWXBo.class);
/* 475 */       return this.platformService.saveWXPayChannel(payChannelWXBo, request);
/*     */     } 
/* 477 */     return CommonResult.error("不支持的运行模式");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：保存商户支付宝APP支付参数", notes = "保存的支付渠道信息直接绑定商户对应的mchId")
/*     */   public CommonResult saveMchALIPAYAPPForPlatform(@RequestBody MchPayChannelALIPAYBo mchPayChannelALIPAYBo, HttpServletRequest request) throws Exception {
/* 486 */     this.securityAccessManager.checkAccessKey(mchPayChannelALIPAYBo.getAccessKey(), mchPayChannelALIPAYBo.getOperator(), mchPayChannelALIPAYBo.getOperatorOrgId(), request);
/* 487 */     if (this.runModeService.getRunMode().getCode().intValue() == RunModeEnum.PRIVATE_INDEPENDENT.getCode().intValue()) {
/* 488 */       PayChannelALIPAYBo payChannelALIPAYBo = (PayChannelALIPAYBo)BeanUtil.copyProperties(mchPayChannelALIPAYBo, PayChannelALIPAYBo.class);
/* 489 */       return this.platformService.saveALIPayChannel(payChannelALIPAYBo, request);
/*     */     } 
/* 491 */     return CommonResult.error("不支持的运行模式");
/*     */   }
/*     */ 
/*     */   
/*     */   private PayChannel getPayChannel(MchPayChannelWXBo mchPayChannelWXBo) throws Exception {
/* 496 */     PayChannel payChannel = (PayChannel)BeanUtil.copyProperties(mchPayChannelWXBo, PayChannel.class);
/* 497 */     payChannel.setChannelName("WX");
/* 498 */     if (mchPayChannelWXBo.getPayType().equals("PC")) {
/* 499 */       payChannel.setChannelId("WX_MWEB");
/* 500 */     } else if (mchPayChannelWXBo.getPayType().equals("APP")) {
/* 501 */       payChannel.setChannelId("WX_APP");
/* 502 */     } else if (mchPayChannelWXBo.getPayType().equals("MWEB")) {
/* 503 */       payChannel.setChannelId("WX_MWEB");
/* 504 */     } else if (mchPayChannelWXBo.getPayType().equals("QR")) {
/* 505 */       payChannel.setChannelId("WX_NATIVE");
/*     */     } else {
/* 507 */       throw new Exception("支付方式参数不支持");
/*     */     } 
/*     */ 
/*     */     
/* 511 */     if (StringUtils.isEmpty(mchPayChannelWXBo.getMchId())) {
/* 512 */       payChannel.setMchId("1");
/*     */     }
/* 514 */     MchInfo platform = this.mchInfoService.createPlatform();
/* 515 */     if (platform == null) {
/* 516 */       throw new Exception("费率参数异常");
/*     */     }
/* 518 */     payChannel.setChannelMchId(payChannel.getMchId());
/* 519 */     payChannel.setRemark("微信APP支付");
/* 520 */     payChannel.setState(Byte.valueOf((byte)1));
/* 521 */     JSONObject param = new JSONObject();
/* 522 */     param.put("IPWhiteList", mchPayChannelWXBo.getIPWhiteList());
/* 523 */     param.put("appId", mchPayChannelWXBo.getAppId());
/* 524 */     param.put("mchId", mchPayChannelWXBo.getChannelMchId());
/* 525 */     param.put("key", mchPayChannelWXBo.getKey());
/* 526 */     param.put("certLocalPath", "classpath:/assets/apiclient_cert.p12");
/* 527 */     param.put("certPassword", mchPayChannelWXBo.getChannelMchId());
/* 528 */     if (mchPayChannelWXBo.getCurrency().equals(CurrencyTypeEnum.CNY.name())) {
/* 529 */       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
/* 530 */     } else if (mchPayChannelWXBo.getCurrency().equals(CurrencyTypeEnum.USD.name())) {
/* 531 */       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
/*     */     } else {
/* 533 */       throw new Exception("支付币种参数不支持");
/*     */     } 
/* 535 */     param.put("poundageRate", mchPayChannelWXBo.getPoundageRate());
/* 536 */     payChannel.setParam(param.toJSONString());
/* 537 */     return payChannel;
/*     */   }
/*     */   
/*     */   private PayChannel getPayChannel(MchPayChannelALIPAYBo mchPayChannelALIPAYBo) throws Exception {
/* 541 */     PayChannel payChannel = (PayChannel)BeanUtil.copyProperties(mchPayChannelALIPAYBo, PayChannel.class);
/* 542 */     payChannel.setChannelName("ALIPAY");
/* 543 */     if (mchPayChannelALIPAYBo.getPayType().equals("PC")) {
/* 544 */       payChannel.setChannelId("ALIPAY_PC");
/* 545 */     } else if (mchPayChannelALIPAYBo.getPayType().equals("APP")) {
/* 546 */       payChannel.setChannelId("ALIPAY_MOBILE");
/* 547 */     } else if (mchPayChannelALIPAYBo.getPayType().equals("MWEB")) {
/* 548 */       payChannel.setChannelId("ALIPAY_WAP");
/* 549 */     } else if (mchPayChannelALIPAYBo.getPayType().equals("QR")) {
/* 550 */       payChannel.setChannelId("ALIPAY_QR");
/*     */     } else {
/* 552 */       throw new Exception("支付方式参数不支持");
/*     */     } 
/*     */     
/* 555 */     if (StringUtils.isEmpty(mchPayChannelALIPAYBo.getMchId())) {
/* 556 */       payChannel.setMchId("1");
/*     */     }
/* 558 */     MchInfo platform = this.mchInfoService.createPlatform();
/* 559 */     if (platform == null) {
/* 560 */       throw new Exception("费率参数异常");
/*     */     }
/* 562 */     payChannel.setChannelMchId(platform.getMchId());
/* 563 */     payChannel.setRemark("支付宝APP支付");
/* 564 */     payChannel.setState(Byte.valueOf((byte)1));
/* 565 */     JSONObject param = new JSONObject();
/* 566 */     param.put("IPWhiteList", mchPayChannelALIPAYBo.getIPWhiteList());
/* 567 */     param.put("appId", mchPayChannelALIPAYBo.getChannelMchId());
/* 568 */     if (mchPayChannelALIPAYBo.getCurrency().equals(CurrencyTypeEnum.CNY.name())) {
/* 569 */       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
/* 570 */     } else if (mchPayChannelALIPAYBo.getCurrency().equals(CurrencyTypeEnum.USD.name())) {
/* 571 */       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
/*     */     } else {
/* 573 */       throw new Exception("支付币种参数不支持");
/*     */     } 
/* 575 */     param.put("private_key", mchPayChannelALIPAYBo.getPrivateKey());
/* 576 */     param.put("alipay_public_key", mchPayChannelALIPAYBo.getAlipayPublicKey());
/* 577 */     param.put("isSandbox", Integer.valueOf(0));
/* 578 */     param.put("poundageRate", mchPayChannelALIPAYBo.getPoundageRate());
/* 579 */     payChannel.setParam(param.toJSONString());
/* 580 */     return payChannel;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/aliPayTest.html"}, method = {RequestMethod.GET})
/*     */   public String aliPayTest(MchPayChannelALIPAYBo mchPayChannelALIPAYBo, ModelMap model, HttpServletRequest request) throws Exception {
/* 585 */     PayChannel payChannel = getPayChannel(mchPayChannelALIPAYBo);
/* 586 */     return payOrderTest(payChannel, model, request);
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/wxPayTest.html"}, method = {RequestMethod.GET})
/*     */   public String wxPayTest(MchPayChannelWXBo mchPayChannelWXBo, ModelMap model, HttpServletRequest request) throws Exception {
/* 591 */     PayChannel payChannel = getPayChannel(mchPayChannelWXBo);
/* 592 */     return payOrderTest(payChannel, model, request); } private String payOrderTest(PayChannel payChannel, ModelMap model, HttpServletRequest request) {
/*     */     CommonResult<JSONObject> result;
/*     */     String payUrl, extra, clientIp;
/*     */     JSONObject jsonObject;
/* 596 */     PayOrder payOrder = new PayOrder();
/* 597 */     payOrder.setPayOrderId("test-" + System.currentTimeMillis());
/* 598 */     payOrder.setChannelId(payChannel.getChannelId());
/*     */ 
/*     */     
/* 601 */     payOrder.setAmount(Long.valueOf(1L));
/*     */     
/* 603 */     String payChannelName = payChannel.getChannelName();
/*     */     
/* 605 */     payOrder.setExpireTime(DateUtils.getAfterMinuteDate(30));
/* 606 */     switch (payChannelName) {
/*     */       case "ALIPAY":
/* 608 */         payChannel.setChannelId("ALIPAY_PC");
/* 609 */         payOrder.setSubject("支付宝《PC网页》支付参数测试");
/* 610 */         payOrder.setBody("请使用支付宝扫描二维码进行支付，如果支付成功，表示参数正确无误。");
/* 611 */         result = this.payChannel4AliService.doAliPayPcReq(payOrder, payChannel);
/* 612 */         payUrl = (String)((JSONObject)result.getData()).get("payUrl");
/* 613 */         if (StringUtils.isNotEmpty(payUrl)) {
/* 614 */           model.put("payUrl", payUrl);
/* 615 */           return "toAliPay";
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 648 */         return "下单失败:不支持的支付渠道";case "WX": payChannel.setChannelId("WX_NATIVE"); payOrder.setSubject("微信《扫码》支付参数测试"); payOrder.setBody("请使用微信扫描二维码进行支付，如果支付成功，表示参数正确无误。"); extra = "{\n  \"productId\": \"test\",\n  \"openId\": \"\",\n  \"sceneInfo\": {\n    \"h5_info\": {\n      \"type\": \"Wap\",\n      \"wap_url\": \"https://www.wljiam.com\",\n      \"wap_name\": \"物连家美\"\n    }\n  }\n}"; payOrder.setExtra(extra); clientIp = IPUtility.getIpAddr(request); payOrder.setClientIp(clientIp); jsonObject = this.payChannel4WxService.doWxPayReq("NATIVE", payOrder, payChannel); payUrl = (String)jsonObject.get("codeUrl"); if (StringUtils.isNotEmpty(payUrl)) { model.put("codeUrl", payUrl); return "toAppPay"; }  return "下单失败:不支持的支付渠道";
/*     */     } 
/*     */     return "不支持的支付渠道";
/*     */   }
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用：变更支付渠道状态", notes = "变更支付渠道状态")
/*     */   public CommonResult updatePayChannelState(@RequestBody PayChannelStateBo payChannelStateBo, HttpServletRequest request) throws Exception {
/* 656 */     this.securityAccessManager.checkAccessKey(payChannelStateBo.getAccessKey(), payChannelStateBo.getOperator(), payChannelStateBo.getOperatorOrgId(), request);
/*     */     
/* 658 */     PayChannel payChannel = this.payChannelService.selectPayChannel(payChannelStateBo.getId().intValue());
/* 659 */     PayChannelBo payChannelBo = (PayChannelBo)BeanUtil.copyProperties(payChannel, PayChannelBo.class);
/* 660 */     payChannelBo.setOperatorOrgId(payChannelStateBo.getOperatorOrgId());
/* 661 */     payChannelBo.setOperator(payChannelStateBo.getOperator());
/* 662 */     payChannelBo.setOperatorId(payChannelStateBo.getOperatorId());
/* 663 */     payChannelBo.setState(payChannelStateBo.getState());
/* 664 */     MchInfo mchInfo = this.mchInfoService.selectMchInfo(payChannel.getMchId());
/* 665 */     return this.platformService.savePayChannel(payChannelBo, mchInfo, request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：审核支付渠道审核信息", notes = "审核的支付渠道审核信息")
/*     */   public CommonResult auditPlatformPayChannel(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 673 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 674 */       return CommonResult.error("非授权IP");
/*     */     }
/* 676 */     PayChannelAuditBo payChannelAuditBo = new PayChannelAuditBo();
/* 677 */     payChannelAuditBo.setId(Integer.valueOf(Integer.parseInt(syncAuditStatusBo.getBizId())));
/* 678 */     payChannelAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 679 */     Integer id = payChannelAuditBo.getId();
/*     */     
/* 681 */     _log.info("审核支付渠道信息：" + JSONObject.toJSONString(syncAuditStatusBo), new Object[0]);
/* 682 */     PayChannel payChannel = this.payChannelService.selectByPrimaryKeyForAudit(id);
/* 683 */     JSONObject jsonObject = JSONObject.parseObject(payChannel.getParam());
/* 684 */     Integer poundageRate = jsonObject.getInteger("poundageRate");
/* 685 */     MchInfo platform = this.mchInfoService.createPlatform();
/* 686 */     platform.setSettlePoundageRate(payChannel.getChannelName(), poundageRate);
/* 687 */     int result = this.mchInfoService.updateMchInfo(platform);
/* 688 */     if (result == 0) {
/* 689 */       return CommonResult.error("审核失败，平台费率更新失败");
/*     */     }
/* 691 */     if (payChannel == null) {
/* 692 */       return CommonResult.error("待审核支付渠道不存在");
/*     */     }
/* 694 */     if (payChannelAuditBo.getAudit().intValue() == 0) {
/* 695 */       payChannel.setState(Byte.valueOf((byte)(payChannel.getState().byteValue() * 10)));
/* 696 */       this.payChannelService.updatePayChannelForAudit(payChannel);
/* 697 */       return CommonResult.success("操作成功");
/*     */     } 
/*     */     
/* 700 */     PayChannel payChannelAudit = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
/* 701 */     PayChannel activePayChannel = this.payChannelService.selectPayChannel(payChannel.getChannelId(), "1");
/* 702 */     if (activePayChannel != null) {
/* 703 */       payChannelAudit.setId(activePayChannel.getId());
/*     */     }
/*     */     
/* 706 */     payChannelAudit.setState(Byte.valueOf((byte)(payChannelAudit.getState().byteValue() % 10)));
/* 707 */     List<PayChannel> list = new ArrayList<>();
/* 708 */     list.add(payChannelAudit);
/* 709 */     result = this.payChannelService.listInsert(list);
/* 710 */     if (result > 0) {
/* 711 */       payChannel.setState(Byte.valueOf((byte)(payChannel.getState().byteValue() * 10 + 1)));
/* 712 */       this.payChannelService.updatePayChannelForAudit(payChannel);
/*     */       
/* 714 */       int runMode = this.runModeService.getRunModeCode();
/* 715 */       if (runMode == RunModeEnum.PUBLIC.getCode().intValue()) {
/* 716 */         this.mq4MchNotify.send("queue.notify.platform.config.update", "syncConfig-pc", 
/*     */             
/* 718 */             DateUtils.getBetweenValueInMilSeconds(DateUtils.getDaysDurationFirstDate(1), new Date()).longValue());
/*     */       }
/* 720 */       return CommonResult.success("操作成功");
/*     */     } 
/* 722 */     return CommonResult.success("审核失败");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用：创建平台内部支付渠道", notes = "创建平台内部支付渠道：默认只能配置给平台商户")
/*     */   public CommonResult<Integer> createPlatformInnerChannel(@RequestBody PayChannelBo payChannelBo, HttpServletRequest request) throws Exception {
/* 731 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 732 */       return CommonResult.error("非授权IP");
/*     */     }
/* 734 */     MchInfo platform = this.mchInfoService.createPlatform();
/* 735 */     payChannelBo.setMchId(platform.getMchId());
/*     */     try {
/* 737 */       return this.payChannelService.createPlatformChannel(payChannelBo);
/* 738 */     } catch (Exception e) {
/* 739 */       e.printStackTrace();
/* 740 */       return CommonResult.error("内部错误");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\PlatformController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */