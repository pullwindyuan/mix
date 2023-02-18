/*     */ package org.hlpay.mgr.controller;
/*     */ 
/*     */ import com.alibaba.fastjson.JSONArray;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import io.swagger.annotations.Api;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.hlpay.base.bo.CashSettleBo;
/*     */ import org.hlpay.base.bo.CheckSumPayOrderBo;
/*     */ import org.hlpay.base.bo.ConfirmAgencySettleBo;
/*     */ import org.hlpay.base.bo.DoWithdrawBo;
/*     */ import org.hlpay.base.bo.FixSettleBo;
/*     */ import org.hlpay.base.bo.GetMonthSettleListBo;
/*     */ import org.hlpay.base.bo.GetMonthSettleListByMchInfoBo;
/*     */ import org.hlpay.base.bo.GetSettleDetailListBo;
/*     */ import org.hlpay.base.bo.GetSettleListBo;
/*     */ import org.hlpay.base.bo.GetSettlePayOrderDetailListBo;
/*     */ import org.hlpay.base.bo.GetSettleTodoListBo;
/*     */ import org.hlpay.base.bo.SyncAuditStatusBo;
/*     */ import org.hlpay.base.plugin.PageModel;
/*     */ import org.hlpay.base.vo.SettleCardVo;
/*     */ import org.hlpay.base.vo.SettlePayOrderVo;
/*     */ import org.hlpay.common.entity.CommonResult;
/*     */ import org.hlpay.mgr.sevice.MchSettleService;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
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
/*     */ @Api(value = "后台管理用的对账接口", tags = {"后台管理用的对账接口"})
/*     */ @Controller
/*     */ @RequestMapping({"/mch_settle"})
/*     */ public class MchSettleController
/*     */ {
/*     */   @Autowired
/*     */   private MchSettleService mchSettleService;
/*     */   
/*     */   @ApiOperation(value = "私有云专用:测试渠道支付总额统计", notes = "测试渠道支付总额统计")
/*     */   @RequestMapping(value = {"/getSum"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<JSONArray> getSum(@RequestBody CheckSumPayOrderBo checkSumPayOrderBo, HttpServletRequest request) {
/*     */     try {
/*  59 */       return this.mchSettleService.getSum(checkSumPayOrderBo, request);
/*  60 */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-公域持有: 现金结算完成", notes = "财务t+n的时候进行打款操作完成后调用")
/*     */   @RequestMapping(value = {"/settleCashSettleCompleted"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult settleCashSettleCompleted(@RequestBody CashSettleBo cashSettleBo, HttpServletRequest request) {
/*     */     try {
/*  71 */       return this.mchSettleService.settleCashSettleCompleted(cashSettleBo, request);
/*  72 */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*  74 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-私域持有: 现金结算完成后接收来自公域的回调）", notes = "财务t+n的时候进行打款操作完成后调用")
/*     */   @RequestMapping(value = {"/settleCashSettleCompletedCallback"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult settleCashSettleCompletedCallback(@RequestParam("settleCardNumber") String settleCardNumber, HttpServletRequest request) {
/*     */     try {
/*  83 */       return this.mchSettleService.settleCashSettleCompletedCallback(settleCardNumber, request);
/*  84 */     } catch (Exception e) {
/*  85 */       e.printStackTrace();
/*  86 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:获取现金结算列表，也就是已经进入打款环节的结算单（结算确认后满足T+n）", notes = "获取现金结算列表，也就是已经进入打款环节的结算单（结算确认后满足T+n）")
/*     */   @RequestMapping(value = {"/getSettleWithdrawTodoList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<ArrayList<SettleCardVo>> getSettleWithdrawTodoList(@RequestBody GetSettleTodoListBo getSettleTodoListBo, HttpServletRequest request) {
/*     */     try {
/*  95 */       return this.mchSettleService.getSettleWithdrawTodoList(getSettleTodoListBo, request);
/*  96 */     } catch (Exception e) {
/*  97 */       e.printStackTrace();
/*  98 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:根据商户信息获取结算月报表", notes = "根据商户信息获取结算月报表")
/*     */   @RequestMapping(value = {"/getMonthSettleListByMchInfo"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<JSONArray> getMonthSettleListByMchInfo(@RequestBody GetMonthSettleListByMchInfoBo getMonthSettleListByMchInfoBo, HttpServletRequest request) {
/*     */     try {
/* 107 */       return this.mchSettleService.getMonthSettleListByMchInfo(getMonthSettleListByMchInfoBo, request);
/* 108 */     } catch (Exception e) {
/* 109 */       e.printStackTrace();
/* 110 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:获取结算月报表", notes = "获取结算月报表")
/*     */   @RequestMapping(value = {"/getMonthSettleList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<JSONObject> getMonthSettleList(@RequestBody GetMonthSettleListBo getMonthSettleListBo, HttpServletRequest request) {
/*     */     try {
/* 119 */       return this.mchSettleService.getMonthSettleList(getMonthSettleListBo, request);
/* 120 */     } catch (Exception e) {
/* 121 */       e.printStackTrace();
/* 122 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:根据商户ID获取结算列表", notes = "根据商户ID获取结算列表:mchId（组织机构ID），status（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/getSettleList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<SettleCardVo>> getSettleList(@RequestBody GetSettleListBo getSettleListBo, HttpServletRequest request) {
/*     */     try {
/* 131 */       return this.mchSettleService.getSettleList(getSettleListBo);
/* 132 */     } catch (Exception e) {
/* 133 */       e.printStackTrace();
/* 134 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:根据结算卡ID获取结算详情列表", notes = "根据结算卡ID获取结算详情列表:mchId（组织机构ID），status（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/getSettleDetailList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<SettleCardVo>> getSettleDetailList(@RequestBody GetSettleDetailListBo getSettleDetailListBo, HttpServletRequest request) {
/*     */     try {
/* 143 */       return this.mchSettleService.getSettleDetailListV1(getSettleDetailListBo, request);
/* 144 */     } catch (Exception e) {
/* 145 */       e.printStackTrace();
/* 146 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "报表导出-私有云专用-后台管理前端调用:导出对账单", notes = "报表导出-根据商户ID获取结算列表:mchId（组织机构ID），status（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/exportSettleList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Object exportSettleList(@RequestBody GetSettleListBo getSettleListBo, HttpServletRequest request) {
/*     */     try {
/* 155 */       return this.mchSettleService.exportSettleList(getSettleListBo, request);
/* 156 */     } catch (Exception e) {
/* 157 */       e.printStackTrace();
/* 158 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "报表导出-私有云专用-后台管理前端调用:根据结算卡ID导出对账详情", notes = "根据结算卡ID获取结算详情列表:mchId（组织机构ID），status（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/exportSettleDetailList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Object exportSettleDetailList(@RequestBody GetSettleDetailListBo getSettleDetailListBo, HttpServletRequest request) {
/*     */     try {
/* 167 */       return this.mchSettleService.exportSettleDetailList(getSettleDetailListBo, request);
/* 168 */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/* 170 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "导出报表：私有云专用-后台管理前端调用:获取现金结算列表，也就是已经进入打款环节的结算单（结算确认后满足T+n）", notes = "导出现金结算列表，也就是已经进入打款环节的结算单（结算确认后满足T+n）")
/*     */   @RequestMapping(value = {"/exportSettleWithdrawTodoList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Object exportSettleWithdrawTodoList(@RequestBody GetSettleTodoListBo getSettleTodoListBo, HttpServletRequest request) {
/*     */     try {
/* 179 */       return this.mchSettleService.exportCashTodoList(getSettleTodoListBo, request);
/* 180 */     } catch (Exception e) {
/* 181 */       e.printStackTrace();
/* 182 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "根据商户ID和结算卡号获取对应的结算订单详情列表信息", notes = "根据商户ID和结算卡号获取对应的结算订单详情列表信息:extra（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/getSettlePayOrderList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<List<SettlePayOrderVo>>> getSettlePayOrderList(@RequestBody GetSettlePayOrderDetailListBo getSettlePayOrderDetailListBo, HttpServletRequest request) {
/*     */     try {
/* 191 */       return this.mchSettleService.getSettlePayOrderList(getSettlePayOrderDetailListBo, request);
/* 192 */     } catch (Exception e) {
/* 193 */       e.printStackTrace();
/* 194 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "根据商户ID和结算卡号导出订单详情", notes = "根据商户ID和结算卡号获取对应的结算订单详情列表信息:extra（对账状态：0-记账中；1-待确认；2-已确认；3-已结算；99-全部）")
/*     */   @RequestMapping(value = {"/exportSettlePayOrderList"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Object exportSettlePayOrderList(@RequestBody GetSettlePayOrderDetailListBo getSettlePayOrderDetailListBo, HttpServletRequest request) {
/*     */     try {
/* 203 */       return this.mchSettleService.exportSettlePayOrderList(getSettlePayOrderDetailListBo, request);
/* 204 */     } catch (Exception e) {
/* 205 */       e.printStackTrace();
/* 206 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:确认结算单", notes = "确认结算单")
/*     */   @RequestMapping(value = {"/confirmSettle"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult confirmSettle(@RequestParam("fromSettleCardNo") String fromSettleCardNo, HttpServletRequest request) {
/*     */     try {
/* 215 */       return this.mchSettleService.confirmSettle(fromSettleCardNo, request);
/* 216 */     } catch (Exception e) {
/* 217 */       e.printStackTrace();
/* 218 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:核对并纠正结算单", notes = "根据结算卡核对并纠正结算单")
/*     */   @RequestMapping(value = {"/tryToFixSettle"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult tryToFixSettle(@RequestParam("fromSettleCardNo") String fromSettleCardNo, HttpServletRequest request) {
/*     */     try {
/* 227 */       return this.mchSettleService.tryToFixSettle(fromSettleCardNo, request);
/* 228 */     } catch (Exception e) {
/* 229 */       e.printStackTrace();
/* 230 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:物连家美平台核对并纠正结算单", notes = "物连家美平台核根据商户号核对并纠正结算单")
/*     */   @RequestMapping(value = {"/tryToFixSettleByMchIdInPlatform"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult tryToFixSettleByMchIdInPlatform(@RequestBody FixSettleBo fixSettleBo, HttpServletRequest request) {
/*     */     try {
/* 239 */       return this.mchSettleService.tryToFixSettleByMchIdInPlatform(fixSettleBo, request);
/* 240 */     } catch (Exception e) {
/* 241 */       e.printStackTrace();
/* 242 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:结算提取申请", notes = "结算提取申请")
/*     */   @RequestMapping(value = {"/settleWithdraw"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult settleWithdraw(@RequestParam("settleCardNo") String settleCardNo, HttpServletRequest request) {
/*     */     try {
/* 251 */       return this.mchSettleService.settleWithdraw(settleCardNo, request);
/* 252 */     } catch (Exception e) {
/* 253 */       e.printStackTrace();
/* 254 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用:执行结算提取", notes = "执行结算提取")
/*     */   @RequestMapping(value = {"/doSettleWithdraw"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult doSettleWithdraw(@RequestBody DoWithdrawBo doWithdrawBo, HttpServletRequest request) {
/*     */     try {
/* 263 */       return this.mchSettleService.doSettleWithdraw(doWithdrawBo.getSettleTransOrderNo(), request);
/* 264 */     } catch (Exception e) {
/* 265 */       e.printStackTrace();
/* 266 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用:公域接收来自私域的结算请求", notes = "执行结算提取")
/*     */   @RequestMapping(value = {"/syncSettleWithdrawToPlatform"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult syncSettleWithdrawToPlatform(@RequestBody String params, HttpServletRequest request) {
/*     */     try {
/* 275 */       return this.mchSettleService.syncSettleWithdrawToPlatform(params, request);
/* 276 */     } catch (Exception e) {
/* 277 */       e.printStackTrace();
/* 278 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:平台确认结算单", notes = "平台确认结算单")
/*     */   @RequestMapping(value = {"/platformConfirmSettle"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult platformConfirmSettle(@RequestBody ConfirmAgencySettleBo confirmAgencySettleBo, HttpServletRequest request) {
/*     */     try {
/* 287 */       return this.mchSettleService.platformConfirmSettle(confirmAgencySettleBo, request);
/* 288 */     } catch (Exception e) {
/* 289 */       e.printStackTrace();
/* 290 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用-后台管理前端调用:平台审核结算单", notes = "平台审核结算单")
/*     */   @RequestMapping(value = {"/auditPlatformConfirmSettle"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult auditPlatformConfirmSettle(@RequestBody SyncAuditStatusBo syncAuditStatusBo, HttpServletRequest request) {
/*     */     try {
/* 299 */       return this.mchSettleService.auditPlatformConfirmSettle(syncAuditStatusBo, request);
/* 300 */     } catch (Exception e) {
/* 301 */       e.printStackTrace();
/* 302 */       return CommonResult.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\MchSettleController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */