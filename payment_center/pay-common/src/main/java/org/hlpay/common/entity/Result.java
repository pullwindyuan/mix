 package org.hlpay.common.entity;

 import com.alibaba.fastjson.JSONObject;
 import java.io.Serializable;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.common.util.JsonUtil;

 public class Result
   implements Serializable
 {
   private boolean isSuccess = false;
   private String errDes;
   private Object bizResult;

   public boolean isSuccess() {
     return this.isSuccess;
   }

   public void setSuccess(boolean success) {
     this.isSuccess = success;
   }

   public String getErrDes() {
     return this.errDes;
   }

   public void setErrDes(String errDes) {
     this.errDes = errDes;
   }

   public Object getBizResult() {
     return this.bizResult;
   }

   public void setBizResult(Object bizResult) {
     this.bizResult = bizResult;
   }


   public String toString() {
     JSONObject jsonObject = new JSONObject();
     jsonObject.put("isSuccess", Boolean.valueOf(isSuccess()));
     if (StringUtils.isNotBlank(this.errDes)) jsonObject.put("errDes", getErrDes());
     if (this.bizResult != null) jsonObject.put("bizResult", JsonUtil.getJSONObjectFromObj(this.bizResult));
     return jsonObject.toJSONString();
   }

   public static Result createSuccessResult(Object result) {
     return createResult(true, "业务处理成功", result);
   }

   public static Result createFailResult(String errDes, Object result) {
     return createResult(false, errDes, result);
   }

   public static Result createResult(boolean isSuccess, String errDes, Object resultData) {
     Result result = new Result();
     result.setSuccess(isSuccess);
     result.setErrDes(errDes);
     result.setBizResult(resultData);
     return result;
   }

   public static Result createResultMap(boolean isSuccess, String errDes, Object resultData) {
     Result result = new Result();
     result.setSuccess(isSuccess);
     result.setErrDes(errDes);

     result.setBizResult(resultData);
     return result;
   }

   public static String createSuccessString(Object result) {
     return createResult(true, "业务处理成功", result).toString();
   }
   public static String createFailString(String errDes, Object result) {
     return createResult(false, errDes, result).toString();
   }

   public static Result createSuccessMap(Object result) {
     return createResultMap(true, "业务处理成功", result);
   }

   public static Result createFailMap(String errDes, Object result) {
     return createResultMap(false, errDes, result);
   }
 }
