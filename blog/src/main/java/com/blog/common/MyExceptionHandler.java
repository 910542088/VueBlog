package com.blog.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)//运行异常
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)//未认证异常
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error(e.getMessage());
        return Result.fail(e.getMessage(),401);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)//实体校验异常
    @ExceptionHandler(value = BindException.class)
    public Result handler(BindException e){
        log.error(e.getMessage());
        return Result.fail(String.valueOf(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
        //只返回了第一个异常
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)//断言异常
    @ExceptionHandler(value = IllegalStateException.class)
    public Result handler(IllegalStateException e){
        log.error(e.getMessage());
        return Result.fail(e.getMessage());
    }

}
