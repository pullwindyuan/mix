package org.hlpay.base.model;

import java.io.Serializable;

public class AccessInfo implements Serializable {
  private String appId;
  private String userNo;
  private String ip;
  private String org;
  private String role;
  private String bizId;
  private String cpk;
  private String accessKey;

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAccessKey() {
    return this.accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getUserNo() {
    return this.userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getOrg() {
    return this.org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getBizId() {
    return this.bizId;
  }

  public void setBizId(String bizId) {
    this.bizId = bizId;
  }

  public String getCpk() {
    return this.cpk;
  }

  public void setCpk(String cpk) {
    this.cpk = cpk;
  }
}

