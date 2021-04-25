package com.wzxc.kbengine.en.QsBaseInfoRep;

public enum Stauts {

    NEEDAUDIT("待审核", 0),
    AUDITPASS("审核通过", 1);

    public String key;
    public Integer value;

    Stauts(String key, Integer value){
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
