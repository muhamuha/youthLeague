package com.wzxc.configcommon.exception;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.constant.Constants;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.configcommon.servlet.MyRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该类捕捉在filter或者拦截器中抛出的异常
 */
@Slf4j
public class MyExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String contentType = servletRequest.getContentType();
            if(StringUtils.isEmpty(contentType) || contentType.contains("multipart/form-data") || contentType.contains("application/x-www-form-urlencoded")){ // 如果是上传文件还是用下面这种方式就会出现异常
                filterChain.doFilter(servletRequest, servletResponse);

            } else if (servletRequest instanceof HttpServletRequest) {
                MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
                String body = myRequestWrapper.getBody();
                try{
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.PAGE_NUM, jsonObject.get(Constants.PAGE_NUM));
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.PAGE_SIZE, jsonObject.get(Constants.PAGE_SIZE));
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.IS_PAGE, jsonObject.get(Constants.IS_PAGE));
                    filterChain.doFilter(myRequestWrapper, servletResponse);
                } catch(Exception e){
                    log.error("MyExceptionFilter(e) --- ", e);
                } finally {
                    filterChain.doFilter(myRequestWrapper, servletResponse);
                }

            }
        } catch (Exception e) {
            // 异常捕获，发送到error controller
            servletRequest.setAttribute("filter.error", e);
            // 将异常分发到/error/exthrow控制器
            try{
                servletRequest.getRequestDispatcher("/error/exthrow").forward(servletRequest, servletResponse);
            } catch(Exception e2){
                log.error("MyExceptionFilter(e2) --- ", e);
            }
        }
    }
}
