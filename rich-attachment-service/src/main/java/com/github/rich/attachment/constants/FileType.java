package com.github.rich.attachment.constants;

public enum FileType {

    /**
     * JPEG
     */
    IMAGE_JPEG("image/jpeg",".jpeg"),
    IMAGE_JPG("image/jpg",".jpg"),
    IMAGE_PNG("image/png",".png"),
    EXCEL_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",".xlsx");
    private String contentType;

    private String expansionName;

    FileType(String contentType, String expansionName) {
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

    public static FileType parse(String contentType) {
        for(FileType cacl : FileType.values()) {
            if (cacl.toString().equals(contentType)) {
                return cacl;
            }
        }

        throw new IllegalArgumentException("Unable to parse the provided type " + contentType);
    }
}
