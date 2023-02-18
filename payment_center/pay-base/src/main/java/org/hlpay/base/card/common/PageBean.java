package org.hlpay.base.card.common;

import java.util.ArrayList;
import java.util.List;


public class PageBean
{
  private int total;
  private List list = new ArrayList();



  public PageBean() {}


  public PageBean(int total, List list) {
    this.total = total;
    this.list = list;
  }

  public int getTotal() {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List getList() {
    return this.list;
  }

  public void setList(List list) {
    this.list = list;
  }
}





