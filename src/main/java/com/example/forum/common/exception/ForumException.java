package com.example.forum.common.exception;

import com.example.forum.common.Result;
import lombok.Getter;

@Getter
public class ForumException extends RuntimeException{
    //在异常中保存错误信息
//    private static final long serialVersionUID = -3533806916645793660L;
    // ⾃定义错误
    protected Result errorResult;
    // 指定状态码，异常描述
    public ForumException(Result errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    public ForumException( String message ){
        super(message);
    }

    public ForumException( String message, Throwable cause ){
        super(message, cause);
    }

    public ForumException( Throwable cause ){
        super(cause);
    }

    protected ForumException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ForumException(){
        super();
    }
}
