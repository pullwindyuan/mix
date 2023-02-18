 package org.hlpay.common.domain;

 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.Map;
 import org.hlpay.common.enumm.RpcSignTypeEnum;
 import org.hlpay.common.util.BeanConvertUtils;
 import org.hlpay.common.util.BizSequenceUtils;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.RandomStrUtils;
 import org.hlpay.common.util.RpcSignUtils;

 public class RpcBaseParam
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   protected String rpcSrcSysId;
   protected String rpcDateTime;
   protected String rpcSeqNo;
   protected Integer rpcSignType;
   protected String rpcSign;
   protected String bizSeqNo;
   protected String bizSign;

   public RpcBaseParam() {}

   public RpcBaseParam(String rpcSrcSysId, String rpcSignKey, String bizSeqNoPrefix) {
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
   }

   public RpcBaseParam(String rpcSrcSysId, String rpcSignKey, String bizSeqNoPrefix, String bizSign) {
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
   }

   public String getRpcSrcSysId() {
     return this.rpcSrcSysId;
   }

   public void setRpcSrcSysId(String rpcSrcSysId) {
     this.rpcSrcSysId = rpcSrcSysId;
   }

   public String getRpcDateTime() {
     return this.rpcDateTime;
   }

   public void setRpcDateTime(String rpcDateTime) {
     this.rpcDateTime = rpcDateTime;
   }

   public String getRpcSeqNo() {
     return this.rpcSeqNo;
   }

   public void setRpcSeqNo(String rpcSeqNo) {
     this.rpcSeqNo = rpcSeqNo;
   }

   public Integer getRpcSignType() {
     return this.rpcSignType;
   }

   public void setRpcSignType(Integer rpcSignType) {
     this.rpcSignType = rpcSignType;
   }

   public String getBizSeqNo() {
     return this.bizSeqNo;
   }

   public void setBizSeqNo(String bizSeqNo) {
     this.bizSeqNo = bizSeqNo;
   }

   public String getBizSign() {
     return this.bizSign;
   }

   public void setBizSign(String bizSign) {
     this.bizSign = bizSign;
   }

   public String getRpcSign() {
     return this.rpcSign;
   }

   public void setRpcSign(String rpcSign) {
     this.rpcSign = rpcSign;
   }

   public static RpcBaseParam convert2Bean(Map<String, Object> map) throws NoSuchMethodException {
     return (RpcBaseParam)BeanConvertUtils.map2Bean(map, RpcBaseParam.class);
   }


   public String toString() {
     StringBuffer sb = new StringBuffer("RpcBaseParam{");
     sb.append("rpcSrcSysId='").append(this.rpcSrcSysId).append('\'');
     sb.append(", rpcDateTime='").append(this.rpcDateTime).append('\'');
     sb.append(", rpcSeqNo='").append(this.rpcSeqNo).append('\'');
     sb.append(", rpcSignType=").append(this.rpcSignType);
     sb.append(", rpcSign='").append(this.rpcSign).append('\'');
     sb.append(", bizSeqNo='").append(this.bizSeqNo).append('\'');
     sb.append(", bizSign='").append(this.bizSign).append('\'');
     sb.append('}');
     return sb.toString();
   }

   public Map<String, Object> convert2Map() {
     Map<String, Object> rpcMap = new HashMap<>();
     rpcMap.put("rpcSrcSysId", this.rpcSrcSysId);
     rpcMap.put("rpcDateTime", this.rpcDateTime);
     rpcMap.put("rpcSeqNo", this.rpcSeqNo);
     rpcMap.put("rpcSignType", this.rpcSignType);
     rpcMap.put("rpcSign", this.rpcSign);
     rpcMap.put("bizSeqNo", this.bizSeqNo);
     rpcMap.put("bizSign", this.bizSign);
     return rpcMap;
   }
 }
