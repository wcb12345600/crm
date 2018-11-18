package com.shsxt.crm.task;

import com.shsxt.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
//@Component  //必须加上此注解
public class TaskJob {

    @Autowired
    private CustomerService customerService;


    @Scheduled(cron = "0/10 * * * * ? ")
    public void job01(){
        System.out.println("开始了..........");
        customerService.addLossCustomer();
        System.out.println("结束了...........");
    }
}
