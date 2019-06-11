package com.github.rich.log.constants;

/**
 * @author Petty
 */

public enum OperateType {
    /**
     * 新增
     */
    ADD("add operate",1),
    DELETE("delete operate",2),
    UPDATE("update operate",3),
    QUERY("query operate",4),
    OTHER("other operate",4);


    private String type;
    private int value;

    OperateType(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
