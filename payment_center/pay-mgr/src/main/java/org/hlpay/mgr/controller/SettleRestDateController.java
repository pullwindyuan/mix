/*     */ package org.hlpay.mgr.controller;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import io.swagger.annotations.Api;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.hlpay.base.bo.CommonBo;
/*     */ import org.hlpay.base.bo.GetSettleRestDateBo;
/*     */ import org.hlpay.base.bo.RestDateAuditBo;
/*     */ import org.hlpay.base.bo.RestDateBo;
/*     */ import org.hlpay.base.bo.SyncAuditStatusBo;
/*     */ import org.hlpay.base.bo.WorkFlowAuditAddBo;
/*     */ import org.hlpay.base.mapper.RestDateDao;
import org.hlpay.base.model.RestDate;
/*     */ import org.hlpay.base.model.RestDateExample;
/*     */ import org.hlpay.base.payFeign.WorkflowAudit;
import org.hlpay.base.plugin.PageModel;
/*     */ import org.hlpay.base.security.SecurityAccessManager;
import org.hlpay.base.vo.RestDateVo;
/*     */ import org.hlpay.common.entity.CommonResult;
/*     */ import org.hlpay.common.util.BeanUtil;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Api(value = "节假日设置", tags = {"节假日设置"})
/*     */ @Controller
/*     */ @RequestMapping({"/SettleRest"})
/*     */ public class SettleRestDateController {
/*     */   @Autowired
/*     */   private SecurityAccessManager securityAccessManager;
/*     */   @Resource
/*     */   private Environment env;
/*     */   
/*     */   @ApiOperation(value = "私有云专用:获取结算节假日列表", notes = "获取结算节假日列表")
/*     */   @RequestMapping(value = {"/list"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<RestDateVo>> list(@RequestBody GetSettleRestDateBo getSettleRestDateBo, HttpServletRequest request) throws Exception {
/*  43 */     this.securityAccessManager.checkAccessKey(getSettleRestDateBo.getAccessKey(), getSettleRestDateBo.getOperator(), getSettleRestDateBo.getOperatorOrgId(), request);
/*  44 */     PageModel<RestDateVo> pageModel = new PageModel();
/*  45 */     RestDateExample example = new RestDateExample();
/*  46 */     example.setOffset(Long.valueOf(((getSettleRestDateBo.getPageIndex().intValue() - 1) * getSettleRestDateBo.getPageSize().intValue())));
/*  47 */     example.setLimit(getSettleRestDateBo.getPageSize());
/*  48 */     long count = this.restDateDao.countByExample(example);
/*  49 */     List<RestDate> restDate = this.restDateDao.selectByExample(example);
/*  50 */     List<RestDateVo> restDateVoList = BeanUtil.copyProperties(restDate, RestDateVo.class);
/*  51 */     pageModel.setList(restDateVoList);
/*  52 */     pageModel.setCount(Integer.valueOf((int)count));
/*  53 */     pageModel.setMsg("ok");
/*  54 */     pageModel.setRel(Boolean.valueOf(true));
/*  55 */     return CommonResult.success((Serializable)pageModel);
/*     */   } @Autowired
/*     */   private WorkflowAudit workflowAudit; @Autowired
/*     */   private RestDateDao restDateDao; @ApiOperation(value = "私有云专用-后台管理前端调用:获取单个节假日信息", notes = "获取单个节假日信息")
/*     */   @RequestMapping(value = {"/item"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<RestDateVo> item(@RequestBody CommonBo commonBo, HttpServletRequest request) throws Exception {
/*  62 */     this.securityAccessManager.checkAccessKey(commonBo.getAccessKey(), commonBo.getOperator(), commonBo.getOperatorOrgId(), request);
/*  63 */     return CommonResult.success((Serializable)BeanUtil.copyProperties(this.restDateDao.selectByPrimaryKey(commonBo.getId()), RestDateVo.class));
/*     */   }
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:保存节假日信息", notes = "保存节假日信息")
/*     */   @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   @Transactional(rollbackFor = {Exception.class})
/*     */   public CommonResult save(@RequestBody RestDateBo restDateBo, HttpServletRequest request) throws Exception {
/*     */     int result;
/*  71 */     this.securityAccessManager.checkAccessKey(restDateBo.getAccessKey(), restDateBo.getOperator(), restDateBo.getOperatorOrgId(), request);
/*     */     
/*  73 */     RestDate restDate = (RestDate)BeanUtil.copyProperties(restDateBo, RestDate.class);
/*  74 */     restDate.setState(Integer.valueOf(0));
/*  75 */     WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
/*  76 */     if (restDateBo.getId() == null) {
/*  77 */       result = this.restDateDao.insertSelective(restDate);
/*  78 */       workFlowAuditAddBo.setSyncAuditDataType("ADD");
/*     */     } else {
/*  80 */       result = this.restDateDao.updateByPrimaryKeySelective(restDate);
/*  81 */       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");
/*     */     } 
/*  83 */     if (result > 0) {
/*  84 */       RestDateExample restDateExample = new RestDateExample();
/*  85 */       RestDateExample.Criteria criteria = restDateExample.createCriteria();
/*  86 */       criteria.andStartDateEqualTo(restDate.getStartDate());
/*  87 */       criteria.andEndDateEqualTo(restDate.getEndDate());
/*     */       
/*  89 */       List<RestDate> restDateList = this.restDateDao.selectByExample(restDateExample);
/*     */ 
/*     */       
/*  92 */       workFlowAuditAddBo.setAuthor(restDateBo.getOperatorId());
/*  93 */       workFlowAuditAddBo.setAuthorName(restDateBo.getOperator());
/*  94 */       workFlowAuditAddBo.setBizId(Long.toString(((RestDate)restDateList.get(0)).getId().longValue()));
/*  95 */       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(restDateList.get(0)));
/*  96 */       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
/*  97 */       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(restDateBo.getOperatorOrgId())));
/*  98 */       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
/*  99 */       workFlowAuditAddBo.setServletPath("/SettleRest/audit");
/*     */       
/* 101 */       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
/* 102 */       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
/* 103 */         return CommonResult.success("保存成功");
/*     */       }
/* 105 */       throw new Exception("工作流同步失败");
/*     */     } 
/*     */     
/* 108 */     return CommonResult.error("保存失败");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "私有云专用:审核节假日信息", notes = "审核节假日信息")
/*     */   @RequestMapping(value = {"/audit"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult audit(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) throws Exception {
/* 119 */     RestDateAuditBo restDateAuditBo = new RestDateAuditBo();
/* 120 */     restDateAuditBo.setId(Long.valueOf(Long.parseLong(syncAuditStatusBo.getBizId())));
/* 121 */     restDateAuditBo.setAudit(Integer.valueOf((syncAuditStatusBo.getAuditStatus().intValue() == 30) ? 1 : 0));
/* 122 */     RestDate restDate = (RestDate)this.restDateDao.selectByPrimaryKey(restDateAuditBo.getId());
/* 123 */     if (restDate == null) {
/* 124 */       return CommonResult.error("找不到该信息");
/*     */     }
/* 126 */     restDate.setState(restDateAuditBo.getAudit());
/* 127 */     int result = this.restDateDao.updateByPrimaryKey(restDate);
/* 128 */     if (result > 0) {
/* 129 */       return CommonResult.success("审核成功");
/*     */     }
/* 131 */     return CommonResult.error("审核失败");
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\SettleRestDateController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */