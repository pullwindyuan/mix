 package org.hlpay.base.model;
 public class MchInfo implements Serializable {
   private String mchId;
   private String parentMchId;
   private String externalId;
   private String parentExternalId;
   private String name;
   private String type;
   private String reqKey;
   private String resKey;
   private Byte state;
   private Byte settleParamsState;

   public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchInfo)) return false;  MchInfo other = (MchInfo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$parentMchId = getParentMchId(), other$parentMchId = other.getParentMchId(); if ((this$parentMchId == null) ? (other$parentMchId != null) : !this$parentMchId.equals(other$parentMchId)) return false;  Object this$externalId = getExternalId(), other$externalId = other.getExternalId(); if ((this$externalId == null) ? (other$externalId != null) : !this$externalId.equals(other$externalId)) return false;  Object this$parentExternalId = getParentExternalId(), other$parentExternalId = other.getParentExternalId(); if ((this$parentExternalId == null) ? (other$parentExternalId != null) : !this$parentExternalId.equals(other$parentExternalId)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$reqKey = getReqKey(), other$reqKey = other.getReqKey(); if ((this$reqKey == null) ? (other$reqKey != null) : !this$reqKey.equals(other$reqKey)) return false;  Object this$resKey = getResKey(), other$resKey = other.getResKey(); if ((this$resKey == null) ? (other$resKey != null) : !this$resKey.equals(other$resKey)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$settleParamsState = getSettleParamsState(), other$settleParamsState = other.getSettleParamsState(); if ((this$settleParamsState == null) ? (other$settleParamsState != null) : !this$settleParamsState.equals(other$settleParamsState)) return false;  Object this$createTime = getCreateTime(), other$createTime = other.getCreateTime(); if ((this$createTime == null) ? (other$createTime != null) : !this$createTime.equals(other$createTime)) return false;  Object this$updateTime = getUpdateTime(), other$updateTime = other.getUpdateTime(); if ((this$updateTime == null) ? (other$updateTime != null) : !this$updateTime.equals(other$updateTime)) return false;  Object this$securityUrl = getSecurityUrl(), other$securityUrl = other.getSecurityUrl(); if ((this$securityUrl == null) ? (other$securityUrl != null) : !this$securityUrl.equals(other$securityUrl)) return false;  Object this$settleType = getSettleType(), other$settleType = other.getSettleType(); if ((this$settleType == null) ? (other$settleType != null) : !this$settleType.equals(other$settleType)) return false;  Object this$settlePoundageRate = getSettlePoundageRate(), other$settlePoundageRate = other.getSettlePoundageRate(); if ((this$settlePoundageRate == null) ? (other$settlePoundageRate != null) : !this$settlePoundageRate.equals(other$settlePoundageRate)) return false;  Object this$settlePoundageRateSets = getSettlePoundageRateSets(), other$settlePoundageRateSets = other.getSettlePoundageRateSets(); if ((this$settlePoundageRateSets == null) ? (other$settlePoundageRateSets != null) : !this$settlePoundageRateSets.equals(other$settlePoundageRateSets)) return false;  Object this$settleParamsTn = getSettleParamsTn(), other$settleParamsTn = other.getSettleParamsTn(); if ((this$settleParamsTn == null) ? (other$settleParamsTn != null) : !this$settleParamsTn.equals(other$settleParamsTn)) return false;  Object this$settleCycle = getSettleCycle(), other$settleCycle = other.getSettleCycle(); if ((this$settleCycle == null) ? (other$settleCycle != null) : !this$settleCycle.equals(other$settleCycle)) return false;  Object this$settleParams = getSettleParams(), other$settleParams = other.getSettleParams(); return !((this$settleParams == null) ? (other$settleParams != null) : !this$settleParams.equals(other$settleParams)); } private Date createTime; private Date updateTime; private String securityUrl; private String settleType; @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false) private Integer settlePoundageRate; @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false) private String settlePoundageRateSets; @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false) private Integer settleParamsTn; private String settleCycle; private String settleParams; private static final long serialVersionUID = 1L; protected boolean canEqual(Object other) { return other instanceof MchInfo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $parentMchId = getParentMchId(); result = result * 59 + (($parentMchId == null) ? 43 : $parentMchId.hashCode()); Object $externalId = getExternalId(); result = result * 59 + (($externalId == null) ? 43 : $externalId.hashCode()); Object $parentExternalId = getParentExternalId(); result = result * 59 + (($parentExternalId == null) ? 43 : $parentExternalId.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $reqKey = getReqKey(); result = result * 59 + (($reqKey == null) ? 43 : $reqKey.hashCode()); Object $resKey = getResKey(); result = result * 59 + (($resKey == null) ? 43 : $resKey.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $settleParamsState = getSettleParamsState(); result = result * 59 + (($settleParamsState == null) ? 43 : $settleParamsState.hashCode()); Object $createTime = getCreateTime(); result = result * 59 + (($createTime == null) ? 43 : $createTime.hashCode()); Object $updateTime = getUpdateTime(); result = result * 59 + (($updateTime == null) ? 43 : $updateTime.hashCode()); Object $securityUrl = getSecurityUrl(); result = result * 59 + (($securityUrl == null) ? 43 : $securityUrl.hashCode()); Object $settleType = getSettleType(); result = result * 59 + (($settleType == null) ? 43 : $settleType.hashCode()); Object $settlePoundageRate = getSettlePoundageRate(); result = result * 59 + (($settlePoundageRate == null) ? 43 : $settlePoundageRate.hashCode()); Object $settlePoundageRateSets = getSettlePoundageRateSets(); result = result * 59 + (($settlePoundageRateSets == null) ? 43 : $settlePoundageRateSets.hashCode()); Object $settleParamsTn = getSettleParamsTn(); result = result * 59 + (($settleParamsTn == null) ? 43 : $settleParamsTn.hashCode()); Object $settleCycle = getSettleCycle(); result = result * 59 + (($settleCycle == null) ? 43 : $settleCycle.hashCode()); Object $settleParams = getSettleParams(); return result * 59 + (($settleParams == null) ? 43 : $settleParams.hashCode()); } public String toString() { return "MchInfo(mchId=" + getMchId() + ", parentMchId=" + getParentMchId() + ", externalId=" + getExternalId() + ", parentExternalId=" + getParentExternalId() + ", name=" + getName() + ", type=" + getType() + ", reqKey=" + getReqKey() + ", resKey=" + getResKey() + ", state=" + getState() + ", settleParamsState=" + getSettleParamsState() + ", createTime=" + getCreateTime() + ", updateTime=" + getUpdateTime() + ", securityUrl=" + getSecurityUrl() + ", settleType=" + getSettleType() + ", settlePoundageRate=" + getSettlePoundageRate() + ", settlePoundageRateSets=" + getSettlePoundageRateSets() + ", settleParamsTn=" + getSettleParamsTn() + ", settleCycle=" + getSettleCycle() + ", settleParams=" + getSettleParams() + ")"; }






   public String getMchId() {
     return this.mchId;
   }




   public String getParentMchId() {
     return this.parentMchId;
   }




   public String getExternalId() {
     return this.externalId;
   }




   public String getParentExternalId() {
     return this.parentExternalId;
   }




   public String getName() {
     return this.name;
   }




   public String getType() {
     return this.type;
   }




   public String getReqKey() {
     return this.reqKey;
   }




   public String getResKey() {
     return this.resKey;
   }




   public Byte getState() {
     return this.state;
   }

   public Date getCreateTime() {
     return this.createTime;
   }




   public Date getUpdateTime() {
     return this.updateTime;
   }




   public String getSecurityUrl() {
     return this.securityUrl;
   }




   public String getSettleType() {
     return this.settleType;
   }

   public String getSettlePoundageRateSets() {
     return this.settlePoundageRateSets;
   }


   public String getSettleParams() {
     return this.settleParams;
   }
   public void setMchId(String mchId) {
     this.mchId = mchId;
   }

   public void setParentMchId(String parentMchId) {
     this.parentMchId = parentMchId;
   }

   public void setExternalId(String externalId) {
     this.externalId = externalId;
   }

   public void setParentExternalId(String parentExternalId) {
     this.parentExternalId = parentExternalId;
   }

   public void setName(String name) {
     this.name = name;
   }

   public void setType(String type) {
     this.type = type;
   }

   public void setReqKey(String reqKey) {
     this.reqKey = reqKey;
   }

   public void setResKey(String resKey) {
     this.resKey = resKey;
   }

   public void setState(Byte state) {
     this.state = state;
   }

   public void setCreateTime(String createTime) {
     this.createTime = DateUtils.formatDateTime(createTime);
   }

   public void setUpdateTime(String updateTime) {
     this.updateTime = DateUtils.formatDateTime(updateTime);
   }

   public void setSecurityUrl(String securityUrl) {
     this.securityUrl = securityUrl;
   }

   public void setSettleType(String settleType) {
     this.settleType = settleType;
   }

   public void setSettleParams(String settleParams) {
     this.settleParams = settleParams;
   }

   public void setSettlePoundageRateSets(String poundageRateSets) {
     this.settleParams = this.settlePoundageRateSets = poundageRateSets;
   }
   public void updateSettlePoundageRateSets(String poundageRateSets) {
     JSONObject obj;
     if (poundageRateSets == null) {
       return;
     }

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }
     obj.putAll((Map)JSONObject.parseObject(poundageRateSets));
     this.settleParams = this.settlePoundageRateSets = obj.toJSONString();
   }






   public JSONObject getSettlePoundageRateJSONObjectSets() {
     if (StringUtils.isBlank(this.settleParams)) {
       return null;
     }
     return JSONObject.parseObject(this.settleParams);
   }
   public void setSettlePoundageRate(Integer settlePoundageRate) {
     JSONObject obj;
     if (settlePoundageRate == null) {
       return;
     }
     this.settlePoundageRate = settlePoundageRate;

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }

     obj.put("pr", settlePoundageRate);
     this.settleParams = obj.toJSONString();
   }
   public void setSettlePoundageRate(String payChannel, Integer settlePoundageRate) {
     JSONObject obj;
     if (settlePoundageRate == null) {
       return;
     }

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }
     obj.put(payChannel, settlePoundageRate);
     this.settleParams = obj.toJSONString();
   }

   public String getPresentSettlePoundageRate() {
     return this.settleParams;
   }


   public Integer getSettlePoundageRate() {
     if (StringUtils.isBlank(this.settleParams)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleParams);
     this.settlePoundageRate = obj.getInteger("pr");
     return this.settlePoundageRate;
   }


   public Integer getSettlePoundageRate(String payChannel) {
     if (StringUtils.isBlank(this.settleParams)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleParams);

     Integer pr = obj.getInteger("pr");

     if (pr != null) {
       this.settlePoundageRate = pr;
     } else {
       this.settlePoundageRate = obj.getInteger(payChannel);
     }
     return this.settlePoundageRate;
   }
   public void setSettleParamsTn(Integer settleParamsTn) {
     JSONObject obj;
     if (settleParamsTn == null) {
       return;
     }
     this.settleParamsTn = settleParamsTn;

     if (StringUtils.isBlank(this.settleType)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleType);
     }

     obj.put("tn", settleParamsTn);
     this.settleType = obj.toJSONString();

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }
     obj.put("tn", settleParamsTn);
     this.settleParams = this.settlePoundageRateSets = obj.toJSONString();
   }
   public void setSettleParamsState(Byte settleParamsState) {
     JSONObject obj;
     if (settleParamsState == null) {
       return;
     }
     this.settleParamsState = settleParamsState;

     if (StringUtils.isBlank(this.settleType)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleType);
     }

     obj.put("state", settleParamsState);
     this.settleType = obj.toJSONString();

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }
     obj.put("state", this.settleParamsTn);
     this.settleParams = this.settlePoundageRateSets = obj.toJSONString();
   }


   public Byte getSettleParamsState() {
     if (StringUtils.isBlank(this.settleType)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleType);
     this.settleParamsState = obj.getByte("state");
     return this.settleParamsState;
   }
   public void updateSettleParamsTn(Integer settleParamsTn) {
     JSONObject obj;
     if (settleParamsTn == null) {
       return;
     }
     this.settleParamsTn = settleParamsTn;
     JSONObject newObj = new JSONObject();
     newObj.put("tn", settleParamsTn);

     if (StringUtils.isBlank(this.settleType)) {
       obj = newObj;
     } else {
       obj = JSONObject.parseObject(this.settleType);
       obj.put("tn", settleParamsTn);
     }
     this.settleType = obj.toJSONString();
   }


   public Integer getSettleParamsTn() {
     if (StringUtils.isBlank(this.settleType)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleType);
     this.settleParamsTn = obj.getInteger("tn");
     return this.settleParamsTn;
   }
   public void setSettleCycle(String settleCycle) {
     JSONObject obj;
     if (settleCycle == null) {
       return;
     }
     this.settleCycle = settleCycle;

     if (StringUtils.isBlank(this.settleType)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleType);
     }
     obj.put("cycle", settleCycle);
     this.settleType = obj.toJSONString();

     if (StringUtils.isBlank(this.settleParams)) {
       obj = new JSONObject();
     } else {
       obj = JSONObject.parseObject(this.settleParams);
     }
     obj.put("cycle", this.settleType);
     this.settleParams = this.settlePoundageRateSets = obj.toJSONString();
   }

   public String getSettleCycle() {
     if (StringUtils.isBlank(this.settleType)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleType);
     this.settleCycle = obj.getString("cycle");
     return this.settleCycle;
   }

   public void setSettleType() {
     JSONObject obj = new JSONObject();
     if (StringUtils.isNotBlank(this.settleCycle)) {
       obj.put("cycle", this.settleCycle);
     }

     if (this.settleParamsTn != null &&
       this.settleParamsTn.intValue() > 0) {
       obj.put("tn", this.settleParamsTn);
     }


     if (obj.size() == 0) {
       this.settleType = null;
     } else {
       this.settleType = obj.toJSONString();
     }
   }



   public static long getSerialVersionUID() {
     return 1L;
   }

   public String toJSONString() {
     return JSONObject.toJSONString(this);
   }
 }





