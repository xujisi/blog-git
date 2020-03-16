package com.spyair.blog.web;

import com.spyair.blog.po.Sign;
import com.spyair.blog.po.User;
import com.spyair.blog.service.SignService;
import com.spyair.blog.service.UserService;
import com.spyair.blog.util.JsonUtil;
import com.spyair.blog.util.TimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spyair.blog.util.XjsUtil.requestToMap;

/**
 * @version V1.0
 * @Title:这是一个关于签到打卡的Controller
 * @ClassName: com.spyair.blog.web.SignController.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/17 23:10
 */

@Controller

public class SignController {

    @Autowired
    private SignService signService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    //列表查询
    @GetMapping("/sign")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        Long userId = (Long) user.getId();
        model.addAttribute("page", signService.listSign(pageable, userId));
        return "sign";
    }

    //签到新增页面跳转
    @GetMapping("/sign/input")
    public String input(Model model) {
        model.addAttribute("sign", new Sign());
        return "sign-input";
    }

    //签到内容保存
    @PostMapping("/sign")
    public String post(@Valid Sign sign, BindingResult result, HttpSession session, HttpServletRequest request, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        Sign sign_temp = signService.getSignByDqrqAndUserId(user);
        if (sign_temp != null) {
            result.rejectValue("name", "nameError", "当前日期已经签到，请勿重复签到");
        }
        if (result.hasErrors()) {
            return "sign-input";
        }

        sign.setUser(user);
        Sign s = signService.saveSign(sign);
        if (s == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/sign";
    }

    //签到修改页面跳转
    @GetMapping("/sign/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("sign", signService.getSign(id));
        return "/sign-input";
    }

    //签到修改保存
    @PostMapping("/sign/{id}")
    public String editpost(@Valid Sign sign, BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes,
                           HttpSession session,
                           HttpServletRequest request) {
        //首先校验新增的日期是否在数据库存在，如果存在就提示
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "登录过期，请重新登录");
            return "admin/login";
        }
        Sign sign_temp = signService.getSignByDqrqAndUserId(user);
        if (sign_temp != null) {
            result.rejectValue("name", "nameError", "当前日期已经签到，请勿重复签到");
        }
        //取出Type实体类非空校验的结果，如果校验不通过则返回界面并且返回错误提示
        if (result.hasErrors()) {
            return "/sign-input";
        }
        Sign type_temp2 = signService.updateSign(id, sign);
        if (type_temp2 == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            //如果保存成功
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/sign";
    }

    //查看页面跳转
    @GetMapping("/sign/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("sign", signService.getSign(id));
        return "sign-view";
    }

    //删除签到记录
    @GetMapping("/sign/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String rand = user.getRand();
        if (!rand.equals("admin")) {
            attributes.addFlashAttribute("message", "当前用户权限无法执行删除操作！");
            return "redirect:/sign";
        }
        signService.deleteSign(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/sign";
    }

    //上传界面跳转
    @GetMapping("/sign/{id}/upload")
    public String upload(@PathVariable Long id, Model model) throws IllegalStateException, IOException {
        List fileNameLists = new ArrayList();
        String filepath = "D:\\Blog\\src\\main\\resources\\static\\images\\" + id;
        List fileLists = readFile(filepath);
        if (fileLists != null && fileLists.size() >= 0) {
            for (int i = 0; i < fileLists.size(); i++) {
                File fileTemp = (File) fileLists.get(i);
                String fileName = fileTemp.getName();
                fileNameLists.add(fileName);
            }
            model.addAttribute("fid", id);
            model.addAttribute("fileNameLists", fileNameLists);
        } else {
            model.addAttribute("fid", "");
            model.addAttribute("fileNameLists", null);
        }

        return "sign-upload";
    }

    //上传保存
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile[] files, @RequestParam String id, RedirectAttributes attributes) throws IllegalStateException, IOException {
        // 如果文件不为空，写入上传路径，进行文件上传
        if (null != files && files[0].getSize() > 0 && files.length > 0) {
            for (MultipartFile file : files) {
                // 测试MultipartFile接口的各个方法
                System.out.println("文件类型ContentType=" + file.getContentType());
                System.out.println("文件组件名称Name=" + file.getName());
                System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
                System.out.println("文件大小Size=" + file.getSize() + "byte or " + file.getSize() / 1024 + "KB");
                saveFile(file, id);
            }
            logger.info("上传成功");
            attributes.addFlashAttribute("message", "上传成功");
        } else {
            logger.error("上传失败");
            attributes.addFlashAttribute("message", "上传失败，请选择图片");
        }
        return "redirect:/sign/" + id + "/upload";
    }

    /**
     * 文件保存方法
     *
     * @param file
     * @param
     * @throws IOException
     * @throws IllegalStateException
     */
    private void saveFile(MultipartFile file, String id) throws IllegalStateException, IOException {
        // 获取上传的文件名称，并结合存放路径，构建新的文件名称
        String destination = "D:\\Blog\\src\\main\\resources\\static\\images\\" + id;
        String fileName = file.getOriginalFilename();
        File filepath = new File(destination, fileName);

        // 判断路径是否存在，不存在则新创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }

        String xjs_fileName = TimeHelper.getCurDateTime_yyyyMMddHHmmssSSS();
        // 将上传文件保存到目标文件目录
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        file.transferTo(new File(destination, xjs_fileName + "." + prefix));
    }

    /**
     * @Title: 文件保存方法
     * @Return List
     * @Exception
     * @Description:
     * @author: 许集思
     * @date: 2020/3/1 23:25
     */
    private static List<File> readFile(String fileDir) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return null;
        }

        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                readFile(f.getAbsolutePath());
            }
        }
        for (File f1 : fileList) {
            System.out.println(f1.getName());
        }
        return fileList;
    }

    //cs
    /*@PostMapping("/sign/cs")
    @ResponseBody
    public void cs(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Map paraMap = requestToMap(request);
        Map returnMap = new HashMap();
        returnMap.put("flag", "1");
        returnMap.put("msg", paraMap.get("abc"));
        try {
            JsonUtil.getAjaxResult(response, returnMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
