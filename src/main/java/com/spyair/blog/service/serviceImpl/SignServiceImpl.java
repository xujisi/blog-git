package com.spyair.blog.service.serviceImpl;

import com.spyair.blog.NotFoundException;
import com.spyair.blog.dao.SignRepository;
import com.spyair.blog.entity.Sign;
import com.spyair.blog.entity.User;
import com.spyair.blog.service.SignService;
import com.spyair.blog.util.TimeHelper;
import com.spyair.blog.util.XjsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title:实现签到接口
 * @ClassName: com.spyair.blog.service.serviceImpl.SignServiceImpl.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/17 23:36
 */


@Service
public class SignServiceImpl implements SignService {

    @Autowired
    private SignRepository signRepository;

    //私有的当前日期（年月日）
    private String dqrq = TimeHelper.date_yyyyddmm();
    //有效标志
    private String yxbz_yx = XjsUtil.yxbz_yx;
    //无效标志
    private String yxbz_wx = XjsUtil.yxbz_wx;


    @Transactional
    @Override
    public Sign saveSign(Sign sign) {
        sign.setDqrq(dqrq);
        sign.setCreateTime(new Date());
        sign.setYxbz(yxbz_yx);
        return signRepository.save(sign);
    }

    @Override
    public Page<Sign> listSign(Pageable pageable, Long userId) {
        return signRepository.findAll(new Specification<Sign>() {
            @Override
            public Predicate toPredicate(Root<Sign> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.<String>get("yxbz"), yxbz_yx));
                String userid = String.valueOf(userId);
                predicates.add(cb.equal(root.<String>get("user").get("id"), userid));

                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Sign getSignByDqrqAndUserId(User user) {
        return signRepository.findByDqrqAndYxbzAndUser(dqrq, yxbz_yx, user);
    }

    @Override
    public Sign getSign(Long id) {
        return signRepository.findById(id).orElse(null);
    }

    @Override
    public Sign updateSign(Long id, Sign sign) {
        Sign sign_old = signRepository.findById(id).orElse(null);
        if (sign_old == null) {
            throw new NotFoundException("不存在该签到记录，请重试");
        }
        //将旧数据无效化
        signRepository.updateSign(yxbz_wx, id);
        //给新数据设置新内容
        sign.setCreateTime(sign_old.getCreateTime());
        sign.setUpdateTime(new Date());
        sign.setDqrq(dqrq);
        sign.setYxbz(yxbz_yx);
        sign.setId(null);
        return signRepository.save(sign);
    }

    @Override
    public int deleteSign(Long id) {
        return signRepository.deleteSign(yxbz_wx, new Date(), id);
    }


}
