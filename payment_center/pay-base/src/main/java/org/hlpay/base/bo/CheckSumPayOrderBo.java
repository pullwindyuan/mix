 package org.hlpay.base.bo;
 public class CheckSumPayOrderBo implements Serializable { @ApiModelProperty("渠道编码：0-微信，1-支付宝；2-银联")
   private Byte channelCode; @ApiModelProperty("支付金额,单位分")
   private Long amount; @ApiModelProperty("三位货币代码")
   private String currency;
   @ApiModelProperty("支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-订单关闭")
   private Byte status;

   public void setChannelCode(Byte channelCode) { this.channelCode = channelCode; } @ApiModelProperty("商户ID") private String mchId; @ApiModelProperty("订单支付成功起始时间") private LocalDateTime paySuccTimeStart; @ApiModelProperty("订单支付成功结算时间") private LocalDateTime paySuccTimeEnd; private static final long serialVersionUID = 1L; public void setAmount(Long amount) { this.amount = amount; } public void setCurrency(String currency) { this.currency = currency; } public void setStatus(Byte status) { this.status = status; } public void setMchId(String mchId) { this.mchId = mchId; } public void setPaySuccTimeStart(LocalDateTime paySuccTimeStart) { this.paySuccTimeStart = paySuccTimeStart; } public void setPaySuccTimeEnd(LocalDateTime paySuccTimeEnd) { this.paySuccTimeEnd = paySuccTimeEnd; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CheckSumPayOrderBo)) return false;  CheckSumPayOrderBo other = (CheckSumPayOrderBo)o; if (!other.canEqual(this)) return false;  Object this$channelCode = getChannelCode(), other$channelCode = other.getChannelCode(); if ((this$channelCode == null) ? (other$channelCode != null) : !this$channelCode.equals(other$channelCode)) return false;  Object this$amount = getAmount(), other$amount = other.getAmount(); if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$status = getStatus(), other$status = other.getStatus(); if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$paySuccTimeStart = getPaySuccTimeStart(), other$paySuccTimeStart = other.getPaySuccTimeStart(); if ((this$paySuccTimeStart == null) ? (other$paySuccTimeStart != null) : !this$paySuccTimeStart.equals(other$paySuccTimeStart)) return false;  Object this$paySuccTimeEnd = getPaySuccTimeEnd(), other$paySuccTimeEnd = other.getPaySuccTimeEnd(); return !((this$paySuccTimeEnd == null) ? (other$paySuccTimeEnd != null) : !this$paySuccTimeEnd.equals(other$paySuccTimeEnd)); } protected boolean canEqual(Object other) { return other instanceof CheckSumPayOrderBo; } public int hashCode() { int PRIME = 59; result = 1; Object $channelCode = getChannelCode(); result = result * 59 + (($channelCode == null) ? 43 : $channelCode.hashCode()); Object $amount = getAmount(); result = result * 59 + (($amount == null) ? 43 : $amount.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $status = getStatus(); result = result * 59 + (($status == null) ? 43 : $status.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $paySuccTimeStart = getPaySuccTimeStart(); result = result * 59 + (($paySuccTimeStart == null) ? 43 : $paySuccTimeStart.hashCode()); Object $paySuccTimeEnd = getPaySuccTimeEnd(); return result * 59 + (($paySuccTimeEnd == null) ? 43 : $paySuccTimeEnd.hashCode()); } public String toString() { return "CheckSumPayOrderBo(channelCode=" + getChannelCode() + ", amount=" + getAmount() + ", currency=" + getCurrency() + ", status=" + getStatus() + ", mchId=" + getMchId() + ", paySuccTimeStart=" + getPaySuccTimeStart() + ", paySuccTimeEnd=" + getPaySuccTimeEnd() + ")"; }






   public Byte getChannelCode() {
     return this.channelCode;
   }





   public Long getAmount() {
     return this.amount;
   }





   public String getCurrency() {
     return this.currency;
   }





   public Byte getStatus() {
     return this.status;
   }





   public String getMchId() {
     return this.mchId;
   }





   public LocalDateTime getPaySuccTimeStart() {
     return this.paySuccTimeStart;
   }




   public LocalDateTime getPaySuccTimeEnd() {
     return this.paySuccTimeEnd;
   } }





