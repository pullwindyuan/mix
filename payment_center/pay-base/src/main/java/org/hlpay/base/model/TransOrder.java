 package org.hlpay.base.model;

 import java.io.Serializable;
 import java.util.Date;

 public class TransOrder implements Serializable {
   public void setSettlePayChannelId(String settlePayChannelId) {
     this.settlePayChannelId = settlePayChannelId;
   }


   private String transOrderId;

   private String mchId;

   private String mchTransNo;

   private String cardNum;

   private String channelId;
   private String settlePayChannelId;
   private Long amount;
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
   private Date transSuccTime;
   private Date createTime;
   private Date updateTime;
   private String otherCardNum;
   private String otherId;
   private String otherName;
   private String cardType;
   private static final long serialVersionUID = 1L;

   public String getSettlePayChannelId() {
     return this.settlePayChannelId;
   }

  public String getCardType() {
    return this.cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getOtherName() {
    return this.otherName;
  }

  public void setOtherName(String otherName) {
    this.otherName = otherName;
  }

  public String getOtherId() {
    return this.otherId;
  }

  public void setOtherId(String otherId) {
    this.otherId = otherId;
  }

  public String getOtherCardNum() {
    return this.otherCardNum;
  }

  public void setOtherCardNum(String otherCardNum) {
    this.otherCardNum = otherCardNum;
  }

  public String getCardNum() {
    return this.cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }



  public String getTransOrderId() {
    return this.transOrderId;
  }

  public void setTransOrderId(String transOrderId) {
    this.transOrderId = transOrderId;
  }

  public String getMchId() {
    return this.mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getMchTransNo() {
    return this.mchTransNo;
  }

  public void setMchTransNo(String mchTransNo) {
    this.mchTransNo = mchTransNo;
  }

  public String getChannelId() {
    return this.channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public Long getAmount() {
    return this.amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    if (this.currency != null) {
      return this.currency.toUpperCase();
    }
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

  public Date getTransSuccTime() {
    return this.transSuccTime;
  }

  public void setTransSuccTime(Date transSuccTime) {
    this.transSuccTime = transSuccTime;
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
    sb.append(", transOrderId=").append(this.transOrderId);
    sb.append(", mchId=").append(this.mchId);
    sb.append(", mchTransNo=").append(this.mchTransNo);
    sb.append(", channelId=").append(this.channelId);
    sb.append(", amount=").append(this.amount);
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
    sb.append(", transSuccTime=").append(this.transSuccTime);
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
    TransOrder other = (TransOrder)that;
    return (((getTransOrderId() == null) ? (other.getTransOrderId() == null) : getTransOrderId().equals(other.getTransOrderId())) && (
      (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
      (getMchTransNo() == null) ? (other.getMchTransNo() == null) : getMchTransNo().equals(other.getMchTransNo())) && (
      (getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
      (getAmount() == null) ? (other.getAmount() == null) : getAmount().equals(other.getAmount())) && (
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
      (getTransSuccTime() == null) ? (other.getTransSuccTime() == null) : getTransSuccTime().equals(other.getTransSuccTime())) && (
      (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
      (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
  }


  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + ((getTransOrderId() == null) ? 0 : getTransOrderId().hashCode());
    result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
    result = 31 * result + ((getMchTransNo() == null) ? 0 : getMchTransNo().hashCode());
    result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
    result = 31 * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
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
    result = 31 * result + ((getTransSuccTime() == null) ? 0 : getTransSuccTime().hashCode());
    result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
    result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
    return result;
  }
}
