package com.wzxc.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map通用处理方法
 * 
 * @author ruoyi
 */
public class MapDataUtil
{
    public static Map<String, Object> convertDataMap(HttpServletRequest request)
    {
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<?> entries = properties.entrySet().iterator();
        Entry<?, ?> entry;
        String name = "";
        String value = "";
        while (entries.hasNext())
        {
            entry = (Entry<?, ?>) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj)
            {
                value = "";
            }
            else if (valueObj instanceof String[])
            {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++)
                {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            }
            else
            {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    //Map转Object
    public static Object getMapToObject(Map<Object, Object> map, Class<?> cla) throws Exception {
        if (map == null)
            return null;
        Object obj = cla.newInstance();
        cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        getObjectToMap(obj, cla, map);
        // 判断是否含有父类
        Class<?> superClass = cla.getSuperclass();
        while(!superClass.toString().equals("class java.lang.Object")){
            getObjectToMap(obj, superClass, map);
            superClass = superClass.getSuperclass();
        }
        return map;
    }
    private static void getObjectToMap(Object obj, Class<?> cla, Map<String, Object> map) throws IllegalAccessException {
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
    }
}
