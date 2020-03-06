package com.spyair.blog.dao;

import com.spyair.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {


    //通过id找出Comment为空的Blog
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);

}
