package com.wzxc.kbengine.en.QsBaseInfoRep;

public enum QPermission {

    ALLALLOW("全部可见", 0);

    public String key;
    public Integer value;

    QPermission(String key, Integer value){
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
        for (QPermission item : values()) {
            if (item.getValue()==enumValue) {
                value = item.getKey();
                break;
            }
        }
        return value;
    }
}
