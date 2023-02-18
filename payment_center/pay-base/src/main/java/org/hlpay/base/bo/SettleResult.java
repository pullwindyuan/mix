 package org.hlpay.base.bo;

 public class SettleResult {
   Long checkSum;

   public void setCheckSum(Long checkSum) { this.checkSum = checkSum; } Long poundage; JSONObject comments; public void setPoundage(Long poundage) { this.poundage = poundage; } public void setComments(JSONObject comments) { this.comments = comments; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SettleResult)) return false;  SettleResult other = (SettleResult)o; if (!other.canEqual(this)) return false;  Object this$checkSum = getCheckSum(), other$checkSum = other.getCheckSum(); if ((this$checkSum == null) ? (other$checkSum != null) : !this$checkSum.equals(other$checkSum)) return false;  Object this$poundage = getPoundage(), other$poundage = other.getPoundage(); if ((this$poundage == null) ? (other$poundage != null) : !this$poundage.equals(other$poundage)) return false;  Object this$comments = getComments(), other$comments = other.getComments(); return !((this$comments == null) ? (other$comments != null) : !this$comments.equals(other$comments)); } protected boolean canEqual(Object other) { return other instanceof SettleResult; } public int hashCode() { int PRIME = 59; result = 1; Object $checkSum = getCheckSum(); result = result * 59 + (($checkSum == null) ? 43 : $checkSum.hashCode()); Object $poundage = getPoundage(); result = result * 59 + (($poundage == null) ? 43 : $poundage.hashCode()); Object $comments = getComments(); return result * 59 + (($comments == null) ? 43 : $comments.hashCode()); } public String toString() { return "SettleResult(checkSum=" + getCheckSum() + ", poundage=" + getPoundage() + ", comments=" + getComments() + ")"; }

   public Long getCheckSum() { return this.checkSum; }
   public Long getPoundage() { return this.poundage; } public JSONObject getComments() {
     return this.comments;
   }
 }

