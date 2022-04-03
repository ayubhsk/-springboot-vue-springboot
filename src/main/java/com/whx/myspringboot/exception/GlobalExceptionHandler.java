package com.whx.myspringboot.exception;

import com.whx.myspringboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        System.out.println("+++++++++++++++++执行GlobalExceptionHandler+++++++++++++++++");
        return Result.error(se.getCode(), se.getMessage());
    }

}
