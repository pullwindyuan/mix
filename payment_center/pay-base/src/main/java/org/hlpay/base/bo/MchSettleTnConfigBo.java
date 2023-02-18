 package org.hlpay.base.bo;

 public class MchSettleTnConfigBo extends AccessBo {
   @ApiModelProperty("商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充")
   private String mchId;

   public MchSettleTnConfigBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false) private Integer settleParamsTn; public MchSettleTnConfigBo setSettleParamsTn(Integer settleParamsTn) { this.settleParamsTn = settleParamsTn; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchSettleTnConfigBo)) return false;  MchSettleTnConfigBo other = (MchSettleTnConfigBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$settleParamsTn = getSettleParamsTn(), other$settleParamsTn = other.getSettleParamsTn(); return !((this$settleParamsTn == null) ? (other$settleParamsTn != null) : !this$settleParamsTn.equals(other$settleParamsTn)); } protected boolean canEqual(Object other) { return other instanceof MchSettleTnConfigBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $settleParamsTn = getSettleParamsTn(); return result * 59 + (($settleParamsTn == null) ? 43 : $settleParamsTn.hashCode()); } public String toString() { return "MchSettleTnConfigBo(mchId=" + getMchId() + ", settleParamsTn=" + getSettleParamsTn() + ")"; }







   public String getMchId() {
     return this.mchId;
   }





   public Integer getSettleParamsTn() {
     return this.settleParamsTn;
   }
 }
