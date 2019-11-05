package com.luo.entity.enums;

public enum OrderType {
    APPLY("申购"),
    REDEEM("赎回"),

        ;
    String desc;

     OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
