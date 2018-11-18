package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerServeMapper;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerServeService extends BaseService<CustomerServe> {

    @Autowired
    private CustomerServeMapper customerServeMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 更新或者增加服务
     * @param customerServe
     */
    public void saveOrUpdateCustomerServe(CustomerServe customerServe,Integer userId){
        //参数校验---省略
        customerServe.setUpdateDate(new Date());
        Integer id = customerServe.getId();
        UserDto userDto = userMapper.queryById(userId);
        if (null==id){
            //添加
            customerServe.setCreatePeople(userDto.getUserName());
            customerServe.setState("1");
            customerServe.setCreateDate(new Date());
            customerServe.setIsValid(1);

            AssertUtil.isTrue(customerServeMapper.save(customerServe)<1, CrmConstant.OPS_FAILED_MSG);
        }else {
            //获取状态码
            String state = customerServe.getState();
            if ("1".equals(state)) {   //分配人添加
                customerServe.setState("2");
                customerServe.setAssignTime(new Date());
            }

            if ("2".equals(state)){  //服务的具体处理
                customerServe.setState("3");
                customerServe.setServiceProcePeople(userDto.getUserName());
                customerServe.setServiceProceTime(new Date());
            }

            if ("3".equals(state)) {   //对服务做出评价
                customerServe.setState("4");
            }

            AssertUtil.isTrue(customerServeMapper.update(customerServe)<1,CrmConstant.OPS_FAILED_MSG);

        }
    }
}
