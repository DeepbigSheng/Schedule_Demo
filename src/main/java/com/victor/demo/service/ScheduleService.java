package com.victor.demo.service;

import com.victor.demo.domain.QrtzJobDetailConfig;
import com.victor.demo.schedule.job.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ScheduleService {
    @Autowired
    private Scheduler scheduler;

    /**
     * 新建一个任务
     */
    public String addJob(QrtzJobDetailConfig quartzConfig) throws Exception {
        Date date = quartzConfig.getStartTime();

        if (!CronExpression.isValidExpression(quartzConfig.getCron())) {
            return "Illegal cron expression";   //表达式格式不正确
        }

        JobDetail jobDetail = null;
        //构建job信息
        jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(quartzConfig.getJobName(), quartzConfig.getJobGroup()).build();

        //表达式调度构建器(即任务执行的时间,不立即执行)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzConfig.getCron());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzConfig.getJobName(), quartzConfig.getJobGroup()).startAt(date)
                .withSchedule(scheduleBuilder).build();

        //传递参数
        if (quartzConfig.getInvokeParam() != null && !"".equals(quartzConfig.getInvokeParam())) {
            trigger.getJobDataMap().put("invokeParam", quartzConfig.getInvokeParam());
        }
        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        log.info("Schedule task {} started at:{}", date, new Date());
        // pauseJob(quartzConfig.getJobName(),quartzConfig.getJobGroup());
        return "success";
    }

    /**
     * 获取Job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);

        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return "No trigger found.";
        }

        if (trigger instanceof CronTrigger) {
            return String.format("jobGroup: %s, jobName: %s, startTime: %s, cron: %s", jobGroup, jobName, trigger.getStartTime(), ((CronTrigger)trigger).getCronExpression());
        }

        return String.format("jobGroup: %s, jobName: %s, startTime: %s, nextFireTime: %s", jobGroup, jobName, trigger.getStartTime(), trigger.getNextFireTime());
    }

    //暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    //暂停任务
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.pauseJob(jobKey);
            return "success";
        }

    }

    //恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    // 恢复某个任务
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.resumeJob(jobKey);
            return "success";
        }
    }

    //删除某个任务
    public String deleteJob(QrtzJobDetailConfig quartzConfig) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzConfig.getJobName(), quartzConfig.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "jobDetail is null";
        } else if (!scheduler.checkExists(jobKey)) {
            return "jobKey is not exists";
        } else {
            scheduler.deleteJob(jobKey);
            return "success";
        }

    }

    //修改任务
    public String modifyJob(QrtzJobDetailConfig quartzConfig) throws SchedulerException {
        if (!CronExpression.isValidExpression(quartzConfig.getCron())) {
            return "Illegal cron expression";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzConfig.getJobName(), quartzConfig.getJobGroup());
        JobKey jobKey = new JobKey(quartzConfig.getJobName(), quartzConfig.getJobGroup());
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //表达式调度构建器,不立即执行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzConfig.getCron()).withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            //修改参数
            if (!trigger.getJobDataMap().get("invokeParam").equals(quartzConfig.getInvokeParam())) {
                trigger.getJobDataMap().put("invokeParam", quartzConfig.getInvokeParam());
            }
            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            return "success";
        } else {
            return "Job or trigger not exists";
        }

    }

}
