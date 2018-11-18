package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("main")
    public String main(HttpServletRequest request){
        //System.out.println(request.getContextPath());
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        //通过用户Id查询权限
        List<String> permissions = userService.queryPermissionsByUserId(id);
        User user = userService.queryById(id);
        request.setAttribute("user",user);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }
}
