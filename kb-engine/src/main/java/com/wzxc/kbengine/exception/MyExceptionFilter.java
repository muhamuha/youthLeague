package com.wzxc.kbengine.exception;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.constant.Constants;
import com.wzxc.common.utils.ServletUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.kbengine.servlet.KbengineRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 该类捕捉在filter或者拦截器中抛出的异常
 */
@Slf4j
public class MyExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String contentType = servletRequest.getContentType();
            if(StringUtils.isEmpty(contentType) || contentType.contains("multipart/form-data")){ // 如果是上传文件还是用下面这种方式就会出现异常
                filterChain.doFilter(servletRequest, servletResponse);

            } else if (servletRequest instanceof HttpServletRequest) {
                KbengineRequestWrapper kbengineRequestWrapper = new KbengineRequestWrapper((HttpServletRequest) servletRequest);
                String body = kbengineRequestWrapper.getBody();
                JSONObject jsonObject = JSONObject.parseObject(body);
                try{
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.PAGE_NUM, jsonObject.get(Constants.PAGE_NUM));
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.PAGE_SIZE, jsonObject.get(Constants.PAGE_SIZE));
                    ((HttpServletRequest) servletRequest).getSession().setAttribute(Constants.IS_PAGE, jsonObject.get(Constants.IS_PAGE));
                } catch(Exception e){
                    log.error("MyExceptionFilter(e) --- ", e);
                } finally {
                    filterChain.doFilter(kbengineRequestWrapper, servletResponse);
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
