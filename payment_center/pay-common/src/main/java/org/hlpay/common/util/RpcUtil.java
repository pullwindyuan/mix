 package org.hlpay.common.util;

 import java.util.HashMap;
 import java.util.Map;
 import org.hlpay.common.domain.BaseParam;
 import org.hlpay.common.domain.RpcBaseParam;
 import org.hlpay.common.enumm.RetEnum;








 public class RpcUtil
 {
   public static BaseParam getBaseParam(Map<String, Object> paramMap) throws NoSuchMethodException {
     if (paramMap == null || paramMap.isEmpty()) {
       return null;
     }
     BaseParam baseParam = BeanConvertUtils.<BaseParam>map2Bean(paramMap, BaseParam.class);
     paramMap.remove("rpcSrcSysId");
     paramMap.remove("rpcDateTime");
     paramMap.remove("rpcSeqNo");
     paramMap.remove("rpcSignType");
     paramMap.remove("rpcSign");
     paramMap.remove("bizSeqNo");
     paramMap.remove("bizSign");
     baseParam.setBizParamMap(paramMap);
     return baseParam;
   }







   public static Map<String, Object> createBizResult(RpcBaseParam baseParam, Object obj) {
     Map<String, Object> resultMap = createResultMap(baseParam, RetEnum.RET_SUCCESS);
     resultMap.put("bizResult", obj);
     return resultMap;
   }

   public static Map<String, Object> createBizResultWithDBError(RpcBaseParam baseParam, Object obj, String dbErrorCode, String dbErrorMsg) {
     Map<String, Object> resultMap = createResultMapWithDBError(baseParam, RetEnum.RET_SUCCESS, dbErrorCode, dbErrorMsg);
     resultMap.put("bizResult", obj);
     return resultMap;
   }







   public static Map<String, Object> createFailResult(RpcBaseParam rpcBaseParam, RetEnum retEnum) {
     if (retEnum == null) {
       retEnum = RetEnum.RET_PARAM_NOT_FOUND;
     }
     return createResultMap(rpcBaseParam, retEnum);
   }


   public static Map<String, Object> createFailResultWithDBError(RpcBaseParam rpcBaseParam, RetEnum retEnum, String dbErrorCode, String dbErrorMsg) {
     if (retEnum == null) {
       retEnum = RetEnum.RET_PARAM_NOT_FOUND;
     }
     return createResultMapWithDBError(rpcBaseParam, retEnum, dbErrorCode, dbErrorMsg);
   }

   private static Map<String, Object> createResultMap(RpcBaseParam rpcBaseParam, RetEnum retEnum) {
     Map<String, Object> resultMap = null;
     if (rpcBaseParam != null) {
       resultMap = rpcBaseParam.convert2Map();
     } else {
       resultMap = new HashMap<>();
     }
     resultMap.put("rpcRetCode", retEnum.getCode());
     resultMap.put("rpcRetMsg", retEnum.getMessage());
     return resultMap;
   }


   private static Map<String, Object> createResultMapWithDBError(RpcBaseParam rpcBaseParam, RetEnum retEnum, String dbErrorCode, String dbErrorMsg) {
     Map<String, Object> resultMap = null;
     if (rpcBaseParam != null) {
       resultMap = rpcBaseParam.convert2Map();
     } else {
       resultMap = new HashMap<>();
     }
     resultMap.put("rpcRetCode", retEnum.getCode());
     resultMap.put("rpcRetMsg", retEnum.getMessage());
     resultMap.put("dbErrorCode", dbErrorCode);
     resultMap.put("dbErrorMsg", dbErrorMsg);
     return resultMap;
   }

   public static String createBaseParam(Map<String, Object> paramMap) {
     BaseParam baseParam = new BaseParam("102", "rpc-src-sys-vvlive-config-key", "cf");
     baseParam.setBizParamMap(paramMap);
     return baseParam.toJson();
   }


   public static String mkRet(Map<String, Object> result) {
     if (result == null) return null;
     String retCode = (String)result.get("rpcRetCode");
     if ("0000".equals(retCode)) {
       if (result.get("bizResult") == null) return null;
       return result.get("bizResult").toString();
     }
     return null;
   }

   public static Boolean isSuccess(Map<String, Object> result) {
     if (result == null) return Boolean.valueOf(false);
     String retCode = (String)result.get("rpcRetCode");
     if ("0000".equals(retCode) && result.get("bizResult") != null) return Boolean.valueOf(true);
     return Boolean.valueOf(false);
   }
 }
