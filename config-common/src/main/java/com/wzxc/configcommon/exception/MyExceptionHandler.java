package com.wzxc.configcommon.exception;

import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.exception.InsertBatchException;
import com.wzxc.common.exception.ParamInException;
import com.wzxc.common.exception.ParamInValidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public KbengineResult exceptionHandler1(Exception e){
        return KbengineResult.unAuth();
    }

    @ExceptionHandler(value = ParamInException.class)
    public KbengineResult exceptionHandler2(Exception e) {
        return KbengineResult.error(e.getMessage());
    }

    @ExceptionHandler(value = ParamInValidException.class)
    public KbengineResult exceptionHandler3(Exception e){
        return KbengineResult.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public KbengineResult exceptionHandler4(Exception e){
        return KbengineResult.error(e.getMessage());
    }

    @ExceptionHandler(value = InsertBatchException.class)
    public KbengineResult exceptionHandler5(Exception e){
        return KbengineResult.error(e.getMessage());
    }

}
