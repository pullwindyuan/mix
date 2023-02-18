 package org.hlpay.base.vo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import java.util.Date;

 public class PayChannelForAppVo
   implements Serializable
 {
   @ApiModelProperty("渠道ID")
   private String channelId;
   @ApiModelProperty("渠道名称")
   private String channelName;
   @ApiModelProperty("使用状态：0-停止使用,1-使用中")
   private Byte state;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("创建时间")
   private Date createTime;
   @ApiModelProperty("更新时间")
   private Date updateTime;
   private static final long serialVersionUID = 1L;

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

   public Byte getState() {
     return this.state;
   }

   public void setState(Byte state) {
     this.state = state;
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

   public static long getSerialVersionUID() {
     return 1L;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", channelId=").append(this.channelId);
     sb.append(", channelName=").append(this.channelName);
     sb.append(", state=").append(this.state);
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
     PayChannelForAppVo other = (PayChannelForAppVo)that;
     return (((getChannelId() == null) ? (other.getChannelId() == null) : getChannelId().equals(other.getChannelId())) && (
       (getChannelName() == null) ? (other.getChannelName() == null) : getChannelName().equals(other.getChannelName())) && (
       (getState() == null) ? (other.getState() == null) : getState().equals(other.getState())) && (
       (getRemark() == null) ? (other.getRemark() == null) : getRemark().equals(other.getRemark())) && (
       (getCreateTime() == null) ? (other.getCreateTime() == null) : getCreateTime().equals(other.getCreateTime())) && (
       (getUpdateTime() == null) ? (other.getUpdateTime() == null) : getUpdateTime().equals(other.getUpdateTime())));
   }


   public int hashCode() {
     int prime = 31;
     int result = 1;
     result = 31 * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
     result = 31 * result + ((getChannelName() == null) ? 0 : getChannelName().hashCode());
     result = 31 * result + ((getState() == null) ? 0 : getState().hashCode());
     result = 31 * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
     result = 31 * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
     result = 31 * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
     return result;
   }
 }





