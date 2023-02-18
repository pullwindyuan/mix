/*     */ package org.hlpay.base.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.hlpay.base.model.MchInfo;
import org.hlpay.base.model.PayChannel;
import org.hlpay.common.enumm.CurrencyTypeEnum;
import org.hlpay.common.util.BeanUtil;

import java.io.Serializable;
import java.util.Date;

 public class PayChannelVo implements Serializable {
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

   public void setCurrency(String currency) { this.currency = currency; }
     @ApiModelProperty("配置参数,json字符串")
     private String param;
   @ApiModelProperty("币种")
     private String currency;
   @ApiModelProperty("手续费")
   private String poundageRate;
   @ApiModelProperty("备注")
   private String remark;
   @ApiModelProperty("创建时间")
   private Date createTime;
   @ApiModelProperty("更新时间")
   private Date updateTime; private static final long serialVersionUID = 1L;
   public void setPoundageRate(String poundageRate) { this.poundageRate = poundageRate; }


   public String getCurrency() {
     return this.currency;
   }





   public String getPoundageRate() {
     return this.poundageRate;
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

   public static PayChannelVo getInstance(PayChannel payChannel, MchInfo mchInfo) {
     PayChannelVo payChannelVo = (PayChannelVo) BeanUtil.copyProperties(payChannel, PayChannelVo.class);
     payChannelVo.setId(payChannel.getId());
     JSONObject param = JSONObject.parseObject(payChannel.getParam());
     String currency = param.getString("currency").toUpperCase();
     currency = CurrencyTypeEnum.valueOf(currency).name();

     if (mchInfo != null) {
       param = JSONObject.parseObject(mchInfo.getSettleParams());
       payChannelVo.setPoundageRate(param.getString(payChannel.getChannelName()));
     } else {
       payChannelVo.setPoundageRate(param.getString("poundageRate"));
     }
     payChannelVo.setCurrency(currency);

     if (payChannelVo.getChannelId().equals("WX_MWEB")) {
       payChannelVo.setChannelId("PC");
     } else if (payChannelVo.getChannelId().equals("WX_APP")) {
       payChannelVo.setChannelId("APP");
     } else if (payChannelVo.getChannelId().equals("WX_JSAPI")) {
       payChannelVo.setChannelId("JSAPI");
     } else if (payChannelVo.getChannelId().equals("ALIPAY_PC")) {
       payChannelVo.setChannelId("PC");
     } else if (payChannelVo.getChannelId().equals("ALIPAY_MOBILE")) {
       payChannelVo.setChannelId("APP");
     } else if (payChannelVo.getChannelId().equals("UNION_PC")) {
       payChannelVo.setChannelId("PC");
     } else if (payChannelVo.getChannelId().equals("UNION_WAP")) {
       payChannelVo.setChannelId("APP");
     }
     return payChannelVo;
   }

   @Override
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


   @Override
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
     PayChannelVo other = (PayChannelVo)that;
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


   @Override
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





