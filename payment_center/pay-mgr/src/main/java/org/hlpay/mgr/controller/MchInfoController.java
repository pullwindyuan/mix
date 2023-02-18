/*     */ package org.hlpay.mgr.controller;
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import com.alibaba.fastjson.JSONArray;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import io.swagger.annotations.Api;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.hlpay.base.bo.ExternalMchInfoBo;
/*     */ import org.hlpay.base.bo.MchInfoBo;
/*     */ import org.hlpay.base.model.MchInfo;
/*     */ import org.hlpay.base.model.PayChannel;
/*     */ import org.hlpay.base.plugin.PageModel;
/*     */ import org.hlpay.base.security.SecurityAccessManager;
/*     */ import org.hlpay.base.service.MchInfoService;
/*     */ import org.hlpay.base.service.PayChannelService;
/*     */ import org.hlpay.base.vo.MchInfoForAppVo;
/*     */ import org.hlpay.base.vo.MchInfoForConfigVo;
/*     */ import org.hlpay.base.vo.PayChannelForAppVo;
/*     */ import org.hlpay.base.vo.PayChannelForConfigVo;
/*     */ import org.hlpay.common.entity.CommonResult;
/*     */ import org.hlpay.common.entity.Result;
/*     */ import org.hlpay.common.entity.SimpleResult;
/*     */ import org.hlpay.common.util.BeanUtil;
/*     */ import org.hlpay.common.util.DateUtil;
/*     */ import org.hlpay.common.util.MyLog;
/*     */ import org.hlpay.common.util.RandomStrUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.util.CollectionUtils;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Api(value = "商户信息维护", tags = {"商户信息维护"})
/*     */ @Controller
/*     */ @RequestMapping({"/mch_info"})
/*     */ public class MchInfoController {
/*  43 */   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.controller.MchInfoController.class);
/*     */   
/*     */   @Autowired
/*     */   private MchInfoService mchInfoService;
/*     */   
/*     */   @Autowired
/*     */   private PayChannelService payChannelService;
/*     */   
/*     */   @Autowired
/*     */   private SecurityAccessManager securityAccessManager;
/*     */   
/*     */   @ApiOperation(value = "通用:根据商户信息查询条件获取分页商户列表", notes = "根据条件查询商户信息列表")
/*     */   @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public PageModel list(@ModelAttribute MchInfo mchInfo, Integer pageIndex, Integer pageSize) {
/*  58 */     PageModel pageModel = new PageModel();
/*  59 */     int count = this.mchInfoService.count(mchInfo).intValue();
/*  60 */     List<MchInfo> mchInfoList = this.mchInfoService.getMchInfoList((pageIndex.intValue() - 1) * pageSize.intValue(), pageSize.intValue(), mchInfo);
/*  61 */     if (!CollectionUtils.isEmpty(mchInfoList)) {
/*  62 */       JSONArray array = new JSONArray();
/*  63 */       for (MchInfo mi : mchInfoList) {
/*  64 */         JSONObject object = (JSONObject)JSONObject.toJSON(mi);
/*  65 */         object.put("createTime", DateUtil.date2Str(mi.getCreateTime()));
/*  66 */         array.add(object);
/*     */       } 
/*  68 */       pageModel.setList((List)array);
/*     */     } 
/*  70 */     pageModel.setCount(Integer.valueOf(count));
/*  71 */     pageModel.setMsg("ok");
/*  72 */     pageModel.setRel(Boolean.valueOf(true));
/*  73 */     return pageModel;
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "通用:根据商户ID获取商户信息", notes = "根据商户ID获取商户信息")
/*     */   @RequestMapping(value = {"/item"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public SimpleResult item(@RequestParam String mchId) throws NoSuchMethodException {
/*  80 */     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
/*  81 */     SimpleResult result = new SimpleResult();
/*  82 */     result.setErroCode(Integer.valueOf(1));
/*  83 */     result.setData("商户信息获取失败");
/*  84 */     if (mchInfo != null) {
/*  85 */       result.setErroCode(Integer.valueOf(0));
/*  86 */       result.setData(mchInfo);
/*     */     } 
/*  88 */     return result;
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "通用:新增商户", notes = "新增商户")
/*     */   @RequestMapping(value = {"/add"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Integer add(@RequestBody MchInfoBo mchInfoBo, HttpServletRequest request) {
/*  95 */     MchInfo mchInfo = (MchInfo)BeanUtil.copyProperties(mchInfoBo, MchInfo.class);
/*  96 */     mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
/*  97 */     mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));
/*     */     
/*  99 */     int result = this.mchInfoService.addMchInfo(mchInfo);
/* 100 */     _log.info("保存商户记录,返回:{}", new Object[] { Integer.valueOf(result) });
/* 101 */     return Integer.valueOf(result);
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：根据父级商户ID获取全部商户信息列表", notes = "根据父级商户ID获取商户信息列表")
/*     */   @RequestMapping(value = {"/listByParentMchId"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<List<MchInfo>>> listByParentMchId(@RequestParam String parentMchId, Integer pageIndex, Integer pageSize, HttpServletRequest request) throws NoSuchMethodException {
/* 108 */     PageModel pageModel = new PageModel();
/* 109 */     MchInfo parent = this.mchInfoService.selectMchInfo(parentMchId);
/* 110 */     if (parent == null) {
/* 111 */       return CommonResult.success((Serializable)pageModel);
/*     */     }
/* 113 */     int count = this.mchInfoService.countByParentMchId(parentMchId).intValue();
/* 114 */     if (count <= 0) {
/* 115 */       return CommonResult.success((Serializable)pageModel);
/*     */     }
/* 117 */     List<MchInfo> mchInfoList = this.mchInfoService.getMchInfoListByParentMchId((pageIndex.intValue() - 1) * pageSize.intValue(), pageSize.intValue(), parentMchId);
/*     */     
/* 119 */     for (int i = 0; i < mchInfoList.size(); i++) {
/* 120 */       MchInfo temp = mchInfoList.get(i);
/* 121 */       if (StringUtils.isBlank(temp.getSettleParams())) {
/* 122 */         temp.setSettleParams(parent.getSettleParams());
/*     */       }
/* 124 */       if (StringUtils.isBlank(temp.getSettleType())) {
/* 125 */         temp.setSettleType(parent.getSettleType());
/*     */       }
/* 127 */       temp.setReqKey(null);
/* 128 */       temp.setResKey(null);
/*     */     } 
/* 130 */     if (!CollectionUtils.isEmpty(mchInfoList)) {
/* 131 */       JSONArray array = JSONArray.parseArray(JSON.toJSONString(mchInfoList));
/* 132 */       pageModel.setList((List)array);
/*     */     } 
/* 134 */     pageModel.setCount(Integer.valueOf(count));
/* 135 */     pageModel.setMsg("ok");
/* 136 */     pageModel.setRel(Boolean.valueOf(true));
/* 137 */     return CommonResult.success((Serializable)pageModel);
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：根据外部父级ID获取全部商户信息列表", notes = "根据外部父级ID获取商户信息列表")
/*     */   @RequestMapping(value = {"/listByParentExternalId"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<List<MchInfo>>> listByParentExternalId(@RequestParam String parentExternalId, String type, Integer pageIndex, Integer pageSize, HttpServletRequest request) throws NoSuchMethodException {
/* 144 */     PageModel pageModel = new PageModel();
/* 145 */     MchInfo parent = this.mchInfoService.getMchInfoByExternalIdAndType(parentExternalId, type);
/* 146 */     if (parent == null) {
/* 147 */       return CommonResult.success((Serializable)pageModel);
/*     */     }
/* 149 */     int count = this.mchInfoService.countByParentExternalId(parentExternalId).intValue();
/* 150 */     if (count <= 0) {
/* 151 */       return CommonResult.success((Serializable)pageModel);
/*     */     }
/* 153 */     List<MchInfo> mchInfoList = this.mchInfoService.getMchInfoListByParentExternalId((pageIndex.intValue() - 1) * pageSize.intValue(), pageSize.intValue(), parentExternalId);
/* 154 */     if (!CollectionUtils.isEmpty(mchInfoList)) {
/* 155 */       JSONArray array = new JSONArray();
/* 156 */       for (MchInfo mi : mchInfoList) {
/* 157 */         JSONObject object = (JSONObject)JSONObject.toJSON(mi);
/* 158 */         object.put("createTime", DateUtil.date2Str(mi.getCreateTime()));
/* 159 */         array.add(object);
/*     */       } 
/* 161 */       pageModel.setList((List)array);
/*     */     } 
/* 163 */     pageModel.setCount(Integer.valueOf(count));
/* 164 */     pageModel.setMsg("ok");
/* 165 */     pageModel.setRel(Boolean.valueOf(true));
/* 166 */     return CommonResult.success((Serializable)pageModel);
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：获取商户信息", notes = "获取商户信息（包括支付渠道信息）")
/*     */   @RequestMapping(value = {"/getMchInfo"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<MchInfoForAppVo> getMchInfo(@RequestBody MchInfoBo mchInfoBo, HttpServletRequest request) throws Exception {
/* 173 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 174 */       return CommonResult.error("非授权IP");
/*     */     }
/*     */     
/* 177 */     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(mchInfoBo.getExternalId(), mchInfoBo.getType());
/* 178 */     if (mchInfo == null) {
/* 179 */       List<MchInfo> mchInfoList = this.mchInfoService.tryToAddAllExternalMchInfo(mchInfoBo.getExternalId());
/* 180 */       if (mchInfoList == null) {
/* 181 */         return CommonResult.error("该商户信息不存在, 补偿失败");
/*     */       }
/* 183 */       MchInfoForAppVo mchInfoForAppVo = (MchInfoForAppVo)BeanUtil.copyProperties(mchInfoList.get(0), MchInfoForAppVo.class);
/* 184 */       mchInfo = (MchInfo)BeanUtil.copyProperties(mchInfoForAppVo, MchInfo.class);
/* 185 */       mchInfoForAppVo.setSettleParams(this.mchInfoService.getSettleParamsIfNullUseParentParams(mchInfo));
/* 186 */       List<PayChannel> list = this.payChannelService.getPayChannelListByMchId(mchInfo.getMchId());
/* 187 */       List<PayChannelForAppVo> list1 = BeanUtil.copyProperties(list, PayChannelForAppVo.class);
/* 188 */       mchInfoForAppVo.setPayChannels(list1);
/* 189 */       return CommonResult.success((Serializable)mchInfoForAppVo);
/*     */     } 
/*     */ 
/*     */     
/* 193 */     MchInfoForAppVo mchInfoVo = (MchInfoForAppVo)BeanUtil.copyProperties(mchInfo, MchInfoForAppVo.class);
/* 194 */     mchInfoVo.setSettleParams(this.mchInfoService.getSettleParamsIfNullUseParentParams(mchInfo));
/* 195 */     List<PayChannel> payChannels = this.payChannelService.getPayChannelListByMchId(mchInfo.getMchId());
/* 196 */     List<PayChannelForAppVo> listPayChannel = BeanUtil.copyProperties(payChannels, PayChannelForAppVo.class);
/* 197 */     mchInfoVo.setPayChannels(listPayChannel);
/* 198 */     return CommonResult.success((Serializable)mchInfoVo);
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：内部获取商户信息", notes = "内部获取商户信息（包括支付渠道信息）")
/*     */   @RequestMapping(value = {"/getMchInfoInSafeIP"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<MchInfoForConfigVo> getMchInfoInSafeIP(@RequestBody MchInfoBo mchInfoBo, HttpServletRequest request) throws Exception {
/* 205 */     if (!this.securityAccessManager.checkAccessLocalIP(request)) {
/* 206 */       return CommonResult.error("非授权IP");
/*     */     }
/*     */     
/* 209 */     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(mchInfoBo.getExternalId(), mchInfoBo.getType());
/* 210 */     if (mchInfo == null) {
/* 211 */       List<MchInfo> mchInfoList = this.mchInfoService.tryToAddAllExternalMchInfo(mchInfoBo.getExternalId());
/* 212 */       if (mchInfoList == null) {
/* 213 */         return CommonResult.error("该商户信息不存在, 补偿失败");
/*     */       }
/*     */     } 
/* 216 */     MchInfoForConfigVo mchInfoVo = (MchInfoForConfigVo)BeanUtil.copyProperties(mchInfo, MchInfoForConfigVo.class);
/* 217 */     mchInfoVo.setSettleType(this.mchInfoService.getSettleTypeIfNullUseParent(mchInfo));
/* 218 */     mchInfoVo.setSettleParams(this.mchInfoService.getSettleParamsIfNullUseParentParams(mchInfo));
/* 219 */     List<PayChannel> payChannels = this.payChannelService.getPayChannelListByMchId(mchInfo.getMchId());
/* 220 */     List<PayChannelForConfigVo> listPayChannel = BeanUtil.copyProperties(payChannels, PayChannelForConfigVo.class);
/* 221 */     mchInfoVo.setPayChannels(listPayChannel);
/* 222 */     return CommonResult.success((Serializable)mchInfoVo);
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：新增外部商户", notes = "新增外部商户：会默认使用父级支付渠道")
/*     */   @RequestMapping(value = {"/externalAddMch"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<Result> externalAddMch(@RequestBody ExternalMchInfoBo externalMchInfoBo, HttpServletRequest request) throws Exception {
/* 229 */     if (!this.securityAccessManager.checkAccessIPWhiteList(request)) {
/* 230 */       return CommonResult.error("非授权IP");
/*     */     }
/* 232 */     return this.mchInfoService.externalAddMch(externalMchInfoBo);
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\MchInfoController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */