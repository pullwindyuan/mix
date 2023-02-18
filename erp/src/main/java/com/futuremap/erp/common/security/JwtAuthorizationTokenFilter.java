package com.futuremap.erp.common.security;


import com.futuremap.erp.common.security.entity.CustomUserDetails;
import com.futuremap.erp.common.security.impl.UserDetailsServiceImpl;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Value("${security.token.header}")
    private String tokenHeader;
    @Value("${security.token.head}")
    private String tokenHead;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = this.tokenService.getUserNameFromToken(authToken);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (this.tokenService.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //User user = userDetails.getUser();
                    //authentication.setDetails(user.getId());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //RequestContextHolder.getRequestAttributes().setAttribute("userId", user.getId(), 0);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
