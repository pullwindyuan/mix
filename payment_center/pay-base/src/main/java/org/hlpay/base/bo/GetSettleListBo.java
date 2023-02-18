 package org.hlpay.base.bo;


 public class GetSettleListBo extends AccessBo {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "商户ID（组织ID：集团ID、下属企业ID、门店ID等）", required = true)
   private String mchId;
   @ApiModelProperty(value = "商户类型（\t *\t\tPLATFORM(\"100\", \"1\", \"平台\"),\n\t *\n\t *     PLATFORM_AGENCY(\"200\", \"2\", \"平台代理：私域集团\"),\n\t *\n\t *     MCH(\"300\", \"3\", \"商户：下级企业\"),\n\t *\n\t *     MCH_VIRTUAL(\"400\", \"3\", \"虚拟商户\"：品牌),\n\t *\n\t *     MCH_BRANCH(\"500\", \"4\", \"商户分支机构：门店\"),）", required = true)
   private String type;
   @ApiModelProperty(value = "商户名称：用于筛选", required = false)
   private String name;
   @ApiModelProperty(value = "币种，如果不传，默认为CNY", required = false)
   private String currency;

   public GetSettleListBo setMchId(String mchId) { this.mchId = mchId; return this; } @ApiModelProperty(value = "对账确认状态：0-记账中，1-记账完成，待确认，2-已确认，3-已完成， 4-完成现金结算， 99:全部", required = true) private int status; @ApiModelProperty(value = "起始页：从1开始", required = true) private Integer pageIndex; @ApiModelProperty(value = "每页条目数", required = true) private Integer pageSize; @ApiModelProperty(value = "起始时间", required = true) private Long startDate; @ApiModelProperty(value = "结束时间", required = true) private Long endDate; public GetSettleListBo setType(String type) { this.type = type; return this; } public GetSettleListBo setName(String name) { this.name = name; return this; } public GetSettleListBo setCurrency(String currency) { this.currency = currency; return this; } public GetSettleListBo setStatus(int status) { this.status = status; return this; } public GetSettleListBo setPageIndex(Integer pageIndex) { this.pageIndex = pageIndex; return this; } public GetSettleListBo setPageSize(Integer pageSize) { this.pageSize = pageSize; return this; } public GetSettleListBo setStartDate(Long startDate) { this.startDate = startDate; return this; } public GetSettleListBo setEndDate(Long endDate) { this.endDate = endDate; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof GetSettleListBo)) return false;  GetSettleListBo other = (GetSettleListBo)o; if (!other.canEqual(this)) return false;  Object this$mchId = getMchId(), other$mchId = other.getMchId(); if ((this$mchId == null) ? (other$mchId != null) : !this$mchId.equals(other$mchId)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$currency = getCurrency(), other$currency = other.getCurrency(); if ((this$currency == null) ? (other$currency != null) : !this$currency.equals(other$currency)) return false;  if (getStatus() != other.getStatus()) return false;  Object this$pageIndex = getPageIndex(), other$pageIndex = other.getPageIndex(); if ((this$pageIndex == null) ? (other$pageIndex != null) : !this$pageIndex.equals(other$pageIndex)) return false;  Object this$pageSize = getPageSize(), other$pageSize = other.getPageSize(); if ((this$pageSize == null) ? (other$pageSize != null) : !this$pageSize.equals(other$pageSize)) return false;  Object this$startDate = getStartDate(), other$startDate = other.getStartDate(); if ((this$startDate == null) ? (other$startDate != null) : !this$startDate.equals(other$startDate)) return false;  Object this$endDate = getEndDate(), other$endDate = other.getEndDate(); return !((this$endDate == null) ? (other$endDate != null) : !this$endDate.equals(other$endDate)); } protected boolean canEqual(Object other) { return other instanceof GetSettleListBo; } public int hashCode() { int PRIME = 59; result = 1; Object $mchId = getMchId(); result = result * 59 + (($mchId == null) ? 43 : $mchId.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $currency = getCurrency(); result = result * 59 + (($currency == null) ? 43 : $currency.hashCode()); result = result * 59 + getStatus(); Object $pageIndex = getPageIndex(); result = result * 59 + (($pageIndex == null) ? 43 : $pageIndex.hashCode()); Object $pageSize = getPageSize(); result = result * 59 + (($pageSize == null) ? 43 : $pageSize.hashCode()); Object $startDate = getStartDate(); result = result * 59 + (($startDate == null) ? 43 : $startDate.hashCode()); Object $endDate = getEndDate(); return result * 59 + (($endDate == null) ? 43 : $endDate.hashCode()); } public String toString() { return "GetSettleListBo(mchId=" + getMchId() + ", type=" + getType() + ", name=" + getName() + ", currency=" + getCurrency() + ", status=" + getStatus() + ", pageIndex=" + getPageIndex() + ", pageSize=" + getPageSize() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ")"; }







   public String getMchId() {
     return this.mchId;
   }




















   public String getType() {
     return this.type;
   }



   public String getName() {
     return this.name;
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

