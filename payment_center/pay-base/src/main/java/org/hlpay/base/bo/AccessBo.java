 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;

 public class AccessBo implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "当前操作人员所属组织ID", required = true)
   private String operatorOrgId;
   @ApiModelProperty(value = "当前操作人员ID，和accessKey是绑定关系", required = true)
   private String operatorId;
   @ApiModelProperty(value = "当前操作人员名称，和accessKey是绑定关系", required = true)
   private String operator;
   @ApiModelProperty(value = "授权码", required = true)
   private String accessKey;

   public AccessBo setOperatorOrgId(String operatorOrgId) { this.operatorOrgId = operatorOrgId; return this; } public AccessBo setOperatorId(String operatorId) { this.operatorId = operatorId; return this; } public AccessBo setOperator(String operator) { this.operator = operator; return this; } public AccessBo setAccessKey(String accessKey) { this.accessKey = accessKey; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof AccessBo)) return false;  AccessBo other = (AccessBo)o; if (!other.canEqual(this)) return false;  Object this$operatorOrgId = getOperatorOrgId(), other$operatorOrgId = other.getOperatorOrgId(); if ((this$operatorOrgId == null) ? (other$operatorOrgId != null) : !this$operatorOrgId.equals(other$operatorOrgId)) return false;  Object this$operatorId = getOperatorId(), other$operatorId = other.getOperatorId(); if ((this$operatorId == null) ? (other$operatorId != null) : !this$operatorId.equals(other$operatorId)) return false;  Object this$operator = getOperator(), other$operator = other.getOperator(); if ((this$operator == null) ? (other$operator != null) : !this$operator.equals(other$operator)) return false;  Object this$accessKey = getAccessKey(), other$accessKey = other.getAccessKey(); return !((this$accessKey == null) ? (other$accessKey != null) : !this$accessKey.equals(other$accessKey)); } protected boolean canEqual(Object other) { return other instanceof AccessBo; } public int hashCode() { int PRIME = 59; result = 1; Object $operatorOrgId = getOperatorOrgId(); result = result * 59 + (($operatorOrgId == null) ? 43 : $operatorOrgId.hashCode()); Object $operatorId = getOperatorId(); result = result * 59 + (($operatorId == null) ? 43 : $operatorId.hashCode()); Object $operator = getOperator(); result = result * 59 + (($operator == null) ? 43 : $operator.hashCode()); Object $accessKey = getAccessKey(); return result * 59 + (($accessKey == null) ? 43 : $accessKey.hashCode()); } public String toString() { return "AccessBo(operatorOrgId=" + getOperatorOrgId() + ", operatorId=" + getOperatorId() + ", operator=" + getOperator() + ", accessKey=" + getAccessKey() + ")"; }







   public String getOperatorOrgId() {
     return this.operatorOrgId;
   }



   public String getOperatorId() {
     return this.operatorId;
   }



   public String getOperator() {
     return this.operator;
   }



   public String getAccessKey() {
     return this.accessKey;
   }
 }
