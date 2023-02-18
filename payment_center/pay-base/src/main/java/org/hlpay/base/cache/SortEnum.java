package org.hlpay.base.cache;


public enum SortEnum
{
  ASC(Integer.valueOf(0), "ASC", "升序排列"),

  DESC(Integer.valueOf(1), "DESC", "降序排列");

  private Integer code;
  private String type;
  private String desc;

  SortEnum(Integer code, String type, String desc) {
    this.code = code;
    this.type = type;
    this.desc = desc;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getType() {
    return this.type;
  }

  public String getDesc() {
    return this.desc;
  }
}
