package org.hlpay.common.enumm;

public enum SettleStatusEnum
{
  DELETE("-1", Integer.valueOf(-1), "移除"),

  ING("0", Integer.valueOf(0), "记账中"),

  SETTLED("1", Integer.valueOf(1), "记账完成，待确认"),

  CONFIRMED("2", Integer.valueOf(2), "已确认"),

  COMPLETED("3", Integer.valueOf(3), "已完成"),

  CASH_COMPLETED("4", Integer.valueOf(4), "现金结算完成"),

  ALL("99", Integer.valueOf(99), "全部");

  private String type;
  private Integer code;
  private String desc;

  SettleStatusEnum(String type, Integer code, String message) {
    this.type = type;
    this.code = code;
    this.desc = message;
  }


  public String getType() {
    return this.type;
  }


  public Integer getCode() {
    return this.code;
  }

  public String getDesc() {
    return this.desc;
  }
}





