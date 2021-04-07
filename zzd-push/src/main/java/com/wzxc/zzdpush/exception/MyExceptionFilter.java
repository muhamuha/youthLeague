package com.wzxc.zzdpush.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 该类捕捉在filter或者拦截器中抛出的异常
 */
@Slf4j
public class MyExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            // 异常捕获，发送到error controller
            servletRequest.setAttribute("filter.error", e);
            // 将异常分发到/error/exthrow控制器
            servletRequest.getRequestDispatcher("/error/exthrow").forward(servletRequest, servletResponse);
        }
    }
}
