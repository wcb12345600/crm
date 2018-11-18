package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.po.CustomerReprieve;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.query.CustomerReprieveQuery;
import com.shsxt.crm.service.CustomerLossService;
import com.shsxt.crm.service.CustomerReprieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("customerReprieve")
public class CustomerReprieveController extends BaseController {

    @Autowired
    private CustomerLossService customerLossService;

    @Autowired
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index")
    public String index(Model model,Integer lossId){
        CustomerLoss customerLoss = customerLossService.queryById(lossId);
        model.addAttribute(customerLoss);
        return "customer_loss_reprieve";
    }

    @RequestMapping("queryReprievesByParams")
    @ResponseBody
    public Map<String, Object> queryReprievesByParams(@RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer rows,
                                                      CustomerReprieveQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return customerReprieveService.queryForPage(query);
    }

    /**
     * 添加或者更新客户流失措施
     * @param lossId
     * @param reprieve
     * @return
     */
    @RequestMapping("saveOrUpdateCustomerReprieve")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerReprieve(Integer lossId,CustomerReprieve reprieve){
        customerReprieveService.saveOrUpdateCustomerReprieve(lossId,reprieve);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("delReprieve")
    @ResponseBody
    public ResultInfo delReprieve(Integer [] ids){
        customerReprieveService.deleteBatchReprieve(ids);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("updateCustomerReprieveState")
    @ResponseBody
    public ResultInfo updateCustomerReprieveState(Integer lossId){
        customerReprieveService.updateCustomerState(lossId);
        return success(200, CrmConstant.OPS_SUCCESS_MSG);
    }


}
