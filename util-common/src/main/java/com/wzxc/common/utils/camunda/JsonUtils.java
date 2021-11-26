package com.wzxc.common.utils.camunda;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {

    public static JSONObject parseVariables(JSONObject variables){
        JSONObject var = new JSONObject();
        Iterator<String> iterator = variables.getJSONObject("variables").keySet().iterator();
        for (Iterator<String> it = iterator; it.hasNext(); ) {
            String key = it.next();
            JSONObject value = var.getJSONObject(key);
            JSONObject val = new JSONObject();
            try{
                val.put("value", value.getString("value"));
                val.put("type", value.getString("type"));
                val.put("valueInfo", value.getJSONObject("valueInfo"));
            } catch (Exception e){}
            var.put(key, val);
        }
        return var;
    }

    public static Map<String, Object> parseObject(JSONObject obj){
        Map<String, Object> var = new HashMap<>();
        Iterator<String> iterator = obj.keySet().iterator();
        for (Iterator<String> it = iterator; it.hasNext(); ) {
            String key = it.next();
            try{
                Object value = StringUtils.isEmpty(obj.getString(key)) ? null : obj.get(key);
                var.put(key, value);
            } catch (Exception e){}
        }
        return var;
    }

}
