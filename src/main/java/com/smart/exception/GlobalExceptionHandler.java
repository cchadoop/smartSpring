package com.smart.exception;

import com.smart.contant.enums.SmartExceptionEnum;
import com.smart.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理统一异常的handler
 *
 * @RestControllerAdvice  = @ControllerAdvice + @ResponseBody
 */
@ControllerAdvice(basePackages = "com.smart.controller")
//@RestControllerAdvice(basePackages = "com.smart.controller")
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 拦截系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception:", e);
        return R.error(SmartExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 拦截自定义业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(SmartException.class)
    @ResponseBody
    public Object handleLearnMallException(SmartException e) {
        log.error("LearnMallException Exception:", e);
        return R.error(e.getCode(),e.getMsg());
    }

}
