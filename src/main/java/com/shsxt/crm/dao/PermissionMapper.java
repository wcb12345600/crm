package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.po.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    public Integer queryModuleByRoleId(Integer roleId);

    public Integer deleteModuleByRoleId(Integer roleId);

    public Integer queryModuleCountByModuleId(Integer moduleId);

    public Integer deletePermissionByOptValue(String aclValue);
}