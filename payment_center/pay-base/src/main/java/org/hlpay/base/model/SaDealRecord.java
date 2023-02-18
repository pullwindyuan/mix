package org.hlpay.base.model;

import com.alibaba.fastjson.JSONArray;
import java.time.LocalDateTime;
import org.hlpay.base.FieldAnnotation;

 public class SaDealRecord
 {
   @FieldAnnotation(name = "交易记录ID")
   private String dealRecordId;
   @FieldAnnotation(name = "交易记录号")
   private String dealRecordNumber;
   @FieldAnnotation(name = "商户订单号")
   private String merchantOrderNumber;
   @FieldAnnotation(name = "退款单号")
   private String refundNumber;
   @FieldAnnotation(name = "用户登录账号")
   private String loginAccount;
   @FieldAnnotation(name = "用户区域")
   private String userRegion;
   @FieldAnnotation(name = "代理号")
   private String proxyNumber;
   @FieldAnnotation(name = "交易类型")
   private String dealType;
   @FieldAnnotation(name = "会员商品类型")
   private String productType;
   @FieldAnnotation(name = "合伙人商品类型")
   private String partnerProductType;
   @FieldAnnotation(name = "出行时间")
   private String tripTime;
   @FieldAnnotation(name = "订单总额")
   private Double orderAmount;
   @FieldAnnotation(name = "创建时生产积分类型")
   private Character dealBringScoreType;
   @FieldAnnotation(name = "创建时生产积分比率")
   private Double dealBringScoreRate;
   @FieldAnnotation(name = "创建时代理分账比率")
   private Double dealProxyAccountRate;
   @FieldAnnotation(name = "积分返还总额")
   private Long scoreReturnAmount;
   @FieldAnnotation(name = "用户返送金额")
   private Long userAmount;
   @FieldAnnotation(name = "合伙人返送金额")
   private Long partnerAmount;
   @FieldAnnotation(name = "订单详情")
   private String orderDetail;
   @FieldAnnotation(name = "交易详情")
   private String dealDetail;
   @FieldAnnotation(name = "交易备注")
   private String dealComment;
   @FieldAnnotation(name = "交易状态")
   private Character dealStatus;
   @FieldAnnotation(name = "交易创建时间")
   private String dealCreateTime;
   @FieldAnnotation(name = "交易完成时间")
   private String dealEndTime;
   @FieldAnnotation(name = "会员绑定手机")
   private String userPhone;
   @FieldAnnotation(name = "合伙人绑定手机")
   private String proxyPhone;
   @FieldAnnotation(name = "用户ID")
   private String userId;
   @FieldAnnotation(name = "用户名称")
   private String userName;
   private Character IsDelete;
   private String flag;
   private String partnerFlag;
   private Character status;
   @FieldAnnotation(name = "本方账号")
   private String oneselfAccount;
   @FieldAnnotation(name = "对方账号")
   private String othersAccount;
   @FieldAnnotation(name = "合伙人账号")
   private String proxyAccount;
   @FieldAnnotation(name = "本方卡号")
   private String oneselfCardNumber;
   @FieldAnnotation(name = "目标卡号：目标卡号集合")
   private String othersCardNumber;
   @FieldAnnotation(name = "目标卡号：单卡号")
   private String otherCardNumber;
   @FieldAnnotation(name = "渠道ID")
   private String channelId;
   private Long price;
   private Long cutOff;
   private String cardType;
   private String currency;
   private LocalDateTime externalPaySuccessTime;
   private boolean preFreeze;
   private JSONArray orderList;
   private String kernelCardNum;

   public Long getPrice() {
     return this.price;
   }

   public void setPrice(Long price) {
     this.price = price;
   }

   public Long getCutOff() {
     return this.cutOff;
   }

   public void setCutOff(Long cutOff) {
     this.cutOff = cutOff;
   }

   public String getCardType() {
     return this.cardType;
   }

   public void setCardType(String cardType) {
     this.cardType = cardType;
   }




























   public String getKernelCardNum() {
     return this.kernelCardNum;
   }

   public void setKernelCardNum(String kernelCardNum) {
     this.kernelCardNum = kernelCardNum;
   }

   public boolean isPreFreeze() {
     return this.preFreeze;
   }

   public void setPreFreeze(boolean preFreeze) {
     this.preFreeze = preFreeze;
   }

   public JSONArray getOrderList() {
     return this.orderList;
   }

   public void setOrderList(JSONArray orderList) {
     this.orderList = orderList;
   }

   public LocalDateTime getExternalPaySuccessTime() {
     return this.externalPaySuccessTime;
   }

   public void setExternalPaySuccessTime(LocalDateTime externalPaySuccessTime) {
     this.externalPaySuccessTime = externalPaySuccessTime;
   }

   public String getCurrency() {
     return this.currency;
   }

   public void setCurrency(String currency) {
     this.currency = currency;
   }

   public String getChannelId() {
     return this.channelId;
   }

   public void setChannelId(String channelId) {
     this.channelId = channelId;
   }

   public String getPartnerProductType() {
     return this.partnerProductType;
   }

   public void setPartnerProductType(String partnerProductType) {
     this.partnerProductType = partnerProductType;
   }

   public String getPartnerFlag() {
     return this.partnerFlag;
   }

   public void setPartnerFlag(String partnerFlag) {
     this.partnerFlag = partnerFlag;
   }

   public String getUserPhone() {
     return this.userPhone;
   }

   public void setUserPhone(String userPhone) {
     this.userPhone = userPhone;
   }

   public String getProxyPhone() {
     return this.proxyPhone;
   }

   public void setProxyPhone(String proxyPhone) {
     this.proxyPhone = proxyPhone;
   }

   public String getRefundNumber() {
     return this.refundNumber;
   }

   public void setRefundNumber(String refundNumber) {
     this.refundNumber = refundNumber;
   }

   public String getDealRecordId() {
     return this.dealRecordId;
   }

   public void setDealRecordId(String dealRecordId) {
     this.dealRecordId = dealRecordId;
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

   public String getLoginAccount() {
     return this.loginAccount;
   }

   public void setLoginAccount(String loginAccount) {
     this.loginAccount = loginAccount;
   }

   public String getUserRegion() {
     return this.userRegion;
   }

   public void setUserRegion(String userRegion) {
     this.userRegion = userRegion;
   }

   public String getProxyNumber() {
     return this.proxyNumber;
   }

   public void setProxyNumber(String proxyNumber) {
     this.proxyNumber = proxyNumber;
   }

   public String getDealType() {
     return this.dealType;
   }

   public void setDealType(String dealType) {
     this.dealType = dealType;
   }

   public String getProductType() {
     return this.productType;
   }

   public void setProductType(String productType) {
     this.productType = productType;
   }

   public String getTripTime() {
     return this.tripTime;
   }

   public void setTripTime(String tripTime) {
     this.tripTime = tripTime;
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

   public Double getDealProxyAccountRate() {
     return this.dealProxyAccountRate;
   }

   public void setDealProxyAccountRate(Double dealProxyAccountRate) {
     this.dealProxyAccountRate = dealProxyAccountRate;
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

   public String getOrderDetail() {
     return this.orderDetail;
   }

   public void setOrderDetail(String orderDetail) {
     this.orderDetail = orderDetail;
   }

   public String getDealDetail() {
     return this.dealDetail;
   }

   public void setDealDetail(String dealDetail) {
     this.dealDetail = dealDetail;
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

   public String getUserName() {
     return this.userName;
   }

   public void setUserName(String userName) {
     this.userName = userName;
   }

   public Character getIsDelete() {
     return this.IsDelete;
   }

   public void setIsDelete(Character isDelete) {
     this.IsDelete = isDelete;
   }

   public String getFlag() {
     return this.flag;
   }

   public void setFlag(String flag) {
     this.flag = flag;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
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

   public String getProxyAccount() {
     return this.proxyAccount;
   }

   public void setProxyAccount(String proxyAccount) {
     this.proxyAccount = proxyAccount;
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

   public String getOtherCardNumber() {
     return this.otherCardNumber;
   }

   public void setOtherCardNumber(String otherCardNumber) {
     this.otherCardNumber = otherCardNumber;
   }


   public String toString() {
     return "SaDealRecord [dealRecordId=" + this.dealRecordId + ", dealRecordNumber=" + this.dealRecordNumber + ", merchantOrderNumber=" + this.merchantOrderNumber + ", refundNumber=" + this.refundNumber + ", loginAccount=" + this.loginAccount + ", userRegion=" + this.userRegion + ", proxyNumber=" + this.proxyNumber + ", dealType=" + this.dealType + ", productType=" + this.productType + ", partnerProductType=" + this.partnerProductType + ", tripTime=" + this.tripTime + ", orderAmount=" + this.orderAmount + ", dealBringScoreType=" + this.dealBringScoreType + ", dealBringScoreRate=" + this.dealBringScoreRate + ", dealProxyAccountRate=" + this.dealProxyAccountRate + ", scoreReturnAmount=" + this.scoreReturnAmount + ", userAmount=" + this.userAmount + ", partnerAmount=" + this.partnerAmount + ", orderDetail=" + this.orderDetail + ", dealDetail=" + this.dealDetail + ", dealComment=" + this.dealComment + ", dealStatus=" + this.dealStatus + ", dealCreateTime=" + this.dealCreateTime + ", dealEndTime=" + this.dealEndTime + ", userPhone=" + this.userPhone + ", proxyPhone=" + this.proxyPhone + ", userId=" + this.userId + ", userName=" + this.userName + ", IsDelete=" + this.IsDelete + ", flag=" + this.flag + ", partnerFlag=" + this.partnerFlag + ", status=" + this.status + ", oneselfAccount=" + this.oneselfAccount + ", othersAccount=" + this.othersAccount + ", proxyAccount=" + this.proxyAccount + ", oneselfCardNumber=" + this.oneselfCardNumber + ", othersCardNumber=" + this.othersCardNumber + ", otherCardNumber=" + this.otherCardNumber + "]";
   }
 }
