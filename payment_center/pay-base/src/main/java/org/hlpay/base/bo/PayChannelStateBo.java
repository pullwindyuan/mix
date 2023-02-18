 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

 public class PayChannelStateBo extends AccessBo implements Serializable {
   @ApiModelProperty("渠道主键ID：该值为空表示新增，有值表示修改")
   private Integer id;

   public PayChannelStateBo setId(Integer id) { this.id = id; return this; } @ApiModelProperty(value = "使用状态：0-停止使用,1-使用中", required = true) private Byte state; public PayChannelStateBo setState(Byte state) { this.state = state; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PayChannelStateBo)) return false;  PayChannelStateBo other = (PayChannelStateBo)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  Object this$state = getState(), other$state = other.getState(); return !((this$state == null) ? (other$state != null) : !this$state.equals(other$state)); } protected boolean canEqual(Object other) { return other instanceof PayChannelStateBo; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); Object $state = getState(); return result * 59 + (($state == null) ? 43 : $state.hashCode()); } public String toString() { return "PayChannelStateBo(id=" + getId() + ", state=" + getState() + ")"; }







   public Integer getId() {
     return this.id;
   }





   public Byte getState() {
     return this.state;
   }
 }

