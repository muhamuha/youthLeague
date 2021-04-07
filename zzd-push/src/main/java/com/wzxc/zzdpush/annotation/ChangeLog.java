package com.wzxc.zzdpush.annotation;

import java.lang.annotation.*;

/**
 * 浙政钉消息修改日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface ChangeLog {

    String value() default "";
}
