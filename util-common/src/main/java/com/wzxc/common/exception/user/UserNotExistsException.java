package com.wzxc.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author ruoyi
 */
public class UserNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("用户不存在");
    }
}
