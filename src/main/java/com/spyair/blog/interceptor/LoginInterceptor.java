package com.spyair.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @Title:界面拦截网
 * @ClassName: com.spyair.blog.interceptor.LoginInterceptor.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/1 21:20
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //如果用户未登录，那么就给他重定向到登录界面
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin/error");
            return false;
        }

        //继续执行
        return true;
    }
}
