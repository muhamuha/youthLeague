package com.wzxc.zzdpush.exception;

import com.wzxc.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一处理错误http状态码
 */
@RestController
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
    public AjaxResult error(HttpServletRequest request, WebRequest webRequest) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                return AjaxResult.error("接口地址错误");
            case 400:
                return AjaxResult.error("缺少必要参数");
            default:
                return AjaxResult.error("发生未知异常");
        }
    }

}
