package com.victor.demo.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@Component
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("当前时间：{}，开始执行定时任务", sdf.format(new Date()));
        int i = 10000000;
        for (int j = 0; j < i; j++){
                doSomething();
        }
        log.info("当前时间：{}，定时任务执行完毕", sdf.format(new Date()));
    }

    private void doSomething(){
        int i = new Random().nextInt();
    }
}
