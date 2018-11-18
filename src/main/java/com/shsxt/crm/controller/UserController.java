package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "user";
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        UserInfo info = userService.login(userName, userPwd);
        return success(200,"登陆成功",info);
    }

    @RequestMapping("updPassword")
    @ResponseBody
    public ResultInfo updPassword(String oldPassword, String newPassword,
                                  String confirmPassword, HttpServletRequest request){
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        //System.out.println(id);
        userService.updatePasswordById(oldPassword,newPassword,confirmPassword,id);
        return success(200,"修改成功");
    }

    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map> queryCustomerManagers(){
       return userService.queryCustomerManagers();
    }



    @RequestMapping("queryUsersByParams")
    @ResponseBody
    public Map<String, Object> queryForPageByParams(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer rows, UserQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return userService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateUser")
    @ResponseBody
    public ResultInfo saveOrUpdateUser(UserDto user, Integer [] roleIds){
        userService.saveOrUpdateUser(user,roleIds);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("deleteUsers")
    @ResponseBody
    public ResultInfo deleteUsers(Integer [] ids){
        userService.deleteUsers(ids);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }


}
