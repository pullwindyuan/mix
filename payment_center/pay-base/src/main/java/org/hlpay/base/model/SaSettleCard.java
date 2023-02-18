package org.hlpay.base.model;
public class SaSettleCard implements Serializable { private String cardId; private String cardNumber; private String cardType; private String cardName; private String configNumber; private String loginAccount; @ApiModelProperty("实际入账金额")
  private String tia;
  @ApiModelProperty("应收金额")
  private String via;
  @ApiModelProperty("优惠金额")
  private String cfa;
  @ApiModelProperty("出账金额")
  private String epa;
  @ApiModelProperty("实际提现金额")
  private String wda;
  @ApiModelProperty("服务费")
  private String pda;
  private String cardStatus;
  private Long cardLimit;
  private LocalDateTime createTime;
  private String userName;

  public SaSettleCard setCardId(String cardId) { this.cardId = cardId; return this; } private String userId; private String isDelete; private Integer flag; private LocalDateTime updateTime; private String currency; private LocalDateTime validStartTime; private LocalDateTime validEndTime; private Long remainPart; private Long freezePart; private Long notBill; private Long expSum; private Long rechargeSum; private Long awardSum; @ApiModelProperty("所在年月") @JSONField(serializeUsing = ToStringSerializer.class) private Long yearMonthGroup; @ApiModelProperty("所在年") @JSONField(serializeUsing = ToStringSerializer.class) private Long yearGroup; @ApiModelProperty("所在月") @JSONField(serializeUsing = ToStringSerializer.class) private Long monthGroup; private static final long serialVersionUID = 1L; public SaSettleCard setCardNumber(String cardNumber) { this.cardNumber = cardNumber; return this; } public SaSettleCard setCardType(String cardType) { this.cardType = cardType; return this; } public SaSettleCard setCardName(String cardName) { this.cardName = cardName; return this; } public SaSettleCard setConfigNumber(String configNumber) { this.configNumber = configNumber; return this; } public SaSettleCard setLoginAccount(String loginAccount) { this.loginAccount = loginAccount; return this; } public SaSettleCard setTia(String tia) { this.tia = tia; return this; } public SaSettleCard setVia(String via) { this.via = via; return this; } public SaSettleCard setCfa(String cfa) { this.cfa = cfa; return this; } public SaSettleCard setEpa(String epa) { this.epa = epa; return this; } public SaSettleCard setWda(String wda) { this.wda = wda; return this; } public SaSettleCard setPda(String pda) { this.pda = pda; return this; } public SaSettleCard setCardStatus(String cardStatus) { this.cardStatus = cardStatus; return this; } public SaSettleCard setCardLimit(Long cardLimit) { this.cardLimit = cardLimit; return this; } public SaSettleCard setCreateTime(LocalDateTime createTime) { this.createTime = createTime; return this; } public SaSettleCard setUserName(String userName) { this.userName = userName; return this; } public SaSettleCard setUserId(String userId) { this.userId = userId; return this; } public SaSettleCard setIsDelete(String isDelete) { this.isDelete = isDelete; return this; } public SaSettleCard setFlag(Integer flag) { this.flag = flag; return this; } public SaSettleCard setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; return this; } public SaSettleCard setCurrency(String currency) { this.currency = currency; return this; } public SaSettleCard setValidStartTime(LocalDateTime validStartTime) { this.validStartTime = validStartTime; return this; } public SaSettleCard setValidEndTime(LocalDateTime validEndTime) { this.validEndTime = validEndTime; return this; } public SaSettleCard setRemainPart(Long remainPart) { this.remainPart = remainPart; return this; } public SaSettleCard setFreezePart(Long freezePart) { this.freezePart = freezePart; return this; } public SaSettleCard setNotBill(Long notBill) { this.notBill = notBill; return this; } public SaSettleCard setExpSum(Long expSum) { this.expSum = expSum; return this; } public SaSettleCard setRechargeSum(Long rechargeSum) { this.rechargeSum = rechargeSum; return this; } public SaSettleCard setAwardSum(Long awardSum) { this.awardSum = awardSum; return this; } public SaSettleCard setYearMonthGroup(Long yearMonthGroup) { this.yearMonthGroup = yearMonthGroup; return this; } public SaSettleCard setYearGroup(Long yearGroup) { this.yearGroup = yearGroup; return this; } public SaSettleCard setMonthGroup(Long monthGroup) { this.monthGroup = monthGroup; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SaSettleCard)) return false;  SaSettleCard other = (SaSettleCard)o; if (!other.canEqual(this)) return false;  Object this$cardId = getCardId(), other$cardId = other.getCardId(); if ((this$cardId == null) ? (other$cardId != null) : !this$cardId.equals(other$cardId)) return false;  Object this$cardNumber = getCardNumber(), other$cardNumber = other.getCardNumber(); if ((this$cardNumber == null) ? (other$cardNumber != null) : !this$cardNumber.equals(other$cardNumber)) return false;  Object this$cardType = getCardType(), other$cardType = other.getCardType(); if ((this$cardType == null) ? (other$cardType != null) : !this$cardType.equals(other$cardType)) return false;  Object this$cardName = getCardName(), other$cardName = other.getCardName(); if ((this$cardName == null) ? (other$cardName != null) : !this$cardName.equals(other$cardName)) return false;  Object this$configNumber = getConfigNumber(), other$configNumber = other.getConfigNumber(); if ((this$configNumber == null) ? (other$configNumber != null) : !this$configNumber.equals(other$configNumber)) return false;  Object this$loginAccount = getLoginAccount(), other$loginAccount = other.getLoginAccount(); if ((this$loginAccount == null) ? (other$loginAccount != null) : !this$loginAccount.equals(other$loginAccount)) return false;  Object this$tia = getTia(), other$tia = other.getTia(); if ((this$tia == null) ? (other$tia != null) : !this$tia.equals(other$tia)) return false;  Object this$via = getVia(), other$via = other.getVia(); if ((this$via == null) ? (other$via != null) : !this$via.equals(other$via)) return false;  Object this$cfa = getCfa(), other$cfa = other.getCfa(); if ((this$cfa == null) ? (other$cfa != null) : !this$cfa.equals(other$cfa)) return false;  Object this$epa = getEpa(), other$epa = other.getEpa(); if ((this$epa == null) ? (other$epa != null) : !this$epa.equals(other$epa)) return false;  Object this$wda = getWda(), other$wda = other.getWda(); if ((this$wda == null) ? (other$wda != null) : !this$wda.equals(other$wda)) return false;  Object this$pda = getPda(), other$pda = other.getPda(); if ((this$pda == null) ? (other$pda != null) : !this$pda.equals(other$pda)) return false;  Object this$cardStatus = getCardStatus(), other$cardStatus = other.getCardStatus(); if ((this$cardStatus == null) ? (other$cardStatus != null) : !this$cardStatus.equals(other$cardStatus)) return false;  Object this$cardLimit = getCardLimit(), other$cardLimit = other.getCardLimit(); if ((this$cardLimit == null) ? (other$cardLimit != null) : !this$cardLimit.equals(other$cardLimit)) return false;  Object this$createTime = getCreateTime(), other$createTime = other.getCreateTime(); if ((this$createTime == null) ? (other$createTime != null) : !this$createTime.equals(other$createTime)) return false;  Object this$userName = getUserName(), other$userName = other.getUserName(); if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName)) return false;  Object this$userId = getUserId(), other$userId = other.getUserId(); if ((this$userId == null) ? (other$userId != null) : !this$userId.equals(other$userId)) return false;  Object this$isDelete = getIsDelete(), other$isDelete = other.getIsDelete(); if ((this$isDelete == null) ? (other$isDelete != null) : !this$isDelete.equals(other$isDelete)) return false;  Object this$flag = getFlag(), other$flag = other.getFlag(); if ((this$flag == null) ? (other$flag != null) : !this$flag.equals(other$flag)) return false;  Object this$updateTime = getUpdateTime(), other$updateTime = other.getUpdateTime(); if ((this$updateTime == null) ? (other$updateTime != null) : !this$updateTime.equals(other$updateTime)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$validStartTime = getValidStartTime(), other$validStartTime = other.getValidStartTime(); if ((this$validStartTime == null) ? (other$validStartTime != null) : !this$validStartTime.equals(other$validStartTime)) return false;  Object this$validEndTime = getValidEndTime(), other$validEndTime = other.getValidEndTime(); if ((this$validEndTime == null) ? (other$validEndTime != null) : !this$validEndTime.equals(other$validEndTime)) return false;  Object this$remainPart = getRemainPart(), other$remainPart = other.getRemainPart(); if ((this$remainPart == null) ? (other$remainPart != null) : !this$remainPart.equals(other$remainPart)) return false;  Object this$freezePart = getFreezePart(), other$freezePart = other.getFreezePart(); if ((this$freezePart == null) ? (other$freezePart != null) : !this$freezePart.equals(other$freezePart)) return false;  Object this$notBill = getNotBill(), other$notBill = other.getNotBill(); if ((this$notBill == null) ? (other$notBill != null) : !this$notBill.equals(other$notBill)) return false;  Object this$expSum = getExpSum(), other$expSum = other.getExpSum(); if ((this$expSum == null) ? (other$expSum != null) : !this$expSum.equals(other$expSum)) return false;  Object this$rechargeSum = getRechargeSum(), other$rechargeSum = other.getRechargeSum(); if ((this$rechargeSum == null) ? (other$rechargeSum != null) : !this$rechargeSum.equals(other$rechargeSum)) return false;  Object this$awardSum = getAwardSum(), other$awardSum = other.getAwardSum(); if ((this$awardSum == null) ? (other$awardSum != null) : !this$awardSum.equals(other$awardSum)) return false;  Object this$yearMonthGroup = getYearMonthGroup(), other$yearMonthGroup = other.getYearMonthGroup(); if ((this$yearMonthGroup == null) ? (other$yearMonthGroup != null) : !this$yearMonthGroup.equals(other$yearMonthGroup)) return false;  Object this$yearGroup = getYearGroup(), other$yearGroup = other.getYearGroup(); if ((this$yearGroup == null) ? (other$yearGroup != null) : !this$yearGroup.equals(other$yearGroup)) return false;  Object this$monthGroup = getMonthGroup(), other$monthGroup = other.getMonthGroup(); return !((this$monthGroup == null) ? (other$monthGroup != null) : !this$monthGroup.equals(other$monthGroup)); } protected boolean canEqual(Object other) { return other instanceof SaSettleCard; } public int hashCode() { int PRIME = 59; result = 1; Object $cardId = getCardId(); result = result * 59 + (($cardId == null) ? 43 : $cardId.hashCode()); Object $cardNumber = getCardNumber(); result = result * 59 + (($cardNumber == null) ? 43 : $cardNumber.hashCode()); Object $cardType = getCardType(); result = result * 59 + (($cardType == null) ? 43 : $cardType.hashCode()); Object $cardName = getCardName(); result = result * 59 + (($cardName == null) ? 43 : $cardName.hashCode()); Object $configNumber = getConfigNumber(); result = result * 59 + (($configNumber == null) ? 43 : $configNumber.hashCode()); Object $loginAccount = getLoginAccount(); result = result * 59 + (($loginAccount == null) ? 43 : $loginAccount.hashCode()); Object $tia = getTia(); result = result * 59 + (($tia == null) ? 43 : $tia.hashCode()); Object $via = getVia(); result = result * 59 + (($via == null) ? 43 : $via.hashCode()); Object $cfa = getCfa(); result = result * 59 + (($cfa == null) ? 43 : $cfa.hashCode()); Object $epa = getEpa(); result = result * 59 + (($epa == null) ? 43 : $epa.hashCode()); Object $wda = getWda(); result = result * 59 + (($wda == null) ? 43 : $wda.hashCode()); Object $pda = getPda(); result = result * 59 + (($pda == null) ? 43 : $pda.hashCode()); Object $cardStatus = getCardStatus(); result = result * 59 + (($cardStatus == null) ? 43 : $cardStatus.hashCode()); Object $cardLimit = getCardLimit(); result = result * 59 + (($cardLimit == null) ? 43 : $cardLimit.hashCode()); Object $createTime = getCreateTime(); result = result * 59 + (($createTime == null) ? 43 : $createTime.hashCode()); Object $userName = getUserName(); result = result * 59 + (($userName == null) ? 43 : $userName.hashCode()); Object $userId = getUserId(); result = result * 59 + (($userId == null) ? 43 : $userId.hashCode()); Object $isDelete = getIsDelete(); result = result * 59 + (($isDelete == null) ? 43 : $isDelete.hashCode()); Object $flag = getFlag(); result = result * 59 + (($flag == null) ? 43 : $flag.hashCode()); Object $updateTime = getUpdateTime(); result = result * 59 + (($updateTime == null) ? 43 : $updateTime.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $validStartTime = getValidStartTime(); result = result * 59 + (($validStartTime == null) ? 43 : $validStartTime.hashCode()); Object $validEndTime = getValidEndTime(); result = result * 59 + (($validEndTime == null) ? 43 : $validEndTime.hashCode()); Object $remainPart = getRemainPart(); result = result * 59 + (($remainPart == null) ? 43 : $remainPart.hashCode()); Object $freezePart = getFreezePart(); result = result * 59 + (($freezePart == null) ? 43 : $freezePart.hashCode()); Object $notBill = getNotBill(); result = result * 59 + (($notBill == null) ? 43 : $notBill.hashCode()); Object $expSum = getExpSum(); result = result * 59 + (($expSum == null) ? 43 : $expSum.hashCode()); Object $rechargeSum = getRechargeSum(); result = result * 59 + (($rechargeSum == null) ? 43 : $rechargeSum.hashCode()); Object $awardSum = getAwardSum(); result = result * 59 + (($awardSum == null) ? 43 : $awardSum.hashCode()); Object $yearMonthGroup = getYearMonthGroup(); result = result * 59 + (($yearMonthGroup == null) ? 43 : $yearMonthGroup.hashCode()); Object $yearGroup = getYearGroup(); result = result * 59 + (($yearGroup == null) ? 43 : $yearGroup.hashCode()); Object $monthGroup = getMonthGroup(); return result * 59 + (($monthGroup == null) ? 43 : $monthGroup.hashCode()); } public String toString() { return "SaSettleCard(cardId=" + getCardId() + ", cardNumber=" + getCardNumber() + ", cardType=" + getCardType() + ", cardName=" + getCardName() + ", configNumber=" + getConfigNumber() + ", loginAccount=" + getLoginAccount() + ", tia=" + getTia() + ", via=" + getVia() + ", cfa=" + getCfa() + ", epa=" + getEpa() + ", wda=" + getWda() + ", pda=" + getPda() + ", cardStatus=" + getCardStatus() + ", cardLimit=" + getCardLimit() + ", createTime=" + getCreateTime() + ", userName=" + getUserName() + ", userId=" + getUserId() + ", isDelete=" + getIsDelete() + ", flag=" + getFlag() + ", updateTime=" + getUpdateTime() + ", currency=" + getCurrency() + ", validStartTime=" + getValidStartTime() + ", validEndTime=" + getValidEndTime() + ", remainPart=" + getRemainPart() + ", freezePart=" + getFreezePart() + ", notBill=" + getNotBill() + ", expSum=" + getExpSum() + ", rechargeSum=" + getRechargeSum() + ", awardSum=" + getAwardSum() + ", yearMonthGroup=" + getYearMonthGroup() + ", yearGroup=" + getYearGroup() + ", monthGroup=" + getMonthGroup() + ")"; }




  public String getCardId() {
    return this.cardId;
  }


  public String getCardNumber() {
    return this.cardNumber;
  }


  public String getCardType() {
    return this.cardType;
  }


  public String getCardName() {
    return this.cardName;
  }


  public String getConfigNumber() {
    return this.configNumber;
  }


  public String getLoginAccount() {
    return this.loginAccount;
  }

  public String getCardStatus() {
    return this.cardStatus;
  }


  public Long getCardLimit() {
    return this.cardLimit;
  }


  public LocalDateTime getCreateTime() {
    return this.createTime;
  }


  public String getUserName() {
    return this.userName;
  }


  public String getUserId() {
    return this.userId;
  }


  public String getIsDelete() {
    return this.isDelete;
  }


  public Integer getFlag() {
    return this.flag;
  }


  public LocalDateTime getUpdateTime() {
    return this.updateTime;
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


  public Long getRemainPart() {
    return this.remainPart;
  }


  public Long getFreezePart() {
    return this.freezePart;
  }


  public Long getNotBill() {
    return this.notBill;
  }

  public Long getExpSum() {
    return this.expSum;
  }


  public Long getRechargeSum() {
    return this.rechargeSum;
  }


  public Long getAwardSum() {
    return this.awardSum;
  }




  public Long getYearMonthGroup() {
    return this.yearMonthGroup;
  }















  public Long getYearGroup() {
    if (this.yearMonthGroup == null) {
      return null;
    }
    String temp = this.yearMonthGroup.toString();
    this.yearGroup = Long.decode(temp.substring(0, 4));
    return this.yearGroup;
  }

  public Long getMonthGroup() {
    if (this.yearMonthGroup == null) {
      return null;
    }
    String temp = this.yearMonthGroup.toString();
    int length = temp.length();
    this.monthGroup = Long.decode(temp.substring(4, length));
    return this.monthGroup;
  }

  public String getTia() {
    if (this.tia == null) {
      if (this.remainPart == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.remainPart.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.tia.contains(".")) {
      return this.tia;
    }
    return this.tia = (new BigDecimal(this.tia)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }

  public String getVia() {
    if (this.via == null) {
      if (this.freezePart == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.freezePart.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.via.contains(".")) {
      return this.via;
    }
    return this.via = (new BigDecimal(this.via)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }

  public String getCfa() {
    if (this.cfa == null) {
      if (this.notBill == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.notBill.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.cfa.contains(".")) {
      return this.cfa;
    }
    return this.cfa = (new BigDecimal(this.cfa)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }

  public String getEpa() {
    if (this.epa == null) {
      if (this.expSum == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.expSum.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.epa.contains(".")) {
      return this.epa;
    }
    return this.epa = (new BigDecimal(this.epa)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }

  public String getWda() {
    if (this.wda == null) {
      if (this.rechargeSum == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.rechargeSum.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.wda.contains(".")) {
      return this.wda;
    }
    return this.wda = (new BigDecimal(this.wda)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }

  public String getPda() {
    if (this.pda == null) {
      if (this.awardSum == null) {
        return "0";
      }
      return BigDecimal.valueOf(this.awardSum.longValue()).divide(new BigDecimal("100"), 2, 7).toPlainString();
    }
    if (this.pda.contains(".")) {
      return this.pda;
    }
    return this.pda = (new BigDecimal(this.pda)).divide(new BigDecimal("100"), 2, 7).toPlainString();
  }


  public static boolean isOverdrawCard(SaSettleCard card) {
    if (card.getCardLimit().longValue() == -1L) {
      return true;
    }
    return false;
  } }
