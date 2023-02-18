package org.hlpay.base.mq;

import javax.annotation.Resource;

@Resource
public enum QueueConfig
{
  NORMAL(0, "普通队列"),



  WITH_DELAY(1, "带延时消息队列"),



  WITH_CALLBACK(2, "带回调消息队列"),



  WITH_DELAY_CALLBACK(3, "带延时和回调消息队列"),


  WITH_FANOUT(4, "广播模式");

  private int type;

  private String name;

  public int getType() {
    return this.type;
  }

  public void setType(int status) {
    this.type = status;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  QueueConfig(int type, String name) {
    this.type = type;
    this.name = name;
  }
}
