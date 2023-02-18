 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

 public class MchInfoBo
   extends ProMchInfoBo
 {
   @ApiModelProperty(value = "父级商户ID", required = true)
   private String parentMchId;
   @ApiModelProperty(value = "外部ID", required = true)
   private String externalId;
   @ApiModelProperty(value = "外部父级ID", required = true)
   private String parentExternalId;
   @ApiModelProperty(value = "商户名称", required = true)
   private String name;
   @ApiModelProperty(value = "商户类型（0：平台； 1：商户； 2：商户分支机构）", required = true)
   private String type;
   private static final long serialVersionUID = 1L;

   public String getParentMchId() {
     return this.parentMchId;
   }

   public void setParentMchId(String parentMchId) {
     this.parentMchId = parentMchId;
   }

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

   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", parentMchId=").append(this.parentMchId);
     sb.append(", externalId=").append(this.externalId);
     sb.append(", parentExternalId=").append(this.parentExternalId);
     sb.append(", name=").append(this.name);
     sb.append(", type=").append(this.type);
     sb.append("]");
     return sb.toString();
   }
 }
