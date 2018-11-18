package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.dao.CustomerMapper;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService extends BaseService<Customer> {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;


    public List<Map> queryCustomerReport(){
        return customerMapper.queryCustomerReport();
    }

    /**
     * 流失客户查询，添加到流失客户表，把客户表中状态码修改
     */
    public void addLossCustomer(){
        List<Customer> customers = customerMapper.addLossCustomer();

        if (null!=customers && customers.size()>0){
            List<CustomerLoss> lossList = new ArrayList<>();
            for (Customer customer:customers){
                CustomerLoss customerLoss = new CustomerLoss();
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setState(0);
                customerLoss.setIsValid(1);
                customerLoss.setCreateDate(new Date());
                customerLoss.setUpdateDate(new Date());
                lossList.add(customerLoss);
            }

            AssertUtil.isTrue(customerLossMapper.saveBatch(lossList)<lossList.size(),CrmConstant.OPS_FAILED_MSG);

            AssertUtil.isTrue(customerMapper.updateCustomerLoss(lossList)<lossList.size(),CrmConstant.OPS_FAILED_MSG);

        }

    }




    public List<Map> queryCustomerLevels(String dicName){
        return customerMapper.queryCustomerLevels(dicName);
    }

    /**
     * 新增或者更新客户信息
     * @param customer
     */
    public void saveOrUpdateCustomer(Customer customer){
        /**
         * 1.判断参数
         * 2.区分新增，修改
         * 3.补全参数
         * 4.执行操作
         */
        customer.setUpdateDate(new Date());
        Integer customerId = customer.getId();

        if (null==customerId){
            //新增
            customer.setState(0);  //未流失客户
            customer.setIsValid(1); //有效数据
            customer.setCreateDate(new Date()); //创建时间
            customer.setKhno(MathUtil.genereateKhCode());

            AssertUtil.isTrue(customerMapper.save(customer)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            AssertUtil.isTrue(customerMapper.update(customer)<1, CrmConstant.OPS_FAILED_MSG);
        }

    }

}
