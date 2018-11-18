package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerServe;

import com.shsxt.crm.query.CustomerServeQuery;
import com.shsxt.crm.service.CustomerServeService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("customerServe")
public class CustomerServeController extends BaseController {

    @Autowired
    private CustomerServeService serveService;

    @RequestMapping("index/{state}")
    public String index(@PathVariable Integer state){
        if(state==1){
            return "customer_serve_create";
        }else if(state==2){
            return "customer_serve_assign";
        }else if(state==3){
            return "customer_serve_proce";
        }else if(state==4){
            return "customer_serve_feed_back";
        }else if(state==5){
            return "customer_serve_archive";
        }
        return "error";
    }

    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerServe(HttpServletRequest request, CustomerServe customerServe){
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        serveService.saveOrUpdateCustomerServe(customerServe,userId);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryCustomerServesByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerServesByParams(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer rows,
                                                           CustomerServeQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return serveService.queryForPage(query);
    }

}
