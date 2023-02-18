 package org.hlpay.base.vo;
 public class SettlePayOrderExportVo {
   @Excel(name = "订单日期")
   @ApiModelProperty("更新时间")
   private String updateTimeStr;
   @Excel(name = "订单编号")
   @ApiModelProperty("商户订单号")
   private String mchOrderNo;
   @Excel(name = "用户账号")
   @ApiModelProperty("用户编号")
   private String userNo;

   public void setUpdateTimeStr(String updateTimeStr) { this.updateTimeStr = updateTimeStr; } @Excel(name = "商品金额(¥)") @ApiModelProperty("应收金额") private String via; @Excel(name = "优惠金额(¥)") @ApiModelProperty("优惠金额") private String cfa; @Excel(name = "支付金额(¥)") @ApiModelProperty("实际入账金额") private String tia; public void setMchOrderNo(String mchOrderNo) { this.mchOrderNo = mchOrderNo; } public void setUserNo(String userNo) { this.userNo = userNo; } public void setVia(String via) { this.via = via; } public void setCfa(String cfa) { this.cfa = cfa; } public void setTia(String tia) { this.tia = tia; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SettlePayOrderExportVo)) return false;  SettlePayOrderExportVo other = (SettlePayOrderExportVo)o; if (!other.canEqual(this)) return false;  Object this$updateTimeStr = getUpdateTimeStr(), other$updateTimeStr = other.getUpdateTimeStr(); if ((this$updateTimeStr == null) ? (other$updateTimeStr != null) : !this$updateTimeStr.equals(other$updateTimeStr)) return false;  Object this$mchOrderNo = getMchOrderNo(), other$mchOrderNo = other.getMchOrderNo(); if ((this$mchOrderNo == null) ? (other$mchOrderNo != null) : !this$mchOrderNo.equals(other$mchOrderNo)) return false;  Object this$userNo = getUserNo(), other$userNo = other.getUserNo(); if ((this$userNo == null) ? (other$userNo != null) : !this$userNo.equals(other$userNo)) return false;  Object this$via = getVia(), other$via = other.getVia(); if ((this$via == null) ? (other$via != null) : !this$via.equals(other$via)) return false;  Object this$cfa = getCfa(), other$cfa = other.getCfa(); if ((this$cfa == null) ? (other$cfa != null) : !this$cfa.equals(other$cfa)) return false;  Object this$tia = getTia(), other$tia = other.getTia(); return !((this$tia == null) ? (other$tia != null) : !this$tia.equals(other$tia)); } protected boolean canEqual(Object other) { return other instanceof SettlePayOrderExportVo; } public int hashCode() { int PRIME = 59; result = 1; Object $updateTimeStr = getUpdateTimeStr(); result = result * 59 + (($updateTimeStr == null) ? 43 : $updateTimeStr.hashCode()); Object $mchOrderNo = getMchOrderNo(); result = result * 59 + (($mchOrderNo == null) ? 43 : $mchOrderNo.hashCode()); Object $userNo = getUserNo(); result = result * 59 + (($userNo == null) ? 43 : $userNo.hashCode()); Object $via = getVia(); result = result * 59 + (($via == null) ? 43 : $via.hashCode()); Object $cfa = getCfa(); result = result * 59 + (($cfa == null) ? 43 : $cfa.hashCode()); Object $tia = getTia(); return result * 59 + (($tia == null) ? 43 : $tia.hashCode()); } public String toString() { return "SettlePayOrderExportVo(updateTimeStr=" + getUpdateTimeStr() + ", mchOrderNo=" + getMchOrderNo() + ", userNo=" + getUserNo() + ", via=" + getVia() + ", cfa=" + getCfa() + ", tia=" + getTia() + ")"; }

   public String getUpdateTimeStr() {
     return this.updateTimeStr;
   }

   public String getMchOrderNo() {
     return this.mchOrderNo;
   }

   public String getUserNo() {
     return this.userNo;
   }

   public String getVia() {
     return this.via;
   }

   public String getCfa() {
     return this.cfa;
   }

   public String getTia() {
     return this.tia;
   }
 }





