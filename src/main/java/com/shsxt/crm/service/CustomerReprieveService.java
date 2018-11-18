package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.dao.CustomerReprieveMapper;
import com.shsxt.crm.po.CustomerReprieve;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve> {

    @Autowired
    private CustomerReprieveMapper customerReprieveMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;

    /**
     * 批量删除客户暂缓计划
     * @param ids
     */
    public void deleteBatchReprieve(Integer [] ids){
        if (null!=ids && ids.length>0){
            AssertUtil.isTrue(customerReprieveMapper.deleteBatchReprieve(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
        }
    }

    /**
     * 确认流失客户
     * @param lossId
     */
    public void updateCustomerState(Integer lossId){
        if (null!=lossId){
            AssertUtil.isTrue(customerLossMapper.updateCustomerState(lossId)<1,CrmConstant.OPS_SUCCESS_MSG);
        }
    }



    /**添加或者更新客户流失措施
     * @param lossId
     */
    public void saveOrUpdateCustomerReprieve(Integer lossId,CustomerReprieve reprieve){
        /**1.参数验证
         * 2.判断是添加还是更新
         * 3.执行操作
         */
        AssertUtil.isTrue(StringUtils.isBlank(reprieve.getMeasure()),"处理措施为空");
        reprieve.setUpdateDate(new Date());
        if (null==reprieve.getId()){
            //添加
            reprieve.setLossId(lossId);
            reprieve.setCreateDate(new Date());
            reprieve.setIsValid(1);
            AssertUtil.isTrue(customerReprieveMapper.save(reprieve)<1, CrmConstant.OPS_FAILED_MSG);
        }else {
            AssertUtil.isTrue(customerReprieveMapper.update(reprieve)<1, CrmConstant.OPS_FAILED_MSG);
        }

    }
}
