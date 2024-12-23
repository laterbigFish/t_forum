package com.example.forum.common.exception;

import com.example.forum.common.model.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class MyException {
    //处理其他异常
    @ExceptionHandler(Exception.class)   //统一异常处理
    public Object handler(Exception e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(ForumException.class)   //处理自定义异常
    public  Object handler(ForumException e){
        return Result.fail(e.getMessage());
    }
}
