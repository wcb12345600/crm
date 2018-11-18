package com.shsxt.crm.interceptors;



import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shsxt.crm.constants.CrmConstant.USER_NOT_LOGIN_MSG;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

         //判断用户是否登陆
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isNotLogin(userId==null || userService.queryById(userId)==null,USER_NOT_LOGIN_MSG);
        return true;
    }



    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("index")){
            return true;
        }

        String userName = CookieUtil.getCookieValue(request, "userName");
        if (!StringUtils.isBlank(userName)){
            return true;
        }



        return false;
    }*/
}
