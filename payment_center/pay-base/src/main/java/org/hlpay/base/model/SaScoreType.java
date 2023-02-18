package org.hlpay.base.model;

 public class SaScoreType
 {
   private String typeId;
   private String typeName;
   private String typeCode;
   private String typeComment;
   private String createTime;
   private Character status;
   private Character isDelete;
   private Character flag;

   public SaScoreType() {}

   public SaScoreType(String typeId, String typeName, String typeCode, String typeComment, String createTime, Character status, Character isDelete, Character flag) {
     this.typeId = typeId;
     this.typeName = typeName;
     this.typeCode = typeCode;
     this.typeComment = typeComment;
     this.createTime = createTime;
     this.status = status;
     this.isDelete = isDelete;
     this.flag = flag;
   }

   public String getTypeId() {
     return this.typeId;
   }

   public void setTypeId(String typeId) {
     this.typeId = typeId;
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

   public String getTypeComment() {
     return this.typeComment;
   }

   public void setTypeComment(String typeComment) {
     this.typeComment = typeComment;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
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
     return "SaScoreType [typeId=" + this.typeId + ", typeName=" + this.typeName + ", typeCode=" + this.typeCode + ", typeComment=" + this.typeComment + ", createTime=" + this.createTime + ", status=" + this.status + ", isDelete=" + this.isDelete + ", flag=" + this.flag + "]";
   }
 }





