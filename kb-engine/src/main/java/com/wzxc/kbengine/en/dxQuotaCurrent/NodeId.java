package com.wzxc.kbengine.en.dxQuotaCurrent;

public enum NodeId {

    WZ("温州市", 330300000000L),
    SX("市辖区", 330301000000L),
    LC("鹿城区", 330302000000L),
    LW("龙湾区", 330303000000L),
    OH("瓯海区", 330304000000L),
    DT("洞头区", 330305000000L),
    YJ("永嘉县", 330324000000L),
    PY("平阳县", 330326000000L),
    CA("苍南县", 330327000000L),
    WC("文成县", 330328000000L),
    TS("泰顺县", 330329000000L),
    RA("瑞安市", 330381000000L),
    YQ("乐清市", 330382000000L),
    LG("龙港市", 330383000000L);

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
