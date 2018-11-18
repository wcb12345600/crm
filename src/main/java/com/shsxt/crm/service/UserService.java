package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.shsxt.crm.base.BaseQuery;
import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends BaseService<UserDto> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    public List<String> queryPermissionsByUserId(Integer userId){
        return userMapper.queryPermissionsByUserId(userId);
    }

    /**
     * 批量删除用户
     * @param ids
     */
    public void deleteUsers(Integer [] ids){
        if (null!=ids && ids.length>0){
            for (int id : ids){
                AssertUtil.isTrue(userMapper.delete(id)<1,CrmConstant.OPS_FAILED_MSG);

                Integer roleNum = userRoleMapper.queryUserRoleByUserId(id);

                if (roleNum>0){
                    AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id)<roleNum,CrmConstant.OPS_FAILED_MSG);
                }
            }
        }
    }


    /**
     * 添加用户
     * @param user
     */
    public void saveOrUpdateUser(UserDto user,Integer [] roleIds){
        /**
         * 1.校验参数
         * 2.验证用户名是否存在
         * 3.补全参数
         */
        checkSaveOrUpdateParams(user);
        //设置更新时间
        user.setUpdateDate(new Date());

        if (null==user.getId()){
            //新增
            AssertUtil.isTrue(null!=userMapper.queryUserByName(user.getUserName()),"用户名已存在");
            user.setCreateDate(new Date());
            user.setUserPwd(Md5Util.encode("123456"));
            user.setIsValid(1);
            AssertUtil.isTrue(userMapper.save(user)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            /**修改
             * 1.用户名不能修改
             * 2.如果之前没角色，直接添加，有，先删除，在添加
             */

            AssertUtil.isTrue(!user.getUserName().equals(userMapper.queryById(user.getId()).getUserName()),"用户名不能修改");
            AssertUtil.isTrue(userMapper.update(user)<1, CrmConstant.OPS_FAILED_MSG);

            Integer roleNum = userRoleMapper.queryUserRoleByUserId(user.getId());

            if (roleNum>0){
                AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(user.getId())<roleNum,CrmConstant.OPS_FAILED_MSG);
            }

        }
        /**
         * 判断是否有角色
         */
        if (null!=roleIds && roleIds.length>0){
            /*List list  = new ArrayList();
            for (int roleId : roleIds){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getId());
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                list.add(userRole);
            }*/
            Map map = new HashMap();
            map.put("userId",user.getId());
            map.put("roleIds",roleIds);

            AssertUtil.isTrue(userRoleMapper.insertBatch(map)<roleIds.length,CrmConstant.OPS_FAILED_MSG);
        }

    }


    private void checkSaveOrUpdateParams(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(user.getTrueName()),"真实姓名");
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱为空");
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"电话为空");
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param id
     */
    public void updatePasswordById(String oldPassword,String newPassword,
                                   String confirmPassword, Integer id){
        checkUpdatePasswordByIdParams(oldPassword,newPassword,confirmPassword);
        User user = userMapper.queryById(id);
        AssertUtil.isTrue(user==null,"用户名不存在或已经注销");
        AssertUtil.isTrue(!Md5Util.encode(oldPassword).equals(user.getUserPwd()),"旧密码错误");
        //Integer row = userMapper.updatePasswordById(Md5Util.encode(newPassword), id);
        AssertUtil.isTrue(userMapper.updatePasswordById(Md5Util.encode(newPassword), id)<1,"修改失败");
    }


    /**
     * 检查密码非空验证
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    private void checkUpdatePasswordByIdParams(String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword),"旧密码为空");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword),"新密码为空");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword),"两次密码不一致");
    }


    /**
     * 登陆操作
     * @param userName
     * @return
     */
    public UserInfo login(String userName, String userPwd){
        AssertUtil.isTrue(StringUtil.isEmpty(userName),"用户名为空");
        AssertUtil.isTrue(StringUtil.isEmpty(userPwd),"密码为空");
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user==null,"用户名不存在或已经注销");
        // System.out.println(user);
        ///System.out.println(user.getUserPwd());
        AssertUtil.isTrue(!Md5Util.encode(userPwd).equals(user.getUserPwd()),"用户名或密码错误");

        return createUserInfo(user);
    }

    /**
     * 创建UserInfo对象
     * @param user
     * @return
     */
    private UserInfo createUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userInfo.setRealName(user.getTrueName());
        userInfo.setUserName(user.getUserName());
        return userInfo;
    }

    /**
     * 查询所有客户经理
     * @return
     */
    public List<Map> queryCustomerManagers(){
        return userMapper.queryCustomerManagers();
    }


    @Override
    public Map<String, Object> queryForPage(BaseQuery baseQuery) throws DataAccessException {
        PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
        List<UserDto> entities=userMapper.queryByParams(baseQuery);
        PageInfo<UserDto> pageInfo=new PageInfo<UserDto>(entities);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        entities = pageInfo.getList();
        for (UserDto userDto:entities){
            String roleIdsStr = userDto.getRoleStr();
            if (null!=roleIdsStr){
                String [] roleStr = roleIdsStr.split(",");
                List<Integer> roleIdList = new ArrayList<>();
                for (String roleId : roleStr){
                    roleIdList.add(Integer.valueOf(roleId));
                }
                userDto.setRoleIds(roleIdList);
            }
        }
        return map;
    }
}
