package com.wzxc.common.validate;

import com.wzxc.common.constant.ValidateConstants;
import com.wzxc.common.utils.reflect.ReflectUtils;

public class FilterUtil {

    /**
     * 不进行任何校验
     * @param value
     * @param express
     * @return
     */
    static Object any(Object value, String express){
        return value;
    }

    /**
     * 用户自定义过滤器
     * @param value
     * @param express
     * @return
     */
    static Object customize(Object value, String classPath) {
        try{
            // 通过value获取对象
            Class clazz = Class.forName(classPath);
            Object o = clazz.newInstance();
            // 调用过滤器方法
            Object oValue =  ReflectUtils.invokeMethodByName(o, "handle", new Object[]{ value });
            return oValue;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            return ValidateConstants.FILTER_FAIL;
        }
    }

}
