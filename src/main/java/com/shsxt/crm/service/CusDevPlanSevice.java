package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CusDevPlanMapper;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.po.CusDevPlan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CusDevPlanSevice extends BaseService<CusDevPlan> {

    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    /**
     * 增加或修改开发计划项
     * 1.参数校验
     * 2.补全参数
     * 3.通过Id 区分添加或者更新
     * 4.执行操作
     * @param cusDevPlan
     */
    public void saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan,Integer sid){
        checkCusDevPlanParams(cusDevPlan);

        cusDevPlan.setUpdateDate(new Date());
        Integer id = cusDevPlan.getId();
        if (null==id){

            //添加操作
            cusDevPlan.setIsValid(1); //有效
            cusDevPlan.setCreateDate(new Date()); //创建时间
            cusDevPlan.setSaleChanceId(sid);  //营销机会id
            AssertUtil.isTrue(cusDevPlanMapper.save(cusDevPlan)<1, CrmConstant.OPS_FAILED_MSG);

            /**
             * 判断开发状态
             * 如果状态为开发，变为开发中
             */
            SaleChance saleChance = saleChanceMapper.queryById(sid);
            if (saleChance.getDevResult()==0){
                saleChance.setDevResult(1);
                AssertUtil.isTrue(saleChanceMapper.update(saleChance)<1,CrmConstant.OPS_FAILED_MSG);
            }
        }else{
            AssertUtil.isTrue(cusDevPlanMapper.update(cusDevPlan)<1,CrmConstant.OPS_FAILED_MSG);
        }
    }


    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null==cusDevPlan.getPlanDate(),"计划日期为空");
        AssertUtil.isTrue(null==cusDevPlan.getPlanItem(),"计划内容为空");
        AssertUtil.isTrue(null==cusDevPlan.getExeAffect(),"计划结果为空");
    }
}
