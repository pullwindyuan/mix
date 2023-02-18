package org.hlpay.base.bo;

public class MchSettleAuditBo extends AccessBo {
  @ApiModelProperty("商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充")
  private String mchId;

  public MchSettleAuditBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "审核状态:0-不通过；1-通过", required = false) private Integer audit; public MchSettleAuditBo setAudit(Integer audit) { this.audit = audit; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchSettleAuditBo)) return false;  MchSettleAuditBo other = (MchSettleAuditBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$audit = getAudit(), other$audit = other.getAudit(); return !((this$audit == null) ? (other$audit != null) : !this$audit.equals(other$audit)); } protected boolean canEqual(Object other) { return other instanceof MchSettleAuditBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $audit = getAudit(); return result * 59 + (($audit == null) ? 43 : $audit.hashCode()); } public String toString() { return "MchSettleAuditBo(mchId=" + getMchId() + ", audit=" + getAudit() + ")"; }







  public String getMchId() {
    return this.mchId;
  }





  public Integer getAudit() {
    return this.audit;
  }
}
