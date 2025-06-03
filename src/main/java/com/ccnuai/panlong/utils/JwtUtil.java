package com.ccnuai.panlong.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final long EXPIRE_Time = 12 * 60 * 1000;
    private static final String TOKEN_SECRET = "ccnupanlong123";

    /**
     * 生成token
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username,String userId){
        String token = null;
        try {
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_Time);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", username)
                    .withClaim("userId",userId)
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            System.out.println("认证通过：");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUserId(String token){

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("userId").asString();
    }


}
