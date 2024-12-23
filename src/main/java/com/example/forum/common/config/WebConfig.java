package com.example.forum.common.config;

import com.example.forum.common.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private final MyInterceptor myInterceptor;

    public WebConfig( MyInterceptor myInterceptor ){
        this.myInterceptor = myInterceptor;
    }
    //确定拦截器的作用路径
    @Override
    public void addInterceptors( InterceptorRegistry registry ){
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**"); //所有路径都生效

    }


//    @Override
//    public void addResourceHandlers( ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
}
