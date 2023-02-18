 package org.hlpay.base.model;

 public class SaOrder
 {
   private String dealOrderId;
   private String proxyId;
   private String dealRecordNumber;
   private String merchantOrderNumber;
   private Character dealType;
   private String productType;
   private String calculateTime;
   private Double orderAmount;
   private Character dealBringScoreType;
   private Double dealBringScoreRate;
   private Long scoreReturnAmount;
   private Long userAmount;
   private Long partnerAmount;
   private String dealComment;
   private Character dealStatus;
   private String dealCreateTime;
   private String dealEndTime;
   private String userId;
   private Character IsDelete;
   private Character flag;
   private Character status;

   public String getDealOrderId() {
     return this.dealOrderId;
   }

   public void setDealOrderId(String dealOrderId) {
     this.dealOrderId = dealOrderId;
   }

   public String getProxyId() {
     return this.proxyId;
   }

   public void setProxyId(String proxyId) {
     this.proxyId = proxyId;
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

   public Character getDealType() {
     return this.dealType;
   }

   public void setDealType(Character dealType) {
     this.dealType = dealType;
   }

   public String getProductType() {
     return this.productType;
   }

   public void setProductType(String productType) {
     this.productType = productType;
   }

   public String getCalculateTime() {
     return this.calculateTime;
   }

   public void setCalculateTime(String calculateTime) {
     this.calculateTime = calculateTime;
   }

   public Double getOrderAmount() {
     return this.orderAmount;
   }

   public void setOrderAmount(Double orderAmount) {
     this.orderAmount = orderAmount;
   }

   public Character getDealBringScoreType() {
     return this.dealBringScoreType;
   }

   public void setDealBringScoreType(Character dealBringScoreType) {
     this.dealBringScoreType = dealBringScoreType;
   }

   public Double getDealBringScoreRate() {
     return this.dealBringScoreRate;
   }

   public void setDealBringScoreRate(Double dealBringScoreRate) {
     this.dealBringScoreRate = dealBringScoreRate;
   }

   public Long getScoreReturnAmount() {
     return this.scoreReturnAmount;
   }

   public void setScoreReturnAmount(Long scoreReturnAmount) {
     this.scoreReturnAmount = scoreReturnAmount;
   }

   public Long getUserAmount() {
     return this.userAmount;
   }

   public void setUserAmount(Long userAmount) {
     this.userAmount = userAmount;
   }

   public Long getPartnerAmount() {
     return this.partnerAmount;
   }

   public void setPartnerAmount(Long partnerAmount) {
     this.partnerAmount = partnerAmount;
   }

   public String getDealComment() {
     return this.dealComment;
   }

   public void setDealComment(String dealComment) {
     this.dealComment = dealComment;
   }

   public Character getDealStatus() {
     return this.dealStatus;
   }

   public void setDealStatus(Character dealStatus) {
     this.dealStatus = dealStatus;
   }

   public String getDealCreateTime() {
     return this.dealCreateTime;
   }

   public void setDealCreateTime(String dealCreateTime) {
     this.dealCreateTime = dealCreateTime;
   }

   public String getDealEndTime() {
     return this.dealEndTime;
   }

   public void setDealEndTime(String dealEndTime) {
     this.dealEndTime = dealEndTime;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public Character getIsDelete() {
     return this.IsDelete;
   }

   public void setIsDelete(Character isDelete) {
     this.IsDelete = isDelete;
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
     return "SaOrder [dealOrderId=" + this.dealOrderId + ", proxyId=" + this.proxyId + ", dealRecordNumber=" + this.dealRecordNumber + ", merchantOrderNumber=" + this.merchantOrderNumber + ", dealType=" + this.dealType + ", productType=" + this.productType + ", calculateTime=" + this.calculateTime + ", orderAmount=" + this.orderAmount + ", dealBringScoreType=" + this.dealBringScoreType + ", dealBringScoreRate=" + this.dealBringScoreRate + ", scoreReturnAmount=" + this.scoreReturnAmount + ", userAmount=" + this.userAmount + ", partnerAmount=" + this.partnerAmount + ", dealComment=" + this.dealComment + ", dealStatus=" + this.dealStatus + ", dealCreateTime=" + this.dealCreateTime + ", dealEndTime=" + this.dealEndTime + ", userId=" + this.userId + ", IsDelete=" + this.IsDelete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }

