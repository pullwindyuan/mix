package com.futuremap.erp.common.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String getUserNameFromToken(String token);

    String generateToken(UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

    String refreshToken(String token);
    
}
