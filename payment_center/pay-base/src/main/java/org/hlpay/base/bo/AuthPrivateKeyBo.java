package org.hlpay.base.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;




public class AuthPrivateKeyBo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "JSON参数集合（包含字段：Long appid; Long userNo; Long lecturerId; Long courseId; String ip; Long org; Long sign;）", required = true)
  private String params;

  public String toString() {
    return "AuthPrivateKeyBo(params=" + getParams() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $params = getParams(); return result * 59 + (($params == null) ? 43 : $params.hashCode()); } protected boolean canEqual(Object other) { return other instanceof AuthPrivateKeyBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof AuthPrivateKeyBo)) return false;  AuthPrivateKeyBo other = (AuthPrivateKeyBo)o; if (!other.canEqual(this)) return false;  Object this$params = getParams(), other$params = other.getParams(); return !((this$params == null) ? (other$params != null) : !this$params.equals(other$params)); } public AuthPrivateKeyBo setParams(String params) { this.params = params; return this; }
  public String getParams() {
    return this.params;
  }
}