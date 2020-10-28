package com.spyair.blog.service.serviceImpl;

import com.spyair.blog.NotFoundException;
import com.spyair.blog.dao.TypeRepository;
import com.spyair.blog.entity.Type;
import com.spyair.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @version V1.0
 * @Title:实现Type接口(下面的Transactional是放进一个事务的意思）
 * @ClassName: com.spyair.blog.service.serviceImpl.TypeServiceImpl.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/2 22:11
 */

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    //增
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    //查一个
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    //查一堆
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    //改
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type_find = typeRepository.findById(id).orElse(null);
        if (type_find == null) {
            //更新前的查询是否存在该数据
            throw new NotFoundException("不存在该类型");
        }
        //type中的值赋给了type_find
        BeanUtils.copyProperties(type, type_find);
        return typeRepository.save(type_find);
    }

    //删
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }


    //根据名字查找Type
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }


}
