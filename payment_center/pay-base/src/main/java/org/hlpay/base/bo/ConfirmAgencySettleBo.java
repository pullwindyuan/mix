 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

 public class ConfirmAgencySettleBo extends AccessBo implements Serializable {
   @ApiModelProperty(value = "结算单号", required = false)
   private String settleTransOrderId;
   @ApiModelProperty(value = "商户号", required = false)
   private String mchId;
   @ApiModelProperty(value = "结算卡号", required = true)
   private String settleCardNo;

   public ConfirmAgencySettleBo setSettleTransOrderId(String settleTransOrderId) { this.settleTransOrderId = settleTransOrderId; return this; } public ConfirmAgencySettleBo setMchId(String mchId) { this.mchId = mchId; return this; } public ConfirmAgencySettleBo setSettleCardNo(String settleCardNo) { this.settleCardNo = settleCardNo; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof ConfirmAgencySettleBo)) return false;  ConfirmAgencySettleBo other = (ConfirmAgencySettleBo)o; if (!other.canEqual(this)) return false;  Object this$settleTransOrderId = getSettleTransOrderId(), other$settleTransOrderId = other.getSettleTransOrderId(); if ((this$settleTransOrderId == null) ? (other$settleTransOrderId != null) : !this$settleTransOrderId.equals(other$settleTransOrderId)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$settleCardNo = getSettleCardNo(), other$settleCardNo = other.getSettleCardNo(); return !((this$settleCardNo == null) ? (other$settleCardNo != null) : !this$settleCardNo.equals(other$settleCardNo)); } protected boolean canEqual(Object other) { return other instanceof ConfirmAgencySettleBo; } public int hashCode() { int PRIME = 59; result = 1; Object $settleTransOrderId = getSettleTransOrderId(); result = result * 59 + (($settleTransOrderId == null) ? 43 : $settleTransOrderId.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $settleCardNo = getSettleCardNo(); return result * 59 + (($settleCardNo == null) ? 43 : $settleCardNo.hashCode()); } public String toString() { return "ConfirmAgencySettleBo(settleTransOrderId=" + getSettleTransOrderId() + ", mchId=" + getMchId() + ", settleCardNo=" + getSettleCardNo() + ")"; }







   public String getSettleTransOrderId() {
     return this.settleTransOrderId;
   }





   public String getMchId() {
     return this.mchId;
   }





   public String getSettleCardNo() {
     return this.settleCardNo;
   }
 }
