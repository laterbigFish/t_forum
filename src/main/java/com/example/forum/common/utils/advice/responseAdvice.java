package com.example.forum.common.utils.advice;

import com.example.forum.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
//统一返回格式
public class responseAdvice implements ResponseBodyAdvice {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public boolean supports( MethodParameter returnType, Class converterType ){
         return true; //表示生效
    }

    @SneakyThrows  //抛出
    @Override
    @ResponseBody
    public Object beforeBodyWrite( Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response ){
        //可以通过解密来进行判断
       //需要对一些数据进行特殊处理
        if(body instanceof Result<?>)  return body;
        //对String特殊处理, 通常情况下, 我们不返回string类型
        if (body instanceof String){
            return objectMapper.writeValueAsString(Result.SUCCESS(body));
        }
        return Result.SUCCESS(body);
    }
}
