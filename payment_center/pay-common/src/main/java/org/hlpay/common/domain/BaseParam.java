 package org.hlpay.common.domain;

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.common.enumm.RpcSignTypeEnum;
 import org.hlpay.common.util.BizSequenceUtils;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.JsonUtil;
 import org.hlpay.common.util.RandomStrUtils;
 import org.hlpay.common.util.RpcSignUtils;



 public class BaseParam
   extends RpcBaseParam
 {
   private Map<String, Object> bizParamMap;

   public BaseParam() {}

   public BaseParam(String rpcSrcSysId, String rpcSignKey, String bizSeqNoPrefix) {
     this.rpcSrcSysId = rpcSrcSysId;
     this.rpcDateTime = DateUtils.getCurrentTimeStrDefault();
     this.rpcSeqNo = RandomStrUtils.getInstance().getRandomString();
     this.rpcSignType = RpcSignTypeEnum.SHA1_SIGN.getCode();
     this.bizSeqNo = BizSequenceUtils.getInstance().generateBizSeqNo(bizSeqNoPrefix);
     StringBuffer decriptBuffer = new StringBuffer();
     decriptBuffer.append(rpcSignKey)
       .append(this.rpcSrcSysId)
       .append(this.rpcDateTime)
       .append(this.rpcSignType)
       .append(this.bizSeqNo);
     this.rpcSign = RpcSignUtils.sha1(decriptBuffer.toString());
     this.bizParamMap = new HashMap<>();
   }

   public BaseParam(String rpcSrcSysId, String rpcSignKey, String bizSeqNoPrefix, String bizSign) {
     this.rpcSrcSysId = rpcSrcSysId;
     this.rpcDateTime = DateUtils.getCurrentTimeStrDefault();
     this.rpcSeqNo = RandomStrUtils.getInstance().getRandomString();
     this.rpcSignType = RpcSignTypeEnum.SHA1_SIGN.getCode();
     this.bizSeqNo = BizSequenceUtils.getInstance().generateBizSeqNo(bizSeqNoPrefix);
     this.bizSign = bizSign;
     StringBuffer decriptBuffer = new StringBuffer();
     decriptBuffer.append(rpcSignKey)
       .append(this.rpcSrcSysId)
       .append(this.rpcDateTime)
       .append(this.rpcSignType)
       .append(this.bizSeqNo)
       .append(this.bizSign);
     this.rpcSign = RpcSignUtils.sha1(decriptBuffer.toString());
     this.bizParamMap = new HashMap<>();
   }

   public Map<String, Object> getBizParamMap() {
     return this.bizParamMap;
   }

   public void setBizParamMap(Map<String, Object> bizParamMap) {
     this.bizParamMap = bizParamMap;
   }

   public String toJson() {
     return JsonUtil.object2Json(this);
   }


   public String toString() {
     StringBuffer sb = new StringBuffer("BaseParam{");
     sb.append("rpcSrcSysId='").append(this.rpcSrcSysId).append('\'');
     sb.append(", rpcDateTime='").append(this.rpcDateTime).append('\'');
     sb.append(", rpcSeqNo='").append(this.rpcSeqNo).append('\'');
     sb.append(", rpcSignType=").append(this.rpcSignType);
     sb.append(", rpcSign='").append(this.rpcSign).append('\'');
     sb.append(", bizSeqNo='").append(this.bizSeqNo).append('\'');
     sb.append(", bizSign='").append(this.bizSign).append('\'');
     sb.append(", bizParamMap=").append(this.bizParamMap);
     sb.append('}');
     return sb.toString();
   }

   public boolean isNullValue(String key) {
     Object objValue = this.bizParamMap.get(key);
     return (objValue == null || StringUtils.isBlank(objValue.toString()));
   }

   public boolean isInvalidMapValue(Object... excludeKeys) {
     if (this.bizParamMap == null || this.bizParamMap.isEmpty()) {
       return true;
     }
     List<Object> list = Arrays.asList(excludeKeys);
     for (Map.Entry<String, Object> entry : this.bizParamMap.entrySet()) {
       if (list.contains(entry.getKey())) {
         continue;
       }
       Object value = entry.getValue();
       if (value != null) {
         if (value instanceof String) {
           if (StringUtils.isNotBlank(value.toString()))
             return false;
           continue;
         }
         return false;
       }
     }

     return true;
   }

   public Long getLongBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     return Long.valueOf(this.bizParamMap.get(name).toString());
   }

   public Integer getIntBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     return Integer.valueOf(this.bizParamMap.get(name).toString());
   }

   public Integer getIntBizParam(String name, int defaultValue) {
     if (isNullValue(name)) {
       return Integer.valueOf(defaultValue);
     }
     return Integer.valueOf(this.bizParamMap.get(name).toString());
   }

   public Short getShortBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     return Short.valueOf(this.bizParamMap.get(name).toString());
   }

   public String getStringBizParam(String name, String defaultValue) {
     if (isNullValue(name)) {
       return defaultValue;
     }
     return this.bizParamMap.get(name).toString();
   }

   public String getStringBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     return this.bizParamMap.get(name).toString();
   }

   public List<Short> getShortListBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     List<Number> numberList = (List<Number>)this.bizParamMap.get(name);
     if (numberList == null) {
       return null;
     }
     List<Short> shortList = new ArrayList<>(numberList.size());
     for (Number number : numberList) {
       Short value = Short.valueOf(number.shortValue());
       shortList.add(value);
     }
     return shortList;
   }

   public List<Integer> getIntegerListBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     List<Number> numberList = (List<Number>)this.bizParamMap.get(name);
     if (numberList == null) {
       return null;
     }
     List<Integer> integerList = new ArrayList<>(numberList.size());
     for (Number number : numberList) {
       Integer value = Integer.valueOf(number.intValue());
       integerList.add(value);
     }
     return integerList;
   }

   public List<Long> getLongListBizParam(String name) {
     if (isNullValue(name)) {
       return null;
     }
     List<Number> numberList = (List<Number>)this.bizParamMap.get(name);
     if (numberList == null) {
       return null;
     }
     List<Long> longList = new ArrayList<>(numberList.size());
     for (Number number : numberList) {
       Long value = Long.valueOf(number.longValue());
       longList.add(value);
     }
     return longList;
   }

   public static void main(String[] args) {
     BaseParam baseParam = new BaseParam();
     Map<String, Object> map = new HashMap<>();
     map.put("a", null);
     map.put("b", "");
     baseParam.setBizParamMap(map);
     System.out.println(baseParam.isInvalidMapValue(new Object[] { "" }));
   }
 }
