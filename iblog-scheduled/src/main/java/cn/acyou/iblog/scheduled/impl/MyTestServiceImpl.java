package cn.acyou.iblog.scheduled.impl;

import cn.acyou.iblog.scheduled.IMyTestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @date 2018-02-09 12:30
 **/
//@Component("myTestService")
public class MyTestServiceImpl implements IMyTestService {

    public MyTestServiceImpl() {
        System.out.println("MyTestServiceImpl初始化---->>>>");
    }

    @Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
    public void myTest() {
        System.out.println("进入测试");
    }

    /**
     * 定时计算。每天凌晨 01:00 执行一次
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void show() {
        System.out.println("show method 2");
    }

    /**
     * 启动时执行一次，之后每隔2秒执行一次
     */
    @Scheduled(fixedRate = 1000 * 2)
    public void print() {
        System.out.println("print method 2");
    }

}