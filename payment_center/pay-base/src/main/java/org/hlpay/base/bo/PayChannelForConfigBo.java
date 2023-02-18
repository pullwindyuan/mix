 package org.hlpay.base.bo;
 public class PayChannelForConfigBo implements Serializable { @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id; @ApiModelProperty(value = "渠道ID", required = true)
   private String channelId; @ApiModelProperty(value = "渠道名称", required = true)
   private String channelName; @ApiModelProperty(value = "支付渠道商户ID", required = true)
   private String channelMchId; @ApiModelProperty("渠道收款账号")
   private String channelAccount;
   public void setId(Integer id) { this.id = id; } @ApiModelProperty("商户ID:当创建商户支付渠道的时候必填，当创建平台支付渠道的时候，这个ID会忽略，后台会自动填充") private String mchId; @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true) private Byte state; @ApiModelProperty(value = "配置参数,json字符串", required = true) private String param; @ApiModelProperty("备注") private String remark; private static final long serialVersionUID = 1L; public void setChannelId(String channelId) { this.channelId = channelId; } public void setChannelName(String channelName) { this.channelName = channelName; } public void setChannelMchId(String channelMchId) { this.channelMchId = channelMchId; } public void setChannelAccount(String channelAccount) { this.channelAccount = channelAccount; } public void setMchId(String mchId) { this.mchId = mchId; } public void setState(Byte state) { this.state = state; } public void setParam(String param) { this.param = param; } public void setRemark(String remark) { this.remark = remark; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelForConfigBo)) return false;  PayChannelForConfigBo other = (PayChannelForConfigBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$channelId = getChannelId(), other$channelId = other.getChannelId(); if ((this$channelId == null) ? (other$channelId != null) : !this$channelId.equals(other$channelId)) return false;  Object this$channelName = getChannelName(), other$channelName = other.getChannelName(); if ((this$channelName == null) ? (other$channelName != null) : !this$channelName.equals(other$channelName)) return false;  Object this$channelMchId = getChannelMchId(), other$channelMchId = other.getChannelMchId(); if ((this$channelMchId == null) ? (other$channelMchId != null) : !this$channelMchId.equals(other$channelMchId)) return false;  Object this$channelAccount = getChannelAccount(), other$channelAccount = other.getChannelAccount(); if ((this$channelAccount == null) ? (other$channelAccount != null) : !this$channelAccount.equals(other$channelAccount)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$param = getParam(), other$param = other.getParam(); if ((this$param == null) ? (other$param != null) : !this$param.equals(other$param)) return false;  Object this$remark = getRemark(), other$remark = other.getRemark(); return !((this$remark == null) ? (other$remark != null) : !this$remark.equals(other$remark)); } protected boolean canEqual(Object other) { return other instanceof PayChannelForConfigBo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $channelId = getChannelId(); result = result * 59 + (($channelId == null) ? 43 : $channelId.hashCode()); Object $channelName = getChannelName(); result = result * 59 + (($channelName == null) ? 43 : $channelName.hashCode()); Object $channelMchId = getChannelMchId(); result = result * 59 + (($channelMchId == null) ? 43 : $channelMchId.hashCode()); Object $channelAccount = getChannelAccount(); result = result * 59 + (($channelAccount == null) ? 43 : $channelAccount.hashCode()); Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $param = getParam(); result = result * 59 + (($param == null) ? 43 : $param.hashCode()); Object $remark = getRemark(); return result * 59 + (($remark == null) ? 43 : $remark.hashCode()); } public String toString() { return "PayChannelForConfigBo(id=" + getId() + ", channelId=" + getChannelId() + ", channelName=" + getChannelName() + ", channelMchId=" + getChannelMchId() + ", channelAccount=" + getChannelAccount() + ", mchId=" + getMchId() + ", state=" + getState() + ", param=" + getParam() + ", remark=" + getRemark() + ")"; }






   public Integer getId() {
     return this.id;
   }




   public String getChannelId() {
     return this.channelId;
   }





   public String getChannelName() {
     return this.channelName;
   }





   public String getChannelMchId() {
     return this.channelMchId;
   }





   public String getChannelAccount() {
     return this.channelAccount;
   }





   public String getMchId() {
     return this.mchId;
   }





   public Byte getState() {
     return this.state;
   }





   public String getParam() {
     return this.param;
   }





   public String getRemark() {
/* 79 */     return this.remark;
/*    */   } }

