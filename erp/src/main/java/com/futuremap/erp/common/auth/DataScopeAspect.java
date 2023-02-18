package com.futuremap.erp.common.auth;

import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Aspect
//@Component
public class DataScopeAspect {
     @Autowired
    UserServiceImpl userService;

    @Pointcut("@annotation(dataScope)")
    public void pointCut(DataScope dataScope) {
    }
    /**
     * 定义拦截规则：拦截com.springboot.bcode.api包下面的所有类中，有@RequestLimit Annotation注解的方法
     * 。
     */
    @Around("pointCut(dataScope)")
    public Object method(ProceedingJoinPoint pjp, DataScope dataScope) throws Throwable {

//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod(); // 获取被拦截的方法
//        RequestLimit limt = method.getAnnotation(RequestLimit.class);
        // No request for limt,continue processing request
        if (dataScope == null) {
            return pjp.proceed();
        }
        //获取用户信息
        DataFilterTypeEnum structureEnum = DataFilterTypeEnum.getStructureEnum(dataScope.dataFilterType());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.selectByPhone(username);
        DataFilterThreadLocal.set(DataFilterMetaData.builder().filterType(structureEnum).userCode(user.getCode()).build());


        return pjp.proceed();
    }
}
