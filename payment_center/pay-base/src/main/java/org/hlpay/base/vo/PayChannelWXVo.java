 package org.hlpay.base.vo;
 public class PayChannelWXVo extends AccessBo implements Serializable {
   @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id;
   @ApiModelProperty(value = "IP白名单", required = true)
   private String IPWhiteList;
   @ApiModelProperty(value = "支付渠道商户ID", required = true)
   private String channelMchId;

   public PayChannelWXVo setId(Integer id) { this.id = id; return this; } @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true) private Byte state; @ApiModelProperty(value = "微信支付APPID", required = true) private String appId; @ApiModelProperty(value = "KEY", required = true) private String key; @ApiModelProperty("APP SECRET") private String appSecret; public PayChannelWXVo setIPWhiteList(String IPWhiteList) { this.IPWhiteList = IPWhiteList; return this; } public PayChannelWXVo setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; return this; } public PayChannelWXVo setState(Byte state) { this.state = state; return this; } public PayChannelWXVo setAppId(String appId) { this.appId = appId; return this; } public PayChannelWXVo setKey(String key) { this.key = key; return this; } public PayChannelWXVo setAppSecret(String appSecret) { this.appSecret = appSecret; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelWXVo)) return false;  PayChannelWXVo other = (PayChannelWXVo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$IPWhiteList = getIPWhiteList(), other$IPWhiteList = other.getIPWhiteList(); if ((this$IPWhiteList == null) ? (other$IPWhiteList != null) : !this$IPWhiteList.equals(other$IPWhiteList)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$appId = getAppId(), other$appId = other.getAppId(); if ((this$appId == null) ? (other$appId != null) : !this$appId.equals(other$appId)) return false;  Object this$key = getKey(), other$key = other.getKey(); if ((this$key == null) ? (other$key != null) : !this$key.equals(other$key)) return false;  Object this$appSecret = getAppSecret(), other$appSecret = other.getAppSecret(); return !((this$appSecret == null) ? (other$appSecret != null) : !this$appSecret.equals(other$appSecret)); } protected boolean canEqual(Object other) { return other instanceof PayChannelWXVo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $IPWhiteList = getIPWhiteList(); result = result * 59 + (($IPWhiteList == null) ? 43 : $IPWhiteList.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $appId = getAppId(); result = result * 59 + (($appId == null) ? 43 : $appId.hashCode()); Object $key = getKey(); result = result * 59 + (($key == null) ? 43 : $key.hashCode()); Object $appSecret = getAppSecret(); return result * 59 + (($appSecret == null) ? 43 : $appSecret.hashCode()); } public String toString() { return "PayChannelWXVo(id=" + getId() + ", IPWhiteList=" + getIPWhiteList() + ", channelMchId=" + getChannelMchId() + ", state=" + getState() + ", appId=" + getAppId() + ", key=" + getKey() + ", appSecret=" + getAppSecret() + ")"; }

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

   public String getAppId() {
     return this.appId;
   }

   public String getKey() {
     return this.key;
   }

   public String getAppSecret() {
     return this.appSecret;
   }
 }

