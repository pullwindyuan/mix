 package org.hlpay.base.model;

 import java.io.Serializable;
 import java.util.Date;

 public class GoodsOrder
   implements Serializable
 {
   private String goodsOrderId;
   private String goodsId;
   private String goodsName;
   private Long amount;
   private String userId;
   private Byte status;
   private String payOrderId;
   private String channelId;
   private String channelUserId;
   private Date createTime;
   private Date updateTime;
   private static final long serialVersionUID = 1L;

   public String getGoodsOrderId() {
     return this.goodsOrderId;
   }

   public void setGoodsOrderId(String goodsOrderId) {
     this.goodsOrderId = goodsOrderId;
   }

   public String getGoodsId() {
     return this.goodsId;
   }

   public void setGoodsId(String goodsId) {
     this.goodsId = goodsId;
   }

   public String getGoodsName() {
     return this.goodsName;
   }

   public void setGoodsName(String goodsName) {
     this.goodsName = goodsName;
   }

   public Long getAmount() {
     return this.amount;
   }

   public void setAmount(Long amount) {
     this.amount = amount;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public Byte getStatus() {
     return this.status;
   }

   public void setStatus(Byte status) {
     this.status = status;
   }

   public String getPayOrderId() {
     return this.payOrderId;
   }

   public void setPayOrderId(String payOrderId) {
     this.payOrderId = payOrderId;
   }

   public String getChannelId() {
     return this.channelId;
   }

   public void setChannelId(String channelId) {
     this.channelId = channelId;
   }

   public String getChannelUserId() {
     return this.channelUserId;
   }

   public void setChannelUserId(String channelUserId) {
     this.channelUserId = channelUserId;
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
     sb.append(", goodsOrderId=").append(this.goodsOrderId);
     sb.append(", goodsId=").append(this.goodsId);
     sb.append(", goodsName=").append(this.goodsName);
     sb.append(", amount=").append(this.amount);
     sb.append(", userId=").append(this.userId);
     sb.append(", status=").append(this.status);
     sb.append(", payOrderId=").append(this.payOrderId);
     sb.append(", channelId=").append(this.channelId);
     sb.append(", channelUserId=").append(this.channelUserId);
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
     GoodsOrder other = (GoodsOrder)that;
     return (((getGoodsOrderId() == null) ? (other.getGoodsOrderId() == null) : getGoodsOrderId().equals(other.getGoodsOrderId())) && (
       (getGoodsId() == null) ? (other.getGoodsId() == null) : getGoodsId().equals(other.getGoodsId())) && (
       (getGoodsName() == null) ? (other.getGoodsName() == null) : getGoodsName().equals(other.getGoodsName())) && (
       (getAmount() == null) ? (other.getAmount() == null) : getAmount().equals(other.getAmount())) && (
       (getUserId() == null) ? (other.getUserId() == null) : getUserId().equals(other.getUserId())) && (
       (getStatus() == null) ? (other.getStatus() == null) : getStatus().equals(other.getStatus())) && (
       (getPayOrderId() == null) ? (other.getPayOrderId() == null) : getPayOrderId().equals(other.getPayOrderId())) && (
       (getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
       (getChannelUserId() == null) ? (other.getChannelUserId() == null) : getChannelUserId().equals(other.getChannelUserId())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getGoodsOrderId() == null) ? 0 : getGoodsOrderId().hashCode());
     result = 31 * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
     result = 31 * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
     result = 31 * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
     result = 31 * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
     result = 31 * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
     result = 31 * result + ((getPayOrderId() == null) ? 0 : getPayOrderId().hashCode());
     result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
     result = 31 * result + ((getChannelUserId() == null) ? 0 : getChannelUserId().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     return result;
   }
 }





