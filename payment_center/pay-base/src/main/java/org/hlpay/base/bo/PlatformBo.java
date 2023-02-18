 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;



















 public class PlatformBo
   extends ProMchInfoBo
 {
   @ApiModelProperty(value = "外部ID", required = false)
   private String externalId;
   @ApiModelProperty(value = "商户名称", required = false)
   private String name;
   @ApiModelProperty(value = "安全域名", required = false)
   private String securityUrl;
   private static final long serialVersionUID = 1L;

   public String getExternalId() {
     return this.externalId;
   }

   public void setExternalId(String externalId) {
     this.externalId = externalId;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getSecurityUrl() {
     return this.securityUrl;
   }

   public void setSecurityUrl(String securityUrl) {
     this.securityUrl = securityUrl;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", externalId=").append(this.externalId);
     sb.append(", name=").append(this.name);
     sb.append(", securityUrl=").append(this.securityUrl);
     sb.append("]");
     return sb.toString();
   }
 }

