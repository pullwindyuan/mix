package org.hlpay.base.model;

import com.alibaba.fastjson.JSONObject;

public class PayOrderForCreate extends PayOrder {
  MchInfo mchInfo;
  JSONObject param2JSONObject;
  PayChannelForRuntime payChannel;

  public void setMchInfo(MchInfo mchInfo) {
    this.mchInfo = mchInfo; } public void setParam2JSONObject(JSONObject param2JSONObject) { this.param2JSONObject = param2JSONObject; } public void setPayChannel(PayChannelForRuntime payChannel) { this.payChannel = payChannel; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayOrderForCreate)) return false;  PayOrderForCreate other = (PayOrderForCreate)o; if (!other.canEqual(this)) return false;  Object this$mchInfo = getMchInfo(), other$mchInfo = other.getMchInfo(); if ((this$mchInfo == null) ? (other$mchInfo != null) : !this$mchInfo.equals(other$mchInfo)) return false;  Object this$param2JSONObject = getParam2JSONObject(), other$param2JSONObject = other.getParam2JSONObject(); if ((this$param2JSONObject == null) ? (other$param2JSONObject != null) : !this$param2JSONObject.equals(other$param2JSONObject)) return false;  Object this$payChannel = getPayChannel(), other$payChannel = other.getPayChannel(); return !((this$payChannel == null) ? (other$payChannel != null) : !this$payChannel.equals(other$payChannel)); } protected boolean canEqual(Object other) { return other instanceof PayOrderForCreate; } public int hashCode() { int PRIME = 59; result = 1; Object $mchInfo = getMchInfo(); result = result * 59 + (($mchInfo == null) ? 43 : $mchInfo.hashCode()); Object $param2JSONObject = getParam2JSONObject(); result = result * 59 + (($param2JSONObject == null) ? 43 : $param2JSONObject.hashCode()); Object $payChannel = getPayChannel(); return result * 59 + (($payChannel == null) ? 43 : $payChannel.hashCode()); } public String toString() { return "PayOrderForCreate(mchInfo=" + getMchInfo() + ", param2JSONObject=" + getParam2JSONObject() + ", payChannel=" + getPayChannel() + ")"; }

  public MchInfo getMchInfo() { return this.mchInfo; }
  public JSONObject getParam2JSONObject() { return this.param2JSONObject; } public PayChannelForRuntime getPayChannel() {
    return this.payChannel;
  }
}





