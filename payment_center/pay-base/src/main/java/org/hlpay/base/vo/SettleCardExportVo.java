 package org.hlpay.base.vo;
 public class SettleCardExportVo {
   @Excel(name = "订单日期")
   @ApiModelProperty("有效起始时间（闭）")
   private String validStartTimeStr;
   @Excel(name = "企业名称")
   @ApiModelProperty("持有者名称")
   private String userName;
   @Excel(name = "商品金额(¥)")
   @ApiModelProperty("应收金额")
   private String via;

   public void setValidStartTimeStr(String validStartTimeStr) { this.validStartTimeStr = validStartTimeStr; } @Excel(name = "优惠金额(¥)") @ApiModelProperty("优惠金额") private String cfa; @Excel(name = "支付金额(¥)") @ApiModelProperty("实际入账金额") private String tia; @Excel(name = "服务费(¥)") @ApiModelProperty("服务费") private String pda; @Excel(name = "结算金额(¥)") @ApiModelProperty("实际提现金额") private String wda; public void setUserName(String userName) { this.userName = userName; } public void setVia(String via) { this.via = via; } public void setCfa(String cfa) { this.cfa = cfa; } public void setTia(String tia) { this.tia = tia; } public void setPda(String pda) { this.pda = pda; } public void setWda(String wda) { this.wda = wda; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof SettleCardExportVo)) return false;  SettleCardExportVo other = (SettleCardExportVo)o; if (!other.canEqual(this)) return false;  Object this$validStartTimeStr = getValidStartTimeStr(), other$validStartTimeStr = other.getValidStartTimeStr(); if ((this$validStartTimeStr == null) ? (other$validStartTimeStr != null) : !this$validStartTimeStr.equals(other$validStartTimeStr)) return false;  Object this$userName = getUserName(), other$userName = other.getUserName(); if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName)) return false;  Object this$via = getVia(), other$via = other.getVia(); if ((this$via == null) ? (other$via != null) : !this$via.equals(other$via)) return false;  Object this$cfa = getCfa(), other$cfa = other.getCfa(); if ((this$cfa == null) ? (other$cfa != null) : !this$cfa.equals(other$cfa)) return false;  Object this$tia = getTia(), other$tia = other.getTia(); if ((this$tia == null) ? (other$tia != null) : !this$tia.equals(other$tia)) return false;  Object this$pda = getPda(), other$pda = other.getPda(); if ((this$pda == null) ? (other$pda != null) : !this$pda.equals(other$pda)) return false;  Object this$wda = getWda(), other$wda = other.getWda(); return !((this$wda == null) ? (other$wda != null) : !this$wda.equals(other$wda)); } protected boolean canEqual(Object other) { return other instanceof SettleCardExportVo; } public int hashCode() { int PRIME = 59; result = 1; Object $validStartTimeStr = getValidStartTimeStr(); result = result * 59 + (($validStartTimeStr == null) ? 43 : $validStartTimeStr.hashCode()); Object $userName = getUserName(); result = result * 59 + (($userName == null) ? 43 : $userName.hashCode()); Object $via = getVia(); result = result * 59 + (($via == null) ? 43 : $via.hashCode()); Object $cfa = getCfa(); result = result * 59 + (($cfa == null) ? 43 : $cfa.hashCode()); Object $tia = getTia(); result = result * 59 + (($tia == null) ? 43 : $tia.hashCode()); Object $pda = getPda(); result = result * 59 + (($pda == null) ? 43 : $pda.hashCode()); Object $wda = getWda(); return result * 59 + (($wda == null) ? 43 : $wda.hashCode()); } public String toString() { return "SettleCardExportVo(validStartTimeStr=" + getValidStartTimeStr() + ", userName=" + getUserName() + ", via=" + getVia() + ", cfa=" + getCfa() + ", tia=" + getTia() + ", pda=" + getPda() + ", wda=" + getWda() + ")"; }

   public String getValidStartTimeStr() {
     return this.validStartTimeStr;
   }

   public String getUserName() {
     return this.userName;
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

   public String getPda() {
     return this.pda;
   }

   public String getWda() {
     return this.wda;
   }
 }

