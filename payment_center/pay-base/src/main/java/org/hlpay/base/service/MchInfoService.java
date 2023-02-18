 package org.hlpay.base.service;
 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.github.pagehelper.StringUtil;
 import java.io.Serializable;
 import java.util.*;
 import java.util.concurrent.TimeUnit;

 import org.apache.commons.collections4.CollectionUtils;
 import org.apache.commons.lang3.ObjectUtils;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.bo.*;
 import org.hlpay.base.cache.CacheService;
 import org.hlpay.base.mapper.MchInfoMapper;
 import org.hlpay.base.model.ExternalMchInfo;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.MchInfoExample;
 import org.hlpay.base.model.PayChannel;
 import org.hlpay.base.model.SaCard;
 import org.hlpay.base.model.SettleParams;
 import org.hlpay.base.payFeign.GetExternalOrgInfo;
 import org.hlpay.base.payFeign.GetExternalStoreInfo;
 import org.hlpay.base.payFeign.WorkflowAudit;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.base.vo.ExternalMchInfoVo;
 import org.hlpay.common.constant.PayConstant;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.*;
 import org.hlpay.common.util.*;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;

 import javax.annotation.Resource;

 @Component
 public class MchInfoService extends CacheService<MchInfo> {
   private static final MyLog _log = MyLog.getLog(MchInfoService.class);

   @Resource
   private Environment env;

   @Autowired
   private WorkflowAudit workflowAudit;

   @Autowired
   private RedisUtil redisUtil;

   @Autowired
   private PayChannelService payChannelService;

   @Autowired
   private PayChannelForPayService payChannelForPayService;

   @Resource
   private MchInfoMapper mchInfoMapper;

   @Autowired
   private CardService cardService;

   @Autowired
   private GetExternalOrgInfo getExternalOrgInfo;

   @Autowired
   private GetExternalStoreInfo getExternalStoreInfo;

   @Autowired
   private Mq4MchNotify mq4MchNotify;

   @Autowired
   private RedisProUtil<String> redisProUtil;

   @Autowired
   private RunModeService runModeService;
   private static Map<String, SettleParams> settleParamsMap = new HashMap<>();
   public SettleParams getSettleParams(MchInfo mchInfo) throws NoSuchMethodException {
     SettleParams settleParams = settleParamsMap.get(mchInfo.getMchId());
     if (settleParams == null) {
       mchInfo.setSettleType(getSettleTypeIfNullUseParent(mchInfo));
       mchInfo.setSettleParams(getSettleParamsIfNullUseParentParams(mchInfo));
       settleParams = new SettleParams(mchInfo);
     }
     return settleParams;
   }





   public Map selectMchInfo(JSONObject jsonParam) throws NoSuchMethodException {
     if (ObjectValidUtil.isInvalid(new Object[] { jsonParam })) {
       _log.warn("查询商户信息失败, {}. jsonParam={}", new Object[] { RetEnum.RET_PARAM_NOT_FOUND.getMessage(), jsonParam });

       return ResultUtil.createFailMap("查询商户信息失败", RetEnum.RET_PARAM_NOT_FOUND.getMessage());
     }
     String mchId = jsonParam.get("mchId").toString();
     if (ObjectValidUtil.isInvalid(new Object[] { mchId })) {
       _log.warn("查询商户信息失败, {}. jsonParam={}", new Object[] { RetEnum.RET_PARAM_INVALID.getMessage(), jsonParam });

       return ResultUtil.createFailMap("查询商户信息失败", RetEnum.RET_PARAM_INVALID.getMessage());
     }
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null)
     {
       return ResultUtil.createFailMap("查询商户信息失败", RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage());
     }
     JSONObject jsonResult = JsonUtil.getJSONObjectFromObj(mchInfo);


     return ResultUtil.createSuccessMap(jsonResult);
   }

   public JSONObject getByMchId(String mchId) throws NoSuchMethodException {
     JSONObject paramMap = new JSONObject();
     paramMap.put("mchId", mchId);
     Map<String, Object> result = selectMchInfo(paramMap);
     Boolean s = (Boolean)result.get("isSuccess");
     if (s.booleanValue()) {
       return (JSONObject)result.get("bizResult");
     }
     return null;
   }



   public int addMchInfo(MchInfo mchInfo) {
     if (StringUtil.isEmpty(mchInfo.getMchId())) {
       String mchId;

       if (StringUtil.isEmpty(mchInfo.getExternalId())) {
         MchInfoExample example = new MchInfoExample();
         example.setOrderByClause("MchId DESC");
         example.setOffset(Integer.valueOf(0));
         example.setLimit(Integer.valueOf(1));
         mchId = RandomStrUtils.getInstance().getMixRandomString(8);
       } else {

         mchId = mchInfo.getExternalId();
       }
       mchInfo.setMchId(mchId);
     }
     return insertSelective(mchInfo);
   }

   public int configMchInfo(MchInfo mchInfo) throws NoSuchMethodException {
     if (StringUtil.isEmpty(mchInfo.getMchId())) {
       return -1;
     }
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("MchId DESC");
     example.setOffset(Integer.valueOf(0));
     example.setLimit(Integer.valueOf(1));
     MchInfo temp = selectByPrimaryKey(mchInfo.getMchId());
     if (temp != null) {
       return updateByPrimaryKey(mchInfo);
     }
     return insertSelective(mchInfo);
   }


   public String addMchInfoReturnMchId(MchInfo mchInfo) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("MchId DESC");
     example.setOffset(Integer.valueOf(0));
     example.setLimit(Integer.valueOf(1));
     List<MchInfo> mchInfos = selectByExample(example);

     String mchId = "10000000";
     if (!CollectionUtils.isEmpty(mchInfos))
     {
       mchId = String.valueOf(Integer.parseInt(((MchInfo)mchInfos.get(0)).getMchId()) + 1);
     }
     mchInfo.setMchId(mchId);
     insertSelective(mchInfo);
     return mchId;
   }

   public int updateMchInfo(MchInfo mchInfo) {
     return updateByPrimaryKeySelective(mchInfo);
   }

   public int updateMchInfoAudit(MchInfo mchInfo) {
     return updateByPrimaryKeySelectiveForAudit(mchInfo);
   }

   public int insertMchInfoAudit(MchInfo mchInfo) {
     return insertSelectiveForAudit(mchInfo);
   }

   public MchInfo selectMchInfo(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectByPrimaryKey(mchId);
     if (mchInfo == null) {
       mchInfo = selectByPrimaryKey(UnionIdUtil.getIdInfoFromUnionId(mchId)[0][0]);
     }
     return mchInfo;
   }

   public List<MchInfo> selectMchInfoByIdList(List<String> idList) {
     MchInfoExample mchInfoExample = new MchInfoExample();
     MchInfoExample.Criteria criteria = mchInfoExample.createCriteria();
     criteria.andMchIdIn(idList);




     List<MchInfo> mchInfoList = selectByExample(mchInfoExample);

     return mchInfoList;
   }

   public MchInfo selectMchInfoForAudit(String mchId) {
     return selectByPrimaryKeyForAudit(mchId);
   }

   public MchInfo getAgencyInfo(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectByPrimaryKey(mchId);
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


   public MchInfo getAgencyInfo() {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andTypeEqualTo(MchTypeEnum.PLATFORM_AGENCY.name());
     criteria.andStateEqualTo(Byte.valueOf((byte)1));
     List<MchInfo> list = selectByExample(example);
     if (list.size() > 0) {
       return list.get(0);
     }
     return null;
   }

   public List<MchInfo> getMchInfoListFromAudit(int offset, int limit, MchInfo mchInfo) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, mchInfo);
     return selectByExampleFromAudit(example);
   }

   public List<MchInfo> getMchInfoList(int offset, int limit, MchInfo mchInfo) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, mchInfo);




     List<MchInfo> mchInfoList = selectByExample(example);

     return mchInfoList;
   }

   public List<MchInfo> getMchInfoListByParentMchId(int offset, int limit, String parentMchId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentMchIdEqualTo(parentMchId);
     return selectByExample(example);
   }

   public List<MchInfo> getMchInfoListByParentMchId(String parentMchId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentMchIdEqualTo(parentMchId);
     return selectByExample(example);
   }

   public List<MchInfo> getMchInfoListByRootMchId(String rootMchId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdStartWith(rootMchId);
     return selectByExample(example);
   }

   public List<MchInfo> getBranchMchInfoListByParentMchId(String parentMchId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentMchIdEqualTo(parentMchId);
     criteria.andTypeEqualTo(MchTypeEnum.MCH_BRANCH.name());
     criteria.andMchIdLike(parentMchId);
     return selectByExample(example);
   }

   public List<MchInfo> getBranchMchInfoListByParentExternalId(String parentExternalId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     MchInfoExample.Criteria criteria = example.createCriteria();

     criteria.andTypeEqualTo(MchTypeEnum.MCH_BRANCH.name());
     criteria.andMchIdLike("-" + parentExternalId + "-");
     List<MchInfo> mchInfoList = selectByExample(example);

     example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     criteria = example.createCriteria();

     criteria.andTypeEqualTo(MchTypeEnum.MCH_BRANCH.name());
     criteria.andMchIdStartWith(parentExternalId + "-");
     mchInfoList.addAll(selectByExample(example));
     return mchInfoList;
   }

   public List<MchInfo> getMchInfoListByExternalIdAndType(int offset, int limit, String externalId, String type) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andExternalIdEqualTo(externalId);
     criteria.andTypeEqualTo(type);
     return selectByExample(example);
   }

   public List<MchInfo> getMchInfoListByExternalId(int offset, int limit, String externalId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andExternalIdEqualTo(externalId);
     return selectByExample(example);
   }

   public MchInfo getMchInfoByExternalIdAndType(String externalId, String type) throws NoSuchMethodException {
     MchInfo mchInfo = (MchInfo)getSimpleDataByUnionKey(externalId + ":" + type);
     if (mchInfo == null) {
       List<MchInfo> list = getMchInfoListByExternalIdAndType(0, 1, externalId, type);
       if (list.size() == 1) {
         mchInfo = list.get(0);
         putToSimpleCache(mchInfo);
         return mchInfo;
       }
       return null;
     }

     return mchInfo;
   }

   public List<MchInfo> getMchInfoListByParentExternalId(int offset, int limit, String parentExternalMchId) {
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     example.setOffset(Integer.valueOf(offset));
     example.setLimit(Integer.valueOf(limit));
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentExternalIdEqualTo(parentExternalMchId);
     return selectByExample(example);
   }

   public List<MchInfo> getMchInfoListByMchIdList(List<String> mchIdList) {
     if (mchIdList.size() == 0) {
       return new ArrayList<>();
     }
     MchInfoExample example = new MchInfoExample();
     example.setOrderByClause("CreateTime DESC");
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andMchIdIn(mchIdList);
     return selectByExample(example);
   }

   public Integer count(MchInfo mchInfo) {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     setCriteria(criteria, mchInfo);
     return Integer.valueOf(countByExample(example));
   }

   public Integer countByParentMchId(String parentMchId) {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentMchIdEqualTo(parentMchId);
     return Integer.valueOf(countByExample(example));
   }

   public Integer countByExternalId(String externalId) {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andExternalIdEqualTo(externalId);
     return Integer.valueOf(countByExample(example));
   }

   public Integer countByParentExternalId(String parentExternalId) {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andParentExternalIdEqualTo(parentExternalId);
     return Integer.valueOf(countByExample(example));
   }

   void setCriteria(MchInfoExample.Criteria criteria, MchInfo mchInfo) {
     if (mchInfo != null) {
       if (StringUtils.isNotBlank(mchInfo.getMchId())) {
         criteria.andMchIdEqualTo(mchInfo.getMchId());
       }
       if (mchInfo.getType() != null && !"-99".equals(mchInfo.getType())) {
         criteria.andTypeEqualTo(mchInfo.getType());
       }
       if (StringUtils.isNotBlank(mchInfo.getParentMchId())) {
         criteria.andParentMchIdEqualTo(mchInfo.getParentMchId());
       }
       if (StringUtils.isNotBlank(mchInfo.getParentExternalId())) {
         criteria.andParentExternalIdEqualTo(mchInfo.getParentExternalId());
       }
       if (StringUtils.isNotBlank(mchInfo.getExternalId())) {
         criteria.andExternalIdEqualTo(mchInfo.getExternalId());
       }
     }
   }

   public String addMchInfoAndChannel(MchInfo mchInfo, List<PayChannel> payChannelList) {
     String mchId = addMchInfoReturnMchId(mchInfo);
     for (PayChannel payChannel : payChannelList) {
       payChannel.setMchId(mchInfo.getMchId());
       this.payChannelService.addPayChannel(payChannel);
     }
     return mchId;
   }

   public int updateMchInfoAndChannel(MchInfo mchInfo, List<PayChannel> payChannelList) {
     updateMchInfo(mchInfo);
     for (PayChannel payChannel : payChannelList) {
       payChannel.setMchId(mchInfo.getMchId());
       if (ObjectUtils.isEmpty(payChannel.getId())) {

         this.payChannelService.addPayChannel(payChannel);
         continue;
       }
       this.payChannelService.updatePayChannel(payChannel);
     }

     return 1;
   }
   @Transactional(rollbackFor = {Exception.class})
   public CommonResult updateMchSettlePoundageRate(MchSettlePoundageRateConfigBo mchSettlePoundageRateConfigBo) throws Exception {
     int result;
     MchInfo mchInfo = selectMchInfo(mchSettlePoundageRateConfigBo.getMchId());
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }

     mchInfo.updateSettlePoundageRateSets(mchSettlePoundageRateConfigBo.getSettlePoundageRateSets());




     MchInfo auditMchInfo = selectMchInfoForAudit(mchSettlePoundageRateConfigBo.getMchId());
     if (auditMchInfo == null) {
       result = insertMchInfoAudit(mchInfo);
     }
     else if (auditMchInfo.getState().byteValue() == 2) {
       result = updateMchInfoAudit(mchInfo);
     } else {
       return CommonResult.error("存在待审核记录");
     }

     _log.info("保存待审核商户信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
       workFlowAuditAddBo.setAuthor(mchSettlePoundageRateConfigBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(mchSettlePoundageRateConfigBo.getOperator());
       workFlowAuditAddBo.setBizId(mchSettlePoundageRateConfigBo.getMchId());
       mchInfo.setReqKey(null);
       mchInfo.setResKey(null);
       mchInfo.getSettlePoundageRate();
       mchInfo.getSettleParamsTn();
       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(mchInfo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(mchSettlePoundageRateConfigBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditMchPoundageRate");
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     return CommonResult.error("保存失败");
   }

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult updateMchSettleTn(MchSettleTnConfigBo mchSettleTnConfigBo) throws Exception {
     int result;
     MchInfo mchInfo = selectMchInfo(mchSettleTnConfigBo.getMchId());
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }

     mchInfo.setSettleParamsTn(mchSettleTnConfigBo.getSettleParamsTn());




     MchInfo auditMchInfo = selectMchInfoForAudit(mchSettleTnConfigBo.getMchId());
     if (auditMchInfo == null) {
       result = insertMchInfoAudit(mchInfo);
     }
     else if (auditMchInfo.getState().byteValue() == 2) {
       result = updateMchInfoAudit(mchInfo);
     } else {
       return CommonResult.error("存在待审核记录");
     }

     _log.info("保存待审核商户信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
       workFlowAuditAddBo.setAuthor(mchSettleTnConfigBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(mchSettleTnConfigBo.getOperator());
       workFlowAuditAddBo.setBizId(mchSettleTnConfigBo.getMchId());
       mchInfo.setReqKey(null);
       mchInfo.setResKey(null);
       mchInfo.getSettlePoundageRate();
       mchInfo.getSettleParamsTn();
       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(mchInfo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(mchSettleTnConfigBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditMchTn");
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     return CommonResult.error("保存失败");
   }

   @Transactional(rollbackFor = {Exception.class})
   public CommonResult updateMchSettle(MchSettleConfigBo mchSettleConfigBo) throws Exception {
     int result;
     MchInfo mchInfo = selectMchInfo(mchSettleConfigBo.getMchId());
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }

     mchInfo.updateSettlePoundageRateSets("{\"pr\":" + mchSettleConfigBo.getSettlePoundageRate() + "}");
     mchInfo.setSettleParamsTn(mchSettleConfigBo.getSettleParamsTn());
     mchInfo.setSettleCycle(mchSettleConfigBo.getSettleType());
     mchInfo.setSettleParamsState(mchSettleConfigBo.getSettleParamsState());



     MchInfo auditMchInfo = selectMchInfoForAudit(mchSettleConfigBo.getMchId());
     if (auditMchInfo == null) {
       result = insertMchInfoAudit(mchInfo);
     }
     else if (auditMchInfo.getState().byteValue() == 2) {
       result = updateMchInfoAudit(mchInfo);
     } else {
       return CommonResult.error("存在待审核记录");
     }

     _log.info("保存待审核商户信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       WorkFlowAuditAddBo workFlowAuditAddBo = new WorkFlowAuditAddBo();
       workFlowAuditAddBo.setAuthor(mchSettleConfigBo.getOperatorId());
       workFlowAuditAddBo.setAuthorName(mchSettleConfigBo.getOperator());
       workFlowAuditAddBo.setBizId(mchSettleConfigBo.getMchId());
       mchInfo.setReqKey(null);
       mchInfo.setResKey(null);


       workFlowAuditAddBo.setContent((JSONObject)JSONObject.toJSON(mchInfo));
       workFlowAuditAddBo.setIsMulti(Integer.valueOf(0));
       workFlowAuditAddBo.setOrgId(Long.valueOf(Long.parseLong(mchSettleConfigBo.getOperatorOrgId())));
       workFlowAuditAddBo.setProjId(this.env.getProperty("spring.application.name"));
       workFlowAuditAddBo.setServletPath("/platform/auditMchSettleParams");
       workFlowAuditAddBo.setSyncAuditDataType("MODIFY");

       CommonResult commonResult = this.workflowAudit.workflowSyncAdd(workFlowAuditAddBo);
       if (commonResult.getCode().intValue() == ResultEnum.SUCCESS.getCode().intValue()) {
         return CommonResult.success("保存成功");
       }
       throw new Exception("工作流同步失败");
     }

     return CommonResult.error("保存失败");
   }


   public CommonResult auditMchSettle(String mchId, Integer audit) throws NoSuchMethodException {
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }
     MchInfo auditMchInfo = selectMchInfoForAudit(mchId);
     if (auditMchInfo == null) {
       return CommonResult.error("待审核商户不存在！");
     }
     int result = 1;
     if (audit.intValue() == 1) {

       String settleParamsStr = (String)this.redisProUtil.get(mchInfo.getMchId() + ":SP");
       if (StringUtils.isEmpty(settleParamsStr)) {
         SettleParams settleParams = getSettleParams(mchInfo);

         long value = DateUtils.getBetweenValueInMilSeconds(new Date(), DateUtils.getDaysDurationFirstDate(1)).longValue();
         this.redisProUtil.set(mchInfo.getMchId() + ":SP",
             JSONObject.toJSONString(settleParams), value, TimeUnit.MILLISECONDS);
       }


       result = updateMchInfo(auditMchInfo);
     }
     mchInfo.setState(Byte.valueOf((byte)2));
     updateMchInfoAudit(mchInfo);
     _log.info("审核商户信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       return CommonResult.success("审核成功");
     }
     return CommonResult.error("审核失败");
   }


   public CommonResult<String> getMchActiveSettlePoundageRate(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }
     String rate = getSettlePoundageRate(mchInfo);
     _log.info("商户当前费率,返回:{}", new Object[] { rate });
     return CommonResult.success(rate);
   }

   public CommonResult<String> getMchAuditSettlePoundageRate(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }

     mchInfo = selectMchInfoForAudit(mchId);
     if (mchInfo == null) {
       return CommonResult.error("待审核商户不存在！");
     }
     String rate = mchInfo.getPresentSettlePoundageRate();
     _log.info("商户待审核费率,返回:{}", new Object[] { rate });
     return CommonResult.success(rate);
   }

   public CommonResult<Integer> getMchActiveSettleTn(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }
     Integer n = getSettleTn(mchInfo);
     _log.info("商户当前费率,返回:{}", new Object[] { n });
     return CommonResult.success(n);
   }

   public CommonResult<Integer> getMchAuditSettleTn(String mchId) throws NoSuchMethodException {
     MchInfo mchInfo = selectMchInfo(mchId);
     if (mchInfo == null) {
       return CommonResult.error("商户不存在！");
     }

     mchInfo = selectMchInfoForAudit(mchId);
     if (mchInfo == null) {
       return CommonResult.error("待审核商户不存在！");
     }
     Integer n = mchInfo.getSettleParamsTn();
     _log.info("商户待审核费率,返回:{}", new Object[] { n });
     return CommonResult.success(n);
   }

   public CommonResult createPlatformInfo() throws Exception {
     MchInfo mchInfo = createPlatform();
     if (mchInfo == null) {
       CommonResult.error("创建失败");
     }
     return CommonResult.success("创建成功");
   }

   public MchInfo createPlatform() throws Exception {
     MchInfo mchInfo = selectMchInfo("1");
     if (mchInfo == null) {
       String type = MchTypeEnum.PLATFORM.name();
       PlatformBo platformBo = new PlatformBo();
       platformBo.setExternalId("1");
       platformBo.setName(MchTypeEnum.PLATFORM.getDesc());
       mchInfo = (MchInfo)BeanUtil.copyProperties(platformBo, MchInfo.class);
       mchInfo.setType(type);
       mchInfo.setSecurityUrl(this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl"));
       mchInfo.setSettleCycle(SettleConfigEnum.DAY.name());
       mchInfo.setSettleParamsTn(PayConstant.DEFAULT_SETTLE_TN);
       mchInfo.setSettlePoundageRate(PayConstant.DEFAULT_SETTLE_POUNDAGE_RATE);

       mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
       mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));

       int result = addMchInfo(mchInfo);
       _log.info("保存平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
       if (result > 0) {
         PayChannelBo payChannelBo = new PayChannelBo();
         payChannelBo.setMchId("1");
         if (this.payChannelService.createInnerChannel("1", CurrencyTypeEnum.CNY.name()) < 0) {
           throw new Exception("内部支付渠道创建失败");
         }
         return mchInfo;
       }
     }
     return mchInfo;
   }

   public MchInfo createPlatform(Integer poundageRate) throws NoSuchMethodException {
     if (poundageRate == null) {
       return null;
     }
     MchInfo mchInfo = selectMchInfo("1");
     if (mchInfo == null) {
       String type = MchTypeEnum.PLATFORM.name();
       PlatformBo platformBo = new PlatformBo();
       platformBo.setExternalId("1");
       platformBo.setName(MchTypeEnum.PLATFORM.getDesc());
       mchInfo = (MchInfo)BeanUtil.copyProperties(platformBo, MchInfo.class);
       mchInfo.setType(type);
       mchInfo.setSettleParamsTn(PayConstant.DEFAULT_SETTLE_TN);
       mchInfo.setSettlePoundageRate(poundageRate);

       mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
       mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));

       int result = addMchInfo(mchInfo);
       _log.info("保存平台信息,返回:{}", new Object[] { Integer.valueOf(result) });
       if (result > 0) {
         return mchInfo;
       }
     }
     return mchInfo;
   }

   public CommonResult<Result> createPlatformAgencyInfo(PlatformAgencyBo platformAgencyBo) throws Exception {
     MchInfo temp = new MchInfo();

     String type = MchTypeEnum.PLATFORM.name();
     temp.setType(type);
     MchInfo platform = createPlatform();

     MchInfo agency = (MchInfo)BeanUtil.copyProperties(platformAgencyBo, MchInfo.class);

     agency.setMchId(platformAgencyBo.getExternalId());
     agency.setParentMchId(platform.getMchId());
     agency.setType(MchTypeEnum.PLATFORM_AGENCY.name());
     agency.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
     agency.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));

     int result = addMchInfo(agency);
     _log.info("保存平台代理信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       return CommonResult.success(ResultUtil.createSuccessResult("ok"));
     }
     return CommonResult.error("fail");
   }

   public CommonResult<Result> configPlatform(MchInfoForConfigBo mchInfoForConfigBo) throws Exception {
     this.redisUtil.delete("tryConf");

     int result = configPlatformMchInfo(mchInfoForConfigBo).intValue();
     if (result > 0) {
       return CommonResult.success(ResultUtil.createSuccessResult("ok"));
     }
     return CommonResult.error("fail");
   }


   public CommonResult<Result> configPlatform(MchInfo mchInfo) throws Exception {
     this.redisUtil.delete("tryConf");

     int result = configPlatformMchInfo((MchInfoForConfigBo)BeanUtil.copyProperties(mchInfo, MchInfoForConfigBo.class)).intValue();
     if (result > 0) {
       return CommonResult.success(ResultUtil.createSuccessResult("ok"));
     }
     return CommonResult.error("fail");
   }


   public MchInfoForConfigBo configPlatform(String externalId) throws Exception {
     String configApiUrl = this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl") + "/platform/getPlatform";
     MchInfoForConfigBo mchInfoForConfigBo = null;
     if (!StringUtil.isEmpty(configApiUrl)) {
       try {
         System.out.println("请求同步更新配置");
         String result = HttpClient.post(configApiUrl, externalId);
         JSONObject jsonObject = JSONObject.parseObject(result);
         CommonResult<JSONObject> commonResult = (CommonResult<JSONObject>)JSONObject.parseObject(result, CommonResult.class);
         _log.info("获取的平台信息：" + ((JSONObject)commonResult.getData()).toJSONString(), new Object[0]);
         try {
           if (commonResult.getCode().intValue() != 200) {
             throw new Exception("配置支付参数失败！" + jsonObject.toJSONString());
           }
           jsonObject = (JSONObject)commonResult.getData();
           mchInfoForConfigBo = (MchInfoForConfigBo)JSONObject.toJavaObject((JSON)jsonObject, MchInfoForConfigBo.class);
           configPlatform(mchInfoForConfigBo);
         }
         catch (Exception e) {
           e.printStackTrace();
         }
       } catch (Exception e) {
         e.printStackTrace();
         throw new Exception("网络错误导致配置支付参数失败！");
       }
     } else {
       System.out.println("请先配置中心配置接口地址后再尝试运行！");
       throw new Exception("请先配置中心配置接口地址后再尝试运行！");
     }
     return mchInfoForConfigBo;
   }
   @Transactional(rollbackFor = {Exception.class})
   public Integer configPlatformMchInfo(MchInfoForConfigBo mchInfoForConfigBo) throws Exception {
     List<PayChannelForConfigBo> list = mchInfoForConfigBo.getPayChannels();
     MchInfo platform = (MchInfo)BeanUtil.copyProperties(mchInfoForConfigBo, MchInfo.class);
     MchInfo agency = (MchInfo)BeanUtil.copyProperties(mchInfoForConfigBo.getAgency(), MchInfo.class);



     List<ExternalMchInfo> mchInfoList = new ArrayList<>();


     String type = MchTypeEnum.PLATFORM.name();


     platform.setType(type);

     mchInfoList.add((ExternalMchInfo)BeanUtil.copyProperties(platform, ExternalMchInfo.class));

     agency.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
     agency.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));
     agency.setType(MchTypeEnum.PLATFORM_AGENCY.name());

     mchInfoList.add((ExternalMchInfo)BeanUtil.copyProperties(agency, ExternalMchInfo.class));


     MchInfo mchInfo = (MchInfo)BeanUtil.copyProperties(agency, MchInfo.class);
     mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
     mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));
     mchInfo.setParentMchId(UnionIdUtil.getUnionId(new String[] { agency.getMchId(), mchInfo.getExternalId() }));
     mchInfo.setType(MchTypeEnum.MCH.name());
     mchInfoList.add((ExternalMchInfo)BeanUtil.copyProperties(mchInfo, ExternalMchInfo.class));
     int result = this.mchInfoMapper.listInsertExternalMchInfo(mchInfoList);
     _log.info("配置商户记录,返回:{}", new Object[] { Integer.valueOf(result) });

     if (result >= 0) {

       if (list == null || list.size() == 0) {
         return Integer.valueOf(0);
       }


       for (PayChannelForConfigBo payChannelBo : list) {
         JSONObject payParam = JSONObject.parseObject(payChannelBo.getParam());
         String currency = payParam.getString("currency");
         CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.valueOf(currency.toUpperCase());


         SaCard trusteeshipCard = this.cardService.addTrusteeshipCard(platform, currencyTypeEnum);
         if (trusteeshipCard == null)
         {
           throw new Exception("创建内部托管卡失败");
         }
         SaCard regulationCard = this.cardService.addRegulationCard(platform, currencyTypeEnum);
         if (regulationCard == null)
         {
           throw new Exception("创建内部资管卡失败");
         }
         SaCard dumbCard = this.cardService.addDumbCard(platform, currencyTypeEnum);
         if (dumbCard == null)
         {
           throw new Exception("创建内部冲销卡失败");
         }
         SaCard paymentCard = this.cardService.addMchCard(platform, CardDataTypeEnum.PAYMENT, currencyTypeEnum, null, RunModeEnum.PUBLIC.getCode().intValue(), "1");
         if (paymentCard == null)
         {
           throw new Exception("创建内部收支卡失败");
         }
       }
       List<PayChannel> records = BeanUtil.copyProperties(list, PayChannel.class);

       int runMode = this.runModeService.getRunModeCode();
       if (runMode != RunModeEnum.PRIVATE.getCode().intValue()) {
         result = this.payChannelService.listInsert(records);
       } else {
         List<PayChannel> inners = new ArrayList<>();
         List<PayChannel> outsides = new ArrayList<>();
         for (PayChannel payChannel : records) {
           if ("INNER".equals(payChannel.getChannelName())) {
             inners.add(payChannel); continue;
           }
           outsides.add(payChannel);
         }


         result = this.payChannelService.listInsert(inners);

         this.payChannelForPayService.configPlatformPayChannelFromRemote(mchInfoForConfigBo);
       }
       if (result <= 0) {
         throw new Exception("平台配置商户信息失败:支付渠道无法保存");
       }
     } else {
       throw new Exception("平台配置商户信息失败：商户无法保存");
     }
     return Integer.valueOf(result);
   }
   public List<MchInfo> genAddMchInfoList(ExternalMchInfoVo externalMchInfoVo, List<MchInfo> list) throws Exception {
     ExternalMchInfoVo parentOfParentExternal;
     if (externalMchInfoVo == null) {
       return null;
     }
     if (list == null) {
       list = new ArrayList<>();
     }

     MchInfo mchInfo = new MchInfo();
     mchInfo.setParentExternalId(Long.toString(externalMchInfoVo.getParentId().longValue()));
     mchInfo.setExternalId(Long.toString(externalMchInfoVo.getId().longValue()));
     mchInfo.setName(externalMchInfoVo.getName());

     ExternalMchTypeEnum externalMchTypeEnum = ExternalMchTypeEnum.getExternalMchTypeEnum(externalMchInfoVo.getType());

     mchInfo.setType(externalMchTypeEnum.getMchTypeEnum().name());
     list.add(mchInfo);

     MchInfo parent = new MchInfo();
     parent.setExternalId(Long.toString(externalMchInfoVo.getParentId().longValue()));
     parent.setName(externalMchInfoVo.getParentName());
     parent.setExternalId(Long.toString(externalMchInfoVo.getParentId().longValue()));
     parent.setName(externalMchInfoVo.getParentName());

     ExternalMchTypeEnum parentExternalMchTypeEnum = ExternalMchTypeEnum.getExternalMchTypeEnum(externalMchInfoVo.getParentType());
     parent.setType(parentExternalMchTypeEnum.getMchTypeEnum().name());



     if (MchTypeEnum.MCH_BRANCH.name().equals(externalMchTypeEnum.getMchTypeEnum().name())) {


       parentOfParentExternal = (ExternalMchInfoVo)this.getExternalOrgInfo.getMerchantParentById(externalMchInfoVo.getParentId()).getData();
     } else {

       parentOfParentExternal = (ExternalMchInfoVo)this.getExternalOrgInfo.getOrgMerchantParentInfo(externalMchInfoVo.getParentId()).getData();
     }

     parent.setParentExternalId(Long.toString(parentOfParentExternal.getParentId().longValue()));


     _log.info("外部商户：" + JSONObject.toJSONString(mchInfo), new Object[0]);
     _log.info("外部商户：" + JSONObject.toJSONString(parent), new Object[0]);

     if (parentOfParentExternal.getParentId().longValue() != 0L) {
       return genAddMchInfoList(parentOfParentExternal, list);
     }
     String rootId = Long.toString(parentOfParentExternal.getId().longValue());
     int runMode = this.runModeService.getRunModeCode();
     if (runMode == RunModeEnum.PRIVATE.getCode().intValue()) {

       MchInfoForConfigBo mchInfoForConfigBo = configPlatform(rootId);
       if (mchInfoForConfigBo == null) {
         _log.error("拉取平台配置失败, externalId = " + rootId, new Object[0]);
         return null;
       }
       _log.error("拉取平台配置成功, externalId = " + rootId, new Object[0]);


       parent.setParentMchId(mchInfoForConfigBo.getAgency().getParentMchId());
       parent.setType(mchInfoForConfigBo.getAgency().getType());
       parent.setMchId(mchInfoForConfigBo.getAgency().getMchId());
       list.add(parent);
     }


     return list;
   }

   public List<MchInfo> tryToAddAllExternalMchInfo(String externalId) throws Exception {
     CommonResult<ExternalMchInfoVo> commonResult = this.getExternalStoreInfo.getMerchantParentByStoreId(Long.valueOf(Long.parseLong(externalId)));
     ExternalMchInfoVo externalMchInfoVo = (ExternalMchInfoVo)commonResult.getData();
     if (externalMchInfoVo == null) {


       commonResult = this.getExternalOrgInfo.getMerchantParentById(Long.valueOf(Long.parseLong(externalId)));
       externalMchInfoVo = (ExternalMchInfoVo)commonResult.getData();
       if (externalMchInfoVo == null) {


         commonResult = this.getExternalOrgInfo.getOrgMerchantParentInfo(Long.valueOf(Long.parseLong(externalId)));
         externalMchInfoVo = (ExternalMchInfoVo)commonResult.getData();
         if (externalMchInfoVo == null) {
           _log.error("该商户信息不存在，并且无法获取补偿数据, externalId = " + externalId, new Object[0]);
           return null;
         }
       }
     }
     List<MchInfo> mchInfoList = genAddMchInfoList(externalMchInfoVo, (List<MchInfo>)null);

     if (mchInfoList == null && mchInfoList.size() == 0) {
       _log.error("该商户信息不存在，数据补偿失败, externalId = " + externalId, new Object[0]);
       return null;
     }

     int size = mchInfoList.size() - 1;
     String rootMchId = ((MchInfo)mchInfoList.get(size)).getMchId();
     MchInfo parent = null;

     List<ExternalMchInfo> newList = new ArrayList<>();
     for (int i = size; i >= 0; i--) {
       MchInfo mchInfo = mchInfoList.get(i);

       if (StringUtils.isEmpty(mchInfo.getParentMchId())) {
         mchInfo.setMchId(UnionIdUtil.getUnionId(new String[] { parent.getMchId(), mchInfo.getExternalId() }));
         mchInfo.setParentMchId(parent.getMchId());
       }

       if (this.mchInfoMapper.selectByPrimaryKey(mchInfo.getMchId()) != null) {

         parent = mchInfo;
       }
       else {

         mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
         mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));
         mchInfo.setState(Byte.valueOf((byte)1));
         parent = mchInfo;
         newList.add((ExternalMchInfo)BeanUtil.copyProperties(mchInfo, ExternalMchInfo.class));
       }
     }  size = newList.size() - 1;

     List<ExternalMchInfo> addList = new ArrayList<>();
     for (int j = size; j >= 0; j--) {
       ExternalMchInfo externalMchInfo = newList.get(j);
       if (MchTypeEnum.MCH.name().equals(externalMchInfo.getType())) {

         externalMchInfo.setParentMchId(rootMchId);
         addList.add(externalMchInfo);
         break;
       }
       addList.add(externalMchInfo);
     }
     int result = this.mchInfoMapper.listInsertExternalMchInfo(addList);
     if (result == 0) {

       this.mq4MchNotify.send("queue.notify.mch.init.pay.order", externalId);
       return null;
     }
     return mchInfoList;
   }


   public CommonResult<Result> externalAddMch(ExternalMchInfoBo mchInfoBo) throws NoSuchMethodException {
     MchInfo mchInfo = (MchInfo)BeanUtil.copyProperties(mchInfoBo, MchInfo.class);
     _log.info("添加外部商户：" + JSONObject.toJSONString(mchInfo), new Object[0]);
     if (StringUtil.isEmpty(mchInfoBo.getParentExternalId())) {
       _log.info("父级商户不存在", new Object[0]);
       return CommonResult.error("父级商户不存在");
     }

     MchInfo parent = null;
     if (mchInfoBo.getType().equals(MchTypeEnum.MCH_BRANCH.name())) {
       String selectType = MchTypeEnum.MCH_VIRTUAL.name();
       parent = getMchInfoByExternalIdAndType(mchInfoBo.getParentExternalId(), selectType);
     } else if (mchInfoBo.getType().equals(MchTypeEnum.PLATFORM_AGENCY.name())) {
       String selectType = MchTypeEnum.PLATFORM.name();
       parent = getMchInfoByExternalIdAndType(mchInfoBo.getParentExternalId(), selectType);
     } else if (mchInfoBo.getType().equals(MchTypeEnum.MCH_VIRTUAL.name())) {
       String selectType = MchTypeEnum.MCH.name();
       parent = getMchInfoByExternalIdAndType(mchInfoBo.getParentExternalId(), selectType);
       if (parent == null) {
         parent = getMchInfoByExternalIdAndType(mchInfoBo.getParentExternalId(), MchTypeEnum.PLATFORM_AGENCY.name());
       }
     } else if (mchInfoBo.getType().equals(MchTypeEnum.MCH.name())) {
       String selectType = MchTypeEnum.PLATFORM_AGENCY.name();
       parent = getMchInfoByExternalIdAndType(mchInfoBo.getParentExternalId(), selectType);
     }

     if (parent == null) {
       _log.info("父级商户不存在", new Object[0]);
       return CommonResult.error("父级商户不存在");
     }
     mchInfo.setType(mchInfoBo.getType());
     mchInfo.setMchId(UnionIdUtil.getUnionId(new String[] { parent.getMchId(), mchInfoBo.getExternalId() }));
     mchInfo.setParentMchId(parent.getMchId());
     mchInfo.setReqKey(RandomStrUtils.getInstance().getMixRandomString(48));
     mchInfo.setResKey(RandomStrUtils.getInstance().getMixRandomString(48));

     int result = addMchInfo(mchInfo);
     _log.info("保存商户信息,返回:{}", new Object[] { Integer.valueOf(result) });
     if (result > 0) {
       return CommonResult.success(ResultUtil.createSuccessResult("ok"));
     }
     return CommonResult.error("fail");
   }


   public MchInfo getRootMchInfo(String mchId) throws NoSuchMethodException {
     String[][] temp = UnionIdUtil.getIdInfoFromUnionId(mchId);
     MchInfo mchInfo = selectByPrimaryKey(temp[0][0]);
     if (mchInfo != null) {
       if (mchInfo.getParentMchId() == null) {
         return mchInfo;
       }
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       if (parent == null) {
         return mchInfo;
       }
       return getRootMchInfo(parent.getMchId());
     }
     return null;
   }

   public List<MchInfo> selectBranchMchInfoList() {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andTypeEqualTo(MchTypeEnum.MCH_BRANCH.name());
     return this.mchInfoMapper.selectByExample(example);
   }

   public List<MchInfo> selectMchInfoList() {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andStateEqualTo(Byte.valueOf((byte)1));
     return this.mchInfoMapper.selectByExample(example);
   }

   public List<MchInfo> selectMchInfoWithOutPlatformList() {
     MchInfoExample example = new MchInfoExample();
     MchInfoExample.Criteria criteria = example.createCriteria();
     criteria.andStateEqualTo(Byte.valueOf((byte)1));
     criteria.andTypeNotEqualTo(MchTypeEnum.PLATFORM.name());
     return this.mchInfoMapper.selectByExample(example);
   }

   public MchInfo getSettleParentMchInfo(MchInfo mchInfo) throws NoSuchMethodException {
     String parentMchId = mchInfo.getParentMchId();
     MchInfo parent = selectMchInfo(parentMchId);
     if (parent == null) {
       return mchInfo;
     }

     if (parent.getType().equals(MchTypeEnum.MCH.name())) {
       return getSettleParentMchInfo(parent);
     }
     return parent;
   }

   public MchInfo getRootMchInfo(MchInfo mchInfo) throws NoSuchMethodException {
     if (mchInfo == null) {
       return null;
     }
     if (StringUtils.isBlank(mchInfo.getParentMchId())) {
       return mchInfo;
     }
     MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
     if (parent != null) {
       return getRootMchInfo(parent);
     }
     return mchInfo;
   }


   public String getSettleParamsIfNullUseParentParams(MchInfo mchInfo) throws NoSuchMethodException {
     if (StringUtils.isBlank(mchInfo.getSettleParams())) {
       if (mchInfo.getParentMchId() == null) {
         return null;
       }
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       if (parent == null)
         return null;
       if (StringUtils.isNotBlank(parent.getSettleParams())) {
         return parent.getSettleParams();
       }
       return getSettleParamsIfNullUseParentParams(parent);
     }
     return mchInfo.getSettleParams();
   }

   public String getSettleTypeIfNullUseParent(MchInfo mchInfo) throws NoSuchMethodException {
     if (StringUtils.isBlank(mchInfo.getSettleType())) {
       if (mchInfo.getParentMchId() == null) {
         return null;
       }
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       if (parent == null)
         return null;
       if (StringUtils.isNotBlank(parent.getSettleType())) {
         String str = parent.getSettleType();
         JSONObject jSONObject = JSONObject.parseObject(str);
         if (StringUtils.isEmpty(jSONObject.getString("cycle"))) {
           parent = selectByPrimaryKey(mchInfo.getParentMchId());
           return getSettleTypeIfNullUseParent(parent, jSONObject);
         }
         return parent.getSettleType();
       }
       return getSettleTypeIfNullUseParent(parent);
     }
     String settleType = mchInfo.getSettleType();
     JSONObject object = JSONObject.parseObject(settleType);
     if (StringUtils.isEmpty(object.getString("cycle"))) {
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       return getSettleTypeIfNullUseParent(parent, object);
     }
     return mchInfo.getSettleType();
   }

   public String getSettleTypeIfNullUseParent(MchInfo mchInfo, JSONObject src) throws NoSuchMethodException {
     if (StringUtils.isBlank(mchInfo.getSettleType())) {
       if (mchInfo.getParentMchId() == null) {
         return null;
       }
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       if (parent == null)
         return null;
       if (StringUtils.isNotBlank(parent.getSettleType())) {
         return parent.getSettleType();
       }
       return getSettleTypeIfNullUseParent(parent);
     }
     String settleType = mchInfo.getSettleType();
     JSONObject object = JSONObject.parseObject(settleType);
     if (StringUtils.isEmpty(object.getString("cycle"))) {
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       return getSettleTypeIfNullUseParent(parent, object);
     }
     src.put("cycle", object.getString("cycle"));
     return src.toJSONString();
   }

   public String getSettleCycleIfNullUseParent(MchInfo mchInfo) throws NoSuchMethodException {
     return getSettleTypeParamIfNullUseParent(mchInfo, "cycle");
   }

   public String getSettleTnIfNullUseParent(MchInfo mchInfo) throws NoSuchMethodException {
     return getSettleTypeParamIfNullUseParent(mchInfo, "tn");
   }

   public String getSettleParamsStateIfNullUseParent(MchInfo mchInfo) throws NoSuchMethodException {
     return getSettleTypeParamIfNullUseParent(mchInfo, "state");
   }

   public String getSettleTypeParamIfNullUseParent(MchInfo mchInfo, String key) throws NoSuchMethodException {
     if (StringUtils.isBlank(mchInfo.getSettleType())) {
       if (mchInfo.getParentMchId() == null) {
         return null;
       }
       MchInfo parent = selectByPrimaryKey(mchInfo.getParentMchId());
       if (parent == null)
         return null;
       if (StringUtils.isNotBlank(parent.getSettleType())) {
         return JSONObject.parseObject(parent.getSettleType()).getString(key);
       }
       return getSettleTypeIfNullUseParent(parent);
     }
     return JSONObject.parseObject(mchInfo.getSettleType()).getString(key);
   }

   public String getSettlePoundageRate(MchInfo mchInfo) throws NoSuchMethodException {
     return getSettleParamsIfNullUseParentParams(mchInfo);
   }

   public Double getSettlePoundageRateByPayChannel(MchInfo mchInfo, String payChannel) throws NoSuchMethodException {
     JSONObject obj = JSONObject.parseObject(getSettleParamsIfNullUseParentParams(mchInfo));
     Double pr = obj.getDouble(payChannel);
     if (pr != null) {
       return pr;
     }
     return obj.getDouble("pr");
   }

   public Integer getSettleTn(MchInfo mchInfo) throws NoSuchMethodException {
     String settleType = getSettleTypeIfNullUseParent(mchInfo);
     JSONObject obj = JSONObject.parseObject(settleType);
     return obj.getInteger("tn");
   }

   public int countByExample(MchInfoExample example) {
     return this.mchInfoMapper.countByExample(example);
   }

   public int deleteByExample(MchInfoExample example) {
     return this.mchInfoMapper.deleteByExample(example);
   }

   public int deleteByPrimaryKey(String mchId) throws NoSuchMethodException {
     int res = this.mchInfoMapper.deleteByPrimaryKey(mchId);
     if (res > 0) {
       deleteSimpleCache(mchId);
     }
     return res;
   }

   public int insert(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.insert(record));
     if (res.intValue() > 0)
     {
       putToSimpleCache(record);
     }
     return res.intValue();
   }

   public int insertSelective(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.insertSelective(record));
     if (res.intValue() > 0) {
       putToSimpleCache(record);
     }
     return res.intValue();
   }

   public int insertSelectiveForAudit(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.insertSelectiveForAudit(record));
     return res.intValue();
   }

   public List<MchInfo> selectByExample(MchInfoExample example) {
     return this.mchInfoMapper.selectByExample(example);
   }

   public List<MchInfo> selectByExampleFromAudit(MchInfoExample example) {
     return this.mchInfoMapper.selectByExampleFromAudit(example);
   }

   public MchInfo selectByPrimaryKey(String mchId) throws NoSuchMethodException {
     MchInfo res = (MchInfo)getSimpleDataByPrimaryKey(mchId);
     if (res != null) {
       return res;
     }
     MchInfo mchInfo = this.mchInfoMapper.selectByPrimaryKey(mchId);

     if (mchInfo != null) {
       putToSimpleCache(mchInfo);
     }
     return mchInfo;
   }

   public MchInfo selectByPrimaryKeyForAudit(String mchId) {
     MchInfo mchInfo = this.mchInfoMapper.selectByPrimaryKeyForAudit(mchId);
     return mchInfo;
   }

   public int updateByExampleSelective(MchInfo record, MchInfoExample example) {
     Integer res = Integer.valueOf(this.mchInfoMapper.updateByExampleSelective(record, example));
     return res.intValue();
   }

   public int updateByExample(MchInfo record, MchInfoExample example) {
     String key = Integer.toString(example.hashCode());
     Integer res = Integer.valueOf(this.mchInfoMapper.updateByExample(record, example));
     return res.intValue();
   }

   public int updateByPrimaryKeySelective(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.updateByPrimaryKeySelective(record));
     if (res.intValue() > 0)
     {
       updateSimpleCache(record);
     }
     return res.intValue();
   }

   public int updateByPrimaryKeySelectiveForAudit(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.updateByPrimaryKeySelectiveForAudit(record));
     return res.intValue();
   }

   public int updateByPrimaryKey(MchInfo record) {
     Integer res = Integer.valueOf(this.mchInfoMapper.updateByPrimaryKey(record));
     if (res.intValue() > 0)
     {
       updateSimpleCache(record);
     }
     return res.intValue();
   }

   private String getHashKey(MchInfo mchInfo) {
     return mchInfo.getMchId() + ":" + mchInfo
       .getExternalId() + ":" + mchInfo
       .getParentMchId() + ":" + mchInfo
       .getParentExternalId() + ":" + mchInfo
       .getType();
   }

   private String getHashKeyFromExample(MchInfo mchInfo) {
     StringBuffer stringBuffer = new StringBuffer("*");
     if (mchInfo != null) {
       if (StringUtils.isNotBlank(mchInfo.getMchId())) {
         stringBuffer.append(mchInfo.getMchId() + ":*");
       }
       if (StringUtils.isNotBlank(mchInfo.getExternalId())) {
         stringBuffer.append("*:" + mchInfo.getExternalId() + ":*");
       }
       if (StringUtils.isNotBlank(mchInfo.getParentMchId())) {
         stringBuffer.append("*:" + mchInfo.getParentMchId() + ":*");
       }
       if (StringUtils.isNotBlank(mchInfo.getParentExternalId())) {
         stringBuffer.append("*:" + mchInfo.getParentExternalId() + ":*");
       }
       if (mchInfo.getType() != null && !"-99".equals(mchInfo.getType())) {
         stringBuffer.append(mchInfo.getType());
       }
     }
     return stringBuffer.toString();
   }

   public String getNameSpace() {
     return "MCH-INFO";
   }

   public String getSimplePrimaryKey(MchInfo data) {
     return data.getMchId();
   }

   public List<String> getPrimaryKeyList(MchInfo data) {
     return null;
   }

   public String getSimpleUnionKey(MchInfo data) {
     return data.getExternalId() + ":" + data.getType();
   }

   public List<TreeMap<String, String>> getUnionKeyList(MchInfo data) {
     return null;
   }

   public Map<String, Double> getScoreMap(MchInfo data) {
     return null;
   }

   public Map<String, String> getGroupMap(MchInfo data) {
     return null;
   }


   public Map<String, SortTypeEnum> getDefaultSortMap() {
     return null;
   }


   public Class<MchInfo> getType() {
     return MchInfo.class;
   }

   public String getQueryExpFromExample(MchInfo data) {
     return null;
   }
 }

