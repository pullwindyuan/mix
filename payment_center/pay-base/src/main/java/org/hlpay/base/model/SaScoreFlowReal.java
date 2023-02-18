 package org.hlpay.base.model;

 import org.hlpay.base.FieldAnnotation;

 public class SaScoreFlowReal
 {
   @FieldAnnotation(name = "积分流水ID")
   private String scoreFlowId;
   @FieldAnnotation(name = "交易记录号")
   private String dealRecordNumber;
   @FieldAnnotation(name = "商户订单号")
   private String merchantOrderNumber;
   @FieldAnnotation(name = "商品类型")
   private Character productType;
   @FieldAnnotation(name = "出行时间")
   private String tripTime;
   @FieldAnnotation(name = "积分流水号")
   private String scoreFlowNumber;
   @FieldAnnotation(name = "积分流向")
   private Character scoreFlowDirection;
   @FieldAnnotation(name = "流水类型")
   private String flowType;
   @FieldAnnotation(name = "当次交易积分")
   private Long currentDealScore;
   @FieldAnnotation(name = "剩余积分")
   private Long remainScore;
   @FieldAnnotation(name = "本方账号")
   private String oneselfAccount;
   @FieldAnnotation(name = "对方账号")
   private String othersAccount;
   @FieldAnnotation(name = "本方卡号")
   private String oneselfCardNumber;
   @FieldAnnotation(name = "对方卡号")
   private String othersCardNumber;
   @FieldAnnotation(name = "合伙人ID")
   private String proxyId;
   @FieldAnnotation(name = "创建时间")
   private String createTime;
   private String endTime;
   @FieldAnnotation(name = "备注")
   private String comment;
   @FieldAnnotation(name = "账单号")
   private String billNumber;
   @FieldAnnotation(name = "用户ID")
   private String userId;
   private Character isDelete;
   private Integer flag;
   private Character status;

   public String getProxyId() {
     return this.proxyId;
   }

   public void setProxyId(String proxyId) {
     this.proxyId = proxyId;
   }

   public String getEndTime() {
     return this.endTime;
   }

   public void setEndTime(String endTime) {
     this.endTime = endTime;
   }

   public String getScoreFlowId() {
     return this.scoreFlowId;
   }

   public void setScoreFlowId(String scoreFlowId) {
     this.scoreFlowId = scoreFlowId;
   }

   public String getDealRecordNumber() {
     return this.dealRecordNumber;
   }

   public void setDealRecordNumber(String dealRecordNumber) {
     this.dealRecordNumber = dealRecordNumber;
   }

   public String getMerchantOrderNumber() {
     return this.merchantOrderNumber;
   }

   public void setMerchantOrderNumber(String merchantOrderNumber) {
     this.merchantOrderNumber = merchantOrderNumber;
   }

   public Character getProductType() {
     return this.productType;
   }

   public void setProductType(Character productType) {
     this.productType = productType;
   }

   public String getTripTime() {
     return this.tripTime;
   }

   public void setTripTime(String tripTime) {
     this.tripTime = tripTime;
   }

   public String getScoreFlowNumber() {
     return this.scoreFlowNumber;
   }

   public void setScoreFlowNumber(String scoreFlowNumber) {
     this.scoreFlowNumber = scoreFlowNumber;
   }

   public Character getScoreFlowDirection() {
     return this.scoreFlowDirection;
   }

   public void setScoreFlowDirection(Character scoreFlowDirection) {
     this.scoreFlowDirection = scoreFlowDirection;
   }

   public String getFlowType() {
     return this.flowType;
   }

   public void setFlowType(String flowType) {
     this.flowType = flowType;
   }

   public Long getCurrentDealScore() {
     return this.currentDealScore;
   }

   public void setCurrentDealScore(Long currentDealScore) {
     this.currentDealScore = currentDealScore;
   }

   public Long getRemainScore() {
     return this.remainScore;
   }

   public void setRemainScore(Long remainScore) {
     this.remainScore = remainScore;
   }

   public String getOneselfAccount() {
     return this.oneselfAccount;
   }

   public void setOneselfAccount(String oneselfAccount) {
     this.oneselfAccount = oneselfAccount;
   }

   public String getOthersAccount() {
     return this.othersAccount;
   }

   public void setOthersAccount(String othersAccount) {
     this.othersAccount = othersAccount;
   }

   public String getOneselfCardNumber() {
     return this.oneselfCardNumber;
   }

   public void setOneselfCardNumber(String oneselfCardNumber) {
     this.oneselfCardNumber = oneselfCardNumber;
   }

   public String getOthersCardNumber() {
     return this.othersCardNumber;
   }

   public void setOthersCardNumber(String othersCardNumber) {
     this.othersCardNumber = othersCardNumber;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }

   public String getComment() {
     return this.comment;
   }

   public void setComment(String comment) {
     this.comment = comment;
   }

   public String getBillNumber() {
     return this.billNumber;
   }

   public void setBillNumber(String billNumber) {
     this.billNumber = billNumber;
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

   public Integer getFlag() {
     return this.flag;
   }

   public void setFlag(Integer flag) {
     this.flag = flag;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
   }


   public String toString() {
     return "SaScoreFlow [scoreFlowId=" + this.scoreFlowId + ", dealRecordNumber=" + this.dealRecordNumber + ", merchantOrderNumber=" + this.merchantOrderNumber + ", productType=" + this.productType + ", tripTime=" + this.tripTime + ", scoreFlowNumber=" + this.scoreFlowNumber + ", scoreFlowDirection=" + this.scoreFlowDirection + ", flowType=" + this.flowType + ", currentDealScore=" + this.currentDealScore + ", remainScore=" + this.remainScore + ", oneselfAccount=" + this.oneselfAccount + ", othersAccount=" + this.othersAccount + ", oneselfCardNumber=" + this.oneselfCardNumber + ", othersCardNumber=" + this.othersCardNumber + ", createTime=" + this.createTime + ", comment=" + this.comment + ", billNumber=" + this.billNumber + ", userId=" + this.userId + ", isDelete=" + this.isDelete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }





