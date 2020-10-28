package com.spyair.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @version V1.0
 * @Title:关于我界面的控制层
 * @ClassName: com.spyair.blog.controller.AboutShowController.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/16 15:19
 */


@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String About(){
        return "about";
    }
}
