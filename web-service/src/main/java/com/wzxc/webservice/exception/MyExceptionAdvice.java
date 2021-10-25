package com.wzxc.webservice.exception;

import com.wzxc.common.core.domain.BusiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一处理错误http状态码
 */
@RestController
@Slf4j
public class MyExceptionAdvice implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 默认错误
     */
    private static final String path_default = "/error";

    @Override
    public String getErrorPath() {
        return path_default;
    }

    /**
     * JSON格式错误信息
     */
    @RequestMapping(value = path_default)
    public BusiResult error(HttpServletRequest request, WebRequest webRequest) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 405:
                return BusiResult.error("调用方式错误GET/POST");
            case 404:
                return BusiResult.error("接口地址错误");
            case 400:
                return BusiResult.error("缺少必要参数");
            default:
                return BusiResult.error("发生未知异常");
        }
    }

}
