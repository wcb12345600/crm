package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module> {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 删除模块
     * @param ids
     */
    public void deleteModule(Integer [] ids){
        /**
         * 1.判断参数
         * 2.删除t_module 表中相关数据-- 通过id查询到模块的授权码，模糊查询
         * 3.删除t_permission表中数据
         */
        AssertUtil.isTrue(null==ids || ids.length<1,"请选择要删除的模块");

        for (Integer moduleId:ids){

            Module module = moduleMapper.queryById(moduleId);
            String optValue = module.getOptValue();
            Integer num1 = moduleMapper.queryModuleCountByOptValue(optValue);
            AssertUtil.isTrue(moduleMapper.deleteModuleByOptValue(optValue)<num1,CrmConstant.OPS_FAILED_MSG);

            Integer num2 = permissionMapper.queryModuleCountByModuleId(moduleId);
            if (num2>0){
                AssertUtil.isTrue(permissionMapper.deletePermissionByOptValue(optValue)<num2,CrmConstant.OPS_FAILED_MSG);;
            }
        }
    }





    /**
     * 添加或者更新模块
     * @param module
     */
    public void saveOrUpdateModule(Module module){
        /**1.验证参数
         * 2.补全参数
         * 3.判断添加和更新
         * 4.执行操作
         */
        checkModuleParams(module);
        module.setUpdateDate(new Date());

        Integer id = module.getId();

        if (null==id){
            module.setIsValid((byte) 1);
            module.setCreateDate(new Date());

            AssertUtil.isTrue(moduleMapper.save(module)<1, CrmConstant.OPS_FAILED_MSG);
        }else {
            /**
             * 更新操作时，权限码不能改变
             */
            Module module2 = moduleMapper.queryById(id);
            AssertUtil.isTrue(!module.getOptValue().equals(module2.getOptValue()),"权限码不允许修改");
            AssertUtil.isTrue(moduleMapper.update(module)<1,CrmConstant.OPS_FAILED_MSG);
        }
    }

    /**
     * 验证参数
     * @param module
     */
    private void checkModuleParams(Module module) {
        /**
         * 1.非空验证
         */
        String moduleName = module.getModuleName();
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"模块名为空");
        String optValue = module.getOptValue();
        AssertUtil.isTrue(StringUtils.isBlank(optValue),"权限码为空");

        /**
         * 2.唯一验证
         * 通过模块名验证--通过权限码验证
         */
        if (null==module.getId()){
            AssertUtil.isTrue(null!=moduleMapper.queryModuleByModuleName(moduleName),"模块名已存在");
            AssertUtil.isTrue(null!=moduleMapper.queryModuleByOptValue(optValue),"权限码已存在");
        }


        Integer grade = module.getGrade();

        AssertUtil.isTrue(null==grade,"菜单层级为空");

        /**0  2位权限验证码
         * 1  4位
         * 2  6位
         */
        AssertUtil.isTrue(optValue.length()!=(grade+1)*2,"权限码格式不正确"+"应为:"+(grade+1)*2+"位");

        if (grade>0){
            /**
             * 层级没有问题   0  Null
             *               1  0
             *               2  1
             */
            Module parentModule = moduleMapper.queryById(module.getParentId());
            Integer parentGrade = parentModule.getGrade();
            AssertUtil.isTrue((grade-parentGrade)!=1,"层级关系错误");
            /**
             * 权限码格式验证
             */
            String parentOptValue = parentModule.getOptValue();
            AssertUtil.isTrue(optValue.indexOf(parentOptValue)!=0,"权限码格式错误：应为"+parentOptValue+"XX");
        }else{
            module.setParentId(null);
        }

    }


    public List<ModuleDto> queryModuleByRoleId(Integer roleId){
        return moduleMapper.queryModuleByRoleId(roleId);
    }

    public List<Map> queryModuleByGrade(Integer grade){
        return moduleMapper.queryModuleByGrade(grade);
    }
}
