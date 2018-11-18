package com.shsxt.crm.dto;
import com.shsxt.crm.po.User;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class UserDto extends User{

    private String roleName;
    private String roleStr;
    private List<Integer> roleIds=new ArrayList<>();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleStr() {
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
