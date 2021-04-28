package com.wzxc.kbengine.en.dxQuotaCurrent;

public enum NodeId {

    WZ("温州市", 330300),
    SX("市辖区", 330301),
    LC("鹿城区", 330302),
    LW("龙湾区", 330303),
    OH("瓯海区", 330304),
    DT("洞头区", 330305),
    YJ("永嘉县", 330324),
    PY("平阳县", 330326),
    CA("苍南县", 330327),
    WC("文成县", 330328),
    TS("泰顺县", 330329),
    RA("瑞安市", 330381),
    YQ("乐清市", 330382),
    LG("龙港市", 330383);

    public String key;
    public Integer value;

    NodeId(String key, Integer value){
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
        for (NodeId item : values()) {
            if (item.getValue()==enumValue) {
                value = item.getKey();
                break;
            }
        }
        return value;
    }
}
