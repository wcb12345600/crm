package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;

import com.shsxt.crm.po.SaleChance;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleChanceMapper extends BaseMapper<SaleChance> {

    public Integer updateSaleChanceDevResult(SaleChance saleChance);
}