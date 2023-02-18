 package org.hlpay.base.bo;
 public class MchPayChannelWXBo extends AccessBo implements Serializable { @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id; @ApiModelProperty(value = "商户ID（组织ID：集团ID、下属企业ID、门店ID等）", required = true)
   private String mchId; @ApiModelProperty(value = "IP白名单", required = true)
   private String IPWhiteList; @ApiModelProperty(value = "支付渠道商户ID", required = true)
   private String channelMchId; @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true)
   private Byte state;

   public MchPayChannelWXBo setId(Integer id) { this.id = id; return this; } @ApiModelProperty(value = "微信支付APPID", required = true) private String appId; @ApiModelProperty(value = "KEY", required = true) private String key; @ApiModelProperty("支付方式：APP 表示手机支付， PC表示PC网页支付，MWEB表示手机网站支付，QR表示扫码支付") private String payType; @ApiModelProperty("币种：CNY表示人民币，USD表示美元,大小写不敏感") private String currency; @ApiModelProperty("费率") private String poundageRate; @ApiModelProperty("APP SECRET") private String appSecret; public MchPayChannelWXBo setMchId(String mchId) { this.mchId = mchId; return this; } public MchPayChannelWXBo setIPWhiteList(String IPWhiteList) { this.IPWhiteList = IPWhiteList; return this; } public MchPayChannelWXBo setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; return this; } public MchPayChannelWXBo setState(Byte state) { this.state = state; return this; } public MchPayChannelWXBo setAppId(String appId) { this.appId = appId; return this; } public MchPayChannelWXBo setKey(String key) { this.key = key; return this; } public MchPayChannelWXBo setPayType(String payType) { this.payType = payType; return this; } public MchPayChannelWXBo setCurrency(String currency) { this.currency = currency; return this; } public MchPayChannelWXBo setPoundageRate(String poundageRate) { this.poundageRate = poundageRate; return this; } public MchPayChannelWXBo setAppSecret(String appSecret) { this.appSecret = appSecret; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof MchPayChannelWXBo)) return false;  MchPayChannelWXBo other = (MchPayChannelWXBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$IPWhiteList = getIPWhiteList(), other$IPWhiteList = other.getIPWhiteList(); if ((this$IPWhiteList == null) ? (other$IPWhiteList != null) : !this$IPWhiteList.equals(other$IPWhiteList)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$appId = getAppId(), other$appId = other.getAppId(); if ((this$appId == null) ? (other$appId != null) : !this$appId.equals(other$appId)) return false;  Object this$key = getKey(), other$key = other.getKey(); if ((this$key == null) ? (other$key != null) : !this$key.equals(other$key)) return false;  Object this$payType = getPayType(), other$payType = other.getPayType(); if ((this$payType == null) ? (other$payType != null) : !this$payType.equals(other$payType)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$poundageRate = getPoundageRate(), other$poundageRate = other.getPoundageRate(); if ((this$poundageRate == null) ? (other$poundageRate != null) : !this$poundageRate.equals(other$poundageRate)) return false;  Object this$appSecret = getAppSecret(), other$appSecret = other.getAppSecret(); return !((this$appSecret == null) ? (other$appSecret != null) : !this$appSecret.equals(other$appSecret)); } protected boolean canEqual(Object other) { return other instanceof MchPayChannelWXBo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $IPWhiteList = getIPWhiteList(); result = result * 59 + (($IPWhiteList == null) ? 43 : $IPWhiteList.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $appId = getAppId(); result = result * 59 + (($appId == null) ? 43 : $appId.hashCode()); Object $key = getKey(); result = result * 59 + (($key == null) ? 43 : $key.hashCode()); Object $payType = getPayType(); result = result * 59 + (($payType == null) ? 43 : $payType.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $poundageRate = getPoundageRate(); result = result * 59 + (($poundageRate == null) ? 43 : $poundageRate.hashCode()); Object $appSecret = getAppSecret(); return result * 59 + (($appSecret == null) ? 43 : $appSecret.hashCode()); } public String toString() { return "MchPayChannelWXBo(id=" + getId() + ", mchId=" + getMchId() + ", IPWhiteList=" + getIPWhiteList() + ", channelMchId=" + getChannelMchId() + ", state=" + getState() + ", appId=" + getAppId() + ", key=" + getKey() + ", payType=" + getPayType() + ", currency=" + getCurrency() + ", poundageRate=" + getPoundageRate() + ", appSecret=" + getAppSecret() + ")"; }







   public Integer getId() {
     return this.id;
   }



   public String getMchId() {
     return this.mchId;
   }





   public String getIPWhiteList() {
     return this.IPWhiteList;
   }





   public String getChannelMchId() {
     return this.channelMchId;
   }





   public Byte getState() {
     return this.state;
   }





   public String getAppId() {
     return this.appId;
   }





   public String getKey() {
     return this.key;
   }





   public String getPayType() {
     return this.payType;
   }





   public String getCurrency() {
     return this.currency;
   }





   public String getPoundageRate() {
     return this.poundageRate;
   }





   public String getAppSecret() {
     return this.appSecret;
   } }

