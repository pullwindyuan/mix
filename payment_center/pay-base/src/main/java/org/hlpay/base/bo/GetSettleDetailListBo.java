package org.hlpay.base.bo;

import io.swagger.annotations.ApiModelProperty;

public class GetSettleDetailListBo
  extends AccessBo
{
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "结算卡ID", required = true)
  private String cardId;
  @ApiModelProperty(value = "对账确认状态：0-记账中，1-记账完成，待确认，2-已确认，3-已完成， 4-完成现金结算， 99:全部", required = true)
  private int status;

  public GetSettleDetailListBo setCardId(String cardId) {
    this.cardId = cardId; return this; } @ApiModelProperty(value = "起始页：从1开始", required = true) private Integer pageIndex; @ApiModelProperty(value = "每页条目数", required = true) private Integer pageSize; @ApiModelProperty(value = "起始时间", required = true) private Long startDate; @ApiModelProperty(value = "结束时间", required = true) private Long endDate; public GetSettleDetailListBo setStatus(int status) { this.status = status; return this; } public GetSettleDetailListBo setPageIndex(Integer pageIndex) { this.pageIndex = pageIndex; return this; } public GetSettleDetailListBo setPageSize(Integer pageSize) { this.pageSize = pageSize; return this; } public GetSettleDetailListBo setStartDate(Long startDate) { this.startDate = startDate; return this; } public GetSettleDetailListBo setEndDate(Long endDate) { this.endDate = endDate; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof GetSettleDetailListBo)) return false;  GetSettleDetailListBo other = (GetSettleDetailListBo)o; if (!other.canEqual(this)) return false;  Object this$cardId = getCardId(), other$cardId = other.getCardId(); if ((this$cardId == null) ? (other$cardId != null) : !this$cardId.equals(other$cardId)) return false;  if (getStatus() != other.getStatus()) return false;  Object this$pageIndex = getPageIndex(), other$pageIndex = other.getPageIndex(); if ((this$pageIndex == null) ? (other$pageIndex != null) : !this$pageIndex.equals(other$pageIndex)) return false;  Object this$pageSize = getPageSize(), other$pageSize = other.getPageSize(); if ((this$pageSize == null) ? (other$pageSize != null) : !this$pageSize.equals(other$pageSize)) return false;  Object this$startDate = getStartDate(), other$startDate = other.getStartDate(); if ((this$startDate == null) ? (other$startDate != null) : !this$startDate.equals(other$startDate)) return false;  Object this$endDate = getEndDate(), other$endDate = other.getEndDate(); return !((this$endDate == null) ? (other$endDate != null) : !this$endDate.equals(other$endDate)); } protected boolean canEqual(Object other) { return other instanceof GetSettleDetailListBo; } public int hashCode() { int PRIME = 59; result = 1; Object $cardId = getCardId(); result = result * 59 + (($cardId == null) ? 43 : $cardId.hashCode()); result = result * 59 + getStatus(); Object $pageIndex = getPageIndex(); result = result * 59 + (($pageIndex == null) ? 43 : $pageIndex.hashCode()); Object $pageSize = getPageSize(); result = result * 59 + (($pageSize == null) ? 43 : $pageSize.hashCode()); Object $startDate = getStartDate(); result = result * 59 + (($startDate == null) ? 43 : $startDate.hashCode()); Object $endDate = getEndDate(); return result * 59 + (($endDate == null) ? 43 : $endDate.hashCode()); } public String toString() { return "GetSettleDetailListBo(cardId=" + getCardId() + ", status=" + getStatus() + ", pageIndex=" + getPageIndex() + ", pageSize=" + getPageSize() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ")"; }







  public String getCardId() {
    return this.cardId;
  }


  public int getStatus() {
    return this.status;
  }


  public Integer getPageIndex() {
    return this.pageIndex;
  }



  public Integer getPageSize() {
    return this.pageSize;
  }



  public Long getStartDate() {
    return this.startDate;
  }



  public Long getEndDate() {
    return this.endDate;
  }
}





