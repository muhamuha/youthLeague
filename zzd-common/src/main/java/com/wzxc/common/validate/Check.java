package com.wzxc.common.validate;

import java.util.function.BiFunction;

public enum Check {

    Any("", "any", CheckUtil::any),

    Null("参数必须为 null", "isNull", CheckUtil::isNull),

    NotNull("参数必须不为 null", "isNotNull", CheckUtil::isNotNull),

    NotEmpty("参数必须非空", "isNotEmpty", CheckUtil::isNotEmpty),

    True("参数必须为 true", "isTrue", CheckUtil::isTrue),

    Date("参数必须是一个日期 yyyy-MM-dd", "isDate", CheckUtil::isDate),

    DateTime("参数必须是一个日期时间  yyyy-MM-dd HH:mm:ss", "isDateTime", CheckUtil::isDateTime),

    Enum("参数必须在枚举中 ", "inEnum", CheckUtil::inEnum),

    Email("参数必须是Email地址", "isEmail", CheckUtil::isEmail),

    Range("参数必须在合适的范围内", "inRange", CheckUtil::inRange),

    Length("参数长度必须在指定范围内", "inLength", CheckUtil::inLength),

    IsArrayAndNotEmpty("参数为数组且不为空", "isArrayAndNotEmpty", CheckUtil::isArrayAndNotEmpty),

//    gt("参数必须大于指定值", CheckUtil::isGreaterThan),

//    lt("参数必须小于指定值", CheckUtil::isLessThan),

    ge("参数必须大于等于指定值", "isGreaterThanEqual", CheckUtil::isGreaterThanEqual),

//    le("参数必须小于等于指定值", CheckUtil::isLessThanEqual),

//    ne("参数必须不等于指定值", CheckUtil::isNotEqual),

    Equal("参数必须不等于指定值", "isEqual", CheckUtil::isEqual),

    Pattern("参数必须符合指定的正则表达式", "isPattern", CheckUtil::isPattern),

    IsEnum("参数必须符合指定的正则表达式", "isEnum", CheckUtil::isEnum);

    public String msg;
    public String methodName;
    public BiFunction<Object, String, Object> fun;

    Check(String msg, String methodName, BiFunction<Object, String, Object> fun) {
        this.msg = msg;
        this.methodName = methodName;
        this.fun = fun;
    }
}
