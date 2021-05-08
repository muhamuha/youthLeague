package com.wzxc.common.validate;

import java.util.function.BiFunction;

public enum Filter {

    Any("", "any", FilterUtil::any),
    Customize("自定义过滤器发生异常", "customize", FilterUtil::customize);

    public String msg;
    public String methodName;
    public BiFunction<Object, String, Object> fun;

    Filter(String msg, String methodName, BiFunction<Object, String, Object> fun) {
        this.msg = msg;
        this.methodName = methodName;
        this.fun = fun;
    }
}
