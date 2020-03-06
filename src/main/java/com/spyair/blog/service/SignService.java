package com.spyair.blog.service;

import com.spyair.blog.po.Sign;
import com.spyair.blog.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @version V1.0
 * @Title:这是一个关于签到的Service接口
 * @ClassName: com.spyair.blog.service.SignService.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/17 23:21
 */


public interface SignService {

    //签到保存并且保存签到内容
    Sign saveSign(Sign sign);

    //列表查询出签到列表
    Page<Sign> listSign(Pageable pageable, Long userId);

    //根据Date查询出签到表数据
    Sign getSignByDqrqAndUserId(User user);

    //根据ID查询出签到列表数据
    Sign getSign(Long id);

    //编辑保存
    Sign updateSign(Long id, Sign sign);

    //删除记录
    void deleteSign(Long id);

}
