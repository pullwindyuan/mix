 package org.hlpay.base.bo;

 public class MchSettlePoundageRateConfigBo extends AccessBo {
   @ApiModelProperty("商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充")
   private String mchId;

   public MchSettlePoundageRateConfigBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = true) private String settlePoundageRateSets; public MchSettlePoundageRateConfigBo setSettlePoundageRateSets(String settlePoundageRateSets) { this.settlePoundageRateSets = settlePoundageRateSets; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchSettlePoundageRateConfigBo)) return false;  MchSettlePoundageRateConfigBo other = (MchSettlePoundageRateConfigBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$settlePoundageRateSets = getSettlePoundageRateSets(), other$settlePoundageRateSets = other.getSettlePoundageRateSets(); return !((this$settlePoundageRateSets == null) ? (other$settlePoundageRateSets != null) : !this$settlePoundageRateSets.equals(other$settlePoundageRateSets)); } protected boolean canEqual(Object other) { return other instanceof MchSettlePoundageRateConfigBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $settlePoundageRateSets = getSettlePoundageRateSets(); return result * 59 + (($settlePoundageRateSets == null) ? 43 : $settlePoundageRateSets.hashCode()); } public String toString() { return "MchSettlePoundageRateConfigBo(mchId=" + getMchId() + ", settlePoundageRateSets=" + getSettlePoundageRateSets() + ")"; }







   public String getMchId() {
     return this.mchId;
   }













   public String getSettlePoundageRateSets() {
     return this.settlePoundageRateSets;
   }
 }

