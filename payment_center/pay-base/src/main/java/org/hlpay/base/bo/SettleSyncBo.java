 package org.hlpay.base.bo;
 public class SettleSyncBo implements Serializable { @ApiModelProperty(value = "当前记账商户ID", required = true)
   String mchId; @ApiModelProperty(value = "当前记账对应的商户支付订单号", required = true)
   String mchOrderNo; @ApiModelProperty(value = "当前记支付中心订单ID", required = true)
   String payOrderId; @ApiModelProperty(value = "渠道订单号", required = true)
   String channelOrderNo; @ApiModelProperty(value = "金额", required = true)
   private Long amount; @ApiModelProperty(value = "支付成功时间", required = true)
   private Long paySuccTime; @ApiModelProperty(value = "当前记账商户名称", required = true)
   String mchName;
   public SettleSyncBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "当前记账支付订单的支付渠道ID", required = true) String channelId; @ApiModelProperty(value = "当前记账支付订单的支付渠道代码", required = true) Integer channelCode; @ApiModelProperty(value = "当前记账支付订单的标题ID", required = true) String subject; @ApiModelProperty(value = "扩展参数1", required = true) String param1; @ApiModelProperty(value = "扩展参数2", required = true) String param2; @ApiModelProperty(value = "当前记账支付订单的描述内容ID", required = true) String body; @ApiModelProperty(value = "签名", required = true) String sign; public SettleSyncBo setMchOrderNo(String mchOrderNo) { this.mchOrderNo = mchOrderNo; return this; } public SettleSyncBo setPayOrderId(String payOrderId) { this.payOrderId = payOrderId; return this; } public SettleSyncBo setChannelOrderNo(String channelOrderNo) { this.channelOrderNo = channelOrderNo; return this; } public SettleSyncBo setAmount(Long amount) { this.amount = amount; return this; } public SettleSyncBo setPaySuccTime(Long paySuccTime) { this.paySuccTime = paySuccTime; return this; } public SettleSyncBo setMchName(String mchName) { this.mchName = mchName; return this; } public SettleSyncBo setChannelId(String channelId) { this.channelId = channelId; return this; } public SettleSyncBo setChannelCode(Integer channelCode) { this.channelCode = channelCode; return this; } public SettleSyncBo setSubject(String subject) { this.subject = subject; return this; } public SettleSyncBo setParam1(String param1) { this.param1 = param1; return this; } public SettleSyncBo setParam2(String param2) { this.param2 = param2; return this; } public SettleSyncBo setBody(String body) { this.body = body; return this; } public SettleSyncBo setSign(String sign) { this.sign = sign; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SettleSyncBo)) return false;  SettleSyncBo other = (SettleSyncBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$mchOrderNo = getMchOrderNo(), other$mchOrderNo = other.getMchOrderNo(); if ((this$mchOrderNo == null) ? (other$mchOrderNo != null) : !this$mchOrderNo.equals(other$mchOrderNo)) return false;  Object this$payOrderId = getPayOrderId(), other$payOrderId = other.getPayOrderId(); if ((this$payOrderId == null) ? (other$payOrderId != null) : !this$payOrderId.equals(other$payOrderId)) return false;  Object this$channelOrderNo = getChannelOrderNo(), other$channelOrderNo = other.getChannelOrderNo(); if ((this$channelOrderNo == null) ? (other$channelOrderNo != null) : !this$channelOrderNo.equals(other$channelOrderNo)) return false;  Object this$amount = getAmount(), other$amount = other.getAmount(); if ((this$amount == null) ? (other$amount != null) : !this$amount.equals(other$amount)) return false;  Object this$paySuccTime = getPaySuccTime(), other$paySuccTime = other.getPaySuccTime(); if ((this$paySuccTime == null) ? (other$paySuccTime != null) : !this$paySuccTime.equals(other$paySuccTime)) return false;  Object this$mchName = getMchName(), other$mchName = other.getMchName(); if ((this$mchName == null) ? (other$mchName != null) : !this$mchName.equals(other$mchName)) return false;  Object this$channelId = getChannelId(), other$channelId = other.getChannelId(); if ((this$channelId == null) ? (other$channelId != null) : !this$channelId.equals(other$channelId)) return false;  Object this$channelCode = getChannelCode(), other$channelCode = other.getChannelCode(); if ((this$channelCode == null) ? (other$channelCode != null) : !this$channelCode.equals(other$channelCode)) return false;  Object this$subject = getSubject(), other$subject = other.getSubject(); if ((this$subject == null) ? (other$subject != null) : !this$subject.equals(other$subject)) return false;  Object this$param1 = getParam1(), other$param1 = other.getParam1(); if ((this$param1 == null) ? (other$param1 != null) : !this$param1.equals(other$param1)) return false;  Object this$param2 = getParam2(), other$param2 = other.getParam2(); if ((this$param2 == null) ? (other$param2 != null) : !this$param2.equals(other$param2)) return false;  Object this$body = getBody(), other$body = other.getBody(); if ((this$body == null) ? (other$body != null) : !this$body.equals(other$body)) return false;  Object this$sign = getSign(), other$sign = other.getSign(); return !((this$sign == null) ? (other$sign != null) : !this$sign.equals(other$sign)); } protected boolean canEqual(Object other) { return other instanceof SettleSyncBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $mchOrderNo = getMchOrderNo(); result = result * 59 + (($mchOrderNo == null) ? 43 : $mchOrderNo.hashCode()); Object $payOrderId = getPayOrderId(); result = result * 59 + (($payOrderId == null) ? 43 : $payOrderId.hashCode()); Object $channelOrderNo = getChannelOrderNo(); result = result * 59 + (($channelOrderNo == null) ? 43 : $channelOrderNo.hashCode()); Object $amount = getAmount(); result = result * 59 + (($amount == null) ? 43 : $amount.hashCode()); Object $paySuccTime = getPaySuccTime(); result = result * 59 + (($paySuccTime == null) ? 43 : $paySuccTime.hashCode()); Object $mchName = getMchName(); result = result * 59 + (($mchName == null) ? 43 : $mchName.hashCode()); Object $channelId = getChannelId(); result = result * 59 + (($channelId == null) ? 43 : $channelId.hashCode()); Object $channelCode = getChannelCode(); result = result * 59 + (($channelCode == null) ? 43 : $channelCode.hashCode()); Object $subject = getSubject(); result = result * 59 + (($subject == null) ? 43 : $subject.hashCode()); Object $param1 = getParam1(); result = result * 59 + (($param1 == null) ? 43 : $param1.hashCode()); Object $param2 = getParam2(); result = result * 59 + (($param2 == null) ? 43 : $param2.hashCode()); Object $body = getBody(); result = result * 59 + (($body == null) ? 43 : $body.hashCode()); Object $sign = getSign(); return result * 59 + (($sign == null) ? 43 : $sign.hashCode()); } public String toString() { return "SettleSyncBo(mchId=" + getMchId() + ", mchOrderNo=" + getMchOrderNo() + ", payOrderId=" + getPayOrderId() + ", channelOrderNo=" + getChannelOrderNo() + ", amount=" + getAmount() + ", paySuccTime=" + getPaySuccTime() + ", mchName=" + getMchName() + ", channelId=" + getChannelId() + ", channelCode=" + getChannelCode() + ", subject=" + getSubject() + ", param1=" + getParam1() + ", param2=" + getParam2() + ", body=" + getBody() + ", sign=" + getSign() + ")"; }


   public String getMchId() {
     return this.mchId;
   }
   public String getMchOrderNo() {
     return this.mchOrderNo;
   }
   public String getPayOrderId() {
     return this.payOrderId;
   }
   public String getChannelOrderNo() {
     return this.channelOrderNo;
   }
   public Long getAmount() {
     return this.amount;
   }
   public Long getPaySuccTime() {
     return this.paySuccTime;
   }
   public String getMchName() {
     return this.mchName;
   }
   public String getChannelId() {
     return this.channelId;
   }
   public Integer getChannelCode() {
     return this.channelCode;
   }
   public String getSubject() {
     return this.subject;
   }
   public String getParam1() {
     return this.param1;
   }
   public String getParam2() {
     return this.param2;
   }
   public String getBody() {
/* 50 */     return this.body;
/*    */   } public String getSign() {
/* 52 */     return this.sign;
/*    */   } }

