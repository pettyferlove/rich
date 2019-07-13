package com.github.rich.log.constants;

/**
 * @author Petty
 */

public enum LogType {
    /**
     * 新增
     */
    ERROR("error",1),
    NORMAL("normal",2);


    private String type;
    private int value;

    LogType(String type, int value) {
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
