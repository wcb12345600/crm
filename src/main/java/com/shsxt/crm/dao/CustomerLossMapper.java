package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.po.CustomerLoss;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLossMapper extends BaseMapper<CustomerLoss>{

    public Integer updateCustomerState(Integer lossId);
}