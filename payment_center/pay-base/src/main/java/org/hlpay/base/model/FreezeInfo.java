 package org.hlpay.base.model;

 import org.hlpay.common.enumm.FreezeActionEnum;






























 public class FreezeInfo
 {
   private String cardNum;
   private String cardType;
   private String currency;
   private String userId;
   private FreezeActionEnum action;
   private Long freezePart;
   private String mchOrderNo;
   private String productCode;
   private String productName;
   private String productType;

   public String getCardNum() {
     return this.cardNum;
   }

   public void setCardNum(String cardNum) {
     this.cardNum = cardNum;
   }

   public String getCardType() {
     return this.cardType;
   }

   public void setCardType(String cardType) {
     this.cardType = cardType;
   }

   public String getCurrency() {
     return this.currency;
   }

   public void setCurrency(String currency) {
     this.currency = currency;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public FreezeActionEnum getAction() {
     return this.action;
   }

   public void setAction(FreezeActionEnum action) {
     this.action = action;
   }

   public Long getFreezePart() {
     return this.freezePart;
   }

   public void setFreezePart(Long freezePart) {
     this.freezePart = freezePart;
   }

   public String getMchOrderNo() {
     return this.mchOrderNo;
   }

   public void setMchOrderNo(String mchOrderNo) {
     this.mchOrderNo = mchOrderNo;
   }

   public String getProductCode() {
     return this.productCode;
   }

   public void setProductCode(String productCode) {
     this.productCode = productCode;
   }

   public String getProductName() {
     return this.productName;
   }

   public void setProductName(String productName) {
     this.productName = productName;
   }

   public String getProductType() {
     return this.productType;
   }

   public void setProductType(String productType) {
     this.productType = productType;
   }
 }





