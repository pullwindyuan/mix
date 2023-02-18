 package org.hlpay.base.model;

 import java.io.Serializable;
 import java.util.Date;

 public class RefundOrder
   implements Serializable
 {
   private String refundOrderId;
   private String payOrderId;
   private String mchOrderNo;
   private String channelAccount;
   private String channelPayOrderNo;
   private String mchId;
   private String mchRefundNo;
   private String channelId;
   private Long payAmount;
   private Long refundAmount;
   private String currency;
   private Byte status;
   private Byte result;
   private String clientIp;
   private String device;
   private String remarkInfo;
   private String channelUser;
   private String userName;
   private String channelMchId;
   private String channelOrderNo;
   private String channelErrCode;
   private String channelErrMsg;
   private String extra;
   private String notifyUrl;
   private String param1;
   private String param2;
   private Date expireTime;
   private Date refundSuccTime;
   private Date createTime;
   private Date updateTime;
   private static final long serialVersionUID = 1L;

   public String getRefundOrderId() {
     return this.refundOrderId;
   }

   public void setRefundOrderId(String refundOrderId) {
     this.refundOrderId = refundOrderId;
   }

   public String getPayOrderId() {
     return this.payOrderId;
   }

   public void setPayOrderId(String payOrderId) {
     this.payOrderId = payOrderId;
   }

   public String getMchOrderNo() {
     return this.mchOrderNo;
   }

   public void setMchOrderNo(String mchOrderNo) {
     this.mchOrderNo = mchOrderNo;
   }

   public String getChannelPayOrderNo() {
     return this.channelPayOrderNo;
   }

   public void setChannelPayOrderNo(String channelPayOrderNo) {
     this.channelPayOrderNo = channelPayOrderNo;
   }

   public String getMchId() {
     return this.mchId;
   }

   public void setMchId(String mchId) {
     this.mchId = mchId;
   }

   public String getMchRefundNo() {
     return this.mchRefundNo;
   }

   public void setMchRefundNo(String mchRefundNo) {
     this.mchRefundNo = mchRefundNo;
   }

   public String getChannelId() {
     return this.channelId;
   }

   public void setChannelId(String channelId) {
     this.channelId = channelId;
   }

   public Long getPayAmount() {
     return this.payAmount;
   }

   public void setPayAmount(Long payAmount) {
     this.payAmount = payAmount;
   }

   public Long getRefundAmount() {
     return this.refundAmount;
   }

   public void setRefundAmount(Long refundAmount) {
     this.refundAmount = refundAmount;
   }

   public String getCurrency() {
     return this.currency;
   }

   public void setCurrency(String currency) {
     this.currency = currency;
   }

   public Byte getStatus() {
     return this.status;
   }

   public void setStatus(Byte status) {
     this.status = status;
   }

   public Byte getResult() {
     return this.result;
   }

   public void setResult(Byte result) {
     this.result = result;
   }

   public String getClientIp() {
     return this.clientIp;
   }

   public void setClientIp(String clientIp) {
     this.clientIp = clientIp;
   }

   public String getDevice() {
     return this.device;
   }

   public void setDevice(String device) {
     this.device = device;
   }

   public String getRemarkInfo() {
     return this.remarkInfo;
   }

   public void setRemarkInfo(String remarkInfo) {
     this.remarkInfo = remarkInfo;
   }

   public String getChannelUser() {
     return this.channelUser;
   }

   public void setChannelUser(String channelUser) {
     this.channelUser = channelUser;
   }

   public String getUserName() {
     return this.userName;
   }

   public void setUserName(String userName) {
     this.userName = userName;
   }

   public String getChannelMchId() {
     return this.channelMchId;
   }

   public void setChannelMchId(String channelMchId) {
     this.channelMchId = channelMchId;
   }

   public String getChannelOrderNo() {
     return this.channelOrderNo;
   }

   public void setChannelOrderNo(String channelOrderNo) {
     this.channelOrderNo = channelOrderNo;
   }

   public String getChannelErrCode() {
     return this.channelErrCode;
   }

   public void setChannelErrCode(String channelErrCode) {
     this.channelErrCode = channelErrCode;
   }

   public String getChannelErrMsg() {
     return this.channelErrMsg;
   }

   public void setChannelErrMsg(String channelErrMsg) {
     this.channelErrMsg = channelErrMsg;
   }

   public String getExtra() {
     return this.extra;
   }

   public void setExtra(String extra) {
     this.extra = extra;
   }

   public String getNotifyUrl() {
     return this.notifyUrl;
   }

   public void setNotifyUrl(String notifyUrl) {
     this.notifyUrl = notifyUrl;
   }

   public String getParam1() {
     return this.param1;
   }

   public void setParam1(String param1) {
     this.param1 = param1;
   }

   public String getParam2() {
     return this.param2;
   }

   public void setParam2(String param2) {
     this.param2 = param2;
   }

   public Date getExpireTime() {
     return this.expireTime;
   }

   public void setExpireTime(Date expireTime) {
     this.expireTime = expireTime;
   }

   public Date getRefundSuccTime() {
     return this.refundSuccTime;
   }

   public void setRefundSuccTime(Date refundSuccTime) {
     this.refundSuccTime = refundSuccTime;
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

   public String getChannelAccount() {
     return this.channelAccount;
   }

   public void setChannelAccount(String channelAccount) {
     this.channelAccount = channelAccount;
   }

   public static long getSerialVersionUID() {
     return 1L;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", refundOrderId=").append(this.refundOrderId);
     sb.append(", payOrderId=").append(this.payOrderId);
     sb.append(", mchOrderNo=").append(this.mchOrderNo);
     sb.append(", channelAccount=").append(this.channelAccount);
     sb.append(", channelPayOrderNo=").append(this.channelPayOrderNo);
     sb.append(", mchId=").append(this.mchId);
     sb.append(", mchRefundNo=").append(this.mchRefundNo);
     sb.append(", channelId=").append(this.channelId);
     sb.append(", payAmount=").append(this.payAmount);
     sb.append(", refundAmount=").append(this.refundAmount);
     sb.append(", currency=").append(this.currency);
     sb.append(", status=").append(this.status);
     sb.append(", result=").append(this.result);
     sb.append(", clientIp=").append(this.clientIp);
     sb.append(", device=").append(this.device);
     sb.append(", remarkInfo=").append(this.remarkInfo);
     sb.append(", channelUser=").append(this.channelUser);
     sb.append(", userName=").append(this.userName);
     sb.append(", channelMchId=").append(this.channelMchId);
     sb.append(", channelOrderNo=").append(this.channelOrderNo);
     sb.append(", channelErrCode=").append(this.channelErrCode);
     sb.append(", channelErrMsg=").append(this.channelErrMsg);
     sb.append(", extra=").append(this.extra);
     sb.append(", notifyUrl=").append(this.notifyUrl);
     sb.append(", param1=").append(this.param1);
     sb.append(", param2=").append(this.param2);
     sb.append(", expireTime=").append(this.expireTime);
     sb.append(", refundSuccTime=").append(this.refundSuccTime);
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
     RefundOrder other = (RefundOrder)that;
     return (((getRefundOrderId() == null) ? (other.getRefundOrderId() == null) : getRefundOrderId().equals(other.getRefundOrderId())) && (
       (getPayOrderId() == null) ? (other.getPayOrderId() == null) : getPayOrderId().equals(other.getPayOrderId())) && (
       (getChannelPayOrderNo() == null) ? (other.getChannelPayOrderNo() == null) : getChannelPayOrderNo().equals(other.getChannelPayOrderNo())) && (
       (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
       (getMchOrderNo() == null) ? (other.getMchOrderNo() == null) : getMchOrderNo().equals(other.getMchOrderNo())) && (
       (getChannelAccount() == null) ? (other.getChannelAccount() == null) : getChannelAccount().equals(other.getChannelAccount())) && (
       (getMchRefundNo() == null) ? (other.getMchRefundNo() == null) : getMchRefundNo().equals(other.getMchRefundNo())) && (
       (getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
       (getPayAmount() == null) ? (other.getPayAmount() == null) : getPayAmount().equals(other.getPayAmount())) && (
       (getRefundAmount() == null) ? (other.getRefundAmount() == null) : getRefundAmount().equals(other.getRefundAmount())) && (
       (getCurrency() == null) ? (other.getCurrency() == null) : getCurrency().equals(other.getCurrency())) && (
       (getStatus() == null) ? (other.getStatus() == null) : getStatus().equals(other.getStatus())) && (
       (getResult() == null) ? (other.getResult() == null) : getResult().equals(other.getResult())) && (
       (getClientIp() == null) ? (other.getClientIp() == null) : getClientIp().equals(other.getClientIp())) && (
       (getDevice() == null) ? (other.getDevice() == null) : getDevice().equals(other.getDevice())) && (
       (getRemarkInfo() == null) ? (other.getRemarkInfo() == null) : getRemarkInfo().equals(other.getRemarkInfo())) && (
       (getChannelUser() == null) ? (other.getChannelUser() == null) : getChannelUser().equals(other.getChannelUser())) && (
       (getUserName() == null) ? (other.getUserName() == null) : getUserName().equals(other.getUserName())) && (
       (getChannelMchId() == null) ? (other.getChannelMchId() == null) : getChannelMchId().equals(other.getChannelMchId())) && (
       (getChannelOrderNo() == null) ? (other.getChannelOrderNo() == null) : getChannelOrderNo().equals(other.getChannelOrderNo())) && (
       (getChannelErrCode() == null) ? (other.getChannelErrCode() == null) : getChannelErrCode().equals(other.getChannelErrCode())) && (
       (getChannelErrMsg() == null) ? (other.getChannelErrMsg() == null) : getChannelErrMsg().equals(other.getChannelErrMsg())) && (
       (getExtra() == null) ? (other.getExtra() == null) : getExtra().equals(other.getExtra())) && (
       (getNotifyUrl() == null) ? (other.getNotifyUrl() == null) : getNotifyUrl().equals(other.getNotifyUrl())) && (
       (getParam1() == null) ? (other.getParam1() == null) : getParam1().equals(other.getParam1())) && (
       (getParam2() == null) ? (other.getParam2() == null) : getParam2().equals(other.getParam2())) && (
       (getExpireTime() == null) ? (other.getExpireTime() == null) : getExpireTime().equals(other.getExpireTime())) && (
       (getRefundSuccTime() == null) ? (other.getRefundSuccTime() == null) : getRefundSuccTime().equals(other.getRefundSuccTime())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getRefundOrderId() == null) ? 0 : getRefundOrderId().hashCode());
     result = 31 * result + ((getPayOrderId() == null) ? 0 : getPayOrderId().hashCode());
     result = 31 * result + ((getChannelPayOrderNo() == null) ? 0 : getChannelPayOrderNo().hashCode());
     result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
     result = 31 * result + ((getMchOrderNo() == null) ? 0 : getMchOrderNo().hashCode());
     result = 31 * result + ((getChannelAccount() == null) ? 0 : getChannelAccount().hashCode());
     result = 31 * result + ((getMchRefundNo() == null) ? 0 : getMchRefundNo().hashCode());
     result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
     result = 31 * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
     result = 31 * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
     result = 31 * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
     result = 31 * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
     result = 31 * result + ((getResult() == null) ? 0 : getResult().hashCode());
     result = 31 * result + ((getClientIp() == null) ? 0 : getClientIp().hashCode());
     result = 31 * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
     result = 31 * result + ((getRemarkInfo() == null) ? 0 : getRemarkInfo().hashCode());
     result = 31 * result + ((getChannelUser() == null) ? 0 : getChannelUser().hashCode());
     result = 31 * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
     result = 31 * result + ((getChannelMchId() == null) ? 0 : getChannelMchId().hashCode());
     result = 31 * result + ((getChannelOrderNo() == null) ? 0 : getChannelOrderNo().hashCode());
     result = 31 * result + ((getChannelErrCode() == null) ? 0 : getChannelErrCode().hashCode());
     result = 31 * result + ((getChannelErrMsg() == null) ? 0 : getChannelErrMsg().hashCode());
     result = 31 * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
     result = 31 * result + ((getNotifyUrl() == null) ? 0 : getNotifyUrl().hashCode());
     result = 31 * result + ((getParam1() == null) ? 0 : getParam1().hashCode());
     result = 31 * result + ((getParam2() == null) ? 0 : getParam2().hashCode());
     result = 31 * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
     result = 31 * result + ((getRefundSuccTime() == null) ? 0 : getRefundSuccTime().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     return result;
   }
 }
