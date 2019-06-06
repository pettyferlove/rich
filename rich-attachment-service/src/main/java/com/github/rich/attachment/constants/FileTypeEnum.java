package com.github.rich.attachment.constants;

import com.aliyun.oss.model.CannedAccessControlList;

public enum FileTypeEnum {

    /**
     * JPEG
     */
    IMAGE_JPEG("image/jpeg",".jpeg"),
    IMAGE_JPG("image/jpg",".jpg");
    private String contentType;

    private String expansionName;

    FileTypeEnum(String contentType, String expansionName) {
        this.contentType = contentType;
        this.expansionName = expansionName;
    }

    @Override
    public String toString() {
        return contentType;
    }

    public String getExpansionName() {
        return expansionName;
    }

    public static FileTypeEnum parse(String contentType) {
        for(FileTypeEnum cacl : FileTypeEnum.values()) {
            if (cacl.toString().equals(contentType)) {
                return cacl;
            }
        }

        throw new IllegalArgumentException("Unable to parse the provided type " + contentType);
    }
}
