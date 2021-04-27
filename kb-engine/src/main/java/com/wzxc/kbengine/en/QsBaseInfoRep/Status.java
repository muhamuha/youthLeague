package com.wzxc.kbengine.en.QsBaseInfoRep;

public enum Status {

    NEEDAUDIT("待审核", 0),
    AUDITPASS("审核通过", 1);

    public String key;
    public Integer value;

    Status(String key, Integer value){
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
        for (Status item : values()) {
            if (item.getValue()==enumValue) {
                value = item.getKey();
                break;
            }
        }
        return value;
    }
}
