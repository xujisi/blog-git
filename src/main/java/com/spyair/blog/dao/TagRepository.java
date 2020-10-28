package com.spyair.blog.dao;

import com.spyair.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Long> {

    //通过Name找出Tag
    Tag findByName(String name);

    //找出前几个Tag
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
