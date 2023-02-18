 package org.hlpay.base.model;

 import java.io.Serializable;
 import java.util.Date;

 public class MchNotify
   implements Serializable
 {
   private String orderId;
   private String mchId;
   private String mchOrderNo;
   private String orderType;
   private String notifyUrl;
   private Byte notifyCount;
   private String result;
   private Byte status;
   private Date lastNotifyTime;
   private Date createTime;
   private Date updateTime;
   private static final long serialVersionUID = 1L;

   public String getOrderId() {
     return this.orderId;
   }

   public void setOrderId(String orderId) {
     this.orderId = orderId;
   }

   public String getMchId() {
     return this.mchId;
   }

   public void setMchId(String mchId) {
     this.mchId = mchId;
   }

   public String getMchOrderNo() {
     return this.mchOrderNo;
   }

   public void setMchOrderNo(String mchOrderNo) {
     this.mchOrderNo = mchOrderNo;
   }

   public String getOrderType() {
     return this.orderType;
   }

   public void setOrderType(String orderType) {
     this.orderType = orderType;
   }

   public String getNotifyUrl() {
     return this.notifyUrl;
   }

   public void setNotifyUrl(String notifyUrl) {
     this.notifyUrl = notifyUrl;
   }

   public Byte getNotifyCount() {
     return this.notifyCount;
   }

   public void setNotifyCount(Byte notifyCount) {
     this.notifyCount = notifyCount;
   }

   public String getResult() {
     return this.result;
   }

   public void setResult(String result) {
     this.result = result;
   }

   public Byte getStatus() {
     return this.status;
   }

   public void setStatus(Byte status) {
     this.status = status;
   }

   public Date getLastNotifyTime() {
     return this.lastNotifyTime;
   }

   public void setLastNotifyTime(Date lastNotifyTime) {
     this.lastNotifyTime = lastNotifyTime;
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


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", orderId=").append(this.orderId);
     sb.append(", mchId=").append(this.mchId);
     sb.append(", mchOrderNo=").append(this.mchOrderNo);
     sb.append(", orderType=").append(this.orderType);
     sb.append(", notifyUrl=").append(this.notifyUrl);
     sb.append(", notifyCount=").append(this.notifyCount);
     sb.append(", result=").append(this.result);
     sb.append(", status=").append(this.status);
     sb.append(", lastNotifyTime=").append(this.lastNotifyTime);
     sb.append(", createTime=").append(this.createTime);
     sb.append(", updateTime=").append(this.updateTime);
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
     MchNotify other = (MchNotify)that;
     return (((getOrderId() == null) ? (other.getOrderId() == null) : getOrderId().equals(other.getOrderId())) && (
       (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
       (getMchOrderNo() == null) ? (other.getMchOrderNo() == null) : getMchOrderNo().equals(other.getMchOrderNo())) && (
       (getOrderType() == null) ? (other.getOrderType() == null) : getOrderType().equals(other.getOrderType())) && (
       (getNotifyUrl() == null) ? (other.getNotifyUrl() == null) : getNotifyUrl().equals(other.getNotifyUrl())) && (
       (getNotifyCount() == null) ? (other.getNotifyCount() == null) : getNotifyCount().equals(other.getNotifyCount())) && (
       (getResult() == null) ? (other.getResult() == null) : getResult().equals(other.getResult())) && (
       (getStatus() == null) ? (other.getStatus() == null) : getStatus().equals(other.getStatus())) && (
       (getLastNotifyTime() == null) ? (other.getLastNotifyTime() == null) : getLastNotifyTime().equals(other.getLastNotifyTime())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
     result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
     result = 31 * result + ((getMchOrderNo() == null) ? 0 : getMchOrderNo().hashCode());
     result = 31 * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
     result = 31 * result + ((getNotifyUrl() == null) ? 0 : getNotifyUrl().hashCode());
     result = 31 * result + ((getNotifyCount() == null) ? 0 : getNotifyCount().hashCode());
     result = 31 * result + ((getResult() == null) ? 0 : getResult().hashCode());
     result = 31 * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
     result = 31 * result + ((getLastNotifyTime() == null) ? 0 : getLastNotifyTime().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     return result;
   }
 }
