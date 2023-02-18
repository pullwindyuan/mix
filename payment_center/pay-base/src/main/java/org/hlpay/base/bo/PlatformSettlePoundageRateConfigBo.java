package org.hlpay.base.bo;

public class PlatformSettlePoundageRateConfigBo extends AccessBo {
  @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false)
  private Integer settlePoundageRate;

  public String toString() { return "PlatformSettlePoundageRateConfigBo(settlePoundageRate=" + getSettlePoundageRate() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $settlePoundageRate = getSettlePoundageRate(); return result * 59 + (($settlePoundageRate == null) ? 43 : $settlePoundageRate.hashCode()); } protected boolean canEqual(Object other) { return other instanceof PlatformSettlePoundageRateConfigBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PlatformSettlePoundageRateConfigBo)) return false;  PlatformSettlePoundageRateConfigBo other = (PlatformSettlePoundageRateConfigBo)o; if (!other.canEqual(this)) return false;  Object this$settlePoundageRate = getSettlePoundageRate(), other$settlePoundageRate = other.getSettlePoundageRate(); return !((this$settlePoundageRate == null) ? (other$settlePoundageRate != null) : !this$settlePoundageRate.equals(other$settlePoundageRate)); } public PlatformSettlePoundageRateConfigBo setSettlePoundageRate(Integer settlePoundageRate) { this.settlePoundageRate = settlePoundageRate; return this; }







  public Integer getSettlePoundageRate() {
    return this.settlePoundageRate;
  }
}





