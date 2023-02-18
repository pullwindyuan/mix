 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;

 public class RestDateAuditBo
   extends AccessBo implements Serializable {
   @ApiModelProperty(value = "主键ID", required = true)
   private Long id;
   @ApiModelProperty(value = "审核状态:0-不通过；1-通过", required = true)
   private Integer audit;

   public RestDateAuditBo setId(Long id) {
     this.id = id; return this; } public RestDateAuditBo setAudit(Integer audit) { this.audit = audit; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof RestDateAuditBo)) return false;  RestDateAuditBo other = (RestDateAuditBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$audit = getAudit(), other$audit = other.getAudit(); return !((this$audit == null) ? (other$audit != null) : !this$audit.equals(other$audit)); } protected boolean canEqual(Object other) { return other instanceof RestDateAuditBo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $audit = getAudit(); return result * 59 + (($audit == null) ? 43 : $audit.hashCode()); } public String toString() { return "RestDateAuditBo(id=" + getId() + ", audit=" + getAudit() + ")"; }







   public Long getId() {
     return this.id;
   }





   public Integer getAudit() {
     return this.audit;
   }
 }
