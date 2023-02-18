 package org.hlpay.base.bo;
 public class MchSettleConfigBo extends AccessBo { @ApiModelProperty(value = "商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充", required = true)
   private String mchId;
   @ApiModelProperty(value = "结算类型: 'DAY'-日结；'WEEK'-周结；'MONTH'-月结；'YEAR'-年结；'ANY'-按需结算", required = false)
   private String settleType;

   public MchSettleConfigBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = true) private String settlePoundageRate; @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = true) private Integer settleParamsTn; @ApiModelProperty(value = "结算参数状态,0-停止使用,1-使用中", required = true) private Byte settleParamsState; public MchSettleConfigBo setSettleType(String settleType) { this.settleType = settleType; return this; } public MchSettleConfigBo setSettlePoundageRate(String settlePoundageRate) { this.settlePoundageRate = settlePoundageRate; return this; } public MchSettleConfigBo setSettleParamsTn(Integer settleParamsTn) { this.settleParamsTn = settleParamsTn; return this; } public MchSettleConfigBo setSettleParamsState(Byte settleParamsState) { this.settleParamsState = settleParamsState; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchSettleConfigBo)) return false;  MchSettleConfigBo other = (MchSettleConfigBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$settleType = getSettleType(), other$settleType = other.getSettleType(); if ((this$settleType == null) ? (other$settleType != null) : !this$settleType.equals(other$settleType)) return false;  Object this$settlePoundageRate = getSettlePoundageRate(), other$settlePoundageRate = other.getSettlePoundageRate(); if ((this$settlePoundageRate == null) ? (other$settlePoundageRate != null) : !this$settlePoundageRate.equals(other$settlePoundageRate)) return false;  Object this$settleParamsTn = getSettleParamsTn(), other$settleParamsTn = other.getSettleParamsTn(); if ((this$settleParamsTn == null) ? (other$settleParamsTn != null) : !this$settleParamsTn.equals(other$settleParamsTn)) return false;  Object this$settleParamsState = getSettleParamsState(), other$settleParamsState = other.getSettleParamsState(); return !((this$settleParamsState == null) ? (other$settleParamsState != null) : !this$settleParamsState.equals(other$settleParamsState)); } protected boolean canEqual(Object other) { return other instanceof MchSettleConfigBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $settleType = getSettleType(); result = result * 59 + (($settleType == null) ? 43 : $settleType.hashCode()); Object $settlePoundageRate = getSettlePoundageRate(); result = result * 59 + (($settlePoundageRate == null) ? 43 : $settlePoundageRate.hashCode()); Object $settleParamsTn = getSettleParamsTn(); result = result * 59 + (($settleParamsTn == null) ? 43 : $settleParamsTn.hashCode()); Object $settleParamsState = getSettleParamsState(); return result * 59 + (($settleParamsState == null) ? 43 : $settleParamsState.hashCode()); } public String toString() { return "MchSettleConfigBo(mchId=" + getMchId() + ", settleType=" + getSettleType() + ", settlePoundageRate=" + getSettlePoundageRate() + ", settleParamsTn=" + getSettleParamsTn() + ", settleParamsState=" + getSettleParamsState() + ")"; }







   public String getMchId() {
     return this.mchId;
   }





   public String getSettleType() {
     return this.settleType;
   }





   public String getSettlePoundageRate() {
     return this.settlePoundageRate;
   }





   public Integer getSettleParamsTn() {
     return this.settleParamsTn;
   }





   public Byte getSettleParamsState() {
/* 48 */     return this.settleParamsState;
/*    */   } }

