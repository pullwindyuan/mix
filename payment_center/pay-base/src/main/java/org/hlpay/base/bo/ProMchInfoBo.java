 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;


















 public class ProMchInfoBo
   implements Serializable
 {
   @ApiModelProperty(value = "结算类型: 'DAY'-日结；'WEEK'-周结；'MONTH'-月结；'YEAR'-年结；'ANY'-按需结算", required = false)
   private String settleType;
   @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false)
   private Integer settlePoundageRate;
   @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false)
   private Integer settleParamsTn;

   public String getSettleType() {
     return this.settleType;
   }

   public void setSettleType(String settleType) {
     this.settleType = settleType;
   }

   public Integer getSettlePoundageRate() {
     return this.settlePoundageRate;
   }

   public void setSettlePoundageRate(Integer settlePoundageRate) {
     this.settlePoundageRate = settlePoundageRate;
   }

   public Integer getSettleParamsTn() {
     return this.settleParamsTn;
   }

   public void setSettleParamsTn(Integer settleParamsTn) {
     this.settleParamsTn = settleParamsTn;
   }


   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(getClass().getSimpleName());
     sb.append(" [");
     sb.append("Hash = ").append(hashCode());
     sb.append("]");
     return sb.toString();
   }
 }
