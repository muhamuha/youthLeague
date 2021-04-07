package com.wzxc.zzdpush.exception;

import com.wzxc.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 根据@ControllerAdvice + @ExceptionHandler 实现的统一异常捕捉处理 存在一个缺点！！！
 * 即：这种方式只能捕捉到控制层（controller），在控制层之上的filter或拦截器抛出的异常是捕捉不到的！！！
 * 解决方法：添加了一个中间filter（MyExceptionFilter），通过filter链先将异常传递到controller层，再通过controller将异常传递到该层。
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public AjaxResult exceptionHandler1(Exception e){
        return AjaxResult.unAuth(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = UnknownAccountException.class)
    public AjaxResult exceptionHandler2(Exception e){
        return AjaxResult.error(e.getLocalizedMessage());
    }
}
