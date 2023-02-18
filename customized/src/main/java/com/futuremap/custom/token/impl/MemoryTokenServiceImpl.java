package com.futuremap.custom.token.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.futuremap.custom.entity.User;
import com.futuremap.custom.service.UserService;
import com.futuremap.custom.service.impl.UserServiceImpl;
import com.futuremap.custom.token.TokenService;

@Component
@Primary
public class MemoryTokenServiceImpl implements TokenService {
	protected final Logger logger = LoggerFactory.getLogger(MemoryTokenServiceImpl.class);

	//超时10分钟 暂时不限制
	private static Long VALIDITY_TIME_MS = 600000L;

    @Value("${security.token.secret}")
    private String secret;
    
    @Autowired
    private UserServiceImpl userService;

    @Override
    public String getUsernameFromToken(String token) {
    	//手机号作username
        String username;
        try {
            username =  Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                //.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .claim("userId", userService.selectByPhone(userDetails.getUsername()).getId())
                //.claim("companyId", userService.getCompanyIdFromName((userDetails.getUsername())))
                //.claim("role", userDetails.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
	public String getEmailFromToken(String token) {
    	String username;
        try {
            username =  String.valueOf(Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody().getSubject());
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    
    
    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getPhone())
                //.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .claim("userId", user.getId())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    
    public User getUserFromToken(String token) {
    	User user = new User();
        try {
        	String userId =  getUserIdFromToken(token);
        	user = userService.findById(userId);
        } catch (Exception e) {
        	user = null;
        }
        return user;
    }
    
    
    public String getUserIdFromToken(String token) {
    	String userId = null;
    	try {
    	userId  = (String) Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody().get("userId");
    	}catch (Exception e) {
    		logger.error("非法token");
    		userId = null;
        }
    	return userId;
    }
    
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return (getUsernameFromToken(token).equals(userDetails.getUsername()));
    }

    @Override
    public void refreshToken(String token) {
    }
}
