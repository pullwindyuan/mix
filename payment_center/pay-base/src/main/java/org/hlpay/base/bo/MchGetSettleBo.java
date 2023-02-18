package org.hlpay.base.bo;

public class MchGetSettleBo extends AccessBo {
  @ApiModelProperty("商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充")
  private String mchId;

  public String toString() { return "MchGetSettleBo(mchId=" + getMchId() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); return result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); } protected boolean canEqual(Object other) { return other instanceof MchGetSettleBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchGetSettleBo)) return false;  MchGetSettleBo other = (MchGetSettleBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); return !((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)); } public MchGetSettleBo setMchId(String mchId) { this.mchId = mchId; return this; }







  public String getMchId() {
    return this.mchId;
  }
}

