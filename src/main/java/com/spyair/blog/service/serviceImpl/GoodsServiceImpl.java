package com.spyair.blog.service.serviceImpl;

import com.spyair.blog.NotFoundException;
import com.spyair.blog.dao.GoodsRepository;
import com.spyair.blog.entity.Goods;
import com.spyair.blog.service.GoodsService;
import com.spyair.blog.util.TimeHelper;
import com.spyair.blog.util.XjsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    //私有的当前日期（年月日）
    private String dqrq = TimeHelper.date_yyyyddmm();
    //有效标志
    private String yxbz_yx = XjsUtil.yxbz_yx;
    //无效标志
    private String yxbz_wx = XjsUtil.yxbz_wx;

    /**
     * 条件查询貨物（预编译）
     *
     * @param pageable,@param goods
     * @return org.springframework.data.domain.Page<com.spyair.blog.entity.Goods>
     * @author: 许集思
     * @date: 2020/10/28 23:13
     **/
    @Override
    public Page<Goods> listGoods(Pageable pageable, Goods goods) {
        return goodsRepository.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty(goods.getGoodsName())) {
                    predicates.add(cb.like(root.<String>get("goodsName"), "%" + goods.getGoodsName() + "%"));
                }
                if (StringUtils.isNotEmpty(goods.getStatus())) {
                    predicates.add(cb.equal(root.<String>get("status"), goods.getStatus()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /**
     * 新增保存
     *
     * @param goods
     * @return com.spyair.blog.entity.Goods
     * @author: 许集思
     * @date: 2020/10/26 0:36
     **/
    @Transactional
    @Override
    public Goods saveGoods(Goods goods) {
        String goodsName = goods.getGoodsName();
        String goodsCost = goods.getCost();
        if (StringUtils.isEmpty(goodsName) || StringUtils.isEmpty(goodsCost)) {
            throw new NotFoundException("货物名称或者成本价格不能为空。请重新提交");
        }
        List goodsList = goodsRepository.findByGoodsName(goods.getGoodsName());
        if (goodsList.size() > 0) {
            throw new NotFoundException("该商品名字已存在。请更换名称重新保存");
        }
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        goods.setCreatePeople(goods.getUpdatePeople());
        //设置默认有效标志
        goods.setStatus("0");
        //设置唯一商品编号
        UUID uuid = UUID.randomUUID();
        goods.setGoodsId(uuid.toString());
        return goodsRepository.save(goods);
    }

    /**
     * 修改保存
     *
     * @param id,@param goods
     * @return com.spyair.blog.entity.Goods
     * @author: 许集思
     * @date: 2020/10/26 0:36
     **/
    @Override
    @Transactional
    public Goods updateGoods(Long id, Goods goods) {
        String goodsName = goods.getGoodsName();
        String goodsCost = goods.getCost();
        if (StringUtils.isEmpty(goodsName) || StringUtils.isEmpty(goodsCost)) {
            throw new NotFoundException("货物名称或者成本价格不能为空。请重新提交");
        }
        Goods g = goodsRepository.findById(id).orElse(null);
        if (g == null) {
            throw new NotFoundException("该商品已不存在。请勿修改");
        }
        //将旧数据无效化
        goodsRepository.updateGoods(yxbz_wx, id);
        //给新数据设置新内容
        goods.setStatus(yxbz_yx);
        goods.setUpdateTime(new Date());
        goods.setId(null);
        goods.setGoodsId(g.getGoodsId());
        goods.setCreatePeople(g.getCreatePeople());
        goods.setCreateTime(g.getCreateTime());
        return goodsRepository.save(goods);
    }

    /**
     * 根据ID查找商品
     *
     * @param id
     * @return com.spyair.blog.entity.Goods
     * @author: 许集思
     * @date: 2020/10/28 15:38
     **/
    @Override
    public Goods getGoods(Long id) {
        Goods goods = goodsRepository.findById(id).orElse(null);
        return goods;
    }

    /**
     * 根据ID无效化数据
     *
     * @param id
     * @return int
     * @author: 许集思
     * @date: 2020/10/28 18:01
     **/
    @Override
    public int updateStatusById(Long id) {
        if (StringUtils.isEmpty(String.valueOf(id))) {
            throw new NotFoundException("入参为空，请重试");
        }
        return goodsRepository.updateGoods(yxbz_wx, id);
    }
}
