package com.wzxc.kbengine.en.policyBaseInfoRep;

public enum NodeId {

    WZ("温州市", 330300L),
    SX("市辖区", 330301L),
    LC("鹿城区", 330302L),
    LW("龙湾区", 330303L),
    OH("瓯海区", 330304L),
    DT("洞头区", 330305L),
    YJ("永嘉县", 330324L),
    PY("平阳县", 330326L),
    CA("苍南县", 330327L),
    WC("文成县", 330328L),
    TS("泰顺县", 330329L),
    RA("瑞安市", 330381L),
    YQ("乐清市", 330382L),
    LG("龙港市", 330383L);

    public String key;
    public long value;

    NodeId(String key, long value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public long getValue(){
        return value;
    }

    public static String getKeyByValue(long enumValue) {
        String value = "";
        for (NodeId item : values()) {
            if (item.getValue() == enumValue) {
                value = item.getKey();
                break;
            }
        }
        return value;
    }
}
