package com.spyair.blog.dao;

import com.spyair.blog.po.Sign;
import com.spyair.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface SignRepository extends JpaRepository<Sign, Long>, JpaSpecificationExecutor<Sign> {

    //通过当前日期查找Sign
    Sign findByDqrqAndYxbzAndUser(String dqrq, String yxbz, User user);

    @Transactional
    @Modifying
    @Query("update Sign s set s.yxbz = ?1 where s.id = ?2")
    int updateSign(String yxbz, Long id);

    @Transactional
    @Modifying
    @Query("update Sign s set s.yxbz = ?1 ,s.updateTime=?2 where s.id = ?3")
    int deleteSign(String yxbz, Date date, Long id);

}
