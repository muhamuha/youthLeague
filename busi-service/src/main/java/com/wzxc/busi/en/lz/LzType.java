package com.wzxc.busi.en.lz;

public enum LzType {

    ACTIVITY("活动履职", 1),
    OTHER("其他履职", 2);

    public String key;
    public Integer value;

    LzType(String key, Integer value){
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
