package com.wzxc.common.annotation;

import com.wzxc.common.validate.Check;
import com.wzxc.common.validate.Filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertBatchParam {

    // 字段校验规则，格式：字段名 + 校验规则 + 冒号 + 错误信息，例如：id<10:ID必须少于10
    Check value() default Check.Any;

    // 字段过滤器，字段值通过过滤器获得想要的结果
    Filter filter() default Filter.Any;

    // 字段名称 例子：用户名
    String fieldNameZh();

    // 字段名 例子：username
    String fieldName();

    // 自定义错误提示信息
    String msg() default "";

    String express() default "";
}
