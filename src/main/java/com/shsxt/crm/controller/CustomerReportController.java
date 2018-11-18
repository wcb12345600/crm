package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("report")
public class CustomerReportController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("{state}")
    public String index(@PathVariable Integer state){
        if (state==1){
            return "echartsReport";
        }

        return "error";
    }


    @RequestMapping("queryParams")
    @ResponseBody
    public List<Map> queryParams(){
        return customerService.queryCustomerReport();
    }
}
