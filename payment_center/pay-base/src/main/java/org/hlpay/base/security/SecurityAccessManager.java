 package org.hlpay.base.security;

 import com.alibaba.fastjson.JSONObject;
 import com.xiaoleilu.hutool.util.ObjectUtil;
 import com.xiaoleilu.hutool.util.RandomUtil;
 import java.io.Serializable;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
 import java.util.concurrent.TimeUnit;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.bo.AuthGenAccessKeyBo;
 import org.hlpay.base.bo.AuthPrivateKeyBo;
 import org.hlpay.base.cache.CacheService;
 import org.hlpay.base.model.AccessInfo;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.vo.AuthPrivateKeyVo;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.SortTypeEnum;
 import org.hlpay.common.util.DigestUtil;
 import org.hlpay.common.util.IPUtility;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service
 public class SecurityAccessManager extends CacheService<AccessInfo> {
   protected final Logger logger = LoggerFactory.getLogger(getClass());
   @Autowired
   private MchInfoService mchInfoService;

   public CommonResult<AuthPrivateKeyVo> genCustomerPrivateKey(AuthPrivateKeyBo authPrivateKeyBO) throws NoSuchMethodException {
     this.logger.info("===获取访问私有privateKey");
     String params = authPrivateKeyBO.getParams();
     JSONObject paramsObj = JSONObject.parseObject(params);
     if (ObjectUtil.isNull(paramsObj)) {
       return CommonResult.error("params不能为空");
     }
     if (validateParams(paramsObj, true) instanceof String) {
       return CommonResult.error("参数校验不通过");
     }


     String appId = paramsObj.getString("appid");
     String bizId = paramsObj.getString("bizId");

     String userNo = paramsObj.getString("userNo");
     String ip = paramsObj.getString("ip");
     String mchId = paramsObj.getString("mchId");
     String privateKey = RandomUtil.randomString(18);

     String randomPrivateKey = RandomUtil.randomNumbers(16);
     AccessInfo accessInfo = new AccessInfo();
     accessInfo.setAccessKey(privateKey);
     accessInfo.setAppId(appId);
     accessInfo.setIp(ip);
     accessInfo.setOrg(mchId);
     accessInfo.setUserNo(userNo);
     putToSimpleCacheExpireAt(accessInfo, Long.valueOf(2L), TimeUnit.HOURS);

     AuthPrivateKeyVo authPrivateKeyVo = new AuthPrivateKeyVo();
     authPrivateKeyVo.setPrivatekey(randomPrivateKey);
     return CommonResult.success((Serializable)authPrivateKeyVo);
   }

   public CommonResult<AuthPrivateKeyVo> genSuperPrivateKey(AuthGenAccessKeyBo authCourseGenPrivateKeyBO) throws NoSuchMethodException {
     this.logger.info("===获取管理操作秘钥privateKey");

     String params = authCourseGenPrivateKeyBO.getParams();
     JSONObject paramsObj = JSONObject.parseObject(params);
     if (ObjectUtil.isNull(paramsObj)) {
       return CommonResult.error("params不能为空");
     }

     String appId = paramsObj.getString("appid");

     MchInfo appInfo = this.mchInfoService.selectMchInfo(appId);
     if (appInfo == null) {
       appInfo = new MchInfo();
       appInfo.setReqKey("16525ad4b35842679c17806ee86639a2");
     }
     this.logger.info("===paramsObj=" + paramsObj.toString());
     this.logger.info("===privateKey=" + appInfo.getReqKey());
     if (validateGenParams(paramsObj, true) instanceof String) {
       return CommonResult.error("参数校验不通过");
     }
     String bizId = paramsObj.getString("bizId");
     String ip = paramsObj.getString("ip");
     String userNo = paramsObj.getString("userNo");
     String org = paramsObj.getString("org");
     String privateKey = RandomUtil.randomNumbers(16);
     this.logger.info("===genPrivateKey=" + privateKey);
     AccessInfo accessInfo = new AccessInfo();
     accessInfo.setAccessKey(privateKey);
     accessInfo.setAppId(appId);
     accessInfo.setBizId(bizId);
     accessInfo.setIp(ip);
     accessInfo.setOrg(org);
     accessInfo.setUserNo(userNo);
     putToSimpleCacheExpireAt(accessInfo, Long.valueOf(2L), TimeUnit.HOURS);





     AuthPrivateKeyVo authPrivateKeyVo = new AuthPrivateKeyVo();
     authPrivateKeyVo.setPrivatekey(privateKey);
     return CommonResult.success((Serializable)authPrivateKeyVo);
   }







   private Object validateParams(JSONObject params, boolean checkSign) throws NoSuchMethodException {
     String accessKey, errorMessage = "";

     String sign = params.getString("sign");
     if (ObjectUtil.isNull(sign)) {
       errorMessage = "签名不能为空";
     }
     Long userNo = params.getLong("userNo");
     if (ObjectUtil.isNull(userNo)) {
       errorMessage = "userNo不能为空";
     }
     String mchId = params.getString("appid");
     if (ObjectUtil.isNull(mchId)) {
       errorMessage = "mchId不能为空";
     }
     String bizId = params.getString("bizId");
     if (StringUtils.isBlank(bizId)) {
       errorMessage = "bizId不能为空";
     }

     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);

     if (mchInfo == null) {
       errorMessage = "商户不正确";
       this.logger.info("商户不正确");
       accessKey = "16525ad4b35842679c17806ee86639a2";
     } else {
       accessKey = mchInfo.getReqKey();
     }

     if (StringUtils.isBlank(accessKey)) {
       errorMessage = "授权主体accessKey不存在";
     }

     if (DigestUtil.verifySign((Map)params, accessKey)) {
       return Boolean.valueOf(true);
     }
     return errorMessage;
   }







   private Object validateGenParams(JSONObject params, boolean checkSign) throws NoSuchMethodException {
     String accessKey, errorMessage = "";

     String mchId = params.getString("appid");
     if (ObjectUtil.isNull(mchId)) {
       errorMessage = "appid不能为空";
       this.logger.info("appid不能为空");
     }
     String ip = params.getString("ip");
     if (ObjectUtil.isNull(ip)) {
       errorMessage = "ip不能为空";
       this.logger.info("ip不能为空");
     }

     String sign = params.getString("sign");
     if (ObjectUtil.isNull(sign)) {
       errorMessage = "签名不能为空";
       this.logger.info("签名不能为空");
     }
     Long userNo = params.getLong("userNo");
     if (ObjectUtil.isNull(userNo)) {
       errorMessage = "userNo不能为空";
       this.logger.info("userNo不能为空");
     }
     MchInfo mchInfo = this.mchInfoService.selectMchInfo(mchId);

     if (mchInfo == null) {
       errorMessage = "商户不正确";
       this.logger.info("商户不正确");
       accessKey = "16525ad4b35842679c17806ee86639a2";
     } else {
       accessKey = mchInfo.getReqKey();
     }

     if (StringUtils.isBlank(accessKey)) {
       errorMessage = "授权主体accessKey不存在";
       this.logger.info("授权主体accessKey不存在");
     }

     if (DigestUtil.verifySign((Map)params, accessKey)) {
       return Boolean.valueOf(true);
     }
     return errorMessage;
   }








   public boolean checkAccessIPWhiteList(HttpServletRequest request) throws Exception {
     return true;
   }


   public boolean checkAccessLocalIP(HttpServletRequest request) throws Exception {
     String ip = IPUtility.getIpAddr(request);
     if (ip.equals("127.0.0.1"))
       this.logger.info("授权主体IP匹配成功 : " + ip);
     return true;
   }


   public Object checkAccessKey(String accessKey, String userNo, String orgId, HttpServletRequest request) throws Exception {
     if (StringUtils.isNotEmpty(accessKey)) {
       deleteSimpleCache(accessKey);
     }
     return Boolean.valueOf(true);
   }

  public String getNameSpace() {
    return "ACCESS-INFO";
  }

   public String getSimplePrimaryKey(AccessInfo data) {
     return data.getAccessKey();
   }

   public List<String> getPrimaryKeyList(AccessInfo data) {
     return null;
   }

 public String getSimpleUnionKey(AccessInfo data) {
   return data.getAppId() + ":" + data
     .getUserNo() + ":" + data
     .getBizId();
 }

   public List<TreeMap<String, String>> getUnionKeyList(AccessInfo data) {
     return null;
   }

   public Map<String, Double> getScoreMap(AccessInfo data) {
     return null;
   }

  public Map<String, String> getGroupMap(AccessInfo data) {
    return null;
  }

   public Map<String, SortTypeEnum> getDefaultSortMap() {
     return null;
   }


   public Class<AccessInfo> getType() {
     return null;
   }

   public String getQueryExpFromExample(AccessInfo data) {
     return null;
   }
 }





