package com.victor.demo.controller;

import com.victor.demo.domain.QrtzJobDetailConfig;
import com.victor.demo.service.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/quartz")
@RestController
public class QuartzController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public String addScheduleJob(@RequestParam String jobGroup, @RequestParam String jobName){
        try {
            return scheduleService.getJobState(jobName, jobGroup);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    @PostMapping("/add")
    public String addScheduleJob(@RequestBody QrtzJobDetailConfig qrtzJobDetailConfig){
        try {
            return scheduleService.addJob(qrtzJobDetailConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }

    @PostMapping("/del")
    public String delScheduleJob(@RequestBody QrtzJobDetailConfig qrtzJobDetailConfig){
        try {
            return scheduleService.addJob(qrtzJobDetailConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }

    @PostMapping("/upd")
    public String updateScheduleJob(@RequestBody QrtzJobDetailConfig qrtzJobDetailConfig){
        try {
            return scheduleService.addJob(qrtzJobDetailConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }
}
