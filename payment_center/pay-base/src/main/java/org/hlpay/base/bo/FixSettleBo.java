 package org.hlpay.base.bo;
 public class FixSettleBo extends AccessBo {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "商户ID（组织ID：集团ID、下属企业ID、门店ID等）", required = true)
   private String mchId;

   public FixSettleBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "币种，如果不传，默认为CNY", required = false) private String currency; @ApiModelProperty(value = "参考结算基准时间", required = true) private Long refSettleTime; @ApiModelProperty(value = "相对参考结算基准时间的偏移天数：比如参考时间refSettleTime是2012年3月21日，offsetDays是3", required = false) private Integer offsetDays; public FixSettleBo setCurrency(String currency) { this.currency = currency; return this; } public FixSettleBo setRefSettleTime(Long refSettleTime) { this.refSettleTime = refSettleTime; return this; } public FixSettleBo setOffsetDays(Integer offsetDays) { this.offsetDays = offsetDays; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof FixSettleBo)) return false;  FixSettleBo other = (FixSettleBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$refSettleTime = getRefSettleTime(), other$refSettleTime = other.getRefSettleTime(); if ((this$refSettleTime == null) ? (other$refSettleTime != null) : !this$refSettleTime.equals(other$refSettleTime)) return false;  Object this$offsetDays = getOffsetDays(), other$offsetDays = other.getOffsetDays(); return !((this$offsetDays == null) ? (other$offsetDays != null) : !this$offsetDays.equals(other$offsetDays)); } protected boolean canEqual(Object other) { return other instanceof FixSettleBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $refSettleTime = getRefSettleTime(); result = result * 59 + (($refSettleTime == null) ? 43 : $refSettleTime.hashCode()); Object $offsetDays = getOffsetDays(); return result * 59 + (($offsetDays == null) ? 43 : $offsetDays.hashCode()); } public String toString() { return "FixSettleBo(mchId=" + getMchId() + ", currency=" + getCurrency() + ", refSettleTime=" + getRefSettleTime() + ", offsetDays=" + getOffsetDays() + ")"; }






   public String getMchId() {
     return this.mchId;
   }




   public String getCurrency() {
     return this.currency;
   }



   public Long getRefSettleTime() {
     return this.refSettleTime;
   }



   public Integer getOffsetDays() {
     return this.offsetDays;
   }
 }

