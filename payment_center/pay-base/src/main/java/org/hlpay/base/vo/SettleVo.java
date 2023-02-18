 package org.hlpay.base.vo;
 public class SettleVo implements Serializable { @ApiModelProperty("卡号")
   private String cardNumber; @ApiModelProperty("卡类型")
   private String cardType; @ApiModelProperty("卡名称")
   private String cardName; @ApiModelProperty("出账配置")
   private String configNumber; @ApiModelProperty("实际入账金额")
   private Long tia; @ApiModelProperty("应收金额")
   private Long via;
   @ApiModelProperty("优惠金额")
   private Long cfa;
   @ApiModelProperty("出账金额")
   private Long epa;

   public SettleVo setCardNumber(String cardNumber) { this.cardNumber = cardNumber; return this; } @ApiModelProperty("实际提现金额") private Long wda; @ApiModelProperty("服务费") private Long pda; @ApiModelProperty("持有者名称") private String userName; @ApiModelProperty("持有者ID") private String userId; @ApiModelProperty("币种") private String currency; @ApiModelProperty("有效起始时间（闭）") private LocalDateTime validStartTime; @ApiModelProperty("有效结束时间（开）") private LocalDateTime validEndTime; private static final long serialVersionUID = 1L; public SettleVo setCardType(String cardType) { this.cardType = cardType; return this; } public SettleVo setCardName(String cardName) { this.cardName = cardName; return this; } public SettleVo setConfigNumber(String configNumber) { this.configNumber = configNumber; return this; } public SettleVo setTia(Long tia) { this.tia = tia; return this; } public SettleVo setVia(Long via) { this.via = via; return this; } public SettleVo setCfa(Long cfa) { this.cfa = cfa; return this; } public SettleVo setEpa(Long epa) { this.epa = epa; return this; } public SettleVo setWda(Long wda) { this.wda = wda; return this; } public SettleVo setPda(Long pda) { this.pda = pda; return this; } public SettleVo setUserName(String userName) { this.userName = userName; return this; } public SettleVo setUserId(String userId) { this.userId = userId; return this; } public SettleVo setCurrency(String currency) { this.currency = currency; return this; } public SettleVo setValidStartTime(LocalDateTime validStartTime) { this.validStartTime = validStartTime; return this; } public SettleVo setValidEndTime(LocalDateTime validEndTime) { this.validEndTime = validEndTime; return this; }

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

   public Long getTia() {
     return this.tia;
   }

   public Long getVia() {
     return this.via;
   }

   public Long getCfa() {
     return this.cfa;
   }

   public Long getEpa() {
     return this.epa;
   }

   public Long getWda() {
     return this.wda;
   }


   public String getUserName() {
     return this.userName;
   }

   public String getUserId() {
     return this.userId;
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
     SettleVo other = (SettleVo)that;
     return (((getCardNumber() == null) ? (other.getCardNumber() == null) : getCardNumber().equals(other.getCardNumber())) && (
       (getCardType() == null) ? (other.getCardType() == null) : getCardType().equals(other.getCardType())) && (
       (getCardName() == null) ? (other.getCardName() == null) : getCardName().equals(other.getCardName())) && (
       (getConfigNumber() == null) ? (other.getConfigNumber() == null) : getConfigNumber().equals(other.getConfigNumber())) && (
       (getTia() == null) ? (other.getTia() == null) : getTia().equals(other.getTia())) && (
       (getUserName() == null) ? (other.getUserName() == null) : getUserName().equals(other.getUserName())) && (
       (getUserId() == null) ? (other.getUserId() == null) : getUserId().equals(other.getUserId())) && (
       (getCurrency() == null) ? (other.getCurrency() == null) : getCurrency().equals(other.getCurrency())) && (
       (getValidStartTime() == null) ? (other.getValidStartTime() == null) : getValidStartTime().equals(other.getValidStartTime())) && (
       (getValidEndTime() == null) ? (other.getValidEndTime() == null) : getValidEndTime().equals(other.getValidEndTime())));
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
     sb.append(", tia=").append(this.tia);
     sb.append(", via=").append(this.via);
     sb.append(", cfa=").append(this.cfa);
     sb.append(", epa=").append(this.epa);
     sb.append(", wda=").append(this.wda);
     sb.append(", pda=").append(this.pda);
     sb.append(", userName=").append(this.userName);
     sb.append(", userId=").append(this.userId);
     sb.append(", currency=").append(this.currency);
     sb.append(", validStartTime=").append(this.validStartTime);
     sb.append(", validEndTime=").append(this.validEndTime);
     sb.append(", serialVersionUID=").append(1L);
     sb.append("]");
     return sb.toString();
   } }





