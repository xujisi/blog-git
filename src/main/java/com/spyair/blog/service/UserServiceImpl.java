package com.spyair.blog.service;

import com.spyair.blog.dao.UserRepository;
import com.spyair.blog.po.User;
import com.spyair.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @version V1.0
 * @Title:接口实现类
 * @ClassName: com.spyair.blog.service.UserServiceImpl.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/1 17:04
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public User saveUser(String username, String password) {
        User user = new User();
        user.setAvatar("/images/avatar.png");
        user.setCreateTime(new Date());
        user.setType(2);
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
        user.setRand("peoson");
        return userRepository.save(user);
    }
}
