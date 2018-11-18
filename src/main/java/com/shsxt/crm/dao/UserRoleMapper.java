package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.po.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole>{

    public Integer insertBatch(Map map);

    public Integer queryUserRoleByUserId(Integer userId);

    public Integer deleteUserRoleByUserId(Integer userId);


    public Integer deleteUserRoleByRoleId(Integer roleId);

    public Integer selectUserRoleByRoleId(Integer roleId);
}