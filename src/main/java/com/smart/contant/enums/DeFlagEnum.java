package com.smart.contant.enums;

/**
 * 删除标识
 */
public enum DeFlagEnum {

    DELETE("DE_FLAG","D")
    ;

    private String name;
    private String value;

    DeFlagEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static DeFlagEnum getByValue(String value) {
        for (DeFlagEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
