package com.wzxc.zzdpush.shiro;

import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.service.impl.HhlZzdPushUserServiceImpl;
import com.wzxc.zzdpush.vo.HhlZzdPushUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private HhlZzdPushUserServiceImpl hhlZzdPushUser;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UnknownAccountException("用户名或密码不能为空");
        }
        // 查询用户账号
        HhlZzdPushUser user = hhlZzdPushUser.selectUserByUsername(username);
        if(user != null){
            ByteSource credentialsSalt = ByteSource.Util.bytes(username); // 使用账号作为盐值
            return new SimpleAuthenticationInfo(username, user.getPassword(), credentialsSalt, "shiroRealm");
        }
        throw new UnknownAccountException("用户名或密码错误");
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5"; // 加密方式
        Object crdentials = "ueQto2sbJ0evrGNnIXOh"; // 密码原值
        Object salt = ByteSource.Util.bytes("root"); // 盐值
        int hashIterations = 1024; // 加密1024次
        Object result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations);
        System.out.println(result);
    }

}
