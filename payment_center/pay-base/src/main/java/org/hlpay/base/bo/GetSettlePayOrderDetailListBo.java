 package org.hlpay.base.bo;


 public class GetSettlePayOrderDetailListBo extends AccessBo {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty(value = "卡ID", required = true)
   private String cardId;
   @ApiModelProperty(value = "对账确认状态：0-记账中，1-记账完成，待确认，2-已确认， 3-已完成， 4-完成现金结算， 3-全部", required = true)
   private int status;
   @ApiModelProperty(value = "起始页：从1开始", required = true)
   private Integer pageIndex;
   @ApiModelProperty(value = "每页条目数", required = true)
   private Integer pageSize;

   public GetSettlePayOrderDetailListBo setCardId(String cardId) { this.cardId = cardId; return this; } public GetSettlePayOrderDetailListBo setStatus(int status) { this.status = status; return this; } public GetSettlePayOrderDetailListBo setPageIndex(Integer pageIndex) { this.pageIndex = pageIndex; return this; } public GetSettlePayOrderDetailListBo setPageSize(Integer pageSize) { this.pageSize = pageSize; return this; } public GetSettlePayOrderDetailListBo setType(Integer type) { this.type = type; return this; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof GetSettlePayOrderDetailListBo)) return false;  GetSettlePayOrderDetailListBo other = (GetSettlePayOrderDetailListBo)o; if (!other.canEqual(this)) return false;  Object this$cardId = getCardId(), other$cardId = other.getCardId(); if ((this$cardId == null) ? (other$cardId != null) : !this$cardId.equals(other$cardId)) return false;  if (getStatus() != other.getStatus()) return false;  Object this$pageIndex = getPageIndex(), other$pageIndex = other.getPageIndex(); if ((this$pageIndex == null) ? (other$pageIndex != null) : !this$pageIndex.equals(other$pageIndex)) return false;  Object this$pageSize = getPageSize(), other$pageSize = other.getPageSize(); if ((this$pageSize == null) ? (other$pageSize != null) : !this$pageSize.equals(other$pageSize)) return false;  Object this$type = getType(), other$type = other.getType(); return !((this$type == null) ? (other$type != null) : !this$type.equals(other$type)); } protected boolean canEqual(Object other) { return other instanceof GetSettlePayOrderDetailListBo; } public int hashCode() { int PRIME = 59; result = 1; Object $cardId = getCardId(); result = result * 59 + (($cardId == null) ? 43 : $cardId.hashCode()); result = result * 59 + getStatus(); Object $pageIndex = getPageIndex(); result = result * 59 + (($pageIndex == null) ? 43 : $pageIndex.hashCode()); Object $pageSize = getPageSize(); result = result * 59 + (($pageSize == null) ? 43 : $pageSize.hashCode()); Object $type = getType(); return result * 59 + (($type == null) ? 43 : $type.hashCode()); } public String toString() { return "GetSettlePayOrderDetailListBo(cardId=" + getCardId() + ", status=" + getStatus() + ", pageIndex=" + getPageIndex() + ", pageSize=" + getPageSize() + ", type=" + getType() + ")"; }







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



   @ApiModelProperty(value = "类型0-分页查询 1-导出所有明细", required = true)
   private Integer type = Integer.valueOf(0); public Integer getType() { return this.type; }

 }

