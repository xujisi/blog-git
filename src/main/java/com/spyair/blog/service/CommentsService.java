package com.spyair.blog.service;

import com.spyair.blog.entity.Comment;

import java.util.List;

public interface CommentsService {

    List<Comment> listCommentsByBlogId(Long id);

    Comment saveComment(Comment comment);
}
