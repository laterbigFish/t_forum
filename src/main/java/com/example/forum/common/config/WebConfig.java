package com.example.forum.common.config;

import com.example.forum.common.interceptor.MyInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 表示一个配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private  MyInterceptor myInterceptor;

    //确定拦截器的作用路径
    @Override
    public void addInterceptors( InterceptorRegistry registry ){
        registry.addInterceptor(myInterceptor)   //添加用户登录拦截器
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")  //排除api接口2
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/info")
                .excludePathPatterns("/user/logout")
                .excludePathPatterns("/user/logout")
                .excludePathPatterns("/sign-in.html")
                .excludePathPatterns("/sign-up.html")
                .excludePathPatterns("/dist/**")   //排除所有静态文件
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/**.ico")   //页面图标
                .excludePathPatterns("/js/**");
    }
}
