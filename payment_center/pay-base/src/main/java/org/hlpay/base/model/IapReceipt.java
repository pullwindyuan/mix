 package org.hlpay.base.model;

 import java.io.Serializable;
 import java.util.Date;















































 public class IapReceipt
   implements Serializable
 {
   private String payOrderId;
   private String mchId;
   private String transactionId;
   private Byte status;
   private Byte handleCount;
   private Date createTime;
   private Date updateTime;
   private String receiptData;
   private static final long serialVersionUID = 1L;

   public String getPayOrderId() {
     return this.payOrderId;
   }

   public void setPayOrderId(String payOrderId) {
     this.payOrderId = payOrderId;
   }

   public String getMchId() {
     return this.mchId;
   }

   public void setMchId(String mchId) {
     this.mchId = mchId;
   }

   public String getTransactionId() {
     return this.transactionId;
   }

   public void setTransactionId(String transactionId) {
     this.transactionId = transactionId;
   }

   public Byte getStatus() {
     return this.status;
   }

   public void setStatus(Byte status) {
     this.status = status;
   }

   public Byte getHandleCount() {
     return this.handleCount;
   }

   public void setHandleCount(Byte handleCount) {
     this.handleCount = handleCount;
   }

   public Date getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }

   public String getReceiptData() {
     return this.receiptData;
   }

   public void setReceiptData(String receiptData) {
     this.receiptData = receiptData;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", payOrderId=").append(this.payOrderId);
     sb.append(", mchId=").append(this.mchId);
     sb.append(", transactionId=").append(this.transactionId);
     sb.append(", status=").append(this.status);
     sb.append(", handleCount=").append(this.handleCount);
     sb.append(", createTime=").append(this.createTime);
     sb.append(", updateTime=").append(this.updateTime);
     sb.append(", receiptData=").append(this.receiptData);
     sb.append("]");
     return sb.toString();
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
     IapReceipt other = (IapReceipt)that;
     return (((getPayOrderId() == null) ? (other.getPayOrderId() == null) : getPayOrderId().equals(other.getPayOrderId())) && (
       (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
       (getTransactionId() == null) ? (other.getTransactionId() == null) : getTransactionId().equals(other.getTransactionId())) && (
       (getStatus() == null) ? (other.getStatus() == null) : getStatus().equals(other.getStatus())) && (
       (getHandleCount() == null) ? (other.getHandleCount() == null) : getHandleCount().equals(other.getHandleCount())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())) && (
       (getReceiptData() == null) ? (other.getReceiptData() == null) : getReceiptData().equals(other.getReceiptData())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getPayOrderId() == null) ? 0 : getPayOrderId().hashCode());
     result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
     result = 31 * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
     result = 31 * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
     result = 31 * result + ((getHandleCount() == null) ? 0 : getHandleCount().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     result = 31 * result + ((getReceiptData() == null) ? 0 : getReceiptData().hashCode());
     return result;
   }
 }





