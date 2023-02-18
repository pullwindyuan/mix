 package org.hlpay.base.vo;
 public class ExternalMchInfoVo implements Serializable { @ApiModelProperty(value = "当前ID", notes = "当前ID")
   private Long id;
   @ApiModelProperty(value = "名称", notes = "名称")
   private String name;
   @ApiModelProperty(value = "类型", notes = "类型: 组织 org; 品牌 brand; 门店 store")
   private String type;

   public void setId(Long id) { this.id = id; } @ApiModelProperty(value = "父类ID", notes = "父类ID") private Long parentId; @ApiModelProperty(value = "父类名称", notes = "父类名称") private String parentName; @ApiModelProperty(value = "父类类型", notes = "父类类型: 组织 org; 品牌 brand; 门店 store") private String parentType; @ApiModelProperty(value = "安全部署域名", notes = "安全部署域名") private String payDomain; public void setName(String name) { this.name = name; } public void setType(String type) { this.type = type; } public void setParentId(Long parentId) { this.parentId = parentId; } public void setParentName(String parentName) { this.parentName = parentName; } public void setParentType(String parentType) { this.parentType = parentType; } public void setPayDomain(String payDomain) { this.payDomain = payDomain; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof ExternalMchInfoVo)) return false;  ExternalMchInfoVo other = (ExternalMchInfoVo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$parentId = getParentId(), other$parentId = other.getParentId(); if ((this$parentId == null) ? (other$parentId != null) : !this$parentId.equals(other$parentId)) return false;  Object this$parentName = getParentName(), other$parentName = other.getParentName(); if ((this$parentName == null) ? (other$parentName != null) : !this$parentName.equals(other$parentName)) return false;  Object this$parentType = getParentType(), other$parentType = other.getParentType(); if ((this$parentType == null) ? (other$parentType != null) : !this$parentType.equals(other$parentType)) return false;  Object this$payDomain = getPayDomain(), other$payDomain = other.getPayDomain(); return !((this$payDomain == null) ? (other$payDomain != null) : !this$payDomain.equals(other$payDomain)); } protected boolean canEqual(Object other) { return other instanceof ExternalMchInfoVo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $parentId = getParentId(); result = result * 59 + (($parentId == null) ? 43 : $parentId.hashCode()); Object $parentName = getParentName(); result = result * 59 + (($parentName == null) ? 43 : $parentName.hashCode()); Object $parentType = getParentType(); result = result * 59 + (($parentType == null) ? 43 : $parentType.hashCode()); Object $payDomain = getPayDomain(); return result * 59 + (($payDomain == null) ? 43 : $payDomain.hashCode()); } public String toString() { return "ExternalMchInfoVo(id=" + getId() + ", name=" + getName() + ", type=" + getType() + ", parentId=" + getParentId() + ", parentName=" + getParentName() + ", parentType=" + getParentType() + ", payDomain=" + getPayDomain() + ")"; }

   public Long getId() {
     return this.id;
   }
   public String getName() {
     return this.name;
   }



   public Long getParentId() {
     return this.parentId;
   }
   public String getParentName() {
     return this.parentName;
   }
   public String getParentType() {
     return this.parentType;
   }
   public String getPayDomain() {
     return this.payDomain;
   } public String getType() {
     if (this.parentId.longValue() == 0L && ExternalMchTypeEnum.org.name().equals(this.type)) {
       this.type = ExternalMchTypeEnum.root_org.name();
     }
     return this.type;
   } }

