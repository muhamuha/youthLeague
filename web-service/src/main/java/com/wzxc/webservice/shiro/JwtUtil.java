package com.wzxc.webservice.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    @Value("${jwt.secret}")
    private static String secret; // jwt的密钥
    @Value(("${jwt.principals}"))
    private static String principals; // jwt的主体
    public void setSecret(String secretSet) {
        secret = secretSet;
    }
    public void setPrincipals(String principalsSet) {
        principals = principalsSet;
    }

    /**
     * 校验 token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("principals", principals).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否过期
     * @param token
     * @return
     */
    public static boolean isExpired(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            Date expiredDate = jwt.getExpiresAt();
            if(new Date().after(expiredDate)){
                return true;
            }
            return false;
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return true;
        }
    }

    /**
     * 从 token中获取用户ID
     * @return token中包含的ID
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 生成 token
     * @param username 用户名
     * @param secret   用户的密码
     * @return token 加密的token
     */
    public static String sign(String userId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withHeader(map)
                    .withClaim("principals", principals)
                    .withSubject(userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000)) // 过期时间(八小时之后)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("error：{}", e);
            return null;
        }
    }
}
