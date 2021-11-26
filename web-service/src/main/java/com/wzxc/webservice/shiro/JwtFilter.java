package com.wzxc.webservice.shiro;

import com.wzxc.common.utils.RequestIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final String TOKEN = "league-token";
    private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

    private String[] anonUrl = new String[]{};
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private boolean TOKEN_ILLEGAL = false;

    /**
     * 获取usrId
     */
    public static String getUserId(){
        return userIdThreadLocal.get();
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 是否允许直接登录
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 生成requestId
        RequestIdUtils.createRequestId(httpServletRequest);
        // 获取免认证接口 url
        boolean match = false;
        for (String u : anonUrl) {
            if (pathMatcher.match(u, httpServletRequest.getRequestURI())) {
                match = true;
            }
        }
        if (match) {
            return true;
        }
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }
        return false;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(TOKEN);
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = ((HttpServletRequest) request).getHeader(TOKEN);
        // 校验token
        if(!JwtUtil.verify(token)){
            TOKEN_ILLEGAL = true;
        } else{
            userIdThreadLocal.set(JwtUtil.getUserId(token));
            return true;
        }
        return false;
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        if(TOKEN_ILLEGAL){
            TOKEN_ILLEGAL = false; // // 过滤器全局的
            throw new AuthenticationException("用户不存在！");
        } else{
            throw new AuthenticationException("没有权限访问！");
        }
    }
}
