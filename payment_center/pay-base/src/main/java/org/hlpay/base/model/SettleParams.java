 package org.hlpay.base.model;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.ApiModelProperty;

 public class SettleParams implements Serializable {
   private String mchId;
   private String name;
   private String type;
   private Byte settleParamsState;

   public SettleParams setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false) private Integer settlePoundageRate; @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false) private Integer settleParamsTn; private String settleCycle; private static final long serialVersionUID = 1L; public SettleParams setName(String name) { this.name = name; return this; } public SettleParams setType(String type) { this.type = type; return this; } public SettleParams setSettleParamsState(Byte settleParamsState) { this.settleParamsState = settleParamsState; return this; } public SettleParams setSettlePoundageRate(Integer settlePoundageRate) { this.settlePoundageRate = settlePoundageRate; return this; } public SettleParams setSettleParamsTn(Integer settleParamsTn) { this.settleParamsTn = settleParamsTn; return this; } public SettleParams setSettleCycle(String settleCycle) { this.settleCycle = settleCycle; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SettleParams)) return false;  SettleParams other = (SettleParams)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$settleParamsState = getSettleParamsState(), other$settleParamsState = other.getSettleParamsState(); if ((this$settleParamsState == null) ? (other$settleParamsState != null) : !this$settleParamsState.equals(other$settleParamsState)) return false;  Object this$settlePoundageRate = getSettlePoundageRate(), other$settlePoundageRate = other.getSettlePoundageRate(); if ((this$settlePoundageRate == null) ? (other$settlePoundageRate != null) : !this$settlePoundageRate.equals(other$settlePoundageRate)) return false;  Object this$settleParamsTn = getSettleParamsTn(), other$settleParamsTn = other.getSettleParamsTn(); if ((this$settleParamsTn == null) ? (other$settleParamsTn != null) : !this$settleParamsTn.equals(other$settleParamsTn)) return false;  Object this$settleCycle = getSettleCycle(), other$settleCycle = other.getSettleCycle(); return !((this$settleCycle == null) ? (other$settleCycle != null) : !this$settleCycle.equals(other$settleCycle)); } protected boolean canEqual(Object other) { return other instanceof SettleParams; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $settleParamsState = getSettleParamsState(); result = result * 59 + (($settleParamsState == null) ? 43 : $settleParamsState.hashCode()); Object $settlePoundageRate = getSettlePoundageRate(); result = result * 59 + (($settlePoundageRate == null) ? 43 : $settlePoundageRate.hashCode()); Object $settleParamsTn = getSettleParamsTn(); result = result * 59 + (($settleParamsTn == null) ? 43 : $settleParamsTn.hashCode()); Object $settleCycle = getSettleCycle(); return result * 59 + (($settleCycle == null) ? 43 : $settleCycle.hashCode()); } public String toString() { return "SettleParams(mchId=" + getMchId() + ", name=" + getName() + ", type=" + getType() + ", settleParamsState=" + getSettleParamsState() + ", settlePoundageRate=" + getSettlePoundageRate() + ", settleParamsTn=" + getSettleParamsTn() + ", settleCycle=" + getSettleCycle() + ")"; }






   public String getMchId() {
     return this.mchId;
   }




   public String getName() {
     return this.name;
   }




   public String getType() {
     return this.type;
   }




   public Byte getSettleParamsState() {
     return this.settleParamsState;
   }





   public Integer getSettlePoundageRate() {
     return this.settlePoundageRate;
   }






   public Integer getSettleParamsTn() {
     return this.settleParamsTn;
   }













   public String getSettleCycle() {
     return this.settleCycle;
   }

   public SettleParams() {}

   public SettleParams(String settleParamsStr) {
     if (StringUtils.isEmpty(settleParamsStr)) {
       return;
     }
     JSONObject jsonObject = JSONObject.parseObject(settleParamsStr);
     setSettleCycle(jsonObject.getString("cycle"));
     setSettleParamsTn(jsonObject.getInteger("tn"));
     setSettlePoundageRate(jsonObject.getInteger("pr"));
   }

   public SettleParams(MchInfo mchInfo) {
     this.mchId = mchInfo.getMchId();
     this.name = mchInfo.getName();
     this.settleCycle = mchInfo.getSettleCycle();
     this.settleParamsState = mchInfo.getSettleParamsState();
     this.settleParamsTn = mchInfo.getSettleParamsTn();
     this.settlePoundageRate = mchInfo.getSettlePoundageRate();
   }



   public static long getSerialVersionUID() {
     return 1L;
   }

   public String toJSONString() {
     return JSONObject.toJSONString(this);
   }
 }





