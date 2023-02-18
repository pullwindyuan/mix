 package org.hlpay.base.model;

 public class CheckSumPayOrder implements Serializable {
   private Byte channelCode;
   private Long amount;
   private String currency;
   private Byte status;
   private String mchId;

   public void setChannelCode(Byte channelCode) { this.channelCode = channelCode; } private LocalDateTime paySuccTimeStart; private LocalDateTime paySuccTimeEnd; private Long paySuccTimeStampStart; private Long paySuccTimeStampEnd; private static final long serialVersionUID = 1L; public void setAmount(Long amount) { this.amount = amount; } public void setCurrency(String currency) { this.currency = currency; } public void setStatus(Byte status) { this.status = status; } public void setMchId(String mchId) { this.mchId = mchId; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CheckSumPayOrder)) return false;  CheckSumPayOrder other = (CheckSumPayOrder)o; if (!other.canEqual(this)) return false;  Object this$channelCode = getChannelCode(), other$channelCode = other.getChannelCode(); if ((this$channelCode == null) ? (other$channelCode != null) : !this$channelCode.equals(other$channelCode)) return false;  Object this$amount = getAmount(), other$amount = other.getAmount(); if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$status = getStatus(), other$status = other.getStatus(); if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$paySuccTimeStart = getPaySuccTimeStart(), other$paySuccTimeStart = other.getPaySuccTimeStart(); if ((this$paySuccTimeStart == null) ? (other$paySuccTimeStart != null) : !this$paySuccTimeStart.equals(other$paySuccTimeStart)) return false;  Object this$paySuccTimeEnd = getPaySuccTimeEnd(), other$paySuccTimeEnd = other.getPaySuccTimeEnd(); if ((this$paySuccTimeEnd == null) ? (other$paySuccTimeEnd != null) : !this$paySuccTimeEnd.equals(other$paySuccTimeEnd)) return false;  Object this$paySuccTimeStampStart = getPaySuccTimeStampStart(), other$paySuccTimeStampStart = other.getPaySuccTimeStampStart(); if ((this$paySuccTimeStampStart == null) ? (other$paySuccTimeStampStart != null) : !this$paySuccTimeStampStart.equals(other$paySuccTimeStampStart)) return false;  Object this$paySuccTimeStampEnd = getPaySuccTimeStampEnd(), other$paySuccTimeStampEnd = other.getPaySuccTimeStampEnd(); return !((this$paySuccTimeStampEnd == null) ? (other$paySuccTimeStampEnd != null) : !this$paySuccTimeStampEnd.equals(other$paySuccTimeStampEnd)); } protected boolean canEqual(Object other) { return other instanceof CheckSumPayOrder; } public int hashCode() { int PRIME = 59; result = 1; Object $channelCode = getChannelCode(); result = result * 59 + (($channelCode == null) ? 43 : $channelCode.hashCode()); Object $amount = getAmount(); result = result * 59 + (($amount == null) ? 43 : $amount.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $status = getStatus(); result = result * 59 + (($status == null) ? 43 : $status.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $paySuccTimeStart = getPaySuccTimeStart(); result = result * 59 + (($paySuccTimeStart == null) ? 43 : $paySuccTimeStart.hashCode()); Object $paySuccTimeEnd = getPaySuccTimeEnd(); result = result * 59 + (($paySuccTimeEnd == null) ? 43 : $paySuccTimeEnd.hashCode()); Object $paySuccTimeStampStart = getPaySuccTimeStampStart(); result = result * 59 + (($paySuccTimeStampStart == null) ? 43 : $paySuccTimeStampStart.hashCode()); Object $paySuccTimeStampEnd = getPaySuccTimeStampEnd(); return result * 59 + (($paySuccTimeStampEnd == null) ? 43 : $paySuccTimeStampEnd.hashCode()); } public String toString() { return "CheckSumPayOrder(channelCode=" + getChannelCode() + ", amount=" + getAmount() + ", currency=" + getCurrency() + ", status=" + getStatus() + ", mchId=" + getMchId() + ", paySuccTimeStart=" + getPaySuccTimeStart() + ", paySuccTimeEnd=" + getPaySuccTimeEnd() + ", paySuccTimeStampStart=" + getPaySuccTimeStampStart() + ", paySuccTimeStampEnd=" + getPaySuccTimeStampEnd() + ")"; }





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
   }













   public void setPaySuccTimeStart(LocalDateTime paySuccTimeStart) {
     this.paySuccTimeStart = paySuccTimeStart;
     setPaySuccTimeStampStart(paySuccTimeStart);
   }

   public void setPaySuccTimeEnd(LocalDateTime paySuccTimeEnd) {
     this.paySuccTimeEnd = paySuccTimeEnd;
     setPaySuccTimeStampEnd(paySuccTimeEnd);
   }

   public void setPaySuccTimeStampStart(LocalDateTime paySuccTimeStart) {
     this.paySuccTimeStampStart = DateUtils.getTimestamp(paySuccTimeStart);
   }

   public void setPaySuccTimeStampEnd(LocalDateTime paySuccStampEnd) {
     this.paySuccTimeStampEnd = DateUtils.getTimestamp(paySuccStampEnd);
   }

   public Long getPaySuccTimeStampStart() {
     return this.paySuccTimeStampStart;
   }

   public Long getPaySuccTimeStampEnd() {
     return this.paySuccTimeStampEnd;
   }
 }





