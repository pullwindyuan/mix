 package org.hlpay.mgr.sevice;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;

 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.bo.BaseMchInfoBo;
 import org.hlpay.base.bo.MchInfoForConfigBo;
 import org.hlpay.base.bo.PayChannelALIPAYBo;
 import org.hlpay.base.bo.PayChannelBo;
 import org.hlpay.base.bo.PayChannelForConfigBo;
 import org.hlpay.base.bo.PayChannelWXBo;
 import org.hlpay.base.bo.PlatformAgencyBo;
 import org.hlpay.base.bo.PlatformBo;
 import org.hlpay.base.bo.PlatformSettlePoundageRateConfigBo;
 import org.hlpay.base.bo.PlatformSettleTnConfigBo;
 import org.hlpay.base.bo.WorkFlowAuditAddBo;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.payFeign.AppOrganizeClient;
 import org.hlpay.base.payFeign.WorkflowAudit;
 import org.hlpay.base.service.CardService;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.PayChannelService;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.base.vo.ExternalMchInfoVo;
 import org.hlpay.base.vo.MchInfoVo;
 import org.hlpay.base.vo.PayChannelVo;
 import org.hlpay.common.constant.PayConstant;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.*;
 import org.hlpay.common.util.BeanUtil;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RandomStrUtils;
 import org.hlpay.common.util.ResultUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.bind.annotation.RequestBody;

 @Component
 public class PlatformService {
   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.sevice.PlatformService.class);

   @Resource
   private Environment env;

   private static RunModeEnum runMode;

   @Autowired
   private WorkflowAudit workflowAudit;

   @Autowired
   private PayChannelService payChannelService;

   @Autowired
   private MchInfoService mchInfoService;

   @Autowired
   private CardService cardService;

   @Autowired
   private AppOrganizeClient appOrganizeClient;

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   private static final String defaultPayChannels = "[\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_RECHARGE\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000001,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部充值支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_WITHDRAW\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000002,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部提现支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_TRANS\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000003,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部转账支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1300000237714774\",\n        \"channelId\": \"HL_CNY_PAY\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000004,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部即时到账支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1300000237714774\",\n        \"channelId\": \"HL_CNY_ASYN_PAY\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000005,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部奖励支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      }\n    ]";

   public MchInfo getAgencyInfo(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = this.mchInfoService.selectByPrimaryKey(mchId);
     if (mchInfo == null) {
       return null;
     }
     if (MchTypeEnum.PLATFORM_AGENCY.name().equals(mchInfo.getType())) {
       return mchInfo;
     }
     if (mchInfo.getParentMchId() != null) {
       return getAgencyInfo(mchInfo.getParentMchId());
     }
     return null;
   }


   public CommonResult<MchInfoVo> getPlatform() throws Exception {
     MchInfo mchInfo = this.mchInfoService.createPlatform();
     if (mchInfo == null) {
       return CommonResult.error("平台信息不存在");
     }

     PayChannel payChannel = new PayChannel();
     payChannel.setMchId("1");
     List<PayChannel> payChannels = this.payChannelService.getPayChannelList(payChannel);
     MchInfoVo mchInfoVo = (MchInfoVo)BeanUtil.copyProperties(mchInfo, MchInfoVo.class);
     List<PayChannelVo> listPayChannel = BeanUtil.copyProperties(payChannels, PayChannelVo.class);
     mchInfoVo.setPayChannels(listPayChannel);
     return CommonResult.success(mchInfoVo);
   }

   public MchInfo getPlatformForLocal(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);
     if (mchInfo == null) {
       return null;
     }


     String type = MchTypeEnum.PLATFORM.name();
     if (!type.equals(mchInfo.getType())) {
       if (StringUtils.isBlank(mchInfo.getParentMchId())) {
         return null;
       }
       return getPlatformForLocal(mchInfo.getParentMchId());
     }

     return mchInfo;
   }

   public CommonResult<Result> safeGetPlatform(String externalId) throws Exception {
     return safeGetPlatform(externalId, "mch");
   }


   public CommonResult<Result> safeGetPlatform(String externalId, String configType) throws Exception {
     MchInfoForConfigBo mchInfoForConfigBo = getPlatform(externalId);

     String configUrl = mchInfoForConfigBo.getAgency().getSecurityUrl() + "/platform/config";
     System.out.println("平台代理安全域名:" + configUrl);
     mchInfoForConfigBo.setConfigType(configType);
     JSONObject jsonObject = (JSONObject)JSONObject.toJSON(mchInfoForConfigBo);
     //这里要用http访问
//     Object object = new Object(this, configUrl, jsonObject);
//     object.run();
     return CommonResult.success(ResultUtil.createSuccessResult("ok"));
   }






   public MchInfoForConfigBo getPlatform(String externalId) throws Exception {
     MchInfo mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(externalId, MchTypeEnum.PLATFORM_AGENCY.name());
     if (mchInfo == null) {
       System.out.println("平台代理信息不存在:" + externalId + ":尝试进行补偿同步信息");
       createAgencyInfo(externalId);
     }
     System.out.println("代理商户信息：" + JSONObject.toJSONString(mchInfo));

     String type = MchTypeEnum.PLATFORM_AGENCY.name();
     if (!type.equals(mchInfo.getType())) {
       System.out.println("类型不匹配：" + mchInfo.toString());
       return null;
     }

     if (mchInfo.getParentMchId() == null) {
       System.out.println("平台代理信息不存在：" + mchInfo.toString());
       return null;
     }


     List<PayChannel> payChannels = this.payChannelService.getPayChannelListByMchId(mchInfo.getMchId());
     if (payChannels.size() == 0) {
       System.out.println("平台支付信息不存在：" + mchInfo.toString());
       return null;
     }

     System.out.println("平台ID:" + mchInfo.getParentMchId());
     MchInfo platForm = this.mchInfoService.selectMchInfo(mchInfo.getParentMchId());
     if (platForm == null) {
       System.out.println("平台信息不存在：" + mchInfo.toString());
       return null;
     }
     System.out.println("平台信息：" + platForm.toString());
     type = MchTypeEnum.PLATFORM.name();
     if (!type.equals(platForm.getType())) {
       System.out.println("平台类型不匹配：" + mchInfo.toString());
       return null;
     }


     MchInfoForConfigBo mchInfoForConfigBo = (MchInfoForConfigBo)BeanUtil.copyProperties(platForm, MchInfoForConfigBo.class);
     BaseMchInfoBo agency = (BaseMchInfoBo)BeanUtil.copyProperties(mchInfo, BaseMchInfoBo.class);
     List<PayChannelForConfigBo> listPayChannel = BeanUtil.copyProperties(payChannels, PayChannelForConfigBo.class);
     mchInfoForConfigBo.setAgency(agency);
     mchInfoForConfigBo.setPayChannels(listPayChannel);
     return mchInfoForConfigBo;
   }


   public void syncConfigToPrivate(String configType) {
     //这里要用http访问
//     Object object = new Object(this, configType);
//     object.run();
   }






   public void createAgencyInfo(String externalId) throws Exception {
     MchInfo mchInfo = null;
     System.out.println("平台代理信息不存在:" + externalId + ":尝试进行补偿同步信息");
     CommonResult<ExternalMchInfoVo> result = this.appOrganizeClient.getOrgMerchantParentInfo(Long.valueOf(externalId));
     if (result.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
       ExternalMchInfoVo externalMchInfoVo = (ExternalMchInfoVo)result.getData();
       PlatformAgencyBo agency = new PlatformAgencyBo();
       agency.setName(externalMchInfoVo.getName());
       agency.setExternalId(externalId);
       agency.setSecurityUrl(externalMchInfoVo.getPayDomain());

       CommonResult temp = this.mchInfoService.createPlatformAgencyInfo(agency);
       if (temp.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         mchInfo = this.mchInfoService.getMchInfoByExternalIdAndType(externalId, MchTypeEnum.PLATFORM_AGENCY.name());
       }
       if (mchInfo == null) {
         System.out.println("平台代理信息不存在:" + externalId + ":尝试失败");
         throw new Exception("平台代理信息不存在:" + externalId + ":尝试失败");
       }
     } else {
       System.out.println("平台代理信息不存在:" + externalId + ":尝试失败");
       throw new Exception("平台代理信息不存在:" + externalId + ":尝试失败");
     }
   }

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult updatePlatformSettlePoundageRate(PlatformSettlePoundageRateConfigBo platformSettlePoundageRateConfigBo) throws Exception {
     int result;
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     mchInfo = list.get(0);
     mchInfo.setSettlePoundageRate(platformSettlePoundageRateConfigBo.getSettlePoundageRate());




     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       result = this.mchInfoService.insertMchInfoAudit(mchInfo);
     }
     else if (((MchInfo)list.get(0)).getState().byteValue() == 2) {
       result = this.mchInfoService.updateMchInfoAudit(mchInfo);
     } else {
       return CommonResult.error("存在待审核记录");
     }

     _log.info("保存待审核平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
       workFlowAuditAddBo.setAuthor(platformSettlePoundageRateConfigBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(platformSettlePoundageRateConfigBo.getOperator());

       mchInfo.setReqKey(null);
       mchInfo.setResKey(null);
       mchInfo.getSettlePoundageRate();
       mchInfo.getSettleParamsTn();
       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(mchInfo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(platformSettlePoundageRateConfigBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditPoundageRate");
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     return CommonResult.error("保存失败");
   }


   public CommonResult auditPlatformSettlePoundageRate(Integer audit) {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("待审核平台不存在！");
     }
     int result = 1;
     mchInfo = list.get(0);


     if (audit.intValue() == 1) {
       result = this.mchInfoService.updateMchInfo(mchInfo);
     }
     mchInfo.setState(Byte.valueOf((byte)2));
     this.mchInfoService.updateMchInfoAudit(mchInfo);
     _log.info("审核平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       return CommonResult.success("审核成功");
     }
     return CommonResult.error("审核失败");
   }

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult updatePlatformSettleTn(PlatformSettleTnConfigBo platformSettleTnConfigBo) throws Exception {
     int result;
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     mchInfo = list.get(0);
     mchInfo.setSettleParamsTn(platformSettleTnConfigBo.getSettleParamsTn());




     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       result = this.mchInfoService.insertMchInfoAudit(mchInfo);
     }
     else if (((MchInfo)list.get(0)).getState().byteValue() == 2) {
       result = this.mchInfoService.updateMchInfoAudit(mchInfo);
     } else {
       return CommonResult.error("存在待审核记录");
     }

     _log.info("保存待审核平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
       workFlowAuditAddBo.setAuthor(platformSettleTnConfigBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(platformSettleTnConfigBo.getOperator());

       mchInfo.setReqKey(null);
       mchInfo.setResKey(null);
       mchInfo.getSettlePoundageRate();
       mchInfo.getSettleParamsTn();
       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(mchInfo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(platformSettleTnConfigBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditTn");
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     return CommonResult.error("保存失败");
   }


   public CommonResult auditPlatformSettleTn(Integer audit) {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("待审核平台不存在！");
     }
     int result = 1;
     mchInfo = list.get(0);
     if (audit.intValue() == 1) {
       result = this.mchInfoService.updateMchInfo(mchInfo);
     }
     mchInfo.setState(Byte.valueOf((byte)2));
     this.mchInfoService.updateMchInfoAudit(mchInfo);
     _log.info("审核平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       return CommonResult.success("审核成功");
     }
     return CommonResult.error("审核失败");
   }


   public CommonResult<Integer> getPlatformActiveSettlePoundageRate() {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     mchInfo = list.get(0);
     Integer rate = mchInfo.getSettlePoundageRate();
     _log.info("平台当前费率,返回:{}", new Object[] { rate });
     return CommonResult.success(rate);
   }

   public CommonResult<Integer> getPlatformAuditSettlePoundageRate() {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }

     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("待审核平台不存在！");
     }
     mchInfo = list.get(0);
     Integer rate = mchInfo.getSettlePoundageRate();
     _log.info("平台待审核费率,返回:{}", new Object[] { rate });
     return CommonResult.success(rate);
   }

   public CommonResult<Integer> getPlatformActiveSettleTn() {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }
     mchInfo = list.get(0);
     Integer n = mchInfo.getSettleParamsTn();
     _log.info("平台当前费率,返回:{}", new Object[] { n });
     return CommonResult.success(n);
   }

   public CommonResult<Integer> getPlatformAuditSettleTn() {
     String type = MchTypeEnum.PLATFORM.name();
     MchInfo mchInfo = new MchInfo();

     mchInfo.setType(type);
     List<MchInfo> list = this.mchInfoService.getMchInfoList(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("平台不存在！");
     }

     list = this.mchInfoService.getMchInfoListFromAudit(0, 1, mchInfo);
     if (list.size() == 0) {
       return CommonResult.error("待审核平台不存在！");
     }
     mchInfo = list.get(0);
     Integer n = mchInfo.getSettleParamsTn();
     _log.info("平台待审核费率,返回:{}", new Object[] { n });
     return CommonResult.success(n);
   }









   public MchInfo createDefaultPlatform() throws Exception {
     MchInfo mchInfo = this.mchInfoService.selectMchInfo("1");

     if (mchInfo == null) {
       String type = MchTypeEnum.PLATFORM.name();
       PlatformBo platformBo = new PlatformBo();
       platformBo.setExternalId("1");
       platformBo.setName(MchTypeEnum.PLATFORM.getDesc());
       mchInfo = (MchInfo)BeanUtil.copyProperties(platformBo, MchInfo.class);
       mchInfo.setType(type);
       mchInfo.setSettleCycle(SettleConfigEnum.DAY.name());
       mchInfo.setSettleParamsTn(PayConstant.DEFAULT_SETTLE_TN);
       mchInfo.setSettlePoundageRate(PayConstant.DEFAULT_SETTLE_POUNDAGE_RATE);

       mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
       mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));

       int i = this.mchInfoService.addMchInfo(mchInfo);
       _log.info("保存平台信息,返回:{}", new Object[] { Integer.valueOf(i) });
     }




     List<PayChannel> payChannels = JSONArray.parseArray("[\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_RECHARGE\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000001,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部充值支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_WITHDRAW\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000002,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部提现支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1200927912212142\",\n        \"channelId\": \"HL_CNY_TRANS\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000003,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部转账支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1300000237714774\",\n        \"channelId\": \"HL_CNY_PAY\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000004,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部即时到账支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      },\n      {\n        \"channelAccount\": \"1300000237714774\",\n        \"channelId\": \"HL_CNY_ASYN_PAY\",\n        \"channelMchId\": \"1\",\n        \"channelName\": \"INNER\",\n        \"createTime\": \"2021-04-15 18:52:28\",\n        \"id\": 100000005,\n        \"mchId\": \"1\",\n        \"param\": \"{\\\"public_key\\\":\\\"\\\",\\\"manageAccount\\\":\\\"1\\\",\\\"appid\\\":\\\"\\\",\\\"currency\\\":\\\"cny\\\",\\\"private_key\\\":\\\"\\\",\\\"isSandbox\\\":0,\\\"dumbAccount\\\":\\\"1\\\"}\",\n        \"remark\": \"人民币内部奖励支付渠道\",\n        \"state\": 1,\n        \"updateTime\": \"2021-04-15 18:52:28\"\n      }\n    ]", PayChannel.class);
     int result = this.payChannelService.listInsert(payChannels);
     if (result > 0) {

       for (PayChannel payChannelBo : payChannels) {
         JSONObject payParam = JSONObject.parseObject(payChannelBo.getParam());
         String currency = payParam.getString("currency");
         CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.valueOf(currency.toUpperCase());


         SaCard trusteeshipCard = this.cardService.addTrusteeshipCard(mchInfo, currencyTypeEnum);
         if (trusteeshipCard == null)
         {
           throw new Exception("创建内部托管卡失败");
         }
         SaCard regulationCard = this.cardService.addRegulationCard(mchInfo, currencyTypeEnum);
         if (regulationCard == null)
         {
           throw new Exception("创建内部资管卡失败");
         }
         SaCard dumbCard = this.cardService.addDumbCard(mchInfo, currencyTypeEnum);
         if (dumbCard == null)
         {
           throw new Exception("创建内部冲销卡失败");
         }
         SaCard paymentCard = this.cardService.addMchCard(mchInfo, CardDataTypeEnum.PAYMENT, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), "1");
         if (paymentCard == null)
         {
           throw new Exception("创建内部收支卡失败");
         }
       }
       return mchInfo;
     }
     throw new Exception("初始化支付渠道创建失败");
   }


   public CommonResult saveWXPayChannel(@RequestBody PayChannelWXBo payChannelWXBo, HttpServletRequest request) throws Exception {
     PayChannelBo payChannelBo = (PayChannelBo)BeanUtil.copyProperties(payChannelWXBo, PayChannelBo.class);
     payChannelBo.setChannelName("WX");
     MchInfo platform = this.mchInfoService.createPlatform();
     if (platform == null) {
       return CommonResult.error("平台信息不存在");
     }
     payChannelBo.setChannelMchId(payChannelBo.getMchId());
     payChannelBo.setRemark("微信APP支付");
     payChannelBo.setState(payChannelWXBo.getState());
     JSONObject param = new JSONObject();
     param.put("IPWhiteList", payChannelWXBo.getIPWhiteList());
     param.put("appId", payChannelWXBo.getAppId());
     param.put("mchId", payChannelWXBo.getChannelMchId());
     param.put("key", payChannelWXBo.getKey());
     param.put("certLocalPath", "classpath:/assets/apiclient_cert.p12");
     param.put("certPassword", payChannelWXBo.getChannelMchId());
     if (payChannelWXBo.getCurrency().equals(CurrencyTypeEnum.CNY.name())) {
       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
     } else if (payChannelWXBo.getCurrency().equals(CurrencyTypeEnum.USD.name())) {
       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
     } else {
       throw new Exception("支付币种参数不支持");
     }
     param.put("poundageRate", payChannelWXBo.getPoundageRate());
     payChannelBo.setParam(param.toJSONString());
     return savePayChannel(payChannelBo, platform, request);
   }

   public CommonResult saveALIPayChannel(@RequestBody PayChannelALIPAYBo payChannelALIPAYBo, HttpServletRequest request) throws Exception {
     PayChannelBo payChannelBo = (PayChannelBo)BeanUtil.copyProperties(payChannelALIPAYBo, PayChannelBo.class);
     payChannelBo.setChannelName("ALIPAY");
     MchInfo platform = this.mchInfoService.createPlatform();
     if (platform == null) {
       return CommonResult.error("平台信息不存在");
     }
     payChannelBo.setChannelMchId(platform.getMchId());
     payChannelBo.setRemark("支付宝APP支付");
     payChannelBo.setState(payChannelALIPAYBo.getState());
     JSONObject param = new JSONObject();
     param.put("IPWhiteList", payChannelALIPAYBo.getIPWhiteList());
     param.put("appId", payChannelALIPAYBo.getChannelMchId());
     if (payChannelALIPAYBo.getCurrency().equals(CurrencyTypeEnum.CNY.name())) {
       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
     } else if (payChannelALIPAYBo.getCurrency().equals(CurrencyTypeEnum.USD.name())) {
       param.put("currency", CurrencyTypeEnum.CNY.name().toLowerCase());
     } else {
       throw new Exception("支付币种参数不支持");
     }
     param.put("private_key", payChannelALIPAYBo.getPrivateKey());
     param.put("alipay_public_key", payChannelALIPAYBo.getAlipayPublicKey());
     param.put("isSandbox", Integer.valueOf(0));
     param.put("poundageRate", payChannelALIPAYBo.getPoundageRate());
     payChannelBo.setParam(param.toJSONString());
     return savePayChannel(payChannelBo, platform, request);
   }





   @Transactional(rollbackFor = {Exception.class})
   public CommonResult savePayChannel(@RequestBody PayChannelBo payChannelBo, MchInfo platform, HttpServletRequest request) throws Exception {
     String channelId = payChannelBo.getChannelId();

     if ("ALIPAY_MOBILE".equals(channelId) || "ALIPAY_PC"
       .equals(channelId) || "ALIPAY_WAP"
       .equals(channelId) || "ALIPAY_QR"
       .equals(channelId)) {
       JSONObject paramObj = null;
       try {
         paramObj = JSON.parseObject(payChannelBo.getParam());
       } catch (Exception e) {
         _log.info("param is not json", new Object[0]);
       }
       if (paramObj != null) {
         paramObj.put("private_key", paramObj.getString("private_key").replaceAll(" ", "+"));
         paramObj.put("alipay_public_key", paramObj.getString("alipay_public_key").replaceAll(" ", "+"));
         payChannelBo.setParam(paramObj.toJSONString());
       }
     }

     PayChannel payChannel = (PayChannel)BeanUtil.copyProperties(payChannelBo, PayChannel.class);
     Integer id = payChannel.getId();
     WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
     if (id == null) {
       payChannel.setId(Integer.valueOf(Integer.parseInt(RandomStrUtils.getInstance().createRandomNumString(8))));
       _log.info("新建渠道记录。分配ID ： " + payChannel.getId(), new Object[0]);
       workFlowAuditAddBo.setSyncAuditDataType("ADD");
     } else {
       _log.info("编辑渠道记录。ID ： " + payChannel.getId(), new Object[0]);
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");
     }

     List<PayChannel> list = new ArrayList<>();



     if (payChannel.getChannelName().equals("WX")) {
       PayChannel APP = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       APP.setChannelId("WX_APP");
       list.add(APP);
       PayChannel QR = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       QR.setChannelId("WX_NATIVE");
       list.add(QR);
       PayChannel JSAPI = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       JSAPI.setChannelId("WX_JSAPI");
       list.add(JSAPI);
       PayChannel MWEB = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       MWEB.setChannelId("WX_MWEB");
       list.add(MWEB);
     } else if (payChannel.getChannelName().equals("ALIPAY")) {
       PayChannel APP = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       APP.setChannelId("ALIPAY_MOBILE");
       list.add(APP);
       PayChannel QR = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       QR.setChannelId("ALIPAY_QR");
       list.add(QR);
       PayChannel PC = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       PC.setChannelId("ALIPAY_PC");
       list.add(PC);
       PayChannel WAP = (PayChannel)BeanUtil.copyProperties(payChannel, PayChannel.class);
       WAP.setChannelId("ALIPAY_WAP");
       list.add(WAP);
     }
     int result = this.payChannelService.listInsertForAudit(list);


     _log.info("保存渠道记录,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       PayChannelVo payChannelVo = PayChannelVo.getInstance(payChannel, platform);

       _log.info("同步审核新建渠道记录。payChannelVo ： " + JSONObject.toJSONString(payChannelVo), new Object[0]);
       workFlowAuditAddBo.setAuthor(payChannelBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(payChannelBo.getOperator());
       _log.info("同步审核新建渠道记录。bizId ： " + payChannel.getId().toString(), new Object[0]);
       workFlowAuditAddBo.setBizId(payChannel.getId().toString());
       payChannel.setParam(null);
       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(payChannelVo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(payChannelBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditPlatformPayChannel");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     throw new Exception("工作流同步失败");
   }
 }





