 package org.hlpay.base.bo;
 public class DoWithdrawBo extends ProMchInfoBo {
   @ApiModelProperty(value = "商户Id", required = false)
   private String mchId;

   public void setMchId(String mchId) { this.mchId = mchId; } @ApiModelProperty(value = "结算单号", required = true) private String settleTransOrderNo; private static final long serialVersionUID = 1L; public void setSettleTransOrderNo(String settleTransOrderNo) { this.settleTransOrderNo = settleTransOrderNo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof DoWithdrawBo)) return false;  DoWithdrawBo other = (DoWithdrawBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$settleTransOrderNo = getSettleTransOrderNo(), other$settleTransOrderNo = other.getSettleTransOrderNo(); return !((this$settleTransOrderNo == null) ? (other$settleTransOrderNo != null) : !this$settleTransOrderNo.equals(other$settleTransOrderNo)); } protected boolean canEqual(Object other) { return other instanceof DoWithdrawBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $settleTransOrderNo = getSettleTransOrderNo(); return result * 59 + (($settleTransOrderNo == null) ? 43 : $settleTransOrderNo.hashCode()); } public String toString() { return "DoWithdrawBo(mchId=" + getMchId() + ", settleTransOrderNo=" + getSettleTransOrderNo() + ")"; }






   public String getMchId() {
     return this.mchId;
   }





   public String getSettleTransOrderNo() {
     return this.settleTransOrderNo;
   }
 }
