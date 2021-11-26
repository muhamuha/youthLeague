package com.wzxc.webservice.exception;

import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.exception.InsertBatchException;
import com.wzxc.common.exception.ParamInException;
import com.wzxc.common.exception.ParamInValidException;
import com.wzxc.common.exception.user.UserNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;


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
    public BusiResult exceptionHandler1(Exception e){
        return BusiResult.unAuth(e.getMessage());
    }

    @ExceptionHandler(value = ParamInException.class)
    public BusiResult exceptionHandler2(Exception e) {
        return BusiResult.error(e.getMessage());
    }

    @ExceptionHandler(value = ParamInValidException.class)
    public BusiResult exceptionHandler3(Exception e){
        return BusiResult.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public BusiResult exceptionHandler4(Exception e){
        return BusiResult.error(e.getMessage());
    }

    @ExceptionHandler(value = InsertBatchException.class)
    public BusiResult exceptionHandler5(Exception e){
        return BusiResult.error(e.getMessage());
    }

    // camunda-flow 相关异常
    @ExceptionHandler(value = UserNotExistsException.class)
    public BusiResult camundaException(Exception e){
        return BusiResult.error(e.getMessage());
    }

    // 数据库 相关异常
    @ExceptionHandler(value = SQLException.class)
    public BusiResult databaseException(Exception e){
        return BusiResult.error("数据库交互异常");
    }

    // 其他异常
    @Order(Ordered.LOWEST_PRECEDENCE - 2)
    @ExceptionHandler(value = RuntimeException.class)
    public BusiResult exceptionHandler(Exception e){
        log.error("发生未知异常", e);
        return BusiResult.error("系统发生未知异常");
    }

}
