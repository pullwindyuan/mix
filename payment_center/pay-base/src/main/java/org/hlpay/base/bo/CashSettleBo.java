package org.hlpay.base.bo;

import java.io.Serializable;
import java.util.List;

public class CashSettleBo extends AccessBo implements Serializable {
  @ApiModelProperty(value = "确认列表", required = true)
  List<CashSettleItem> cashSettleList;

  public String toString() { return "CashSettleBo(cashSettleList=" + getCashSettleList() + ")"; } public int hashCode() { int PRIME = 59; result = 1; Object<CashSettleItem> $cashSettleList = (Object<CashSettleItem>)getCashSettleList(); return result * 59 + (($cashSettleList == null) ? 43 : $cashSettleList.hashCode()); } protected boolean canEqual(Object other) { return other instanceof CashSettleBo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof CashSettleBo)) return false;  CashSettleBo other = (CashSettleBo)o; if (!other.canEqual(this)) return false;  Object<CashSettleItem> this$cashSettleList = (Object<CashSettleItem>)getCashSettleList(), other$cashSettleList = (Object<CashSettleItem>)other.getCashSettleList(); return !((this$cashSettleList == null) ? (other$cashSettleList != null) : !this$cashSettleList.equals(other$cashSettleList)); } public CashSettleBo setCashSettleList(List<CashSettleItem> cashSettleList) { this.cashSettleList = cashSettleList; return this; }


  public List<CashSettleItem> getCashSettleList() {
    return this.cashSettleList;
  }
}
