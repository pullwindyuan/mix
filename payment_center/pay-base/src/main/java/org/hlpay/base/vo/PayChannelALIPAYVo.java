 package org.hlpay.base.vo;
 public class PayChannelALIPAYVo extends AccessBo implements Serializable {
   @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id;
   @ApiModelProperty(value = "IP白名单", required = true)
   private String IPWhiteList;
   @ApiModelProperty(value = "支付渠道商户ID", required = true)
   private String channelMchId;

   public PayChannelALIPAYVo setId(Integer id) { this.id = id; return this; } @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true) private Byte state; @ApiModelProperty("商户私钥") private String privateKey; @ApiModelProperty("支付宝公钥") private String alipayPublicKey; public PayChannelALIPAYVo setIPWhiteList(String IPWhiteList) { this.IPWhiteList = IPWhiteList; return this; } public PayChannelALIPAYVo setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; return this; } public PayChannelALIPAYVo setState(Byte state) { this.state = state; return this; } public PayChannelALIPAYVo setPrivateKey(String privateKey) { this.privateKey = privateKey; return this; } public PayChannelALIPAYVo setAlipayPublicKey(String alipayPublicKey) { this.alipayPublicKey = alipayPublicKey; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelALIPAYVo)) return false;  PayChannelALIPAYVo other = (PayChannelALIPAYVo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$IPWhiteList = getIPWhiteList(), other$IPWhiteList = other.getIPWhiteList(); if ((this$IPWhiteList == null) ? (other$IPWhiteList != null) : !this$IPWhiteList.equals(other$IPWhiteList)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$privateKey = getPrivateKey(), other$privateKey = other.getPrivateKey(); if ((this$privateKey == null) ? (other$privateKey != null) : !this$privateKey.equals(other$privateKey)) return false;  Object this$alipayPublicKey = getAlipayPublicKey(), other$alipayPublicKey = other.getAlipayPublicKey(); return !((this$alipayPublicKey == null) ? (other$alipayPublicKey != null) : !this$alipayPublicKey.equals(other$alipayPublicKey)); } protected boolean canEqual(Object other) { return other instanceof PayChannelALIPAYVo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $IPWhiteList = getIPWhiteList(); result = result * 59 + (($IPWhiteList == null) ? 43 : $IPWhiteList.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $privateKey = getPrivateKey(); result = result * 59 + (($privateKey == null) ? 43 : $privateKey.hashCode()); Object $alipayPublicKey = getAlipayPublicKey(); return result * 59 + (($alipayPublicKey == null) ? 43 : $alipayPublicKey.hashCode()); } public String toString() { return "PayChannelALIPAYVo(id=" + getId() + ", IPWhiteList=" + getIPWhiteList() + ", channelMchId=" + getChannelMchId() + ", state=" + getState() + ", privateKey=" + getPrivateKey() + ", alipayPublicKey=" + getAlipayPublicKey() + ")"; }

   public Integer getId() {
     return this.id;
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

   public String getPrivateKey() {
     return this.privateKey;
   }

   public String getAlipayPublicKey() {
     return this.alipayPublicKey;
   }
 }





