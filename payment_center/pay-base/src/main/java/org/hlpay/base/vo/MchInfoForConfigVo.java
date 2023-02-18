 package org.hlpay.base.vo;

 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.ApiModelProperty;
 import java.util.List;
 import org.apache.commons.lang3.StringUtils;


 public class MchInfoForConfigVo
   extends ProMchInfoVo
 {
   @ApiModelProperty("商户ID")
   private String mchId;
   @ApiModelProperty("父级商户ID")
   private String parentMchId;
   @ApiModelProperty("外部ID")
   private String externalId;
   @ApiModelProperty("外部父级ID")
   private String parentExternalId;
   @ApiModelProperty("商户名称")
   private String name;
   @ApiModelProperty("商户类型（0：平台； 1：商户； 2：商户分支机构）")
   private String type;
   @ApiModelProperty("商户状态,0-停止使用,1-使用中")
   private Byte state;
   @ApiModelProperty(value = "安全域名", required = true)
   private String securityUrl;
   @ApiModelProperty("商户对应的支付渠道信息")
   private List<PayChannelForConfigVo> payChannels;
   private String settleCycle;
   @ApiModelProperty("平台代理信息，隶属于平台")
   private List<BaseMchInfoVo> Agency;
   private static final long serialVersionUID = 1L;

   public List<BaseMchInfoVo> getAgency() {
     return this.Agency;
   }

   public void setAgency(List<BaseMchInfoVo> agency) {
     this.Agency = agency;
   }

   public String getMchId() {
     return this.mchId;
   }

   public void setMchId(String mchId) {
     this.mchId = mchId;
   }



   public String getParentMchId() {
     return this.parentMchId;
   }

   public void setParentMchId(String parentMchId) {
     this.parentMchId = parentMchId;
   }

   public String getExternalId() {
     return this.externalId;
   }

   public void setExternalId(String externalId) {
     this.externalId = externalId;
   }

   public String getParentExternalId() {
     return this.parentExternalId;
   }

   public void setParentExternalId(String parentExternalId) {
     this.parentExternalId = parentExternalId;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }

   public Byte getState() {
     return this.state;
   }

   public void setState(Byte state) {
     this.state = state;
   }

   public String getSecurityUrl() {
     return this.securityUrl;
   }

   public void setSecurityUrl(String securityUrl) {
     this.securityUrl = securityUrl;
   }

   public List<PayChannelForConfigVo> getPayChannels() {
     return this.payChannels;
   }

   public void setPayChannels(List<PayChannelForConfigVo> payChannels) {
     this.payChannels = payChannels;
   }

   public String getSettleCycle() {
     if (StringUtils.isBlank(this.settleType)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleType);
     this.settleCycle = obj.getString("cycle");
     return this.settleCycle;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append(", mchId=").append(this.mchId);
     sb.append(", parentMchId=").append(this.parentMchId);
     sb.append(", externalId=").append(this.externalId);
     sb.append(", parentExternalId=").append(this.parentExternalId);
     sb.append(", name=").append(this.name);
     sb.append(", type=").append(this.type);
     sb.append(", state=").append(this.state);
     sb.append(", securityUrl=").append(this.securityUrl);
     sb.append(", channel=").append(JSONArray.toJSON(this.payChannels).toString());
     sb.append("]");
     return sb.toString();
   }
 }
