//package com.futuremap.erp.common.security.impl;
//
//import com.futuremap.erp.common.security.JwtTokenService;
//import com.futuremap.erp.common.security.entity.CustomUserDetails;
//import com.futuremap.erp.common.service.RedisService;
//import com.futuremap.erp.module.auth.entity.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class RedisTokenServiceImpl implements JwtTokenService {
//    private static final String CLAIM_KEY_USERNAME = "sub";
//    private static final String CLAIM_KEY_CREATED = "created";
//
//    @Autowired
//    RedisService redisService;
//
//    /**
//     * 密钥
//     */
//    @Value("${security.token.secret}")
//    private String secret;
//    /**
//     * //有效期
//     */
//    @Value("${security.token.expiration}")
//    private Long expiration;
//
//
//    @Value("${security.token.head}")
//    private String tokenHead;
//
//
//
//    /**
//     * 将token存储到redis
//     */
//    public void setExpire(String key, String val, long time) {
//        redisService.setExpire(key, val, time);
//    }
//
//    /**
//     * 移除
//     */
//    public void del(String key) {
//        redisService.del(key);
//    }
//
//    /**
//     * 判断是否有效
//     * @param authToken
//     * @return
//     */
//    public Boolean validateToken(String authToken) {
//        Object o = redisService.get(authToken);
//        if(null != o){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 从数据声明生成令牌
//     *
//     * @param claims 数据声明
//     * @return 令牌
//     */
//    private String generateToken(Map<String, Object> claims) {
//        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
//        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//
//    /**
//     * 从令牌中获取数据声明
//     *
//     * @param token 令牌
//     * @return 数据声明
//     */
//    private Claims getClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        } catch (Exception e) {
//            claims = null;
//        }
//        return claims;
//    }
//
//    /**
//     * 生成令牌
//     *
//     * @param userDetails 用户
//     * @return 令牌
//     */
//    @Override
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>(2);
//        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims);
//    }
//
//
//
//    /**
//     * 从令牌中获取用户名
//     *
//     * @param token 令牌
//     * @return 用户名
//     */
//    @Override
//    public String getUserNameFromToken(String token) {
//        String username;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
//    }
//
//    /**
//     * 判断令牌是否过期
//     *
//     * @param token 令牌
//     * @return 是否过期
//     */
//    public Boolean isTokenExpired(String token) {
//        try {
//            Claims claims = getClaimsFromToken(token);
//            Date expiration = claims.getExpiration();
//            return expiration.before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * 刷新令牌
//     *
//     * @param token 原令牌
//     * @return 新令牌
//     */
//    @Override
//    public String refreshToken(String token) {
//        String refreshedToken;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims);
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }
//
//
//
//    /**
//     * 验证令牌
//     *
//     * @param token       令牌
//     * @param userDetails 用户
//     * @return 是否有效
//     */
//    @Override
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = getUserNameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}
