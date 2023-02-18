 package org.hlpay.base.vo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;

 public class AuthPrivateKeyVo
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "私有key", required = true)
   private String privatekey;

   public String toString() {
     return "AuthPrivateKeyVo(privatekey=" + getPrivatekey() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $privatekey = getPrivatekey(); return result * 59 + (($privatekey == null) ? 43 : $privatekey.hashCode()); } protected boolean canEqual(Object other) { return other instanceof AuthPrivateKeyVo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof AuthPrivateKeyVo)) return false;  AuthPrivateKeyVo other = (AuthPrivateKeyVo)o; if (!other.canEqual(this)) return false;  Object this$privatekey = getPrivatekey(), other$privatekey = other.getPrivatekey(); return !((this$privatekey == null) ? (other$privatekey != null) : !this$privatekey.equals(other$privatekey)); } public AuthPrivateKeyVo setPrivatekey(String privatekey) { this.privatekey = privatekey; return this; }

   public String getPrivatekey() {
     return this.privatekey;
   }
 }





