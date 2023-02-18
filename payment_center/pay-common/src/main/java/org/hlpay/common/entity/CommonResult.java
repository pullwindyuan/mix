 package org.hlpay.common.entity;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import io.swagger.annotations.ApiModelProperty;
 import java.io.Serializable;
 import org.hlpay.common.enumm.ResultEnum;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public final class CommonResult<T>
   implements Serializable
 {
   @JsonIgnore
   private static final Logger logger = LoggerFactory.getLogger(Result.class);


   @JsonIgnore
   private static final long serialVersionUID = 1L;

   @ApiModelProperty("错误码")
   private Integer code = ResultEnum.ERROR
     .getCode();

   @ApiModelProperty("错误信息")
   private String msg = null;

   @ApiModelProperty("扩展信息")
   private String extra = null;

   @ApiModelProperty("返回结果实体")
   private T data = null;

   public CommonResult() {}

   private CommonResult(int code, String msg, T data) {
     this.code = Integer.valueOf(code);
     this.msg = msg;
     this.data = data;
   }

   private CommonResult(int code, String msg, String extra, T data) {
     this.code = Integer.valueOf(code);
     this.msg = msg;
     this.extra = extra;
     this.data = data;
   }

   public static <T> CommonResult<T> error(String msg) {
     logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR.getCode(), msg);
     return new CommonResult<>(ResultEnum.ERROR.getCode().intValue(), msg, null);
   }

   public static <T > CommonResult<T> error(ResultEnum resultEnum) {
     logger.debug("返回错误：code={}, msg={}", resultEnum.getCode(), resultEnum.getDesc());
     return new CommonResult<>(resultEnum.getCode().intValue(), resultEnum.getDesc(), null);
   }

   public static <T> CommonResult<T> error(int code, String msg) {
     logger.debug("返回错误：code={}, msg={}", Integer.valueOf(code), msg);
     return new CommonResult<>(code, msg, null);
   }

   public static <T > CommonResult<T> success(T data) {
     return new CommonResult<>(ResultEnum.SUCCESS.getCode().intValue(), "", data);
   }

   public static <T> CommonResult<T> success(String extra, T data) {
     return new CommonResult<>(ResultEnum.SUCCESS.getCode().intValue(), "", extra, data);
   }

   public Integer getCode() {
     return this.code;
   }

   public void setCode(Integer code) {
     this.code = code;
   }

   public String getMsg() {
     return this.msg;
   }

   public void setMsg(String msg) {
     this.msg = msg;
   }

   public T getData() {
     return this.data;
   }

   public void setData(T data) {
     this.data = data;
   }

   public String getExtra() {
     return this.extra;
   }

   public void setExtra(String extra) {
     this.extra = extra;
   }


   public String toString() {
     return "Result [code=" + this.code + ", msg=" + this.msg + ", extra=" + this.extra + ", data=" + this.data + "]";
   }
 }





