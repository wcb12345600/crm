package com.shsxt.crm.dao;


import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.po.User;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<UserDto> {

    public User queryUserByName(String userName);

    public Integer updatePasswordById(@Param("newPassword") String newPassword, @Param("id") Integer id);

    public List<Map> queryCustomerManagers();

    public List<String> queryPermissionsByUserId(Integer userId);
}