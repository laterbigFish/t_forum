package com.example.forum.common.interceptor;

import com.example.forum.common.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component

public class MyInterceptor implements HandlerInterceptor {
     @Value("${forum.login.url}")
    private  String url;
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception{
        //获取session
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute(AppConfig.USER_SESSION)!=null) return true;
        //跳转到登录页面

        if(!url.startsWith("/")){   //startsWith 方法用于检查字符串是否以指定的前缀开始
            url = "/"+url;
        }
        response.sendRedirect(url);  //为了减低耦合性  把这个跳转页面放到配置文件中
        //返回一个中断流程
        return false;
    }
}
