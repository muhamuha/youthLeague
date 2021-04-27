package com.wzxc.kbengine.en.QsBaseInfoRep;

public enum Resource {

    NEEDAUDIT("温州数字门户 - 手工录入", 0),
    AUDITPASS("温州数字门户 - 批导入", 1);

    public String key;
    public Integer value;

    Resource(String key, Integer value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public Integer getValue(){
        return value;
    }

    public static String getKeyByValue( int enumValue) {
        String value = "";
        for (Resource item : values()) {
            if (item.getValue()==enumValue) {
                value = item.getKey();
                break;
            }
        }
        return value;
    }
}
