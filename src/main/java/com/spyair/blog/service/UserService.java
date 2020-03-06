package com.spyair.blog.service;

import com.spyair.blog.po.User;

/**
 * @version V1.0
 * @Title:Service层接口管理类
 * @ClassName: com.spyair.blog.service.UserService.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/1 17:03
 */

public interface UserService {

    User checkUser(String username, String password);

    User saveUser(String username, String password);


}

