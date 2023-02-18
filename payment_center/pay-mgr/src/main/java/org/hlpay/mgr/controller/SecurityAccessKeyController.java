/*    */ package org.hlpay.mgr.controller;
/*    */ 
/*    */ import io.swagger.annotations.Api;
/*    */ import io.swagger.annotations.ApiOperation;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.hlpay.base.bo.AuthGenAccessKeyBo;
/*    */ import org.hlpay.base.bo.AuthPrivateKeyBo;
/*    */ import org.hlpay.base.security.SecurityAccessManager;
/*    */ import org.hlpay.base.vo.AuthPrivateKeyVo;
/*    */ import org.hlpay.common.entity.CommonResult;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Api(value = "获取操作秘钥", tags = {"获取操作秘钥"})
/*    */ @Controller
/*    */ @RequestMapping({"/security"})
/*    */ public class SecurityAccessKeyController
/*    */ {
/*    */   @Autowired
/*    */   private SecurityAccessManager securityAccessManager;
/*    */   
/*    */   @ApiOperation(value = "获取访问秘钥", notes = "获取访问秘钥接口：本接口只对后台服务开放，客户端不能直接调用。传入参数为JSON格式封装的params参数集合，集合内字段定义如下\n \t/**\n\t * 商户ID\n\t */\n\t@ApiModelProperty(value = \"商户ID\", required = true)\n\tprivate Long mchId;\n\t/**\n\t * 用户编号\n\t */\n\t@ApiModelProperty(value = \"用户编号\", required = true)\n\tprivate Long userNo;\n\t/**\n\t * 业务编号\n\t */\n\t@ApiModelProperty(value = \"业务编号\", required = true)\n\tprivate Long bizId;\n\t/**\n\t * MD5签名\n\t */\n\t@ApiModelProperty(value = \"MD5签名：通过随mchId一起分配的reqKey来对params参数进行签名，防止被篡改\", required = true)\n\tprivate String sign;\t * ip地址\n\t */\n\t@ApiModelProperty(value = \"IP：客户端IP地址, required = true)\n\tprivate String ip;")
/*    */   @RequestMapping(value = {"/private"}, method = {RequestMethod.POST})
/*    */   @ResponseBody
/*    */   public CommonResult<AuthPrivateKeyVo> getPrivateKey(@RequestBody AuthPrivateKeyBo authPrivateKeyBO, HttpServletRequest request) throws Exception {
/* 55 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 56 */       return CommonResult.error("非授权IP");
/*    */     }
/* 58 */     return this.securityAccessManager.genCustomerPrivateKey(authPrivateKeyBO);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ApiOperation(value = "获取操作秘钥", notes = "获取操作秘钥接口：传入参数为JSON格式封装的params参数集合，集合内字段定义如下\n \t/**\n\t/**\n\t * api接口访问编号\n\t */\n\t@ApiModelProperty(value = \"api接口访问编号：由本系统审核通过后分配\", required = true)\n\tprivate String appid;\n\t/**\n\t * 被授权用户编号\n\t */\n\t@ApiModelProperty(value = \"被授权用户编号\", required = true)\n\tprivate Long userNo;\n\t/**\n\t * 所属组织ID\n\t */\n\t@ApiModelProperty(value = \"所属组织ID\", required = true)\n\tprivate Long orgId;\n\t/**\n\t * MD5签名\n\t */\n\t@ApiModelProperty(value = \"MD5签名：通过随appid一起分配的accesskey来对params参数进行签名，防止被篡改\", required = true)\n\tprivate String sign;\n\t/**\n\t * ip地址\n\t */\n\t@ApiModelProperty(value = \"IP：客户端IP地址, required = true)\n\tprivate String ip;")
/*    */   @RequestMapping(value = {"/genPlatformAccessKey"}, method = {RequestMethod.POST})
/*    */   @ResponseBody
/*    */   public CommonResult<AuthPrivateKeyVo> genPlatformAccessKey(@RequestBody AuthGenAccessKeyBo authCourseGenPrivateKeyBO, HttpServletRequest request) throws Exception {
/* 93 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 94 */       return CommonResult.error("非授权IP");
/*    */     }
/* 96 */     return this.securityAccessManager.genSuperPrivateKey(authCourseGenPrivateKeyBO);
/*    */   }
/*    */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\SecurityAccessKeyController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */