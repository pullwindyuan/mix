 package org.hlpay.base.bo;

 public class PlatformSettleTnConfigBo extends AccessBo {
   @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false)
   private Integer settleParamsTn;

   public String toString() { return "PlatformSettleTnConfigBo(settleParamsTn=" + getSettleParamsTn() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $settleParamsTn = getSettleParamsTn(); return result * 59 + (($settleParamsTn == null) ? 43 : $settleParamsTn.hashCode()); } protected boolean canEqual(Object other) { return other instanceof PlatformSettleTnConfigBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PlatformSettleTnConfigBo)) return false;  PlatformSettleTnConfigBo other = (PlatformSettleTnConfigBo)o; if (!other.canEqual(this)) return false;  Object this$settleParamsTn = getSettleParamsTn(), other$settleParamsTn = other.getSettleParamsTn(); return !((this$settleParamsTn == null) ? (other$settleParamsTn != null) : !this$settleParamsTn.equals(other$settleParamsTn)); } public PlatformSettleTnConfigBo setSettleParamsTn(Integer settleParamsTn) { this.settleParamsTn = settleParamsTn; return this; }







   public Integer getSettleParamsTn() {
     return this.settleParamsTn;
   }
 }





