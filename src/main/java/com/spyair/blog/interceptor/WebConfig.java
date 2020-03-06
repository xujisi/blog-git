package com.spyair.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version V1.0
 * @Title:自定义配置类
 * @ClassName: com.spyair.blog.interceptor.WebConfig.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/1 21:23
 */


//可以扩展MVC的功能
//@EnableWebMvc  //全面接管SpringMvc ，另 所有Springmvc的自动配置失效包括静态资源等
@Configuration
public class WebConfig implements WebMvcConfigurer {


    //注册拦截器(注意放出静态资源)
    public void addInterceptors(InterceptorRegistry registry) {
        // /**拦截任意请求 .excludePathPatterns 表示不用拦截器的路径请求
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/zhuce")
                .excludePathPatterns("/admin/zcsave")
                .excludePathPatterns("/admin/error")
                .excludePathPatterns("/admin/login");
    }


}
