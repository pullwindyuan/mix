 package org.hlpay.base.model;

 public class SysProxyUser
 {
   private String proxyId;
   private String proxyNumber;
   private Character proxyType;
   private String proxyName;
   private String companyName;
   private String companyPhone;
   private String officeAddr;
   private String contact;
   private String contactPhone;
   private String contactEmail;
   private String regAttachment;
   private String userId;
   private Character isDelete;
   private Character flag;
   private Character status;

   public SysProxyUser() {}

   public SysProxyUser(String proxyId, String proxyNumber, Character proxyType, String proxyName, String companyName, String companyPhone, String officeAddr, String contact, String contactPhone, String contactEmail, String regAttachment, String userId, Character isDelete, Character flag, Character status) {
     this.proxyId = proxyId;
     this.proxyNumber = proxyNumber;
     this.proxyType = proxyType;
     this.proxyName = proxyName;
     this.companyName = companyName;
     this.companyPhone = companyPhone;
     this.officeAddr = officeAddr;
     this.contact = contact;
     this.contactPhone = contactPhone;
     this.contactEmail = contactEmail;
     this.regAttachment = regAttachment;
     this.userId = userId;
     this.isDelete = isDelete;
     this.flag = flag;
     this.status = status;
   }

   public String getProxyId() {
     return this.proxyId;
   }

   public void setProxyId(String proxyId) {
     this.proxyId = proxyId;
   }

   public String getProxyNumber() {
     return this.proxyNumber;
   }

   public void setProxyNumber(String proxyNumber) {
     this.proxyNumber = proxyNumber;
   }

   public Character getProxyType() {
     return this.proxyType;
   }

   public void setProxyType(Character proxyType) {
     this.proxyType = proxyType;
   }

   public String getProxyName() {
     return this.proxyName;
   }

   public void setProxyName(String proxyName) {
     this.proxyName = proxyName;
   }

   public String getCompanyName() {
     return this.companyName;
   }

   public void setCompanyName(String companyName) {
     this.companyName = companyName;
   }

   public String getCompanyPhone() {
     return this.companyPhone;
   }

   public void setCompanyPhone(String companyPhone) {
     this.companyPhone = companyPhone;
   }

   public String getOfficeAddr() {
     return this.officeAddr;
   }

   public void setOfficeAddr(String officeAddr) {
     this.officeAddr = officeAddr;
   }

   public String getContact() {
     return this.contact;
   }

   public void setContact(String contact) {
     this.contact = contact;
   }

   public String getContactPhone() {
     return this.contactPhone;
   }

   public void setContactPhone(String contactPhone) {
     this.contactPhone = contactPhone;
   }

   public String getContactEmail() {
     return this.contactEmail;
   }

   public void setContactEmail(String contactEmail) {
     this.contactEmail = contactEmail;
   }

   public String getRegAttachment() {
     return this.regAttachment;
   }

   public void setRegAttachment(String regAttachment) {
     this.regAttachment = regAttachment;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public Character getIsDelete() {
     return this.isDelete;
   }

   public void setIsDelete(Character isDelete) {
     this.isDelete = isDelete;
   }

   public Character getFlag() {
     return this.flag;
   }

   public void setFlag(Character flag) {
     this.flag = flag;
   }

   public Character getStatus() {
     return this.status;
   }

   public void setStatus(Character status) {
     this.status = status;
   }


   public String toString() {
     return "SysProxyUser [proxyId=" + this.proxyId + ", proxyNumber=" + this.proxyNumber + ", proxyType=" + this.proxyType + ", proxyName=" + this.proxyName + ", companyName=" + this.companyName + ", companyPhone=" + this.companyPhone + ", officeAddr=" + this.officeAddr + ", contact=" + this.contact + ", contactPhone=" + this.contactPhone + ", contactEmail=" + this.contactEmail + ", regAttachment=" + this.regAttachment + ", userId=" + this.userId + ", isDelete=" + this.isDelete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }





