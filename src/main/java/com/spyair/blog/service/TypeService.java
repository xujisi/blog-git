package com.spyair.blog.service;

import com.spyair.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @version V1.0
 * @Title:Type接口
 * @ClassName: com.spyair.blog.service.TypeService.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/2 22:08
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type getTypeByName(String name);
}
