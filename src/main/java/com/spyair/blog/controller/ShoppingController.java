package com.spyair.blog.controller;

import com.spyair.blog.entity.Goods;
import com.spyair.blog.entity.Result;
import com.spyair.blog.entity.StatusCode;
import com.spyair.blog.entity.User;
import com.spyair.blog.service.GoodsService;
import com.spyair.blog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.spyair.blog.util.XjsUtil.requestToMap;

@Controller
@RequestMapping("/shopping")

public class ShoppingController {

    //自定义界面
    private static final String shopping_input = "shopping/shopping-input";
    private static final String shopping_add = "shopping/shopping-add";
    private static final String shopping_view = "shopping/shopping-view";

    //私有注入
    @Autowired
    private GoodsService goodsService;


    //上货界面跳转
    @GetMapping("/input")
    public String input(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Goods goods, Model model, HttpSession session, HttpServletRequest request) {
        //检测用户过期
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        model.addAttribute("page", goodsService.listGoods(pageable, goods));
        return "shopping/shopping-input";
    }


    /**
     * 查詢返回列表页面
     *
     * @param pageable,@param blog,@param model
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/10/21 15:06
     **/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Goods goods, Model model, HttpSession session, HttpServletRequest request) {
        //检测用户过期
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        model.addAttribute("page", goodsService.listGoods(pageable, goods));
        return "shopping/shopping-input :: goodsList";
    }


    /**
     * 新增商品界面跳转
     *
     * @param model
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/10/21 15:44
     **/
    @GetMapping("/add")
    public String input(Model model, HttpSession session, HttpServletRequest request) {
        //检测用户过期
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        model.addAttribute("goods", new Goods());
        return shopping_add;
    }

    /**
     * 编辑商品界面跳转
     *
     * @param model
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/10/21 15:44
     **/
    @GetMapping("/{id}/edit")
    public String input(@PathVariable Long id, Model model, HttpSession session, HttpServletRequest request) {
        //检测用户过期
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        model.addAttribute("goods", new Goods());
        Goods goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        return shopping_add;
    }

    /**
     * 查看界面跳转
     *
     * @param model
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/10/21 15:44
     **/
    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model, HttpSession session, HttpServletRequest request) {
        //检测用户过期
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        model.addAttribute("goods", new Goods());
        Goods goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        return shopping_view;
    }

    /**
     * 保存新商品
     *
     * @param goods,@param attributes,@param session
     * @return java.lang.String
     * @author: 许集思
     * @date: 2020/10/21 16:02
     **/
    @PostMapping("/save")
    @ResponseBody
    public Result post(Goods goods, HttpSession session, HttpServletRequest request) {
        try {
            //检测用户过期
            User user = (User) session.getAttribute("user");
            if (user == null) {
                request.setAttribute("message", "登录过期，请重新登录");
                return new Result(true, StatusCode.TIMELATER, "登录过期，请重新登录");
            }
            String userName = ((User) session.getAttribute("user")).getUsername();
            goods.setUpdatePeople(userName);
            Goods goods_temp;
            if (goods.getId() == null) {
                //新增保存
                goods_temp = goodsService.saveGoods(goods);
            } else {
                //修改保存
                goods_temp = goodsService.updateGoods(goods.getId(), goods);
            }
            if (goods_temp == null) {
                return new Result(true, StatusCode.ERROR, "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, StatusCode.ERROR, e.getMessage());
        }
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 无效化数据
     *
     * @param request,@param session,@param response
     * @return com.spyair.blog.entity.Result
     * @author: 许集思
     * @date: 2020/10/28 18:01
     **/
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        try {
            //检测用户过期
            User user = (User) session.getAttribute("user");
            if (user == null) {
                request.setAttribute("message", "登录过期，请重新登录");
                return new Result(true, StatusCode.TIMELATER, "登录过期，请重新登录");
            }
            //入参
            Map paraMap = requestToMap(request);
            Long id = Long.parseLong((String) paraMap.get("id"));

            int result = goodsService.updateStatusById(id);
            if (result <= 0) {
                return new Result(true, StatusCode.ERROR, "无效化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, StatusCode.ERROR, e.getMessage());
        }
        return new Result(true, StatusCode.OK, "无效化成功");
    }

    @GetMapping("/downloadExcel")
    @ResponseBody
    public void downTemplate(HttpServletResponse response) {
        new FileUtil().downloadExcel(response, "testModel.xlsx", "modelExcel.xlsx");
    }
}

