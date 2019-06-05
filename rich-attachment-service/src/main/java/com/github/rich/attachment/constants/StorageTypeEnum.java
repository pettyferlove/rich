package com.github.rich.attachment.constants;

/**
 * @author Petty
 */

public enum StorageTypeEnum {

    /**
     * 阿里云OSS储存方案
     */
    CLOUD_OSS("Aliyun Cloud OSS", 1001),

    /**
     * 七牛云对象储存方案
     */
    CLOUD_QINIU("Qiniu Cloud", 1002),


    /**
     * 腾讯云COS方案
     */
    CLOUD_TENCENT("Tencent Cloud COS", 1003),

    /**
     * 百度云BOS方案
     */
    CLOUD_BAIDU("Baidu Cloud BOS", 1004),

    /**
     * 本地文件储存
     */
    FILE_LOCAL("Local File", 2001);

    private String type;
    private int value;

    StorageTypeEnum(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StorageType{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }

    public static boolean isInEnum(int value) {
        for (StorageTypeEnum userType : StorageTypeEnum.values()) {
            if (userType.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
