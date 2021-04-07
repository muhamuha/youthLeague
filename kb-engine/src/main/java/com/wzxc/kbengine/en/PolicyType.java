package com.wzxc.kbengine.en;

import io.swagger.models.auth.In;

public enum PolicyType{

    Nation("国级", 0), // 国级
    Province("省级", 1), // 省级
    City("市级", 2), // 市级
    County("县级", 3); // 县级

    public String key;
    public Integer value;

    PolicyType(String key, Integer value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public Integer getValue(){
        return value;
    }
}
