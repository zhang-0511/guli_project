package com.tzl.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/** 生成JWT密文的工具类
 * @author zyf
 * @date 2021年05月18日 13:48
 */
public class JwtHelper {

    //token的过期时间
    private static final long tokenExpiration = 24*60*60*1000;
    //token的签名密钥
    private static final String tokenSignKey = "ABCDEFGHIGKLMNOPQRSTUVWXVZabcdefghigklmnopqrstuvwxvz01234567890!@#$%^&*()";

    /**
     * 生成token
     * @author zyf
     * @date 2021/5/18 13:51
     * @param userId 用户id
     * @param userName  用户名
     * @return java.lang.String
     */
    public static String createToken(String userId, String userName) {
        String token = Jwts.builder()
                .setSubject("guli-user")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 根据token得到用户id
     * @author zyf
     * @date 2021/5/18 13:52
     * @param token token
     * @return java.lang.Long
     */
    public static String getUserId(String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }

        Claims claims;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            claims = claimsJws.getBody();

        }catch (ExpiredJwtException e){
            claims = e.getClaims();
        }
        return (String)claims.get("userId");
    }

    /**
     * 根据token得到用户名
     * @author zyf
     * @date 2021/5/18 13:52
     * @param token token
     * @return java.lang.Long
     */
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)){
            return "";
        }
        Claims claims;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            claims = claimsJws.getBody();

        }catch (ExpiredJwtException e){
            claims = e.getClaims();
        }
        return (String)claims.get("userName");
    }


    public static void main(String[] args) {
        String token = JwtHelper.createToken("1", "55");
        System.out.println(token);
        System.out.println(JwtHelper.getUserName(token));
    }
}
