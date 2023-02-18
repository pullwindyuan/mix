package com.futuremap.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.base.CustomResponseContent;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

public class CustomResponseReturnValueHandler implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {

    private static final ObjectMapper objectMapper;
    
    
    private static Integer SUCCESS_CODE = 200;
    

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

    @Override
    public boolean isAsyncReturnValue(Object o, MethodParameter methodParameter) {
        return supportsReturnType(methodParameter);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
    	
    	//判断这个方法对应的类是否有@ResponseBody注解
        return (AnnotationUtils.findAnnotation(returnType.getContainingClass(), CustomResponse.class) != null ||
                //判断这个方法上是否有@ResponseBody注解
                returnType.getMethodAnnotation(CustomResponse.class) != null);
        //return methodParameter.hasMethodAnnotation(CustomResponse.class);
    }

    @Override
    public void handleReturnValue(Object data, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        modelAndViewContainer.setRequestHandled(true);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String message = "操作成功";
		String status = "success";
        
		if (!SUCCESS_CODE.equals(response.getStatus())) {
			try {
				//toJSONString(obj,SerializerFeature.WriteMapNullValue); 
				String responeString = JSONObject.toJSONString(data);
				JSONObject jsonObj =  JSONObject.parseObject(responeString);
				JSONObject body = (JSONObject) jsonObj.get("body");
				message = body.getString("message");
			}catch (Exception e) {
				message = "操作失败";
			}
            status = "fail";
		}
		
		

        if ((data instanceof Boolean) && !((Boolean) data)) {
            message = "操作失败";
            status = "fail";
        }
        
        Object returnObj  = new Object();
        String responeString = JSONObject.toJSONString(data);
        try {
        	JSONObject jsonObj =  JSONObject.parseObject(responeString);
        	returnObj = jsonObj;
        }catch (Exception e) {
        	JSONArray jsonObj = JSONObject.parseArray(responeString);
        	returnObj = jsonObj;
		}
		
		
        CustomResponseContent responseContent = CustomResponseContent.builder()
                                                                     .code(response.getStatus())
                                                                     .status(response.getStatus())
                                                                     .message(message)
				.data(SUCCESS_CODE.equals(response.getStatus()) ? returnObj : "")
                                                                     .build();

        response.getWriter()
                .write(objectMapper.writeValueAsString(responseContent));

    }
}
