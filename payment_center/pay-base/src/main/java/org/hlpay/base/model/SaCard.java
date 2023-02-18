 package org.hlpay.base.model;

 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import java.time.LocalDateTime;

 public class SaCard
   implements Serializable
 {
   private String cardId;
   private String cardNumber;
   private String cardType;
   private String cardName;
   private String configNumber;
   private String loginAccount;
   private Long remainPart;
   private Long freezePart;
   private Long notBill;
   private String cardStatus;
   private Long cardLimit;
   private LocalDateTime createTime;
   private String userName;
   private String userId;
   private String isDelete;
   private Integer flag;
   private Long expSum;
   private Long rechargeSum;
   private Long awardSum;
   private LocalDateTime updateTime;
   private String currency;
   private LocalDateTime validStartTime;
   private LocalDateTime validEndTime;
   private Long yearMonthGroup;
   private Long yearGroup;
   private String mchType;
   private static final long serialVersionUID = 1L;

   public String getMchType() {
     return this.mchType;
   }

   public void setMchType(String mchType) {
     this.mchType = mchType;
   }

   public Long getYearMonthGroup() {
     if (this.validEndTime == null) {
       return null;
     }
     LocalDateTime temp = this.validEndTime.minusDays(1L);
     return Long.valueOf("" + temp.getYear() + "" + temp.getMonthValue());
   }

   public void setYearMonthGroup(Long yearMonthGroup) {
     this.yearMonthGroup = yearMonthGroup;
   }

   public Long getYearGroup() {
     if (this.validEndTime == null) {
       return null;
     }
     LocalDateTime temp = this.validEndTime.minusDays(1L);
     return Long.valueOf("" + temp.getYear());
   }

   public void setYearGroup(Long yearGroup) {
     this.yearGroup = yearGroup;
   }



   public String getCardId() {
     return this.cardId;
   }

   public void setCardId(String cardId) {
     this.cardId = cardId;
   }

   public String getCardNumber() {
     return this.cardNumber;
   }

   public void setCardNumber(String cardNumber) {
     this.cardNumber = cardNumber;
   }

   public String getCardType() {
     return this.cardType;
   }

   public void setCardType(String cardType) {
     this.cardType = cardType;
   }

   public String getCardName() {
     return this.cardName;
   }

   public void setCardName(String cardName) {
     this.cardName = cardName;
   }

   public String getConfigNumber() {
     return this.configNumber;
   }

   public void setConfigNumber(String configNumber) {
     this.configNumber = configNumber;
   }

   public String getLoginAccount() {
     return this.loginAccount;
   }

   public void setLoginAccount(String loginAccount) {
     this.loginAccount = loginAccount;
   }

   public Long getRemainPart() {
     return this.remainPart;
   }

   public void setRemainPart(Long remainPart) {
     this.remainPart = remainPart;
   }

   public Long getFreezePart() {
     return this.freezePart;
   }

   public void setFreezePart(Long freezePart) {
     this.freezePart = freezePart;
   }

   public Long getNotBill() {
     return this.notBill;
   }

   public void setNotBill(Long notBill) {
     this.notBill = notBill;
   }

   public String getCardStatus() {
     return this.cardStatus;
   }

   public void setCardStatus(String cardStatus) {
     this.cardStatus = cardStatus;
   }

   public Long getCardLimit() {
     return this.cardLimit;
   }

   public void setCardLimit(Long cardLimit) {
     this.cardLimit = cardLimit;
   }

   public LocalDateTime getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
     this.createTime = createTime;
   }

   public String getUserName() {
     return this.userName;
   }

   public void setUserName(String userName) {
     this.userName = userName;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public String getIsDelete() {
     return this.isDelete;
   }

   public void setIsDelete(String isDelete) {
     this.isDelete = isDelete;
   }

   public Integer getFlag() {
     return this.flag;
   }

   public void setFlag(Integer flag) {
     this.flag = flag;
   }

   public Long getExpSum() {
     return this.expSum;
   }

   public void setExpSum(Long expSum) {
     this.expSum = expSum;
   }

   public Long getRechargeSum() {
     return this.rechargeSum;
   }

   public void setRechargeSum(Long rechargeSum) {
     this.rechargeSum = rechargeSum;
   }

   public Long getAwardSum() {
     return this.awardSum;
   }

   public void setAwardSum(Long awardSum) {
     this.awardSum = awardSum;
   }

   public LocalDateTime getUpdateTime() {
     return this.updateTime;
   }

   public void setUpdateTime(LocalDateTime updateTime) {
     this.updateTime = updateTime;
   }

   public String getCurrency() {
     return this.currency;
   }

   public void setCurrency(String currency) {
     this.currency = currency;
   }

   public LocalDateTime getValidStartTime() {
     return this.validStartTime;
   }

   public void setValidStartTime(LocalDateTime validStartTime) {
     this.validStartTime = validStartTime;
   }

   public LocalDateTime getValidEndTime() {
     return this.validEndTime;
   }


   public Double getPoundageRateByPayChannel(String channel) {
     JSONObject configObj = JSONObject.parseObject(this.configNumber);
     JSONObject settleParams = configObj.getJSONObject("settleParams");
     Double temp = settleParams.getDouble(channel);
     if (temp == null) {
       temp = settleParams.getDouble("pr");
     }
     return temp;
   }
   public void setValidEndTime(LocalDateTime validEndTime) {
     this.validEndTime = validEndTime;
   }


   public boolean equals(Object that) {
     if (this == that) {
       return true;
     }
     if (that == null) {
       return false;
     }
     if (getClass() != that.getClass()) {
       return false;
     }
     SaCard other = (SaCard)that;
     return (((getCardId() == null) ? (other.getCardId() == null) : getCardId().equals(other.getCardId())) && (
       (getCardNumber() == null) ? (other.getCardNumber() == null) : getCardNumber().equals(other.getCardNumber())) && (
       (getCardType() == null) ? (other.getCardType() == null) : getCardType().equals(other.getCardType())) && (
       (getCardName() == null) ? (other.getCardName() == null) : getCardName().equals(other.getCardName())) && (
       (getConfigNumber() == null) ? (other.getConfigNumber() == null) : getConfigNumber().equals(other.getConfigNumber())) && (
       (getLoginAccount() == null) ? (other.getLoginAccount() == null) : getLoginAccount().equals(other.getLoginAccount())) && (
       (getRemainPart() == null) ? (other.getRemainPart() == null) : getRemainPart().equals(other.getRemainPart())) && (
       (getFreezePart() == null) ? (other.getFreezePart() == null) : getFreezePart().equals(other.getFreezePart())) && (
       (getNotBill() == null) ? (other.getNotBill() == null) : getNotBill().equals(other.getNotBill())) && (
       (getCardStatus() == null) ? (other.getCardStatus() == null) : getCardStatus().equals(other.getCardStatus())) && (
       (getCardLimit() == null) ? (other.getCardLimit() == null) : getCardLimit().equals(other.getCardLimit())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUserName() == null) ? (other.getUserName() == null) : getUserName().equals(other.getUserName())) && (
       (getUserId() == null) ? (other.getUserId() == null) : getUserId().equals(other.getUserId())) && (
       (getIsDelete() == null) ? (other.getIsDelete() == null) : getIsDelete().equals(other.getIsDelete())) && (
       (getFlag() == null) ? (other.getFlag() == null) : getFlag().equals(other.getFlag())) && (
       (getExpSum() == null) ? (other.getExpSum() == null) : getExpSum().equals(other.getExpSum())) && (
       (getRechargeSum() == null) ? (other.getRechargeSum() == null) : getRechargeSum().equals(other.getRechargeSum())) && (
       (getAwardSum() == null) ? (other.getAwardSum() == null) : getAwardSum().equals(other.getAwardSum())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())) && (
       (getCurrency() == null) ? (other.getCurrency() == null) : getCurrency().equals(other.getCurrency())) && (
       (getValidStartTime() == null) ? (other.getValidStartTime() == null) : getValidStartTime().equals(other.getValidStartTime())) && (
       (getValidEndTime() == null) ? (other.getValidEndTime() == null) : getValidEndTime().equals(other.getValidEndTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getCardId() == null) ? 0 : getCardId().hashCode());
     result = 31 * result + ((getCardNumber() == null) ? 0 : getCardNumber().hashCode());
     result = 31 * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
     result = 31 * result + ((getCardName() == null) ? 0 : getCardName().hashCode());
     result = 31 * result + ((getConfigNumber() == null) ? 0 : getConfigNumber().hashCode());
     result = 31 * result + ((getLoginAccount() == null) ? 0 : getLoginAccount().hashCode());
     result = 31 * result + ((getRemainPart() == null) ? 0 : getRemainPart().hashCode());
     result = 31 * result + ((getFreezePart() == null) ? 0 : getFreezePart().hashCode());
     result = 31 * result + ((getNotBill() == null) ? 0 : getNotBill().hashCode());
     result = 31 * result + ((getCardStatus() == null) ? 0 : getCardStatus().hashCode());
     result = 31 * result + ((getCardLimit() == null) ? 0 : getCardLimit().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
     result = 31 * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
     result = 31 * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
     result = 31 * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
     result = 31 * result + ((getExpSum() == null) ? 0 : getExpSum().hashCode());
     result = 31 * result + ((getRechargeSum() == null) ? 0 : getRechargeSum().hashCode());
     result = 31 * result + ((getAwardSum() == null) ? 0 : getAwardSum().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     result = 31 * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
     result = 31 * result + ((getValidStartTime() == null) ? 0 : getValidStartTime().hashCode());
     result = 31 * result + ((getValidEndTime() == null) ? 0 : getValidEndTime().hashCode());
     return result;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", cardId=").append(this.cardId);
     sb.append(", cardNumber=").append(this.cardNumber);
     sb.append(", cardType=").append(this.cardType);
     sb.append(", cardName=").append(this.cardName);
     sb.append(", configNumber=").append(this.configNumber);
     sb.append(", loginAccount=").append(this.loginAccount);
     sb.append(", remainPart=").append(this.remainPart);
     sb.append(", freezePart=").append(this.freezePart);
     sb.append(", notBill=").append(this.notBill);
     sb.append(", cardStatus=").append(this.cardStatus);
     sb.append(", cardLimit=").append(this.cardLimit);
     sb.append(", createTime=").append(this.createTime);
     sb.append(", userName=").append(this.userName);
     sb.append(", userId=").append(this.userId);
     sb.append(", isDelete=").append(this.isDelete);
     sb.append(", flag=").append(this.flag);
     sb.append(", expSum=").append(this.expSum);
     sb.append(", rechargeSum=").append(this.rechargeSum);
     sb.append(", awardSum=").append(this.awardSum);
     sb.append(", updateTime=").append(this.updateTime);
     sb.append(", currency=").append(this.currency);
     sb.append(", validStartTime=").append(this.validStartTime);
     sb.append(", validEndTime=").append(this.validEndTime);
     sb.append(", serialVersionUID=").append(1L);
     sb.append("]");
     return sb.toString();
   }

   public static boolean isOverdrawCard(SaCard card) {
     if (card.getCardLimit().longValue() == -1L) {
       return true;
     }
     return false;
   }
 }
