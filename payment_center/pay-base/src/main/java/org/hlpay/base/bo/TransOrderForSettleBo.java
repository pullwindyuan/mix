 package org.hlpay.base.bo;
 public class TransOrderForSettleBo implements Serializable { private String transOrderId; private String mchId; private String mchTransNo; private String channelId; private Long amount; private String currency; private Byte result; private String clientIp;
   private String device;
   private String remarkInfo;
   private String channelUser;
   private String userName;
   private String channelMchId;
   private String channelOrderNo;

   public TransOrderForSettleBo setTransOrderId(String transOrderId) { this.transOrderId = transOrderId; return this; } private String channelErrCode; private String channelErrMsg; private String extra; private String notifyUrl; private String param1; private String param2; private Date expireTime; private Date createTime; private Date updateTime; private String settleCardNo; private String sign; private LocalDateTime settleStartTime; private LocalDateTime settleEndTime; private static final long serialVersionUID = 1L; public TransOrderForSettleBo setMchId(String mchId) { this.mchId = mchId; return this; } public TransOrderForSettleBo setMchTransNo(String mchTransNo) { this.mchTransNo = mchTransNo; return this; } public TransOrderForSettleBo setChannelId(String channelId) { this.channelId = channelId; return this; } public TransOrderForSettleBo setAmount(Long amount) { this.amount = amount; return this; } public TransOrderForSettleBo setCurrency(String currency) { this.currency = currency; return this; } public TransOrderForSettleBo setResult(Byte result) { this.result = result; return this; } public TransOrderForSettleBo setClientIp(String clientIp) { this.clientIp = clientIp; return this; } public TransOrderForSettleBo setDevice(String device) { this.device = device; return this; } public TransOrderForSettleBo setRemarkInfo(String remarkInfo) { this.remarkInfo = remarkInfo; return this; } public TransOrderForSettleBo setChannelUser(String channelUser) { this.channelUser = channelUser; return this; } public TransOrderForSettleBo setUserName(String userName) { this.userName = userName; return this; } public TransOrderForSettleBo setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; return this; } public TransOrderForSettleBo setChannelOrderNo(String channelOrderNo) { this.channelOrderNo = channelOrderNo; return this; } public TransOrderForSettleBo setChannelErrCode(String channelErrCode) { this.channelErrCode = channelErrCode; return this; } public TransOrderForSettleBo setChannelErrMsg(String channelErrMsg) { this.channelErrMsg = channelErrMsg; return this; } public TransOrderForSettleBo setExtra(String extra) { this.extra = extra; return this; } public TransOrderForSettleBo setNotifyUrl(String notifyUrl) { this.notifyUrl = notifyUrl; return this; } public TransOrderForSettleBo setParam1(String param1) { this.param1 = param1; return this; } public TransOrderForSettleBo setParam2(String param2) { this.param2 = param2; return this; } public TransOrderForSettleBo setExpireTime(Date expireTime) { this.expireTime = expireTime; return this; } public TransOrderForSettleBo setCreateTime(Date createTime) { this.createTime = createTime; return this; } public TransOrderForSettleBo setUpdateTime(Date updateTime) { this.updateTime = updateTime; return this; } public TransOrderForSettleBo setSettleCardNo(String settleCardNo) { this.settleCardNo = settleCardNo; return this; } public TransOrderForSettleBo setSign(String sign) { this.sign = sign; return this; } public TransOrderForSettleBo setSettleStartTime(LocalDateTime settleStartTime) { this.settleStartTime = settleStartTime; return this; } public TransOrderForSettleBo setSettleEndTime(LocalDateTime settleEndTime) { this.settleEndTime = settleEndTime; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof TransOrderForSettleBo)) return false;  TransOrderForSettleBo other = (TransOrderForSettleBo)o; if (!other.canEqual(this)) return false;  Object this$transOrderId = getTransOrderId(), other$transOrderId = other.getTransOrderId(); if ((this$transOrderId == null) ? (other$transOrderId != null) : !this$transOrderId.equals(other$transOrderId)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$mchTransNo = getMchTransNo(), other$mchTransNo = other.getMchTransNo(); if ((this$mchTransNo == null) ? (other$mchTransNo != null) : !this$mchTransNo.equals(other$mchTransNo)) return false;  Object this$channelId = getChannelId(), other$channelId = other.getChannelId(); if ((this$channelId == null) ? (other$channelId != null) : !this$channelId.equals(other$channelId)) return false;  Object this$amount = getAmount(), other$amount = other.getAmount(); if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$result = getResult(), other$result = other.getResult(); if ((this$result == null) ? (other$result != null) : !this$result.equals(other$result)) return false;  Object this$clientIp = getClientIp(), other$clientIp = other.getClientIp(); if ((this$clientIp == null) ? (other$clientIp != null) : !this$clientIp.equals(other$clientIp)) return false;  Object this$device = getDevice(), other$device = other.getDevice(); if ((this$device == null) ? (other$device != null) : !this$device.equals(other$device)) return false;  Object this$remarkInfo = getRemarkInfo(), other$remarkInfo = other.getRemarkInfo(); if ((this$remarkInfo == null) ? (other$remarkInfo != null) : !this$remarkInfo.equals(other$remarkInfo)) return false;  Object this$channelUser = getChannelUser(), other$channelUser = other.getChannelUser(); if ((this$channelUser == null) ? (other$channelUser != null) : !this$channelUser.equals(other$channelUser)) return false;  Object this$userName = getUserName(), other$userName = other.getUserName(); if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$channelOrderNo = getChannelOrderNo(), other$channelOrderNo = other.getChannelOrderNo(); if ((this$channelOrderNo == null) ? (other$channelOrderNo != null) : !this$channelOrderNo.equals(other$channelOrderNo)) return false;  Object this$channelErrCode = getChannelErrCode(), other$channelErrCode = other.getChannelErrCode(); if ((this$channelErrCode == null) ? (other$channelErrCode != null) : !this$channelErrCode.equals(other$channelErrCode)) return false;  Object this$channelErrMsg = getChannelErrMsg(), other$channelErrMsg = other.getChannelErrMsg(); if ((this$channelErrMsg == null) ? (other$channelErrMsg != null) : !this$channelErrMsg.equals(other$channelErrMsg)) return false;  Object this$extra = getExtra(), other$extra = other.getExtra(); if ((this$extra == null) ? (other$extra != null) : !this$extra.equals(other$extra)) return false;  Object this$notifyUrl = getNotifyUrl(), other$notifyUrl = other.getNotifyUrl(); if ((this$notifyUrl == null) ? (other$notifyUrl != null) : !this$notifyUrl.equals(other$notifyUrl)) return false;  Object this$param1 = getParam1(), other$param1 = other.getParam1(); if ((this$param1 == null) ? (other$param1 != null) : !this$param1.equals(other$param1)) return false;  Object this$param2 = getParam2(), other$param2 = other.getParam2(); if ((this$param2 == null) ? (other$param2 != null) : !this$param2.equals(other$param2)) return false;  Object this$expireTime = getExpireTime(), other$expireTime = other.getExpireTime(); if ((this$expireTime == null) ? (other$expireTime != null) : !this$expireTime.equals(other$expireTime)) return false;  Object this$createTime = getCreateTime(), other$createTime = other.getCreateTime(); if ((this$createTime == null) ? (other$createTime != null) : !this$createTime.equals(other$createTime)) return false;  Object this$updateTime = getUpdateTime(), other$updateTime = other.getUpdateTime(); if ((this$updateTime == null) ? (other$updateTime != null) : !this$updateTime.equals(other$updateTime)) return false;  Object this$settleCardNo = getSettleCardNo(), other$settleCardNo = other.getSettleCardNo(); if ((this$settleCardNo == null) ? (other$settleCardNo != null) : !this$settleCardNo.equals(other$settleCardNo)) return false;  Object this$sign = getSign(), other$sign = other.getSign(); if ((this$sign == null) ? (other$sign != null) : !this$sign.equals(other$sign)) return false;  Object this$settleStartTime = getSettleStartTime(), other$settleStartTime = other.getSettleStartTime(); if ((this$settleStartTime == null) ? (other$settleStartTime != null) : !this$settleStartTime.equals(other$settleStartTime)) return false;  Object this$settleEndTime = getSettleEndTime(), other$settleEndTime = other.getSettleEndTime(); return !((this$settleEndTime == null) ? (other$settleEndTime != null) : !this$settleEndTime.equals(other$settleEndTime)); } protected boolean canEqual(Object other) { return other instanceof TransOrderForSettleBo; } public int hashCode() { int PRIME = 59; result = 1; Object $transOrderId = getTransOrderId(); result = result * 59 + (($transOrderId == null) ? 43 : $transOrderId.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $mchTransNo = getMchTransNo(); result = result * 59 + (($mchTransNo == null) ? 43 : $mchTransNo.hashCode()); Object $channelId = getChannelId(); result = result * 59 + (($channelId == null) ? 43 : $channelId.hashCode()); Object $amount = getAmount(); result = result * 59 + (($amount == null) ? 43 : $amount.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $result = getResult(); result = result * 59 + (($result == null) ? 43 : $result.hashCode()); Object $clientIp = getClientIp(); result = result * 59 + (($clientIp == null) ? 43 : $clientIp.hashCode()); Object $device = getDevice(); result = result * 59 + (($device == null) ? 43 : $device.hashCode()); Object $remarkInfo = getRemarkInfo(); result = result * 59 + (($remarkInfo == null) ? 43 : $remarkInfo.hashCode()); Object $channelUser = getChannelUser(); result = result * 59 + (($channelUser == null) ? 43 : $channelUser.hashCode()); Object $userName = getUserName(); result = result * 59 + (($userName == null) ? 43 : $userName.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $channelOrderNo = getChannelOrderNo(); result = result * 59 + (($channelOrderNo == null) ? 43 : $channelOrderNo.hashCode()); Object $channelErrCode = getChannelErrCode(); result = result * 59 + (($channelErrCode == null) ? 43 : $channelErrCode.hashCode()); Object $channelErrMsg = getChannelErrMsg(); result = result * 59 + (($channelErrMsg == null) ? 43 : $channelErrMsg.hashCode()); Object $extra = getExtra(); result = result * 59 + (($extra == null) ? 43 : $extra.hashCode()); Object $notifyUrl = getNotifyUrl(); result = result * 59 + (($notifyUrl == null) ? 43 : $notifyUrl.hashCode()); Object $param1 = getParam1(); result = result * 59 + (($param1 == null) ? 43 : $param1.hashCode()); Object $param2 = getParam2(); result = result * 59 + (($param2 == null) ? 43 : $param2.hashCode()); Object $expireTime = getExpireTime(); result = result * 59 + (($expireTime == null) ? 43 : $expireTime.hashCode()); Object $createTime = getCreateTime(); result = result * 59 + (($createTime == null) ? 43 : $createTime.hashCode()); Object $updateTime = getUpdateTime(); result = result * 59 + (($updateTime == null) ? 43 : $updateTime.hashCode()); Object $settleCardNo = getSettleCardNo(); result = result * 59 + (($settleCardNo == null) ? 43 : $settleCardNo.hashCode()); Object $sign = getSign(); result = result * 59 + (($sign == null) ? 43 : $sign.hashCode()); Object $settleStartTime = getSettleStartTime(); result = result * 59 + (($settleStartTime == null) ? 43 : $settleStartTime.hashCode()); Object $settleEndTime = getSettleEndTime(); return result * 59 + (($settleEndTime == null) ? 43 : $settleEndTime.hashCode()); } public String toString() { return "TransOrderForSettleBo(transOrderId=" + getTransOrderId() + ", mchId=" + getMchId() + ", mchTransNo=" + getMchTransNo() + ", channelId=" + getChannelId() + ", amount=" + getAmount() + ", currency=" + getCurrency() + ", result=" + getResult() + ", clientIp=" + getClientIp() + ", device=" + getDevice() + ", remarkInfo=" + getRemarkInfo() + ", channelUser=" + getChannelUser() + ", userName=" + getUserName() + ", channelMchId=" + getChannelMchId() + ", channelOrderNo=" + getChannelOrderNo() + ", channelErrCode=" + getChannelErrCode() + ", channelErrMsg=" + getChannelErrMsg() + ", extra=" + getExtra() + ", notifyUrl=" + getNotifyUrl() + ", param1=" + getParam1() + ", param2=" + getParam2() + ", expireTime=" + getExpireTime() + ", createTime=" + getCreateTime() + ", updateTime=" + getUpdateTime() + ", settleCardNo=" + getSettleCardNo() + ", sign=" + getSign() + ", settleStartTime=" + getSettleStartTime() + ", settleEndTime=" + getSettleEndTime() + ")"; }






   public String getTransOrderId() {
     return this.transOrderId;
   }




   public String getMchId() {
     return this.mchId;
   }




   public String getMchTransNo() {
     return this.mchTransNo;
   }




   public String getChannelId() {
     return this.channelId;
   }




   public Long getAmount() {
     return this.amount;
   }




   public String getCurrency() {
     return this.currency;
   }




   public Byte getResult() {
     return this.result;
   }




   public String getClientIp() {
     return this.clientIp;
   }




   public String getDevice() {
     return this.device;
   }




   public String getRemarkInfo() {
     return this.remarkInfo;
   }




   public String getChannelUser() {
     return this.channelUser;
   }




   public String getUserName() {
     return this.userName;
   }




   public String getChannelMchId() {
     return this.channelMchId;
   }




   public String getChannelOrderNo() {
     return this.channelOrderNo;
   }




   public String getChannelErrCode() {
     return this.channelErrCode;
   }




   public String getChannelErrMsg() {
     return this.channelErrMsg;
   }




   public String getExtra() {
     return this.extra;
   }




   public String getNotifyUrl() {
     return this.notifyUrl;
   }




   public String getParam1() {
     return this.param1;
   }




   public String getParam2() {
     return this.param2;
   }




   public Date getExpireTime() {
     return this.expireTime;
   }




   public Date getCreateTime() {
     return this.createTime;
   }




   public Date getUpdateTime() {
     return this.updateTime;
   }




   public String getSettleCardNo() {
     return this.settleCardNo;
   }




   public String getSign() {
     return this.sign;
   } public LocalDateTime getSettleStartTime() {
     return this.settleStartTime;
   } public LocalDateTime getSettleEndTime() {
     return this.settleEndTime;
/*     */   } }
