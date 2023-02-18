 package org.hlpay.base.model;
 public class PayChannel implements Serializable { private Integer id; private String channelId; private String channelName;
   private String channelMchId;
   private String channelAccount;
   private String mchId;
   private Byte state;
   private String param;

   public void setOpenId(String openId) { this.openId = openId; } private String remark; private Date createTime; private Date updateTime; private String currency; private String openId; private String productId; private String sceneInfo; private static final long serialVersionUID = 1L; public void setProductId(String productId) { this.productId = productId; } public void setSceneInfo(String sceneInfo) { this.sceneInfo = sceneInfo; }


   public String getOpenId() {
     return this.openId;
   } public String getProductId() {
     return this.productId;
   } public String getSceneInfo() {
     return this.sceneInfo;
   }


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

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = DateUtils.formatDateTime(createTime);
   }

   public void setUpdateTime(String updateTime) {
     this.updateTime = DateUtils.formatDateTime(updateTime);
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

   public String getCurrency() {
     return this.currency;
   }

   public void setCurrency(String currency) {
     this.currency = currency;
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
     sb.append(", currency=").append(this.currency);
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
     PayChannel other = (PayChannel)that;
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
   } }

