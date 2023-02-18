 package org.hlpay.base.model;

 public class SysUser
 {
   private String userId;
   private String loginAccount;
   private String mobliePhone;
   private String email;
   private String loginPwd;
   private String payPwd;
   private Character userType;
   private String tdOpenId;
   private String userRegion;
   private String registerTime;
   private String lastLoginTime;
   private String createTime;
   private String roleId;
   private String merchantCode;
   private int showCount;
   private Character is_delete;
   private Character flag;
   private Character status;

   public SysUser() {}

   public SysUser(String userId, String loginAccount, String mobliePhone, String email, String loginPwd, String payPwd, Character userType, String tdOpenId, String userRegion, String registerTime, String lastLoginTime, String createTime, String roleId, String merchantCode, int showCount, Character is_delete, Character flag, Character status) {
     this.userId = userId;
     this.loginAccount = loginAccount;
     this.mobliePhone = mobliePhone;
     this.email = email;
     this.loginPwd = loginPwd;
     this.payPwd = payPwd;
     this.userType = userType;
     this.tdOpenId = tdOpenId;
     this.userRegion = userRegion;
     this.registerTime = registerTime;
     this.lastLoginTime = lastLoginTime;
     this.createTime = createTime;
     this.roleId = roleId;
     this.merchantCode = merchantCode;
     this.showCount = showCount;
     this.is_delete = is_delete;
     this.flag = flag;
     this.status = status;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setUserId(String userId) {
     this.userId = userId;
   }

   public String getLoginAccount() {
     return this.loginAccount;
   }

   public void setLoginAccount(String loginAccount) {
     this.loginAccount = loginAccount;
   }

   public String getMobliePhone() {
     return this.mobliePhone;
   }

   public void setMobliePhone(String mobliePhone) {
     this.mobliePhone = mobliePhone;
   }

   public String getEmail() {
     return this.email;
   }

   public void setEmail(String email) {
     this.email = email;
   }

   public String getLoginPwd() {
     return this.loginPwd;
   }

   public void setLoginPwd(String loginPwd) {
     this.loginPwd = loginPwd;
   }

   public String getPayPwd() {
     return this.payPwd;
   }

   public void setPayPwd(String payPwd) {
     this.payPwd = payPwd;
   }

   public Character getUserType() {
     return this.userType;
   }

   public void setUserType(Character userType) {
     this.userType = userType;
   }

   public String getTdOpenId() {
     return this.tdOpenId;
   }

   public void setTdOpenId(String tdOpenId) {
     this.tdOpenId = tdOpenId;
   }

   public String getUserRegion() {
     return this.userRegion;
   }

   public void setUserRegion(String userRegion) {
     this.userRegion = userRegion;
   }

   public String getRegisterTime() {
     return this.registerTime;
   }

   public void setRegisterTime(String registerTime) {
     this.registerTime = registerTime;
   }

   public String getLastLoginTime() {
     return this.lastLoginTime;
   }

   public void setLastLoginTime(String lastLoginTime) {
     this.lastLoginTime = lastLoginTime;
   }

   public String getCreateTime() {
     return this.createTime;
   }

   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }

   public String getRoleId() {
     return this.roleId;
   }

   public void setRoleId(String roleId) {
     this.roleId = roleId;
   }

   public String getMerchantCode() {
     return this.merchantCode;
   }

   public void setMerchantCode(String merchantCode) {
     this.merchantCode = merchantCode;
   }

   public int getShowCount() {
     return this.showCount;
   }

   public void setShowCount(int showCount) {
     this.showCount = showCount;
   }

   public Character getIs_delete() {
     return this.is_delete;
   }

   public void setIs_delete(Character is_delete) {
     this.is_delete = is_delete;
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
     return "SysUser [userId=" + this.userId + ", loginAccount=" + this.loginAccount + ", mobliePhone=" + this.mobliePhone + ", email=" + this.email + ", loginPwd=" + this.loginPwd + ", payPwd=" + this.payPwd + ", userType=" + this.userType + ", tdOpenId=" + this.tdOpenId + ", userRegion=" + this.userRegion + ", registerTime=" + this.registerTime + ", lastLoginTime=" + this.lastLoginTime + ", createTime=" + this.createTime + ", roleId=" + this.roleId + ", merchantCode=" + this.merchantCode + ", showCount=" + this.showCount + ", is_delete=" + this.is_delete + ", flag=" + this.flag + ", status=" + this.status + "]";
   }
 }





