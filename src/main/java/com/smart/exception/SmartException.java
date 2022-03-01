package com.smart.exception;

import com.smart.contant.enums.SmartExceptionEnum;

public class SmartException extends  Exception{
    private final Integer code;
    private final String msg;



    public SmartException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public SmartException(SmartExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
