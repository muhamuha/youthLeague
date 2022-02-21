package com.wzxc.busi.en;

public enum IsReelect {

    STAY("是", 1),
    NOTSTAY("否", 0);

    public String key;
    public Integer value;

    IsReelect(String key, Integer value){
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
