package com.example.forum.common.exception;

import com.example.forum.common.Result;
import com.example.forum.common.enmu.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyException {

    @ResponseBody   //明确表明将数据返回给客户端  而不是 一个视图 和 确定使用那种格式

    @ExceptionHandler(ForumException.class)
    public Result applicationHandler( ForumException forumException){
        forumException.printStackTrace();   //生产之前要删除
        log.error(forumException.getMessage());

        if(forumException.getMessage()!=null) return forumException.getErrorResult();

        if(forumException.getMessage()==null || StringUtils.hasLength(forumException.getMessage())){
            return Result.FAIL(ResultCode.ERROR_SERVICES);
        }
        //返回具体的错误信息
        return Result.FAIL(forumException.getMessage());
    }

    //处理其他异常
    @ExceptionHandler(Exception.class)   //统一异常处理
    @ResponseBody
    public Result handler( Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        if(e.getMessage()==null || !StringUtils.hasLength(e.getMessage())) {
            return Result.FAIL(ResultCode.ERROR_SERVICES);
        }
        return Result.FAIL(e.getMessage());
    }

}
