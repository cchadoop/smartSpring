package com.smart.exception;

import com.smart.contant.enums.SmartExceptionEnum;

public class SmartException extends RuntimeException {
    private final Integer code;
    private final String msg;


    public SmartException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public SmartException(SmartExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    /**
     *
     */
    public static SmartException throwException(SmartExceptionEnum exceptionEnum) {
        return new SmartException(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    /**
     * msg含有{}替代符
     * @param exceptionEnum
     * @param args
     * @return
     */
    public static SmartException throwException(SmartExceptionEnum exceptionEnum,Object... args) {
        return new SmartException(exceptionEnum.getCode(), String.format(exceptionEnum.getMsg(),args));
    }

}
