 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;



 public class CommonBo
   extends AccessBo
 {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "通用ID:根据业务决定", required = false)
   private Long id;

   public String toString() {
     return "CommonBo(id=" + getId() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); return result * 59 + (($id == null) ? 43 : $id.hashCode()); } protected boolean canEqual(Object other) { return other instanceof CommonBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CommonBo)) return false;  CommonBo other = (CommonBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); return !((this$id == null) ? (other$id != null) : !this$id.equals(other$id)); } public CommonBo setId(Long id) { this.id = id; return this; }







   public Long getId() {
     return this.id;
   }
 }





