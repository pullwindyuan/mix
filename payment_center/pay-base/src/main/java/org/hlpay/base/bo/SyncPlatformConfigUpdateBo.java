 package org.hlpay.base.bo;
 public class SyncPlatformConfigUpdateBo extends ProMchInfoBo { @ApiModelProperty("商户ID")
   private String mchId;
   @ApiModelProperty("父级商户ID")
   private String parentMchId;
   @ApiModelProperty("外部ID")
   private String externalId;

   public void setMchId(String mchId) { this.mchId = mchId; } @ApiModelProperty("外部父级ID") private String parentExternalId; @ApiModelProperty("商户名称") private String name; @ApiModelProperty("配置类型：pc(支付渠道)， mch（商户信息）") private String configType; public void setParentMchId(String parentMchId) { this.parentMchId = parentMchId; } public void setExternalId(String externalId) { this.externalId = externalId; } public void setParentExternalId(String parentExternalId) { this.parentExternalId = parentExternalId; } public void setName(String name) { this.name = name; } public void setConfigType(String configType) { this.configType = configType; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SyncPlatformConfigUpdateBo)) return false;  SyncPlatformConfigUpdateBo other = (SyncPlatformConfigUpdateBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$parentMchId = getParentMchId(), other$parentMchId = other.getParentMchId(); if ((this$parentMchId == null) ? (other$parentMchId != null) : !this$parentMchId.equals(other$parentMchId)) return false;  Object this$externalId = getExternalId(), other$externalId = other.getExternalId(); if ((this$externalId == null) ? (other$externalId != null) : !this$externalId.equals(other$externalId)) return false;  Object this$parentExternalId = getParentExternalId(), other$parentExternalId = other.getParentExternalId(); if ((this$parentExternalId == null) ? (other$parentExternalId != null) : !this$parentExternalId.equals(other$parentExternalId)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$configType = getConfigType(), other$configType = other.getConfigType(); return !((this$configType == null) ? (other$configType != null) : !this$configType.equals(other$configType)); } protected boolean canEqual(Object other) { return other instanceof SyncPlatformConfigUpdateBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $parentMchId = getParentMchId(); result = result * 59 + (($parentMchId == null) ? 43 : $parentMchId.hashCode()); Object $externalId = getExternalId(); result = result * 59 + (($externalId == null) ? 43 : $externalId.hashCode()); Object $parentExternalId = getParentExternalId(); result = result * 59 + (($parentExternalId == null) ? 43 : $parentExternalId.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $configType = getConfigType(); return result * 59 + (($configType == null) ? 43 : $configType.hashCode()); } public String toString() { return "SyncPlatformConfigUpdateBo(mchId=" + getMchId() + ", parentMchId=" + getParentMchId() + ", externalId=" + getExternalId() + ", parentExternalId=" + getParentExternalId() + ", name=" + getName() + ", configType=" + getConfigType() + ")"; }






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





   public String getConfigType() {
/* 56 */     return this.configType;
/*    */   } }
