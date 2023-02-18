 package org.hlpay.base.model;

 public class PayChannelForRuntime
   extends PayChannel
 {
   MchInfo mchInfo;

   public String toString() {
     return "PayChannelForRuntime(mchInfo=" + getMchInfo() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $mchInfo = getMchInfo(); return result * 59 + (($mchInfo == null) ? 43 : $mchInfo.hashCode()); } protected boolean canEqual(Object other) { return other instanceof PayChannelForRuntime; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelForRuntime)) return false;  PayChannelForRuntime other = (PayChannelForRuntime)o; if (!other.canEqual(this)) return false;  Object this$mchInfo = getMchInfo(), other$mchInfo = other.getMchInfo(); return !((this$mchInfo == null) ? (other$mchInfo != null) : !this$mchInfo.equals(other$mchInfo)); } public void setMchInfo(MchInfo mchInfo) { this.mchInfo = mchInfo; }
    public MchInfo getMchInfo() {
     return this.mchInfo;
   }
 }

