/*     */ package org.hlpay.mgr.controller;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import com.alibaba.fastjson.JSONArray;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import io.swagger.annotations.Api;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.hlpay.base.bo.PayChannelBo;
/*     */ import org.hlpay.base.model.MchInfo;
/*     */ import org.hlpay.base.model.PayChannel;
/*     */ import org.hlpay.base.plugin.PageModel;
/*     */ import org.hlpay.base.service.MchInfoService;
/*     */ import org.hlpay.base.service.PayChannelService;
/*     */ import org.hlpay.base.vo.PayChannelVo;
/*     */ import org.hlpay.common.entity.CommonResult;
/*     */ import org.hlpay.common.enumm.CurrencyTypeEnum;
/*     */ import org.hlpay.common.enumm.MchTypeEnum;
/*     */ import org.hlpay.common.util.BeanUtil;
/*     */ import org.hlpay.common.util.DateUtil;
/*     */ import org.hlpay.common.util.MyLog;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.util.CollectionUtils;
/*     */ import org.springframework.web.bind.annotation.ModelAttribute;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Api(value = "支付渠道接口", tags = {"支付渠道接口"})
/*     */ @Controller
/*     */ @RequestMapping({"/pay_channel"})
/*     */ public class PayChannelController
/*     */ {
/*  44 */   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.controller.PayChannelController.class);
/*     */   
/*     */   @Autowired
/*     */   private MchInfoService mchInfoService;
/*     */   @Autowired
/*     */   private PayChannelService payChannelService;
/*     */   
/*     */   @ApiOperation(value = "通用：获取支付渠道列表", notes = "获取支付渠道列表")
/*     */   @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public PageModel list(@ModelAttribute PayChannel payChannel, Integer pageIndex, Integer pageSize) {
/*  55 */     PageModel pageModel = new PageModel();
/*  56 */     int count = this.payChannelService.count(payChannel).intValue();
/*  57 */     List<PayChannel> payChannelList = this.payChannelService.getPayChannelList((pageIndex.intValue() - 1) * pageSize.intValue(), pageSize.intValue(), payChannel);
/*  58 */     if (!CollectionUtils.isEmpty(payChannelList)) {
/*  59 */       JSONArray array = new JSONArray();
/*  60 */       for (PayChannel pc : payChannelList) {
/*  61 */         JSONObject object = (JSONObject)JSONObject.toJSON(pc);
/*  62 */         object.put("createTime", DateUtil.date2Str(pc.getCreateTime()));
/*  63 */         array.add(object);
/*     */       } 
/*  65 */       pageModel.setList((List)array);
/*     */     } 
/*  67 */     pageModel.setCount(Integer.valueOf(count));
/*  68 */     pageModel.setMsg("ok");
/*  69 */     pageModel.setRel(Boolean.valueOf(true));
/*  70 */     return pageModel;
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "通用：根据商户ID获取支付渠道信息列表", notes = "根据商户ID获取支付渠道信息列表")
/*     */   @RequestMapping(value = {"/byMchId"}, method = {RequestMethod.GET})
/*     */   @ResponseBody
/*     */   public CommonResult<PageModel<List<PayChannelVo>>> byMchId(@RequestParam String mchId) {
/*  77 */     PageModel pageModel = new PageModel();
/*  78 */     PayChannel payChannel = new PayChannel();
/*  79 */     payChannel.setMchId(mchId);
/*  80 */     int count = this.payChannelService.countWithoutInner(payChannel).intValue();
/*  81 */     if (count <= 0) return CommonResult.success((Serializable)pageModel); 
/*  82 */     List<PayChannel> payChannelList = this.payChannelService.getPayChannelListWithoutInner(0, 50, payChannel);
/*  83 */     List<PayChannelVo> payChannelVoList = BeanUtil.copyProperties(payChannelList, PayChannelVo.class);
/*     */ 
/*     */ 
/*     */     
/*  87 */     Map<String, String> mchIdMap = new HashMap<>();
/*  88 */     for (int i = 0; i < payChannelVoList.size(); i++) {
/*  89 */       PayChannelVo payChannelVo = payChannelVoList.get(i);
/*  90 */       mchIdMap.put(payChannelVo.getMchId(), null);
/*  91 */       _log.info("支付渠道信息：" + JSONObject.toJSONString(payChannelVo), new Object[0]);
/*     */     } 
/*     */     
/*  94 */     List<String> mchIdList = new ArrayList<>();
/*  95 */     mchIdMap.forEach((BiConsumer<? super String, ? super String>)new Object(this, mchIdList));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     Map<String, MchInfo> mchInfoMap = new HashMap<>();
/* 103 */     List<MchInfo> mchInfoList = this.mchInfoService.selectMchInfoByIdList(mchIdList);
/* 104 */     _log.info("mchInfoList：" + JSONArray.toJSONString(mchInfoList), new Object[0]);
/* 105 */     mchInfoList.forEach((Consumer<? super MchInfo>)new Object(this, mchInfoMap));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     for (int j = 0; j < payChannelVoList.size(); j++) {
/* 113 */       PayChannelVo payChannelVo = payChannelVoList.get(j);
/* 114 */       JSONObject param = JSONObject.parseObject(payChannelVo.getParam());
/* 115 */       String currency = param.getString("currency").toUpperCase();
/* 116 */       currency = CurrencyTypeEnum.valueOf(currency).name();
/*     */       
/* 118 */       MchInfo mchInfo = mchInfoMap.get(payChannelVo.getMchId());
/* 119 */       if (mchInfo != null) {
/* 120 */         param = JSONObject.parseObject(mchInfo.getSettleParams());
/* 121 */         payChannelVo.setPoundageRate(param.getString(payChannelVo.getChannelName()));
/*     */       } else {
/* 123 */         payChannelVo.setPoundageRate(param.getString("poundageRate"));
/*     */       } 
/* 125 */       payChannelVo.setCurrency(currency);
/* 126 */       payChannelVo.setParam(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       if (payChannelVo.getChannelId().equals("WX_MWEB")) {
/* 136 */         payChannelVo.setChannelId("PC");
/* 137 */       } else if (payChannelVo.getChannelId().equals("WX_APP")) {
/* 138 */         payChannelVo.setChannelId("APP");
/* 139 */       } else if (payChannelVo.getChannelId().equals("WX_JSAPI")) {
/* 140 */         payChannelVo.setChannelId("JSAPI");
/* 141 */       } else if (payChannelVo.getChannelId().equals("ALIPAY_PC")) {
/* 142 */         payChannelVo.setChannelId("PC");
/* 143 */       } else if (payChannelVo.getChannelId().equals("ALIPAY_MOBILE")) {
/* 144 */         payChannelVo.setChannelId("APP");
/* 145 */       } else if (payChannelVo.getChannelId().equals("UNION_PC")) {
/* 146 */         payChannelVo.setChannelId("PC");
/* 147 */       } else if (payChannelVo.getChannelId().equals("UNION_WAP")) {
/* 148 */         payChannelVo.setChannelId("APP");
/*     */       } 
/*     */     } 
/* 151 */     pageModel.setCount(Integer.valueOf(count));
/* 152 */     pageModel.setMsg("ok");
/* 153 */     pageModel.setRel(Boolean.valueOf(true));
/* 154 */     pageModel.setList(payChannelVoList);
/* 155 */     return CommonResult.success((Serializable)pageModel);
/*     */   }
/*     */   @ApiOperation(value = "通用：保存支付渠道信息", notes = "保存支付渠道信息：参数为渠道信息json")
/*     */   @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public String save(@RequestParam String params, HttpServletRequest request) {
/*     */     int result;
/* 162 */     JSONObject po = JSONObject.parseObject(params);
/* 163 */     String channelId = po.getString("channelId");
/* 164 */     String param = po.getString("param");
/*     */     
/* 166 */     if ("ALIPAY_MOBILE".equals(channelId) || "ALIPAY_PC"
/* 167 */       .equals(channelId) || "ALIPAY_WAP"
/* 168 */       .equals(channelId) || "ALIPAY_QR"
/* 169 */       .equals(channelId)) {
/* 170 */       JSONObject paramObj = null;
/*     */       try {
/* 172 */         paramObj = JSON.parseObject(po.getString("param"));
/* 173 */       } catch (Exception e) {
/* 174 */         _log.info("param is not json", new Object[0]);
/*     */       } 
/* 176 */       if (paramObj != null) {
/* 177 */         paramObj.put("private_key", paramObj.getString("private_key").replaceAll(" ", "+"));
/* 178 */         paramObj.put("alipay_public_key", paramObj.getString("alipay_public_key").replaceAll(" ", "+"));
/* 179 */         param = paramObj.toJSONString();
/*     */       } 
/*     */     } 
/* 182 */     PayChannel payChannel = new PayChannel();
/* 183 */     Integer id = po.getInteger("id");
/* 184 */     payChannel.setChannelId(channelId);
/* 185 */     payChannel.setMchId(po.getString("mchId"));
/* 186 */     payChannel.setChannelName(po.getString("channelName"));
/*     */     
/* 188 */     payChannel.setChannelAccount(po.getString("channelAccount"));
/* 189 */     payChannel.setChannelMchId(po.getString("channelMchId"));
/* 190 */     payChannel.setState(Byte.valueOf((byte)("on".equalsIgnoreCase(po.getString("state")) ? 1 : 0)));
/* 191 */     payChannel.setParam(param);
/* 192 */     payChannel.setRemark(po.getString("remark"));
/*     */     
/* 194 */     if (id == null) {
/*     */       
/* 196 */       result = this.payChannelService.addPayChannel(payChannel);
/*     */     } else {
/*     */       
/* 199 */       payChannel.setId(id);
/* 200 */       result = this.payChannelService.updatePayChannel(payChannel);
/*     */     } 
/* 202 */     _log.info("保存渠道记录,返回:{}", new Object[] { Integer.valueOf(result) });
/* 203 */     return result + "";
/*     */   }
/*     */   
/*     */   @ApiOperation(value = "私有云专用：创建普通商户支付渠道", notes = "创建普通商户支付渠道")
/*     */   @RequestMapping(value = {"/createMchChannel"}, method = {RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public CommonResult<Integer> createMchChannel(@RequestBody PayChannelBo payChannelBo, HttpServletRequest request) throws NoSuchMethodException {
/* 210 */     MchInfo mchInfo = this.mchInfoService.selectMchInfo(payChannelBo.getMchId());
/* 211 */     if (MchTypeEnum.PLATFORM.name().equals(mchInfo.getType())) {
/* 212 */       return CommonResult.error("商户类型不匹配");
/*     */     }
/* 214 */     return this.payChannelService.createChannel(payChannelBo);
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\PayChannelController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */