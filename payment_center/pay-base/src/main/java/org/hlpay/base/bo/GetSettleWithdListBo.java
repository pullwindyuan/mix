 package org.hlpay.base.bo;

 import io.swagger.annotations.ApiModelProperty;

 public class GetSettleWithdListBo extends AccessBo {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "商户ID（组织ID：集团ID、下属企业ID、门店ID等）", required = true)
   private String mchId;
   @ApiModelProperty(value = "币种，如果不传，默认为CNY", required = false)
   private String currency;
   @ApiModelProperty(value = "对账确认状态：0-记账中，1-记账完成，待确认，2-已确认，3-已完成， 99:全部", required = true)
   private int status;

   public GetSettleWithdListBo setMchId(String mchId) {
     this.mchId = mchId; return this; } @ApiModelProperty(value = "起始页：从1开始", required = true) private Integer pageIndex; @ApiModelProperty(value = "每页条目数", required = true) private Integer pageSize; @ApiModelProperty(value = "起始时间", required = true) private Long startDate; @ApiModelProperty(value = "结束时间", required = true) private Long endDate; public GetSettleWithdListBo setCurrency(String currency) { this.currency = currency; return this; } public GetSettleWithdListBo setStatus(int status) { this.status = status; return this; } public GetSettleWithdListBo setPageIndex(Integer pageIndex) { this.pageIndex = pageIndex; return this; } public GetSettleWithdListBo setPageSize(Integer pageSize) { this.pageSize = pageSize; return this; } public GetSettleWithdListBo setStartDate(Long startDate) { this.startDate = startDate; return this; } public GetSettleWithdListBo setEndDate(Long endDate) { this.endDate = endDate; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof GetSettleWithdListBo)) return false;  GetSettleWithdListBo other = (GetSettleWithdListBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  if (getStatus() != other.getStatus()) return false;  Object this$pageIndex = getPageIndex(), other$pageIndex = other.getPageIndex(); if ((this$pageIndex == null) ? (other$pageIndex != null) : !this$pageIndex.equals(other$pageIndex)) return false;  Object this$pageSize = getPageSize(), other$pageSize = other.getPageSize(); if ((this$pageSize == null) ? (other$pageSize != null) : !this$pageSize.equals(other$pageSize)) return false;  Object this$startDate = getStartDate(), other$startDate = other.getStartDate(); if ((this$startDate == null) ? (other$startDate != null) : !this$startDate.equals(other$startDate)) return false;  Object this$endDate = getEndDate(), other$endDate = other.getEndDate(); return !((this$endDate == null) ? (other$endDate != null) : !this$endDate.equals(other$endDate)); } protected boolean canEqual(Object other) { return other instanceof GetSettleWithdListBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); result = result * 59 + getStatus(); Object $pageIndex = getPageIndex(); result = result * 59 + (($pageIndex == null) ? 43 : $pageIndex.hashCode()); Object $pageSize = getPageSize(); result = result * 59 + (($pageSize == null) ? 43 : $pageSize.hashCode()); Object $startDate = getStartDate(); result = result * 59 + (($startDate == null) ? 43 : $startDate.hashCode()); Object $endDate = getEndDate(); return result * 59 + (($endDate == null) ? 43 : $endDate.hashCode()); } public String toString() { return "GetSettleWithdListBo(mchId=" + getMchId() + ", currency=" + getCurrency() + ", status=" + getStatus() + ", pageIndex=" + getPageIndex() + ", pageSize=" + getPageSize() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ")"; }







   public String getMchId() {
     return this.mchId;
   }



   public String getCurrency() {
     return this.currency;
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





