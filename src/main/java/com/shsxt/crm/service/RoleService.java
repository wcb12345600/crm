package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dao.RoleMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 批量删除角色
     * 1.删除角色表中角色
     * 2.查询数据，有的话，删除role_user表中数据
     * 3.查询数据，有的话，删除t_permission表中数据
     * @param roleIds
     */
    public void deleteRoleBatch(Integer [] roleIds){
        if (null!=roleIds && roleIds.length>0){
            for (Integer roleId : roleIds){
               AssertUtil.isTrue(roleMapper.delete(roleId)<1,CrmConstant.OPS_FAILED_MSG);

                Integer num1 = userRoleMapper.selectUserRoleByRoleId(roleId);
                if (num1>0){
                    AssertUtil.isTrue(userRoleMapper.deleteUserRoleByRoleId(roleId)<num1,CrmConstant.OPS_FAILED_MSG);
                }

                Integer num2 = permissionMapper.queryModuleByRoleId(roleId);
                if (num2>0){
                    AssertUtil.isTrue(permissionMapper.deleteModuleByRoleId(roleId)<num2,CrmConstant.OPS_FAILED_MSG);
                }
            }
        }
    }




    /**
     * 查询所有角色
     * @return
     */
    public List<Map> queryAllRoles(){

        return roleMapper.queryAllRoles();
    }

    /**
     * 角色授权
     * @param roleId
     * @param moduleIds
     */
    public void doGrant(Integer roleId,Integer [] moduleIds){
        /**1.判断角色Id是否为空，判断角色是否存在
         * 2.判断原先是否有模块，有，全部删除
         * 3.批量增加新的模块
         */
        AssertUtil.isTrue(null==roleId,"角色Id为空");
        AssertUtil.isTrue(null==roleMapper.queryById(roleId),"角色不存在");

        Integer num = permissionMapper.queryModuleByRoleId(roleId);
        if (num>0){
            AssertUtil.isTrue(permissionMapper.deleteModuleByRoleId(roleId)<num,CrmConstant.OPS_FAILED_MSG);
        }

        if (null != moduleIds && moduleIds.length > 0) {
            List<Permission> lists = new ArrayList<>();
            for (Integer moduleId:moduleIds){
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(moduleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setAclValue(moduleMapper.queryById(moduleId).getOptValue());
                lists.add(permission);
            }

            AssertUtil.isTrue(permissionMapper.saveBatch(lists)<lists.size(),CrmConstant.OPS_FAILED_MSG);
        }

    }



    public void saveOrUpdateRole(Role role){
        /**
         * 1.验证角色名不为空
         * 2.判断是添加还是更新,补全参数
         * 3.验证角色名的重复性
         * 4.执行操作
         */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名为空");
        role.setUpdateDate(new Date());  //补全更新时间
        Integer id = role.getId();
        String roleName = role.getRoleName();
        if (null==id){
            //验证角色名是否重复
            AssertUtil.isTrue(null!=roleMapper.queryRoleByRoleName(roleName),"角色已存在");
            role.setIsValid(1);
            role.setCreateDate(new Date());
            AssertUtil.isTrue(roleMapper.save(role)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            if (!roleName.equals(roleMapper.queryById(id).getRoleName())){
                AssertUtil.isTrue(null!=roleMapper.queryRoleByRoleName(roleName),"角色已存在");
            }
            AssertUtil.isTrue(roleMapper.update(role)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }
}
