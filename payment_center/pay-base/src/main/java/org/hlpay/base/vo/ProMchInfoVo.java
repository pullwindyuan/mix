 package org.hlpay.base.vo;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import org.apache.commons.lang3.StringUtils;

 public class ProMchInfoVo
   implements Serializable
 {
   @ApiModelProperty(value = "结算类型", required = false)
   protected String settleType;
   @ApiModelProperty(value = "结算参数: 该参数确定了分账的手续费率，比如千六手续费就设置为6.不确定的情况下不填写，会默认使用平台统一设置", required = false)
   protected Integer settlePoundageRate;
   @ApiModelProperty(value = "结算参数: T+n,这里需要填写的就是n值，如果不指定就默认使用平台统一设置", required = false)
   protected Integer settleParamsTn;
   protected String settleParams;

   public void setSettlePoundageRate(Integer settlePoundageRate) {
     this.settlePoundageRate = settlePoundageRate;
   }


   public void setSettleParamsTn(Integer settleParamsTn) {
     this.settleParamsTn = settleParamsTn;
   }


   public Integer getSettlePoundageRate() {
     if (StringUtils.isBlank(this.settleParams)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleParams);
     this.settlePoundageRate = obj.getInteger("pr");
     return this.settlePoundageRate;
   }


   public Integer getSettleParamsTn() {
     if (StringUtils.isBlank(this.settleParams)) {
       return null;
     }
     JSONObject obj = JSONObject.parseObject(this.settleParams);
     this.settleParamsTn = obj.getInteger("tn");
     return this.settleParamsTn;
   }

   public String getSettleType() {
     return this.settleType;
   }

   public void setSettleType(String settleType) {
     this.settleType = settleType;
   }

   public String getSettleParams() {
     return this.settleParams;
   }

   public void setSettleParams(String settleParams) {
     this.settleParams = settleParams;
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

