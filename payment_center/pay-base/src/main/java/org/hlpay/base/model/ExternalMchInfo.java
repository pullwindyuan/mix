 package org.hlpay.base.model;

 import java.io.Serializable;

 public class ExternalMchInfo implements Serializable {
   private String mchId;
   private String parentMchId;
   private String externalId;
   private String parentExternalId;
   private String name;

   public ExternalMchInfo setMchId(String mchId) { this.mchId = mchId; return this; } private String type; private String securityUrl; private String reqKey; private String resKey; private Byte state; public ExternalMchInfo setParentMchId(String parentMchId) { this.parentMchId = parentMchId; return this; } public ExternalMchInfo setExternalId(String externalId) { this.externalId = externalId; return this; } public ExternalMchInfo setParentExternalId(String parentExternalId) { this.parentExternalId = parentExternalId; return this; } public ExternalMchInfo setName(String name) { this.name = name; return this; } public ExternalMchInfo setType(String type) { this.type = type; return this; } public ExternalMchInfo setSecurityUrl(String securityUrl) { this.securityUrl = securityUrl; return this; } public ExternalMchInfo setReqKey(String reqKey) { this.reqKey = reqKey; return this; } public ExternalMchInfo setResKey(String resKey) { this.resKey = resKey; return this; } public ExternalMchInfo setState(Byte state) { this.state = state; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof ExternalMchInfo)) return false;  ExternalMchInfo other = (ExternalMchInfo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$parentMchId = getParentMchId(), other$parentMchId = other.getParentMchId(); if ((this$parentMchId == null) ? (other$parentMchId != null) : !this$parentMchId.equals(other$parentMchId)) return false;  Object this$externalId = getExternalId(), other$externalId = other.getExternalId(); if ((this$externalId == null) ? (other$externalId != null) : !this$externalId.equals(other$externalId)) return false;  Object this$parentExternalId = getParentExternalId(), other$parentExternalId = other.getParentExternalId(); if ((this$parentExternalId == null) ? (other$parentExternalId != null) : !this$parentExternalId.equals(other$parentExternalId)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$securityUrl = getSecurityUrl(), other$securityUrl = other.getSecurityUrl(); if ((this$securityUrl == null) ? (other$securityUrl != null) : !this$securityUrl.equals(other$securityUrl)) return false;  Object this$reqKey = getReqKey(), other$reqKey = other.getReqKey(); if ((this$reqKey == null) ? (other$reqKey != null) : !this$reqKey.equals(other$reqKey)) return false;  Object this$resKey = getResKey(), other$resKey = other.getResKey(); if ((this$resKey == null) ? (other$resKey != null) : !this$resKey.equals(other$resKey)) return false;  Object this$state = getState(), other$state = other.getState(); return !((this$state == null) ? (other$state != null) : !this$state.equals(other$state)); } protected boolean canEqual(Object other) { return other instanceof ExternalMchInfo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $parentMchId = getParentMchId(); result = result * 59 + (($parentMchId == null) ? 43 : $parentMchId.hashCode()); Object $externalId = getExternalId(); result = result * 59 + (($externalId == null) ? 43 : $externalId.hashCode()); Object $parentExternalId = getParentExternalId(); result = result * 59 + (($parentExternalId == null) ? 43 : $parentExternalId.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $securityUrl = getSecurityUrl(); result = result * 59 + (($securityUrl == null) ? 43 : $securityUrl.hashCode()); Object $reqKey = getReqKey(); result = result * 59 + (($reqKey == null) ? 43 : $reqKey.hashCode()); Object $resKey = getResKey(); result = result * 59 + (($resKey == null) ? 43 : $resKey.hashCode()); Object $state = getState(); return result * 59 + (($state == null) ? 43 : $state.hashCode()); } public String toString() { return "ExternalMchInfo(mchId=" + getMchId() + ", parentMchId=" + getParentMchId() + ", externalId=" + getExternalId() + ", parentExternalId=" + getParentExternalId() + ", name=" + getName() + ", type=" + getType() + ", securityUrl=" + getSecurityUrl() + ", reqKey=" + getReqKey() + ", resKey=" + getResKey() + ", state=" + getState() + ")"; }






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




   public String getSecurityUrl() {
     return this.securityUrl;
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
 }





