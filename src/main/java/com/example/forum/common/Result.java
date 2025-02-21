package com.example.forum.common;

import com.example.forum.common.enmu.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Result<T> {
    //使得如果为空也可以在前端显示出来

    @JsonInclude(JsonInclude.Include.ALWAYS)  //表示无论任何情况都参与JSON序列化
    private long code;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String message;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;

    //三种构造方法
    public Result(){}

    public Result(long code,String message){
      this(code,message,null);
  }
    public Result(long code,String message,T data){
      this.code = code;
      this.message = message;
      this.data = data;
  }
  public static <T> Result<T> SUCCESS(T Date){
        return new Result<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),Date);
  }
  public static <T> Result<T> SUCCESS(String message){
        return new Result<>(ResultCode.SUCCESS.getCode(), message);
  }
    public static <T> Result<T> SUCCESS(){
        return new Result<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }
    public static <T> Result<T> SUCCESS(String message,T date){
        return new Result<>(ResultCode.SUCCESS.getCode(),message,date);
    }


    public static <T> Result<T> FAIL(){
        return new Result<>(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage());
    }
    public static <T> Result<T> FAIL(String message){
        return new Result<>(ResultCode.FAILED.getCode(),message);
    }
    public  static <T> Result<T> FAIL(ResultCode resultCode){
        return new Result<>(resultCode.getCode(), resultCode.getMessage());
    }

    public long getCode(){
        return code;
    }

    public void setCode( long code ){
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage( String message ){
        this.message = message;
    }

    public T getData(){
        return data;
    }

    public void setData( T data ){
        this.data = data;
    }
}