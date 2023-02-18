 package org.hlpay.base.model;

 import org.hlpay.base.FieldAnnotation;

 public class SysScoreSettleBill
 {
   @FieldAnnotation(name = "账单ID")
   private String billId;
   @FieldAnnotation(name = "账单号")
   private String billNumber;
   @FieldAnnotation(name = "账单开始日期")
   private String billStartDate;
   @FieldAnnotation(name = "账单结束日期")
   private String billEndDate;
   @FieldAnnotation(name = "卡号")
   private String cardNumber;
   @FieldAnnotation(name = "用户登录账号")
   private String loginAccount;
   @FieldAnnotation(name = "当期结算积分")
   private Long currentSettleScore;
   @FieldAnnotation(name = "出账日期")
   private String billDate;
   @FieldAnnotation(name = "结算日期")
   private String settleDate;
   @FieldAnnotation(name = "账单状态")
   private Character billStatus;
   @FieldAnnotation(name = "创建时间")
   private String createTime;
   @FieldAnnotation(name = "用户ID")
   private String userId;
   private Character isDelete;
   private Character flag;
   private Character status;

   public SysScoreSettleBill() {}

   public SysScoreSettleBill(String billId, String billNumber, String billStartDate, String billEndDate, String cardNumber, String loginAccount, Long currentSettleScore, String billDate, String settleDate, Character billStatus, String createTime, String userId, Character isDelete, Character flag, Character status) {
     this.billId = billId;
     this.billNumber = billNumber;
     this.billStartDate = billStartDate;
     this.billEndDate = billEndDate;
     this.cardNumber = cardNumber;
     this.loginAccount = loginAccount;
     this.currentSettleScore = currentSettleScore;
     this.billDate = billDate;
     this.settleDate = settleDate;
     this.billStatus = billStatus;
     this.createTime = createTime;
     this.userId = userId;
     this.isDelete = isDelete;
     this.flag = flag;
     this.status = status;
   }

   public String getBillId() {
     return this.billId;
   }

   public void setBillId(String billId) {
     this.billId = billId;
   }

   public String getBillNumber() {
     return this.billNumber;
   }

   public void setBillNumber(String billNumber) {
     this.billNumber = billNumber;
   }

   public String getBillStartDate() {
     return this.billStartDate;
   }

   public void setBillStartDate(String billStartDate) {
     this.billStartDate = billStartDate;
   }

   public String getBillEndDate() {
     return this.billEndDate;
   }

   public void setBillEndDate(String billEndDate) {
     this.billEndDate = billEndDate;
   }

   public String getCardNumber() {
     return this.cardNumber;
   }

   public void setCardNumber(String cardNumber) {
     this.cardNumber = cardNumber;
   }

   public String getLoginAccount() {
     return this.loginAccount;
   }

   public void setLoginAccount(String loginAccount) {
     this.loginAccount = loginAccount;
   }

   public Long getCurrentSettleScore() {
     return this.currentSettleScore;
   }

   public void setCurrentSettleScore(Long currentSettleScore) {
     this.currentSettleScore = currentSettleScore;
   }

   public String getBillDate() {
     return this.billDate;
   }

   public void setBillDate(String billDate) {
     this.billDate = billDate;
   }

   public String getSettleDate() {
     return this.settleDate;
   }

   public void setSettleDate(String settleDate) {
     this.settleDate = settleDate;
   }

   public Character getBillStatus() {
     return this.billStatus;
   }

   public void setBillStatus(Character billStatus) {
     this.billStatus = billStatus;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public Character getIsDelete() {
     return this.isDelete;
   }

   public void setIsDelete(Character isDelete) {
     this.isDelete = isDelete;
   }

   public Character getFlag() {
     return this.flag;
   }

   public void setFlag(Character flag) {
     this.flag = flag;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
   }


   public String toString() {
     return "SysScoreSettleBill [billId=" + this.billId + ", billNumber=" + this.billNumber + ", billStartDate=" + this.billStartDate + ", billEndDate=" + this.billEndDate + ", cardNumber=" + this.cardNumber + ", loginAccount=" + this.loginAccount + ", currentSettleScore=" + this.currentSettleScore + ", billDate=" + this.billDate + ", settleDate=" + this.settleDate + ", billStatus=" + this.billStatus + ", createTime=" + this.createTime + ", userId=" + this.userId + ", isDelete=" + this.isDelete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }

