package com.shsxt.crm.controller;

import com.shsxt.crm.annotation.RequestPermission;
import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
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
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService chanceService;

    @RequestMapping("index/{staus}")
    public String show(@PathVariable Integer staus){
        if (staus==1){
            return "sale_chance";
        }else if (staus==2){
            return "cus_dev_plan";
        }
        return "sale_chance";
    }

    @RequestPermission(aclValue = "101001")
    @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    public Map<String, Object> queryForPageByParams(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer rows,
                                                    SaleChanceQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return chanceService.queryForPage(query);
    }


    @RequestMapping("saveOrUpdateSaleChance")
    @ResponseBody
    public ResultInfo saveOrUpdateSaleChance(SaleChance saleChance, HttpServletRequest request){
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        chanceService.saveOrUpdateSaleChance(saleChance,userId);
        return success(200,"操作成功");
    }


    @RequestMapping("deleteSaleChanceBatch")
    @ResponseBody
    public ResultInfo deleteBatch(Integer [] ids){
        chanceService.deleteBatch(ids);
        return success(200,"操作成功");
    }

    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(SaleChance saleChance){
        chanceService.updateSaleChanceDevResult(saleChance);
        return success(200,"操作成功");
    }


}
