 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

















 public class PlatformAgencyBo
   extends ProMchInfoBo
 {
   @ApiModelProperty(value = "外部ID", required = true)
   private String externalId;
   @ApiModelProperty(value = "商户名称", required = true)
   private String name;
   @ApiModelProperty(value = "安全域名：代理商支付中心部署的安全域名", required = true)
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

