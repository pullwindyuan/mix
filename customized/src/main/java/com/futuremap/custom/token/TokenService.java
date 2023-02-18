package com.futuremap.custom.token;

import org.springframework.security.core.userdetails.UserDetails;

import com.futuremap.custom.entity.User;

public interface TokenService {

    String getUsernameFromToken(String token);

    String generateToken(UserDetails userDetails);
    
    String generateToken(User user);

    boolean validateToken(String token, UserDetails userDetails);

    void refreshToken(String token);
    
    String getEmailFromToken(String token);
}
