package com.wzxc.zzdpush.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

/**
 * shiro 密码校验器
 */
public class MyCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) authenticationToken;
        // 获得用户输入的密码
        String inPassword = new String(utoken.getPassword());
        String username = utoken.getUsername();
        // 获得数据库中的密码
        String dbPassword = (String) authenticationInfo.getCredentials();
        SimpleAuthenticationInfo saInfo = (SimpleAuthenticationInfo) authenticationInfo;
        ByteSource salt = saInfo.getCredentialsSalt();
        // 校验密码
        inPassword = this.encodeMD5InSalt(salt, inPassword);
        // 进行密码的比对
        boolean flag = Objects.equals(inPassword, dbPassword);
        if(!flag){
            throw new UnknownAccountException("用户名或者密码错误");
        }
        return true;
    }

    private String encodeMD5InSalt(ByteSource salt, String password){
        String hashAlgorithmName = "MD5"; // 加密方式
        int hashIterations = 1024; // 加密1024次
        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        return result.toString();
    }
}
