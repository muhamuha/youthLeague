package com.wzxc.zzdpush.annotation;

import java.lang.annotation.*;

/**
 * 浙政钉消息推送日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface PushLog {

    String value() default "";
}
