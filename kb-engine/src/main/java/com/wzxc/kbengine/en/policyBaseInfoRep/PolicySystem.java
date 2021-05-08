package com.wzxc.kbengine.en.policyBaseInfoRep;

public enum PolicySystem {

    Organs("党政机关整体智治系统", 0),
    Gov("数字政府系统", 1),
    Soci("数字社会系统", 2),
    Fin("数字经济系统", 3),
    Law("数字法制系统", 4);

    public String key;
    public Integer value;

    PolicySystem(String key, Integer value){
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
