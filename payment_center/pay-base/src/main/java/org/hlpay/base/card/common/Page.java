package org.hlpay.base.card.common;

import java.io.Serializable;
import java.util.List;







public class Page<T>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int start;
  private int end;
  private int total;
  private List<T> list;

  public Page() {}

  public Page(int start, int end, int total, List<T> list) {
    this.start = start;
    this.end = end;
    this.total = total;
    this.list = list;
  }

  public int getStart() {
    return this.start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return this.end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public int getTotal() {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}





