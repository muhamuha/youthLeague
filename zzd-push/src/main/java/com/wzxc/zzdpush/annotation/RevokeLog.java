package com.wzxc.zzdpush.annotation;

import java.lang.annotation.*;

/**
 * 浙政钉消息撤销日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface RevokeLog {

    String value() default "";
}
