package com.spyair.blog.controller.admin;

import com.spyair.blog.entity.Type;
import com.spyair.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @version V1.0
 * @Title:Type的控制层
 * @ClassName: com.spyair.blog.controller.admin.TypeController.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/2 22:31
 */

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //跳转到types页面
    @GetMapping("/types")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    //跳转到新增Types页面
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //跳转到编辑Types页面
    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        //根据ID取出TYPEs
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //redirect是类似于Ajax的东西，参数只能通过RedirectAttributes 进行回传
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {

        //首先校验新增的项是否在数据库存在，如果存在就提示
        Type type_temp1 = typeService.getTypeByName(type.getName());
        if (type_temp1 != null) {
            result.rejectValue("name", "nameError", "该分类已经存在，请勿添加重复的分类");
        }

        //取出Type实体类非空校验的结果，如果校验不通过则返回界面并且返回错误提示
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Type type_temp2 = typeService.saveType(type);
        if (type_temp2 == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "提交失败");
        } else {
            //如果保存成功
            attributes.addFlashAttribute("message", "提交成功");
        }
        return "redirect:/admin/types";
    }

    //编辑的保存
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        //首先校验新增的项是否在数据库存在，如果存在就提示
        Type type_temp1 = typeService.getTypeByName(type.getName());
        if (type_temp1 != null) {
            result.rejectValue("name", "nameError", "该分类已经存在，请勿添加重复的分类");
        }
        //取出Type实体类非空校验的结果，如果校验不通过则返回界面并且返回错误提示
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type_temp2 = typeService.updateType(id, type);
        if (type_temp2 == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            //如果保存成功
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
