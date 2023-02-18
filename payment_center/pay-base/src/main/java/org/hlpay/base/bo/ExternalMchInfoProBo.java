 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

 public class ExternalMchInfoProBo
   extends ProMchInfoBo
 {
   @ApiModelProperty(value = "外部ID", required = true)
   private String externalId;
   @ApiModelProperty(value = "外部父级ID", required = true)
   private String parentExternalId;
   @ApiModelProperty(value = "商户名称", required = true)
   private String name;
   @ApiModelProperty(value = "父级商户名称", required = true)
   private String parentName;
   private static final long serialVersionUID = 1L;

   public String getExternalId() {
     return this.externalId;
   }

   public void setExternalId(String externalId) {
     this.externalId = externalId;
   }

   public String getParentExternalId() {
     return this.parentExternalId;
   }

   public void setParentExternalId(String parentExternalId) {
     this.parentExternalId = parentExternalId;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getParentName() {
     return this.parentName;
   }

   public void setParentName(String parentName) {
     this.parentName = parentName;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", externalId=").append(this.externalId);
     sb.append(", parentExternalId=").append(this.parentExternalId);
     sb.append(", name=").append(this.name);
     sb.append(", parentName=").append(this.parentName);
     sb.append("]");
     return sb.toString();
   }
 }
