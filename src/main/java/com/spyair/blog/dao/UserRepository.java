package com.spyair.blog.dao;

import com.spyair.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    //JPA内部方法，可以查询数据库 ，命名规则要规范
    User findByUsernameAndPassword(String username, String password);
}
