 package org.hlpay.base.bo;

 public class PlatformSettleAuditBo extends AccessBo {
   @ApiModelProperty(value = "审核状态:0-不通过；1-通过", required = false)
   private Integer audit;

   public String toString() { return "PlatformSettleAuditBo(audit=" + getAudit() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $audit = getAudit(); return result * 59 + (($audit == null) ? 43 : $audit.hashCode()); } protected boolean canEqual(Object other) { return other instanceof PlatformSettleAuditBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PlatformSettleAuditBo)) return false;  PlatformSettleAuditBo other = (PlatformSettleAuditBo)o; if (!other.canEqual(this)) return false;  Object this$audit = getAudit(), other$audit = other.getAudit(); return !((this$audit == null) ? (other$audit != null) : !this$audit.equals(other$audit)); } public PlatformSettleAuditBo setAudit(Integer audit) { this.audit = audit; return this; }







   public Integer getAudit() {
     return this.audit;
   }
 }





