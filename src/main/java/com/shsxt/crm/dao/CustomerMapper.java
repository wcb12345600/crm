package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerMapper extends BaseMapper<Customer>{


    public List<Map> queryCustomerLevels(String dicName);

    public List<Customer> addLossCustomer();

    public Integer updateCustomerLoss(List<CustomerLoss> list);

    public List<Map> queryCustomerReport();
}