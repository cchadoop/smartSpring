package com.smart.contant.enums;

public enum SmartExceptionEnum {

    NEED_USER_NAME(10001,"用户名不能为空！"),
    NEED_PASSWORD(10002,"密码不能为空！"),
    PASSWORD_TOO_SHORT(10003,"密码长度不能小于8位！"),
    USERNAME_EXISTED(10004,"用户名已存在！"),
    INSERT_FAILED(10005,"数据插入失败！"),
    LOGIN_ERROR(10006,"用户名或密码不正确！"),
    NEED_LOGIN(10007,"用户未登录！"),
    NEED_ADMIN(10008,"无管理员权限！"),
    UPDATE_FAILED(10008,"更新用户信息失败！"),
    SYSTEM_ERROR(20001,"系统异常！");

    //异常码
    Integer code;
    //异常信息
    String msg;

    SmartExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
