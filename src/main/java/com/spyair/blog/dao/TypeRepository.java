package com.spyair.blog.dao;

import com.spyair.blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @version V1.0
 * @Title:处理Type数据的Dao接口
 * @ClassName: com.spyair.blog.dao.TypeRepository.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/2 22:12
 */

public interface TypeRepository extends JpaRepository<Type, Long> {

    //通过Name找出Type
    Type findByName(String name);

    //找出前几个Type
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
