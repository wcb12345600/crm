package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 添加或修改saleChance
     * 1.检验参数
     * 2.补全参数
     * 3.通过Id区分是添加或者更新
     * 4.执行最终操作
     * @param saleChance
     */
    public void saveOrUpdateSaleChance(SaleChance saleChance,Integer userId){
        checkSaleChanceParams(saleChance);

        saleChance.setUpdateDate(new Date()); //设置更新时间

        Integer id = saleChance.getId();

        if (null==id){

            /**
             * 如果选择分配人，status为1，已分配 设置分配时间
             * 如果没选择分配人，status为0,未分配
             */
            if (StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setState(0);  //未分配
            }else{
                saleChance.setState(1);
                saleChance.setAssignTime(new Date());
            }

            //添加
            saleChance.setDevResult(0);  //未开发
            saleChance.setIsValid(1);    //有效数据
            saleChance.setCreateDate(new Date());  //创建时间
            User user = userMapper.queryById(userId);
            saleChance.setCreateMan(user.getUserName());  //创建人
            AssertUtil.isTrue(saleChanceMapper.save(saleChance)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            //更新
            AssertUtil.isTrue(saleChanceMapper.update(saleChance)<1,CrmConstant.OPS_FAILED_MSG);
        }
    }

    /**
     * 检验参数
     * @param saleChance
     */
    private void checkSaleChanceParams(SaleChance saleChance) {
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()),"客户名称为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()),"联系人为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()),"联系人电话为空");
    }


    public Integer updateSaleChanceDevResult(SaleChance saleChance){
        return saleChanceMapper.updateSaleChanceDevResult(saleChance);
    }

}
