 package org.hlpay.base.model;
 public class PayOrder implements Serializable { private String payOrderId; private String mchId; private String channelAccount; private String mchOrderNo; private String channelId; private Long amount; private String currency; private Byte status; private Integer channelCode; private String clientIp;
   private String device;
   private String subject;
   private String body;
   private String extra;
   private String channelMchId;
   private String channelOrderNo;

   public void setChannelCode(Integer channelCode) { this.channelCode = channelCode; } private String errCode; private String errMsg; private String param1; private String param2; private String notifyUrl; private Byte notifyCount; private Long lastNotifyTime; private Long expireTime; private Long paySuccTime; private Date createTime; private Date updateTime; private String redirectUrl; private String openId; private String mchName; private String sign; private static final long serialVersionUID = 1L; public void setMchName(String mchName) { this.mchName = mchName; } public void setSign(String sign) { this.sign = sign; }

 public Integer getChannelCode() {
/*  73 */     return this.channelCode;
/*     */   }
public String getMchName() {
  return this.mchName;
} public String getSign() {
  return this.sign;
}
public String getOpenId() {
  return this.openId;
}

public void setOpenId(String openId) {
  this.openId = openId;
}



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

public String getMchOrderNo() {
  return this.mchOrderNo;
}

public void setMchOrderNo(String mchOrderNo) {
  this.mchOrderNo = mchOrderNo;
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

public String getSubject() {
  return this.subject;
}

public void setSubject(String subject) {
  this.subject = subject;
}

public String getBody() {
  return this.body;
}

public void setBody(String body) {
  this.body = body;
}

public String getExtra() {
  return this.extra;
}

public void setExtra(String extra) {
  this.extra = extra;
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

public String getErrCode() {
  return this.errCode;
}

public void setErrCode(String errCode) {
  this.errCode = errCode;
}

public String getErrMsg() {
  return this.errMsg;
}

public void setErrMsg(String errMsg) {
  this.errMsg = errMsg;
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

public Long getLastNotifyTime() {
  return this.lastNotifyTime;
}

public void setLastNotifyTime(Long lastNotifyTime) {
  this.lastNotifyTime = lastNotifyTime;
}

public Long getExpireTime() {
  return this.expireTime;
}

public void setExpireTime(Long expireTime) {
  this.expireTime = expireTime;
}

public Long getPaySuccTime() {
  return this.paySuccTime;
}

public void setPaySuccTime(Long paySuccTime) {
  this.paySuccTime = paySuccTime;
}

public Date getCreateTime() {
  return this.createTime;
}

public Date getUpdateTime() {
  return this.updateTime;
}

public void setCreateTime(String createTime) {
  if (StringUtils.isNumeric(createTime)) {
    this.createTime = DateUtils.getDate(Long.decode(createTime));
  } else {
    this.createTime = DateUtils.formatDateTime(createTime);
  }
}

public void setUpdateTime(String updateTime) {
  if (StringUtils.isNumeric(updateTime)) {
    this.updateTime = DateUtils.getDate(Long.decode(updateTime));
  } else {
    this.updateTime = DateUtils.formatDateTime(updateTime);
  }
}

public String getRedirectUrl() {
  return this.redirectUrl;
}

public void setRedirectUrl(String redirectUrl) {
  this.redirectUrl = redirectUrl;
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
  sb.append(", payOrderId=").append(this.payOrderId);
  sb.append(", mchId=").append(this.mchId);
  sb.append(", mchOrderNo=").append(this.mchOrderNo);
  sb.append(", channelId=").append(this.channelId);
  sb.append(", channelAccount=").append(this.channelAccount);
  sb.append(", amount=").append(this.amount);
  sb.append(", currency=").append(this.currency);
  sb.append(", status=").append(this.status);
  sb.append(", clientIp=").append(this.clientIp);
  sb.append(", device=").append(this.device);
  sb.append(", subject=").append(this.subject);
  sb.append(", body=").append(this.body);
  sb.append(", extra=").append(this.extra);
  sb.append(", channelMchId=").append(this.channelMchId);
  sb.append(", channelOrderNo=").append(this.channelOrderNo);
  sb.append(", errCode=").append(this.errCode);
  sb.append(", errMsg=").append(this.errMsg);
  sb.append(", param1=").append(this.param1);
  sb.append(", param2=").append(this.param2);
  sb.append(", notifyUrl=").append(this.notifyUrl);
  sb.append(", notifyCount=").append(this.notifyCount);
  sb.append(", lastNotifyTime=").append(this.lastNotifyTime);
  sb.append(", expireTime=").append(this.expireTime);
  sb.append(", paySuccTime=").append(this.paySuccTime);
  sb.append(", createTime=").append(this.createTime);
  sb.append(", updateTime=").append(this.updateTime);
  sb.append(", redirectUrl=").append(this.redirectUrl);
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
  PayOrder other = (PayOrder)that;
  return (((getPayOrderId() == null) ? (other.getPayOrderId() == null) : getPayOrderId().equals(other.getPayOrderId())) && (
    (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
    (getMchOrderNo() == null) ? (other.getMchOrderNo() == null) : getMchOrderNo().equals(other.getMchOrderNo())) && (
    (getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
    (getChannelAccount() == null) ? (other.getChannelAccount() == null) : getChannelAccount().equals(other.getChannelAccount())) && (
    (getAmount() == null) ? (other.getAmount() == null) : getAmount().equals(other.getAmount())) && (
    (getCurrency() == null) ? (other.getCurrency() == null) : getCurrency().equals(other.getCurrency())) && (
    (getStatus() == null) ? (other.getStatus() == null) : getStatus().equals(other.getStatus())) && (
    (getClientIp() == null) ? (other.getClientIp() == null) : getClientIp().equals(other.getClientIp())) && (
    (getDevice() == null) ? (other.getDevice() == null) : getDevice().equals(other.getDevice())) && (
    (getSubject() == null) ? (other.getSubject() == null) : getSubject().equals(other.getSubject())) && (
    (getBody() == null) ? (other.getBody() == null) : getBody().equals(other.getBody())) && (
    (getExtra() == null) ? (other.getExtra() == null) : getExtra().equals(other.getExtra())) && (
    (getChannelMchId() == null) ? (other.getChannelMchId() == null) : getChannelMchId().equals(other.getChannelMchId())) && (
    (getChannelOrderNo() == null) ? (other.getChannelOrderNo() == null) : getChannelOrderNo().equals(other.getChannelOrderNo())) && (
    (getErrCode() == null) ? (other.getErrCode() == null) : getErrCode().equals(other.getErrCode())) && (
    (getErrMsg() == null) ? (other.getErrMsg() == null) : getErrMsg().equals(other.getErrMsg())) && (
    (getParam1() == null) ? (other.getParam1() == null) : getParam1().equals(other.getParam1())) && (
    (getParam2() == null) ? (other.getParam2() == null) : getParam2().equals(other.getParam2())) && (
    (getNotifyUrl() == null) ? (other.getNotifyUrl() == null) : getNotifyUrl().equals(other.getNotifyUrl())) && (
    (getNotifyCount() == null) ? (other.getNotifyCount() == null) : getNotifyCount().equals(other.getNotifyCount())) && (
    (getLastNotifyTime() == null) ? (other.getLastNotifyTime() == null) : getLastNotifyTime().equals(other.getLastNotifyTime())) && (
    (getExpireTime() == null) ? (other.getExpireTime() == null) : getExpireTime().equals(other.getExpireTime())) && (
    (getPaySuccTime() == null) ? (other.getPaySuccTime() == null) : getPaySuccTime().equals(other.getPaySuccTime())) && (
    (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
    (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())) && (
    (getRedirectUrl() == null) ? (other.getRedirectUrl() == null) : getRedirectUrl().equals(other.getRedirectUrl())));
}


public int hashCode() {
  int prime = 31;
  int result = 1;
  result = 31 * result + ((getPayOrderId() == null) ? 0 : getPayOrderId().hashCode());
  result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
  result = 31 * result + ((getMchOrderNo() == null) ? 0 : getMchOrderNo().hashCode());
  result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
  result = 31 * result + ((getChannelAccount() == null) ? 0 : getChannelAccount().hashCode());
  result = 31 * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
  result = 31 * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
  result = 31 * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
  result = 31 * result + ((getClientIp() == null) ? 0 : getClientIp().hashCode());
  result = 31 * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
  result = 31 * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
  result = 31 * result + ((getBody() == null) ? 0 : getBody().hashCode());
  result = 31 * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
  result = 31 * result + ((getChannelMchId() == null) ? 0 : getChannelMchId().hashCode());
  result = 31 * result + ((getChannelOrderNo() == null) ? 0 : getChannelOrderNo().hashCode());
  result = 31 * result + ((getErrCode() == null) ? 0 : getErrCode().hashCode());
  result = 31 * result + ((getErrMsg() == null) ? 0 : getErrMsg().hashCode());
  result = 31 * result + ((getParam1() == null) ? 0 : getParam1().hashCode());
  result = 31 * result + ((getParam2() == null) ? 0 : getParam2().hashCode());
  result = 31 * result + ((getNotifyUrl() == null) ? 0 : getNotifyUrl().hashCode());
  result = 31 * result + ((getNotifyCount() == null) ? 0 : getNotifyCount().hashCode());
  result = 31 * result + ((getLastNotifyTime() == null) ? 0 : getLastNotifyTime().hashCode());
  result = 31 * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
  result = 31 * result + ((getPaySuccTime() == null) ? 0 : getPaySuccTime().hashCode());
  result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
  result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
  result = 31 * result + ((getRedirectUrl() == null) ? 0 : getRedirectUrl().hashCode());
  return result;
} }

