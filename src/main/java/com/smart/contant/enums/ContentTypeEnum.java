package com.smart.contant.enums;

/**
 * 常用Content-Type类型
 *
 * @author LiaoQinZhou
 * @date: 2021/4/22 09:51
 */
public enum ContentTypeEnum {
    APPLICATION_FORM("表单", "application/x-www-form-urlencoded;charset=utf-8"),
    APPLICATION_FORM_DATA("表单", "multipart/form-data"),
    APPLICATION_JSON("json", "application/json;charset=utf8"),
    TEXT_XML("xml", "text/xml"),
    APPLICATION_OCTET_STREAM("文件流", "application/octet-stream"),
    APPLICATION_DOWNLOAD("文件下载", "application/force-download"),
    //防止空指针异常
    DEFAULT_NULL("", ""),
    ;

    private String name;
    private String value;

    ContentTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static ContentTypeEnum getByValue(String value) {
        for (ContentTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return ContentTypeEnum.DEFAULT_NULL;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
