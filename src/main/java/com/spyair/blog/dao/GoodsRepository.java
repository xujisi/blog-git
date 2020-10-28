package com.spyair.blog.dao;

import com.spyair.blog.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 货物jpa数据库类
 *
 * @author: 许集思
 * @date: 2020/10/21 13:33
 */
public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    @Transactional
    @Modifying
    @Query("update Goods s set s.status = ?1 where s.id = ?2")
    int updateGoods(String yxbz, Long id);

    @Transactional
    @Query("select b from Goods b where b.goodsName = ?1")
    List<Goods> findByGoodsName(String goodsName);


}
