 package org.hlpay.common.domain;

 import java.util.HashMap;
 import java.util.Map;

 public class RpcBaseResult
   extends RpcBaseParam
 {
   protected String rpcRetCode;
   protected String rpcRetMsg;
   protected String dbErrorCode;
   protected String dbErrorMsg;

   public String getRpcRetCode() {
     return this.rpcRetCode;
   }

   public void setRpcRetCode(String rpcRetCode) {
     this.rpcRetCode = rpcRetCode;
   }

   public String getRpcRetMsg() {
     return this.rpcRetMsg;
   }

   public void setRpcRetMsg(String rpcRetMsg) {
     this.rpcRetMsg = rpcRetMsg;
   }


   public String toString() {
     StringBuffer sb = new StringBuffer("RpcBaseResult{");
     sb.append("rpcSrcSysId='").append(this.rpcSrcSysId).append('\'');
     sb.append(", rpcDateTime='").append(this.rpcDateTime).append('\'');
     sb.append(", rpcSeqNo='").append(this.rpcSeqNo).append('\'');
     sb.append(", rpcSignType=").append(this.rpcSignType);
     sb.append(", rpcSign='").append(this.rpcSign).append('\'');
     sb.append(", bizSeqNo='").append(this.bizSeqNo).append('\'');
     sb.append(", bizSign='").append(this.bizSign).append('\'');
     sb.append(", rpcRetCode='").append(this.rpcRetCode).append('\'');
     sb.append(", rpcRetMsg='").append(this.rpcRetMsg).append('\'');
     sb.append(", dbErrorCode='").append(this.dbErrorCode).append('\'');
     sb.append(", dbErrorMsg='").append(this.dbErrorMsg).append('\'');
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
     rpcMap.put("rpcRetCode", this.rpcRetCode);
     rpcMap.put("rpcRetMsg", this.rpcRetMsg);
     rpcMap.put("dbErrorCode", this.dbErrorCode);
     rpcMap.put("dbErrorMsg", this.dbErrorMsg);
     return rpcMap;
   }
 }





