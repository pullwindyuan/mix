package org.hlpay.base.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SaSettleCardSum
  implements Serializable
{
  private String mchId;
  private String type;
  private String cardStatus;
  private String userName;
  private String currency;
  private LocalDateTime validStartTime;
  private LocalDateTime validEndTime;

  public SaSettleCardSum setMchId(String mchId) {
    this.mchId = mchId; return this; } public SaSettleCardSum setType(String type) { this.type = type; return this; } public SaSettleCardSum setCardStatus(String cardStatus) { this.cardStatus = cardStatus; return this; } public SaSettleCardSum setUserName(String userName) { this.userName = userName; return this; } public SaSettleCardSum setCurrency(String currency) { this.currency = currency; return this; } public SaSettleCardSum setValidStartTime(LocalDateTime validStartTime) { this.validStartTime = validStartTime; return this; } public SaSettleCardSum setValidEndTime(LocalDateTime validEndTime) { this.validEndTime = validEndTime; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SaSettleCardSum)) return false;  SaSettleCardSum other = (SaSettleCardSum)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$cardStatus = getCardStatus(), other$cardStatus = other.getCardStatus(); if ((this$cardStatus == null) ? (other$cardStatus != null) : !this$cardStatus.equals(other$cardStatus)) return false;  Object this$userName = getUserName(), other$userName = other.getUserName(); if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$validStartTime = getValidStartTime(), other$validStartTime = other.getValidStartTime(); if ((this$validStartTime == null) ? (other$validStartTime != null) : !this$validStartTime.equals(other$validStartTime)) return false;  Object this$validEndTime = getValidEndTime(), other$validEndTime = other.getValidEndTime(); return !((this$validEndTime == null) ? (other$validEndTime != null) : !this$validEndTime.equals(other$validEndTime)); } protected boolean canEqual(Object other) { return other instanceof SaSettleCardSum; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $cardStatus = getCardStatus(); result = result * 59 + (($cardStatus == null) ? 43 : $cardStatus.hashCode()); Object $userName = getUserName(); result = result * 59 + (($userName == null) ? 43 : $userName.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $validStartTime = getValidStartTime(); result = result * 59 + (($validStartTime == null) ? 43 : $validStartTime.hashCode()); Object $validEndTime = getValidEndTime(); return result * 59 + (($validEndTime == null) ? 43 : $validEndTime.hashCode()); } public String toString() { return "SaSettleCardSum(mchId=" + getMchId() + ", type=" + getType() + ", cardStatus=" + getCardStatus() + ", userName=" + getUserName() + ", currency=" + getCurrency() + ", validStartTime=" + getValidStartTime() + ", validEndTime=" + getValidEndTime() + ")"; }




  public String getMchId() {
    return this.mchId;
  }

  public String getType() {
    return this.type;
  }

  public String getCardStatus() {
    return this.cardStatus;
  }


  public String getUserName() {
    return this.userName;
  }


  public String getCurrency() {
    return this.currency;
  }


  public LocalDateTime getValidStartTime() {
    return this.validStartTime;
  }


  public LocalDateTime getValidEndTime() {
    return this.validEndTime;
  }
}

