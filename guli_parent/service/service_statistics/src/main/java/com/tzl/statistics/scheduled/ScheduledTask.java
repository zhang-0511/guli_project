package com.tzl.statistics.scheduled;

import com.tzl.statistics.service.StatisticsDailyService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: guli_parent
 * @description: 定时任务配置类
 * @author: zl
 * @create: 2022-06-10 15:20
 **/
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //@Scheduled(cron = "0/5 * * * * ?")
    public void task01(){
        System.out.println("****************************");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void task02(){
        String day = LocalDate.now().minusDays(1).toString("yyyy-MM-dd");
        statisticsDailyService.countRegister(day);
    }
}
