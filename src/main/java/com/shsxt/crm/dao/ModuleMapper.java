package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseMapper;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Module;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ModuleMapper extends BaseMapper<Module> {

    public List<ModuleDto> queryModuleByRoleId(Integer roleId);

    public List<Map> queryModuleByGrade(Integer grade);

    public Module queryModuleByModuleName(String moduleName);

    public Module queryModuleByOptValue(String optValue);

    public Integer queryModuleCountByOptValue(String optValue);

    public Integer deleteModuleByOptValue(String optValue);
}