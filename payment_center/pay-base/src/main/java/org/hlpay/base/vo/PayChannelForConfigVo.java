 package org.hlpay.base.vo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import java.util.Date;


 public class PayChannelForConfigVo
   implements Serializable
 {
   @ApiModelProperty("主键ID")
   private Integer id;
   @ApiModelProperty("渠道ID")
   private String channelId;
   @ApiModelProperty("渠道名称")
   private String channelName;
   @ApiModelProperty("支付渠道商户ID")
   private String channelMchId;
   @ApiModelProperty("渠道收款账号")
   private String channelAccount;
   @ApiModelProperty("商户ID")
   private String mchId;
   @ApiModelProperty("使用状态：0-停止使用,1-使用中")
   private Byte state;
   @ApiModelProperty("配置参数,json字符串")
   private String param;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("创建时间")
   private Date createTime;
   @ApiModelProperty("更新时间")
   private Date updateTime;
   private static final long serialVersionUID = 1L;

   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getChannelId() {
     return this.channelId;
   }

   public void setChannelId(String channelId) {
     this.channelId = channelId;
   }

   public String getChannelName() {
     return this.channelName;
   }

   public void setChannelName(String channelName) {
     this.channelName = channelName;
   }

   public String getChannelMchId() {
     return this.channelMchId;
   }

   public void setChannelMchId(String channelMchId) {
     this.channelMchId = channelMchId;
   }

   public String getMchId() {
     return this.mchId;
   }

   public void setMchId(String mchId) {
     this.mchId = mchId;
   }

   public Byte getState() {
     return this.state;
   }

   public void setState(Byte state) {
     this.state = state;
   }

   public String getParam() {
     return this.param;
   }

   public void setParam(String param) {
     this.param = param;
   }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) {
     this.remark = remark;
   }

   public Date getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
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
     sb.append(", id=").append(this.id);
     sb.append(", channelId=").append(this.channelId);
     sb.append(", channelName=").append(this.channelName);
     sb.append(", channelMchId=").append(this.channelMchId);
     sb.append(", channelAccount=").append(this.channelAccount);
     sb.append(", mchId=").append(this.mchId);
     sb.append(", state=").append(this.state);
     sb.append(", param=").append(this.param);
     sb.append(", remark=").append(this.remark);
     sb.append(", createTime=").append(this.createTime);
     sb.append(", updateTime=").append(this.updateTime);
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
     PayChannelForConfigVo other = (PayChannelForConfigVo)that;
     return (((getId() == null) ? (other.getId() == null) : getId().equals(other.getId())) && (
       (getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
       (getChannelName() == null) ? (other.getChannelName() == null) : getChannelName().equals(other.getChannelName())) && (
       (getChannelMchId() == null) ? (other.getChannelMchId() == null) : getChannelMchId().equals(other.getChannelMchId())) && (
       (getChannelAccount() == null) ? (other.getChannelAccount() == null) : getChannelAccount().equals(other.getChannelAccount())) && (
       (getMchId() == null) ? (other.getMchId() == null) : getMchId().equals(other.getMchId())) && (
       (getState() == null) ? (other.getState() == null) : getState().equals(other.getState())) && (
       (getParam() == null) ? (other.getParam() == null) : getParam().equals(other.getParam())) && (
       (getRemark() == null) ? (other.getRemark() == null) : getRemark().equals(other.getRemark())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getId() == null) ? 0 : getId().hashCode());
     result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
     result = 31 * result + ((getChannelName() == null) ? 0 : getChannelName().hashCode());
     result = 31 * result + ((getChannelMchId() == null) ? 0 : getChannelMchId().hashCode());
     result = 31 * result + ((getChannelAccount() == null) ? 0 : getChannelAccount().hashCode());
     result = 31 * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
     result = 31 * result + ((getState() == null) ? 0 : getState().hashCode());
     result = 31 * result + ((getParam() == null) ? 0 : getParam().hashCode());
     result = 31 * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     return result;
   }
 }

