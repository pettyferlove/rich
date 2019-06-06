package com.github.rich.attachment.constants;

/**
 * @author Petty
 */

public enum StorageTypeEnum {

    /**
     * 阿里云OSS储存方案
     */
    CloudAliyunOSS("Aliyun Cloud OSS", "aliyun", 1001),

    /**
     * 七牛云对象储存方案
     */
    CloudQiniu("Qiniu Cloud", "qiniu", 1002),


    /**
     * 腾讯云COS方案
     */
    CloudTencent("Tencent Cloud COS", "tencent", 1003),

    /**
     * 百度云BOS方案
     */
    CloudBaidu("Baidu Cloud BOS", "baidu", 1004),

    /**
     * 本地文件储存
     */
    FileLocal("Local File", "local", 2001);

    private String type;
    private String service;
    private int value;

    StorageTypeEnum(String type, String service, int value) {
        this.type = type;
        this.service = service;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getService() {
        return service;
    }

    @Override
    public String toString() {
        return "StorageTypeEnum{" +
                "type='" + type + '\'' +
                ", service='" + service + '\'' +
                ", value=" + value +
                '}';
    }

    public static StorageTypeEnum parse(int value) {
        for (StorageTypeEnum storageTypeEnum : StorageTypeEnum.values()) {
            if (storageTypeEnum.getValue() == value) {
                return storageTypeEnum;
            }
        }

        throw new IllegalArgumentException("Unable to parse the provided type " + value);
    }

}
