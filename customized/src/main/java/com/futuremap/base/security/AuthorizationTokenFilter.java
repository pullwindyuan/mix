package com.futuremap.base.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import com.futuremap.custom.token.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationTokenFilter extends OncePerRequestFilter {

    @Value("${security.token.header}")
    private String tokenHeader;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private com.futuremap.custom.service.impl.UserServiceImpl userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    	/*
    	String authToken = request.getHeader(tokenHeader);
        String username = this.tokenService.getUsernameFromToken(authToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.tokenService.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                User user = userService.selectByPhone(username);
                authentication.setDetails(user.getId());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                RequestContextHolder.getRequestAttributes().setAttribute("userId", user.getId(), 0);
            }
        }
        */
        chain.doFilter(request, response);
    }
}
