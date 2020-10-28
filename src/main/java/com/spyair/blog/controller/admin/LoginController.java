package com.spyair.blog.controller.admin;


import com.spyair.blog.entity.User;
import com.spyair.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @version V1.0
 * @Title:用户管理登录 控制层
 * @ClassName: com.spyair.blog.controller.admin.LoginController.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/1 17:08
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    //这个代表/admin的页面
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/error")
    public String loginError(HttpServletRequest request) {
        request.setAttribute("message", "登录过期，请重新登录");
        return "admin/login";
    }

    //用户登录Action
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            //将密码清空
            user.setPassword(null);
            session.setAttribute("user", user);
            return "about";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    //用户注销Action
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    //注册新用户界面跳转
    @GetMapping("/zhuce")
    public String zhuce() {
        return "admin/zhuce";
    }

    //用户注册保存
    @PostMapping("/zcsave")
    public String zcsave(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String surepsd,
                         RedirectAttributes attributes,
                         HttpSession session) {
        if (!password.equals(surepsd)) {
            attributes.addFlashAttribute("message", "两次输入密码不一致，请重新输入");
            return "redirect:/admin/zhuce";
        }
        User user = userService.checkUser(username, password);
        if (user != null) {
            //代表用户重复
            attributes.addFlashAttribute("message", "该用户已经存在，请重新注册");
            return "redirect:/admin/zhuce";
        } else {
            //保存新用户，并且回到登录界面
            userService.saveUser(username, password);
            session.removeAttribute("user");
            attributes.addFlashAttribute("message", "注册成功，请登录");
            return "redirect:/admin";
        }
    }

}
