package org.hlpay.common.entity;

import java.io.Serializable;

public class SimpleResult
  implements Serializable
{
  private Integer erroCode;
  private Object data;

  public void setErroCode(Integer erroCode) {
    this.erroCode = erroCode; } public void setData(Object data) { this.data = data; }
    public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof SimpleResult)) return false;
    SimpleResult other = (SimpleResult)o;
    if (!other.canEqual(this)) return false;
    Object this$erroCode = getErroCode(), other$erroCode = other.getErroCode();
    if ((this$erroCode == null) ? (other$erroCode != null) : !this$erroCode.equals(other$erroCode)) return false;
    Object this$data = getData(), other$data = other.getData();
    return !((this$data == null) ? (other$data != null) : !this$data.equals(other$data));
  }
  protected boolean canEqual(Object other) {
    return other instanceof SimpleResult;
  } public int hashCode() {
    int PRIME = 59;
    int result = 1;
    Object $erroCode = getErroCode();
    result = result * 59 + (($erroCode == null) ? 43 : $erroCode.hashCode()); Object $data = getData();
    return result * 59 + (($data == null) ? 43 : $data.hashCode());
  } public String toString() {
    return "SimpleResult(erroCode=" + getErroCode() + ", data=" + getData() + ")";
  }

  public Integer getErroCode() { return this.erroCode; } public Object getData() {
    return this.data;
  }
}

