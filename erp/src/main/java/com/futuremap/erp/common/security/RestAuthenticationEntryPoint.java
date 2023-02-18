package com.futuremap.erp.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        log.error("用户访问资源没有携带正确的token",e);
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e==null?"用户访问资源没有携带正确的token":e.getMessage());
    }
}
