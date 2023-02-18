 package org.hlpay.base.bo;
 public class PayChannelALIPAYBo extends AccessBo implements Serializable { @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id; @ApiModelProperty(value = "IP白名单", required = true)
   private String IPWhiteList; @ApiModelProperty(value = "渠道对应的支付中心商户ID：如果为空或者不传就默认配置成平台全局支付参数", required = false)
   private String mchId; @ApiModelProperty(value = "支付渠道商户ID", required = true)
   private String channelMchId; @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true)
   private Byte state;

   public PayChannelALIPAYBo setId(Integer id) { this.id = id; return this; } @ApiModelProperty("商户私钥") private String privateKey; @ApiModelProperty("支付方式：APP 表示手机支付， PC表示PC网页支付，MWEB表示手机网站支付，QR表示扫码支付") private String payType; @ApiModelProperty("币种：CNY表示人民币，USD表示美元,大小写不敏感") private String currency; @ApiModelProperty("费率") private String poundageRate; @ApiModelProperty("支付宝公钥") private String alipayPublicKey; public PayChannelALIPAYBo setIPWhiteList(String IPWhiteList) { this.IPWhiteList = IPWhiteList; return this; } public PayChannelALIPAYBo setMchId(String mchId) { this.mchId = mchId; return this; } public PayChannelALIPAYBo setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; return this; } public PayChannelALIPAYBo setState(Byte state) { this.state = state; return this; } public PayChannelALIPAYBo setPrivateKey(String privateKey) { this.privateKey = privateKey; return this; } public PayChannelALIPAYBo setPayType(String payType) { this.payType = payType; return this; } public PayChannelALIPAYBo setCurrency(String currency) { this.currency = currency; return this; } public PayChannelALIPAYBo setPoundageRate(String poundageRate) { this.poundageRate = poundageRate; return this; } public PayChannelALIPAYBo setAlipayPublicKey(String alipayPublicKey) { this.alipayPublicKey = alipayPublicKey; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelALIPAYBo)) return false;  PayChannelALIPAYBo other = (PayChannelALIPAYBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$IPWhiteList = getIPWhiteList(), other$IPWhiteList = other.getIPWhiteList(); if ((this$IPWhiteList == null) ? (other$IPWhiteList != null) : !this$IPWhiteList.equals(other$IPWhiteList)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$privateKey = getPrivateKey(), other$privateKey = other.getPrivateKey(); if ((this$privateKey == null) ? (other$privateKey != null) : !this$privateKey.equals(other$privateKey)) return false;  Object this$payType = getPayType(), other$payType = other.getPayType(); if ((this$payType == null) ? (other$payType != null) : !this$payType.equals(other$payType)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  Object this$poundageRate = getPoundageRate(), other$poundageRate = other.getPoundageRate(); if ((this$poundageRate == null) ? (other$poundageRate != null) : !this$poundageRate.equals(other$poundageRate)) return false;  Object this$alipayPublicKey = getAlipayPublicKey(), other$alipayPublicKey = other.getAlipayPublicKey(); return !((this$alipayPublicKey == null) ? (other$alipayPublicKey != null) : !this$alipayPublicKey.equals(other$alipayPublicKey)); } protected boolean canEqual(Object other) { return other instanceof PayChannelALIPAYBo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $IPWhiteList = getIPWhiteList(); result = result * 59 + (($IPWhiteList == null) ? 43 : $IPWhiteList.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $privateKey = getPrivateKey(); result = result * 59 + (($privateKey == null) ? 43 : $privateKey.hashCode()); Object $payType = getPayType(); result = result * 59 + (($payType == null) ? 43 : $payType.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); Object $poundageRate = getPoundageRate(); result = result * 59 + (($poundageRate == null) ? 43 : $poundageRate.hashCode()); Object $alipayPublicKey = getAlipayPublicKey(); return result * 59 + (($alipayPublicKey == null) ? 43 : $alipayPublicKey.hashCode()); } public String toString() { return "PayChannelALIPAYBo(id=" + getId() + ", IPWhiteList=" + getIPWhiteList() + ", mchId=" + getMchId() + ", channelMchId=" + getChannelMchId() + ", state=" + getState() + ", privateKey=" + getPrivateKey() + ", payType=" + getPayType() + ", currency=" + getCurrency() + ", poundageRate=" + getPoundageRate() + ", alipayPublicKey=" + getAlipayPublicKey() + ")"; }







   public Integer getId() {
     return this.id;
   }





   public String getIPWhiteList() {
     return this.IPWhiteList;
   }





   public String getMchId() {
     return this.mchId;
   }





   public String getChannelMchId() {
     return this.channelMchId;
   }





   public Byte getState() {
     return this.state;
   }





   public String getPrivateKey() {
     return this.privateKey;
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





   public String getAlipayPublicKey() {
     return this.alipayPublicKey;
   } }
