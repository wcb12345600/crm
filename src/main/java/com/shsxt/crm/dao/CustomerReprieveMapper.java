package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.po.CustomerReprieve;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReprieveMapper extends BaseMapper<CustomerReprieve>{

    public Integer deleteBatchReprieve(Integer [] ids);
}