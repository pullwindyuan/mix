 package org.hlpay.base.model;












 public class SaCardData
 {
   private String dataId;
   private String dataName;
   private String dataCode;
   private String createTime;
   private Character status;
   private Character flag;
   private String dataType;

   public String getDataType() {
     return this.dataType;
   }

   public void setDataType(String dataType) {
     this.dataType = dataType;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }

   public String getDataId() {
     return this.dataId;
   }

   public void setDataId(String dataId) {
     this.dataId = dataId;
   }

   public String getDataName() {
     return this.dataName;
   }

   public void setDataName(String dataName) {
     this.dataName = dataName;
   }

   public String getDataCode() {
     return this.dataCode;
   }

   public void setDataCode(String dataCode) {
     this.dataCode = dataCode;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
   }

   public Character getFlag() {
     return this.flag;
   }

   public void setFlag(Character flag) {
     this.flag = flag;
   }


   public String toString() {
     return "SaCardData [dataId=" + this.dataId + ", dataName=" + this.dataName + ", dataCode=" + this.dataCode + ", status=" + this.status + ", flag=" + this.flag + "]";
   }
 }

