package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.OrderDetailsMapper;
import com.shsxt.crm.po.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService extends BaseService<OrderDetails> {

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;
}
