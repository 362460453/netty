package com.server.utils;

import lombok.Data;

//@Data
public enum TypeEnum {


    CONTROL(0x00, "control type"),
    INTERRUPT(0x01, "interrupt type"),
    RESPONSE(0x02, "response type"),
    QUERY(0x03, "query type"),
    NETWORK(0x04, "network type");

    private int type;
    private String descr;

    public int value() {
        return this.type;
    }

    //Class clazz;
    TypeEnum(int type, String descr) {
        this.type = type;
        this.descr = descr;
    }


}
