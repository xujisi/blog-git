package com.spyair.blog.web;

import com.spyair.blog.po.Comment;
import com.spyair.blog.po.User;
import com.spyair.blog.service.BlogService;
import com.spyair.blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @version V1.0
 * @Title:这是一个评论的Controller
 * @ClassName: com.spyair.blog.web.CommentConterller.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/15 21:00
 */

@Controller
public class CommentConterller {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentsService.listCommentsByBlogId(blogId));
        return "blog :: commentList";
    }

    //提交数据
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user.getRand().equals("admin")) {
            //说明是博主的评论信息
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentsService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }

}
