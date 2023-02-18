 package org.hlpay.base.bo;
 public class ExternalMchInfoBo extends ProMchInfoBo { @ApiModelProperty(value = "外部ID", required = true)
   private String externalId;
   @ApiModelProperty(value = "外部父级ID", required = true)
   private String parentExternalId;

   public ExternalMchInfoBo setExternalId(String externalId) { this.externalId = externalId; return this; } @ApiModelProperty(value = "商户名称", required = true) private String name; @ApiModelProperty(value = "商户类型（MCH:商户（企业或者品牌）；MCH_BRANCH：分支商户(门店)）", required = true) private String type; public ExternalMchInfoBo setParentExternalId(String parentExternalId) { this.parentExternalId = parentExternalId; return this; } public ExternalMchInfoBo setName(String name) { this.name = name; return this; } public ExternalMchInfoBo setType(String type) { this.type = type; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof ExternalMchInfoBo)) return false;  ExternalMchInfoBo other = (ExternalMchInfoBo)o; if (!other.canEqual(this)) return false;  Object this$externalId = getExternalId(), other$externalId = other.getExternalId(); if ((this$externalId == null) ? (other$externalId != null) : !this$externalId.equals(other$externalId)) return false;  Object this$parentExternalId = getParentExternalId(), other$parentExternalId = other.getParentExternalId(); if ((this$parentExternalId == null) ? (other$parentExternalId != null) : !this$parentExternalId.equals(other$parentExternalId)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$type = getType(), other$type = other.getType(); return !((this$type == null) ? (other$type != null) : !this$type.equals(other$type)); } protected boolean canEqual(Object other) { return other instanceof ExternalMchInfoBo; } public int hashCode() { int PRIME = 59; result = 1; Object $externalId = getExternalId(); result = result * 59 + (($externalId == null) ? 43 : $externalId.hashCode()); Object $parentExternalId = getParentExternalId(); result = result * 59 + (($parentExternalId == null) ? 43 : $parentExternalId.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $type = getType(); return result * 59 + (($type == null) ? 43 : $type.hashCode()); } public String toString() { return "ExternalMchInfoBo(externalId=" + getExternalId() + ", parentExternalId=" + getParentExternalId() + ", name=" + getName() + ", type=" + getType() + ")"; }







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
/* 40 */     return this.type;
/*    */   } }





