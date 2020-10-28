package com.spyair.blog.service;

import com.spyair.blog.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Shopping接口
 *
 * @author: 许集思
 * @date: 2020/10/21 13:19
 */

@Service
public interface GoodsService {

    Page<Goods> listGoods(Pageable pageable,Goods goods);

    Goods saveGoods(Goods blog);

    Goods updateGoods(Long id, Goods blog);

    Goods getGoods(Long id);

    int updateStatusById(Long id);
}
