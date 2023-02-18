 package org.hlpay.base.vo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import java.time.LocalDateTime;

 public class SaCardVo
   implements Serializable
 {
   @ApiModelProperty("卡号")
   private String cardNumber;
   @ApiModelProperty("卡类型")
   private String cardType;
   @ApiModelProperty("卡名称")
   private String cardName;
   @ApiModelProperty("出账配置")
   private String configNumber;
   @ApiModelProperty("余额")
   private Long remainPart;
   @ApiModelProperty("持有者名称")
   private String userName;
   @ApiModelProperty("持有者ID")
   private String userId;
   @ApiModelProperty("币种")
   private String currency;
   @ApiModelProperty("有效起始时间（闭）")
   private LocalDateTime validStartTime;
   @ApiModelProperty("有效结束时间（开）")
   private LocalDateTime validEndTime;
   private static final long serialVersionUID = 1L;

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

   public Long getRemainPart() {
     return this.remainPart;
   }

   public void setRemainPart(Long remainPart) {
     this.remainPart = remainPart;
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
     SaCardVo other = (SaCardVo)that;
     return (((getCardNumber() == null) ? (other.getCardNumber() == null) : getCardNumber().equals(other.getCardNumber())) && (
       (getCardType() == null) ? (other.getCardType() == null) : getCardType().equals(other.getCardType())) && (
       (getCardName() == null) ? (other.getCardName() == null) : getCardName().equals(other.getCardName())) && (
       (getConfigNumber() == null) ? (other.getConfigNumber() == null) : getConfigNumber().equals(other.getConfigNumber())) && (
       (getRemainPart() == null) ? (other.getRemainPart() == null) : getRemainPart().equals(other.getRemainPart())) && (
       (getUserName() == null) ? (other.getUserName() == null) : getUserName().equals(other.getUserName())) && (
       (getUserId() == null) ? (other.getUserId() == null) : getUserId().equals(other.getUserId())) && (
       (getCurrency() == null) ? (other.getCurrency() == null) : getCurrency().equals(other.getCurrency())) && (
       (getValidStartTime() == null) ? (other.getValidStartTime() == null) : getValidStartTime().equals(other.getValidStartTime())) && (
       (getValidEndTime() == null) ? (other.getValidEndTime() == null) : getValidEndTime().equals(other.getValidEndTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getCardNumber() == null) ? 0 : getCardNumber().hashCode());
     result = 31 * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
     result = 31 * result + ((getCardName() == null) ? 0 : getCardName().hashCode());
     result = 31 * result + ((getConfigNumber() == null) ? 0 : getConfigNumber().hashCode());
     result = 31 * result + ((getRemainPart() == null) ? 0 : getRemainPart().hashCode());
     result = 31 * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
     result = 31 * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
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
     sb.append(", cardNumber=").append(this.cardNumber);
     sb.append(", cardType=").append(this.cardType);
     sb.append(", cardName=").append(this.cardName);
     sb.append(", configNumber=").append(this.configNumber);
     sb.append(", remainPart=").append(this.remainPart);
     sb.append(", userName=").append(this.userName);
     sb.append(", userId=").append(this.userId);
     sb.append(", currency=").append(this.currency);
     sb.append(", validStartTime=").append(this.validStartTime);
     sb.append(", validEndTime=").append(this.validEndTime);
     sb.append(", serialVersionUID=").append(1L);
     sb.append("]");
     return sb.toString();
   }
 }

