 package org.hlpay.base.bo;


 public class CashSettleItem implements Serializable {
   @ApiModelProperty(value = "现金结算交易流水号：银行提供", required = false)
   String cashFlowNo;
   @ApiModelProperty(value = "附件地址", required = false)
   String attachmentUrl;

   public CashSettleItem setCashFlowNo(String cashFlowNo) { this.cashFlowNo = cashFlowNo; return this; } @ApiModelProperty(value = "对应结算卡号", required = true) String cardNumber; @ApiModelProperty(value = "私域对应的结算卡号", required = false) String configNumber; @ApiModelProperty(value = "卡对应的用户/商户", required = false) String userId; public CashSettleItem setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; return this; } public CashSettleItem setCardNumber(String cardNumber) { this.cardNumber = cardNumber; return this; } public CashSettleItem setConfigNumber(String configNumber) { this.configNumber = configNumber; return this; } public CashSettleItem setUserId(String userId) { this.userId = userId; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CashSettleItem)) return false;  CashSettleItem other = (CashSettleItem)o; if (!other.canEqual(this)) return false;  Object this$cashFlowNo = getCashFlowNo(), other$cashFlowNo = other.getCashFlowNo(); if ((this$cashFlowNo == null) ? (other$cashFlowNo != null) : !this$cashFlowNo.equals(other$cashFlowNo)) return false;  Object this$attachmentUrl = getAttachmentUrl(), other$attachmentUrl = other.getAttachmentUrl(); if ((this$attachmentUrl == null) ? (other$attachmentUrl != null) : !this$attachmentUrl.equals(other$attachmentUrl)) return false;  Object this$cardNumber = getCardNumber(), other$cardNumber = other.getCardNumber(); if ((this$cardNumber == null) ? (other$cardNumber != null) : !this$cardNumber.equals(other$cardNumber)) return false;  Object this$configNumber = getConfigNumber(), other$configNumber = other.getConfigNumber(); if ((this$configNumber == null) ? (other$configNumber != null) : !this$configNumber.equals(other$configNumber)) return false;  Object this$userId = getUserId(), other$userId = other.getUserId(); return !((this$userId == null) ? (other$userId != null) : !this$userId.equals(other$userId)); } protected boolean canEqual(Object other) { return other instanceof CashSettleItem; } public int hashCode() { int PRIME = 59; result = 1; Object $cashFlowNo = getCashFlowNo(); result = result * 59 + (($cashFlowNo == null) ? 43 : $cashFlowNo.hashCode()); Object $attachmentUrl = getAttachmentUrl(); result = result * 59 + (($attachmentUrl == null) ? 43 : $attachmentUrl.hashCode()); Object $cardNumber = getCardNumber(); result = result * 59 + (($cardNumber == null) ? 43 : $cardNumber.hashCode()); Object $configNumber = getConfigNumber(); result = result * 59 + (($configNumber == null) ? 43 : $configNumber.hashCode()); Object $userId = getUserId(); return result * 59 + (($userId == null) ? 43 : $userId.hashCode()); } public String toString() { return "CashSettleItem(cashFlowNo=" + getCashFlowNo() + ", attachmentUrl=" + getAttachmentUrl() + ", cardNumber=" + getCardNumber() + ", configNumber=" + getConfigNumber() + ", userId=" + getUserId() + ")"; }


   public String getCashFlowNo() {
     return this.cashFlowNo;
   }
   public String getAttachmentUrl() {
     return this.attachmentUrl;
   }
   public String getCardNumber() {
     return this.cardNumber;
   }
   public String getConfigNumber() {
     return this.configNumber;
   }
   public String getUserId() {
     return this.userId;
   }
 }
