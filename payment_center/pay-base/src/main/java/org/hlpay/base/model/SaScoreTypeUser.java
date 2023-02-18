 package org.hlpay.base.model;

 public class SaScoreTypeUser
 {
   private String scoreTypeId;
   private String typeName;
   private String typeCode;
   private Double remianPart;
   private String loginAccount;
   private String userId;
   private Character status;
   private Character isDelete;
   private Character flag;

   public SaScoreTypeUser() {}

   public SaScoreTypeUser(String scoreTypeId, String typeName, String typeCode, Double remianPart, String loginAccount, String userId, Character status, Character isDelete, Character flag) {
     this.scoreTypeId = scoreTypeId;
     this.typeName = typeName;
     this.typeCode = typeCode;
     this.remianPart = remianPart;
     this.loginAccount = loginAccount;
     this.userId = userId;
     this.status = status;
     this.isDelete = isDelete;
     this.flag = flag;
   }

   public String getScoreTypeId() {
     return this.scoreTypeId;
   }

   public void setScoreTypeId(String scoreTypeId) {
     this.scoreTypeId = scoreTypeId;
   }

   public String getTypeName() {
     return this.typeName;
   }

   public void setTypeName(String typeName) {
     this.typeName = typeName;
   }

   public String getTypeCode() {
     return this.typeCode;
   }

   public void setTypeCode(String typeCode) {
     this.typeCode = typeCode;
   }

   public Double getRemianPart() {
     return this.remianPart;
   }

   public void setRemianPart(Double remianPart) {
     this.remianPart = remianPart;
   }

   public String getLoginAccount() {
     return this.loginAccount;
   }

   public void setLoginAccount(String loginAccount) {
     this.loginAccount = loginAccount;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
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


   public String toString() {
     return "SaScoreTypeUser [scoreTypeId=" + this.scoreTypeId + ", typeName=" + this.typeName + ", typeCode=" + this.typeCode + ", remianPart=" + this.remianPart + ", loginAccount=" + this.loginAccount + ", userId=" + this.userId + ", status=" + this.status + ", isDelete=" + this.isDelete + ", flag=" + this.flag + "]";
   }
 }
