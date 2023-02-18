package org.hlpay.base.bo;

import io.swagger.annotations.ApiModelProperty;

public class GetSettleRestDateBo
  extends AccessBo
{
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "起始页：从1开始", required = true)
  private Integer pageIndex;
  @ApiModelProperty(value = "每页条目数", required = true)
  private Integer pageSize;

  public GetSettleRestDateBo setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex; return this; } public GetSettleRestDateBo setPageSize(Integer pageSize) { this.pageSize = pageSize; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof GetSettleRestDateBo)) return false;  GetSettleRestDateBo other = (GetSettleRestDateBo)o; if (!other.canEqual(this)) return false;  Object this$pageIndex = getPageIndex(), other$pageIndex = other.getPageIndex(); if ((this$pageIndex == null) ? (other$pageIndex != null) : !this$pageIndex.equals(other$pageIndex)) return false;  Object this$pageSize = getPageSize(), other$pageSize = other.getPageSize(); return !((this$pageSize == null) ? (other$pageSize != null) : !this$pageSize.equals(other$pageSize)); } protected boolean canEqual(Object other) { return other instanceof GetSettleRestDateBo; } public int hashCode() { int PRIME = 59; result = 1; Object $pageIndex = getPageIndex(); result = result * 59 + (($pageIndex == null) ? 43 : $pageIndex.hashCode()); Object $pageSize = getPageSize(); return result * 59 + (($pageSize == null) ? 43 : $pageSize.hashCode()); } public String toString() { return "GetSettleRestDateBo(pageIndex=" + getPageIndex() + ", pageSize=" + getPageSize() + ")"; }







  public Integer getPageIndex() {
    return this.pageIndex;
  }



  public Integer getPageSize() {
    return this.pageSize;
  }
}





