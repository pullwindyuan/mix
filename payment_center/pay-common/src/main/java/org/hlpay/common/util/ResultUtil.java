 package org.hlpay.common.util;

 import java.util.HashMap;
 import java.util.Map;
 import org.hlpay.common.entity.Result;
















 public class ResultUtil
 {
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

   public static Map createResultMap(boolean isSuccess, String errDes, Object resultData) {
     Map<Object, Object> result = new HashMap<>();
     result.put("isSuccess", Boolean.valueOf(isSuccess));
     result.put("errDes", errDes);

     result.put("bizResult", resultData);
     return result;
   }

   public static String createSuccessString(Object result) {
     return createResult(true, "业务处理成功", result).toString();
   }
   public static String createFailString(String errDes, Object result) {
     return createResult(false, errDes, result).toString();
   }

   public static Map createSuccessMap(Object result) {
     return createResultMap(true, "业务处理成功", result);
   }

   public static Map createFailMap(String errDes, Object result) {
     return createResultMap(false, errDes, result);
   }
 }





